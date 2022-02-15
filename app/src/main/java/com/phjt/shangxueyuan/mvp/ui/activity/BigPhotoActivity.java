package com.phjt.shangxueyuan.mvp.ui.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.utils.Constant;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;

/**
 * @author: Roy
 * date:   2020/9/17
 * company: 普华集团
 * description:
 */
public class BigPhotoActivity extends BaseActivity {


    private ViewPager viewPager;
    private TextView index;

    private ArrayList<String> imgUrls;
    /**
     * 从哪张图片开始
     */
    private int startPosition;
    private BigPhotoAdapter adapter;


    private int currentPosition, mType;
    private String mUrlPre;
    private Disposable disposable;

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_big_photo;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initData();
        initAdapter();
    }


    private void initData() {
        viewPager = findViewById(R.id.pager);
        ImageView ivBack = findViewById(R.id.iv_common_back);
        TextView tvTitle = findViewById(R.id.tv_common_title);
        TextView tvSave = findViewById(R.id.tv_common_right);
        index = findViewById(R.id.tv_index);
        startPosition = getIntent().getIntExtra(Constant.BUNDLE_BIG_IMAGE_POSITION, 0);
        mType = getIntent().getIntExtra(Constant.BUNDLE_BIG_IMAGE_TYPE, 0);
        mUrlPre = getIntent().getStringExtra(Constant.BUNDLE_BIG_IMAGE_PRE);
        imgUrls = getIntent().getStringArrayListExtra(Constant.BUNDLE_BIG_IMAGE_URLS);
        index.setText((startPosition + 1) + "/" + imgUrls.size());
        tvTitle.setText("查看图片");
        tvSave.setVisibility(View.GONE);
        //tvSave.setOnClickListener(this);
        ivBack.setOnClickListener(v -> finish());
    }


    private void initAdapter() {
        adapter = new BigPhotoAdapter(this, mType, mUrlPre, new BigPhotoAdapter.Listener() {
            @Override
            public void onImageClicked(View view) {
                finish();
            }
        });
        adapter.setDatas(imgUrls);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(startPosition);
        currentPosition = startPosition;
        viewPager.addOnPageChangeListener(pageChangeListener);

    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            currentPosition = position;
            index.setText((position + 1) + "/" + imgUrls.size());
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

//    @SingleClick
//    @Override
//    public void onClick(View v) {
//        if (imgUrls != null) {
//            String urlAll = imgUrls.get(currentPosition);
//            if (mType == 1) {
//                if (TextUtils.isEmpty(mUrlPre)) {
//                    return;
//                }
//                urlAll = mUrlPre + urlAll;
//            }
//            try {
//                downloadFile(urlAll);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//
//    private void downloadFile(String url) throws Exception {
//        if (TextUtils.isEmpty(url) || !url.startsWith("http")) {
//            return;
//        }
//        disposable = Observable.create(new ObservableOnSubscribe<File>() {
//            @Override
//            public void subscribe(ObservableEmitter<File> e) throws Exception {
//                e.onNext(Glide.with(BigPhotoActivity.this)
//                        .load(url)
//                        .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
//                        .get());
//                e.onComplete();
//            }
//        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<File>() {
//            @Override
//            public void accept(File file) throws Exception {
////获取到下载得到的图片，进行本地保存
//                File pictureFolder = Environment.getExternalStorageDirectory();
//                //第二个参数为你想要保存的目录名称
//                File appDir = new File(pictureFolder, "com.phjt.disciplegroup");
//                if (!appDir.exists()) {
//                    appDir.mkdirs();
//                }
//                String fileName = System.currentTimeMillis() + ".jpg";
//                TipsUtil.show("图片保存成功");
//                File destFile = new File(appDir, fileName);
//                //把gilde下载得到图片复制到定义好的目录中去
//                copy(file, destFile);
//
//                // 最后通知图库更新
//                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
//                        Uri.fromFile(new File(destFile.getPath()))));
//            }
//        });
//
//
//    }

//    public void copy(File source, File target) {
//        FileInputStream fileInputStream = null;
//        FileOutputStream fileOutputStream = null;
//        try {
//            fileInputStream = new FileInputStream(source);
//            fileOutputStream = new FileOutputStream(target);
//            byte[] buffer = new byte[1024];
//            while (fileInputStream.read(buffer) > 0) {
//                fileOutputStream.write(buffer);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                fileInputStream.close();
//                fileOutputStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
