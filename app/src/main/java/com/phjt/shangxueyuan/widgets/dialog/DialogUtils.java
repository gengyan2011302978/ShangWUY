package com.phjt.shangxueyuan.widgets.dialog;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mzmedia.utils.String_Utils;
import com.phjt.base.integration.AppManager;
import com.phjt.base.integration.EventBusManager;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.annotate.SingleClick;
import com.phjt.shangxueyuan.bean.ListBannerBean;
import com.phjt.shangxueyuan.bean.SaveGeneratePicturesBean;
import com.phjt.shangxueyuan.bean.ScreenlBean;
import com.phjt.shangxueyuan.bean.event.EventBean;
import com.phjt.shangxueyuan.common.AppImageLoader;
import com.phjt.shangxueyuan.mvp.ui.adapter.StudyScreenPopAdapter;
import com.phjt.shangxueyuan.utils.BitmapPhjtUtils;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.ConstantUmeng;
import com.phjt.shangxueyuan.utils.GlideUtils;
import com.phjt.shangxueyuan.utils.ScreenMapUtils;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.shangxueyuan.utils.ToWeChatUtil;
import com.phjt.shangxueyuan.utils.UmengUtil;
import com.phjt.shangxueyuan.utils.VipUtil;
import com.phjt.shangxueyuan.widgets.GetConfig;
import com.phjt.shangxueyuan.widgets.GlideImageLoader;
import com.phjt.shangxueyuan.widgets.HttpDownListener;
import com.phjt.shangxueyuan.widgets.NetworkUtils;
import com.phjt.shangxueyuan.widgets.OkHttpDownUtil;
import com.phjt.view.roundImg.RoundedImageView;
import com.phsxy.utils.SPUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import me.jessyan.autosize.utils.AutoSizeUtils;
import okhttp3.Call;
import okhttp3.Response;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * @author: Roy
 * date:    2020/3/30
 * company: 普华集团
 * description: dialog公共类
 */
public class DialogUtils {
    /**
     * 取消、确定的公共dialog
     *
     * @param context           上下文
     * @param title             标题
     * @param content           内容
     * @param cancelContent     取消按钮内容
     * @param sureContent       确定按钮内容
     * @param onCancelSureClick 取消、确定点击监听
     */
    public static void showCancelSureDialog(Context context, String title, String content, String cancelContent,
                                            String sureContent, OnCancelSureClick onCancelSureClick) {
        showCancelSureDialog(context, R.layout.dialog_sure_cancel, title, content, cancelContent, sureContent, onCancelSureClick);
    }


    public static void showCancelSureDialog(Context context, int id, String title, String content, String cancelContent,
                                            String sureContent, OnCancelSureClick onCancelSureClick) {
        Dialog cancelSureDialog = new BaseDialog(context, R.style.BaseDialog, id);
        cancelSureDialog.setCancelable(true);
        cancelSureDialog.setCanceledOnTouchOutside(false);
        cancelSureDialog.show();

        TextView tvTitle = cancelSureDialog.findViewById(R.id.tv_titile);
        TextView tvContent = cancelSureDialog.findViewById(R.id.tv_content);
        TextView tvCancel = cancelSureDialog.findViewById(R.id.tv_cancel);
        TextView tvSure = cancelSureDialog.findViewById(R.id.tv_sure);

        tvTitle.setText(title);
        if (TextUtils.isEmpty(content)) {
            tvContent.setVisibility(View.GONE);
        } else {
            tvContent.setText(content);
            tvContent.setVisibility(View.VISIBLE);
        }
        if (title.equals("提问将支付学豆") || title.equals("当前学豆不足，请去充值")) {
            tvTitle.setVisibility(View.GONE);
            tvCancel.setText("否");
            tvSure.setText("是");
            tvSure.setBackgroundResource(R.drawable.bg_consume);
        }
        tvCancel.setText(cancelContent);
        tvSure.setText(sureContent);
        if (!TextUtils.isEmpty(title)) {
            if (title.contains("无法使用相册")) {
                tvSure.setText("去授权");
            }
        }
        if (context.getResources().getString(R.string.is_delete_diary).equals(content)) {
            tvTitle.setVisibility(View.GONE);
        }


        tvCancel.setOnClickListener(v -> cancelSureDialog.dismiss());

        tvSure.setOnClickListener(v -> {
            cancelSureDialog.dismiss();
            if (onCancelSureClick != null) {
                onCancelSureClick.clickSure();
            }
        });
    }

    /**
     * WebView页面，购买成功的dialog
     */
    public static void showWebVipSuccessDialog(Context context, OnCancelSureClick onCancelSureClick) {
        Dialog cancelSureDialog = new BaseDialog(context, R.style.BaseDialog, R.layout.dialog_web_vip_success);
        cancelSureDialog.setCancelable(true);
        cancelSureDialog.setCanceledOnTouchOutside(false);
        cancelSureDialog.show();

        TextView tvCancel = cancelSureDialog.findViewById(R.id.tv_cancel);
        TextView tvSure = cancelSureDialog.findViewById(R.id.tv_sure);

        tvCancel.setOnClickListener(v -> {
            cancelSureDialog.dismiss();

        });

        tvSure.setOnClickListener(v -> {
            cancelSureDialog.dismiss();
            if (onCancelSureClick != null) {
                onCancelSureClick.clickSure();
            }

        });
    }

    /**
     * 结束直播dialog
     */
    public static void showCloseLiveDialog(Context context, OnCancelSureClick onCancelSureClick) {
        Dialog cancelSureDialog = new BaseDialog(context, R.style.BaseDialog, R.layout.dialog_sure_close);
        cancelSureDialog.setCancelable(true);
        cancelSureDialog.setCanceledOnTouchOutside(false);
        cancelSureDialog.show();

        TextView tvCancel = cancelSureDialog.findViewById(R.id.tv_cancel);
        TextView tvSure = cancelSureDialog.findViewById(R.id.tv_sure);

        tvCancel.setOnClickListener(v -> {
            cancelSureDialog.dismiss();

        });

        tvSure.setOnClickListener(v -> {
            cancelSureDialog.dismiss();
            if (onCancelSureClick != null) {
                onCancelSureClick.clickSure();
            }

        });
    }

