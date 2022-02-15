package com.phjt.shangxueyuan.widgets.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.ScreenLabelBean;
import com.phjt.shangxueyuan.interf.IpopWindowClickCallBack;
import com.phjt.shangxueyuan.mvp.ui.adapter.SexSelectionAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: Roy
 * date:   2020/3/27
 * company: 普华集团
 * description:
 */
public class SexSelectionDialog extends Dialog {

    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_determine)
    TextView tvDetermine;
    @BindView(R.id.rv_selection)
    RecyclerView rvSelection;
    private Window window;
    private Context mContext;
    private List<ScreenLabelBean> mList;


    private int statusId = 0;
    private String mName;
    private IpopWindowClickCallBack mIpopWindowClickCallBack;

    public SexSelectionDialog(Context context, List<ScreenLabelBean> mList, IpopWindowClickCallBack mcallBack) {
        super(context, R.style.custom_dialog);
        this.mContext = context;
        this.mList = mList;
        this.mIpopWindowClickCallBack = mcallBack;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_question_selection);
        ButterKnife.bind(this);
        window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.gravity = Gravity.BOTTOM;
        WindowManager windowManager = ((Activity) mContext).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = (int) (display.getWidth() * 1);
        getWindow().setAttributes(lp);

        rvSelection.setLayoutManager(new LinearLayoutManager(mContext));
        SexSelectionAdapter mAdapter = new SexSelectionAdapter(mContext, mList);
        rvSelection.setAdapter(mAdapter);
        mAdapter.setCallBack((id, name) -> {
            statusId = id;
            mName = name;
        });

    }

    @OnClick({R.id.tv_cancel, R.id.tv_determine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.tv_determine:
                if (0 != statusId) {
                    mIpopWindowClickCallBack.popWindowCallBack(statusId, mName);
                }
                dismiss();
                break;
            default:
                break;
        }
    }
}
