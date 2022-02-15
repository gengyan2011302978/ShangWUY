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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.phjt.base.base.BaseFragment;
import com.phjt.base.base.delegate.IFragment;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.BaseListBean;
import com.phjt.shangxueyuan.bean.MyDiaryBean;
import com.phjt.shangxueyuan.bean.event.EventBean;
import com.phjt.shangxueyuan.di.component.DaggerPublishedDiaryComponent;
import com.phjt.shangxueyuan.mvp.contract.PublishedDiaryContract;
import com.phjt.shangxueyuan.mvp.presenter.PublishedDiaryPresenter;
import com.phjt.shangxueyuan.mvp.ui.activity.HistoryThemeActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.JournalActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.MyWebViewActivity;
import com.phjt.shangxueyuan.mvp.ui.adapter.PublishedDiaryAdapter;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.ConstantWeb;
import com.phjt.shangxueyuan.widgets.dialog.DialogUtils;
import com.phjt.shangxueyuan.widgets.popupwindow.EditScreenPop;
import com.phjt.shangxueyuan.widgets.popupwindow.EditScreenPop.IEditScreenPop;
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
 * Created by Template
 *
 * @author :
 * description :我的打卡-发表的日记
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/01/15 11:21
 */

public class PublishedDiaryFragment extends BaseFragment<PublishedDiaryPresenter> implements PublishedDiaryContract.View, OnRefreshLoadMoreListener {

    @BindView(R.id.rv_published_diary)
    RecyclerView rvPublishedDiary;
    @BindView(R.id.srl_published_diary)
    SmartRefreshLayout srlPublishedDiary;
    private int pageNo = 1;
    private  final int PAGE_SIZE = 10;
    private ReplyJournalFragment mReplyJournalFragment;
    private PublishedDiaryAdapter adapter;
    private View mEmptyView;

    /**
     * 提供静态工厂方法 避免在实例化是利用构造方法初始化对象
     *
     * @return 对应Fragment实例
     */
    public static PublishedDiaryFragment newInstance() {
        PublishedDiaryFragment fragment = new PublishedDiaryFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerPublishedDiaryComponent //Dagger 编译时生成代码,报错先请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_published_diary, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvPublishedDiary.setLayoutManager(layoutManager);
        adapter = new PublishedDiaryAdapter(getActivity());
        rvPublishedDiary.setAdapter(adapter);
        mEmptyView = LayoutInflater.from(getActivity()).inflate(R.layout.empty_layout, null);
        ImageView imageDodata = mEmptyView.findViewById(R.id.image_nodata);
        TextView tvNodata = mEmptyView.findViewById(R.id.tv_nodata);
        imageDodata.setBackgroundResource(R.drawable.ic_puncn_nodata);
        tvNodata.setText("你还没有参与打卡");
        adapter.setEmptyView(mEmptyView);
        mEmptyView.setVisibility(View.GONE);
        srlPublishedDiary.setOnRefreshLoadMoreListener(this);
        pageNo = 1;
        loadData(true);

        adapter.setOnItemClickListener((adapter, view, position) -> {
            List<MyDiaryBean> data = adapter.getData();
            Intent intent = new Intent(getActivity(), MyWebViewActivity.class);
            intent.putExtra(Constant.BUNDLE_WEB_TITLE, "日记详情");
            intent.putExtra(Constant.BUNDLE_WEB_URL, ConstantWeb.getH5AddressById(ConstantWeb.H5_DIARY_DETAILS) +
                    "?id="+data.get(position).getId()+"&cardId="+data.get(position).getPunchCardId());
            intent.putExtra(Constant.BUNDLE_ADD_DIARY_ID, data.get(position).getId());
            intent.putExtra(Constant.PARTICIPATION_PUNCH_CARDSID, data.get(position).getPunchCardId());
            startActivity(intent);
        });
        setItemChildClickListener();
    }


    /**
     * 删除弹框提示
     */
    public void showExitDialog(String id) {
        DialogUtils.showCancelSureDialog(getActivity(), "",
                getResources().getString(R.string.is_delete_diary), getResources().getString(R.string.quit_cancel),
                getResources().getString(R.string.quit_delete),
                new DialogUtils.OnCancelSureClick() {
                    @Override
                    public void clickSure() {
                        if (mPresenter != null) {
                            mPresenter.delectDiary(id);
                        }

                    }
                });

    }

