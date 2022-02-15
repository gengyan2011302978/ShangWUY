package com.phjt.shangxueyuan.widgets;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.DisabuseBean;
import com.phjt.shangxueyuan.mvp.ui.adapter.ScreenPopAdapterAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: Roy
 * date:   2021/6/25
 * company: 普华集团
 * description:
 */
public class ScreenPopWindow extends PopupWindow {


    @BindView(R.id.rl_screen_window)
    RecyclerView mRvContentLabel;
    @BindView(R.id.cl_pop_screen)
    ConstraintLayout mClScreen;

    private Context mContext;
    private List<DisabuseBean> mLabelBeanList;
    private View rootView;
    private PopInter mPopInter;
    private ScreenPopAdapterAdapter popContentAdapter;
    private int timeStatus;
    private int mReplyStatus;

    public ScreenPopWindow(Context context, int type, int selectStatus, int replyStatus, PopInter studyPopInter) {
        super(context);
        this.mContext = context;
        this.mPopInter = studyPopInter;
        timeStatus = selectStatus;
        mReplyStatus = replyStatus;
        rootView = LayoutInflater.from(mContext).inflate(R.layout.pop_screen_window, null);
        ButterKnife.bind(this, rootView);
        this.setContentView(rootView);
        mLabelBeanList = new ArrayList<>();
        setTimeData(type);

        // 设置PopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置PopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置PopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 设置PopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.PopupWindowAnimation);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(ContextCompat.getColor(context, R.color.transparent));
        // 设置PopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);

        mRvContentLabel.setLayoutManager(new LinearLayoutManager(context));
        popContentAdapter = new ScreenPopAdapterAdapter(mContext);
        mRvContentLabel.setAdapter(popContentAdapter);
        popContentAdapter.setNewData(mLabelBeanList);

        popContentAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<DisabuseBean> data = adapter.getData();
                int id = data.get(position).getId();
                String name = data.get(position).getName();
                setTitleStatus(position, id, name);
            }
        });


    }

    public void setTitleStatus(int position, int id, String name) {
        List<DisabuseBean> data = popContentAdapter.getData();
        for (int i = 0; i < data.size(); i++) {
            data.get(i).setCheck(false);
        }
        popContentAdapter.notifyDataSetChanged();
        popContentAdapter.getData().get(position).setCheck(true);

        popContentAdapter.notifyItemChanged(position);
        if (mPopInter != null) {
            mPopInter.sure(id, name);
            dismiss();
        }

    }


    private void setTimeData(int cancelType) {
        if (mClScreen != null && cancelType == 1 || cancelType == 2) {
            mClScreen.setBackgroundResource(R.drawable.icon_pop_screen);
        } else if (mClScreen != null && cancelType == 3 || cancelType == 4) {
            mClScreen.setBackgroundResource(R.drawable.icon_pop_con_screen);
        }

        if (mLabelBeanList != null && (cancelType == 1 || cancelType == 3)) {
            DisabuseBean disabuseBean = new DisabuseBean("时间倒序", 1, timeStatus == 1 ? true : false);
            DisabuseBean disabuseBean2 = new DisabuseBean("时间正序", 2, timeStatus == 2 ? true : false);
            mLabelBeanList.add(disabuseBean);
            mLabelBeanList.add(disabuseBean2);
        } else if (mLabelBeanList != null && (cancelType == 2 || cancelType == 4)) {
            DisabuseBean answerBean = new DisabuseBean("已回答", 1, mReplyStatus == 1 ? true : false);
            DisabuseBean answerBean1 = new DisabuseBean("未回答", 0, mReplyStatus == 0 ? true : false);
            DisabuseBean answerBean2 = new DisabuseBean("已忽略", 2, mReplyStatus == 2 ? true : false);
            mLabelBeanList.add(answerBean);
            mLabelBeanList.add(answerBean1);
            mLabelBeanList.add(answerBean2);
        }

    }

    public interface PopInter {
        default void sure(int selectContentId, String name) {

        }
    }
}
