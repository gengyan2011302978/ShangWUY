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
import com.phjt.shangxueyuan.di.component.DaggerTopicTimeComponent;
import com.phjt.shangxueyuan.mvp.contract.TopicTimeContract;
import com.phjt.shangxueyuan.mvp.presenter.TopicTimePresenter;
import com.phjt.shangxueyuan.mvp.ui.activity.TopicInfoActivity;
import com.phjt.shangxueyuan.mvp.ui.adapter.TopicListTimeAdapter;
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
 * date: 10/28/2020 16:43
 * company: 普华集团
 * description: 模版请保持更新
 */
public class TopicTimeFragment extends BaseFragment<TopicTimePresenter> implements TopicTimeContract.View, OnRefreshLoadMoreListener {


    @BindView(R.id.recycle_topic_time)
    RecyclerView recycleTopicTime;
    @BindView(R.id.srf_study)
    SmartRefreshLayout srfStudy;

    private int pageNo = 1;
    TopicListTimeAdapter topicListTimeAdapter;
    List<TopicListBean.RecordsBean> list = new ArrayList<>();
    private int localType;
    private View mEmptyView;

    public static TopicTimeFragment newInstance(int loacal) {
        TopicTimeFragment fragment = new TopicTimeFragment();
        Bundle args = new Bundle();
        args.putInt("local", loacal);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerTopicTimeComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_topic_time, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            localType = bundle.getInt("local");
        }
        topicListTimeAdapter = new TopicListTimeAdapter(list);
        recycleTopicTime.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        recycleTopicTime.setAdapter(topicListTimeAdapter);
        srfStudy.setOnRefreshLoadMoreListener(this);

        mEmptyView = LayoutInflater.from(getActivity()).inflate(R.layout.empty_layout, null);
        topicListTimeAdapter.setEmptyView(mEmptyView);
        mEmptyView.setVisibility(View.GONE);
        requestData(true);
        topicListTimeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
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
                    intent.putExtra("topicId", recordsBean.getId() + "");
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
            topicListTimeAdapter.setNewData(new ArrayList<>());
            if (beans != null && beans.getRecords().size() > 0) {
                topicListTimeAdapter.setNewData(beans.getRecords());
            } else if (beans != null && beans.getRecords().size() == 0) {
                if (topicListTimeAdapter != null && mEmptyView != null) {
                    mEmptyView.setVisibility(View.VISIBLE);
                }
            } else {
                srfStudy.setEnableLoadMore(false);
                if (topicListTimeAdapter != null && mEmptyView != null) {
                    mEmptyView.setVisibility(View.VISIBLE);
                }
            }
            srfStudy.finishRefresh();
        } else {
            if (beans != null && beans.getRecords().size() > 0) {
                topicListTimeAdapter.addData(beans.getRecords());
            } else {
                srfStudy.setEnableLoadMore(false);
            }
            srfStudy.finishLoadMore();
        }
    }

    @Override
    public void loadRefreshDataFailure() {
        srfStudy.finishRefresh();
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
