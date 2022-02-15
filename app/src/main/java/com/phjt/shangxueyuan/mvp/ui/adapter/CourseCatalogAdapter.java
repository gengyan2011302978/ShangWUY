package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.phjt.base.integration.EventBusManager;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.CourseCatalogFirstBean;
import com.phjt.shangxueyuan.bean.CourseCatalogSecondBean;

import java.util.List;

/**
 * @author: gengyan
 * date:    2020/3/30 10:06
 * company: 普华集团
 * description: 课程目录的adapter
 */
public class CourseCatalogAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    private Context mContext;
    /**
     * 记录当前播放的视频 标识
     */
    public String pointId;
    /*
    是否是专栏
     */
    public int courseType = 1;

    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;

    public CourseCatalogAdapter(Context context, List<MultiItemEntity> data) {
        super(data);
        this.mContext = context;
        addItemType(TYPE_LEVEL_0, R.layout.item_catalog_type_0);
        addItemType(TYPE_LEVEL_1, R.layout.item_catalog_type_1);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case TYPE_LEVEL_0:
                CourseCatalogFirstBean firstBean = (CourseCatalogFirstBean) item;
                int position = helper.getAdapterPosition();

                helper.setImageResource(R.id.iv_catalog_0_item, firstBean.isExpanded() ? R.drawable.arrow_catalog_up : R.drawable.arrow_catalog_down)
                        .setText(R.id.tv_catalog_0_title_item, firstBean.getCoursewareName());
                if (firstBean.getCouDescribe() != null) {
                    helper.setVisible(R.id.tv_desc, true);
                    helper.setText(R.id.tv_desc, firstBean.getCouDescribe());
                } else {
                    helper.setVisible(R.id.tv_desc, false);
                }

                //展开收起
                helper.getView(R.id.cl_catalog_0).setOnClickListener(v -> {
                    if (firstBean.isExpanded()) {
                        collapse(position, false);
                    } else {
                        expand(position, false);
                    }
                    resetDataSelect();
                });

                break;
            case TYPE_LEVEL_1:
                CourseCatalogSecondBean secondBean = (CourseCatalogSecondBean) item;
                helper.setText(R.id.tv_catalog_title_1_item, secondBean.getPointIdName());
//                CourseCatalogFirstBean.CouWareVOSBean couWareVOSBean = (CourseCatalogFirstBean.CouWareVOSBean) item;
                TextView tvName = helper.getView(R.id.tv_catalog_title_1_item);
                TextView tvNameTitle = helper.getView(R.id.course_title);
                ImageView ivArrow = helper.getView(R.id.iv_catalog_1_item);
//                helper.getView(R.id.cl_cl_catalog_1).setBackgroundResource(R.color.colorFFFFFF);
                tvName.setSelected(secondBean.isSelect());
                if (TextUtils.isEmpty(secondBean.getTitle())) {
                    tvNameTitle.setVisibility(View.GONE);
                } else {
                    tvNameTitle.setVisibility(View.VISIBLE);
                    courseType = 2;
                }
                if (courseType == 2) {
                    helper.getView(R.id.cl_cl_catalog_1).setBackgroundResource(R.color.color_F7F7F7);
                }
                tvNameTitle.setText(secondBean.getTitle());
                ivArrow.setSelected(secondBean.isSelect());
                //点击进行视频播放
                helper.getView(R.id.cl_cl_catalog_1).setOnClickListener(v -> {
                    playVideo(secondBean);
                });
                break;
            default:
                break;
        }
    }

    public void playVideo(CourseCatalogSecondBean secondBean) {
        EventBusManager.getInstance().post(secondBean);
        if (!TextUtils.isEmpty(secondBean.getPointId())) {
            pointId = secondBean.getPointId();
            resetDataSelect();
        }
    }

    /**
     * 重置Rv显示状态
     */
    public void resetDataSelect() {
        List<MultiItemEntity> itemEntities = getData();
        for (int i = 0; i < itemEntities.size(); i++) {
            MultiItemEntity multiItemEntity = itemEntities.get(i);
            if (multiItemEntity instanceof CourseCatalogSecondBean) {
                CourseCatalogSecondBean secondBean = (CourseCatalogSecondBean) multiItemEntity;
                if (TextUtils.equals(secondBean.getPointId(), pointId)) {
                    secondBean.setSelect(true);
                } else {
                    secondBean.setSelect(false);
                }
            }
        }
        notifyDataSetChanged();
    }

    /**
     * 切换下一个节
     * 先获取当前的position，再获取下一节
     */
    public void changeNextVideo() {
        List<MultiItemEntity> itemEntities = getData();
        int position = 0;
        int size = itemEntities.size();
        for (int i = 0; i < size; i++) {
            MultiItemEntity multiItemEntity = itemEntities.get(i);
            if (multiItemEntity instanceof CourseCatalogSecondBean) {
                CourseCatalogSecondBean secondBean = (CourseCatalogSecondBean) multiItemEntity;
                if (secondBean.isSelect()) {
                    position = i;
                    secondBean.setPointWatchDuration(0L);
                    break;
                }
            }
        }
        getNext(position, size, itemEntities);
    }

    public void getNext(int position, int size, List<MultiItemEntity> itemEntities) {
        if (position >= size - 1) {
            //最后一节，传递空实体
            playVideo(new CourseCatalogSecondBean());
        } else {
            int index = position + 1;
            MultiItemEntity multiItemEntity = itemEntities.get(index);
            if (multiItemEntity instanceof CourseCatalogSecondBean) {
                CourseCatalogSecondBean secondBean = (CourseCatalogSecondBean) multiItemEntity;
                playVideo(secondBean);
            } else {
                getNext(index, size, itemEntities);
            }
        }
    }

    /**
     * --------------------------- 笔记页面使用 ------------------
     * 先通过id获取笔记定位的小节，在赋值播放时间
     */
    public void choseChapterAndSetTime(String pointId, Long pointWatchDuration) {
        List<MultiItemEntity> itemEntities = getData();
        for (int i = 0; i < itemEntities.size(); i++) {
            MultiItemEntity multiItemEntity = itemEntities.get(i);
            if (multiItemEntity instanceof CourseCatalogSecondBean) {
                CourseCatalogSecondBean secondBean = (CourseCatalogSecondBean) multiItemEntity;
                if (TextUtils.equals(pointId, secondBean.getPointId())) {
                    secondBean.setPointWatchDuration(pointWatchDuration);
                    playVideo(secondBean);
                    break;
                }
            }
        }
    }
}
