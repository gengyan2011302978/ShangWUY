package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.annotate.SingleClick;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.ShareImgBean;
import com.phjt.shangxueyuan.common.AppImageLoader;
import com.phjt.shangxueyuan.di.component.DaggerShareComponent;
import com.phjt.shangxueyuan.mvp.contract.ShareContract;
import com.phjt.shangxueyuan.mvp.presenter.SharePresenter;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.ConstantUmeng;
import com.phjt.shangxueyuan.utils.ConstantWeb;
import com.phjt.shangxueyuan.utils.ShareUtils;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.shangxueyuan.utils.UmengUtil;
import com.phjt.shangxueyuan.utils.Zxingutil;
import com.phjt.shangxueyuan.widgets.GlideImageLoader;
import com.phjt.sharestatistic.ShareInit;
import com.phjt.sharestatistic.entity.SharePlatType;
import com.youth.banner.BannerConfig;
import com.youth.banner.BannerShare;
import com.youth.banner.Transformer;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.shangxueyuan.widgets.GlideImageLoader.listBitmap;


/**
 * @author: Created by GengYan
 * date: 07/07/2020 17:32
 * company: 普华集团
 * description: 模版请保持更新
 */
public class ShareActivity extends BaseActivity<SharePresenter> implements ShareContract.View {

    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.ic_common_right)
    ImageView mIvRight;
    @BindView(R.id.share_the_invitation)
    ConstraintLayout share_the_invitation;
    @BindView(R.id.wb_qr_code)
    ImageView ivQrCode;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.cl_share)
    RelativeLayout clShare;

    private String mTitle;
    private String url = "", mCodeData;
    @BindView(R.id.banner)
    BannerShare banner;
    List<ShareImgBean> shareImgBeans = new ArrayList<>();
    private int bitmapindex = 1;
    public static List<String> images = new ArrayList<>();

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerShareComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_share_web_view;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initContentView();
        Bitmap mBitmap = Zxingutil.createQRCodeBitmap(url, 600, 600);
        ivQrCode.setImageBitmap(mBitmap);
        images = new ArrayList<>();
        listBitmap.clear();
        for (int i = 0; i < shareImgBeans.size(); i++) {
            listBitmap.add(mBitmap);
            images.add(shareImgBeans.get(i).getInviteImg());
        }
        if (shareImgBeans.size() != 0) {
            listBitmap.add(mBitmap);
            listBitmap.add(mBitmap);
        }
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                .setImageLoader(new GlideImageLoader(), 2)
                .setImages(images, url)
                .setBannerAnimation(Transformer.ZoomOutSlide)
                .isAutoPlay(false)
                .setDelayTime(5000)

                .setIndicatorGravity(BannerConfig.CENTER)
                .setOffscreenPageLimit(images.size())
                .start();
        banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
//                if (i+1==images.size()){
//                    bitmapindex = 0;
//                }else {
                bitmapindex = i + 1;
