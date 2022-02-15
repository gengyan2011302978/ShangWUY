//package com.phjt.shangxueyuan.mvp.ui.fragment;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Toast;
//
//import com.chad.library.adapter.base.BaseQuickAdapter;
//import com.phjt.base.base.BaseFragment;
//import com.phjt.base.base.BaseLazyLoadFragment;
//import com.phjt.base.base.delegate.IFragment;
//import com.phjt.base.di.component.AppComponent;
//import com.phjt.base.utils.ArchitectUtils;
//
//import com.phjt.shangxueyuan.bean.BaseListBean;
//import com.phjt.shangxueyuan.bean.MyNotesBean;
//import com.phjt.shangxueyuan.bean.NotesDetailsBean;
//import com.phjt.shangxueyuan.di.component.DaggerAllNoteComponent;
//import com.phjt.shangxueyuan.mvp.contract.AllNoteContract;
//import com.phjt.shangxueyuan.mvp.presenter.AllNotePresenter;
//
//import com.phjt.shangxueyuan.R;
//import com.phjt.shangxueyuan.mvp.ui.adapter.MyNotesAdapter;
//import com.phjt.shangxueyuan.utils.Constant;
//import com.phsxy.utils.ToastUtils;
//import com.scwang.smartrefresh.layout.SmartRefreshLayout;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import butterknife.BindView;
//
//import static com.phjt.base.utils.Preconditions.checkNotNull;
//
//
///**
// * Created by Template
// *
// * @author :Roy
// * description :所有笔记
// * company     : Copyright (c) 2019 普华集团 All rights reserved
// * @date : 2020/06/04 11:19
// */
//
//public class AllNoteFragment extends BaseLazyLoadFragment<AllNotePresenter> implements AllNoteContract.View {
//
//    @BindView(R.id.rv_my_notes)
//    RecyclerView rvMyNotes;
//
//
//    /**
//     * 课程id
//     */
//    private String courseId;
//    /**
//     * 当前课程状态 1.免费；2.会员
//     */
//    private int freeType;
//
//    private int pageNo = 1;
//    private int pageSize = 10;
//
//    public static final String COURSE_FREE_TYPE = "course_free_type";
//    private MyNotesAdapter adapter;
//    private NotesReviewDetailFragment mNotesReviewFragment;
//
//
//    /**
//     * 提供静态工厂方法 避免在实例化是利用构造方法初始化对象
//     *
//     * @return 对应Fragment实例
//     */
//    public static AllNoteFragment newInstance(String courseId, int freeType) {
//        Bundle args = new Bundle();
//        args.putString(Constant.BUNDLE_COURSE_ID, courseId);
//        args.putInt(COURSE_FREE_TYPE, freeType);
//        AllNoteFragment fragment = new AllNoteFragment();
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
//        DaggerAllNoteComponent //Dagger 编译时生成代码,报错先请编译一下项目
//                .builder()
//                .appComponent(appComponent)
//                .view(this)
//                .build()
//                .inject(this);
//    }
//
//    @Override
//    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_all_note, container, false);
//    }
//
//    @Override
//    public void initData(@Nullable Bundle savedInstanceState) {
//        Bundle bundle = getArguments();
//        if (bundle != null) {
//            courseId = bundle.getString(Constant.BUNDLE_COURSE_ID);
//            freeType = bundle.getInt(COURSE_FREE_TYPE);
//        }
//    }
//
//    @Override
//    public void lazyLoadData() {
//        setDatas();
//    }
//
//    private void setDatas() {
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
//        rvMyNotes.setLayoutManager(layoutManager);
//        adapter = new MyNotesAdapter(getActivity(),0);
//        rvMyNotes.setAdapter(adapter);
//        if (mPresenter != null) {
//            mPresenter.getNotesList(courseId,pageNo, pageSize, true);
//        }
//
//
//        adapter.setOnItemClickListener((adapter, view, position) -> {
//            List<MyNotesBean> data = adapter.getData();
//            MyNotesBean myNotesBean = data.get(position);
//            if (null == mNotesReviewFragment) {
//                mNotesReviewFragment = new NotesReviewDetailFragment();
//            }
//            Bundle bundle = new Bundle();
//            bundle.putSerializable(NotesReviewDetailFragment.BUNDLE_NOTES, myNotesBean);
//            mNotesReviewFragment.setArguments(bundle);
//            mNotesReviewFragment.show(getActivity().getSupportFragmentManager(), "dialog");
//
//        });
//        adapter.setOnItemChildClickListener((adapter12, view, position) -> {
//            switch (view.getId()) {
//                case R.id.tv_my_notes_address:
//                    ToastUtils.show("小节课");
//                    break;
//                default:
//                    break;
//            }
//        });
//    }
//
//    /**
//     * 此方法使用请查看 {@link IFragment#setData(Object)} 注释 目的：建立统一入口 与 Fragment 进行通信
//     *
//     * @param data 当不需要参数时 {@code data} 可以为 {@code null}
//     */
//    @Override
//    public void setData(@Nullable Object data) {
//
//    }
//
//    @Override
//    public void showLoading() {
//
//    }
//
//    @Override
//    public void hideLoading() {
//
//    }
//
//    @Override
//    public void showMessage(@NonNull String message) {
//        checkNotNull(message);
//        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
//    }
//
//    /**
//     * View 层的默认方法 可以不实现 直接在 P 层 调用 此方法
//     * Demo
//     *
//     * @param intent {@code intent} 不能为 {@code null}
//     */
//    @Override
//    public void launchActivity(@NonNull Intent intent) {
//        checkNotNull(intent);
//        ArchitectUtils.startActivity(intent);
//    }
//
//
//    @Override
//    public void loadDataSuccess(BaseListBean<MyNotesBean> bean, boolean isRefresh) {
////        srlMyNotes.setEnableLoadMore(pageNo < bean.getTotalPage());
//        if (isRefresh) {
//            adapter.setNewData(new ArrayList<>());
//            if (bean != null && bean.getRecords().size() > 0) {
//                adapter.setNewData(bean.getRecords());
//            } else if (bean != null && bean.getRecords().size() == 0) {
//                adapter.setEmptyView(R.layout.empty_layout, rvMyNotes);
//            } else {
////                srlMyNotes.setEnableLoadMore(false);
//                adapter.setEmptyView(R.layout.empty_layout, rvMyNotes);
//            }
////            srlMyNotes.finishRefresh();
//        } else {
//            if (bean != null && bean.getRecords().size() > 0) {
//                adapter.addData(bean.getRecords());
//            } else {
////                srlMyNotes.setEnableLoadMore(false);
//            }
////            srlMyNotes.finishLoadMore();
//        }
//    }
//
//    @Override
//    public void loadDataFailure() {
//
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        if (mNotesReviewFragment != null) {
//            mNotesReviewFragment = null;
//        }
//    }
//}
