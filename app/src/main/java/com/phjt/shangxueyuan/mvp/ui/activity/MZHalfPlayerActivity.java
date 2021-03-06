package com.phjt.shangxueyuan.mvp.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

import com.mengzhu.live.sdk.business.dto.AnchorInfoDto;
import com.mengzhu.live.sdk.business.dto.MZGoodsListDto;
import com.mengzhu.live.sdk.business.dto.MZOnlineUserListDto;
import com.mengzhu.live.sdk.business.dto.ad.MZAdDto;
import com.mengzhu.live.sdk.business.dto.chat.ChatTextDto;
import com.mengzhu.live.sdk.business.dto.play.PlayInfoDto;
import com.mengzhu.live.sdk.core.utils.ToastUtils;
import com.mengzhu.live.sdk.ui.widgets.popupwindow.MZFullScreenPopupWindow;
import com.mzmedia.IPlayerClickListener;
import com.mzmedia.fragment.HalfPlayerFragment;
import com.mzmedia.fragment.PlayerFragment;
import com.mzmedia.widgets.popupwindow.MZWatchAuthPopWindow;
import com.phjt.base.mvp.IBaseView;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.BaseBean;
import com.phjt.shangxueyuan.bean.LiveShareImgBean;
import com.phjt.shangxueyuan.common.CommonHttpManager;
import com.phjt.shangxueyuan.mvp.model.api.ApiService;
import com.phjt.shangxueyuan.utils.MZLiveUtils;
import com.phjt.shangxueyuan.utils.RxUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by DELL on 2018/10/12.
 */
public class MZHalfPlayerActivity extends MZBasePlayerActivity implements IPlayerClickListener, IBaseView {

    private FragmentManager mFragmentManager;
    private HalfPlayerFragment mPlayerFragment;
    private MZWatchAuthPopWindow authPopWindow;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    showFullScreenDialog();
                    break;
                case 2:
                    mFragmentManager.beginTransaction().replace(R.id.container_activity_watch_broadcast, mPlayerFragment).commitAllowingStateLoss();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void doAfterGetLiveState(boolean isCanPlay) {
        //??????????????????
        mPlayerFragment = HalfPlayerFragment.newInstance(
                getIntent().getStringExtra(HalfPlayerFragment.APP_ID),
                getIntent().getStringExtra(HalfPlayerFragment.AVATAR),
                getIntent().getStringExtra(HalfPlayerFragment.NICKNAME),
                getIntent().getStringExtra(HalfPlayerFragment.UNIQUE_ID),
                getIntent().getStringExtra(HalfPlayerFragment.TICKET_ID),
                getIntent().getStringExtra(HalfPlayerFragment.LIVE_ID),
                isCanPlay);
        List<HashMap<String, Fragment>> tabs = new ArrayList<>();
        HashMap<String, Fragment> tab = new HashMap<>();
        tab.put("??????Tab", new Fragment());
        tabs.add(tab);
//        mPlayerFragment.setBottomTabs(tabs); // ????????????fragment replace????????????
        mFragmentManager = getSupportFragmentManager();
//        authPopWindow = new MZWatchAuthPopWindow(this, mTicketId, phone);
//        authPopWindow.setOnCheckResultListener(new MZWatchAuthPopWindow.OnCheckResultListener() {
//            @Override
//            public void onSuccess() {
//                isFullScreen();
//            }
//
//            @Override
//            public void onFailed() {
//                finish();
//            }
//        });
        isFullScreen();
        mPlayerFragment.setIPlayerClickListener(this);
    }

    @Override
    public void reloadLiveStatus() {
        if (mPlayerFragment != null) {
            mPlayerFragment.reloadLiveStatus();
        }
    }

    private MZFullScreenPopupWindow popUp;
    private boolean isLink = false;

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
//        authPopWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    private void isFullScreen() {

