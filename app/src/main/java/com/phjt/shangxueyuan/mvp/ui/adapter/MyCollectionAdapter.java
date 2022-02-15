package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Context;

import androidx.core.content.ContextCompat;

import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.MyCollectionBean;
import com.phjt.shangxueyuan.utils.CountNumUtils;
import com.phjt.shangxueyuan.utils.FileUploadUtils;
import com.phjt.shangxueyuan.utils.GlideUtils;
import com.phjt.view.roundImg.RoundedImageView;

import java.util.Iterator;
import java.util.Set;

/**
 * @author: Roy
 * date:   2020/3/30
 * company: 普华集团
 * description:
 */
public class MyCollectionAdapter extends BaseQuickAdapter<MyCollectionBean, BaseViewHolder> {

    private Context mContext;
    private ICallBack call;
    private boolean isEdit;
    private int mType;

    public MyCollectionAdapter(Context context) {
        super(R.layout.item_my_collection);
        this.mContext = context;
    }

    public void setCallBack(ICallBack call) {
        this.call = call;
    }

    public void setEdit(boolean isEdits) {
        this.isEdit = isEdits;
    }

    public void setType(int type) {
        this.mType = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, MyCollectionBean item) {
        int position = helper.getAdapterPosition();

        helper.setVisible(R.id.tv_has_been_removed, item.getUpState() == 0)
                .setVisible(R.id.gray_view, item.getUpState() == 0);

        RoundedImageView ivTutor = helper.getView(R.id.iv_audition_item);
        ImageView ivCheck = helper.getView(R.id.iv_parent_check);
        FrameLayout flCheck = helper.getView(R.id.item_parent_check);

        if (0 == mType || 2 == mType) {
            helper.setText(R.id.tv_audition_title_item, String.valueOf(item.getName()))
                    .setText(R.id.tv_audition_content_item, item.getCouDescribe() == null ? "" : item.getCouDescribe())
                    .setText(R.id.tv_study_people_item, CountNumUtils.getStudyNum(item.getStudyNum()) + "人在学");
            GlideUtils.load(item.getCoverImg(), ivTutor, R.drawable.image_placeholder);
        } else {
            helper.setText(R.id.tv_audition_title_item, String.valueOf(item.getSpecialTitle()))
//                    .setText(R.id.tv_audition_content_item, TextUtils.isEmpty(item.getSpecialContent()) ? "" : item.getSpecialContent())
                    .setText(R.id.tv_study_people_item, item.getCollectNum() == null ? "0人收藏" : CountNumUtils.getStudyNum(item.getCollectNum()) + "人收藏");
            if (!TextUtils.isEmpty(item.getSpecialContent())) {
                String content = item.getSpecialContent();
                if (FileUploadUtils.getImgStr(content).size() > 0) {
                    Set<String> imgSet = FileUploadUtils.getImgStr(content);
                    Iterator iter = imgSet.iterator();
                    if (iter.hasNext()) {
                        String url = (String) iter.next();
                        GlideUtils.load(url, ivTutor, R.drawable.image_placeholder);
                    }
                }
            }
        }
        if (item.isCheck()) {
            ivCheck.setImageResource(R.drawable.ic_delete_check);
        } else {
            ivCheck.setImageResource(R.drawable.ic_delete_check_un);
        }

        if (isEdit) {
            flCheck.setVisibility(View.VISIBLE);
        } else {
            flCheck.setVisibility(View.GONE);
        }

        helper.getView(R.id.item_parent_check).setOnClickListener(v -> {
            item.setCheck(!item.isCheck());
            notifyDataSetChanged();
            if (item.isCheck()) {
                call.callBack(item.getId());
            } else {
                call.removeBack(item.getId());
            }
        });

    }


    public interface ICallBack {
        /**
         * 点击回调方法
         *
         * @param id 点击id
         */
        void callBack(int id);

        /**
         * 删除条目
         *
         * @param id 条目id
         */
        void removeBack(int id);
    }

}
