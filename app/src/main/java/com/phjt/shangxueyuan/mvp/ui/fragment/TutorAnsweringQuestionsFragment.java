package com.phjt.shangxueyuan.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.phjt.base.base.BaseFragment;
import com.phjt.base.base.delegate.IFragment;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.TutorAnsweringQuestionsBean;
import com.phjt.shangxueyuan.bean.event.EventBean;
import com.phjt.shangxueyuan.di.component.DaggerTutorAnsweringQuestionsComponent;
import com.phjt.shangxueyuan.mvp.contract.TutorAnsweringQuestionsContract;
import com.phjt.shangxueyuan.mvp.presenter.TutorAnsweringQuestionsPresenter;
import com.phjt.shangxueyuan.mvp.ui.activity.CurrencyRechargeActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.OrderConfirmActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.PutQuestionsToActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.QuestionsAndAnswersActivity;
import com.phjt.shangxueyuan.mvp.ui.adapter.TutorAnsweringQuestionsAdapter;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.shangxueyuan.widgets.ScreenPopWindow;
import com.phjt.shangxueyuan.widgets.dialog.DialogUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import javax.inject.Singleton;

import butterknife.BindView;

import static com.phjt.base.utils.Preconditions.checkNotNull;

/**
 * @author : gengyan
 * @company : Copyright (c) 2021 普华集团 All rights reserved
 * @date : 2021/06/18 14:21
 * @description :
 */