    /**
     * 此方法使用请查看 {@link IFragment#setData(Object)} 注释 目的：建立统一入口 与 Fragment 进行通信
     *
     * @param data 当不需要参数时 {@code data} 可以为 {@code null}
     */
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
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * View 层的默认方法 可以不实现 直接在 P 层 调用 此方法
     * Demo
     *
     * @param intent {@code intent} 不能为 {@code null}
     */
    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArchitectUtils.startActivity(intent);
    }

    private void setItemChildClickListener() {
        adapter.setOnItemChildClickListener((adapter, view, position) -> {
            List<MyDiaryBean> data = adapter.getData();
            switch (view.getId()) {
                case R.id.tv_recyclear_like_num:
                    mPresenter.thumbsUp(data.get(position).getId(), 1, position, data.get(position).getState());
                    break;
                case R.id.view_show:
                    EditScreenPop teacherPop = new EditScreenPop(mContext);
                    teacherPop.showAsDropDown(view, 0, 0);
                    teacherPop.setTeacherPop(new IEditScreenPop() {
                        @Override
                        public void getScreen(String teacherId, String name) {
                            if (getResources().getString(R.string.quit_delete).equals(name)) {
                                showExitDialog(data.get(position).getId());
                            } else {
                                Intent intent = new Intent(getActivity(), JournalActivity.class);
                                intent.putExtra(Constant.BUNDLE_ADD_DIARY_ID, data.get(position).getId());
                                intent.putExtra(Constant.BUNDLE_ADD_REISSUE_CARD_TYPE, 0);
                                intent.putExtra(Constant.BUNDLE_ADD_PUNCH_CARD_ID, data.get(position).getPunchCardId());
                                intent.putExtra(Constant.BUNDLE_ADD_MOTIF_ID, data.get(position).getMotifId());
                                intent.putExtra(Constant.BUNDLE_ADD_ONCE_MORE, 1);
                                startActivity(intent);
                            }
                        }
                    });
                    break;
                case R.id.tv_reply:
                    if (null == mReplyJournalFragment) {
                        mReplyJournalFragment = new ReplyJournalFragment();
                    }
                    if (!mReplyJournalFragment.isAdded()) {
                        Bundle bundle = new Bundle();
                        bundle.putString(Constant.BUNDLE_ADD_DIARY_ID, data.get(position).getId());
                        bundle.putString(Constant.BUNDLE_ADD_PUNCH_CARD_ID, data.get(position).getPunchCardId());
                        bundle.putString(Constant.BUNDLE_ADD_MOTIF_ID, data.get(position).getMotifId());
                        bundle.putString(Constant.BUNDLE_ADD_COMMENT_ID, "");
                        mReplyJournalFragment.setArguments(bundle);
                        mReplyJournalFragment.show(getActivity().getSupportFragmentManager(), "dialog");
                    }
                    break;
                case R.id.tv_course:
                    //跳去历史主题详情页
                    Intent courseIntent = new Intent(getActivity(), HistoryThemeActivity.class);
                    courseIntent.putExtra(Constant.BUNDLE_ADD_MOTIF_ID, data.get(position).getMotifId());
                    courseIntent.putExtra("punchCardId", data.get(position).getPunchCardId());
                    courseIntent.putExtra("id", data.get(position).getMotifId());
                    startActivity(courseIntent);
                    break;
                default:
                    break;
            }
        });
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
            mPresenter.getMyDiaryList(pageNo, PAGE_SIZE, isReFresh);
        }
    }

    @Override
    public void LoadSuccess(BaseListBean<MyDiaryBean> bean, boolean isRefresh) {
        if (bean != null) {
            srlPublishedDiary.setEnableLoadMore(pageNo < bean.getTotalPage());
            if (isRefresh) {
                adapter.setNewData(new ArrayList<>());
                if (bean.getRecords().size() > 0) {
                    adapter.setNewData(bean.getRecords());
                } else if (bean.getRecords().size() == 0) {
                    if (adapter != null && mEmptyView != null) {
                        mEmptyView.setVisibility(View.VISIBLE);
                    }
                } else {
                    srlPublishedDiary.setEnableLoadMore(false);
                    if (adapter != null && mEmptyView != null) {
                        mEmptyView.setVisibility(View.VISIBLE);
                    }
                }
                srlPublishedDiary.finishRefresh();
            } else {
                if (bean.getRecords().size() > 0) {
                    adapter.addData(bean.getRecords());
                } else {
                    srlPublishedDiary.setEnableLoadMore(false);
                }
                srlPublishedDiary.finishLoadMore();
            }
        }
    }

    @Override
    public void LoadFailed(boolean isRefresh) {
        if (srlPublishedDiary != null) {
            srlPublishedDiary.finishRefresh();
            srlPublishedDiary.finishLoadMore();
        }
        if (adapter != null && mEmptyView != null && isRefresh) {
            mEmptyView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void delectDiarySuccess() {
        pageNo = 1;
        loadData(true);
    }

    @Override
    public void thumbsUpSuccess(int position, int likeStatus) {
        int thumbsUpCount = adapter.getData().get(position).getLikeNum();
        adapter.getData().get(position).setLikeNum(likeStatus == 0 ? thumbsUpCount + 1 : thumbsUpCount - 1);
        adapter.getData().get(position).setState(likeStatus == 1 ? 0 : 1);
        adapter.notifyItemChanged(position, R.id.tv_like);

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventBean(EventBean eventBean) {
        if (eventBean != null) {
            int type = eventBean.getType();
            if (type == EventBean.ADD_DIARY) {
                pageNo = 1;
                loadData(true);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mReplyJournalFragment != null) {
            mReplyJournalFragment = null;
        }
    }
}
