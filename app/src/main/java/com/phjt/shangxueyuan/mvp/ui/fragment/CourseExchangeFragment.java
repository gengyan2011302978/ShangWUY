package com.phjt.shangxueyuan.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.phjt.base.base.BaseFragment;
import com.phjt.base.di.component.AppComponent;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.ExchangeCodeBean;
import com.phjt.shangxueyuan.bean.TrainingBattalionExchangeBean;
import com.phjt.shangxueyuan.bean.event.ExchangeCode;
import com.phjt.shangxueyuan.di.component.DaggerCourseExchangeComponent;
import com.phjt.shangxueyuan.mvp.contract.CourseExchangeContract;
import com.phjt.shangxueyuan.mvp.presenter.CourseExchangePresenter;
import com.phjt.shangxueyuan.mvp.ui.activity.CourseDetailActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.TrainingCampDetailActivity;
import com.phjt.shangxueyuan.mvp.ui.adapter.ExchangeCodeAdapter;
import com.phjt.shangxueyuan.mvp.ui.adapter.TrainingBattalionExchangeAdapter;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.phjt.base.utils.Preconditions.checkNotNull;


/**
 * @author: Created by GengYan
 * date: 08/06/2020 09:48
 * company: 普华集团
 * description: 兑换码
 */
public class CourseExchangeFragment extends BaseFragment<CourseExchangePresenter> implements CourseExchangeContract.View, OnRefreshLoadMoreListener {

    @BindView(R.id.rv_download_video)
    RecyclerView rvDownloadVideo;
    ExchangeCodeAdapter exchangeCodeAdapter;
    @BindView(R.id.srf_study)
    SmartRefreshLayout srfStudy;
    @BindView(R.id.relat)
    RelativeLayout relat;
    private int pageNo = 1;
    List<ExchangeCodeBean.RecordsBean> list = new ArrayList<>();
    /**
     * 1:课程 ;3:训练营
     */
    private int mType;
    private View mEmptyView;
    private TrainingBattalionExchangeAdapter trainingAdapter;


    public static CourseExchangeFragment newInstance(int type) {
        Bundle args = new Bundle();
        CourseExchangeFragment fragment = new CourseExchangeFragment();
        args.putInt(Constant.FRAGMENT_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerCourseExchangeComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_course_exchange, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mType = bundle.getInt(Constant.FRAGMENT_TYPE);
        }
        mEmptyView = LayoutInflater.from(getActivity()).inflate(R.layout.empty_exchange_layout, null);
        TextView tvNodata = mEmptyView.findViewById(R.id.tv_nodata);
        if (mType == 1) {
            initExchangeCodeAdapter();
            tvNodata.setText("您还没有兑换课程～");
        } else {
            initTrainingAdapter();
            tvNodata.setText("您还没有兑换训练营~");
        }
        mEmptyView.setVisibility(View.GONE);
        requestData(true);
    }

    /**
     * 初始化训练营
     */
    private void initTrainingAdapter() {
        trainingAdapter = new TrainingBattalionExchangeAdapter();
        rvDownloadVideo.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rvDownloadVideo.setAdapter(trainingAdapter);
        srfStudy.setOnRefreshLoadMoreListener(this);
        trainingAdapter.setEmptyView(mEmptyView);
        trainingAdapter.setOnItemClickListener((adapter, view, position) -> {
            List<TrainingBattalionExchangeBean> list = adapter.getData();
            if (list.get(position).getEffectiveState() == 0) {
                TipsUtil.show("该兑换的训练营已失效");
                return;
            }
            if (list.get(position).getUpState() != 1) {
                TipsUtil.show("该训练营已下架");
                return;
            }
            if (!TextUtils.isEmpty(list.get(position).getTrainingCampId()) && !"0".equals(list.get(position).getTrainingCampId())) {
                Intent intent = new Intent(getActivity(), TrainingCampDetailActivity.class);
                intent.putExtra(Constant.BUNDLE_TRAINING_CAMP_ID, list.get(position).getTrainingCampId());
                startActivity(intent);
            } else {
                TipsUtil.show("该训练营已下架");
            }
        });
    }

