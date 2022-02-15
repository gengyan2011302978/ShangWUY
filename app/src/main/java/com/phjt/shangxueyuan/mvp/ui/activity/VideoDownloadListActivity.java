package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.integration.EventBusManager;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.app.MyApplication;
import com.phjt.shangxueyuan.bean.event.ChangeEvent;
import com.phjt.shangxueyuan.bean.event.DeleteDownLoadBean;
import com.phjt.shangxueyuan.bean.model.File;
import com.phjt.shangxueyuan.bean.model.VideoInfo;
import com.phjt.shangxueyuan.di.component.DaggerVideoDownloadListComponent;
import com.phjt.shangxueyuan.greendao.gen.DaoSession;
import com.phjt.shangxueyuan.greendao.gen.FileDao;
import com.phjt.shangxueyuan.mvp.contract.VideoDownloadListContract;
import com.phjt.shangxueyuan.mvp.presenter.VideoDownloadListPresenter;
import com.phjt.shangxueyuan.mvp.ui.adapter.VideoDownloadAdapter;
import com.phjt.shangxueyuan.service.DownLoadCacheService;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.shangxueyuan.widgets.NetworkUtils;
import com.phjt.shangxueyuan.widgets.dialog.DialogUtils;
import com.phsxy.utils.SPUtils;
import com.phsxy.utils.ToastUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;
import static com.phjt.shangxueyuan.service.DownLoadCacheService.EXTRA_VIDEO;


/**
 * @author: Created by Template
 * date: 06/04/2020 15:01
 * company: 普华集团
 * description: 模版请保持更新
 */
public class VideoDownloadListActivity extends BaseActivity<VideoDownloadListPresenter> implements VideoDownloadListContract.View {


    @BindView(R.id.tv_start)
    TextView tvStart;
    @BindView(R.id.rv_download_video)
    RecyclerView rvDownloadVideo;
    @BindView(R.id.image_edit)
    TextView mImageEdit;
    @BindView(R.id.tv_delete)
    public TextView mTvDelete;

