package com.phjt.shangxueyuan.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.phjt.base.integration.EventBusManager;
import com.phjt.shangxueyuan.bean.TrainingCatalogSecondBean;
import com.phjt.shangxueyuan.bean.TrainingDetailBean;
import com.phjt.shangxueyuan.bean.event.TrainingBuySuccessEvent;
import com.phjt.shangxueyuan.common.CommonHttpManager;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;
import com.phjt.shangxueyuan.mvp.ui.activity.ExerciseShowActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.HistoryThemeActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.OrderConfirmActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.TrainingPlayActivity;
import com.phjt.shangxueyuan.widgets.dialog.DialogUtils;
import com.phsxy.utils.LogUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * @author: gengyan
 * date:    2021/2/2 15:18
 * company: 普华集团
 * description: 训练营工具类
 */
public class TrainingUtils {

    /**
     * 目录-小节 点击事件
     *
     * @param context    上下文
     * @param detailBean 训练营实体
     * @param secondBean 小节实体
     * @param isPlayPage 是否是视频播放页
     */
    public static void itemClick(Context context, TrainingDetailBean detailBean, TrainingCatalogSecondBean secondBean, boolean isPlayPage) {
        if (detailBean == null || secondBean == null) {
            return;
        }
        //1.视频 2.打卡
        int taskType = secondBean.getTaskType();
        //1 未解锁 2 未完成 3 已完成
        int unlockState = secondBean.getUnlockState();
        //试看 0 否 1是
        int tryRead = secondBean.getTryRead();

        //1. 上架 2.下架
        if (detailBean.getUpStatus() == 2) {
            showMessage("非常抱歉，训练营已下架");
        } else if (!detailBean.isBuy() && tryRead == 1 && taskType == 1) {
            //试看，且未购买时，前往视频播放页
            toTrainingPlayPage(context, detailBean, secondBean, isPlayPage);

            //保存最后观看节点
            saveLastWatch(context, detailBean.getId(), secondBean);
        } else if (!detailBean.isBuy()) {
            //判断报名时间
            checkBuyTime(context, detailBean, true);
        } else if (unlockState == 1) {
            //1.自由模式 2.闯关模式 3.日期模式
            int unlockPatternStatus = detailBean.getUnlockPatternStatus();
            if (unlockPatternStatus == 2) {
                if (checkClassBeginTime(detailBean)) {
                    showMessage("任务未解锁，请先完成上一个任务");
                }
            } else if (unlockPatternStatus == 3) {
                showMessage("未到解锁时间，敬请期待");
            }
        } else if (unlockState == 2 || unlockState == 3) {
            if (taskType == 1) {
                //前往视频播放页
                toTrainingPlayPage(context, detailBean, secondBean, isPlayPage);
            } else if (taskType == 2) {
                //前往打卡页
                EventBusManager.getInstance().post(secondBean);

                Intent intent = new Intent(context, HistoryThemeActivity.class);
                intent.putExtra("punchCardId", secondBean.getPunchCardId());
                intent.putExtra("id", secondBean.getOtherId());
                intent.putExtra("nodeTaskLinkId", secondBean.getId());
                intent.putExtra("trainingCampId", detailBean.getId());
                context.startActivity(intent);
            } else if (taskType == 3) {
                //前往作业本详情页
                EventBusManager.getInstance().post(secondBean);

                Intent intent = new Intent(context, ExerciseShowActivity.class);
                intent.putExtra(Constant.BUNDLE_EXERCISE_ID, secondBean.getOtherId());
                intent.putExtra(Constant.BUNDLE_EXERCISE_BOOK_ID, secondBean.getExerciseBookId());
                intent.putExtra(Constant.BUNDLE_NODE_TASK_ID, secondBean.getId());
                intent.putExtra(Constant.BUNDLE_TRAINING_CAMP_ID, detailBean.getId());
                context.startActivity(intent);
            }
            //保存最后观看节点
            saveLastWatch(context, detailBean.getId(), secondBean);
        }
    }

