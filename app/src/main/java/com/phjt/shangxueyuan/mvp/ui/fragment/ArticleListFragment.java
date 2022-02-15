package com.phjt.shangxueyuan.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.phjt.base.base.BaseLazyLoadFragment;
import com.phjt.base.di.component.AppComponent;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.ArticleListBean;
import com.phjt.shangxueyuan.di.component.DaggerArticleListComponent;
import com.phjt.shangxueyuan.mvp.contract.ArticleListContract;
import com.phjt.shangxueyuan.mvp.presenter.ArticleListPresenter;
import com.phjt.shangxueyuan.mvp.ui.activity.MyWebViewActivity;
import com.phjt.shangxueyuan.mvp.ui.adapter.ArticInformationAdapter;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.ConstantWeb;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;

import static com.phjt.base.utils.Preconditions.checkNotNull;


/**
 * @author: Created by Template
 * date: 05/07/2020 17:42
 * company: 普华集团
 * description: 模版请保持更新
 */
public class ArticleListFragment extends BaseLazyLoadFragment<ArticleListPresenter> implements ArticleListContract.View, OnRefreshLoadMoreListener {

    public static final String COURSE_TYPE_ID = "course_type_id";
    @BindView(R.id.recycle_articlelist)
    RecyclerView recycleArticlelist;
    ArticInformationAdapter adapter;
    private String typeId;
    private int currentPage;
    public  final int PAGE_SIZE = 10;
    @BindView(R.id.srl_audition)
    SmartRefreshLayout mSrlAudition;
    private boolean isFirstLoad;
    List<ArticleListBean.RecordsBean> beanList;

    /**
     * 空页面
     */
    private View emptyView;

    public static ArticleListFragment newInstance(String typeId) {
        Bundle args = new Bundle();
        args.putString(COURSE_TYPE_ID, typeId);
        ArticleListFragment fragment = new ArticleListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerArticleListComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_article_list, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            typeId = bundle.getString(COURSE_TYPE_ID);
        }
        mSrlAudition.setOnRefreshLoadMoreListener(this);
        recycleArticlelist.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ArticInformationAdapter(beanList);
        adapter.setOnItemClickListener((adapter, view, position) -> {
            Intent intent = new Intent(getActivity(), MyWebViewActivity.class);
            intent.putExtra(Constant.BUNDLE_WEB_URL, ConstantWeb.getH5AddressById(ConstantWeb.H5_CERTIFICATE_DETAILS)
                    + "?id=" + beanList.get(position).getId());
            startActivity(intent);
        });
        recycleArticlelist.setAdapter(adapter);
        emptyView = LayoutInflater.from(mContext).inflate(R.layout.empty_layout, null);
        adapter.setEmptyView(emptyView);
        emptyView.setVisibility(View.GONE);

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void setData(@Nullable Object data) {

    }


    @Override
    public void getArticleListSuccess(List<ArticleListBean.RecordsBean> beans) {
        beanList = beans;
        adapter = new ArticInformationAdapter(beans);
        recycleArticlelist.setAdapter(adapter);
        adapter.setNewData(beans);
    }

    @Override
    public void showAuditionCourseRefresh(List<ArticleListBean.RecordsBean> itemBeanList) {
        beanList = itemBeanList;
        adapter.setNewData(itemBeanList);
    }

    @Override
    public void showAuditionCourseLoadMore(List<ArticleListBean.RecordsBean> itemBeanList) {
        beanList.addAll(itemBeanList);
        adapter.addData(itemBeanList);
    }

    @Override
    public void canLoadMore() {
        if (mSrlAudition != null) {
            mSrlAudition.setEnableLoadMore(true);
        }
    }

    @Override
    public void cannotLoadMore() {
        if (mSrlAudition != null) {
            mSrlAudition.setEnableLoadMore(false);
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (mPresenter != null) {
            currentPage += 1;
            Objects.requireNonNull(mPresenter).getArticleList(typeId, currentPage, PAGE_SIZE, false);
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        if (mPresenter != null) {
            currentPage = 1;
            Objects.requireNonNull(mPresenter).getArticleList(typeId, currentPage, PAGE_SIZE, true);
        }
        isFirstLoad = true;
    }

    @Override
    public void lazyLoadData() {
        if (mSrlAudition != null) {
            mSrlAudition.autoRefresh();
        } else {
            if (mPresenter != null) {
                currentPage = 1;
                Objects.requireNonNull(mPresenter).getArticleList(typeId, currentPage, PAGE_SIZE, true);
            }
        }
    }

    @Override
    public void hideLoading() {
        stopRefreshAndLoadMore();
    }

    public void stopRefreshAndLoadMore() {
        if (mSrlAudition != null) {
            mSrlAudition.finishRefresh();
            mSrlAudition.finishLoadMore();
        }
    }

    @Override
    public void showEmptyView() {
        if (emptyView != null) {
            emptyView.setVisibility(View.VISIBLE);
        }
    }
}
