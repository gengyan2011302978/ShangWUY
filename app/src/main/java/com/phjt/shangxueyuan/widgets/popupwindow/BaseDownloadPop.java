package com.phjt.shangxueyuan.widgets.popupwindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.app.MyApplication;
import com.phjt.shangxueyuan.bean.CourseCatalogSecondBean;
import com.phjt.shangxueyuan.bean.model.File;
import com.phjt.shangxueyuan.greendao.gen.FileDao;
import com.phjt.shangxueyuan.mvp.ui.activity.CourseDetailActivity;
import com.phjt.shangxueyuan.mvp.ui.adapter.CourseDownloadCatalogAdapter;
import com.phsxy.utils.ToastUtils;

import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.ButterKnife;

/**
 * @author: gengyan
 * date:    2020/6/5 16:56
 * company: 普华集团
 * description: 描述
 */
public abstract class BaseDownloadPop extends PopupWindow {

    public Context mContext;

    /**
     * 获取PopWindow 布局
     *
     * @return PopWindow Layout
     */
    public abstract int getLayoutId();

    /**
     * 获取 RecycleView
     *
     * @return RecycleView
     */
    public abstract RecyclerView getRvView();

    /**
     * 获取 高度
     *
     * @return 高度
     */
    public abstract int getPopHeight();

    /**
     * 是否是直播回放
     *
     * @return 是否是直播回放
     */
    public abstract boolean isLivePlayback();

    private CourseDownloadCatalogAdapter catalogAdapter;

    public BaseDownloadPop(Context context) {
        super(context);
        this.mContext = context;

        View rootView = LayoutInflater.from(mContext).inflate(getLayoutId(), null);
        ButterKnife.bind(this, rootView);
        this.setContentView(rootView);
        // 设置PopupWindow弹出窗体的宽/高
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(getPopHeight());
        // 设置PopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 设置PopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.ActionSheetDialogAnimation);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(ContextCompat.getColor(context, R.color.transparent3));
        // 设置PopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        getRvView().setLayoutManager(layoutManager);
    }

    public void showCatalogDate(List<MultiItemEntity> data) {
        if (data != null && !data.isEmpty()) {
            resetDataDownload(data);
            catalogAdapter = new CourseDownloadCatalogAdapter(mContext, data, isLivePlayback());
            getRvView().setAdapter(catalogAdapter);
        } else {
            ToastUtils.show("课程目录数据为空");
        }
    }

    /**
     * 查询数据库中是否下载过
     *
     * @param itemEntities 课程目录集合
     */
    public void resetDataDownload(List<MultiItemEntity> itemEntities) {
        for (int i = 0; i < itemEntities.size(); i++) {
            MultiItemEntity multiItemEntity = itemEntities.get(i);
            if (multiItemEntity instanceof CourseCatalogSecondBean) {
                CourseCatalogSecondBean secondBean = (CourseCatalogSecondBean) multiItemEntity;
                List<File> fileList = null;
                if (MyApplication.instance().getDaoSession() != null) {
                    fileList = MyApplication.instance().getDaoSession().getFileDao().queryBuilder()
                            .where(FileDao.Properties.Id.eq(Long.parseLong(secondBean.getPointId() + CourseDetailActivity.courseId))).list();
                }
                if (fileList != null && !fileList.isEmpty()) {
                    secondBean.setDownState(fileList.get(0).getStatus());
                } else {
                    secondBean.setDownState(File.DOWNLOAD_REDYA);
                }
            }
        }
    }

    /**
     * 刷新数据
     *
     * @param data 目录实体
     */
    public void refreshData(List<MultiItemEntity> data) {
        if (data != null && !data.isEmpty()) {
            resetDataDownload(data);
            if (catalogAdapter != null) {
                catalogAdapter.setNewData(data);
            }
        }
    }
}
