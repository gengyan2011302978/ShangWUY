package com.phjt.shangxueyuan.service;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;
import android.util.SparseArray;

import com.phjt.base.integration.EventBusManager;
import com.phjt.shangxueyuan.app.MyApplication;
import com.phjt.shangxueyuan.bean.event.DeleteDownLoadBean;
import com.phjt.shangxueyuan.greendao.gen.FileDao;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.widgets.NetworkUtils;
import com.phsxy.utils.SPUtils;
import com.tencent.rtmp.downloader.ITXVodDownloadListener;
import com.tencent.rtmp.downloader.TXVodDownloadManager;
import com.tencent.rtmp.downloader.TXVodDownloadMediaInfo;

import java.io.File;
import java.lang.reflect.Field;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

import static android.os.Environment.MEDIA_MOUNTED;

/**
 * @author : austenYang
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date :  2019/10/30 10:35
 */
public class DownLoadCacheService extends JobIntentService {
    public static final String TAG = "DownLoadCacheService";
    public static final String EXTRA_VIDEO = "EXTRA_VIDEO";
    private static final int MAX_RUNNING_TASK_NUM = 3;
    private static SparseArray<DownloadRunning> mDownMap = new SparseArray<>();
    private static SparseArray<TXVodDownloadMediaInfo> mDownMap1 = new SparseArray<>();

    public static SparseArray getDownloadingTaskList() {
        return mDownMap;
    }

    public static FileDao videoInfoDao = MyApplication.instance().getDaoSession().getFileDao();
    Intent intent;
    com.phjt.shangxueyuan.bean.model.File file;

    private void checkTaskEnque(com.phjt.shangxueyuan.bean.model.File videoInfo) {
        // 1.查找数据库中是否存在 将要下载的视频 根据 filedId
//        VideoInfoDao videoInfoDao = MyApplication.instance().getDaoSession().getVideoInfoDao();
        file = new com.phjt.shangxueyuan.bean.model.File();
//        List<VideoInfo> mVideoInfoList = LitePal.where("videoFileId = ?", videoInfo.getVideoFileId()).find(VideoInfo.class);
        List<com.phjt.shangxueyuan.bean.model.File> mVideoInfoList = videoInfoDao.queryBuilder().where(FileDao.Properties.Id.eq(videoInfo.getId())).build().list();
        if (mVideoInfoList.isEmpty()) {
            // 2.查询数据库为空 这个视频从来没有下载过 --> 储存到数据库
//            videoInfo.save();
                videoInfoDao.insert(videoInfo);
                txDownloadVideo(videoInfo.getUrl());
            // 开始下载视频
//            if (checkDownTaskNeedStart(videoInfo)) {

//            } else {
//
//            }

        } else {
            com.phjt.shangxueyuan.bean.model.File videoInfoFromDb = mVideoInfoList.get(0);
//            if (checkDownTaskNeedStart(videoInfoFromDb) ) { //表示没有下载完成 继续下载
            txDownloadVideo(videoInfo.getUrl());
//            }
        }

    }


    private void autoFetchDownloadTask() {
        // 1.查找数据库中是否存在 将要下载的视频 根据 filedId
//        VideoInfo videoInfo = LitePal
//                .where("downloadState = ?", String.valueOf(VideoInfo.DOWN_WAITING))
//                .findFirst(VideoInfo.class);
//        VideoInfo videoInfo = videoInfoDao.queryBuilder().where(VideoInfoDao.Properties.DownloadState.eq(String.valueOf(VideoInfo.DOWN_WAITING))).unique();
//        if (videoInfo != null && checkDownTaskNeedStart(videoInfo)) {
//            txDownloadVideo(videoInfo.getVideoFileId());
//        }
    }

    private static class DownloadRunning {
        public DownloadRunning(int indexInDonwloadList, boolean isRuning) {
            this.indexInDonwloadList = indexInDonwloadList;
            this.isRunning = isRuning;
        }

        public int indexInDonwloadList;
        public boolean isRunning;

    }

