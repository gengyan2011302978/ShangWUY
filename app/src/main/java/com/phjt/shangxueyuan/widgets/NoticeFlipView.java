package com.phjt.shangxueyuan.widgets;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import androidx.core.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.AnnouncementListBean;
import com.phjt.shangxueyuan.mvp.ui.activity.CourseDetailActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.MyWebViewActivity;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.ConstantWeb;
import com.phjt.shangxueyuan.utils.UmengUtil;

import java.util.List;

import static com.phjt.shangxueyuan.utils.UmengUtil.onEventMainPage;

/**
 * @author HiYoung
 * desc: 首页上下滚动公告逻辑,解决MarqueeView中的快速切换数据源重复的问题
 */
public class NoticeFlipView extends ViewFlipper {
    /**
     * 是否显示淡入淡出动画
     */
    private boolean isSetAlphaAnim = true;
    /**
     * 切换间隔时间
     */
    private int interval = 7000;
    /**
     * 动画时间
     */
    private int animDuration = 2000;
    private AnimationSet mInAnimSet;
    private AnimationSet mOutAnimSet;

    public NoticeFlipView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public void stop() {
        removeAllViews();
        stopFlipping();
        if (mInAnimSet != null) {
            mInAnimSet.cancel();
        }
        if (mOutAnimSet != null) {
            mOutAnimSet.cancel();
        }
    }

    private void init(Context context, AttributeSet attrs) {
        @SuppressLint({"CustomViewStyleable", "Recycle"})
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MarqueeViewUp, 0, 0);
        isSetAlphaAnim = ta.getBoolean(R.styleable.MarqueeViewUp_isSetAlphaAnim, isSetAlphaAnim);
        interval = ta.getInteger(R.styleable.MarqueeViewUp_interval, interval);
        animDuration = ta.getInteger(R.styleable.MarqueeViewUp_animDuration, animDuration);
        setFlipInterval(interval);
        //淡入淡出动画
        AlphaAnimation animationIn = new AlphaAnimation(0, 1);
        animationIn.setDuration(animDuration);
        AlphaAnimation animationOut = new AlphaAnimation(1, 0);
        animationOut.setDuration(animDuration);
        //平移动画
        TranslateAnimation translateAnimationIn = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);

        TranslateAnimation translateAnimationOut = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, -1.0f);
        translateAnimationIn.setDuration(animDuration);
        translateAnimationOut.setDuration(animDuration);
        // 动画集-- 进入动画
        mInAnimSet = new AnimationSet(false);
        mInAnimSet.addAnimation(translateAnimationIn);
        // 动画集-- 退出动画
        mOutAnimSet = new AnimationSet(false);
        mOutAnimSet.addAnimation(translateAnimationOut);
        if (isSetAlphaAnim) {
            //设置淡入淡出动画
            mInAnimSet.addAnimation(animationIn);
            mOutAnimSet.addAnimation(animationOut);
        }
        setInAnimation(mInAnimSet);
        setOutAnimation(mOutAnimSet);
    }


    /**
     * 设置循环滚动的View数组
     */
    public void setViews(Activity activity, List<AnnouncementListBean> datas) {
        stop();
        if (datas == null || datas.isEmpty()) {
            return;
        } else {
            removeAllViews();
        }

        for (AnnouncementListBean bean : datas) {
            FrameLayout moreView = (FrameLayout) LayoutInflater.from(activity.getApplicationContext()).inflate(R.layout.layout_custom, null);
            //初始化布局的控件
            TextView tvTitle = moreView.findViewById(R.id.tv_new_title);
            TextView tvNotice = moreView.findViewById(R.id.iv_notice);
            TextView tvGotobuy = moreView.findViewById(R.id.tv_gotobuy);
            if ("1".equals(bean.getType())) {
                tvNotice.setBackgroundResource(R.drawable.vip_gobuy_bg);
                tvGotobuy.setText("VIP开通");
            } else {
                tvNotice.setBackgroundResource(R.drawable.hot_comment_bg);
                tvGotobuy.setText("了解详情");
            }
            // 设置监听
//            tvTitle.setOnClickListener(v -> {
//                //跳转到消息中心，公告列表页
//                Intent intent = new Intent(activity, MessageCenterActivity.class);
//                intent.putExtra(Constant.INFO_IS_NOTICE, true);
//                activity.startActivity(intent);
//            });
            //进行对控件赋值
//            tvTitle.setText(String.format("恭喜@%s成为第%s名预选弟子群学员",bean.getUserName(),bean.getIndex()));
            tvTitle.setText(bean.getTitle());
            tvTitle.setTextColor(ContextCompat.getColor(activity, R.color.color_666666));
            tvGotobuy.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ("1".equals(bean.getType())) {
                        Intent intent = new Intent(activity, MyWebViewActivity.class);
                        intent.putExtra(Constant.BUNDLE_WEB_URL, ConstantWeb.getH5AddressById(ConstantWeb.H5_VIP));
                        intent.putExtra(Constant.BUNDLE_WEB_TITLE, "VIP会员");
                        activity.startActivity(intent);
                        onEventMainPage("购买动态", UmengUtil.EVENT_GMDT_MAIN);
                    } else {
                        Intent intent = new Intent(activity, CourseDetailActivity.class);
                        intent.putExtra(Constant.BUNDLE_COURSE_ID, bean.getOtherId());
                        activity.startActivity(intent);
                    }
                }
            });
            addView(moreView);
        }

        //大于1时，执行动画
        if (datas.size() >= 1) {
            startFlipping();
        }
    }

}

