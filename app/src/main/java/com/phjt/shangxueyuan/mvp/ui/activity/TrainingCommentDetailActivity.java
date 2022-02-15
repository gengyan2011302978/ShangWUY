package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import android.text.TextUtils;
import android.view.KeyEvent;
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
import com.phjt.shangxueyuan.annotate.SingleClick;
import com.phjt.shangxueyuan.bean.TrainingCommentBean;
import com.phjt.shangxueyuan.common.AppImageLoader;
import com.phjt.shangxueyuan.di.component.DaggerTrainingCommentDetailComponent;
import com.phjt.shangxueyuan.interf.IWithoutImmersionBar;
import com.phjt.shangxueyuan.mvp.contract.TrainingCommentDetailContract;
import com.phjt.shangxueyuan.mvp.presenter.TrainingCommentDetailPresenter;
import com.phjt.shangxueyuan.mvp.ui.adapter.ReleasedAdapter;
import com.phjt.shangxueyuan.mvp.ui.adapter.TrainingReplayAdapter;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.CountNumUtils;
import com.phjt.shangxueyuan.utils.SoftKeyboardUtil;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.shangxueyuan.utils.VipUtil;
import com.phjt.view.roundImg.RoundedImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;


/**
 * @author: Created by GengYan
 * date: 02/04/2021 11:17
 * company: 普华集团
 * description: 训练营评论详情页
 */
