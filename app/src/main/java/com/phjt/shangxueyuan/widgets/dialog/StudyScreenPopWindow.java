package com.phjt.shangxueyuan.widgets.dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.phjt.base.integration.EventBusManager;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.ScreenlBean;
import com.phjt.shangxueyuan.bean.event.EventBean;
import com.phjt.shangxueyuan.mvp.ui.adapter.StudyScreenPopAdapter;
import com.phjt.shangxueyuan.utils.ScreenMapUtils;
import com.phjt.view.roundView.RoundTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: Roy
 * date:   2021/6/18
 * company: 普华集团
 * description:
 */
public class StudyScreenPopWindow extends PopupWindow {

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.rv_study_label)
    RecyclerView mRvStudyLabel;
    @BindView(R.id.tv_clear)
    RoundTextView mTvClear;
    @BindView(R.id.tv_sure)
    RoundTextView mTvSure;
    @BindView(R.id.view_bottom)
    View mViewBottom;

    private Context mContext;
    private List<ScreenlBean> mLabelBeanList;
    private View rootView;
    private StudyPopInter mPopInter;
    private StudyScreenPopAdapter popAdapter;
    private String mMapkey;

    public StudyScreenPopWindow(Context context, List<ScreenlBean> beans,
                                String mapKey, StudyPopInter studyPopInter) {
        super(context);

        this.mContext = context;
        this.mLabelBeanList = beans;
        this.mPopInter = studyPopInter;
        this.mMapkey = mapKey;

        rootView = LayoutInflater.from(mContext).inflate(R.layout.pop_study_screen, null);
        ButterKnife.bind(this, rootView);
        this.setContentView(rootView);

        // 设置PopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置PopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置PopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 设置PopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.PopupWindowAnimation);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(ContextCompat.getColor(context, R.color.transparent3));
        // 设置PopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);

        String mapIds = ScreenMapUtils.getInstance().mScreenMap.get(mapKey);
        for (int i = 0; i < mLabelBeanList.size(); i++) {
            ScreenlBean dataBean = mLabelBeanList.get(i);
            String id = String.valueOf(dataBean.getId());
            if (!TextUtils.isEmpty(mapIds) && mapIds.contains(id)) {
                dataBean.setCheck(true);
            }
        }
        mRvStudyLabel.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        popAdapter = new StudyScreenPopAdapter(mContext, mLabelBeanList, mRvStudyLabel);
        mRvStudyLabel.setAdapter(popAdapter);
    }


    public void setTitle(String title) {
        mTvTitle.setText(title);
        //设置RecycleView是否是单选
        String titleText = mTvTitle.getText().toString();
        popAdapter.setSingle(!titleText.contains("多选"));

    }

    @OnClick({R.id.tv_clear, R.id.tv_sure, R.id.view_bottom})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_clear:
                if (mLabelBeanList != null) {
                    for (int i = 0; i < mLabelBeanList.size(); i++) {
                        ScreenlBean dataBean = mLabelBeanList.get(i);
                        dataBean.setCheck(false);
                    }
                    popAdapter.setNewData(mLabelBeanList);
                }
                if (mPopInter != null) {
                    mPopInter.clear();
                }
                EventBusManager.getInstance().post(new EventBean(EventBean.SCREENING_STATUS, "0"));
                break;
            case R.id.tv_sure:
                doConfirm();
                break;
            case R.id.view_bottom:
                dismiss();
                break;
            default:
                break;
        }
    }

    private void doConfirm() {
        String realmId= "";
        StringBuffer sbSureText = new StringBuffer();
        if (mLabelBeanList != null) {
            for (int i = 0; i < mLabelBeanList.size(); i++) {
                ScreenlBean dataBean = mLabelBeanList.get(i);
                if (dataBean != null && dataBean.isCheck) {
                    sbSureText.append(dataBean.getId()).append(",");
                }
            }
        }
        if (mPopInter != null) {
            String sureText = sbSureText.toString();
            if (!TextUtils.isEmpty(sureText)) {
                sureText = sureText.substring(0, sbSureText.length() - 1);
            }
            ScreenMapUtils.getInstance().mScreenMap.put(mMapkey, sureText);
            realmId= sureText;
        }
        mPopInter.sure(realmId);
        dismiss();
    }

    public interface StudyPopInter {

        default void clear() {

        }

        default void sure(String labelList) {

        }
    }
}
