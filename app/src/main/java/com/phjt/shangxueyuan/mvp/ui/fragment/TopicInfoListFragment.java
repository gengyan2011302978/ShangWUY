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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.phjt.base.base.BaseFragment;
import com.phjt.base.di.component.AppComponent;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.annotate.SingleClick;
import com.phjt.shangxueyuan.bean.ShareBean;
import com.phjt.shangxueyuan.bean.ShareItemBean;
import com.phjt.shangxueyuan.bean.TopicItemInfoBean;
import com.phjt.shangxueyuan.bean.event.CourseCommentStateBean;
import com.phjt.shangxueyuan.di.component.DaggerTopicInfoListComponent;
import com.phjt.shangxueyuan.mvp.contract.TopicInfoListContract;
import com.phjt.shangxueyuan.mvp.presenter.TopicInfoListPresenter;
import com.phjt.shangxueyuan.mvp.ui.activity.MyWebViewActivity;
import com.phjt.shangxueyuan.mvp.ui.adapter.ThemeTopicAdapter;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.ConstantWeb;
import com.phjt.shangxueyuan.utils.ShareUtils;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.shangxueyuan.widgets.dialog.DialogUtils;
import com.phsxy.utils.ToastUtils;
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
 * date: 11/03/2020 18:23
 * company: 普华集团
 * description: 模版请保持更新
 */
public class TopicInfoListFragment extends BaseFragment<TopicInfoListPresenter> implements TopicInfoListContract.View, OnRefreshLoadMoreListener {

    @BindView(R.id.recycle_topic_hot)
    RecyclerView recycleTopicHot;
    @BindView(R.id.srf_study)
    SmartRefreshLayout srfStudy;

    private String type;
    private String topicId;
    private String topicUserId;
    private Integer vipStatus = 1;
    private Integer freezeState = 1;
    private int pageNo = 1;
    private String isTop = "0";
    ThemeTopicAdapter themeTopicAdapter;
    List<TopicItemInfoBean.RecordsBean> list = new ArrayList<>();
    private ShareBean mShareBean;
    private View mEmptyView;

    public static TopicInfoListFragment newInstance(String type, String topicId, String topicUserId, int vipStatus, int freezeState) {
        Bundle args = new Bundle();
        args.putString("topicType", type);
        args.putString("topicId", topicId);
        args.putInt("vipStatus", vipStatus);
        args.putInt("freezeState", freezeState);
        args.putString("topicUserId", topicUserId);
        TopicInfoListFragment fragment = new TopicInfoListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerTopicInfoListComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_topic_info_list, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            vipStatus = bundle.getInt("vipStatus");
            freezeState = bundle.getInt("freezeState");
            type = bundle.getString("topicType");
            topicId = bundle.getString("topicId");
            topicUserId = bundle.getString("topicUserId");
        }
        themeTopicAdapter = new ThemeTopicAdapter(list);
        recycleTopicHot.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        recycleTopicHot.setAdapter(themeTopicAdapter);
        mEmptyView = LayoutInflater.from(getActivity()).inflate(R.layout.empty_layout, null);
        themeTopicAdapter.setEmptyView(mEmptyView);
        mEmptyView.setVisibility(View.GONE);

