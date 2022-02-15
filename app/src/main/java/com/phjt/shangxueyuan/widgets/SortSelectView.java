package com.phjt.shangxueyuan.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.phjt.shangxueyuan.R;

/**
 * @author: Roy
 * date:   2021/6/18
 * company: 普华集团
 * description:
 */
public class SortSelectView extends FrameLayout implements View.OnClickListener {

    private ImageView ivTime;
    private ImageView ivLike;
    private OnSelectClickListener listener;

    private int timeState;
    private int likeState;
    private int timeDefaultState;
    private int likeDefaultState;

    private int timeUpClickId;
    private int timeDownClickId;
    private int likeUpClickId;
    private int likeDownClickId;

    /**
     * 按钮空状态
     */
    public static final int NULL = 0;
    /**
     * 按钮向上
     */
    public static final int UP = 1;
    /**
     * 按钮向下
     */
    public static final int DOWN = 2;
    private String timeName = "";
    private String likeName = "";


    public SortSelectView(Context context) {
        this(context, null);
    }

    public SortSelectView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SortSelectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 获取属性配置
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
                R.styleable.SortSelectView);
        timeUpClickId = mTypedArray.getInt(R.styleable.SortSelectView_timeUpClickId, 1);
        timeDownClickId = mTypedArray.getInt(R.styleable.SortSelectView_timeDownClickId, 2);
        likeUpClickId = mTypedArray.getInt(R.styleable.SortSelectView_likeUpClickId, 1);
        likeDownClickId = mTypedArray.getInt(R.styleable.SortSelectView_likeDownClickId, 2);

        timeDefaultState = mTypedArray.getInt(R.styleable.SortSelectView_timeStyle, DOWN);
        likeDefaultState = mTypedArray.getInt(R.styleable.SortSelectView_likeStyle, NULL);
        int timeNameRes = mTypedArray.getResourceId(R.styleable.SortSelectView_timeName, NULL);
        int likeNameRes = mTypedArray.getResourceId(R.styleable.SortSelectView_likeName, NULL);
        timeName = mTypedArray.getString(R.styleable.SortSelectView_timeName);
        likeName = mTypedArray.getString(R.styleable.SortSelectView_likeName);
        mTypedArray.recycle();

        // 加载view
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.sort_select_view, this, true);
        ivTime = view.findViewById(R.id.iv_time);
        ivLike = view.findViewById(R.id.iv_like);
        TextView tvTime = view.findViewById(R.id.tv_time);
        TextView tvLike = view.findViewById(R.id.tv_like);
        setClick(ivTime, ivLike, tvTime, tvLike);

        timeState = timeDefaultState;
        likeState = likeDefaultState;
        setTimeSelectImg();
        setLikeSelectImg();
        if (timeNameRes == 0) {
            tvTime.setText(timeName);
        } else {
            tvTime.setText("" + timeNameRes);
        }
        if (likeNameRes == 0) {
            tvLike.setText(likeName);
        } else {
            tvLike.setText("" + likeNameRes);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_time:
            case R.id.tv_time:
                if (UP == timeState) {
                    timeState = DOWN;
                } else if (DOWN == timeState) {
                    timeState = UP;
                } else {
                    timeState = timeDefaultState == NULL ? DOWN : timeDefaultState;
                }
                setTimeSelectImg();
                likeState = NULL;

                setLikeSelectImg();
                callbackClickId();
                break;
            case R.id.iv_like:
            case R.id.tv_like:
                if (UP == likeState) {
                    likeState = DOWN;
                } else if (DOWN == likeState) {
                    likeState = UP;
                } else {
                    likeState = likeDefaultState == NULL ? DOWN : likeDefaultState;
                }
                setLikeSelectImg();

                timeState = NULL;
                setTimeSelectImg();
                callbackClickId();
                break;
            default:
                break;
        }
    }

    private void setTimeSelectImg() {
        if (getContext() == null) {
            return;
        }
        if (NULL == timeState) {
            ivTime.setImageDrawable(getContext().getDrawable(R.drawable.triangle_select_default));
        } else if (UP == timeState) {
            ivTime.setImageDrawable(getContext().getDrawable(R.drawable.triangle_select_up));
        } else if (DOWN == timeState) {
            ivTime.setImageDrawable(getContext().getDrawable(R.drawable.triangle_select_down));
        }
    }

    private void setLikeSelectImg() {
        if (getContext() == null) {
            return;
        }
        if (NULL == likeState) {
            ivLike.setImageDrawable(getContext().getDrawable(R.drawable.triangle_select_default));
        } else if (UP == likeState) {
            ivLike.setImageDrawable(getContext().getDrawable(R.drawable.triangle_select_up));
        } else if (DOWN == likeState) {
            ivLike.setImageDrawable(getContext().getDrawable(R.drawable.triangle_select_down));
        }
    }

    private int getTimeClickId() {
        if (UP == timeState) {
            return timeUpClickId;
        } else if (DOWN == timeState) {
            return timeDownClickId;
        }
        return 0;
    }

    private int getLikeClickId() {
        if (UP == likeState) {
            return likeUpClickId;
        } else if (DOWN == likeState) {
            return likeDownClickId;
        }
        return 0;
    }

    private void callbackClickId() {
        if (listener != null) {
            listener.sortClick(getTimeClickId(), getLikeClickId());
        }
    }

    private void setClick(View... views) {
        for (View view : views) {
            view.setOnClickListener(this);
        }
    }

    public void setOnSelectClickListener(OnSelectClickListener listener) {
        this.listener = listener;
    }

    public interface OnSelectClickListener {
        /**
         * 按钮点击回调
         *
         * @param timeId   筛选项1选中ID
         * @param statusId 筛选项2选中ID
         */
        void sortClick(int timeId, int statusId);
    }
}
