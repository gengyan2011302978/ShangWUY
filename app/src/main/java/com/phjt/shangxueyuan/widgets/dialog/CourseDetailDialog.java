package com.phjt.shangxueyuan.widgets.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.interf.ICourseDetailDialog;
import com.phjt.shangxueyuan.interf.IpopWindowClickCallBack;
import com.umeng.commonsdk.debug.I;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: gengyan
 * date:    2020/3/30
 * company: 普华集团
 * description: 课程详情底部的dialog
 */
public class CourseDetailDialog extends BaseCourseDialog {

    @BindView(R.id.iv_collection)
    ImageView mIvCollection;
    @BindView(R.id.tv_collection)
    TextView mTvCollection;

    /**
     * 课程是否收藏
     */
    private boolean isCollection;

    public CourseDetailDialog(@NonNull Context context, boolean isCollection) {
        super(context, R.style.custom_dialog);
        this.isCollection = isCollection;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_course_detail);
        ButterKnife.bind(this);

        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.gravity = Gravity.BOTTOM;
        WindowManager windowManager = ((Activity) mContext).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = (int) (display.getWidth() * 1);
        getWindow().setAttributes(lp);

        mIvCollection.setSelected(isCollection);
        mTvCollection.setSelected(isCollection);
    }

    @OnClick({R.id.iv_collection, R.id.iv_multiple, R.id.iv_download, R.id.iv_wechat,
            R.id.iv_wechat_circle, R.id.tv_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_collection:
                if (mListener != null) {
                    mListener.collectionCallBack();
                }
                dismiss();
                break;
            case R.id.iv_multiple:
                if (mListener != null) {
                    mListener.multipleCallBack();
                }
                dismiss();
                break;
            case R.id.iv_download:
                if (mListener != null) {
                    mListener.downloadCallBack();
                }
                dismiss();
                break;
            case R.id.iv_wechat:
                if (mListener != null) {
                    mListener.weChatCallBack(0, "微信");
                }
                dismiss();
                break;
            case R.id.iv_wechat_circle:
                if (mListener != null) {
                    mListener.weChatCallBack(1, "朋友圈");
                }
                dismiss();
                break;
            case R.id.tv_cancel:
                dismiss();
                break;
            default:
                break;
        }
    }
}
