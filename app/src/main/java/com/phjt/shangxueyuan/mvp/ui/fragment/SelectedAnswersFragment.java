package com.phjt.shangxueyuan.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.phjt.base.base.BaseLazyLoadFragment;
import com.phjt.base.di.component.AppComponent;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.SelectedAnswersBean;
import com.phjt.shangxueyuan.bean.event.EventBean;
import com.phjt.shangxueyuan.di.component.DaggerSelectedAnswersComponent;
import com.phjt.shangxueyuan.mvp.contract.SelectedAnswersContract;
import com.phjt.shangxueyuan.mvp.presenter.SelectedAnswersPresenter;
import com.phjt.shangxueyuan.mvp.ui.activity.CheckTheAnswerActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.QuestionsAndAnswersActivity;
import com.phjt.shangxueyuan.mvp.ui.adapter.SelectedAnswersAdapter;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.widgets.ScreenPopWindow;
import com.phjt.shangxueyuan.widgets.SortSelectView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;

/**
 * @author : gengyan
 * @company : Copyright (c) 2021 普华集团 All rights reserved
 * @date : 2021/06/18 14:27
 * @description :
 */
public class SelectedAnswersFragment extends BaseLazyLoadFragment<SelectedAnswersPresenter> implements SelectedAnswersContract.View {
    public static final String BUNDLE_NAME = "name";
    @BindView(R.id.rv_selected_answers)
    RecyclerView rvSelectedAnswers;
    @BindView(R.id.srl_selected_answers)
    SmartRefreshLayout srlSelectedAnswers;
    @BindView(R.id.view_short)
    SortSelectView viewShort;
    @BindView(R.id.iv_screen_select)
    ImageView ivScreenSelect;
    @BindView(R.id.iv_screen_reply)
    ImageView ivScreenReply;
    @BindView(R.id.screen_include)
    View screenInclude;
    @BindView(R.id.view_screen_reply)
    View viewDcreenReply;
    @BindView(R.id.view_screen_data)
    View viewDcreenData;

    @BindView(R.id.view_my_consultation_reply)
    View viewMyDcreenReply;
    @BindView(R.id.view_my_consultation_data)
    View viewMyDcreenData;
    @BindView(R.id.tv_screen_data)
    TextView tvScreenData;
    @BindView(R.id.tv_screen_reply)
    TextView tvScreenReply;

    /**
     * mType:1:精选解答,2: 我的提问
     */
    private int mType;
    private int pageNo = 1;
    private int pageSize = 10;

    private SelectedAnswersAdapter mAdapter;
    private View mEmptyView;
    private String realmId = "";
    /**
     * （0-未回答，1-已回答，2-已忽略)
     */
    private int isReply = 1;
    /**
     * 1-倒序，2-正序
     */
    private int timeSort = 1;


    private ScreenPopWindow popWindow;
    private boolean isFrist = false;

