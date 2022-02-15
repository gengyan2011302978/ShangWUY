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
import com.phjt.base.utils.ArchitectUtils;

import com.phjt.shangxueyuan.annotate.VipStateCheck;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.MyCollectionBean;
import com.phjt.shangxueyuan.di.component.DaggerMyCollectionsComponent;
import com.phjt.shangxueyuan.mvp.contract.MyCollectionsContract;
import com.phjt.shangxueyuan.mvp.presenter.MyCollectionsPresenter;

import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.mvp.ui.activity.CourseDetailActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.MyCollectionActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.MyWebViewActivity;
import com.phjt.shangxueyuan.mvp.ui.adapter.MyCollectionAdapter;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.ConstantWeb;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;

import static com.phjt.base.utils.Preconditions.checkNotNull;


/**
 * Created by Template
 *
 * @author :
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/08/26 09:53
 */

public class MyCollectionsFragment extends BaseLazyLoadFragment<MyCollectionsPresenter> implements MyCollectionsContract.View, OnRefreshLoadMoreListener {

    public static final String TYPE = "type_id";

    @BindView(R.id.rv_my_collection)
    RecyclerView rvMyCollection;
    @BindView(R.id.srl_my_collection)
    SmartRefreshLayout srlMyCollection;

    private int pageNo = 1;
    private final int PAGE_SIZE = 10;
    private MyCollectionAdapter adapter;

    private List<Integer> mIdList = new ArrayList<>();

    private boolean itemClick = true;
    /**
     * 0:课程, 1:专题,2: 专栏
     */
    private int mType;
    private View mEmptyView;
    private boolean isloaded = false;

    public static MyCollectionsFragment newInstance(int type) {
        Bundle args = new Bundle();
        MyCollectionsFragment fragment = new MyCollectionsFragment();
        args.putInt(TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerMyCollectionsComponent //Dagger 编译时生成代码,报错先请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_collections, container, false);
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mType = bundle.getInt(TYPE);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvMyCollection.setLayoutManager(layoutManager);
        adapter = new MyCollectionAdapter(getActivity());
        rvMyCollection.setAdapter(adapter);
        mEmptyView = LayoutInflater.from(getActivity()).inflate(R.layout.empty_layout, null);
        adapter.setEmptyView(mEmptyView);
        mEmptyView.setVisibility(View.GONE);
        setOnItemClick();
        srlMyCollection.setOnRefreshLoadMoreListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        //是否第一次加载，只加载一个fragment
         if (isloaded) {
             loadData(true);
        }
    }

    @Override
    public void lazyLoadData() {
        //是否第一次加载
        if (!isloaded) {
            loadData(true);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        MyCollectionActivity activity = (MyCollectionActivity) getActivity();
        if (activity != null) {
            if (!isVisibleToUser && null != adapter) {
                activity.loadFavoriteEdit();
                itemClick = true;
                adapter.setEdit(false);
                adapter.notifyDataSetChanged();
            } else if (isVisibleToUser && null != adapter) {
                isloaded = true;
                List<MyCollectionBean> datas = adapter.getData();
                if (datas.size() > 0) {
                    activity.setMayEnabled(true);
                } else {
                    activity.setMayEnabled(false);
                }
            }
        }
    }

    private void setOnItemClick() {
        adapter.setOnItemClickListener((adapter, view, position) -> {
            if (itemClick) {
                MyCollectionBean itemBean = (MyCollectionBean) adapter.getData().get(position);
                if (itemBean != null) {
                    //1.免费；2.会员
                    int mTypes = 2;
                    int freeType = itemBean.getFreeType();
                    if (mType == 2) {
                        toCourseDetail(itemBean);
                    } else {
                        if (mTypes == freeType) {
                            checkVipState(itemBean);
                        } else {
                            toCourseDetail(itemBean);
                        }
                    }
                }
            }
        });

        adapter.setCallBack(new MyCollectionAdapter.ICallBack() {
            @Override
            public void callBack(int id) {
                mIdList.add(id);
                if (mIdList != null && mIdList.size() > 0) {
                    MyCollectionActivity activity = (MyCollectionActivity) getActivity();
                    if (activity != null) {
                        activity.setEditClick(true);
                    }
                }
            }

            @Override
            public void removeBack(int id) {
                Iterator<Integer> it = mIdList.iterator();
                while (it.hasNext()) {
                    int strId = it.next();
                    if (id == strId) {
                        it.remove();
                    }
                }
                if (mIdList != null && mIdList.size() == 0) {
                    MyCollectionActivity activity = (MyCollectionActivity) getActivity();
                    if (activity != null) {
                        activity.setEditClick(false);
                    }
                }
            }
        });
    }


    @VipStateCheck
    private void checkVipState(MyCollectionBean recordsBean) {
        toCourseDetail(recordsBean);
    }

    public void setEdit(boolean edit) {
        if (edit) {
            adapter.setEdit(true);
            adapter.notifyDataSetChanged();
            srlMyCollection.setEnableRefresh(false);
            srlMyCollection.setEnableLoadMore(false);
            itemClick = false;
        } else {
            itemClick = true;
            srlMyCollection.setEnableRefresh(true);
            srlMyCollection.setEnableLoadMore(true);
            if (mIdList != null) {
                mIdList.clear();
            }
            List<MyCollectionBean> list = adapter.getData();
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setCheck(false);
            }
            adapter.setEdit(false);
            adapter.notifyDataSetChanged();
        }
    }

    public void setDelete() {
        if (mIdList != null && mIdList.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mIdList.size(); i++) {
                sb.append(mIdList.get(i));
                if (i != mIdList.size() - 1) {
                    sb.append(",");
                }
            }
            if (mPresenter != null) {
                mPresenter.getFavoriteEdit(getActivity(), sb.toString(), mType);
            }
        }
    }

