package com.phjt.shangxueyuan.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.widget.TextView;

import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.annotate.SingleClick;
import com.phsxy.utils.LogUtils;

import java.io.File;

/**
 * @author: Roy
 * date:   2020/12/17
 * company: 普华集团
 * description:
 */
public class WebCameraHelper {
    private static class SingletonHolder {
        static final WebCameraHelper INSTANCE = new WebCameraHelper();
    }

    public static WebCameraHelper getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 图片选择回调
     */
    public ValueCallback<Uri> mUploadMessage;
    public ValueCallback<Uri[]> mUploadCallbackAboveL;

    public Uri fileUri;
    public static final int TYPE_REQUEST_PERMISSION = 3;
    public static final int TYPE_CAMERA = 1;
    public static final int TYPE_GALLERY = 2;

    /**
     * 包含拍照和相册选择
     */
    @SingleClick
    public void showOptions(final Activity act) {
        AlertDialog.Builder alertDialog = null;
        if (alertDialog == null) {
            alertDialog = new AlertDialog.Builder(act);
            alertDialog.setOnCancelListener(new ReOnCancelListener());
            alertDialog.setTitle("选择");
            alertDialog.setItems(new CharSequence[]{"相机", "相册"},
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == 0) {
                                if (ContextCompat.checkSelfPermission(act,
                                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                    // 申请WRITE_EXTERNAL_STORAGE权限
                                    ActivityCompat.requestPermissions(act,
                                            new String[]{Manifest.permission.CAMERA,
                                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                                                    Manifest.permission.ACCESS_NETWORK_STATE},
                                            TYPE_REQUEST_PERMISSION);
                                } else {
                                    toCamera(act);
                                }
                            } else {
                                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                // 调用android的图库
                                act.startActivityForResult(i, TYPE_GALLERY);
                            }
                        }
                    });
            alertDialog.show();
        }
    }

    /**
     * 重新获取权限
     *
     * @param act
     */
    @SingleClick
    public void againPermissions(final Activity act) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(act);
        alertDialog.setOnCancelListener(new ReOnCancelListener());
        final View view = View.inflate(act, R.layout.dialog_register, null);
        TextView btn_dismiss = (TextView) view.findViewById(R.id.tv_sure);
        alertDialog.setView(view);
        AlertDialog dialog = alertDialog.show();
        btn_dismiss.setOnClickListener(v -> {
            dialog.cancel();
            dialog.dismiss();
        });
    }

    /**
     * 点击取消的回调
     */
    public class ReOnCancelListener implements DialogInterface.OnCancelListener {

        @Override
        public void onCancel(DialogInterface dialogInterface) {
            if (mUploadMessage != null) {
                mUploadMessage.onReceiveValue(null);
                mUploadMessage = null;
            }
            if (mUploadCallbackAboveL != null) {
                mUploadCallbackAboveL.onReceiveValue(null);
                mUploadCallbackAboveL = null;
            }
        }
    }

    /**
     * 请求拍照
     *
     * @param act
     */
    public void toCamera(Activity act) {

        // 调用android的相机
        // 创建一个文件保存图片
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri imageUri;
            String mCompressPath = Environment.getExternalStorageDirectory().getPath();
            File outputImage = new File(mCompressPath, "img" + System.currentTimeMillis() + ".jpg");
            if (Build.VERSION.SDK_INT > 24) {
                String p = act.getPackageName() + ".fileprovider";
                imageUri = FileProvider.getUriForFile(act, p, outputImage);
            } else {
                imageUri = Uri.fromFile(outputImage);
            }

            fileUri = imageUri;
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            act.startActivityForResult(intent, TYPE_CAMERA);
        } catch (Exception e) {
            LogUtils.e("===================" + e.toString());
        }
    }

    /**
     * startActivityForResult之后要做的处理
     *
     * @param requestCode
     * @param resultCode
     * @param intent
     */
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == TYPE_CAMERA) { // 相册选择
            if (resultCode == -1) {//RESULT_OK = -1，拍照成功
                if (mUploadCallbackAboveL != null) { //高版本SDK处理方法
                    Uri[] uris = new Uri[]{fileUri};
                    mUploadCallbackAboveL.onReceiveValue(uris);
                    mUploadCallbackAboveL = null;
                } else if (mUploadMessage != null) { //低版本SDK 处理方法
                    mUploadMessage.onReceiveValue(fileUri);
                    mUploadMessage = null;
                } else {
//                    Toast.makeText(CubeAndroid.this, "无法获取数据", Toast.LENGTH_LONG).show();
                }
            } else { //拍照不成功，或者什么也不做就返回了，以下的处理非常有必要，不然web页面不会有任何响应
                if (mUploadCallbackAboveL != null) {
                    mUploadCallbackAboveL.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, intent));
                    mUploadCallbackAboveL = null;
                } else if (mUploadMessage != null) {
                    mUploadMessage.onReceiveValue(fileUri);
                    mUploadMessage = null;
                } else {
//                    Toast.makeText(CubeAndroid.this, "无法获取数据", Toast.LENGTH_LONG).show();
                }

            }
        } else if (requestCode == TYPE_GALLERY) {// 相册选择
            if (mUploadCallbackAboveL != null) {
                mUploadCallbackAboveL.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, intent));
                mUploadCallbackAboveL = null;
            } else if (mUploadMessage != null) {
                Uri result = intent == null || resultCode != Activity.RESULT_OK ? null : intent.getData();
                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;
            } else {
//                Toast.makeText(CubeAndroid.this, "无法获取数据", Toast.LENGTH_LONG).show();
            }
        }
    }


}