        popUp = new MZFullScreenPopupWindow(this, mTicketId);
        popUp.setClippingEnabled(false);
        popUp.setBackgroundDrawable(null);
        popUp.setOnGetMZAdResult(new MZFullScreenPopupWindow.OnGetMZAdResultListener() {
            @Override
            public void onMzAdResult(MZAdDto mzAdDto) {
                /**
                 * MZAdFullScreenDto ????????????
                 */
                if (mzAdDto.full_screen.getContent() != null) {
                    if (mzAdDto.full_screen.getContent().size() > 0) {
                        handler.sendEmptyMessage(1);
                    }
                } else {
                    handler.sendEmptyMessage(2);
                }
            }
        });
        popUp.setOnImagerClickListener(new MZFullScreenPopupWindow.OnImagerClickListener() {
            @Override
            public void onImagerClick(String link) {
                //???????????????????????????????????????????????????
                ToastUtils.popUpToast("??????????????????=" + link);
//                isLink = true;
                if (!TextUtils.isEmpty(link)) {
                    popUp.dismiss();
                }
            }
        });
        popUp.setOnFullScreenDismissListener(new MZFullScreenPopupWindow.OnFullScreenDismiss() {
            @Override
            public void onDismiss() {
                //??????????????????
                if (!isLink) {
                    handler.sendEmptyMessage(2);
                }
            }
        });
    }

    private void showFullScreenDialog() {
        popUp.showAtLocation(findViewById(R.id.container_activity_watch_broadcast), Gravity.CENTER, 0, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //???????????????????????????????????????fragment ?????????????????????
//        if (isLink) {
//            isLink = false;
//            handler.sendEmptyMessage(2);
//        }
    }


    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * ?????????????????????????????????
     */
    @Override
    public void onAvatarClick(AnchorInfoDto dto) {
//        Toast.makeText(this, "??????????????????", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAttentionClick(PlayInfoDto dto, TextView Attention) {
//        Toast.makeText(this, "????????????", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onOnlineClick(MZOnlineUserListDto onlineUserDto) {
//        Toast.makeText(this, "??????????????????", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCloseClick(PlayInfoDto dto) {
//        Toast.makeText(this, "????????????", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void onReportClick(PlayInfoDto dto) {
//        Toast.makeText(this, "????????????", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onShareClick(PlayInfoDto dto) {
        //????????????
        getSharepicture();
    }


    @Override
    public void onLikeClick(PlayInfoDto dto, ImageView Like) {

//        Toast.makeText(this, "????????????", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRecommendGoods(MZGoodsListDto dto) {
//        Toast.makeText(this, "??????????????????", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onGoodsListItem(MZGoodsListDto dto) {
//        Toast.makeText(this, "??????????????????", Toast.LENGTH_LONG).show();
        MZLiveUtils.jumpPage(this, dto.getBuy_url());
    }

    @Override
    public void onChatAvatar(ChatTextDto dto) {
//        Toast.makeText(this, "????????????????????????", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNotLogin(PlayInfoDto dto) {
//        Toast.makeText(this, "???????????????????????????", Toast.LENGTH_LONG).show();
    }

    @Override
    public void resultAnchorInfo(AnchorInfoDto anchorInfoDto) {
//        Toast.makeText(this, "????????????????????????" + anchorInfoDto.getNickname(), Toast.LENGTH_LONG).show();
    }

    /**
     * ??????????????????
     */
    @SuppressLint("CheckResult")
    private void getSharepicture() {
        CommonHttpManager.getInstance(this)
                .obtainRetrofitService(ApiService.class)
                .getSharepicture(mLiveId)
                .compose(RxUtils.applySchedulers())
                .subscribe(new Observer<BaseBean<List<LiveShareImgBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseBean<List<LiveShareImgBean>> bean) {
                        if (bean.isOk()) {
                            BaseBean<List<LiveShareImgBean>> beans = bean;
                            if (beans != null) {
                                Intent intent = new Intent(MZHalfPlayerActivity.this, LiveSharingActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("data", beans);
                                intent.putExtra(PlayerFragment.LIVE_ID,mLiveId);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }

                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }


    @Override
    public void showMessage(@NonNull String message) {

    }
}
