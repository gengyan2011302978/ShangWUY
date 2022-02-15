package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.ExerciseShowBean;
import com.phjt.shangxueyuan.mvp.ui.activity.BigPhotoActivity;
import com.phjt.shangxueyuan.utils.Constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.phjt.base.utils.ArchitectUtils.startActivity;

/**
 * @author: Roy
 * date:   2021/1/16
 * company: 普华集团
 * description:
 */
public class AnswerImageAdapter extends BaseQuickAdapter<ExerciseShowBean.RecordVosBean, BaseViewHolder> {
    private Context mContext;
    public AnswerImageAdapter(Context context,List<ExerciseShowBean.RecordVosBean> date) {
        super(R.layout.item_image_answer,date);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ExerciseShowBean.RecordVosBean item) {
        TextView tvContent = helper.getView(R.id.tv_answer_content);
        RecyclerView recycleImageAnswer = helper.getView(R.id.recycle_image_answer);
        if (item.getQuestionType() == 6) {
            tvContent.setText(Html.fromHtml(item.getUserAnswer()));
            tvContent.setVisibility(View.VISIBLE);
            GridLayoutManager gridLayoutManager;
            recycleImageAnswer.setVisibility(View.VISIBLE);
            List<String> imagelist = new ArrayList<>();
            ThemeMainImageAdapter themeMainImageAdapter = null;
            if (!TextUtils.isEmpty(item.getUserAnswerImg())){
                imagelist = Arrays.asList(item.getUserAnswerImg().split(","));
                if (imagelist.size() == 4) {
                    gridLayoutManager = new GridLayoutManager(mContext, 2);
                } else {
                    gridLayoutManager = new GridLayoutManager(mContext, 3);
                }
                recycleImageAnswer.setLayoutManager(gridLayoutManager);
                themeMainImageAdapter = new ThemeMainImageAdapter(imagelist);
                recycleImageAnswer.setAdapter(themeMainImageAdapter);
                themeMainImageAdapter.setOnItemClickListener((adapter, view, position) -> {
                    List<String> adapterImages = adapter.getData();
                    Intent intent = new Intent(mContext, BigPhotoActivity.class);
                    intent.putStringArrayListExtra(Constant.BUNDLE_BIG_IMAGE_URLS, new ArrayList<>(adapterImages));
                    intent.putExtra(Constant.BUNDLE_BIG_IMAGE_POSITION, position);
                    intent.putExtra(Constant.BUNDLE_BIG_IMAGE_TYPE, 1);
                    intent.putExtra(Constant.BUNDLE_BIG_IMAGE_PRE, "");
                    startActivity(intent);
                });
            }else {
                recycleImageAnswer.setVisibility(View.GONE);
            }

        }else {
            tvContent.setVisibility(View.GONE);
            recycleImageAnswer.setVisibility(View.GONE);
        }
    }
}
