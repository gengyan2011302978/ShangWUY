package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.MyCollectionBean;
import com.phjt.shangxueyuan.bean.MyTrainingCampBean;
import com.phjt.shangxueyuan.utils.CountNumUtils;
import com.phjt.shangxueyuan.utils.GlideUtils;
import com.phjt.view.roundImg.RoundedImageView;

import static com.phjt.shangxueyuan.utils.CountNumUtils.getStudyNum;

/**
 * @author: Roy
 * date:   2021/1/15
 * company: 普华集团
 * description:我的课程-训练营/专栏
 */
public class TrainingCampAdapter extends BaseQuickAdapter<MyTrainingCampBean, BaseViewHolder> {

    private Context mContext;
    private int mType;

    public TrainingCampAdapter(Context context) {
        super(R.layout.item_my_craining_camp);
        this.mContext = context;
    }

    public void setType(int type) {
        mType = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, MyTrainingCampBean item) {
        helper.setText(R.id.tv_audition_title_item, item.getName())
                .setText(R.id.tv_audition_content_item, item.getCoursewareName())
                .setText(R.id.tv_study_people_item, mType == 1 ? String.format("已更新%s节", item.getCoursewareNum()) :
                        String.valueOf(item.getCurriculumStartTime()) + "至" + item.getCurriculumEndTime());
        ImageView ivTutor = helper.getView(R.id.iv_audition_item);
        GlideUtils.load(item.getCoverImg(), ivTutor, R.drawable.image_placeholder);
    }
}
