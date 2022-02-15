package com.phjt.shangxueyuan.mvp.ui.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.phjt.base.base.delegate.IFragment;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.integration.EventBusManager;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.event.EventBean;
import com.phjt.shangxueyuan.di.component.DaggerReplyJournalComponent;
import com.phjt.shangxueyuan.mvp.contract.ReplyJournalContract;
import com.phjt.shangxueyuan.mvp.presenter.ReplyJournalPresenter;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.SoftKeyboardUtil;
import com.phjt.shangxueyuan.utils.TipsUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.phjt.base.utils.Preconditions.checkNotNull;


/**
 * Created by Template
 *
 * @author :
 * description :回复日记
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/01/16 14:46
 */

public class ReplyJournalFragment extends BaseNoteFragment<ReplyJournalPresenter> implements ReplyJournalContract.View, TextWatcher {

    @BindView(R.id.iv_back_img)
    ImageView ivBackImg;
    @BindView(R.id.tv_publish)
    TextView tvPublish;
    @BindView(R.id.edit_reply_journal)
    EditText editReplyJournal;
    @BindView(R.id.tv_input_num)
    TextView tvInputNum;
    @BindView(R.id.fl_edit_feedback)
    FrameLayout flEditFeedback;
    private Context context;
    Unbinder unbinder;
    private static final int MAX_TEXT_NUMBER = 3000;
    private String bundleAddDiaryId = "";
    private String punchCardId = "";
    private String motifId = "";
    private String commentId = "";

    /**
     * 提供静态工厂方法 避免在实例化是利用构造方法初始化对象
     *
     * @return 对应Fragment实例
     */
    public static ReplyJournalFragment newInstance() {
        ReplyJournalFragment fragment = new ReplyJournalFragment();
        return fragment;
    }


    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerReplyJournalComponent //Dagger 编译时生成代码,报错先请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @androidx.annotation.Nullable ViewGroup container, @androidx.annotation.Nullable Bundle savedInstanceState) {
        context = getContext();
        View view = inflater.inflate(R.layout.fragment_reply_journal, container, false);
        unbinder = ButterKnife.bind(this, view);
        setDatas();
        return view;
    }

    private void setDatas() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            bundleAddDiaryId = bundle.getString(Constant.BUNDLE_ADD_DIARY_ID);
            punchCardId = bundle.getString(Constant.BUNDLE_ADD_PUNCH_CARD_ID);
            motifId = bundle.getString(Constant.BUNDLE_ADD_MOTIF_ID);
            commentId = bundle.getString(Constant.BUNDLE_ADD_COMMENT_ID);
        }
        editReplyJournal.addTextChangedListener(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return null;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onStart() {
        super.onStart();
        //获取dialog对象
        BottomSheetDialog mBottomSheetDialog = (BottomSheetDialog) getDialog();
        //把windowsd的默认背景颜色去掉，不然圆角显示不见
        mBottomSheetDialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //获取diglog的根部局
        FrameLayout bottomSheet = mBottomSheetDialog.getDelegate().findViewById(R.id.design_bottom_sheet);
        if (bottomSheet != null) {
            //获取根部局的LayoutParams对象
            CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomSheet.getLayoutParams();
            layoutParams.height = getPeekHeight();
            //修改弹窗的最大高度，不允许上滑（默认可以上滑）
            bottomSheet.setLayoutParams(layoutParams);

            final BottomSheetBehavior<FrameLayout> behavior = BottomSheetBehavior.from(bottomSheet);
            //peekHeight即弹窗的最大高度
            behavior.setPeekHeight(getPeekHeight());
            // 初始为展开状态
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
        mBottomSheetDialog.setCanceledOnTouchOutside(false);
        mBottomSheetDialog.setCancelable(false);
    }

    /**
     * 弹窗高度，默认为屏幕高度的四分之三
     * 子类可重写该方法返回peekHeight
     *
     * @return height
     */
    protected int getPeekHeight() {
        int peekHeight = getResources().getDisplayMetrics().heightPixels;
        //设置弹窗高度为屏幕高度的3/4
        return (int) (peekHeight - peekHeight / 9.5);
    }

    @Override
    public Dialog onCreateDialog(@androidx.annotation.Nullable Bundle savedInstanceState) {
        //返回BottomSheetDialog的实例
        return new BottomSheetDialog(this.getContext());
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


    @OnClick({R.id.iv_back_img, R.id.tv_publish})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back_img:
                SoftKeyboardUtil.setHideKeyboard(ivBackImg);
                if (null != editReplyJournal) {
                    editReplyJournal.setText("");
                }
                dismiss();
                break;
            case R.id.tv_publish:
                SoftKeyboardUtil.setHideKeyboard(tvPublish);
                String mContent = "";
                if (null != editReplyJournal) {
                    mContent = editReplyJournal.getText().toString().trim();
                }
                if (TextUtils.isEmpty(mContent)) {
                    TipsUtil.show("请输入评论内容");
                    return;
                }
                if (mPresenter != null) {
                    tvPublish.setEnabled(false);
                    // 发布评论
                    mPresenter.addComment(bundleAddDiaryId, punchCardId, motifId, commentId, mContent);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String tvNickName = editReplyJournal.getText().toString().trim();
        if (tvNickName.length() == 0) {
            tvPublish.setBackgroundResource(R.drawable.bg_publish_un);
            tvPublish.setTextColor(ContextCompat.getColor(context, R.color.color_666666));
        } else {
            tvPublish.setBackgroundResource(R.drawable.bg_publish);
            tvPublish.setTextColor(Color.WHITE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        int length = s.toString().replaceAll(" ", "").length();
        tvInputNum.setText(length + "/" + MAX_TEXT_NUMBER);
        tvInputNum.setTextColor(ContextCompat.getColor(getActivity(), length > MAX_TEXT_NUMBER ? R.color.colorTintRed : R.color.color_333333));
    }

    @Override
    public void addCommentSuccess() {
        dismiss();
        EventBusManager.getInstance().post(new EventBean(EventBean.ADD_DIARY, null));
        if (null != editReplyJournal) {
            editReplyJournal.setText("");
        }
        tvPublish.setEnabled(true);
    }

    @Override
    public void addCommentFail() {
        tvPublish.setEnabled(true);
    }
}
