package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Context;
import androidx.core.content.ContextCompat;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.SelectedAnswersBean;
import com.phjt.shangxueyuan.common.AppImageLoader;
import com.phjt.view.roundImg.RoundedImageView;

/**
 * @author: Roy
 * date:   2021/6/21
 * company: 普华集团
 * description:
 */
public class SelectedAnswersAdapter extends BaseQuickAdapter<SelectedAnswersBean, BaseViewHolder> {
    private Context mContext;
    private int mType;


    public SelectedAnswersAdapter(Context context, int type) {
        super(R.layout.item_selected_answers);
        this.mContext = context;
        this.mType = type;
    }


    @Override
    protected void convert(BaseViewHolder helper, SelectedAnswersBean item) {
        String answerName = "";
        if (!TextUtils.isEmpty(item.getAnswerName())) {
            if (item.getAnswerName().length() > 7) {
                answerName = item.getAnswerName().substring(0, 7);
            } else {
                answerName = item.getAnswerName();
            }
        }
        helper.setText(R.id.tv_questions_title, String.valueOf(item.getTitle()))
                .setText(R.id.tv_questions_time, item.getCreateTime())
                .setText(R.id.tv_questions_fabulous, String.valueOf(item.getLikeNum()) + "点赞")
                .setText(R.id.tv_questions_respondent, String.format(mContext.getString(R.string.questions_answers_respondent), answerName))
                .setText(R.id.tv_questions_label, String.valueOf(item.getRealmName()));

        TextView tvQuestionsFabulous = helper.getView(R.id.tv_questions_fabulous);
        ImageView ivQuestionsFabulous = helper.getView(R.id.iv_question_fabulous);

        RoundedImageView ivTutor = helper.getView(R.id.iv_tutor);
        AppImageLoader.loadResUrl("" + item.getAnswerPhoto(), ivTutor, R.drawable.iv_mine_avatar);


        ivQuestionsFabulous.setImageResource(item.getLikeStatus() == 0 ? R.drawable.ic_zan : R.drawable.ic_zan_s);
        tvQuestionsFabulous.setTextColor(item.getLikeStatus() == 0 ? ContextCompat.getColor(mContext, R.color.color_FF9B9C9A) : ContextCompat.getColor(mContext, R.color.color_FFFF650C));
        TextView mTvIntroduce = helper.getView(R.id.tv_questions_content);
        if (item.getIsReply() == 0) {
            //isReply 0-未回答，1-已回答，2-已忽略
            mTvIntroduce.setBackgroundResource(R.drawable.bg_card);
            helper.setVisible(R.id.iv_tutor, false)
                    .setVisible(R.id.tv_questions_respondent, false)
                    .setVisible(R.id.tv_questions_fabulous, false)
                    .setVisible(R.id.tv_questions_time, false)
                    .setVisible(R.id.tv_refuse_reason, false)
                    .setVisible(R.id.iv_question_fabulous, false)
                    .setVisible(R.id.tv_reply_transstate, false);
            setContent("" + item.getQuestionContent(), mTvIntroduce);
        } else if (item.getIsReply() == 1) {
            mTvIntroduce.setBackgroundResource(R.color.color_white);
            helper.setVisible(R.id.tv_reply_transstate, false)
                    .setVisible(R.id.tv_refuse_reason, false)
                    .setVisible(R.id.iv_tutor, true)
                    .setVisible(R.id.tv_questions_fabulous, true)
                    .setVisible(R.id.tv_questions_respondent, true)
                    .setVisible(R.id.iv_question_fabulous, true)
                    .setVisible(R.id.tv_questions_time, true);
            setContent("" + item.getContent(), mTvIntroduce);
        } else if (item.getIsReply() == 2) {
            mTvIntroduce.setBackgroundResource(R.drawable.bg_card);
            helper.setVisible(R.id.tv_reply_transstate, true)
                    .setVisible(R.id.tv_refuse_reason, true)
                    .setText(R.id.tv_refuse_reason, "忽略理由:" + item.getContent())
                    .setText(R.id.tv_reply_transstate, "已忽略")
                    .setVisible(R.id.tv_questions_fabulous, false)
                    .setVisible(R.id.iv_tutor, false)
                    .setVisible(R.id.tv_questions_respondent, false)
                    .setVisible(R.id.iv_question_fabulous, false)
                    .setVisible(R.id.tv_questions_time, false);
            setContent("" + item.getQuestionContent(), mTvIntroduce);
        }

        helper.getView(R.id.tv_questions_label).setVisibility(!TextUtils.isEmpty(item.getRealmName()) ? View.VISIBLE : View.GONE);
        helper.addOnClickListener(R.id.iv_question_fabulous);
        helper.addOnClickListener(R.id.tv_questions_fabulous);

    }

    public void setContent(String content, TextView tvIntroduce) {
        if (!TextUtils.isEmpty(content)) {
            CharSequence charSequence;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                charSequence = Html.fromHtml(content, Html.FROM_HTML_MODE_LEGACY);
            } else {
                charSequence = Html.fromHtml(content);
            }
            tvIntroduce.setText(charSequence);
            tvIntroduce.setVisibility(View.VISIBLE);
        } else {
            tvIntroduce.setVisibility(View.GONE);
        }
    }

}