    /**
     * 底部购买/报名时间判断  报名开始和结束时间进行判断
     *
     * @param context      上下文
     * @param detailBean   训练营实体
     * @param isShowDialog 是否显示提示框，false直接购买
     */
    public static void checkBuyTime(Context context, TrainingDetailBean detailBean, boolean isShowDialog) {
        if (detailBean != null) {
            //是否停售（0.否 1.是）
            int whetherStopSell = detailBean.getWhetherStopSell();
            if (whetherStopSell == 1) {
                showMessage("商品已停售");
                return;
            }

            try {
                String startTime = detailBean.getRecruitStudentStartTime();
                String endTime = detailBean.getRecruitStudentEndTime();
                String nowTime = detailBean.getNowTime();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                Date startDate = df.parse(startTime);
                Date endDate = df.parse(endTime);
                Date nowDate = df.parse(nowTime);
                if (nowDate.before(startDate)) {
                    showMessage("训练营将于" + startTime + "开启报名敬请关注！");
                } else if (nowDate.after(endDate)) {
                    showMessage("非常抱歉，训练营已结束！");
                } else {
                    if (isShowDialog) {
                        showBuyDialog(context, detailBean);
                    } else {
                        buyTrainingCamp(context, detailBean);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                LogUtils.e("============" + e.getMessage());
            }
        }
    }

    /**
     * 购买提示弹框
     *
     * @param context    上下文
     * @param detailBean 训练营实体
     */
    public static void showBuyDialog(Context context, TrainingDetailBean detailBean) {
        //售卖方式（1.免费 2.付费）
        int sellType = detailBean.getSellType();
        DialogUtils.showCancelSureDialog(context,
                sellType == 1 ? "您还未报名训练营" : "您还未购买训练营", "解锁学习全部内容", "再想想",
                sellType == 1 ? "立即报名" : "立即购买",
                new DialogUtils.OnCancelSureClick() {
                    @Override
                    public void clickSure() {
                        buyTrainingCamp(context, detailBean);
                    }
                });
    }

    /**
     * 去报名 、购买
     *
     * @param context    上下文
     * @param detailBean 训练营实体
     */
    @SuppressLint("CheckResult")
    public static void buyTrainingCamp(Context context, TrainingDetailBean detailBean) {
        String trainingCampId = detailBean.getId();
        //售卖方式（1.免费 2.付费）
        int sellType = detailBean.getSellType();
        if (sellType == 1) {
            CommonHttpManager.getInstance(context)
                    .obtainRetrofitService(ApiService.class)
                    .trainingFreeBuy(trainingCampId)
                    .compose(RxUtils.applySchedulers())
                    .subscribe(baseBean -> {
                        if (baseBean.isOk()) {
                            showMessage("报名成功");
                            EventBusManager.getInstance().post(new TrainingBuySuccessEvent(1));
                        } else {
                            showMessage(baseBean.msg);
                        }
                    }, throwable -> LogUtils.e("===============" + throwable.getMessage()));
        } else if (sellType == 2) {
            Intent intent1 = new Intent(context, OrderConfirmActivity.class);
            intent1.putExtra(Constant.BUNDLE_ORDER_COMMODITYID, trainingCampId);
            intent1.putExtra(Constant.BUNDLE_ORDER_ACTIVITYSTATE, "1");
            intent1.putExtra(Constant.BUNDLE_ORDER_NAME, detailBean.getTrainingCampName());
            intent1.putExtra(Constant.BUNDLE_ORDER_REALPRICE, detailBean.getSellPrice());
            intent1.putExtra(Constant.BUNDLE_ORDER_TYPE, 3);
            intent1.putExtra(Constant.BUNDLE_ORDER_COMMODITY_TYPE, 3);
            context.startActivity(intent1);
        }
    }

    /**
     * 前往视频播放页面
     *
     * @param isPlayPage 是否是视频播放页
     */
    public static void toTrainingPlayPage(Context context, TrainingDetailBean detailBean, TrainingCatalogSecondBean secondBean, boolean isPlayPage) {
        if (isPlayPage) {
            EventBusManager.getInstance().post(secondBean);
        } else {
            Intent playIntent = new Intent(context, TrainingPlayActivity.class);
            Bundle playBundle = new Bundle();
            playBundle.putSerializable(Constant.BUNDLE_TRAINING_DETAIL_BEAN, detailBean);
            playBundle.putSerializable(Constant.BUNDLE_TRAINING_PLAY_BEAN, secondBean);
            playIntent.putExtras(playBundle);
            context.startActivity(playIntent);
        }
    }

    /**
     * 开课时间判断
     *
     * @return true已开课  false未到开课时间
     */
    public static boolean checkClassBeginTime(TrainingDetailBean detailBean) {
        try {
            String startTime = detailBean.getCurriculumStartTime();
            String endTime = detailBean.getCurriculumEndTime();
            String nowTime = detailBean.getNowTime();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Date startDate = df.parse(startTime);
            Date endDate = df.parse(endTime);
            Date nowDate = df.parse(nowTime);
            if (nowDate.before(startDate)) {
                showMessage(String.format("训练营将于%s开课，敬请期待", startTime));
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("=======================" + e.toString());
        }
        return true;
    }

    private static void showMessage(String message) {
        TipsUtil.show(message);
    }


    /**
     * -------------------------------------试看结束逻辑-------------------------------------------
     */
    public static void tryLookEndTimeCheck(Context context, TrainingDetailBean detailBean) {
        if (detailBean != null) {
            try {
                String startTime = detailBean.getRecruitStudentStartTime();
                String endTime = detailBean.getRecruitStudentEndTime();
                String nowTime = detailBean.getNowTime();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                Date startDate = df.parse(startTime);
                Date endDate = df.parse(endTime);
                Date nowDate = df.parse(nowTime);
                if (nowDate.before(startDate)) {
                    //showMessage("训练营将于" + startTime + "开启报名敬请关注！");
                } else if (nowDate.after(endDate)) {
                    //showMessage("非常抱歉，训练营已结束！");
                } else {
                    showTryLookEndDialog(context, detailBean);
                }
            } catch (Exception e) {
                e.printStackTrace();
                LogUtils.e("============" + e.getMessage());
            }
        }
    }

    public static void showTryLookEndDialog(Context context, TrainingDetailBean detailBean) {
        //售卖方式（1.免费 2.付费）
        int sellType = detailBean.getSellType();
        DialogUtils.showCancelSureDialog(context,
                sellType == 1 ? "试看结束，是否报名训练营？" : "试看结束，是否购买训练营？", null, "再想想",
                sellType == 1 ? "立即报名" : "立即购买",
                new DialogUtils.OnCancelSureClick() {
                    @Override
                    public void clickSure() {
                        buyTrainingCamp(context, detailBean);
                    }
                });
    }

    /**
     * 保存训练营最后观看节点位置
     */
    @SuppressLint("CheckResult")
    public static void saveLastWatch(Context context, String trainingCampId, TrainingCatalogSecondBean secondBean) {
        CommonHttpManager.getInstance(context)
                .obtainRetrofitService(ApiService.class)
                .saveTrainingCampLastWatch(trainingCampId, secondBean.getNodeId(), secondBean.getId())
                .compose(RxUtils.applySchedulers())
                .subscribe(baseBean -> {
                    if (baseBean.isOk()) {

                    }
                }, throwable -> LogUtils.e("===============" + throwable.getMessage()));
    }
}