    /**
     * 开始直播dialog
     */
    public static void showOpenLiveDialog(Context context, int type, OnCancelSureClick onCancelSureClick) {
        Dialog cancelSureDialog = new BaseDialog(context, R.style.BaseDialog, R.layout.dialog_sure_open);
        cancelSureDialog.setCancelable(true);
        cancelSureDialog.setCanceledOnTouchOutside(false);
        cancelSureDialog.show();

        ImageView tvCancel = cancelSureDialog.findViewById(R.id.iv_cancel);
        ImageView ivScan = cancelSureDialog.findViewById(R.id.iv_scan);
        TextView tvScan = cancelSureDialog.findViewById(R.id.tv_scan);
        ImageView tvSure = cancelSureDialog.findViewById(R.id.iv_sure);

        if (type == 1) {
            tvScan.setText("请将屏幕竖过来");
            ivScan.setBackgroundResource(R.drawable.icon_vertical);
        }

        tvCancel.setOnClickListener(v -> {
            cancelSureDialog.dismiss();

        });

        tvSure.setOnClickListener(v -> {
            cancelSureDialog.dismiss();
            if (onCancelSureClick != null) {
                onCancelSureClick.clickSure();
            }

        });
    }

    /**
     * 日记排序
     */
    public static void showChangeListDialog(Context context, OnCancelSureClick onCancelSureClick) {
        Dialog cancelSureDialog = new BaseDialog(context, R.style.BaseDialog, R.layout.dialog_change_list);
        cancelSureDialog.setCancelable(false);
        cancelSureDialog.setCanceledOnTouchOutside(true);
        cancelSureDialog.show();

        LinearLayout tvCancel = cancelSureDialog.findViewById(R.id.linear_delete);
        LinearLayout tvSure = cancelSureDialog.findViewById(R.id.linear_top);

        tvCancel.setOnClickListener(v -> {
            cancelSureDialog.dismiss();
            if (onCancelSureClick != null) {
                onCancelSureClick.clickDelete();
            }
        });

        tvSure.setOnClickListener(v -> {
            cancelSureDialog.dismiss();
            if (onCancelSureClick != null) {
                onCancelSureClick.clickSure();
            }

        });
    }

    /**
     * 话题详情页面更多
     */
    public static void showMoreTopicDialog(Context context, String type, OnCancelSureClick onCancelSureClick) {
        Dialog cancelSureDialog = new BaseDialog(context, R.style.BaseDialog, R.layout.dialog_more_topic);
        cancelSureDialog.setCancelable(false);
        cancelSureDialog.setCanceledOnTouchOutside(true);
        cancelSureDialog.show();

        LinearLayout tvCancel = cancelSureDialog.findViewById(R.id.linear_delete);
        LinearLayout tvSure = cancelSureDialog.findViewById(R.id.linear_top);
        TextView tvTop = cancelSureDialog.findViewById(R.id.tv_top);
        ImageView ivTop = cancelSureDialog.findViewById(R.id.iv_top);

        if (type.equals("0")) {
            tvTop.setText("取消置顶");
            ivTop.setBackgroundResource(R.drawable.cancle_topic_icon);
        } else {
            tvTop.setText("动态置顶");
            ivTop.setBackgroundResource(R.drawable.top_topic_icon);
        }
        tvCancel.setOnClickListener(v -> {
            cancelSureDialog.dismiss();
            if (onCancelSureClick != null) {
                onCancelSureClick.clickDelete();
            }
        });

        tvSure.setOnClickListener(v -> {
            cancelSureDialog.dismiss();
            if (onCancelSureClick != null) {
                onCancelSureClick.clickSure();
            }

        });
    }


    /**
     * 会员开通提示
     */
    public static void showVipBuyDialog(Context context, String comment, String title, String strbutton, OnCancelSureClick onCancelSureClick) {
        Dialog cancelSureDialog = new BaseDialog(context, R.style.BaseDialog, R.layout.dialog_vip_buy_success);
        cancelSureDialog.setCancelable(true);
        cancelSureDialog.setCanceledOnTouchOutside(false);
        cancelSureDialog.show();

        TextView tvCancel = cancelSureDialog.findViewById(R.id.tv_cancel);
        TextView tvSure = cancelSureDialog.findViewById(R.id.tv_sure);
        TextView tvTitile = cancelSureDialog.findViewById(R.id.tv_titile);
        TextView tvComment = cancelSureDialog.findViewById(R.id.tv_comment);
        tvComment.setText(comment);
        tvTitile.setText(title);
        tvSure.setText(strbutton);
        tvCancel.setOnClickListener(v -> {
            cancelSureDialog.dismiss();
        });

        tvSure.setOnClickListener(v -> {
            cancelSureDialog.dismiss();
            if (onCancelSureClick != null) {
                onCancelSureClick.clickSure();
            }
        });
    }

