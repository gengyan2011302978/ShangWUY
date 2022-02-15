package com.phjt.shangxueyuan.mvp.ui.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.phjt.base.base.BaseFragment;
import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.di.component.DaggerCourseCatalogComponent;
import com.phjt.shangxueyuan.mvp.contract.CourseCatalogContract;
import com.phjt.shangxueyuan.mvp.presenter.CourseCatalogPresenter;

import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.mvp.ui.adapter.CourseCatalogAdapter;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.TipsUtil;

import java.util.ArrayList;

import butterknife.BindView;

import static com.phjt.base.utils.Preconditions.checkNotNull;


/**
 * @author: Created by GengYan
 * date: 03/27/2020 15:52
 * company: 普华集团
 * description: 课程目录
 */
public class CourseCatalogFragment extends BaseFragment<CourseCatalogPresenter> implements CourseCatalogContract.View {

    @BindView(R.id.rv_course_catalog)
    RecyclerView mRvCourseCatalog;

    /**
     * 用于播放页播放
     */
    public CourseCatalogAdapter catalogAdapter;

    /**
     * 课程id
     */
    private String courseId;

    public static CourseCatalogFragment newInstance(String courseId) {
        Bundle args = new Bundle();
        args.putString(Constant.BUNDLE_COURSE_ID, courseId);
        CourseCatalogFragment fragment = new CourseCatalogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerCourseCatalogComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_course_catalog, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            courseId = bundle.getString(Constant.BUNDLE_COURSE_ID);
        }
    }

    private void initRv() {
        if (mRvCourseCatalog != null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
            mRvCourseCatalog.setLayoutManager(layoutManager);
            ArrayList<MultiItemEntity> mList = new ArrayList<>();
            catalogAdapter = new CourseCatalogAdapter(mContext, mList);
            mRvCourseCatalog.setAdapter(catalogAdapter);
        }
    }

    public void showCourseCatalogList(ArrayList<MultiItemEntity> list) {
        initRv();
        if (list == null || list.isEmpty()) {
            showEmptyView();
        } else if (catalogAdapter!= null){
            catalogAdapter.setNewData(list);
            //默认展开所有
            catalogAdapter.expandAll();
        }
    }

    @Override
    public void showEmptyView() {
        if (catalogAdapter != null) {
            catalogAdapter.setEmptyView(R.layout.empty_layout, mRvCourseCatalog);
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
        TipsUtil.showTips(message);
    }
}