    /**
     * 暂停逻辑
     */


    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        com.phjt.shangxueyuan.bean.model.File videoInfo = (com.phjt.shangxueyuan.bean.model.File) intent.getSerializableExtra(EXTRA_VIDEO);
        /**
         * 暂停逻辑
         */
        checkTaskEnque(videoInfo);


    }


    public static void enqueueWork(@NonNull Context context, int jobId,
                                   @NonNull Intent work) {
        enqueueWork(context, DownLoadCacheService.class, jobId, work);
    }

    private class VideoITXVodDownloadListener implements ITXVodDownloadListener {

        @Override
        public void onDownloadStart(TXVodDownloadMediaInfo txVodDownloadMediaInfo) {
            System.out.println("本地播放地址：" + txVodDownloadMediaInfo.getPlayPath());
            List<com.phjt.shangxueyuan.bean.model.File> mVideoInfoList = videoInfoDao.queryBuilder().where(FileDao.Properties.Url.eq(txVodDownloadMediaInfo.getUrl())).build().list();
            if (mDownMap.get(mVideoInfoList.get(0).getSeq()) == null) {
                mDownMap.put(mVideoInfoList.get(0).getSeq(), new DownloadRunning(mDownMap.size(), true));
                mDownMap1.put(mVideoInfoList.get(0).getSeq(), txVodDownloadMediaInfo);
            } else {
                return;
            }
            file = mVideoInfoList.get(0);
            file.setUrl(txVodDownloadMediaInfo.getUrl());
            file.setId(mVideoInfoList.get(0).getId());
            file.setFileName(mVideoInfoList.get(0).getFileName());
            FileDao fileDao = MyApplication.instance().getDaoSession().getFileDao();
            file.setPath(txVodDownloadMediaInfo.getPlayPath());
//                    file.setSizeStr(totalSize);
            file.setStatus(com.phjt.shangxueyuan.bean.model.File.DOWNLOAD_PROCEED);
            file.setCreateTime(mVideoInfoList.get(0).getCreateTime());
            file.setFileLevel(mVideoInfoList.get(0).getFileLevel());
            file.setFileLevelDesc(mVideoInfoList.get(0).getFileLevelDesc());
            file.setFileType("video");
            file.setSize((long) txVodDownloadMediaInfo.getSize());
            file.setFileLevelUrl(mVideoInfoList.get(0).getFileLevelUrl());
            fileDao.update(file);

        }

        //DebugDB
        @Override
        public void onDownloadProgress(TXVodDownloadMediaInfo txVodDownloadMediaInfo) {
            List<com.phjt.shangxueyuan.bean.model.File> mVideoInfoList = videoInfoDao.queryBuilder().where(FileDao.Properties.Url.eq(txVodDownloadMediaInfo.getUrl())).build().list();
            if (NetworkUtils.isWiFiConnected(getApplicationContext()) || SPUtils.getInstance().getBoolean(Constant.SP_SET_WIFI_DOWNLOAD)) {

                if (mVideoInfoList.size() == 0) {
                    return;
                }
                intent = new Intent("com.phjt.shangxueyuan" + mVideoInfoList.get(0).getSeq());
                intent.setPackage(getPackageName());
                Log.e(TAG, "onDownloadProgress===fileId==" + " ===" + txVodDownloadMediaInfo.getProgress() + "--" + Thread.currentThread().getName());
                intent.putExtra("percent", (int) (txVodDownloadMediaInfo.getProgress() * 100));
                intent.putExtra("status", com.phjt.shangxueyuan.bean.model.File.DOWNLOAD_PROCEED);
                intent.putExtra("totalSize", txVodDownloadMediaInfo.getDownloadSize()+"");
                intent.putExtra("iswifi", 0);
                sendBroadcast(intent);
            } else {
                intent = new Intent("com.phjt.shangxueyuan" + mVideoInfoList.get(0).getSeq());
                intent.setPackage(getPackageName());
                Log.e(TAG, "onDownloadProgress===fileId==" + " ===" + txVodDownloadMediaInfo.getProgress() + "--" + Thread.currentThread().getName());
                intent.putExtra("percent", (int) (txVodDownloadMediaInfo.getProgress() * 100));
                intent.putExtra("status", com.phjt.shangxueyuan.bean.model.File.DOWNLOAD_PROCEED);
                intent.putExtra("totalSize", txVodDownloadMediaInfo.getDownloadSize()+"");
                intent.putExtra("iswifi", 1);
                DownLoadCacheService.txStopDownloadVideo(file.getSeq());
                sendBroadcast(intent);
            }
            for (int i = 0; i < mVideoInfoList.size(); i++) {
                file = mVideoInfoList.get(i);
                file.setUrl(txVodDownloadMediaInfo.getUrl());
                file.setId(mVideoInfoList.get(i).getId());
                file.setFileName(mVideoInfoList.get(i).getFileName());
                FileDao fileDao = MyApplication.instance().getDaoSession().getFileDao();
                file.setFileType("video");
                file.setCreateTime(mVideoInfoList.get(i).getCreateTime());
                file.setPath(txVodDownloadMediaInfo.getPlayPath());
                file.setStatus(com.phjt.shangxueyuan.bean.model.File.DOWNLOAD_PROCEED);
                file.setFileLevel(mVideoInfoList.get(i).getFileLevel());
                file.setFileLevelDesc(mVideoInfoList.get(i).getFileLevelDesc());
                file.setSize((long) txVodDownloadMediaInfo.getDownloadSize());
                file.setFileLevelUrl(mVideoInfoList.get(i).getFileLevelUrl());
                fileDao.update(file);
            }
        }

        @Override
        public void onDownloadStop(TXVodDownloadMediaInfo txVodDownloadMediaInfo) {
            List<com.phjt.shangxueyuan.bean.model.File> mVideoInfoList = videoInfoDao.queryBuilder().where(FileDao.Properties.Url.eq(txVodDownloadMediaInfo.getUrl())).build().list();

            if (mVideoInfoList.size() == 0||!NetworkUtils.isNetWorkAvailable(getApplication())) {
            }else {
                FileDao fileDao = MyApplication.instance().getDaoSession().getFileDao();
                intent = new Intent("com.phjt.shangxueyuan" + mVideoInfoList.get(0).getSeq());
                intent.setPackage(getPackageName());
                intent.putExtra("percent", (int) (txVodDownloadMediaInfo.getProgress() * 100));
                intent.putExtra("status", com.phjt.shangxueyuan.bean.model.File.DOWNLOAD_PAUSE);
                intent.putExtra("totalSize", txVodDownloadMediaInfo.getDownloadSize()+"");
                file = mVideoInfoList.get(0);
                file.setStatus(com.phjt.shangxueyuan.bean.model.File.DOWNLOAD_PAUSE);
                file.setProgress((int) (txVodDownloadMediaInfo.getProgress() * 100));
                file.setSize((long) txVodDownloadMediaInfo.getDownloadSize());
                fileDao.update(file);
                sendBroadcast(intent);
            }

        }

        @Override
        public void onDownloadFinish(TXVodDownloadMediaInfo txVodDownloadMediaInfo) {
            List<com.phjt.shangxueyuan.bean.model.File> mVideoInfoList = videoInfoDao.queryBuilder().where(FileDao.Properties.Url.eq(txVodDownloadMediaInfo.getUrl())).build().list();
            for (int i = 0; i < mVideoInfoList.size(); i++) {
                intent = new Intent("com.phjt.shangxueyuan" + mVideoInfoList.get(i).getSeq());
                intent.putExtra("percent", 100);
                intent.putExtra("status", com.phjt.shangxueyuan.bean.model.File.DOWNLOAD_COMPLETE);
                intent.putExtra("totalSize", txVodDownloadMediaInfo.getSize()+"");
                file = mVideoInfoList.get(i);
                file.setUrl(txVodDownloadMediaInfo.getUrl());
                file.setId(mVideoInfoList.get(i).getId());
                file.setFileName(mVideoInfoList.get(i).getFileName());
                FileDao fileDao = MyApplication.instance().getDaoSession().getFileDao();
                file.setProgress(100);
                file.setFileType("video");
                file.setCreateTime(mVideoInfoList.get(i).getCreateTime());
                file.setPath(txVodDownloadMediaInfo.getPlayPath());
                file.setStatus(com.phjt.shangxueyuan.bean.model.File.DOWNLOAD_COMPLETE);
                file.setFileLevel(mVideoInfoList.get(i).getFileLevel());
                file.setFileLevelDesc(mVideoInfoList.get(i).getFileLevelDesc());
                file.setSize((long) txVodDownloadMediaInfo.getSize());
                file.setFileLevelUrl(mVideoInfoList.get(i).getFileLevelUrl());
                fileDao.update(file);
                EventBusManager.getInstance().post(new DeleteDownLoadBean());
                sendBroadcast(intent);
            }

            mDownMap.remove(mVideoInfoList.get(0).getSeq());
            stopSelf();
        }

        @Override
        public void onDownloadError(TXVodDownloadMediaInfo txVodDownloadMediaInfo, int i, String s) {
            List<com.phjt.shangxueyuan.bean.model.File> mVideoInfoList = videoInfoDao.queryBuilder().where(FileDao.Properties.Url.eq(txVodDownloadMediaInfo.getUrl())).build().list();
            Log.e(TAG, "onDownloadProgress" + "onDownloadError" + i + s);
            intent = new Intent("com.phjt.shangxueyuan" + mVideoInfoList.get(0).getSeq());
            intent.setPackage(getPackageName());
            Log.e(TAG, "onDownloadProgress===fileId==" + " ===" + txVodDownloadMediaInfo.getProgress() + "--" + Thread.currentThread().getName());
            intent.putExtra("percent", (int) (txVodDownloadMediaInfo.getProgress() * 100));
            intent.putExtra("status", com.phjt.shangxueyuan.bean.model.File.DOWNLOAD_ERROR);
            intent.putExtra("totalSize", txVodDownloadMediaInfo.getDownloadSize()+"");
            sendBroadcast(intent);
            FileDao fileDao = MyApplication.instance().getDaoSession().getFileDao();
            file = mVideoInfoList.get(0);
            file.setStatus(com.phjt.shangxueyuan.bean.model.File.DOWNLOAD_PAUSE);
            file.setProgress((int) (txVodDownloadMediaInfo.getProgress() * 100));
            file.setSize((long) txVodDownloadMediaInfo.getDownloadSize());
            fileDao.update(file);
            DownLoadCacheService.txStopDownloadVideo(mVideoInfoList.get(0).getSeq());
        }

        @Override
        public int hlsKeyVerify(TXVodDownloadMediaInfo txVodDownloadMediaInfo, String s, byte[] bytes) {
            Log.e(TAG, "onDownloadProgress" + "hlsKeyVerify"  + s);
            return 0;
        }
    }