    /**
     * 生成分享图片
     */
    public static void generatingSharedImages(Activity context, SaveGeneratePicturesBean bean) {
        Dialog mDialog = new BaseDialog(context, R.style.BaseDialog, R.layout.dialog_generating_shared_images);
        mDialog.setCancelable(true);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();

        RoundedImageView ivRoundedImageView = mDialog.findViewById(R.id.iv_re_head_pic);
        RoundedImageView ivQrCode = mDialog.findViewById(R.id.iv_qr_code);
        ImageView ivCancel = mDialog.findViewById(R.id.iv_cancel);
        ConstraintLayout clSharedImages = mDialog.findViewById(R.id.cl_shared_images);
        TextView tvSure = mDialog.findViewById(R.id.tv_sure);
        TextView tvTitile = mDialog.findViewById(R.id.tv_audition_title_item);
        TextView tvComment = mDialog.findViewById(R.id.tv_study_people_item);
        TextView tvDay = mDialog.findViewById(R.id.tv_day);
        TextView tvMoreAction = mDialog.findViewById(R.id.tv_more_action);
        ConstraintLayout clQrCode = mDialog.findViewById(R.id.cl_qr_code);
        ConstraintLayout clEneratingSharedImages = mDialog.findViewById(R.id.cl_enerating_shared_images);
        RoundedImageView ivImagesBg = mDialog.findViewById(R.id.iv_enerating_shared_images);
        tvTitile.setText(bean.getNickName());
        tvComment.setText(String.format("在【%s】完成打卡", bean.getPunchCardName()));
        tvDay.setText(String.format("%s天", bean.getPartyNum()));
        tvMoreAction.setText(String.format("%s", bean.getPerCentum() + "%"));

        GlideUtils.load(bean.getPhoto(), ivRoundedImageView, R.drawable.iv_mine_avatar);
        GlideUtils.load(bean.getBackgroundImg(), ivImagesBg, R.drawable.image_placeholder);
        GlideUtils.load(bean.getPunchCardCode(), ivQrCode, R.drawable.ic_about);

        ivCancel.setOnClickListener(v -> {
            mDialog.dismiss();
        });

        clSharedImages.setOnLongClickListener(v -> {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // 申请WRITE_EXTERNAL_STORAGE权限
                ActivityCompat.requestPermissions(context,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_NETWORK_STATE}, 3);
            } else {
                Bitmap viewBitmap = BitmapPhjtUtils.getViewBitmap(clEneratingSharedImages);
                BitmapPhjtUtils.bitmapToFile(context, viewBitmap);
            }
            return true;
        });
    }

    /**
     * 提现规则
     */
    public static void withdrawalDialogRule(Context context) {
        Dialog cancelSureDialog = new BaseDialog(context, R.style.BaseDialog, R.layout.dialog_withdrawal);
        cancelSureDialog.setCancelable(true);
        cancelSureDialog.setCanceledOnTouchOutside(false);
        cancelSureDialog.show();

        ImageView tvCancel = cancelSureDialog.findViewById(R.id.ic_cancle);
        TextView tvComment = cancelSureDialog.findViewById(R.id.tv_comment);
        tvComment.setText(SPUtils.getInstance().getString("WITHDRAWAL_RULE"));
        tvCancel.setOnClickListener(v -> {
            cancelSureDialog.dismiss();

        });
    }

    static Dialog cancelSureDialog = null;

    /**
     * 清流量下载
     */
    public static void showWifiDownloadDialog(Context context, String commont, String leftstr, String rightstr, OnCancelSureClick onCancelSureClick) {
        if (cancelSureDialog != null) {
            return;
        }
        cancelSureDialog = new BaseDialog(context, R.style.BaseDialog, R.layout.dialog_web_vip_success);
        cancelSureDialog.setCancelable(true);
        cancelSureDialog.setCanceledOnTouchOutside(false);
        cancelSureDialog.show();

        TextView tvTitile = cancelSureDialog.findViewById(R.id.tv_titile);
        TextView tvCancel = cancelSureDialog.findViewById(R.id.tv_cancel);
        TextView tvSure = cancelSureDialog.findViewById(R.id.tv_sure);
        tvTitile.setText(commont);
        tvSure.setText(rightstr);
        tvCancel.setText(leftstr);

        tvCancel.setOnClickListener(v -> {
            cancelSureDialog.dismiss();
            if (onCancelSureClick != null) {
                onCancelSureClick.clickCancel();
            }
            cancelSureDialog = null;
        });

        tvSure.setOnClickListener(v -> {
            cancelSureDialog.dismiss();
            if (onCancelSureClick != null) {
                onCancelSureClick.clickSure();
            }
            cancelSureDialog = null;
        });
    }

    /**
     * 专栏购买提示
     * <p>
     * 打开微信的dialog （弃用）
     */
    public static Dialog SubscribeDialog(Context context, OnCancelSureClick onCancelSureClick) {
        Dialog openWeChatDialog = new BaseDialog(context, R.style.BaseDialog, R.layout.dialog_wechat_open);
        openWeChatDialog.setCancelable(true);
        openWeChatDialog.setCanceledOnTouchOutside(false);
        openWeChatDialog.show();
        if (context instanceof Activity) {
            UmengUtil.umengCount((Activity) context, ConstantUmeng.MINE_CLICK_ONLINE_CUSTOMER_SERVICE);
        }
        TextView tvCancel = openWeChatDialog.findViewById(R.id.tv_wechat_open_cancel);
        TextView tvWeChatOpen = openWeChatDialog.findViewById(R.id.tv_wechat_open);

        tvCancel.setOnClickListener(v -> {
            openWeChatDialog.dismiss();

        });
        tvWeChatOpen.setOnClickListener(v -> {
            onCancelSureClick.clickSure();
            openWeChatDialog.dismiss();
        });

        return openWeChatDialog;
    }

    /**
     * 公告的dialog
     */
    public static Dialog mainAnnoDialog(Context context, String title, String desc) {
        Dialog openWeChatDialog = new BaseDialog(context, R.style.BaseDialog, R.layout.dialog_main_anno);
        openWeChatDialog.setCancelable(true);
        openWeChatDialog.setCanceledOnTouchOutside(false);
        openWeChatDialog.show();

        //赋值微信号到剪切板
        ToWeChatUtil.copyContent(context, Constant.WECHAT_NUMBER);

        TextView tvTitle = openWeChatDialog.findViewById(R.id.tv_wechat_open_title);
        TextView tvContent = openWeChatDialog.findViewById(R.id.tv_wechat_open_content);
        TextView tvWeChatOpen = openWeChatDialog.findViewById(R.id.tv_wechat_open);

        tvTitle.setText(title);
        tvContent.setText(desc);
        tvWeChatOpen.setOnClickListener(v -> {
            openWeChatDialog.dismiss();
            AppManager.getAppManager().killAll();
        });

        return openWeChatDialog;
    }

    /**
     * 通知弹框
     */
    public static void showNoticeDialog(Context context, String title, String content, final OnClickDialogListener listener) {
        Dialog openWeChatDialog = new BaseDialog(context, R.style.Plane_Dialog, R.layout.dialog_notice);
        openWeChatDialog.setCancelable(true);
        openWeChatDialog.setCanceledOnTouchOutside(false);
        Window dialogWindow = openWeChatDialog.getWindow();
        dialogWindow.setGravity(Gravity.TOP);
        WindowManager windowManager = ((Activity) context).getWindowManager();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        Display display = windowManager.getDefaultDisplay();
        lp.width = (int) (display.getWidth() * 0.9);
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialogWindow.setAttributes(lp);
        openWeChatDialog.show();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialogWindow.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            dialogWindow.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            dialogWindow.setStatusBarColor(Color.TRANSPARENT);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                try {
                    Class decorViewClazz = Class.forName("com.android.internal.policy.DecorView");
                    Field field = decorViewClazz.getDeclaredField("mSemiTransparentStatusBarColor");
                    field.setAccessible(true);
                    //去掉高版本蒙层改为透明
                    field.setInt(dialogWindow.getDecorView(), Color.TRANSPARENT);
                } catch (Exception e) {
                }
            }
        }
        TextView tvTitle = openWeChatDialog.findViewById(R.id.tv_home_title);
        TextView tvContent = openWeChatDialog.findViewById(R.id.tv_home_content);
        ConstraintLayout clOpen = openWeChatDialog.findViewById(R.id.cl_propelling_movement);

        tvTitle.setText(title);
        tvContent.setText(content);

        clOpen.setOnClickListener(v -> {

            if (listener != null) {
                listener.clickOk();
            }
            openWeChatDialog.dismiss();
        });
        openWeChatDialog.findViewById(android.R.id.content).postDelayed(new Runnable() {
            @Override
            public void run() {
                openWeChatDialog.dismiss();
            }
        }, 3000);

    }

    /**
     * 视频Vip弹框的dialog
     */
    public static void showVipVideoTipDialog(Context context, String title, String content, String cancelContent,
                                             String sureContent, OnCancelSureClick onCancelSureClick) {
        showCancelSureDialog(context, R.layout.dialog_sure_cancel, title, content, cancelContent, sureContent, onCancelSureClick);
    }

    /**
     * VIP弹框提示 (公共提示,勿改)
     */
    public static void showVipDialog(Context context) {
        DialogUtils.showVipVideoTipDialog(context, "您还不是VIP会员",
                "开通即可解锁课程", "取消", "去开通",
                new DialogUtils.OnCancelSureClick() {
                    @Override
                    public void clickSure() {
                        VipUtil.toVipPage(context);
                    }
                });
    }

    /**
     * VIP过期弹框提示
     */
    public static void showVipOverdueDialog(Context context) {
        DialogUtils.showVipVideoTipDialog(context, "您的会员已过期",
                "", "取消", "去续费",
                new DialogUtils.OnCancelSureClick() {
                    @Override
                    public void clickSure() {
                        VipUtil.toVipPage(context);
                    }
                });
    }

    public static void showVipBugDialog(Context context) {
        DialogUtils.showVipVideoTipDialog(context, "您还不是VIP会员",
                "", "取消", "去开通",
                new DialogUtils.OnCancelSureClick() {
                    @Override
                    public void clickSure() {
                        VipUtil.toVipPage(context);
                    }
                });
    }

    /**
     * app升级dialog
     */
    public static Dialog AppUpdateDialog(Context context, String versioncode, String communt, String downloadurl) {
        Dialog openWeChatDialog = new BaseDialog(context, R.style.BaseDialog, R.layout.updatedialog);
        openWeChatDialog.setCancelable(false);
        openWeChatDialog.setCanceledOnTouchOutside(false);
        openWeChatDialog.show();
        OkHttpDownUtil okHttpDownUtil = new OkHttpDownUtil();
        TextView app_code = openWeChatDialog.findViewById(R.id.app_code);
        TextView tv_update_communt = openWeChatDialog.findViewById(R.id.tv_update_communt);
        Button iv_gengxin = openWeChatDialog.findViewById(R.id.iv_gengxin);
        app_code.setText(versioncode + "");
        tv_update_communt.setText(communt);
        iv_gengxin.setOnClickListener(new View.OnClickListener() {
            @SingleClick
            @Override
            public void onClick(View v) {
                if (GetConfig.requestPermission(context)) {

                } else {
                    //TipsUtil.show("请先同意存储权限");
                    return;
                }
                if ("已下载完成，去安装".equals(iv_gengxin.getText().toString())) {
                    GetConfig.Instanll(new File(GetConfig.getverson()), context);
                    return;
                }
                if ("更新版本".equals(iv_gengxin.getText().toString()) || "下载异常，继续下载".equals(iv_gengxin.getText().toString()) || "已下载完成，去安装".equals(iv_gengxin.getText().toString()) || "网络异常，请检查网络".equals(iv_gengxin.getText().toString())) {
                    if (!"已下载完成，去安装".equals(iv_gengxin.getText().toString())) {

                        new Handler(context.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                iv_gengxin.setText("更新中");
                            }
                        });
                    }
                } else {
                    return;
                }
                if (!NetworkUtils.isMobileDataEnable(context) && !NetworkUtils.isWifiDataEnable(context)) {

                    new Handler(context.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            iv_gengxin.setText("网络异常，请检查网络");
                        }
                    });
                    return;
                }
                okHttpDownUtil.getRenewalDownRequest(context, downloadurl, new File(GetConfig.getverson()), iv_gengxin, new HttpDownListener() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response, long mTotalLength, long mAlreadyDownLength) {
                        if ("已下载完成，去安装".equals(iv_gengxin.getText().toString())) {
                            GetConfig.Instanll(new File(GetConfig.getverson()), context);
                        }
                        new Handler(context.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                iv_gengxin.setText("已下载" + (int) (mAlreadyDownLength * 1.0f / mTotalLength * 100) + "%");
                            }
                        });

                        if ((int) (mAlreadyDownLength * 1.0f / mTotalLength * 100) >= 100) {
                            GetConfig.Instanll(new File(GetConfig.getverson()), context);

                            new Handler(context.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    iv_gengxin.setText("已下载完成，去安装");
                                }
                            });
                        }
                    }
                });
            }
        });
