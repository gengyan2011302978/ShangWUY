package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Context;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ctetin.expandabletextviewlibrary.ExpandableTextView;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.TutorAnsweringQuestionsBean;
import com.phjt.shangxueyuan.common.AppImageLoader;
import com.phjt.shangxueyuan.widgets.FlowLayout;
import com.phjt.view.roundImg.RoundedImageView;

import java.util.List;

/**
 * @author: Roy
 * date:   2021/6/18
 * company: 普华集团
 * description:
 */
public class TutorAnsweringQuestionsAdapter extends BaseQuickAdapter<TutorAnsweringQuestionsBean, BaseViewHolder> {
    private Context mContext;
    private int mType;

    public TutorAnsweringQuestionsAdapter(Context context, int tpye) {
        super(R.layout.item_tutor_answering_questions);
        this.mContext = context;
        this.mType = tpye;
    }

    @Override
    protected void convert(BaseViewHolder helper, TutorAnsweringQuestionsBean item) {
        TextView tvPayment = helper.getView(R.id.tv_payment);
        ConstraintLayout.LayoutParams tvFrameLayoutParams = (ConstraintLayout.LayoutParams) tvPayment.getLayoutParams();
        tvFrameLayoutParams.width = ConstraintLayout.LayoutParams.WRAP_CONTENT;
        tvPayment.setLayoutParams(tvFrameLayoutParams);

        helper.setText(R.id.tv_payment, mContext.getString(R.string.question_pay_hint, item.getQuestionCoin()))
                .setText(R.id.tv_tutor_name, String.valueOf(item.getName()) + mContext.getString(R.string.string_teacher))
                .addOnClickListener(R.id.tv_payment)
                .setText(R.id.tv_teacher_name, mContext.getString(R.string.string_teacher));
        RoundedImageView ivTutor = helper.getView(R.id.iv_tutor);
        AppImageLoader.loadResUrl(item.getCoverImg(), ivTutor, R.drawable.image_placeholder);
        tvPayment.setBackgroundResource(R.drawable.bg_select_tutor);
        if (mType == 3) {
            tvPayment.setText(mContext.getString(R.string.question_hint, item.getQuestionCoin()));
            tvPayment.setBackgroundResource(R.color.white);
            tvPayment.setTextColor(ContextCompat.getColor(mContext,R.color.color_FFFF650C));
        }
//        ivTutor.setOnClickListener(v -> {
//            if (!TextUtils.isEmpty(item.getCoverImg())) {
//                Intent intent = new Intent(mContext, BigPhotoActivity.class);
//                List<String> imgList = new ArrayList<>();
//                imgList.add(item.getCoverImg());
//                intent.putStringArrayListExtra(BUNDLE_BIG_IMAGE_URLS, (ArrayList<String>) imgList);
//                intent.putExtra(BUNDLE_BIG_IMAGE_POSITION, 0);
//                ArchitectUtils.startActivity(intent);
//            }
//        });
        ExpandableTextView mTvIntroduce = helper.getView(R.id.tv_introduce);
        mTvIntroduce.setContent(String.valueOf(item.getLecDesc()));

        RecyclerView rlowLayout = helper.getView(R.id.rl_label);
        if (null != item.getSpecialityRealmList() && item.getSpecialityRealmList().size() > 0) {
            List<TutorAnsweringQuestionsBean.RealmList> list = item.getSpecialityRealmList();
            if (list.size() > 6) {
                List<TutorAnsweringQuestionsBean.RealmList> realmLists = list.subList(0, 6);
                setRecycler(realmLists, rlowLayout);
            } else {
                setRecycler(list, rlowLayout);
            }
        }
    }

    private void setRecycler(List<TutorAnsweringQuestionsBean.RealmList> list, RecyclerView rlowLayout) {
        rlowLayout.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        FlowAdapter popAdapter = new FlowAdapter(mContext);
        rlowLayout.setAdapter(popAdapter);
        popAdapter.setNewData(list);

    }

    private void setFlowLayout(List<TutorAnsweringQuestionsBean.RealmList> list, FlowLayout flowLayout) {
        //往容器内添加TextView数据
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 8, 10, 8);
        if (flowLayout != null) {
            flowLayout.removeAllViews();
        }

        for (int i = 0; i < list.size(); i++) {
            TextView tv = new TextView(mContext);
            tv.setPadding(25, 5, 25, 5);
            tv.setText(String.valueOf("" + list.get(i).getRealmName()));
            tv.setMaxEms(8);
            tv.setTextSize(12);
            tv.setSingleLine();
            tv.setTextColor(ContextCompat.getColor(mContext, R.color.color_FFFC8E1A));
            tv.setBackgroundResource(R.drawable.bg_questions);
            tv.setLayoutParams(layoutParams);
            flowLayout.addView(tv, layoutParams);
        }
    }
}
