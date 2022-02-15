package com.phjt.shangxueyuan.utils;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Looper;
import android.provider.MediaStore;

import com.phjt.base.utils.ArchitectUtils;
import com.phsxy.utils.ToastUtils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * @author: gengyan
 * date:    2019/11/15
 * company: 普华集团
 * description: 文件写入
 */
public class FileUtils {
    /**
     * 单线程列队执行
     */
    private ExecutorService singleExecutor;
    private File imgUri;
    public static final String PIC_DIR_NAME = "myPhotos"; //在系统的图片文件夹下创建了一个相册文件夹，名为“myPhotos"，所有的图片都保存在该文件夹下。
    private static File mPicDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), PIC_DIR_NAME); //图片统一保存在系统的图片文件夹中
    private File writeFile(InputStream is, File file) throws IOException {
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdir();
        }
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(file);
        BufferedInputStream bis = new BufferedInputStream(is);
        byte[] buffer = new byte[1024];
        int len;
        while ((len = bis.read(buffer)) != -1) {
            fos.write(buffer, 0, len);
        }
        fos.flush();
        fos.close();
        bis.close();
        is.close();

        return file;
    }


    /**
     * 执行单线程列队执行
     */
    private void runOnQueue(Runnable runnable) {
        if (singleExecutor == null) {
            singleExecutor = Executors.newSingleThreadExecutor();
        }
        singleExecutor.submit(runnable);
        singleExecutor.shutdown();
    }

    /**
     * 启动图片下载线程
     */
    public void onDownLoad(Context context, String url, boolean tips) {
        DownLoadImageService service = new DownLoadImageService(context, url,
                new DownLoadImageService.ImageDownLoadCallBack() {
                    @Override
                    public void onDownLoadSuccess(File files) throws IOException {
                        // 在这里执行图片保存方法
                        Context appContext = ArchitectUtils.obtainAppComponentFromContext(context).application();
                        File downloadFileDir = appContext.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
                        String fileName = System.currentTimeMillis() + ".jpg";
                        File saveDownloadFilePath = new File(downloadFileDir, fileName);
                        if (!saveDownloadFilePath.exists()) {
                            saveDownloadFilePath.createNewFile();
                        }
                        FileInputStream fileInputStream = new FileInputStream(files);
                        setUri(writeFile(fileInputStream, saveDownloadFilePath));
//
//                        //把文件插入到系统图库
//                        try {
//                            MediaStore.Images.Media.insertImage(appContext.getContentResolver(),
//                                    saveDownloadFilePath.getAbsolutePath(), fileName, null);
//                        } catch (FileNotFoundException e) {
//                            e.printStackTrace();
//                        }
//                        // 通知图库更新
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                            MediaScannerConnection.scanFile(appContext, new String[]{saveDownloadFilePath.getAbsolutePath()}, null,
//                                    new MediaScannerConnection.OnScanCompletedListener() {
//                                        @Override
//                                        public void onScanCompleted(String path, Uri uri) {
//                                            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//                                            mediaScanIntent.setData(uri);
//                                            appContext.sendBroadcast(mediaScanIntent);
//                                        }
//                                    });
//                        } else {
//                            String relationDir = saveDownloadFilePath.getParent();
//                            File file1 = new File(relationDir);
//                            appContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.fromFile(file1.getAbsoluteFile())));
//                        }
                        saveBitmapToGallery(context,fileName, BitmapFactory.decodeFile(saveDownloadFilePath.getAbsolutePath()));
                        if (tips) {
                            if(Looper.myLooper() == null)
                            {
                                Looper.prepare();
                            }
                            ToastUtils.show("图片保存成功");
                            Looper.loop();
                        }
                    }

                    @Override
                    public void onDownLoadFailed() {
                        // 图片保存失败
                        if (tips) {
                            if(Looper.myLooper() == null)
                            {
                                Looper.prepare();
                            }
                            ToastUtils.show("图片保存失败");
                            Looper.loop();
                        }
                    }
                });
        //启动图片下载线程
        runOnQueue(service);
    }





    public void setUri(File file) {
        imgUri = file;
    }

    public File getUri() {
        return imgUri;
    }
    public static Uri saveBitmapToGallery(final Context mContext, String fileName, Bitmap bitmap) {
        OutputStream out = null;
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream(1920 * 1920);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            long size = stream.size();
            stream.close();
            mPicDir.mkdirs();
            String mPicPath = new File(mPicDir, fileName).getAbsolutePath();
            ContentValues values = new ContentValues();
            ContentResolver resolver = mContext.getContentResolver();
            values.put(MediaStore.Images.ImageColumns.DATA, mPicPath);
            values.put(MediaStore.Images.ImageColumns.DISPLAY_NAME, fileName);
            values.put(MediaStore.Images.ImageColumns.MIME_TYPE, "image/png");
            //将图片的拍摄时间设置为当前的时间
            long current = System.currentTimeMillis() / 1000;
            values.put(MediaStore.Images.ImageColumns.DATE_ADDED, current);
            values.put(MediaStore.Images.ImageColumns.DATE_MODIFIED, current);
            values.put(MediaStore.Images.ImageColumns.DATE_TAKEN, current);
            values.put(MediaStore.Images.ImageColumns.SIZE, size);
            values.put(MediaStore.Images.ImageColumns.WIDTH, bitmap.getWidth());
            values.put(MediaStore.Images.ImageColumns.HEIGHT, bitmap.getHeight());
            Uri uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            if (uri != null) {
                out = resolver.openOutputStream(uri);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                return uri;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