//    private VideoInfo queryVideoFromFileId(String fileId) {
////        List<VideoInfo> mVideoInfoList1 = LitePal.where("videoFileId = ?", fileId).find(VideoInfo.class);
//        List<VideoInfo> mVideoInfoList = videoInfoDao.queryBuilder().where(VideoInfoDao.Properties.VideoFileId.eq(fileId)).build().list();
//        return mVideoInfoList.get(0);
//    }

    private void txDownloadVideo(String downloadurl) {
        TXVodDownloadManager mDownloadManager = TXVodDownloadManager.getInstance();
        //腾讯云设置下载路径
        mDownloadManager.setDownloadPath(getFilePath(this));
        mDownloadManager.setListener(new VideoITXVodDownloadListener());
        mDownloadManager.startDownloadUrl(downloadurl);
    }

    public static String getFilePath(Context context) {
        File directoryPath = null;
        //判断外部存储是否可用
        if (MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            directoryPath = context.getExternalFilesDir("");
        }


        //判断文件目录是否存在
        if (directoryPath == null || !directoryPath.exists()) {
            return null;
        }
        return directoryPath.getAbsolutePath();
    }

    /**
     * 停止正在下载的任务
     *
     * @param taskId
     */
    public static void txStopDownloadVideo(int taskId) {
        TXVodDownloadManager mDownloadManager = TXVodDownloadManager.getInstance();
        //腾讯云设置下载路径
        try {
            TXVodDownloadMediaInfo txVodDownloadMediaInfo = new TXVodDownloadMediaInfo();
            Field tid = txVodDownloadMediaInfo.getClass().getDeclaredField("tid");
            tid.setAccessible(true);
            tid.setInt(txVodDownloadMediaInfo, taskId);
            txVodDownloadMediaInfo = mDownMap1.get(taskId);
            mDownloadManager.stopDownload(txVodDownloadMediaInfo);
            mDownMap.remove(taskId);
            mDownMap1.remove(taskId);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void txDeleteDownloadVideo(com.phjt.shangxueyuan.bean.model.File videoInfo) {
        int taskId = videoInfo.getSeq();
        DownloadRunning downloadRunning = mDownMap.get(taskId);
        if (downloadRunning != null) {
            //删除的是正在进行的任务
            txStopDownloadVideo(taskId);
        }
        String localVideoPath = videoInfo.getPath();
        if (localVideoPath != null) {
            File file = new File(localVideoPath);
            List<com.phjt.shangxueyuan.bean.model.File> mVideoInfoList = videoInfoDao.queryBuilder().where(FileDao.Properties.Path.eq(localVideoPath)).build().list();
            if (mVideoInfoList.size()==1){
                if (file.exists()) {
                    TXVodDownloadManager mDownloadManager = TXVodDownloadManager.getInstance();
                    mDownloadManager.deleteDownloadFile(localVideoPath);
                    file.delete();
                }
            }

        }
    }

//    public static void txDeleteCachedDownloadVideo(VideoInfo videoInfo) {
//        String localVideoPath = videoInfo.getLocalVideoPath();
//        if (localVideoPath != null) {
//            File file = new File(localVideoPath);
//            if (file.exists()) {
//                TXVodDownloadManager mDownloadManager = TXVodDownloadManager.getInstance();
//                mDownloadManager.deleteDownloadFile(localVideoPath);
//                file.delete();
//            }
//        }
//        videoInfoDao.deleteByKey(videoInfo.getVideoFileId());
////        LitePal.deleteAll(VideoInfo.class, "videoFileId = ?", videoInfo.getVideoFileId());
//        DownLoadMessage downLoadMessage = new DownLoadMessage(DownLoadMessage.DOWN_DELETE);
//        EventBus.getDefault().post(downLoadMessage);
//    }


}