    @Override
    public void loadDataSuccess(BaseListBean<MyCollectionBean> bean, boolean isRefresh) {
        if (bean!= null) {
            srlMyCollection.setEnableLoadMore(pageNo < bean.getTotalPage());
            MyCollectionActivity activity = (MyCollectionActivity) getActivity();
            if (isRefresh && activity != null) {
                adapter.setNewData(new ArrayList<>());
                if (bean.getRecords().size() > 0) {
                    adapter.setType(mType);
                    adapter.setNewData(bean.getRecords());
                    activity.setEdit(true);
                } else if (bean.getRecords().size() == 0) {
                    if (adapter != null && mEmptyView != null) {
                        mEmptyView.setVisibility(View.VISIBLE);
                    }
                    activity.visibility();
                } else {
                    srlMyCollection.setEnableLoadMore(false);
                    if (adapter != null && mEmptyView != null) {
                        mEmptyView.setVisibility(View.VISIBLE);
                    }
                    activity.setEdit(false);
                }
                srlMyCollection.finishRefresh();
            } else {
                if (bean.getRecords().size() > 0) {
                    adapter.addData(bean.getRecords());
                    adapter.setType(mType);
                } else {
                    srlMyCollection.setEnableLoadMore(false);
                }
                srlMyCollection.finishLoadMore();
            }
        }
    }


    @Override
    public void loadFavoriteEditSuccess() {
        MyCollectionActivity activity = (MyCollectionActivity) getActivity();
        itemClick = true;
        srlMyCollection.setEnableRefresh(true);
        srlMyCollection.setEnableLoadMore(true);
        adapter.setEdit(false);
        adapter.notifyDataSetChanged();
        if (activity != null) {
            activity.loadFavoriteEdit();
        }
        if (mIdList != null) {
            mIdList.clear();
        }
        pageNo = 1;
        loadData(true);

    }

    @Override
    public void loadDataFailure(boolean isRefresh) {
        srlMyCollection.finishRefresh();
        srlMyCollection.finishLoadMore();
        if (adapter != null && mEmptyView != null&& isRefresh) {
            mEmptyView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNo += 1;
        loadData(false);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNo = 1;
        loadData(true);
    }

    private void loadData(boolean isReFresh) {
        if (mPresenter != null) {
            if (mType == 0 || mType == 2) {
                mPresenter.getCollectionList(mType == 2 ? 1 : 0, pageNo, PAGE_SIZE, isReFresh);
            } else if (mType == 1) {
                mPresenter.getSpecialFavouriteList(pageNo, PAGE_SIZE, isReFresh);
            }
        }
    }

    private void toCourseDetail(MyCollectionBean recordsBean) {
        if (mType == 0) {
            //跳课程
            if (0 == recordsBean.getDelStatus()) {
                //0 已删除 1 未删除
                TipsUtil.showTips(" 该课程已失效");
            } else if (recordsBean.getUpState() != 0) {
                Intent intent = new Intent(getActivity(), CourseDetailActivity.class);
                intent.putExtra(Constant.BUNDLE_COURSE_ID, String.valueOf(recordsBean.getCouId()));
                intent.putExtra(Constant.BUNDLE_COURSE_NAME, recordsBean.getName());
                startActivity(intent);
            } else {
                //upState:0下架 1上架
            }
        } else if (mType == 1) {
            //跳专题
            if (recordsBean.getUpState() != 0) {
                Intent intent = new Intent(getActivity(), MyWebViewActivity.class);
                intent.putExtra(Constant.BUNDLE_WEB_URL, ConstantWeb.getH5AddressById(ConstantWeb.H5_PROJECTDETAILS)
                        + "?id=" + recordsBean.getSpecialId());
                startActivity(intent);
            } else {
                //TipsUtil.showTips(" 该课程已下架");
            }
        } else if (mType == 2) {
            //跳专栏
            Intent intent = new Intent(getActivity(), CourseDetailActivity.class);
            intent.putExtra(Constant.BUNDLE_COURSE_ID, String.valueOf(recordsBean.getCouId()));
            intent.putExtra(Constant.BUNDLE_COURSE_NAME, recordsBean.getName());
            startActivity(intent);
        }
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        TipsUtil.show(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArchitectUtils.startActivity(intent);
    }
}
