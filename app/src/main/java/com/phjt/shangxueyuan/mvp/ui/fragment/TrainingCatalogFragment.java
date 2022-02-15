package com.phjt.shangxueyuan.mvp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.phjt.base.base.BaseFragment;
import com.phjt.base.di.component.AppComponent;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.TrainingCatalogFirstBean;
import com.phjt.shangxueyuan.bean.TrainingCatalogSecondBean;
import com.phjt.shangxueyuan.bean.TrainingDetailBean;
import com.phjt.shangxueyuan.di.component.DaggerTrainingCatalogComponent;
import com.phjt.shangxueyuan.mvp.contract.TrainingCatalogContract;
import com.phjt.shangxueyuan.mvp.presenter.TrainingCatalogPresenter;
import com.phjt.shangxueyuan.mvp.ui.adapter.TrainingCatalogAdapter;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.shangxueyuan.utils.TrainingUtils;
import com.phsxy.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

import static com.phjt.base.utils.Preconditions.checkNotNull;


/**
 * @author: Created by GengYan
 * date: 01/19/2021 16:26
 * company: 普华集团
 * description: 训练营-目录
 */
public class TrainingCatalogFragment extends BaseFragment<TrainingCatalogPresenter> implements TrainingCatalogContract.View {

    @BindView(R.id.rv_training_catalog)
    RecyclerView mRvCatalog;

    /**
     * 训练营实体
     */
    private TrainingDetailBean mDetailBean;

    private TrainingCatalogAdapter catalogAdapter;

    public static TrainingCatalogFragment newInstance() {
        Bundle args = new Bundle();
        TrainingCatalogFragment fragment = new TrainingCatalogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerTrainingCatalogComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_training_catalog, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
    }

    public void showCatalogData(TrainingDetailBean detailBean) {
        this.mDetailBean = detailBean;

        int lastWatchIndex = 0;
        ArrayList<MultiItemEntity> catalogList = new ArrayList<>();
        if (mDetailBean != null && mDetailBean.getNodeList() != null) {
            List<TrainingCatalogFirstBean> catalogFirstBeans = mDetailBean.getNodeList();
            for (int i = 0; i < catalogFirstBeans.size(); i++) {
                TrainingCatalogFirstBean catalogFirstBean = catalogFirstBeans.get(i);
                if (catalogFirstBean != null && catalogFirstBean.getTaskList() != null) {
                    if (catalogFirstBean.getLastWatch() == 1) {
                        lastWatchIndex = i;
                        LogUtils.e("====================lastWatchIndex::" + lastWatchIndex);
                    }
                    List<TrainingCatalogSecondBean> catalogSecondBeans = catalogFirstBean.getTaskList();
                    for (int j = 0; j < catalogSecondBeans.size(); j++) {
                        TrainingCatalogSecondBean catalogSecondBean = catalogSecondBeans.get(j);
                        catalogFirstBean.addSubItem(catalogSecondBean);
                    }
                }
                catalogList.add(catalogFirstBean);
            }
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRvCatalog.setLayoutManager(layoutManager);
        catalogAdapter = new TrainingCatalogAdapter(mContext, catalogList);
        catalogAdapter.setBuyAndPatternStatus(detailBean.isBuy(), detailBean.getUnlockPatternStatus(), detailBean.getUnLockDate());
        mRvCatalog.setAdapter(catalogAdapter);
        catalogAdapter.expand(lastWatchIndex, false);

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
                    TrainingUtils.itemClick(mContext, mDetailBean, secondBean, false);
                }
            }
        });
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        TipsUtil.show(message);
    }
}