    public static SelectedAnswersFragment newInstance(int type) {
        Bundle args = new Bundle();
        SelectedAnswersFragment fragment = new SelectedAnswersFragment();
        args.putInt(BUNDLE_NAME, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerSelectedAnswersComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_selected_answers, container, false);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isFrist) {
            pageNo = 1;
            loadData(true);
        } else if (isVisibleToUser && srlSelectedAnswers != null && !isFrist) {
            pageNo = 1;
            srlSelectedAnswers.autoRefresh();
            isFrist = true;
        }
    }

    @Override
    public void lazyLoadData() {
    }

    @Override
    public void onResume() {
        super.onResume();


    }


    /**
     * 在 onActivityCreate()时调用
     */
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        mType = bundle.getInt(BUNDLE_NAME, 0);
        rvSelectedAnswers.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new SelectedAnswersAdapter(getActivity(), mType);
        rvSelectedAnswers.setAdapter(mAdapter);

        ((SimpleItemAnimator) Objects.requireNonNull(rvSelectedAnswers.getItemAnimator())).setSupportsChangeAnimations(false);
        View mFooterView = View.inflate(getContext(), R.layout.item_invitation_list_foot, null);
        mFooterView.setVisibility(View.GONE);
        mAdapter.setFooterView(mFooterView);
        mEmptyView = LayoutInflater.from(getActivity()).inflate(R.layout.empty_layout, null);
        TextView tvNodata = mEmptyView.findViewById(R.id.tv_nodata);

        initSortSelectView(tvNodata);
        mAdapter.setEmptyView(mEmptyView);
        mEmptyView.setVisibility(View.GONE);
        setItemClick();
        setItemChildClick();
        initRefresh();
    }


    private void setItemClick() {
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            SelectedAnswersBean bean = mAdapter.getData().get(position);
            String questionId = bean.getQuestionId();
            Intent intent = new Intent(getActivity(), CheckTheAnswerActivity.class);
            intent.putExtra(Constant.QUESTION_ID, questionId);
            launchActivity(intent);
        });

    }

    private void setItemChildClick() {
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            List<SelectedAnswersBean> data = adapter.getData();
            String answerId = data.get(position).getId();
            int like = data.get(position).getLikeStatus();
            int likeState;
            switch (view.getId()) {
                case R.id.iv_question_fabulous:
                case R.id.tv_questions_fabulous:
                    if (like == 0) {
                        likeState = 1;
                    } else {
                        likeState = 0;
                    }
                    if (mPresenter != null) {
                        mPresenter.getCollectLike(answerId, likeState, position);
                    }
                    break;

                default:
                    break;
            }
        });
    }

    private void initRefresh() {
        srlSelectedAnswers.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageNo = pageNo + 1;
                loadData(false);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageNo = 1;
                loadData(true);

            }
        });
    }

    @OnClick({R.id.iv_screen_select, R.id.tv_screen_data, R.id.iv_screen_reply, R.id.tv_screen_reply})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_screen_select:
            case R.id.tv_screen_data:
                //时间
                setPopWindow(1);
                break;
            case R.id.iv_screen_reply:
            case R.id.tv_screen_reply:
                //回答状态
                setPopWindow(2);
                break;
            default:
                break;
        }
    }

    private void initSortSelectView(TextView tvNodata) {
        screenInclude.setVisibility(View.GONE);
        viewShort.setVisibility(View.GONE);
        if (mType == 1) {
            isReply = 0;
            screenInclude.setVisibility(View.GONE);
            viewShort.setVisibility(View.VISIBLE);
            viewShort.setOnSelectClickListener((timeId, statusId) -> {
                timeSort = timeId;
                isReply = statusId;
                pageNo = 1;
                loadData(true);
            });
        } else if (mType == 2) {
            isReply = 1;
            screenInclude.setVisibility(View.VISIBLE);
            viewShort.setVisibility(View.GONE);
            tvNodata.setText("你还没有提问数据 ~");
        }

    }


    /**
     * 获取筛选
     */
    public void setPopWindow(int type) {
        closePopWindow();
        popWindow = new ScreenPopWindow(getActivity(), type, timeSort, isReply, new ScreenPopWindow.PopInter() {
            @Override
            public void sure(int selectContentId, String name) {
                if (1 == type) {
                    timeSort = selectContentId;
                    tvScreenData.setText(name);
                } else if (2 == type) {
                    isReply = selectContentId;
                    tvScreenReply.setText(name);
                } else if (3 == type) {
                    timeSort = selectContentId;
                    setActivityText(name, type);
                } else if (4 == type) {
                    isReply = selectContentId;
                    setActivityText(name, type);
                }
                pageNo = 1;
                loadData(true);
            }
        });

        if (1 == type) {
            popWindow.showAsDropDown(viewDcreenData, 0, 1);
            ivScreenSelect.setImageResource(R.drawable.icon_select_down);
        } else if (2 == type) {
            popWindow.showAsDropDown(viewDcreenReply, 0, 1);
            ivScreenReply.setImageResource(R.drawable.icon_select_down);
        } else if (3 == type) {
            popWindow.showAsDropDown(viewMyDcreenReply, 0, 1);
            getActivityMethod(1);
        } else if (4 == type) {
            popWindow.showAsDropDown(viewMyDcreenData, 0, 1);
            getActivityMethod(2);
        }

        popWindow.setOnDismissListener(() -> {
            if (1 == type) {
                ivScreenSelect.setImageResource(R.drawable.icon_select_up);
            } else if (2 == type) {
                ivScreenReply.setImageResource(R.drawable.icon_select_up);
            } else if (3 == type) {
                getActivityMethod(3);
            } else if (4 == type) {
                getActivityMethod(4);
            }
        });

    }

    /**
     * 弹框收起和关闭的状态
     *
     * @param type
     */
    private void getActivityMethod(int type) {
        FragmentActivity activity = getActivity();
        if (activity instanceof QuestionsAndAnswersActivity) {
            QuestionsAndAnswersActivity activitys = (QuestionsAndAnswersActivity) activity;
            if (type == 1) {
                activitys.selectTimeUp();
            } else if (type == 2) {
                activitys.selectReplyUp();
            } else if (type == 3) {
                activitys.selectTimeDown();
            } else if (type == 4) {
                activitys.selectReplyDown();
            }
        }
    }

    /**
     * 弹框设置文字
     *
     * @param name
     */
    private void setActivityText(String name, int type) {
        FragmentActivity activity = getActivity();
        if (activity instanceof QuestionsAndAnswersActivity) {
            QuestionsAndAnswersActivity activitys = (QuestionsAndAnswersActivity) activity;
            if (type == 3) {
                activitys.setReplyText(name);
            } else if (type == 4) {
                activitys.setDataText(name);
            }
        }
    }

    private void closePopWindow() {
        if (popWindow != null) {
            popWindow.dismiss();
            popWindow = null;
        }
    }

    private void loadData(boolean isReFresh) {
        if (mPresenter != null) {
            mPresenter.getQuestionList(mType, pageNo, pageSize, realmId, timeSort, isReply, isReFresh);
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
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void showWithdrawalRecordRefresh(List<SelectedAnswersBean> withdrawalRecordBeans) {
        if (mAdapter != null) {
            mAdapter.setNewData(withdrawalRecordBeans);
        }
        stopRefreshAndLoadMore();
    }

    @Override
    public void showWithdrawalRecordLoadMore(List<SelectedAnswersBean> withdrawalRecordBeans) {
        if (mAdapter != null) {
            mAdapter.addData(withdrawalRecordBeans);
        }
        stopRefreshAndLoadMore();
    }

    @Override
    public void collectLikeSuccess(int position, int likeStatus) {
        Integer thumbsUpCount = mAdapter.getData().get(position).getLikeNum();
        mAdapter.getData().get(position).setLikeNum(likeStatus == 1 ? thumbsUpCount + 1 : thumbsUpCount - 1);
        mAdapter.getData().get(position).setLikeStatus(likeStatus == 0 ? 0 : 1);
        mAdapter.notifyItemChanged(position);
    }

    @Override
    public void canLoadMore() {
        if (srlSelectedAnswers != null) {
            srlSelectedAnswers.setEnableLoadMore(true);
        }
    }

    @Override
    public void cannotLoadMore() {
        if (srlSelectedAnswers != null) {
            srlSelectedAnswers.setEnableLoadMore(false);
        }
    }

    @Override
    public void stopRefreshAndLoadMore() {
        if (srlSelectedAnswers != null) {
            srlSelectedAnswers.finishRefresh();
            srlSelectedAnswers.finishLoadMore();
        }
    }

    @Override
    public void showEmptyView() {
        if (mEmptyView != null) {
            mEmptyView.setVisibility(View.VISIBLE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventBean(EventBean eventBean) {
        if (eventBean != null) {
            int type = eventBean.getType();
            if (type == EventBean.QUESTIONS_ID && mType == 1) {
                if (mPresenter != null) {
                    realmId = "" + eventBean.getMsg();
                    pageNo = 1;
                    loadData(true);
                }
            } else if (type == EventBean.MY_ANSWERS_ID && mType == 2) {
                realmId = "" + eventBean.getMsg();
                pageNo = 1;
                loadData(true);
            }
        }
    }
}