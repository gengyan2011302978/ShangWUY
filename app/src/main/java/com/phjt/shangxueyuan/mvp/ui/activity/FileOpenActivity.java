package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.Intent;
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.tencent.smtt.sdk.TbsReaderView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import butterknife.BindView;
import butterknife.OnClick;

public class FileOpenActivity extends BaseActivity {

    @BindView(R.id.iv_common_back)
    ImageView mIvCommonBack;
    @BindView(R.id.file_layout)
    FrameLayout mFileLayout;

    private TbsReaderView mTbsReaderView;
    private String path;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_file_open;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initTbsReaderView();
        Intent intent = getIntent();
        if (intent != null) {
            path = intent.getStringExtra(Constant.FILE_SAVE_PATH);
        }

        if (!TextUtils.isEmpty(path)) {
            File file = new File(path);

            if (file.exists()) {
                if (copyFile(path, Environment.getExternalStorageDirectory() + "/" + path.split("download/")[1])) {
                    path = Environment.getExternalStorageDirectory() + "/" + path.split("download/")[1];
                    showOffice(new File(path));
                }
            } else {
                TipsUtil.show("文件为空");
            }
        } else {
            TipsUtil.show("文件路径为空");
        }
    }

    public boolean copyFile(String oldPath$Name, String newPath$Name) {
        try {
            File oldFile = new File(oldPath$Name);
            if (!oldFile.exists()) {
                return false;
            } else if (!oldFile.isFile()) {
                return false;
            } else if (!oldFile.canRead()) {
                return false;
            }

            FileInputStream fileInputStream = new FileInputStream(oldPath$Name);
            FileOutputStream fileOutputStream = new FileOutputStream(newPath$Name);
            byte[] buffer = new byte[1024];
            int byteRead;
            while (-1 != (byteRead = fileInputStream.read(buffer))) {
                fileOutputStream.write(buffer, 0, byteRead);
            }
            fileInputStream.close();
            fileOutputStream.flush();
            fileOutputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void initTbsReaderView() {
        mTbsReaderView = new TbsReaderView(this, new TbsReaderView.ReaderCallback() {
            @Override
            public void onCallBackAction(Integer integer, Object o, Object o1) {
                //ReaderCallback 接口提供的方法可以不予处理（目前不知道有什么用途，但是一定要实现这个接口类）
            }
        });
        mFileLayout.addView(mTbsReaderView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    private void showOffice(File file) {
        Bundle bundle = new Bundle();
        bundle.putString("filePath", file.toString());
        bundle.putString("tempPath", path);
        boolean result = mTbsReaderView.preOpen(parseFormat(file.toString()), false);
        if (result) {
            mTbsReaderView.openFile(bundle);
        }
    }

    private String parseFormat(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    @OnClick(R.id.iv_common_back)
    public void click(View view) {
        finish();
    }

    @Override
    public void onDestroy() {
        if (mTbsReaderView != null) {
            mTbsReaderView.onStop();
        }
        super.onDestroy();
    }
}