//                }
                System.out.println(bitmapindex + "__________________" + i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }


    private void initContentView() {
        Intent intent = getIntent();
        if (intent != null) {
            BaseBean<List<ShareImgBean>> baseBean = (BaseBean<List<ShareImgBean>>) intent.getSerializableExtra("data");
            if (baseBean != null) {
                shareImgBeans = baseBean.data;
            }
            url = intent.getStringExtra(Constant.BUNDLE_WEB_URL);
            tvCommonTitle.setText("邀请有礼");
        }
        if (null != mPresenter) {
            mPresenter.getConfig();
        }
    }

    @SingleClick
    @OnClick({R.id.iv_common_back, R.id.iv_yq, R.id.iv_gz, R.id.iv_wechat, R.id.iv_wechat_circle, R.id.iv_copy_link})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_common_back:
                finish();
                break;
            case R.id.iv_yq:
                //startWebview(2);
                //我的邀请
                Intent intent = new Intent(this, MyWalletActivity.class);
                startActivity(intent);
                UmengUtil.umengCount(this, ConstantUmeng.MINE_CLICK_SHARE_NICE);
                break;
            case R.id.iv_gz:
                startWebview(1);
                UmengUtil.umengCount(this, ConstantUmeng.MINE_CLICK_SHARE_RULE);
                break;
            case R.id.iv_wechat:
                Bitmap viewBitmap;
                if (listBitmap.size() == 1) {
                    viewBitmap = listBitmap.get(0);
                } else if (listBitmap.size() == 0) {
                    Resources res = getResources();
                    Bitmap bmp = BitmapFactory.decodeResource(res, R.drawable.share_by_bg);
                    viewBitmap = bmp;
                } else {
                    viewBitmap = listBitmap.get(bitmapindex);
                }
                shareInvitation(viewBitmap, 1);
                addUserIntegralRecord();
                UmengUtil.umengCount(this, ConstantUmeng.MINE_CLICK_SHARE_WECHAT);
                break;
            case R.id.iv_wechat_circle:
                Bitmap viewBitmap2;
                if (listBitmap.size() == 1) {
                    viewBitmap2 = listBitmap.get(0);
                } else if (listBitmap.size() == 0) {
                    Resources res = getResources();
                    Bitmap bmp = BitmapFactory.decodeResource(res, R.drawable.share_by_bg);
                    viewBitmap2 = bmp;
                } else {
                    viewBitmap2 = listBitmap.get(bitmapindex);
                }
                shareInvitation(viewBitmap2, 2);
                addUserIntegralRecord();
                UmengUtil.umengCount(this, ConstantUmeng.MINE_CLICK_SHARE_CIRCLE);
                break;
            case R.id.iv_copy_link:
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData mClipMailbox = ClipData.newPlainText("Label", url);
                cm.setPrimaryClip(mClipMailbox);
                TipsUtil.showTips("已复制到粘贴板");
                addUserIntegralRecord();
                UmengUtil.umengCount(this, ConstantUmeng.MINE_CLICK_SHARE_COPY);
                break;
            default:
                break;
        }
    }

    public void addUserIntegralRecord() {
        if (null != mPresenter) {
            mPresenter.addUserIntegralRecord();
        }
    }

    private void startWebview(int type) {
        Intent intent = new Intent(ShareActivity.this, MyWebViewActivity.class);
        intent.putExtra(Constant.BUNDLE_WEB_TITLE, "");
        intent.putExtra(Constant.BUNDLE_WEB_URL, ConstantWeb.getH5AddressById(ConstantWeb.H5_SHARERULES) + "?type=" + type);
        startActivity(intent);
    }

    /**
     * 如果布局文件没有超出屏幕高度的话的做法
     */
    public static Bitmap getViewBitmap(View v) {
        v.clearFocus();
        v.setPressed(false);
        boolean willNotCache = v.willNotCacheDrawing();
        v.setWillNotCacheDrawing(false);
        int color = v.getDrawingCacheBackgroundColor();
        v.setDrawingCacheBackgroundColor(0);
        if (color != 0) {
            v.destroyDrawingCache();
        }
        v.buildDrawingCache();
        Bitmap cacheBitmap = v.getDrawingCache();
        if (cacheBitmap == null) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);
        v.destroyDrawingCache();
        v.setWillNotCacheDrawing(willNotCache);
        v.setDrawingCacheBackgroundColor(color);
        return bitmap;
    }

    /**
     * 分享图片
     */
    private void shareInvitation(Bitmap file, int type) {
        ShareUtils.shareImgData(this, file, type == 1 ? SharePlatType.WeChat : SharePlatType.WeChatCircle);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ShareInit.getInstance().onActivityResult(this, requestCode, resultCode, data);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        TipsUtil.showTips(message);
    }


    /**
     * bitmap保存为file
     */
    public File bitmapToFile(String filePath, Bitmap bitmap) throws IOException {
        File file = null;
        if (bitmap != null) {
            file = new File(filePath.substring(0, filePath.lastIndexOf(File.separator)));
            if (!file.exists()) {
                file.mkdirs();
            }
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(filePath));
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            bos.flush();
            bos.close();
        }
        return file;
    }

    @Override
    public void getConfigSuccess(String imagePath) {
        AppImageLoader.loadResUrl(imagePath, ivShare);
    }

    @Override
    public void fail() {

    }
}
