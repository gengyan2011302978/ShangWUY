package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.immersionbar.ImmersionBar;
import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.MotifDetailBean;
import com.phjt.shangxueyuan.bean.MotifDiaryListBean;
import com.phjt.shangxueyuan.common.CommonHttpManager;
import com.phjt.shangxueyuan.di.component.DaggerHistoryThemeComponent;
import com.phjt.shangxueyuan.mvp.contract.HistoryThemeContract;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;
import com.phjt.shangxueyuan.mvp.presenter.HistoryThemePresenter;
import com.phjt.shangxueyuan.mvp.ui.adapter.ThemeHistoryAdapter;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.RxUtils;
import com.phjt.shangxueyuan.utils.ShareUtils;
import com.phjt.shangxueyuan.utils.SoftKeyboardUtil;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.shangxueyuan.utils.crash.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;


/**
 * @author: Created by GengYan
 * date: 01/28/2021 17:08
 * company: 普华集团
 * description: 模版请保持更新
 */
public class HistoryThemeActivity extends BaseActivity<HistoryThemePresenter> implements HistoryThemeContract.View, TextWatcher {

    @BindView(R.id.tv_common_left)
    TextView tvCommonLeft;
    @BindView(R.id.iv_common_back)
    ImageView ivCommonBack;
    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.tv_common_right)
    TextView tvCommonRight;
    @BindView(R.id.ic_common_right)
    ImageView icCommonRight;
    @BindView(R.id.tv_common_right_collection)
    TextView tvCommonRightCollection;
    @BindView(R.id.ic_common_right_sweep)
    ImageView icCommonRightSweep;
    @BindView(R.id.ic_common_right_collect)
    ImageView icCommonRightCollect;
    @BindView(R.id.ic_common_right_collection)
    ImageView icCommonRightCollection;
    @BindView(R.id.tv_main_info)
    TextView tvMainInfo;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.recycle_comment)
    RecyclerView recycleComment;
    ThemeHistoryAdapter themeListAdapter;
    @BindView(R.id.tv_motif_desc)
    TextView tvMotifDesc;
    @BindView(R.id.tv_change)
    TextView tvChange;
    @BindView(R.id.tv_journal)
    TextView tvJournal;
    @BindView(R.id.et_reply)
    EditText etReply;
    @BindView(R.id.tv_reply_send)
    TextView tvReplySend;
    @BindView(R.id.ll_reply_layout)
    LinearLayout llReplyLayout;

    private View mEmptyView;
    MotifDetailBean bean;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
    SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    /**
     * 主题id
     */
    private String motifId;
    /**
     * 日记ID
     */
    private String diaryId;
    private String mContent;
    private String trainingId;
    private String couId;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerHistoryThemeComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_history_theme;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(R.color.color_white)
                .statusBarDarkFont(true).keyboardEnable(true).init();
        icCommonRight.setVisibility(View.VISIBLE);
        icCommonRight.setBackgroundResource(R.drawable.icon_share_right);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recycleComment.setLayoutManager(layoutManager);
        themeListAdapter = new ThemeHistoryAdapter(this);
        mEmptyView = LayoutInflater.from(this).inflate(R.layout.empty_theme_layout, null);
        themeListAdapter.setEmptyView(mEmptyView);
        mEmptyView.setVisibility(View.GONE);
        recycleComment.setAdapter(themeListAdapter);
        motifId = getIntent().getStringExtra(Constant.BUNDLE_ADD_MOTIF_ID);

        themeListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                MotifDiaryListBean motifDiaryListBean = (MotifDiaryListBean) adapter.getData().get(position);
                switch (view.getId()) {
                    case R.id.iv_like:
                        if (mPresenter != null) {
                            if ("0".equals(motifDiaryListBean.getState())) {
                                motifDiaryListBean.setState("1");
                                motifDiaryListBean.setLikeNum(motifDiaryListBean.getLikeNum() + 1);
                            } else {
                                motifDiaryListBean.setState("0");
                                motifDiaryListBean.setLikeNum(motifDiaryListBean.getLikeNum() - 1);
                            }
                            mPresenter.punchThumbsUp(motifDiaryListBean.getMotifId(), "1", motifDiaryListBean.getState());

                            adapter.notifyDataSetChanged();
                        }
                        break;
                    case R.id.tv_edit_comment:
                        diaryId = motifDiaryListBean.getId() + "";
                        motifId = motifDiaryListBean.getMotifId() + "";
                        showEditDialog();
                        break;
                    default:
                        break;
                }
            }
        });
        etReply.addTextChangedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.motifDetail(getIntent().getStringExtra("id"), getIntent().getStringExtra("punchCardId"));
            mPresenter.diaryList(getIntent().getStringExtra("punchCardId"), "1", getIntent().getStringExtra("id"));
        }
    }

    /**
     * 弹框评论弹框
     */
    private void showEditDialog() {
        llReplyLayout.setVisibility(View.VISIBLE);
        SoftKeyboardUtil.showKeyboard(etReply);
    }

    @Override
    public void motifDetailSucceed(MotifDetailBean motifDetailBean) {
        trainingId = getIntent().getStringExtra("trainingCampId");
        if (motifDetailBean.getPunchCardType().equals("0") || motifDetailBean.getPunchCardType().equals("3")) {
            couId = motifDetailBean.getOtherId();
        } else if (motifDetailBean.getPunchCardType().equals("1")) {
            couId = motifDetailBean.getOtherId();
        } else if (motifDetailBean.getPunchCardType().equals("2")) {
            trainingId = motifDetailBean.getOtherId();
        }
        bean = motifDetailBean;
        tvTitle.setText(motifDetailBean.getMotifTitle());
        tvDate.setText(motifDetailBean.getMotifDate());
        tvMotifDesc.setText(motifDetailBean.getMotifDescribe());
        if (motifDetailBean.getNowDate().contains(motifDetailBean.getMotifDate())) {
            tvCommonTitle.setText("今日主题");
        } else {
            tvCommonTitle.setText("历史主题");
        }
        try {
            if (simpleTimeFormat.parse(motifDetailBean.getNowDate().split("\\s+")[0]).before(simpleTimeFormat.parse(motifDetailBean.getMotifDate()))) {
                tvCommonTitle.setText("打卡主题");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (motifDetailBean.getPunchCardState() == 1) {
            tvJournal.setText("已打卡");
            tvJournal.setBackgroundResource(R.drawable.bg_e6e6e6_rectangle);
            tvJournal.setClickable(false);
        }
        try {
            if (Utils.belongCalendar(simpleDateFormat.parse(motifDetailBean.getNowDate().split("\\s+")[1]), simpleDateFormat.parse(motifDetailBean.getPunchCardStartTime()), simpleDateFormat.parse(motifDetailBean.getPunchCardEndTime()))) {
                if (Utils.belongCalendar(simpleTimeFormat.parse(motifDetailBean.getNowDate().split("\\s+")[0]), simpleTimeFormat.parse(motifDetailBean.getPunchCardStartDate()), simpleTimeFormat.parse(motifDetailBean.getPunchCardEndDate()))) {

                    if (motifDetailBean.getPunchCardState() == 0 && "1".equals(motifDetailBean.getReissueCardType())) {
                        if (motifDetailBean.getNowDate().contains(motifDetailBean.getMotifDate())) {
                            tvJournal.setText("立即打卡");
                            tvJournal.setBackgroundResource(R.drawable.bg_2675fe_rectangle);
                        } else {
                            tvJournal.setText("立即补打卡");
                            if (bean.getReissueCardNum().equals("0")) {
                                tvJournal.setBackgroundResource(R.drawable.bg_e6e6e6_rectangle);
                                tvJournal.setClickable(false);
                            } else {
                                tvJournal.setBackgroundResource(R.drawable.bg_2675fe_rectangle);
                            }

                        }
                    }
                    if (motifDetailBean.getPunchCardState() == 0 && motifDetailBean.getReissueCardType().equals("0")) {
                        if (simpleTimeFormat.parse(motifDetailBean.getNowDate().split("\\s+")[0]).after(simpleTimeFormat.parse(motifDetailBean.getMotifDate()))) {
                            tvJournal.setText("立即补打卡");
                            tvJournal.setBackgroundResource(R.drawable.bg_e6e6e6_rectangle);
                            tvJournal.setClickable(false);
                        } else {
                            tvJournal.setText("立即打卡");
                            tvJournal.setBackgroundResource(R.drawable.bg_2675fe_rectangle);
                        }
                    }

                    if (simpleTimeFormat.parse(motifDetailBean.getNowDate().split("\\s+")[0]).before(simpleTimeFormat.parse(motifDetailBean.getMotifDate()))) {
                        tvJournal.setText("不在打卡时间内");
                        tvJournal.setBackgroundResource(R.drawable.bg_e6e6e6_rectangle);
                        tvJournal.setClickable(false);
                    }
                } else {
//                    tvJournal.setText("不在打卡时间内");
//                    tvJournal.setBackgroundResource(R.drawable.bg_e6e6e6_rectangle);
//                    tvJournal.setClickable(false);
                }
            } else {
                if (motifDetailBean.getNowDate().contains(motifDetailBean.getMotifDate())) {
                    tvJournal.setText("不在打卡时间内");
                    tvJournal.setBackgroundResource(R.drawable.bg_e6e6e6_rectangle);
                    tvJournal.setClickable(false);
                }

                try {
                    if (simpleTimeFormat.parse(motifDetailBean.getNowDate().split("\\s+")[0]).before(simpleTimeFormat.parse(motifDetailBean.getMotifDate()))) {
                        tvJournal.setText("不在打卡时间内");
                        tvJournal.setBackgroundResource(R.drawable.bg_e6e6e6_rectangle);
                        tvJournal.setClickable(false);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } catch (ParseException e) {

        }
    }

    @Override
    public void diaryListSucceed(List<MotifDiaryListBean> motifDiaryListBean) {
        if (motifDiaryListBean.size() == 0) {
            mEmptyView.setVisibility(View.VISIBLE);
        }
        themeListAdapter.setNewData(motifDiaryListBean);
    }

    @Override
    public void punchThumbsUpSucceed(String state) {
        if ("0".equals(state)) {

        } else {

        }
    }

    @Override
    public void punchThumbsUpFail(String msg) {

    }

    @Override
    public void addCommentSuccess() {
        SoftKeyboardUtil.setHideKeyboard(tvReplySend);
        llReplyLayout.setVisibility(View.GONE);
        etReply.setText("");
        if (mPresenter != null) {
            mPresenter.diaryList(getIntent().getStringExtra("punchCardId"), "1", getIntent().getStringExtra("id"));
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String tvNickName = etReply.getText().toString().trim();
        if (tvNickName.length() == 0) {
            tvReplySend.setBackgroundResource(R.drawable.bg_publish_un);
            tvReplySend.setTextColor(ContextCompat.getColor(this, R.color.color_666666));
        } else {
            tvReplySend.setBackgroundResource(R.drawable.bg_publish);
            tvReplySend.setTextColor(Color.WHITE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @OnClick({R.id.iv_common_back, R.id.tv_change, R.id.tv_journal, R.id.tv_reply_send, R.id.ic_common_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_reply_send:
                if (mPresenter != null) {
                    if (null != etReply) {
                        mContent = etReply.getText().toString().trim();
                    }
                    if (TextUtils.isEmpty(mContent)) {
                        TipsUtil.show("请输入评论内容");
                        return;
                    }
                    mPresenter.addComment(diaryId, getIntent().getStringExtra("punchCardId"), motifId, "", mContent);
                }
                break;
            case R.id.iv_common_back:
                finish();
                break;
            case R.id.ic_common_right:
                CommonHttpManager.getInstance(this)
                        .obtainRetrofitService(ApiService.class)
                        .diaryShare(bean.getPunchCardId() + "", couId, trainingId, diaryId)
                        .compose(RxUtils.applySchedulersWithLoading(this))
                        .subscribe(shareBaseBean -> {
                            if (shareBaseBean.isOk()) {
                                ShareUtils.showSharePop(this, shareBaseBean.data);
                            }
                        }, throwable -> TipsUtil.showTips("获取分享内容有误"));
                break;
            case R.id.tv_change:
                if (tvChange.getText().toString().equals("按精选")) {
                    if (mPresenter != null) {
                        mPresenter.diaryList(getIntent().getStringExtra("punchCardId"), "1", getIntent().getStringExtra("id"));
                    }
                    tvChange.setText("按时间");
                } else {
                    if (mPresenter != null) {
                        mPresenter.diaryList(getIntent().getStringExtra("punchCardId"), "0", getIntent().getStringExtra("id"));
                    }
                    tvChange.setText("按精选");
                }
//                DialogUtils.showChangeListDialog(this, new DialogUtils.OnCancelSureClick() {
//                    @Override
//                    public void clickSure() {
//                        if (mPresenter != null) {
//                            mPresenter.diaryList(getIntent().getStringExtra("punchCardId"), "0", getIntent().getStringExtra("id"));
//                        }
//                        tvChange.setText("按时间");
//                    }
//
//                    @Override
//                    public void clickDelete() {
//                        if (mPresenter != null) {
//                            mPresenter.diaryList(getIntent().getStringExtra("punchCardId"), "1", getIntent().getStringExtra("id"));
//                        }
//                        tvChange.setText("按精选");
//                    }
//                });
                break;
            case R.id.tv_journal:
                if (bean != null) {
                    Intent intent = new Intent(HistoryThemeActivity.this, JournalActivity.class);
                    intent.putExtra(Constant.BUNDLE_ADD_DIARY_ID, "");
                    intent.putExtra(Constant.BUNDLE_ADD_PUNCH_CARD_ID, bean.getPunchCardId() + "");
                    intent.putExtra(Constant.BUNDLE_ADD_REISSUE_CARD_TYPE, bean.getReissueCardType());
                    intent.putExtra(Constant.BUNDLE_ADD_MOTIF_ID, bean.getId() + "");
                    intent.putExtra(Constant.BUNDLE_ADD_CALENDAR_DATE, bean.getMotifDate());
                    intent.putExtra(Constant.BUNDLE_ADD_NODE_TASK_LINK_ID, getIntent().getStringExtra("nodeTaskLinkId"));
                    intent.putExtra(Constant.BUNDLE_ADD_TRAINING_CAMP_ID, getIntent().getStringExtra("trainingCampId"));
                    if (tvJournal.getText().toString().contains("补")) {
                        intent.putExtra(Constant.BUNDLE_ADD_REISSUE_CARD_TYPE, 1);
                    }
                    startActivity(intent);
                }
                break;
            default:
                break;
        }
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