//        tvCancel.setOnClickListener(v -> {
//            openWeChatDialog.dismiss();
//        });
//        tvWeChatOpen.setOnClickListener(v -> {
//            openWeChatDialog.dismiss();
//        });

        return openWeChatDialog;
    }


    /**
     * Splash 页面，协议弹框备份dialog
     */
    public static void showProtocolBoxBackupDialog(Context context, String title, String welcoming, String content, String cancelContent,
                                                   String sureContent, OnCancelSureClick onCancelSureClick) {
        Dialog cancelSureDialog = new BaseDialog(context, R.style.BaseDialog, R.layout.dialog_protocol_box_backup);
        cancelSureDialog.setCancelable(false);
        cancelSureDialog.setCanceledOnTouchOutside(false);
        cancelSureDialog.show();

        TextView tvTile = cancelSureDialog.findViewById(R.id.tv_box_title);
        TextView tvWelcoming = cancelSureDialog.findViewById(R.id.tv_welcoming);
        TextView tvContent = cancelSureDialog.findViewById(R.id.tv_content);
        TextView tvCancel = cancelSureDialog.findViewById(R.id.tv_cancel);
        TextView tvSure = cancelSureDialog.findViewById(R.id.tv_sure);
        TextView tvAgreement = cancelSureDialog.findViewById(R.id.tv_agreement);
        TextView tvPrivacy = cancelSureDialog.findViewById(R.id.tv_privacy);

        tvTile.setText(title);
        tvWelcoming.setText(welcoming);
        tvContent.setText(content);
        tvCancel.setText(cancelContent);
        tvSure.setText(sureContent);
        if (TextUtils.isEmpty(welcoming)) {
            tvWelcoming.setVisibility(View.GONE);
        }

        tvAgreement.setOnClickListener(v -> {
            if (onCancelSureClick != null) {
                onCancelSureClick.clickAgreement();
            }
        });

        tvPrivacy.setOnClickListener(v -> {
            if (onCancelSureClick != null) {
                onCancelSureClick.clickPrivacyAgreement();
            }
        });
        tvCancel.setOnClickListener(v -> {
            cancelSureDialog.dismiss();
            if (onCancelSureClick != null) {
                cancelSureDialog.dismiss();
                onCancelSureClick.clickCancel();
            }
        });

        tvSure.setOnClickListener(v -> {
            cancelSureDialog.dismiss();
            if (onCancelSureClick != null) {
                onCancelSureClick.clickSure();
            }

        });

    }


    /**
     * 支付失败弹框
     */
    public static Dialog showPayFailDialog(Context context, String titleStr, String contentStr, final OnClickDialogListener listener) {
        final Dialog dialog = new Dialog(context, R.style.custom_dialog);
        View mView = LayoutInflater.from(context).inflate(R.layout.dialog_order_payfail, null);
        dialog.setContentView(mView);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager windowManager = ((Activity) context).getWindowManager();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        Display display = windowManager.getDefaultDisplay();
        lp.width = (int) (display.getWidth() * 0.8);
        dialogWindow.setAttributes(lp);

        TextView tvTitle = mView.findViewById(R.id.dialog_order_payfail_tvtitle);
        TextView tvContent = mView.findViewById(R.id.dialog_order_payfail_tvcontent);
        tvTitle.setText(titleStr);
        tvContent.setText(contentStr);

        mView.findViewById(R.id.dialog_order_payfail_btncancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.clickCancel();
                }
                dialog.dismiss();
            }
        });
        mView.findViewById(R.id.dialog_order_payfail_btnok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.clickOk();
                }
                dialog.dismiss();
            }
        });
        dialog.show();
        return dialog;

    }

    /**
     * 充值弹窗
     *
     * @param context       上下文
     * @param linearNumb    需支付的总价格
     * @param isOrder       是否是订单确认页 （false为学豆充值页）
     * @param userStudyCoin 用户目前的学豆
     * @param listener      确认支付回调
     * @return dialog
     */
    public static Dialog showRechargeDialog(Context context, String linearNumb, boolean isOrder, String userStudyCoin, final OnClickDialogListener listener) {
        final Dialog dialog = new Dialog(context, R.style.custom_dialog);
        View mView = LayoutInflater.from(context).inflate(R.layout.dialog_recharge, null);
        dialog.setContentView(mView);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager windowManager = ((Activity) context).getWindowManager();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        Display display = windowManager.getDefaultDisplay();
        lp.width = (int) (display.getWidth() * 1);
        dialog.getWindow().setAttributes(lp);
        dialog.show();

        TextView tvRecharge = mView.findViewById(R.id.tv_recharge);
        TextView tvCurrency = mView.findViewById(R.id.tv_currency);
        Group rechargeGroup = mView.findViewById(R.id.recharge_group);
        ImageView ivClose = mView.findViewById(R.id.iv_dialog_close);
        TextView tvOrderTitle = mView.findViewById(R.id.tv_order_title);
        TextView tvOrderHaveCurrency = mView.findViewById(R.id.tv_order_have_currency);
        TextView tvOrderNeedCurrency = mView.findViewById(R.id.tv_order_need_currency);
        Group orderGroup = mView.findViewById(R.id.order_group);
        TextView tvSubmit = mView.findViewById(R.id.tv_submit);
        LinearLayout llWechatPay = mView.findViewById(R.id.ll_wechat_pay);
        LinearLayout llAlipay = mView.findViewById(R.id.ll_alipay);
        ImageView tvAlipayCheckStatus = mView.findViewById(R.id.tv_alipay_check_status);
        ImageView tvWechatPayCheckStatus = mView.findViewById(R.id.tv_wechat_pay_check_status);

        if (isOrder) {
            rechargeGroup.setVisibility(View.GONE);
            orderGroup.setVisibility(View.VISIBLE);
            tvOrderTitle.setText(String.format("本次兑换共需要%s学豆", String_Utils.linearNmber(linearNumb)));
            tvOrderHaveCurrency.setText(String.format("%s学豆", String_Utils.linearNmber(userStudyCoin)));
            double needCurrency = Double.parseDouble(linearNumb) - Double.parseDouble(userStudyCoin);
            tvOrderNeedCurrency.setText(String.format("%s学豆", String_Utils.linearNmber(String.valueOf(needCurrency))));
        } else {
            rechargeGroup.setVisibility(View.VISIBLE);
            orderGroup.setVisibility(View.GONE);
            tvCurrency.setText(String.format("%s学豆", String_Utils.linearNmber(linearNumb)));
            tvRecharge.setText(String.format("￥ %s", String_Utils.linearNmber(linearNumb)));
        }

        ivClose.setOnClickListener(v -> {
            dialog.dismiss();
        });
        int[] type = {2};
        llWechatPay.setOnClickListener(v -> {
            type[0] = 2;
            tvAlipayCheckStatus.setImageResource(R.drawable.login_iv_arguemnt_uncheck);
            tvWechatPayCheckStatus.setImageResource(R.drawable.login_iv_arguemnt_check);
        });
        llAlipay.setOnClickListener(v -> {
            type[0] = 1;
            tvAlipayCheckStatus.setImageResource(R.drawable.login_iv_arguemnt_check);
            tvWechatPayCheckStatus.setImageResource(R.drawable.login_iv_arguemnt_uncheck);
        });
        tvSubmit.setOnClickListener(v -> {
            if (listener != null) {
                listener.recharge(isOrder ? String.format(Locale.getDefault(), "%.2f", Double.parseDouble(linearNumb) - Double.parseDouble(userStudyCoin)) : linearNumb, type[0]);
            }
            dialog.dismiss();
        });
        return dialog;
    }

    /**
     * 评论弹框
     */
    public static Dialog showCommentDialog(Context context, String postContent, final OnClickDialogListener listener) {
        final Dialog dialog = new Dialog(context, R.style.custom_dialog);
        View mView = LayoutInflater.from(context).inflate(R.layout.dialog_comment, null);
        dialog.setContentView(mView);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager windowManager = ((Activity) context).getWindowManager();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        Display display = windowManager.getDefaultDisplay();
        lp.width = (int) (display.getWidth() * 1);

        dialog.getWindow().setAttributes(lp);
        EditText etContent = mView.findViewById(R.id.et_post_content);
        TextView tvSendComment = mView.findViewById(R.id.tv_send_comment);
        if (!TextUtils.isEmpty(postContent)) {
            tvSendComment.setTextColor(ContextCompat.getColor(context, R.color.color_2673FE));
            etContent.setText(postContent);
            etContent.setSelection(etContent.getText().length());
        }

        etContent.postDelayed(() -> {
            etContent.setFocusable(true);
            etContent.setFocusableInTouchMode(true);
            etContent.requestFocus();

            InputMethodManager imm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
            imm.showSoftInput(etContent, InputMethodManager.SHOW_IMPLICIT);
        }, 100);

        setEditText(context, etContent, tvSendComment);

        tvSendComment.setOnClickListener(v -> {
            String mPostContent = etContent.getText().toString().trim();
            if (TextUtils.isEmpty(mPostContent)) {
                TipsUtil.show("请输入回复内容");
                return;
            }
            if (listener != null && !TextUtils.isEmpty(mPostContent)) {
                listener.sendComment(mPostContent);
            }
            dialog.dismiss();
        });
        dialog.setOnDismissListener(dialog1 -> {
            String mPostContent = "";
            mPostContent = etContent.getText().toString().trim();
            EventBusManager.getInstance().post(new EventBean(EventBean.NOTES_REVIEW, mPostContent));
        });

        dialog.show();
        return dialog;
    }

    private static void setEditText(Context context, EditText etContent, TextView tvSendComment) {
        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String tvNickName = etContent.getText().toString().trim();
                if (tvNickName.length() == 0) {
                    tvSendComment.setTextColor(ContextCompat.getColor(context, R.color.color_DADADA));
                } else {
                    tvSendComment.setTextColor(ContextCompat.getColor(context, R.color.color_2673FE));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
     * 首页活动
     */
    public static void showHomeActivityDialog(Context context, List<ListBannerBean> bannerBeanList, OnBannerClick onBannerClick) {
        Dialog activityDialog = new BaseDialog(context, R.style.BaseDialog, R.layout.dialog_home_activity);
        activityDialog.setCancelable(true);
        activityDialog.setCanceledOnTouchOutside(false);
        activityDialog.show();

        ImageView ivClose = activityDialog.findViewById(R.id.iv_activity_close);
        Banner banner = activityDialog.findViewById(R.id.banner_activity);

        ivClose.setOnClickListener(v -> activityDialog.dismiss());

        List<String> images = new ArrayList<>();
        for (int i = 0; i < bannerBeanList.size(); i++) {
            ListBannerBean bannerBean = bannerBeanList.get(i);
            images.add(bannerBean.getCoverUrl());
        }
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                .setImageLoader(new GlideImageLoader(), 1)
                .setImages(images, "")
                .isAutoPlay(true)
                .setDelayTime(5000)
                .setIndicatorGravity(BannerConfig.CENTER)
                .setOffscreenPageLimit(images.size())
                .setOnBannerListener(position -> {
                    ListBannerBean bannerBean = bannerBeanList.get(position);
                    if (bannerBean != null && onBannerClick != null) {
                        //是否有跳转：1.无 2.有 3.课程
                        activityDialog.dismiss();
                        onBannerClick.clickBanner(bannerBean);
                    }
                })
                .start();
    }

    /**
     * 绑定银行卡提示
     */
    public static void showBindingBankCardDialog(Context context, OnCancelSureClick onCancelSureClick) {
        showCancelSureDialog(context, "提示", "您还未绑定银行卡", "取消", "去绑定", onCancelSureClick);
    }

    /**
     * 绑定银行卡提示
     */
    public static void showPublicExcicseDialog(Context context, OnCancelSureClick onCancelSureClick) {
        showCancelSureDialog(context, "提醒", "是否公开展示作业,公开展示的作业将对该课程的学员可见", "取消", "公开展示", onCancelSureClick);
    }

    /**
     * 兑换礼券使用说明
     *
     * @param context
     */
    public static void showExchangeVoucheDialog(Context context, String useExplain, boolean isToUse, final OnClickDialogListener listener) {
        final Dialog dialog = new Dialog(context, R.style.custom_dialog);
        View mView = LayoutInflater.from(context).inflate(R.layout.dialog_exchange_vouche, null);
        TextView tvContent = mView.findViewById(R.id.tv_exchange_vouche_content);
        tvContent.setText(useExplain);
        dialog.setContentView(mView);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager windowManager = ((Activity) context).getWindowManager();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        Display display = windowManager.getDefaultDisplay();
        lp.width = (int) (display.getWidth() * 0.8);
        dialogWindow.setAttributes(lp);

        TextView mTvToUse = mView.findViewById(R.id.tv_to_use);
        mTvToUse.setText(isToUse ? "知道了" : "知道了");
        mTvToUse.setOnClickListener(v -> {
            if (listener != null) {
                listener.clickOk();
            }
            dialog.dismiss();
        });
        dialog.show();
    }

    /**
     * 训练营 - 保存二维码弹框
     */
    public static void showSaveQrCode(Context context, String title, String desc, String qrCodeImg, OnClickDialogListener listener) {
        final Dialog dialog = new Dialog(context, R.style.custom_dialog);
        View mView = LayoutInflater.from(context).inflate(R.layout.dialog_training_group, null);
        dialog.setContentView(mView);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = AutoSizeUtils.dp2px(context, 250f);
        dialogWindow.setAttributes(lp);
        dialog.show();
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);

        ImageView ivClose = mView.findViewById(R.id.iv_close_dialog);
        TextView tvTitle = mView.findViewById(R.id.tv_title_dialog);
        TextView tvGroupName = mView.findViewById(R.id.tv_group_name_dialog);
        ImageView ivQrCode = mView.findViewById(R.id.iv_group_qrcode_dialog);
        TextView tvSaveQrCode = mView.findViewById(R.id.tv_save_qrcode);

        tvTitle.setText(desc);
        tvGroupName.setText(title);
        AppImageLoader.loadResUrl(qrCodeImg, ivQrCode);

        ivClose.setOnClickListener(v -> dialog.dismiss());
        tvSaveQrCode.setOnClickListener(v -> {
            if (listener != null) {
                listener.clickOk();
            }
            dialog.dismiss();
        });
    }

    /**
     * 写作业-提交成功提醒
     */
    public static void showExerciseSubmitSuccessDialog(Context context, OnClickDialogListener listener) {
        Dialog submitSuccessDialog = new BaseDialog(context, R.style.BaseDialog, R.layout.dialog_exercise_submit_success);
        submitSuccessDialog.setCancelable(true);
        submitSuccessDialog.setCanceledOnTouchOutside(false);
        submitSuccessDialog.show();

        ImageView ivPushTip = submitSuccessDialog.findViewById(R.id.iv_push_tip);
        ivPushTip.setSelected(false);
        ivPushTip.setImageResource(R.drawable.chose_multiple);
        ivPushTip.setOnClickListener(v -> {
            if (ivPushTip.isSelected()) {
                ivPushTip.setSelected(false);
                ivPushTip.setImageResource(R.drawable.chose_multiple);
            } else {
                ivPushTip.setSelected(true);
                ivPushTip.setImageResource(R.drawable.exercise_submit_success_chose);
            }
        });

        TextView tvSure = submitSuccessDialog.findViewById(R.id.tv_submit_sure);
        tvSure.setOnClickListener(v -> {
            if (listener != null) {
                listener.clickOk();
            }
            submitSuccessDialog.dismiss();
        });
    }


    /**
     * 启富通弹框
     */
    public static void showQiitongDialog(Context context, OnClickDialogListener listener) {
        Dialog submitSuccessDialog = new BaseDialog(context, R.style.BaseDialog, R.layout.dialog_qiitong);
        submitSuccessDialog.setCancelable(true);
        submitSuccessDialog.setCanceledOnTouchOutside(false);
        submitSuccessDialog.show();
        ImageView ivPushTip = submitSuccessDialog.findViewById(R.id.iv_push_tip);
        TextView tvBackingOut = submitSuccessDialog.findViewById(R.id.tv_submit_backing_out);
        ivPushTip.setSelected(true);
        ivPushTip.setImageResource(R.drawable.coupon_qiitong_select);
        ivPushTip.setOnClickListener(v -> {
            if (ivPushTip.isSelected()) {
                ivPushTip.setSelected(false);
                ivPushTip.setImageResource(R.drawable.coupon_qiitong_unselect);
            } else {
                ivPushTip.setSelected(true);
                ivPushTip.setImageResource(R.drawable.coupon_qiitong_select);

            }
        });

        TextView tvSure = submitSuccessDialog.findViewById(R.id.tv_submit_sure);
        tvSure.setOnClickListener(v -> {
            if (listener != null) {
                listener.clickOk(ivPushTip.isSelected());
            }
            submitSuccessDialog.dismiss();
        });

        tvBackingOut.setOnClickListener(v -> {
            if (listener != null) {
                listener.clickCancel(ivPushTip.isSelected());
            }
            submitSuccessDialog.dismiss();
        });
    }

    /**
     * 发布问题选择标签
     *
     * @param context
     * @param beans
     * @param listener
     */
    public static void showViewProblemsDialog(Context context, List<ScreenlBean> beans, OnClickDialogListener listener) {
        Dialog submitSuccessDialog = new BaseDialog(context, R.style.BaseDialog, R.layout.dialog_view_problems);
        Window window = submitSuccessDialog.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.gravity = Gravity.BOTTOM;
        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        attributes.width = (int) (display.getWidth() * 1);
        window.setAttributes(attributes);

        submitSuccessDialog.setCancelable(true);
        submitSuccessDialog.setCanceledOnTouchOutside(true);
        submitSuccessDialog.show();

        TextView tvBackingOut = submitSuccessDialog.findViewById(R.id.tv_dialog_close);
        TextView tvSure = submitSuccessDialog.findViewById(R.id.tv_dialog_close_bottom);
        RecyclerView rvMyQuestions = submitSuccessDialog.findViewById(R.id.rv_text_reply);

        rvMyQuestions.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        StudyScreenPopAdapter popAdapter = new StudyScreenPopAdapter(context, beans, rvMyQuestions);
        String titleText = context.getString(R.string.question_select_interest);
        popAdapter.setSingle(!titleText.contains("多选"));
        rvMyQuestions.setAdapter(popAdapter);

        tvSure.setOnClickListener(v -> {
            if (listener != null) {
                StringBuffer sbSureText = new StringBuffer();
                StringBuffer sbSureId = new StringBuffer();
                if (beans != null) {
                    for (int i = 0; i < beans.size(); i++) {
                        ScreenlBean dataBean = beans.get(i);
                        if (dataBean != null && dataBean.isCheck) {
                            sbSureText.append(dataBean.getRealmName()).append(",");
                            sbSureId.append(dataBean.getId()).append(",");
                        }
                    }
                }
                if (listener != null) {
                    String sureText = sbSureText.toString();
                    String sureId = sbSureId.toString();
                    if (!TextUtils.isEmpty(sureText)) {
                        sureText = sureText.substring(0, sbSureText.length() - 1);
                    }
                    if (!TextUtils.isEmpty(sureId)) {
                        sureId = sureId.substring(0, sureId.length() - 1);
                    }
                    ScreenMapUtils.getInstance().mScreenMap.put(ScreenMapUtils.TASK_RECORD, sureText);
                    listener.sendProblemsComment(sureText, sureId);
                }

            }
            submitSuccessDialog.dismiss();
        });

        tvBackingOut.setOnClickListener(v -> {
            if (beans != null) {
                for (int i = 0; i < beans.size(); i++) {
                    ScreenlBean dataBean = beans.get(i);
                    dataBean.setCheck(false);
                }
                popAdapter.setNewData(beans);
            }

        });
    }


    /**
     * 开通专栏浮层
     *
     * @param context
     * @param listener
     */
    public static void showOpenColumnDialog(Context context, OnClickDialogListener listener) {
        Dialog submitSuccessDialog = new BaseDialog(context, R.style.BaseDialog, R.layout.dialog_open_column);
        Window window = submitSuccessDialog.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.gravity = Gravity.TOP;
        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        attributes.width = (int) (display.getWidth() * 1);
        window.setAttributes(attributes);

        submitSuccessDialog.setCancelable(true);
        submitSuccessDialog.setCanceledOnTouchOutside(true);
        submitSuccessDialog.show();

        submitSuccessDialog.findViewById(R.id.iv_i_know).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.clickOk();
                }
                submitSuccessDialog.dismiss();

            }
        });


    }

    public interface OnBannerClick {
        /**
         * 活动banner点击数事件
         */
        void clickBanner(ListBannerBean bannerBean);
    }

    public interface OnClickDialogListener {
        /**
         * 点击取消
         */
        default void clickCancel() {

        }

        /**
         * 点击取消
         */
        default void clickCancel(boolean selected) {

        }

        /**
         * 点击确定
         */
        default void clickOk() {
        }

        /**
         * 点击确定
         */
        default void clickOk(boolean selected) {
        }

        /**
         * 点击发送
         */
        default void sendComment(String comment) {
        }

        default void recharge(String payPrice, int payMethod) {
        }

        default void sendProblemsComment(String comment, String id) {
        }
    }

    public interface OnCancelSureClick {
        default void clickCancel() {

        }

        default void clickSure() {

        }

        default void clickDelete() {

        }

        default void clickSure(String password) {

        }

        /**
         * 点击用户协议
         */
        default void clickAgreement() {

        }

        /**
         * 点击隐私政策协议
         */
        default void clickPrivacyAgreement() {

        }


        /**
         * 生成分享图片
         */
        default void generatingImages(ConstraintLayout view) {
        }

    }

}