    /**
     * 初始化课程
     */
    private void initExchangeCodeAdapter() {
        exchangeCodeAdapter = new ExchangeCodeAdapter(list);
        rvDownloadVideo.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rvDownloadVideo.setAdapter(exchangeCodeAdapter);
        srfStudy.setOnRefreshLoadMoreListener(this);
        exchangeCodeAdapter.setEmptyView(mEmptyView);
        exchangeCodeAdapter.setOnItemClickListener((adapter, view, position) -> {
            List<ExchangeCodeBean.RecordsBean> list = adapter.getData();
            if (list.get(position).getUpState().equals("1") && list.get(position).getEffectiveState().equals("1")) {
                Intent intent = new Intent(getActivity(), CourseDetailActivity.class);
                intent.putExtra(Constant.BUNDLE_COURSE_ID, list.get(position).getCourseId());
                startActivity(intent);
            } else if (list.get(position).getEffectiveState().equals("0")) {
                TipsUtil.show("该兑换的课程已失效");
            } else {
                TipsUtil.show("该课程已下架");
            }
        });
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        if (srfStudy != null) {
            srfStudy.finishRefresh();
            srfStudy.finishLoadMore();
        }
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
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

    public void requestData(boolean isRefresh) {
        if (mType == 1 && mPresenter != null) {
            mPresenter.getRefreshList(pageNo, 10, isRefresh);
        } else if (mType == 3 && mPresenter != null) {
            mPresenter.getRecordList(pageNo, 10, isRefresh);
        }

    }

    @Override
    public void loadRefreshDataSuccess(ExchangeCodeBean beans, int pageNo, boolean isRefresh) {
        srfStudy.setEnableLoadMore(pageNo < beans.getTotalPage());
        list = beans.getRecords();
        if (isRefresh) {
            exchangeCodeAdapter.setNewData(new ArrayList<>());
            if (beans != null && beans.getRecords().size() > 0) {
                exchangeCodeAdapter.setNewData(beans.getRecords());
            } else if (beans != null && beans.getRecords().size() == 0) {
                if (mEmptyView != null) {
                    mEmptyView.setVisibility(View.VISIBLE);
                }
            } else {
                srfStudy.setEnableLoadMore(false);
                if (mEmptyView != null) {
                    mEmptyView.setVisibility(View.VISIBLE);
                }
            }
            srfStudy.finishRefresh();
        } else {
            if (beans != null && beans.getRecords().size() > 0) {
                exchangeCodeAdapter.addData(beans.getRecords());
            } else {
                srfStudy.setEnableLoadMore(false);
            }
            srfStudy.finishLoadMore();
        }
    }

    @Override
    public void loadRefreshDataFailure() {
        srfStudy.finishRefresh();
        srfStudy.finishLoadMore();
        if (mEmptyView != null) {
            mEmptyView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showCommentListRefresh(List<TrainingBattalionExchangeBean> commentBeans) {
        if (trainingAdapter != null) {
            trainingAdapter.setNewData(commentBeans);
        }
    }

    @Override
    public void showCommentListLoadMore(List<TrainingBattalionExchangeBean> commentBeans) {
        if (trainingAdapter != null) {
            trainingAdapter.addData(commentBeans);
        }
    }

    @Override
    public void canLoadMore() {
        if (srfStudy != null) {
            srfStudy.setEnableLoadMore(true);
        }
    }

    @Override
    public void cannotLoadMore() {
        if (srfStudy != null) {
            srfStudy.setEnableLoadMore(false);
        }
    }

    @Override
    public void showEmptyView() {
        if (mEmptyView != null) {
            mEmptyView.setVisibility(View.VISIBLE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventBean(ExchangeCode exchangeCode) {
        pageNo = 1;
        requestData(true);
    }

}