        srfStudy.setOnRefreshLoadMoreListener(this);
//        requestData(true);
        themeTopicAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @SingleClick
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                TopicItemInfoBean.RecordsBean recordsBean = (TopicItemInfoBean.RecordsBean) adapter.getData().get(position);
                isTop = recordsBean.getThemeState();
                if ("0".equals(isTop)) {
                    isTop = "1";
                } else {
                    isTop = "0";
                }
                switch (view.getId()) {
                    case R.id.iv_like:
                    case R.id.tv_like:
                        if (mPresenter != null) {
                            mPresenter.themeLike(recordsBean.getId() + "", position, recordsBean.getLikeState());
                        }
                        break;
                    case R.id.iv_notes_reply:
                    case R.id.relat_comment:
                    case R.id.tv_my_notes_reply:
                        String webUrl = ConstantWeb.getH5AddressById(ConstantWeb.H5_DYNAMIC);
                        Intent intent = new Intent(getActivity(), MyWebViewActivity.class);
                        intent.putExtra(Constant.BUNDLE_WEB_URL, webUrl + "?id=" + recordsBean.getId() + "&typeId=4&type=1");
                        startActivity(intent);
                        break;
                    case R.id.tv_share:
                        if (mPresenter != null) {
                            mPresenter.getShareItemData(4, recordsBean.getId() + "", recordsBean.getThemeName());
                        }
                        break;
                    case R.id.iv_more:
                        if (vipStatus == 3) {
                            TipsUtil.show("VIP会员已过期");
                            return;
                        }
                        if (freezeState == 1) {
                            TipsUtil.show("该话题已冻结");
                            return;
                        }
                        DialogUtils.showMoreTopicDialog(getActivity(), isTop, new DialogUtils.OnCancelSureClick() {
                            @Override
                            public void clickSure() {
                                if (mPresenter != null) {
                                    mPresenter.themeTop(recordsBean.getId() + "", isTop, topicId, topicUserId);
                                }
                            }

                            @Override
                            public void clickDelete() {
                                if (mPresenter != null) {
                                    mPresenter.themeDelete(recordsBean.getId() + "", topicId, topicUserId);
                                }
                            }
                        });
                    default:
                        break;
                }
            }
        });

    }

    public void requestData(boolean isRefresh) {
        if ("1".equals(type)) {
            if (mPresenter != null) {
                mPresenter.themeList(pageNo, 10, type, topicId, topicUserId, isRefresh);
            }
        } else {
            if (mPresenter != null) {
                mPresenter.themeList(pageNo, 10, type, topicId, topicUserId, isRefresh);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ("1".equals(type)) {
            if (mPresenter != null) {
                mPresenter.themeList(pageNo, 10, type, topicId, topicUserId, true);
            }
        } else {
            if (mPresenter != null) {
                mPresenter.themeList(pageNo, 10, type, topicId, topicUserId, true);
            }
        }
    }

    @Override
    public void themeListSuccess(TopicItemInfoBean beans, int pageNo, boolean isRefresh) {
        srfStudy.setEnableLoadMore(pageNo < beans.getTotalPage());
        list = beans.getRecords();
        if (isRefresh) {
            themeTopicAdapter.setNewData(new ArrayList<>());
            if (beans.getRecords().size() > 0) {
                themeTopicAdapter.setNewData(beans.getRecords());
            } else if (beans.getRecords().size() == 0) {
                if (themeTopicAdapter != null && mEmptyView != null) {
                    mEmptyView.setVisibility(View.VISIBLE);
                }
            } else {
                srfStudy.setEnableLoadMore(false);
                if (themeTopicAdapter != null && mEmptyView != null) {
                    mEmptyView.setVisibility(View.VISIBLE);
                }
            }
            srfStudy.finishRefresh();
        } else {
            if (beans.getRecords().size() > 0) {
                themeTopicAdapter.addData(beans.getRecords());
            } else {
                srfStudy.setEnableLoadMore(false);
            }
            srfStudy.finishLoadMore();
        }
    }

    @Override
    public void themeListFailure() {
        srfStudy.finishLoadMore();
        srfStudy.finishRefresh();
    }

    @Override
    public void themeLikeSuccess(int position, String likeStatus) {
        int thumbsUpCount = themeTopicAdapter.getData().get(position).getThemeLikeNum();
        themeTopicAdapter.getData().get(position).setThemeLikeNum("0".equals(likeStatus) ? thumbsUpCount + 1 : thumbsUpCount - 1);
        themeTopicAdapter.getData().get(position).setLikeState("1".equals(likeStatus) ? "0" : "1");
        themeTopicAdapter.notifyItemChanged(position, R.id.tv_like);
    }

    @Override
    public void themeLikeFaile(String msg) {

    }

    @Override
    public void themeDeleteSuccess() {
        pageNo = 1;
        requestData(true);
    }

    @Override
    public void themeDeleteFaile(String msg) {
        ToastUtils.show(msg);
    }

    @Override
    public void themeTopSuccess() {
        pageNo = 1;
        requestData(true);
    }

    @Override
    public void themeTopFaile(String msg) {
        ToastUtils.show(msg);
    }

    @Override
    public void showShareItemDialog(ShareItemBean shareItemBean) {
        //封装分享内容
        mShareBean = new ShareBean();
        mShareBean.setTitle(shareItemBean.getTitle());
        mShareBean.setContent(shareItemBean.getContent());
        mShareBean.setImgUrl(shareItemBean.getPhoto());
        mShareBean.setUrl(shareItemBean.getUrl());

        ShareUtils.showSharePop(getActivity(), mShareBean);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventBean(CourseCommentStateBean commentStateBean) {
        if (commentStateBean != null) {
            List<TopicItemInfoBean.RecordsBean> commentBeans = themeTopicAdapter.getData();
            for (int i = 0; i < commentBeans.size(); i++) {
                TopicItemInfoBean.RecordsBean recordsBean = commentBeans.get(i);
                if (recordsBean != null && TextUtils.equals(recordsBean.getId() + "", commentStateBean.getId())) {
                    recordsBean.setLikeState(commentStateBean.isLikeState() ? "1" : "0");
                    recordsBean.setThemeLikeNum(commentStateBean.getLikeNum());
                    recordsBean.setThemeDynamicNum(commentStateBean.getReplyNum() + "");
                    themeTopicAdapter.notifyItemChanged(i);
                }
            }
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
    }

}
