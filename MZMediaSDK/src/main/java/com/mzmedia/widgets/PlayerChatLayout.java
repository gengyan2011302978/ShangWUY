package com.mzmedia.widgets;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.BaseAdapter;

import com.mzmedia.adapter.base.CommonAdapterType;

import tv.mengzhu.core.module.model.dto.BaseItemDto;


/**
 * Created by DELL on 2016/7/8.
 */
public class PlayerChatLayout extends android.widget.LinearLayout {
    private BaseAdapter adapter;
    private OnClickListener onClickListener = null;
    private boolean isConstant = false;

    public void setConstant(boolean isConstant) {
        this.isConstant = isConstant;
    }

    /**
     * <p>控件初始化</p>
     * <p>调用传进来的adapter获取view</p>
     *
     * @author sunxh
     * @since 2.6
     */
    public void fillLinearLayout() {
        int count = adapter.getCount();
        removeAllViews();
        mChildCount = 0;
        View v = null;
        for (int i = 0; i < count; i++) {
            v = adapter.getView(i, null, null);
            v.setOnClickListener(this.onClickListener);
//			onClickListener.onClick(v);
            v.setId(i);
            addView(v, i);
        }
        mChildCount = count;
    }

    private AddViewListener mListener;

    public interface AddViewListener {
        public void onAddView();
    }

    public void setAddViewListener(AddViewListener listener) {
        mListener = listener;
    }

    int mChildCount = 0;

    public void addItemView(BaseItemDto dto) {
        int count = adapter.getCount();
        View v = null;
        v = adapter.getView(mChildCount, null, null);
//        v.setOnClickListener(this.onClickListener);
        v.setId(mChildCount);
        if (!isConstant) {
            startLayoutAnimator(v);
        }
        addView(v, mChildCount);
        mChildCount++;
        mListener.onAddView();
    }

    public void removeChild(int index) {
        View view = getChildAt(index);
        removeView(view);
        mChildCount--;
        ((CommonAdapterType) adapter).remove(index);
    }

    private AnimatorSet mAnimatorSet;

    private void startLayoutAnimator(View view) {
        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.playTogether(ObjectAnimator.ofFloat(view, "alpha", 1f, 0f)
        );
        mAnimatorSet.addListener(new LayoutAnimatorListener(view));
        mAnimatorSet.setDuration(3 * 1000).start();
    }

    private class LayoutAnimatorListener implements Animator.AnimatorListener {
        View mView;

        public LayoutAnimatorListener(View view) {
            mView = view;
        }

        @Override
        public void onAnimationStart(Animator animator) {
        }

        @Override
        public void onAnimationEnd(Animator animator) {
            PlayerChatLayout.this.removeView(mView);
            mChildCount = mChildCount - 1;
            ((CommonAdapterType) adapter).remove(0);
        }

        @Override
        public void onAnimationCancel(Animator animator) {
        }

        @Override
        public void onAnimationRepeat(Animator animator) {
        }
    }

    /**
     * <p>去除更多按钮</p>
     *
     * @author sunxh
     * @since 2.6
     */
    public void removeMore() {
        View view = getChildAt(mChildCount - 1);
        if (getChildAt(mChildCount) != null) {
            removeViewAt(mChildCount);
        }
    }

    public PlayerChatLayout(Context context) {
        super(context);
        setOrientation(VERTICAL);
    }

    public PlayerChatLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
    }

    /**
     * <p>获取adapter方法</p>
     *
     * @return
     * @author sunxh
     * @since 2.6
     */
    public android.widget.BaseAdapter getAdpater() {
        return adapter;
    }

    /**
     * <p>添加adapter</p>
     *
     * @author sunxh
     * @since 2.6
     */
    public void setAdapter(android.widget.BaseAdapter adapter) {
        this.adapter = adapter;
    }

    /**
     * <p>获取监听方法</p>
     * <p>TODO</p>(详细描述)
     *
     * @return
     * @author sunxh
     * @since 2.6
     */
    public OnClickListener getOnclickListner() {
        return onClickListener;
    }

    /**
     * <p>获取监听方法</p>
     * <p>TODO</p>(详细描述)
     *
     * @param onClickListener
     * @author sunxh
     * @since 2.6
     */
    public void setOnclickLinstener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    /**
     * <p>控件刷新方法</p>
     * <p>TODO</p>(详细描述)
     *
     * @return
     * @author sunxh
     * @since 2.6
     */
    public boolean inform() {
        fillLinearLayout();
        return true;
    }
}
