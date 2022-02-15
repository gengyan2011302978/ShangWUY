package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.di.component.DaggerPlayerComponent;
import com.phjt.shangxueyuan.mvp.contract.PlayerContract;
import com.phjt.shangxueyuan.mvp.presenter.PlayerPresenter;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.tencent.liteav.demo.play.SuperPlayerModel;
import com.tencent.liteav.demo.play.SuperPlayerVideoId;
import com.tencent.liteav.demo.play.SuperPlayerView;

import butterknife.BindView;

import static com.phjt.base.utils.Preconditions.checkNotNull;


/**
 * @author: Created by Template
 * date: 03/25/2020 14:40
 * company: 普华集团
 * description: 模版请保持更新
 */
public class PlayerActivity extends BaseActivity<PlayerPresenter> implements PlayerContract.View {

    @BindView(R.id.txc_video)
    SuperPlayerView txcVideo;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerPlayerComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_player;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        SuperPlayerModel model = new SuperPlayerModel();
        model.appId = 1400329071;
        // 配置 AppId
        model.videoId = new SuperPlayerVideoId();
        model.videoId.fileId = "5285890799710173650";
        // 配置 FileId
        model.videoId.pSign = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcHBJZCI6MTQwMDMyOTA3MSwiZmlsZUlkIjoiNTI4NTg5MDc5OTcxMDE3MzY1MCIsImN1cnJlbnRUaW1lU3RhbXAiOjEsImV4cGlyZVRpbWVTdGFtcCI6MjE0NzQ4MzY0NywidXJsQWNjZXNzSW5mbyI6eyJ0IjoiN2ZmZmZmZmYifSwiZHJtTGljZW5zZUluZm8iOnsiZXhwaXJlVGltZVN0YW1wIjoyMTQ3NDgzNjQ3fX0.yJxpnQ2Evp5KZQFfuBBK05BoPpQAzYAWo6liXws-LzU";
        txcVideo.playWithModel(model);
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
        TipsUtil.showTips(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArchitectUtils.startActivity(intent);
    }
}