public class TrainingCommentDetailActivity extends BaseActivity<TrainingCommentDetailPresenter> implements
        TrainingCommentDetailContract.View, IWithoutImmersionBar, BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.tv_common_title)
    TextView mTvCommonTitle;
    @BindView(R.id.iv_reply_icon)
    RoundedImageView mIvReplyIcon;
    @BindView(R.id.tv_reply_phone)
    TextView mTvReplyPhone;
    @BindView(R.id.iv_reply_vip)
    ImageView mIvReplyVip;
    @BindView(R.id.tv_reply_time)
    TextView mTvReplyTime;
    @BindView(R.id.tv_reply_content)
    TextView mTvReplyContent;
    @BindView(R.id.rv_reply_img)
    RecyclerView mRvReplyImg;
    @BindView(R.id.iv_like)
    ImageView mIvLike;
    @BindView(R.id.tv_like)
    TextView mTvLike;
    @BindView(R.id.iv_reply)
    ImageView mIvReply;
    @BindView(R.id.tv_reply)
    TextView mTvReply;
    @BindView(R.id.tv_reply_count)
    TextView mTvReplyCount;
    @BindView(R.id.rv_comment_reply)
    RecyclerView mRvCommentReply;
    @BindView(R.id.ll_write_comment)
    LinearLayout mLlWriteComment;
    @BindView(R.id.ll_reply_layout)
    LinearLayout mLlReplyLayout;
    @BindView(R.id.et_reply)
    EditText mEtReply;

    /**
     * 评论实体 和 评论id
     */
    private TrainingCommentBean.DiaryListBean mCommentBean;
    private String mCommentId;

    private TrainingReplayAdapter replayAdapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerTrainingCommentDetailComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_training_comment_detail;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(R.color.color_white)
                .statusBarDarkFont(true).keyboardEnable(true).init();

        mTvCommonTitle.setText("评论详情");
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mCommentBean = (TrainingCommentBean.DiaryListBean) bundle.getSerializable(Constant.BUNDLE_TRAINING_COMMENT_BEAN);
        }

        if (mCommentBean != null) {
            mCommentId = mCommentBean.getId();
            AppImageLoader.loadResUrl(mCommentBean.getPhoto(), mIvReplyIcon, R.drawable.iv_mine_avatar);
            mTvReplyPhone.setText(mCommentBean.getNickname());
            VipUtil.setVipImage(mCommentBean.getVipState(), mIvReplyVip);
            mTvReplyTime.setText(mCommentBean.getCreateTime());
            mTvReplyContent.setText(mCommentBean.getContent());

            GridLayoutManager layoutManager = new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false);
            mRvReplyImg.setLayoutManager(layoutManager);
            ReleasedAdapter mAdapter = new ReleasedAdapter(this);
            mRvReplyImg.setAdapter(mAdapter);
            if (!TextUtils.isEmpty(mCommentBean.getDiaryImg())) {
                List<String> imgList = Arrays.asList(mCommentBean.getDiaryImg().split(","));
                mAdapter.setNewData(imgList);
                mAdapter.setOnItemClickListener((adapter, view, position) -> {
                    Intent intent = new Intent(this, BigPhotoActivity.class);
                    intent.putStringArrayListExtra(Constant.BUNDLE_BIG_IMAGE_URLS, new ArrayList<>(imgList));
                    intent.putExtra(Constant.BUNDLE_BIG_IMAGE_POSITION, position);
                    intent.putExtra(Constant.BUNDLE_BIG_IMAGE_TYPE, 1);
                    intent.putExtra(Constant.BUNDLE_BIG_IMAGE_PRE, "");
                    ArchitectUtils.startActivity(intent);
                });
            } else {
                mAdapter.setNewData(null);
            }
            setLikeState();
            setReplyCount();

            LinearLayoutManager replyLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            mRvCommentReply.setLayoutManager(replyLayoutManager);
            //处理RecycleView item刷新闪烁的问题
            ((SimpleItemAnimator) Objects.requireNonNull(mRvCommentReply.getItemAnimator())).setSupportsChangeAnimations(false);
            replayAdapter = new TrainingReplayAdapter(this, mCommentBean.getReplyVoList());
            mRvCommentReply.setAdapter(replayAdapter);
            replayAdapter.setOnItemChildClickListener(this);
        }
    }

    /**
     * 设置当前评论点赞
     */
    private void setLikeState() {
        if (mCommentBean != null && mIvLike != null && mTvLike != null) {
            mIvLike.setSelected(mCommentBean.getLikestatus() != 0);
            mTvLike.setText(CountNumUtils.getCountNum(mCommentBean.getLikeNum()));
        }
    }

    /**
     * 设置评论回复数
     **/
    private void setReplyCount() {
        if (mTvReply != null && mTvReplyContent != null && mCommentBean != null) {
            mTvReply.setText(String.valueOf(mCommentBean.getCommentNum()));
            mTvReplyCount.setText(String.format(Locale.getDefault(), "（%d）", mCommentBean.getCommentNum()));
        }
    }

    @SingleClick
    @OnClick({R.id.iv_common_back, R.id.iv_like, R.id.tv_like, R.id.ll_write_comment, R.id.iv_reply, R.id.tv_reply, R.id.tv_reply_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_common_back:
                onBackResult();
                break;
            case R.id.iv_like:
            case R.id.tv_like:
                //点赞
                if (mPresenter != null && !TextUtils.isEmpty(mCommentId)) {
                    mPresenter.trainingCommentLike(mCommentId);
                }
                break;
            case R.id.ll_write_comment:
                //写评论
                mLlWriteComment.setVisibility(View.GONE);
                mLlReplyLayout.setVisibility(View.VISIBLE);
                SoftKeyboardUtil.showKeyboard(mEtReply);
                break;
            case R.id.tv_reply_send:
                //发送回复
                String content = mEtReply.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    showMessage("内容为空，请输入");
                    return;
                } else if (mPresenter != null && !TextUtils.isEmpty(mCommentId)) {
                    mPresenter.addCommentReply(mCommentId, content);
                }
                break;
            default:
                break;
        }
    }

    @SingleClick
    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        TrainingCommentBean.ReplyVoListBean replyVoListBean = (TrainingCommentBean.ReplyVoListBean) adapter.getData().get(position);
        if (replyVoListBean != null) {
            switch (view.getId()) {
                case R.id.iv_like:
                case R.id.tv_like:
                    //回复点赞
                    if (mPresenter != null) {
                        mPresenter.replyLike(replyVoListBean, position);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void getReplyList(List<TrainingCommentBean.ReplyVoListBean> replyVoListBeans) {
        if (replayAdapter != null) {
            replayAdapter.setNewData(replyVoListBeans);
        }
        if (mCommentBean != null) {
            mCommentBean.setCommentNum(replyVoListBeans == null ? 0 : replyVoListBeans.size());
            mCommentBean.setReplyVoList(replyVoListBeans);
        }
        setReplyCount();
    }

    @Override
    public void addReplySuccess() {
        SoftKeyboardUtil.hideSoftKeyboard(this);
        mEtReply.setText("");
        if (mPresenter != null && !TextUtils.isEmpty(mCommentId)) {
            mPresenter.getCommentReplyList(mCommentId);
        }
    }

    @Override
    public void replyLikeChange(TrainingCommentBean.ReplyVoListBean replyVoListBean, int position) {
        if (replayAdapter != null) {
            replayAdapter.notifyItemChanged(position);
        }
    }

    @Override
    public void trainingCommentLikeSuccess() {
        if (mCommentBean != null) {
            //0 未点赞
            int likestatus = mCommentBean.getLikestatus();
            int likeNum = mCommentBean.getLikeNum();
            if (likestatus == 0) {
                mCommentBean.setLikestatus(1);
                mCommentBean.setLikeNum(likeNum + 1);
            } else {
                mCommentBean.setLikestatus(0);
                mCommentBean.setLikeNum(Math.max(likeNum - 1, 0));
            }
            setLikeState();
        }
    }

    private void onBackResult() {
        Intent intent = new Intent();
        intent.putExtra(Constant.BUNDLE_TRAINING_COMMENT_BEAN, mCommentBean);
        setResult(Constant.COMMENT_DETAIL_RESULT_CODE, intent);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackResult();
            return true;
        }
        return super.onKeyDown(keyCode, event);
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