public class TutorAnsweringQuestionsFragment extends BaseFragment<TutorAnsweringQuestionsPresenter> implements TutorAnsweringQuestionsContract.View,
        BaseQuickAdapter.OnItemChildClickListener {
    public static final String BUNDLE_NAME = "name";

    @BindView(R.id.rv_tutor)
    RecyclerView rvTutor;
    @BindView(R.id.srl_tutor)
    SmartRefreshLayout srlTutor;

    @BindView(R.id.view_my_consultation_reply)
    View viewMyDcreenReply;
    @BindView(R.id.view_my_consultation_data)
    View viewMyDcreenData;

    private ScreenPopWindow popWindow;
    private int pageNo = 1;
    private View mFooterView;
    private TutorAnsweringQuestionsAdapter mAdapter;
    private String realmId = "";
    private View emptyView;
    private int pageSize = 10;
    /**
     * 0 导师解答疑 1 咨询答疑  3 我的咨询
     */
    private int mType;

    /**
     * （0-未回答，1-已回答，2-已忽略)
     */
    private int isReply = 1;
    /**
     * 1-倒序，2-正序
     */
    private int timeSort = 1;

    public static TutorAnsweringQuestionsFragment newInstance(int type) {
        Bundle args = new Bundle();
        TutorAnsweringQuestionsFragment fragment = new TutorAnsweringQuestionsFragment();
        args.putInt(BUNDLE_NAME, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerTutorAnsweringQuestionsComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tutor_answering_questions, container, false);
    }

    /**
     * 在 onActivityCreate()时调用
     */
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        mType = bundle.getInt(BUNDLE_NAME, 0);
        rvTutor.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new TutorAnsweringQuestionsAdapter(getActivity(), mType);
        rvTutor.setAdapter(mAdapter);
        mFooterView = View.inflate(getContext(), R.layout.item_invitation_list_foot, null);
        mFooterView.setVisibility(View.GONE);
        mAdapter.setFooterView(mFooterView);
        emptyView = LayoutInflater.from(mContext).inflate(R.layout.empty_layout, null);
        mAdapter.setEmptyView(emptyView);
        TextView tvNodata = emptyView.findViewById(R.id.tv_nodata);
        if (mType == 1 || mType == 3) {
            tvNodata.setText("还没有咨询答疑 ~");
        }
        emptyView.setVisibility(View.GONE);
        mAdapter.setOnItemChildClickListener(this);
        setOnItemClickListener();
        initRefresh();
    }


    @Singleton
    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.tv_payment:
                TutorAnsweringQuestionsBean bean = mAdapter.getData().get(position);
                if (mType == 0) {
                    //去提问
                    Intent intent = new Intent(getActivity(), PutQuestionsToActivity.class);
                    intent.putExtra(Constant.TUTOR_ID, bean.getId());
                    intent.putExtra(Constant.BUNDLE_ORDER_REALPRICE, bean.getQuestionCoin());
                    intent.putExtra(Constant.BUNDLE_ORDER_COMMODITY_TYPE, 4);
                    startActivity(intent);
                } else if (mType == 1 && bean.getFrozenStatus() != 1) {
                    Intent intent = new Intent(getActivity(), OrderConfirmActivity.class);
                    intent.putExtra(Constant.BUNDLE_ORDER_NAME, getString(R.string.question_ask));
                    intent.putExtra(Constant.BUNDLE_ORDER_REALPRICE, bean.getQuestionCoin());
                    intent.putExtra(Constant.BUNDLE_ORDER_COMMODITY_TYPE, 16);
                    intent.putExtra(Constant.BUNDLE_ORDER_COMMODITYID, bean.getId());
                    startActivity(intent);
                } else if (mType == 1 && bean.getFrozenStatus() == 1) {
                    TipsUtil.show("该达人已冻结");
                }
                break;
            default:
                break;
        }

    }

    public void setOnItemClickListener() {
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            TutorAnsweringQuestionsBean bean = mAdapter.getData().get(position);
            if (mType == 3 && bean.getFrozenStatus() != 1) {
                Intent intent = new Intent(getActivity(), OrderConfirmActivity.class);
                intent.putExtra(Constant.BUNDLE_ORDER_NAME, getString(R.string.question_ask));
                intent.putExtra(Constant.BUNDLE_ORDER_REALPRICE, bean.getQuestionCoin());
                intent.putExtra(Constant.BUNDLE_ORDER_COMMODITY_TYPE, 16);
                intent.putExtra(Constant.BUNDLE_ORDER_COMMODITYID, bean.getConsultTeachcerId());
                startActivity(intent);
            } else if (mType == 3 && bean.getFrozenStatus() == 1) {
                TipsUtil.show("该达人已冻结");
            }
        });
    }

    private void initRefresh() {
        srlTutor.autoRefresh();
        srlTutor.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (mPresenter != null && mType == 3) {
                    pageNo += 1;
                    loadData(false);
                } else if (mPresenter != null) {
                    pageNo += 1;
                    mPresenter.getTutorInfoList(pageNo, pageSize, realmId, mType, false);
                }
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (mPresenter != null && mType == 3) {
                    pageNo = 1;
                    loadData(true);
                } else if (mPresenter != null) {
                    pageNo = 1;
                    mPresenter.getTutorInfoList(pageNo, pageSize, realmId, mType, true);
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


    @Override
    public void onPause() {
        super.onPause();

    }


    @Override
    public void showWithdrawalRecordRefresh(List<TutorAnsweringQuestionsBean> withdrawalRecordBeans) {
        if (mAdapter != null) {
            mAdapter.setNewData(withdrawalRecordBeans);
        }
        stopRefreshAndLoadMore();
    }

    @Override
    public void showWithdrawalRecordLoadMore(List<TutorAnsweringQuestionsBean> withdrawalRecordBeans) {
        if (mAdapter != null) {
            mAdapter.addData(withdrawalRecordBeans);
        }
        stopRefreshAndLoadMore();
    }

    @Override
    public void canLoadMore() {
        if (srlTutor != null) {
            srlTutor.setEnableLoadMore(true);
        }
    }

    @Override
    public void cannotLoadMore() {
        if (srlTutor != null) {
            srlTutor.setEnableLoadMore(false);
        }
    }

    @Override
    public void stopRefreshAndLoadMore() {
        if (srlTutor != null) {
            srlTutor.finishRefresh();
            srlTutor.finishLoadMore();
        }
    }

    @Override
    public void showEmptyView() {
        if (emptyView != null) {
            emptyView.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void checkUserCapitalSuccess(TutorAnsweringQuestionsBean bean) {
        askQuestions(bean);
    }

    @Override
    public void checkUserCapitalFail(String questionCoin) {
        notEnoughCoin(questionCoin);
    }


    /**
     * 去提问
     */
    private void askQuestions(TutorAnsweringQuestionsBean bean) {
        DialogUtils.showCancelSureDialog(getActivity(), "提问将支付学豆",
                String.format(getString(R.string.str_tips), bean.getQuestionCoin()),
                getString(R.string.quit_cancel), getString(R.string.quit_sure), new DialogUtils.OnCancelSureClick() {
                    @Override
                    public void clickCancel() {
                        //再想想
                    }

                    @Override
                    public void clickSure() {
                        //去提问

                    }
                }
        );
    }

    /**
     * 学分不足
     */
    private void notEnoughCoin(String questionCoin) {
        DialogUtils.showCancelSureDialog(getActivity(), "当前学豆不足，请去充值",
                String.format(getString(R.string.str_tips), questionCoin),
                getString(R.string.quit_cancel), getString(R.string.quit_sure), new DialogUtils.OnCancelSureClick() {

                    @Override
                    public void clickSure() {
                        //去充值
                        Intent intent = new Intent(mContext, CurrencyRechargeActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }


    /**
     * 获取筛选
     */
    public void setPopWindow(int type) {
        closePopWindow();
        popWindow = new ScreenPopWindow(getActivity(), type, timeSort, isReply, new ScreenPopWindow.PopInter() {
            @Override
            public void sure(int selectContentId, String name) {
                if (3 == type) {
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

        if (3 == type) {
            popWindow.showAsDropDown(viewMyDcreenReply, 0, 1);
            getActivityMethod(1);
        } else if (4 == type) {
            popWindow.showAsDropDown(viewMyDcreenData, 0, 1);
            getActivityMethod(2);
        }

        popWindow.setOnDismissListener(() -> {
            if (3 == type) {
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
            mPresenter.getMyConsultationList(pageNo, pageSize, realmId, timeSort, isReply, isReFresh);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventBean(EventBean eventBean) {
        if (eventBean != null) {
            int type = eventBean.getType();
            if (type == EventBean.REALM_ID) {
                if (mPresenter != null && mType == 3) {
                    pageNo = 1;
                    loadData(true);
                } else if (mPresenter != null) {
                    realmId = "" + eventBean.getMsg();
                    pageNo = 1;
                    mPresenter.getTutorInfoList(pageNo, pageSize, realmId, mType, true);
                }
            }
        }
    }
}