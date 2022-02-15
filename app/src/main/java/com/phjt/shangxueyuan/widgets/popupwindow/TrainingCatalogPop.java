package com.phjt.shangxueyuan.widgets.popupwindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.TrainingCatalogFirstBean;
import com.phjt.shangxueyuan.bean.TrainingCatalogSecondBean;
import com.phjt.shangxueyuan.bean.TrainingDetailBean;
import com.phjt.shangxueyuan.mvp.ui.adapter.TrainingCatalogAdapter;
import com.phjt.shangxueyuan.utils.TrainingUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.jessyan.autosize.utils.AutoSizeUtils;

/**
 * @author: gengyan
 * date:    2021/1/21 10:26
 * company: 普华集团
 * description: 训练营目录
 */
public class TrainingCatalogPop extends PopupWindow {

    @BindView(R.id.rv_catalog_pop)
    RecyclerView mRvCatalog;

    private Context mContext;
    private TrainingCatalogAdapter catalogAdapter;
    /**
     * 训练营实体
     */
    private TrainingDetailBean mDetailBean;

    public TrainingCatalogPop(Context context) {
        super(context);
        this.mContext = context;

        View rootView = LayoutInflater.from(mContext).inflate(R.layout.pop_training_catalog, null);
        ButterKnife.bind(this, rootView);
        this.setContentView(rootView);
        // 设置PopupWindow弹出窗体的宽/高
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(AutoSizeUtils.dp2px(mContext, 595f));
        // 设置PopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 设置PopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.ActionSheetDialogAnimation);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(ContextCompat.getColor(context, R.color.transparent3));
        // 设置PopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRvCatalog.setLayoutManager(layoutManager);
        catalogAdapter = new TrainingCatalogAdapter(mContext, null);
        mRvCatalog.setAdapter(catalogAdapter);

        catalogAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                int itemViewType = adapter.getItemViewType(position);
                if (itemViewType == TrainingCatalogAdapter.TYPE_LEVEL_0) {
                    TrainingCatalogFirstBean firstBean = (TrainingCatalogFirstBean) adapter.getData().get(position);
                    if (firstBean.isExpanded()) {
                        adapter.collapse(position, false);
                    } else {
                        adapter.expand(position, false);
                    }
                } else {
                    TrainingCatalogSecondBean secondBean = (TrainingCatalogSecondBean) adapter.getData().get(position);
                    TrainingUtils.itemClick(mContext, mDetailBean, secondBean, true);
                    dismiss();
                }
            }
        });
    }

    public void showCatalogList(TrainingDetailBean detailBean) {
        this.mDetailBean = detailBean;
        ArrayList<MultiItemEntity> catalogList = new ArrayList<>();
        if (detailBean.getNodeList() != null) {
            List<TrainingCatalogFirstBean> catalogFirstBeans = detailBean.getNodeList();
            for (int i = 0; i < catalogFirstBeans.size(); i++) {
                TrainingCatalogFirstBean catalogFirstBean = catalogFirstBeans.get(i);
                if (catalogFirstBean != null && catalogFirstBean.getTaskList() != null) {
                    catalogFirstBean.setSubItems(null);
                    catalogFirstBean.setExpanded(false);
                    List<TrainingCatalogSecondBean> catalogSecondBeans = catalogFirstBean.getTaskList();
                    for (int j = 0; j < catalogSecondBeans.size(); j++) {
                        TrainingCatalogSecondBean catalogSecondBean = catalogSecondBeans.get(j);
                        catalogFirstBean.addSubItem(catalogSecondBean);
                    }
                }
                catalogList.add(catalogFirstBean);
            }
        }
        catalogAdapter.setBuyAndPatternStatus(detailBean.isBuy(), detailBean.getUnlockPatternStatus(), detailBean.getUnLockDate());
        catalogAdapter.setNewData(catalogList);
        catalogAdapter.expandAll();
    }

    @OnClick(R.id.iv_close_pop)
    public void onClick(View view) {
        dismiss();
    }
}
