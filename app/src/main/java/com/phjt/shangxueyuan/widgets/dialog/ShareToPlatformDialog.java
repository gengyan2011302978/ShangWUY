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
import com.phjt.shangxueyuan.interf.IpopWindowClickCallBack;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: gengyan
 * date:    2020/3/30
 * company: 普华集团
 * description: 分享的dialog
 */
public class ShareToPlatformDialog extends Dialog {

    private Window window;
    private Context mContext;
    private IpopWindowClickCallBack mListener;

    public ShareToPlatformDialog(@NonNull Context context, IpopWindowClickCallBack mcallBack) {
        super(context, R.style.custom_dialog);
        this.mListener = mcallBack;
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_share_wechat);
        ButterKnife.bind(this);

        window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.gravity = Gravity.BOTTOM;
        WindowManager windowManager = ((Activity) mContext).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = (int) (display.getWidth() * 1);
        getWindow().setAttributes(lp);
    }

    @OnClick({R.id.iv_wechat_friend, R.id.iv_wechat_circle, R.id.tv_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_wechat_friend:
                if (mListener != null) {
                    mListener.popWindowCallBack(0, "微信");
                }
                dismiss();
                break;
            case R.id.iv_wechat_circle:
                if (mListener != null) {
                    mListener.popWindowCallBack(1, "朋友圈");
                }
                dismiss();
                break;
            case R.id.tv_sure:
                dismiss();
                break;
            default:
                break;
        }
    }
}
