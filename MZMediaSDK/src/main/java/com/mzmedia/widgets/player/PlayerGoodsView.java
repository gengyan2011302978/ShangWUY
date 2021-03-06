package com.mzmedia.widgets.player;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mengzhu.live.sdk.business.dto.MZGoodsListDto;
import com.mengzhu.sdk.R;
import com.mzmedia.utils.String_Utils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.Timer;
import java.util.TimerTask;

public class PlayerGoodsView extends LinearLayout {
    private ImageView item_player_goods_avatar_iv;
    private TextView item_player_goods_name_tv;
    private TextView item_player_goods_price_tv;
    private LinearLayout item_player_goods_layout;
    private Timer mTimer;
    private ObjectAnimator mAnimator;
    private boolean isEndAnimation = false;
    private AnimatorSet animatorSetsuofang;
    private MZGoodsListDto mzGoodsListDto;
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            animatorSetsuofang = new AnimatorSet();//组合动画
            item_player_goods_layout.setPivotX(0);
            item_player_goods_layout.setPivotY(PlayerGoodsView.this.getHeight());
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(item_player_goods_layout, "scaleX", 1f, 0);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(item_player_goods_layout, "scaleY", 1f, 0);

            animatorSetsuofang.addListener(new AlphaAnimatorListener());
            animatorSetsuofang.setDuration(800);
            animatorSetsuofang.setInterpolator(new DecelerateInterpolator());
            animatorSetsuofang.play(scaleX).with(scaleY);//两个动画同时开始
            animatorSetsuofang.start();
//            isEndAnimation = true;
        }
    };

    public PlayerGoodsView(Context context) {
        super(context);
        initView(context);
    }

    public PlayerGoodsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        View.inflate(context, R.layout.item_player_goods_layout, this);
        item_player_goods_layout = findViewById(R.id.item_player_goods_layout);
        item_player_goods_avatar_iv = findViewById(R.id.item_player_goods_avatar_iv);
        item_player_goods_name_tv = findViewById(R.id.item_player_goods_name_tv);
        item_player_goods_price_tv = findViewById(R.id.item_player_goods_price_tv);
        mTimer = new Timer();
    }

    public void setGoodsData(MZGoodsListDto mzGoodsListDto) {
        item_player_goods_name_tv.setText(mzGoodsListDto.getName());
        item_player_goods_price_tv.setText(String_Utils.linearNmber(mzGoodsListDto.getPrice())+ " 学豆");
        ImageLoader.getInstance().displayImage(mzGoodsListDto.getPic() + String_Utils.getPictureSizeAvatar(), item_player_goods_avatar_iv, new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.icon_goods_default)
                .showImageForEmptyUri(R.mipmap.icon_goods_default)
                .showImageOnFail(R.mipmap.icon_goods_default)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .displayer(new RoundedBitmapDisplayer(0))
                .build());
    }

    class AnimatorTask extends TimerTask {
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
        }
    }

    private void startTimeTask() {
        mTimer.cancel();
        mTimer = null;
        mTimer = new Timer();
        mTimer.schedule(new AnimatorTask(), 3000);
    }

    class OnlineAnimatorListener implements Animator.AnimatorListener {

        @Override
        public void onAnimationStart(Animator animator) {
            item_player_goods_layout.setVisibility(View.VISIBLE);
            isEndAnimation = true;
        }

        @Override
        public void onAnimationEnd(Animator animator) {

        }

        @Override
        public void onAnimationCancel(Animator animator) {

        }

        @Override
        public void onAnimationRepeat(Animator animator) {

        }
    }

    public void startPlayerGoods() {
        item_player_goods_layout.setVisibility(VISIBLE);
        if (!isEndAnimation) {
            animatorSetsuofang = new AnimatorSet();//组合动画
            item_player_goods_layout.setPivotX(0);
            item_player_goods_layout.setPivotY(PlayerGoodsView.this.getHeight()==0?180:PlayerGoodsView.this.getHeight());
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(item_player_goods_layout, "scaleX", 0, 1f);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(item_player_goods_layout, "scaleY", 0, 1f);

            animatorSetsuofang.addListener(new OnlineAnimatorListener());
            animatorSetsuofang.setDuration(800);
            animatorSetsuofang.setInterpolator(new DecelerateInterpolator());
            animatorSetsuofang.play(scaleX).with(scaleY);//两个动画同时开始
            animatorSetsuofang.start();
            isEndAnimation = true;
        }
        startTimeTask();
//        item_player_goods_name_tv.setText();
//        item_player_goods_price_tv.setText();
        item_player_goods_layout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnGoodsItemClickListener != null) {
                    mOnGoodsItemClickListener.onGoodsItemClick();
                }
            }
        });
    }

    class AlphaAnimatorListener implements Animator.AnimatorListener {

        @Override
        public void onAnimationStart(Animator animator) {
        }

        @Override
        public void onAnimationEnd(Animator animator) {
            item_player_goods_layout.setVisibility(View.INVISIBLE);
            isEndAnimation = false;
        }

        @Override
        public void onAnimationCancel(Animator animator) {
        }

        @Override
        public void onAnimationRepeat(Animator animator) {

        }
    }

    private OnGoodsItemClickListener mOnGoodsItemClickListener;

    public interface OnGoodsItemClickListener {
        void onGoodsItemClick();
    }

    public void setOnGoodsItemClickListener(OnGoodsItemClickListener listener) {
        mOnGoodsItemClickListener = listener;
    }
}
