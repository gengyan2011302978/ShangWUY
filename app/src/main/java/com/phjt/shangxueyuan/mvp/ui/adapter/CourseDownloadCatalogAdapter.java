package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.annotate.SingleClick;
import com.phjt.shangxueyuan.app.MyApplication;
import com.phjt.shangxueyuan.bean.CourseCatalogFirstBean;
import com.phjt.shangxueyuan.bean.CourseCatalogSecondBean;
import com.phjt.shangxueyuan.bean.model.File;
import com.phjt.shangxueyuan.greendao.gen.FileDao;
import com.phjt.shangxueyuan.mvp.ui.activity.CourseDetailActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.SetUpActivity;
import com.phjt.shangxueyuan.service.DownLoadCacheService;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.shangxueyuan.widgets.NetworkUtils;
import com.phjt.shangxueyuan.widgets.dialog.DialogUtils;
import com.phsxy.utils.SPUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.Date;
import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import static com.phjt.shangxueyuan.service.DownLoadCacheService.EXTRA_VIDEO;

/**
 * @author: gengyan
 * date:    2020/3/30 10:06
 * company: 普华集团
 * description: 课程 下载目录的adapter
 */
public class CourseDownloadCatalogAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    private Context mContext;
    /**
     * 是否是直播回放
     */
    private boolean isLivePlayback;

    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;
    private RxPermissions rxPermissions;

    public CourseDownloadCatalogAdapter(Context context, List<MultiItemEntity> data, boolean isLivePlayback) {
        super(data);
        this.mContext = context;
        this.isLivePlayback = isLivePlayback;
        rxPermissions = new RxPermissions((FragmentActivity) mContext);
        addItemType(TYPE_LEVEL_0, R.layout.item_catalog_type_0);
        addItemType(TYPE_LEVEL_1, R.layout.item_catalog_type_1);
    }

    @SuppressLint("CheckResult")
    @Override
    protected void convert(BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case TYPE_LEVEL_0:
                CourseCatalogFirstBean firstBean = (CourseCatalogFirstBean) item;
                int position = helper.getAdapterPosition();

                helper.setImageResource(R.id.iv_catalog_0_item, firstBean.isExpanded() ? R.drawable.arrow_catalog_up : R.drawable.arrow_catalog_down)
                        .setText(R.id.tv_catalog_0_title_item, firstBean.getCoursewareName())
                        .setVisible(R.id.iv_catalog_0_item, !isLivePlayback);

                TextView tvTitle = helper.getView(R.id.tv_catalog_0_title_item);
                TextView tvDesc = helper.getView(R.id.tv_desc);
//                if (TextUtils.isEmpty(firstBean.getCouDescribe())){
                    tvDesc.setVisibility(View.GONE);
//                }else {
//                    tvDesc.setVisibility(View.VISIBLE);
//                    tvDesc.setText(firstBean.getCouDescribe());
//                }
                tvTitle.setTextColor(isLivePlayback ? ContextCompat.getColor(mContext, R.color.colorWhite)
                        : ContextCompat.getColor(mContext, R.color.color_333333));

                //展开收起
                helper.getView(R.id.cl_catalog_0).setOnClickListener(v -> {
                    if (firstBean.isExpanded()) {
                        collapse(position, false);
                    } else {
                        expand(position, false);
                    }
                });

                break;
            case TYPE_LEVEL_1:
                CourseCatalogSecondBean secondBean = (CourseCatalogSecondBean) item;
                helper.setText(R.id.tv_catalog_title_1_item, secondBean.getPointIdName());

                TextView tvVideo = helper.getView(R.id.tv_catalog_video_item);
                TextView tvCourseTitle = helper.getView(R.id.course_title);
                TextView tvName = helper.getView(R.id.tv_catalog_title_1_item);
                ImageView ivArrow = helper.getView(R.id.iv_catalog_1_item);
                ImageView ivLine = helper.getView(R.id.iv_line);
                ivLine.setVisibility(View.GONE);
                if (TextUtils.isEmpty(secondBean.getTitle())){
                    tvCourseTitle.setVisibility(View.GONE);
                }else {
                    tvCourseTitle.setVisibility(View.VISIBLE);
                    tvCourseTitle.setText(secondBean.getTitle());
                }
                tvVideo.setTextColor(isLivePlayback ? ContextCompat.getColor(mContext, R.color.color_141414)
                        : ContextCompat.getColor(mContext, R.color.color_DE8E40));

                int downState = secondBean.getDownState();
                if (downState == File.DOWNLOAD_REDYA) {
                    //准备下载
                    tvName.setTextColor(isLivePlayback ? ContextCompat.getColor(mContext, R.color.colorWhite)
                            : ContextCompat.getColor(mContext, R.color.color_333333));
                    ivArrow.setVisibility(View.INVISIBLE);
                } else if (downState == File.DOWNLOAD_COMPLETE) {
                    //下载完成
                    tvName.setTextColor(isLivePlayback ? ContextCompat.getColor(mContext, R.color.color_99FFFFFF)
                            : ContextCompat.getColor(mContext, R.color.color_999999));
                    ivArrow.setVisibility(View.VISIBLE);
                    ivArrow.setSelected(true);
                } else {
                    //下载中
                    tvName.setTextColor(isLivePlayback ? ContextCompat.getColor(mContext, R.color.colorWhite)
                            : ContextCompat.getColor(mContext, R.color.color_333333));
                    ivArrow.setVisibility(View.VISIBLE);
                    ivArrow.setSelected(false);
                }

                helper.getView(R.id.cl_cl_catalog_1).setOnClickListener(new View.OnClickListener() {
                    @SingleClick
                    @Override
                    public void onClick(View v) {
                        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                .subscribe(granted -> {
                                    if (granted) {
                                        if (!NetworkUtils.isNetWorkAvailable(mContext)) {
                                            TipsUtil.showTips("暂无网络连接");
                                            return;
                                        }
                                        CourseDownloadCatalogAdapter.this.downLoad(downState, secondBean);
                                    } else {
                                        TipsUtil.show("请先同意存储权限");
                                    }
                                });
                    }
                });
                break;
            default:
                break;
        }
    }

    public void downLoad(int downState, CourseCatalogSecondBean secondBean) {
        if (NetworkUtils.isWiFiConnected(mContext) || SPUtils.getInstance().getBoolean(Constant.SP_SET_WIFI_DOWNLOAD)) {
            if (downState == File.DOWNLOAD_REDYA) {
                File file = new File();
                file.setId(Long.parseLong(secondBean.getPointId() + CourseDetailActivity.courseId));
                file.setSeq(Integer.parseInt(secondBean.getPointId()));
                file.setFileName(secondBean.getPointIdName());
                file.setFileLevel(CourseDetailActivity.level_name);
                file.setFileLevelUrl(CourseDetailActivity.level_url);
                file.setFileLevelDesc(CourseDetailActivity.level_desc);
                file.setCourseId(Integer.parseInt(CourseDetailActivity.courseId));
                file.setVideotype(CourseDetailActivity.isLivePlayback?1:2);
                file.setProgress(0);
                file.setCreateTime(new Date(System.currentTimeMillis()));
                file.setStatus(File.DOWNLOAD_PROCEED);
                file.setSize((long) 0);
                file.setFileType("video");
                file.setUrl(secondBean.getVideoUrl());
                Intent intent = new Intent();
                intent.putExtra(EXTRA_VIDEO, file);
                List<File> userInfos = MyApplication.instance().getDaoSession().getFileDao().queryBuilder().where(FileDao.Properties.Seq.eq(Integer.parseInt(secondBean.getPointId()))).build().list();
                if (userInfos.size() == 0) {
                    DownLoadCacheService.enqueueWork(mContext, 0x111, intent);
                } else {
                    file.setStatus(userInfos.get(0).getStatus());
                    file.setSize(userInfos.get(0).getSize());
                    file.setPath(userInfos.get(0).getPath());
                }
                MyApplication.instance().getDaoSession().getFileDao().insert(file);
                secondBean.setDownState(File.DOWNLOAD_PROCEED);
                CourseDownloadCatalogAdapter.this.notifyDataSetChanged();
                TipsUtil.show("已加入下载列表");
            } else if (downState == File.DOWNLOAD_COMPLETE) {
                //下载完成
            } else {
                //下载中
                TipsUtil.show("视频下载中");
            }
        } else {
            DialogUtils.showWifiDownloadDialog(mContext, "当前设置仅在WIFI网络下载，您可以到【设置】中开启", "仅在WiFi下载", "去设置",
                    new DialogUtils.OnCancelSureClick() {
                        @Override
                        public void clickCancel() {

                        }

                        @Override
                        public void clickSure() {
                            Intent intent1 = new Intent(mContext, SetUpActivity.class);
                            mContext.startActivity(intent1);
                        }
                    });
        }
    }
}