    DaoSession daoSession;
    VideoDownloadAdapter downloadAdapter;
    List<File> learnBeanList;
    public static List<File> deleteBeanList;
    @BindView(R.id.image_back)
    ImageView imageBack;
    @BindView(R.id.title_commont)
    TextView titleCommont;
    @BindView(R.id.relat_title)
    RelativeLayout relatTitle;
    private HashMap<String, DownloadReceiver> downloadReceiverMap = new HashMap<>();
    public static boolean isChange = false;//是否编辑
    private List<VideoInfo> mUnCompletedDownloads;
    private List<VideoInfo> mWillDeleteTask = new ArrayList<>();
    boolean mIsEditable = false;
    FileDao videoInfoDao = MyApplication.instance().getDaoSession().getFileDao();
    private Handler mHandler = new Handler();

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerVideoDownloadListComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_video_download_list;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        daoSession = MyApplication.instance().getDaoSession();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvDownloadVideo.setLayoutManager(layoutManager);
        learnBeanList = new ArrayList<>();
        isChange = false;
        deleteBeanList = new ArrayList<>();
        List<File> userInfos = daoSession.getFileDao().queryBuilder().where(FileDao.Properties.FileLevel.eq(getIntent().getStringExtra("levelName")), FileDao.Properties.FileType.eq(("video"))).build().list();
        learnBeanList = userInfos;
        getData();
        if (userInfos.size() > 0) {
            titleCommont.setText(userInfos.get(0).getFileLevel());
        }
        downloadAdapter = new VideoDownloadAdapter(this, learnBeanList);
        rvDownloadVideo.setAdapter(downloadAdapter);
        downloadAdapter.setEmptyView(R.layout.empty_layout, rvDownloadVideo);
        downloadAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (mIsEditable) {
                return;
            }
            mIsEditable = true;
            if (view.getId() == R.id.relat_item) {
                File file = learnBeanList.get(position);
                if (file.getStatus() == File.DOWNLOAD_REDYA || file.getStatus() == File.DOWNLOAD_PAUSE) {
                    // 下载任务暂停 点击开始下载
                    file.setStatus(File.DOWNLOAD_PROCEED);
                    videoInfoDao.update(file);
                    startDownloadItem(file);

                } else if (file.getStatus() == File.DOWNLOAD_PROCEED) {
                    File videoInfoFromFiledId = getVideoInfoFromFiledId(file.getId());
                    if (videoInfoFromFiledId != null) {
                        videoInfoFromFiledId.setStatus(File.DOWNLOAD_PAUSE);
                        DownLoadCacheService.txStopDownloadVideo(file.getSeq());
                    }
                } else if (file.getStatus() == File.DOWNLOAD_COMPLETE) {
                    Intent intent = new Intent(VideoDownloadListActivity.this, CourseLocalPlayActivity.class);
                    intent.putExtra(Constant.BUNDLE_LOCAL_PATH, file.getPath());
                    intent.putExtra(Constant.BUNDLE_COURSE_ID, String.valueOf(file.getCourseId()));
                    intent.putExtra(Constant.BUNDLE_POINT_ID, String.valueOf(file.getSeq()));
                    intent.putExtra(Constant.BUNDLE_POINT_NAME, file.getFileName());
                    intent.putExtra(Constant.ISLIVEPLAYBACK, file.getVideotype());
                    startActivity(intent);
                }
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mIsEditable = false;
                }
            }, 2000);
        });
        mImageEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isChange) {
                    mImageEdit.setText("");
                    mImageEdit.setBackgroundResource(R.drawable.btn_edit_icon);
                    isChange = false;
                    downloadAdapter.notifyDataSetChanged();
                    mTvDelete.setVisibility(View.GONE);
                } else {
                    mImageEdit.setText("取消");
                    mImageEdit.setBackground(null);
                    isChange = true;
                    downloadAdapter.notifyDataSetChanged();
                    mTvDelete.setVisibility(View.VISIBLE);
                }

            }
        });
        mImageEdit.setOnClickListener(v -> {
            if (isChange) {
                mImageEdit.setText("");
                mImageEdit.setBackgroundResource(R.drawable.btn_edit_icon);
                isChange = false;
                deleteBeanList.clear();
                downloadAdapter.notifyDataSetChanged();
                mTvDelete.setVisibility(View.GONE);
            } else {
                mImageEdit.setText("取消");
                mImageEdit.setBackground(null);
                isChange = true;
                downloadAdapter.notifyDataSetChanged();
                mTvDelete.setVisibility(View.VISIBLE);
            }

        });
        mTvDelete.setOnClickListener(v -> {
            if (deleteBeanList.size() > 0) {
                for (int i = 0; i < deleteBeanList.size(); i++) {
                    learnBeanList.remove(deleteBeanList.get(i));
                    DownLoadCacheService.txDeleteDownloadVideo(deleteBeanList.get(i));
                    MyApplication.instance().getDaoSession().getFileDao().delete(deleteBeanList.get(i));
                    downloadAdapter.notifyDataSetChanged();
                }
                if (learnBeanList.size() == 0) {
                    mImageEdit.setText("");
                    mImageEdit.setBackgroundResource(R.drawable.btn_edit_icon);
                    tvStart.setVisibility(View.GONE);
                    mTvDelete.setVisibility(View.GONE);
                }
                EventBusManager.getInstance().post(new DeleteDownLoadBean());
                mImageEdit.setText("");
                mImageEdit.setBackgroundResource(R.drawable.btn_edit_icon);
                isChange = false;
                deleteBeanList.clear();
                downloadAdapter.notifyDataSetChanged();
                mTvDelete.setVisibility(View.GONE);
            } else {
                ToastUtils.show("请选择要删除的目录");
            }
            deleteBeanList.clear();
            if (learnBeanList.size() == 0) {
                mImageEdit.setVisibility(View.GONE);
            }
        });
        changeStatus();
    }

    void changeStatus() {
        List<File> files = daoSession.getFileDao().queryBuilder().where(FileDao.Properties.Status.eq(File.DOWNLOAD_PROCEED), FileDao.Properties.FileType.eq(("video")), FileDao.Properties.FileLevel.eq(getIntent().getStringExtra("levelName"))).build().list();
        List<File> files1 = daoSession.getFileDao().queryBuilder().where(FileDao.Properties.Status.eq(File.DOWNLOAD_PAUSE), FileDao.Properties.FileType.eq(("video")), FileDao.Properties.FileLevel.eq(getIntent().getStringExtra("levelName"))).build().list();
        List<File> files2 = daoSession.getFileDao().queryBuilder().where(FileDao.Properties.Status.eq(File.DOWNLOAD_REDYA), FileDao.Properties.FileType.eq(("video")), FileDao.Properties.FileLevel.eq(getIntent().getStringExtra("levelName"))).build().list();
        if (files.size() > 0) {
//            DownLoadCacheService.txStopDownloadVideo(file.getSeq());
//            for (int i = 0; i < files.size(); i++) {
//                startDownloadItem(files.get(i));
//            }
            if (learnBeanList.get(0).getStatus() == File.DOWNLOAD_ERROR) {
                tvStart.setText("开始下载");
            } else {
                tvStart.setText("暂停下载");
            }

        } else if (files1.size() > 0 || files2.size() > 0) {
            tvStart.setText("开始下载");
        } else {
            tvStart.setVisibility(View.GONE);
        }
    }

    private File getVideoInfoFromFiledId(Long filedId) {
        List<File> videoInfos = videoInfoDao.queryBuilder().where(FileDao.Properties.Id.eq(filedId)).build().list();
        if (videoInfos != null && !videoInfos.isEmpty()) {
            return videoInfos.get(0);
        }
        return null;
    }

    private void startDownloadItem(File videoInfo) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_VIDEO, videoInfo);
        DownLoadCacheService.enqueueWork(this, 0x111, intent);
    }

    private void getData() {
        for (int i = 0; i < learnBeanList.size(); i++) {
            IntentFilter filter = new IntentFilter();
            DownloadReceiver receiver = new DownloadReceiver();
            filter.addAction("com.phjt.shangxueyuan" + learnBeanList.get(i).getSeq());
            registerReceiver(receiver, filter);
            downloadReceiverMap.put(learnBeanList.get(i).getSeq() + "", receiver);
        }

    }

    /**
     * 查询已下载的文件
     *
     * @return
     */
    private List<File> selectDownloadedFiles() {
        List<File> userInfos = daoSession.loadAll(File.class);
//        learnBeanList = userInfos;
        return userInfos;
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
    }

    @OnClick({R.id.image_back, R.id.title_commont, R.id.relat_title, R.id.tv_start})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_back:
                finish();
                break;
            case R.id.title_commont:
                break;
            case R.id.relat_title:
                break;
            case R.id.tv_start:
                if (!NetworkUtils.isNetWorkAvailable(VideoDownloadListActivity.this)) {
                    TipsUtil.showTips("暂无网络连接");
                    return;
                }
                if (tvStart.getText().equals("暂停下载")) {
                    tvStart.setText("暂停中");
                    tvStart.setClickable(false);
                    mHandler.postDelayed(() -> {
                        tvStart.setText("开始下载");
                        tvStart.setClickable(true);
                    }, 2000);
                    for (int i = 0; i < learnBeanList.size(); i++) {
                        if (learnBeanList.get(i).getStatus() == File.DOWNLOAD_PROCEED) {
                            DownLoadCacheService.txStopDownloadVideo(learnBeanList.get(i).getSeq());
                        }
                    }
                } else {
                    if (NetworkUtils.isWiFiConnected(getApplicationContext()) || SPUtils.getInstance().getBoolean(Constant.SP_SET_WIFI_DOWNLOAD)) {
                        tvStart.setText("正在开始下载");
                        tvStart.setClickable(false);
                        mHandler = new Handler();
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                tvStart.setText("暂停下载");
                                tvStart.setClickable(true);
                            }
                        }, 2000);
                        for (int i = 0; i < learnBeanList.size(); i++) {
                            if (learnBeanList.get(i).getStatus() == File.DOWNLOAD_PROCEED) {
                            } else {
                                startDownloadItem(learnBeanList.get(i));
                            }
                        }

                    } else {
                        DialogUtils.showWifiDownloadDialog(VideoDownloadListActivity.this, "当前设置仅在WIFI网络下载，您可以到【设置】中开启", "仅在WiFi下载", "去设置",
                                new DialogUtils.OnCancelSureClick() {
                                    @Override
                                    public void clickCancel() {
                                    }

                                    @Override
                                    public void clickSure() {
                                        Intent intent1 = new Intent(getApplicationContext(), SetUpActivity.class);
                                        startActivity(intent1);
                                    }
                                });
                    }

                }
                break;
        }
    }

    class DownloadReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            for (int i = 0; i < learnBeanList.size(); i++) {
                File file = learnBeanList.get(i);
                if (intent.getAction().equals("com.phjt.shangxueyuan" + file.getSeq())) {
                    int pencent = intent.getIntExtra("percent", 0);
                    file.setStatus(intent.getIntExtra("status", 0));
                    file.setProgress(pencent);
                    file.setSizeStr(intent.getStringExtra("totalSize"));
                    downloadAdapter.notifyItemChanged(i, R.id.progressBar);
                    changeStatus();
                    if (intent.getIntExtra("iswifi", 0) == 1) {
                        DownLoadCacheService.txStopDownloadVideo(file.getSeq());
                        DialogUtils.showWifiDownloadDialog(VideoDownloadListActivity.this, "当前设置仅在WIFI网络下载，您可以到【设置】中开启", "仅在WiFi下载", "去设置",
                                new DialogUtils.OnCancelSureClick() {
                                    @Override
                                    public void clickCancel() {

                                    }

                                    @Override
                                    public void clickSure() {
                                        Intent intent1 = new Intent(getApplicationContext(), SetUpActivity.class);
                                        startActivity(intent1);
                                    }
                                });
                    }
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        if (downloadReceiverMap.size() > 0) {
            for (String key : downloadReceiverMap.keySet()) {
                unregisterReceiver(downloadReceiverMap.get(key));
            }
            downloadReceiverMap.clear();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEvent(ChangeEvent msgEvent) {
        if (deleteBeanList.size() == 0) {
            mTvDelete.setBackgroundResource(R.drawable.bg_eeeeee_rectangle);
        } else {
            mTvDelete.setBackgroundResource(R.drawable.bg_2076ff_rectangle);
        }
    }
}
