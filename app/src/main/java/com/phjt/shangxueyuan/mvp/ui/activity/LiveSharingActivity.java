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
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mzmedia.fragment.PlayerFragment;
import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.annotate.SingleClick;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.LiveShareImgBean;
import com.phjt.shangxueyuan.common.AppImageLoader;
import com.phjt.shangxueyuan.di.component.DaggerLiveSharingComponent;
import com.phjt.shangxueyuan.mvp.contract.LiveSharingContract;
import com.phjt.shangxueyuan.mvp.presenter.LiveSharingPresenter;
import com.phjt.shangxueyuan.utils.ShareUtils;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.shangxueyuan.utils.Zxingutil;

import com.phjt.shangxueyuan.widgets.LiveGlideImageLoader;
import com.phjt.sharestatistic.ShareInit;
import com.phjt.sharestatistic.entity.SharePlatType;
import com.youth.banner.BannerConfig;
import com.youth.banner.BannerShare;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;
import static com.phjt.shangxueyuan.widgets.LiveGlideImageLoader.listBitmap;

/**
 * Created by Template
 *
 * @author :Roy
 * description :直播分享
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2021/04/07 17:27
 */
public class LiveSharingActivity extends BaseActivity<LiveSharingPresenter> implements LiveSharingContract.View {

    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.wb_qr_code)
    ImageView ivQrCode;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.cl_share)
    RelativeLayout clShare;
    @BindView(R.id.rl_live_sharing)
    RelativeLayout rlLiveSharing;
    @BindView(R.id.banner_live_sharing)
    BannerShare mBannerShare;

    private String url = "";
    private int bitmapindex = 1;
    private List<LiveShareImgBean> shareImgBeans;
    public static List<String> imageList = new ArrayList<>();
    public List<String> titlesList;
    public List<String> codeList;
    private String mLiveId="";


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        //如找不到该类,请编译一下项目
        DaggerLiveSharingComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_live_sharing;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvCommonTitle.setText("直播分享");
        shareImgBeans = new ArrayList<>();
        titlesList = new ArrayList<>();
        codeList = new ArrayList<>();
        Intent intent = getIntent();
        if (intent != null) {
            BaseBean<List<LiveShareImgBean>> baseBean = (BaseBean<List<LiveShareImgBean>>) intent.getSerializableExtra("data");
            if (baseBean != null) {
                shareImgBeans = baseBean.data;
            }
            mLiveId = getIntent().getStringExtra(PlayerFragment.LIVE_ID);
            if (shareImgBeans != null && shareImgBeans.size() > 0) {
                url = shareImgBeans.get(0).getUrl();
                initImageBitmap();
            }
        }
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
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * View 层的默认方法 可以不实现 直接在 P 层 调用 此方法
     * Demo
     *
     * @param intent {@code intent} 不能为 {@code null}
     */
    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArchitectUtils.startActivity(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ShareInit.getInstance().onActivityResult(this, requestCode, resultCode, data);
    }


    @Override
    public void getgetSharepictureSuccess(List<LiveShareImgBean> beans) {

    }

    @Override
    public void fail() {

    }

    private void initImageBitmap() {
        Bitmap mBitmap = Zxingutil.createQRCodeBitmap(url, 600, 600);
        ivQrCode.setImageBitmap(mBitmap);
        listBitmap.clear();
        codeList.clear();

        for (int i = 0; i < shareImgBeans.size(); i++) {
            listBitmap.add(mBitmap);
            imageList.add(shareImgBeans.get(i).getInviteImg());
            titlesList.add("");
            codeList.add(shareImgBeans.get(i).getUrl());
            AppImageLoader.loadResUrl(shareImgBeans.get(i).getInviteImg(), ivShare);
        }
        if (shareImgBeans.size() != 0) {
            listBitmap.add(mBitmap);
            listBitmap.add(mBitmap);
        }

        mBannerShare.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                .setImageLoader(new LiveGlideImageLoader(), 3)
                .setImages(imageList, url)
                .setCodeList(codeList)
                .setBannerTitles(titlesList)
                .setBannerAnimation(Transformer.ZoomOutSlide)
                .isAutoPlay(false)
                .setDelayTime(5000)
                .setIndicatorGravity(BannerConfig.CENTER)
                .setOffscreenPageLimit(imageList.size())
                .start();

        mBannerShare.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                bitmapindex = i + 1;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @SingleClick
    @OnClick({R.id.iv_common_back, R.id.iv_wechat, R.id.iv_wechat_circle, R.id.iv_copy_link})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_common_back:
                finish();
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
                break;
            case R.id.iv_copy_link:
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData mClipMailbox = ClipData.newPlainText("Label", url);
                cm.setPrimaryClip(mClipMailbox);
                addUserIntegralRecord();
                TipsUtil.showTips("已复制到粘贴板");

                break;
            default:
                break;
        }
    }
    public void addUserIntegralRecord() {
        if (null != mPresenter) {
            mPresenter.addUserIntegralRecord(mLiveId);
        }
    }

    /**
     * 分享图片
     */
    private void shareInvitation(Bitmap file, int type) {
        ShareUtils.shareImgData(this, file, type == 1 ? SharePlatType.WeChat : SharePlatType.WeChatCircle);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (shareImgBeans != null) {
            shareImgBeans.clear();
        }
        if (imageList != null) {
            imageList.clear();
        }
        if (titlesList != null) {
            titlesList.clear();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
