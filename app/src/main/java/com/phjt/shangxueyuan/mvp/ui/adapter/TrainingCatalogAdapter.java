package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Context;
import androidx.core.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.TrainingCatalogFirstBean;
import com.phjt.shangxueyuan.bean.TrainingCatalogSecondBean;
import com.phjt.view.roundView.RoundTextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author: gengyan
 * date:    2021/1/20
 * company: 普华集团
 * description: 训练营目录的adapter
 */
public class TrainingCatalogAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    private Context mContext;
    /**
     * 记录当前播放的视频 标识
     */
    public String pointId;
    /**
     * 是否已购买
     */
    private boolean isBuy;
    /**
     * 解锁模式（1.自由模式 2.闯关模式 3.日期模式）
     */
    private int unlockPatternStatus;
    /**
     * 解锁时间
     */
    private String unLockDate;
    /**
     * 记录解锁时间的显示位置的id，处理只显示一个的问题
     */
    private String unLockId;

    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;

    public TrainingCatalogAdapter(Context context, List<MultiItemEntity> data) {
        super(data);
        this.mContext = context;
        addItemType(TYPE_LEVEL_0, R.layout.item_catalog_training_type_0);
        addItemType(TYPE_LEVEL_1, R.layout.item_catalog_training_type_1);
    }

    public void setBuyAndPatternStatus(boolean buy, int unlockPatternStatus, String unLockDate) {
        this.isBuy = buy;
        this.unlockPatternStatus = unlockPatternStatus;
        this.unLockDate = unLockDate;
        this.unLockId = null;
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case TYPE_LEVEL_0:
                TrainingCatalogFirstBean firstBean = (TrainingCatalogFirstBean) item;

                //判断是否显示解锁时间
                String formatUnLockDate = null;
                List<TrainingCatalogSecondBean> taskList = firstBean.getTaskList();
                if (unlockPatternStatus == 3 && taskList != null && !TextUtils.isEmpty(unLockDate)) {
                    for (int i = 0; i < taskList.size(); i++) {
                        TrainingCatalogSecondBean secondBean = taskList.get(i);
                        if (secondBean != null && secondBean.getUnlockState() == 1) {
                            try {
                                DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                                Date date = dFormat.parse(unLockDate);
                                DateFormat dFormat1 = new SimpleDateFormat("MM月dd日 HH:mm", Locale.getDefault());
                                formatUnLockDate = dFormat1.format(date);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            if (TextUtils.isEmpty(unLockId) || TextUtils.equals(unLockId, firstBean.getId())) {
                                unLockId = firstBean.getId();
                            }
                            break;
                        }
                    }
                }

                helper.setText(R.id.tv_catalog_0_time_item, formatUnLockDate)
                        .setVisible(R.id.tv_catalog_0_time_item, TextUtils.equals(unLockId, firstBean.getId()))
                        .setImageResource(R.id.iv_catalog_0_item, firstBean.isExpanded() ? R.drawable.arrow_catalog_up : R.drawable.arrow_catalog_down)
                        .setText(R.id.tv_catalog_0_title_item, firstBean.getNodeName());
                break;
            case TYPE_LEVEL_1:
                TrainingCatalogSecondBean secondBean = (TrainingCatalogSecondBean) item;
                helper.setText(R.id.tv_catalog_1_name_item, secondBean.getOtherName());
                //1 未解锁 2 未完成 3 已完成
                int unlockState = secondBean.getUnlockState();
                //任务类型（1.视频 2.打卡 3.作业）
                int taskType = secondBean.getTaskType();
                //作业状态 0未提交 1已提交 2未点评
                int exrciseState = secondBean.getExrciseState();

                RoundTextView tvCatalogType = helper.getView(R.id.tv_catalog_1_type_item);
                if (taskType == 1) {
                    tvCatalogType.setText("视频");
                    tvCatalogType.setTextColor(ContextCompat.getColor(mContext, R.color.color_DE8E40));
                    tvCatalogType.getDelegate().setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_FFECD6));
                    tvCatalogType.getDelegate().setBackgroundPressColor(ContextCompat.getColor(mContext, R.color.color_FFECD6));
                } else {
                    tvCatalogType.setText(taskType == 2 ? "打卡" : "作业");
                    tvCatalogType.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                    tvCatalogType.getDelegate().setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_4071FC));
                    tvCatalogType.getDelegate().setBackgroundPressColor(ContextCompat.getColor(mContext, R.color.color_4071FC));
                }

                ImageView ivLock = helper.getView(R.id.iv_catalog_1_lock_item);
                TextView tvState = helper.getView(R.id.tv_catalog_1_state_item);
                TextView tvName = helper.getView(R.id.tv_catalog_1_name_item);
                if (isBuy) {
                    if (unlockState == 1) {
                        ivLock.setVisibility(View.VISIBLE);
                        tvState.setVisibility(View.GONE);
                        tvName.setTextColor(ContextCompat.getColor(mContext, R.color.color_999999));
                    } else {
                        ivLock.setVisibility(View.GONE);
                        tvState.setVisibility(View.VISIBLE);
                        tvName.setTextColor(ContextCompat.getColor(mContext, R.color.color_333333));
                        if (taskType == 3) {
                            tvState.setText(exrciseState == 0 ? "未完成" : exrciseState == 1 ? "已提交" : "已点评");
                            tvState.setTextColor(ContextCompat.getColor(mContext, exrciseState == 0 ? R.color.color_DB0000 :
                                    exrciseState == 1 ? R.color.color_FF9630 : R.color.color_999999));
                        } else {
                            tvState.setText(unlockState == 2 ? "未完成" : "已完成");
                            tvState.setTextColor(ContextCompat.getColor(mContext, unlockState == 2 ? R.color.color_DB0000 : R.color.color_999999));
                        }
                    }
                } else {
                    if (secondBean.getTryRead() == 1) {
                        ivLock.setVisibility(View.GONE);
                        tvState.setVisibility(View.VISIBLE);
                        tvState.setText("试看");
                        tvState.setTextColor(ContextCompat.getColor(mContext, R.color.color_2673FE));
                    } else {
                        ivLock.setVisibility(View.VISIBLE);
                        tvState.setVisibility(View.GONE);
                        tvName.setTextColor(ContextCompat.getColor(mContext, R.color.color_999999));
                    }
                }

                break;
            default:
                break;
        }
    }

    /**
     * 重置Rv显示状态
     */
    public void resetDataSelect() {
//        List<MultiItemEntity> itemEntities = getData();
//        for (int i = 0; i < itemEntities.size(); i++) {
//            MultiItemEntity multiItemEntity = itemEntities.get(i);
//            if (multiItemEntity instanceof CourseCatalogSecondBean) {
//                CourseCatalogSecondBean secondBean = (CourseCatalogSecondBean) multiItemEntity;
//                if (TextUtils.equals(secondBean.getPointId(), pointId)) {
//                    secondBean.setSelect(true);
//                } else {
//                    secondBean.setSelect(false);
//                }
//            }
//        }
//        notifyDataSetChanged();
    }

}
