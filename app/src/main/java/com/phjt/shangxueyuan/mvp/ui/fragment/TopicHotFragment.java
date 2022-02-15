package com.phjt.shangxueyuan.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.phjt.base.base.BaseFragment;
import com.phjt.base.di.component.AppComponent;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.TopicListBean;
import com.phjt.shangxueyuan.di.component.DaggerTopicHotComponent;
import com.phjt.shangxueyuan.mvp.contract.TopicHotContract;
import com.phjt.shangxueyuan.mvp.presenter.TopicHotPresenter;
import com.phjt.shangxueyuan.mvp.ui.activity.TopicInfoActivity;
import com.phjt.shangxueyuan.mvp.ui.adapter.TopicListHotAdapter;
import com.phjt.shangxueyuan.utils.Constant;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.phjt.base.utils.Preconditions.checkNotNull;


/**
 * @author: Created by GengYan
 * date: 10/28/2020 16:42
 * company: 普华集团
 * description: 模版请保持更新
 */
public class TopicHotFragment extends BaseFragment<TopicHotPresenter> implements TopicHotContract.View, OnRefreshLoadMoreListener {


    @BindView(R.id.recycle_topic_hot)
    RecyclerView recycleTopicHot;
    @BindView(R.id.srf_study)
    SmartRefreshLayout srfStudy;

    private int pageNo = 1;
    TopicListHotAdapter topicListHotAdapter;
    List<TopicListBean.RecordsBean> list = new ArrayList<>();
    int localType = 1;
    private View mEmptyView;

    public static TopicHotFragment newInstance(int loacal) {
        TopicHotFragment fragment = new TopicHotFragment();
        Bundle args = new Bundle();
        args.putInt("local", loacal);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerTopicHotComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_topic_hot, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            localType = bundle.getInt("local");
        }
        topicListHotAdapter = new TopicListHotAdapter(list);
        recycleTopicHot.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        recycleTopicHot.setAdapter(topicListHotAdapter);
        mEmptyView = LayoutInflater.from(getActivity()).inflate(R.layout.empty_layout, null);
        topicListHotAdapter.setEmptyView(mEmptyView);
        mEmptyView.setVisibility(View.GONE);

        srfStudy.setOnRefreshLoadMoreListener(this);
        requestData(true);
        topicListHotAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                TopicListBean.RecordsBean recordsBean = (TopicListBean.RecordsBean) adapter.getData().get(position);
                if (localType == 1) {
                    Intent i = new Intent();
                    i.putExtra(Constant.PUSH_TOPICID, recordsBean.getId() + "");
                    i.putExtra(Constant.PUSH_TOPICNAME, recordsBean.getTopicName());
                    getActivity().setResult(9, i);
                    getActivity().finish();
                } else {
                    Intent intent = new Intent(getActivity(), TopicInfoActivity.class);
                    intent.putExtra(Constant.BUNDLE_TOPIC_ID, recordsBean.getId() + "");
                    startActivity(intent);
                }
            }
        });
    }

    public void requestData(boolean isRefresh) {
        if (mPresenter != null) {
            mPresenter.getRefreshList(pageNo, 10, isRefresh);
        }

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
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadRefreshDataSuccess(TopicListBean beans, int pageNo, boolean isRefresh) {
        srfStudy.setEnableLoadMore(pageNo < beans.getTotalPage());
        list = beans.getRecords();
        if (isRefresh) {
            topicListHotAdapter.setNewData(new ArrayList<>());
            if (beans != null && beans.getRecords().size() > 0) {
                topicListHotAdapter.setNewData(beans.getRecords());
            } else if (beans != null && beans.getRecords().size() == 0) {
                if (topicListHotAdapter != null && mEmptyView != null) {
                    mEmptyView.setVisibility(View.VISIBLE);
                }
            } else {
                srfStudy.setEnableLoadMore(false);
                if (topicListHotAdapter != null && mEmptyView != null) {
                    mEmptyView.setVisibility(View.VISIBLE);
                }
            }
            srfStudy.finishRefresh();
        } else {
            if (beans != null && beans.getRecords().size() > 0) {
                topicListHotAdapter.addData(beans.getRecords());
            } else {
                srfStudy.setEnableLoadMore(false);
            }
            srfStudy.finishLoadMore();
        }
    }

    @Override
    public void loadRefreshDataFailure() {
        srfStudy.finishLoadMore();
        srfStudy.finishRefresh();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNo += 1;
        requestData(false);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNo = 1;
        requestData(true);
    }
}
