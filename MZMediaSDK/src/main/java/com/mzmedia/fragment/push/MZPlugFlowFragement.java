package com.mzmedia.fragment.push;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.mengzhu.live.sdk.business.dto.MZOnlineUserListDto;
import com.mengzhu.live.sdk.business.dto.chat.ChatMessageDto;
import com.mengzhu.live.sdk.business.dto.chat.ChatTextDto;
import com.mengzhu.live.sdk.business.dto.chat.impl.ChatCmdDto;
import com.mengzhu.live.sdk.business.dto.chat.impl.ChatOnlineDto;
import com.mengzhu.live.sdk.business.dto.play.PlayInfoDto;
import com.mengzhu.live.sdk.business.presenter.chat.ChatMessageObserver;
import com.mengzhu.live.sdk.ui.api.MZApiDataListener;
import com.mengzhu.live.sdk.ui.api.MZApiRequest;
import com.mengzhu.live.sdk.ui.chat.MZChatManager;
import com.mengzhu.live.sdk.ui.chat.MZChatMessagerListener;
import com.mengzhu.sdk.R;
import com.mzmedia.IPushClickListener;
import com.mzmedia.fragment.PlayerChatListFragment;
import com.mzmedia.presentation.dto.LiveConfigDto;
import com.mzmedia.utils.ActivityUtils;
import com.mzmedia.utils.String_Utils;
import com.mzmedia.widgets.ChatOnlineView;
import com.mzmedia.widgets.CircleImageView;
import com.mzmedia.widgets.dialog.MessageDialog;
import com.mzmedia.widgets.popupwindow.BeautyPopWindow;
import com.mzmedia.widgets.popupwindow.BitratePopupWindow;
import com.mzmedia.widgets.popupwindow.PersonListPopupWindow;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import me.jessyan.autosize.internal.CancelAdapt;
import tv.mengzhu.core.module.model.dto.BaseDto;
import tv.mengzhu.core.wrap.netwock.Page;
import tv.mengzhu.core.wrap.user.modle.UserDto;
import tv.mengzhu.core.wrap.user.presenter.MyUserInfoPresenter;
import tv.mengzhu.restreaming.push.MZPushManager;
import tv.mengzhu.restreaming.push.PushStreamingListener;
import tv.mengzhu.restreaming.push.StreamLiveCameraView;
import tv.mengzhu.restreaming.ws.StreamAVOption;

public class MZPlugFlowFragement extends Fragment implements View.OnClickListener, CancelAdapt {

    public static final int RB360 = 500 * 1024;
    public static final int RB480 = 800 * 1024;
    public static final int RB720 = 1000 * 1024;

    private StreamLiveCameraView streamLiveCameraView;
    private CircleImageView mPushIvAvatar; //??????
    private CircleImageView mPushOnlinePersonIv1, mPushOnlinePersonIv2, mPushOnlinePersonIv3; //??????????????????
    private TextView mPushTvNickName; //??????
    private TextView mPushTvPopular; //??????
    private TextView mPushTvOnline; //????????????
    private ImageView mPushIvClose; //??????
    private ImageView mPushIvConfig; //??????
    private ImageView mPushIvShare; //??????
    public ImageView mPushIvCut; //????????????
    private RelativeLayout mPushRlLiveOver; //????????????
    private RelativeLayout mPushRlSendChat; //?????????
    private TextView mPushLiveContent; //??????????????????
    private TextView mPushTvHitChat; //?????????????????????
    private TextView mCount; //???????????????
    private ImageView mIvStartClose; //??????????????????
    private ChatOnlineView mPushChatOnlineView; //??????????????????UI
    private ConstraintLayout mConstraintLayout; //???????????????
    private LinearLayout mConfigGroup; //???????????????
    private LinearLayout mConfigGroup_h; //?????????????????????
    private LottieAnimationView audio_anim_view; // ????????????
    private ImageView mIvPushVoice; //??????
    private ImageView mIvPushBitrate; //?????????
    private ImageView mIvPushFlash; //?????????
    private ImageView mIvPushMirror; //??????
    private ImageView mIvPushBeauty; //??????
    private ImageView mIvPushAllChat; //????????????
    private ImageView mIvPushBitrate_h; //?????????
    private ImageView mIvPushVoice_h; //??????
    private ImageView mIvPushFlash_h; //?????????
    private ImageView mIvPushMirror_h; //??????
    private ImageView mIvPushBeauty_h; //??????
    private ImageView mIvPushAllChat_h; //????????????
    private LinearLayout mPushKborTimeLayout; //kb?????????????????????
    private LinearLayout mPushOnlineCount; //??????????????????
    private TextView mTvKb; //????????????
    private TextView mTvLiveTime; // ????????????
    private View mViewToolbar;
    private boolean isPushConfig;
    private boolean isBackStage; //??????????????????
    private String mPushVideoUrl; //????????????
    private UserDto mPushUserDto; //????????????
    private MZPushManager mzPushManager;
    private StreamAVOption streamAVOption;
    private String ticketId, pushUrl;
    private PlayerChatListFragment mChatFragment;
    private IPushClickListener mIPushClickListener;
    private MZApiRequest mzApiRequestOnline; //??????????????????
    private MZApiRequest mzApiRequestStopLive; //??????????????????
    private MZApiRequest mzApiRequestAllChat; //????????????
    private MZApiRequest mzApiRequestBanChat; //????????????

    private PlayInfoDto mPlayInfoDto;
    private LiveConfigDto mLiveConfigDto;
    private List<MZOnlineUserListDto> personAvatars = new ArrayList<>(); //???????????????
    private DisplayImageOptions avatarOptions;
    private Activity mActivity;
    private FragmentTransaction fragmentTransaction;
    private boolean isMirror, isFlash, isVoice = true, isAllChat;
    private int screen;

    private boolean isAudioPush = false;
    private int bitrateValue;
    private float beautyValue = 0.2f; //????????????
    private BitratePopupWindow bitratePopupWindow;
    private BeautyPopWindow beautyPopWindow;
    MessageDialog messageDialog;

    public static MZPlugFlowFragement newInstance(String pushUrl, String ticket_id, int screen, PlayInfoDto dto, LiveConfigDto liveConfigDto, boolean isAudioPush) {

        Bundle args = new Bundle();
        args.putString("push_url", pushUrl);
        args.putString("ticketId", ticket_id);
        args.putInt("screen", screen);
        args.putSerializable("playinfoDto", dto);
        args.putSerializable("liveConfig", liveConfigDto);
        args.putBoolean("isAudioPush", isAudioPush);
        MZPlugFlowFragement fragment = new MZPlugFlowFragement();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pushUrl = getArguments().getString("push_url");
        ticketId = getArguments().getString("ticketId");
        screen = getArguments().getInt("screen");
        isAudioPush = getArguments().getBoolean("isAudioPush", false);
        mPlayInfoDto = (PlayInfoDto) getArguments().getSerializable("playinfoDto");
        mLiveConfigDto = (LiveConfigDto) getArguments().getSerializable("liveConfig");

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.mz_fragment_plug_flow, container, false);
        streamLiveCameraView = rootView.findViewById(R.id.push_stream_previewView);
        mPushTvOnline = rootView.findViewById(R.id.push_tv_playerfragment_person);
        mPushIvClose = rootView.findViewById(R.id.push_iv_playerfragment_close);
        mPushIvAvatar = rootView.findViewById(R.id.push_civ_playerfragment_avatar);
        mPushOnlinePersonIv1 = rootView.findViewById(R.id.push_civ_activity_live_online_person1);
        mPushOnlinePersonIv2 = rootView.findViewById(R.id.push_civ_activity_live_online_person2);
        mPushOnlinePersonIv3 = rootView.findViewById(R.id.push_civ_activity_live_online_person3);
        mPushTvNickName = rootView.findViewById(R.id.push_tv_playerfragment_nickname);
        mPushTvPopular = rootView.findViewById(R.id.push_tv_playerfragment_popularity);
        mPushIvConfig = rootView.findViewById(R.id.push_iv_playerfragment_config);
        mPushIvShare = rootView.findViewById(R.id.push_iv_playerfragment_share);
        mPushIvCut = rootView.findViewById(R.id.push_iv_playerfragment_cut);
        mPushRlSendChat = rootView.findViewById(R.id.push_rl_playerfragment_send_chat);
        mPushRlLiveOver = rootView.findViewById(R.id.push_rl_activity_broadcast_live_over);
        mPushTvHitChat = rootView.findViewById(R.id.push_tv_playerfragment_chat);
        mPushLiveContent = rootView.findViewById(R.id.push_tv_activity_broadcast_live_over);
        mConstraintLayout = rootView.findViewById(R.id.layout_activity_broadcast_count);
        mCount = rootView.findViewById(R.id.tv_activity_broadcast_count);
        mIvStartClose = rootView.findViewById(R.id.iv_activity_live_broadcast_close_out);
        mPushChatOnlineView = rootView.findViewById(R.id.push_player_chat_list_online_view);
        mConfigGroup = rootView.findViewById(R.id.push_layout_live_config_group);
        mIvPushVoice = rootView.findViewById(R.id.push_iv_playerfragment_voice);
        mIvPushBitrate = rootView.findViewById(R.id.push_iv_playerfragment_bitrate);
        mIvPushFlash = rootView.findViewById(R.id.push_iv_playerfragment_flash);
        mIvPushMirror = rootView.findViewById(R.id.push_iv_playerfragment_mirror);
        mIvPushBeauty = rootView.findViewById(R.id.push_iv_playerfragment_beauty);
        mIvPushAllChat = rootView.findViewById(R.id.push_iv_playerfragment_allchat);
        mIvPushBitrate_h = rootView.findViewById(R.id.push_iv_playerfragment_bitrate_h);
        mConfigGroup_h = rootView.findViewById(R.id.push_layout_live_config_group_h);
        mIvPushVoice_h = rootView.findViewById(R.id.push_iv_playerfragment_voice_h);
        mIvPushFlash_h = rootView.findViewById(R.id.push_iv_playerfragment_flash_h);
        mIvPushMirror_h = rootView.findViewById(R.id.push_iv_playerfragment_mirror_h);
        mIvPushBeauty_h = rootView.findViewById(R.id.push_iv_playerfragment_beauty_h);
        mIvPushAllChat_h = rootView.findViewById(R.id.push_iv_playerfragment_allchat_h);
        mPushKborTimeLayout = rootView.findViewById(R.id.push_layout_live_kb_time_group);
        mPushOnlineCount = rootView.findViewById(R.id.push_ll_activity_live_broadcast_top_group);
        mViewToolbar = rootView.findViewById(R.id.push_v_live_toolbar);
        mTvKb = rootView.findViewById(R.id.tv_plug_fragment_kb);
        mTvLiveTime = rootView.findViewById(R.id.tv_plug_fragment_time);
        audio_anim_view = rootView.findViewById(R.id.audio_anim_view);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        streamAVOption = new StreamAVOption();
        streamAVOption.streamUrl = pushUrl;
        mzPushManager = new MZPushManager(getActivity(), streamAVOption);
        mzPushManager.initPushLive(streamLiveCameraView, isAudioPush);
        mzApiRequestOnline = new MZApiRequest();
        mzApiRequestStopLive = new MZApiRequest();
        mzApiRequestBanChat = new MZApiRequest();
        mzApiRequestAllChat = new MZApiRequest();
        if (screen == 2) {
            mPushIvConfig.setVisibility(View.GONE);
            mConfigGroup_h.setVisibility(View.VISIBLE);
            mViewToolbar.setVisibility(View.GONE);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.LEFT_OF, R.id.push_ll_activity_live_broadcast_top_group);
            params.topMargin = 12;
            mPushKborTimeLayout.setLayoutParams(params);
            mPushKborTimeLayout.invalidate();
        }

        if (isAudioPush) {
            audio_anim_view.setVisibility(View.VISIBLE);
            mIvPushFlash.setVisibility(View.GONE); //?????????
            mIvPushMirror.setVisibility(View.GONE); //??????
            mIvPushBeauty.setVisibility(View.GONE); //??????
            mPushIvCut.setVisibility(View.INVISIBLE);
            mIvPushBitrate.setVisibility(View.GONE); //?????????
        }

        initData();
        initListener();
        loadData();

        //??????????????????????????????
        avatarOptions = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.icon_default_avatar)
                .showImageForEmptyUri(R.mipmap.icon_default_avatar)
                .showImageOnFail(R.mipmap.icon_default_avatar)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .build();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isBackStage) {
            streamLiveCameraView.resetCamera();
            if (!mzPushManager.isStreaming()) {
                mzPushManager.startStreaming();
            }

        }

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN && i == KeyEvent.KEYCODE_BACK) {
                    if (!messageDialog.isShowing()) {
                        messageDialog.show();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        isBackStage = true;
        if (mzPushManager.isStreaming()) {
            mzPushManager.stopStreaming();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
//        mzPushManager.rePrepare();
//        mzPushManager.changeFilter(beautyValue);
    }

    private void initData() {
        //?????????????????????
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(getActivity()));
        mChatFragment = new PlayerChatListFragment();
        fragmentTransaction = getChildFragmentManager().beginTransaction();
        int times;
        if (screen == 2) {
            times = mLiveConfigDto.getTime() + 1;
        } else {
            times = mLiveConfigDto.getTime();
        }
        StartLiveCountDownTimer startLiveCountDownTimer = new StartLiveCountDownTimer(times * 1000, 1000);
        startLiveCountDownTimer.start();
        //????????????fragment
        Bundle bundle = new Bundle();
        bundle.putBoolean(PlayerChatListFragment.PLAY_TYPE_KEY, true);
        bundle.putSerializable(PlayerChatListFragment.PLAY_INFO_KEY, mPlayInfoDto);
        mChatFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.push_layout_activity_live_broadcast_chat, mChatFragment).commitAllowingStateLoss();
        mPushTvNickName.setText(MyUserInfoPresenter.getInstance().getUserInfo().getNickname());
        ImageLoader.getInstance().displayImage(MyUserInfoPresenter.getInstance().getUserInfo().getAvatar() + String_Utils.getPictureSizeAvatar(), mPushIvAvatar);

    }

    private void initListener() {
        messageDialog = new MessageDialog(getActivity());
        messageDialog.setTitle("????????????");
        messageDialog.setContentMessage("??????????????????");
        messageDialog.setMessageDialogCallBack(new MessageDialog.OnMessageDialogCallBack() {
            @Override
            public void onClick(boolean isConfirm) {
                if (!isConfirm) {
                    if (mzPushManager.isStreaming()) {
                        isBackStage = false;
                        mzPushManager.stopStreaming();
                    }
                    //if (mIPushClickListener != null) {
                        //mIPushClickListener.onStopLive();
                    //}
                    //????????????????????????
                    mzApiRequestStopLive.startData(MZApiRequest.API_TYPE_LIVE_STOP, ticketId);
                }
            }
        });
        mPushIvClose.setOnClickListener(this);
        mIvStartClose.setOnClickListener(this);
        mPushRlSendChat.setOnClickListener(this);
        mPushIvCut.setOnClickListener(this);
        mPushIvConfig.setOnClickListener(this);
        mIvPushBitrate.setOnClickListener(this);
        mIvPushVoice.setOnClickListener(this);
        mIvPushFlash.setOnClickListener(this);
        mIvPushMirror.setOnClickListener(this);
        mIvPushBeauty.setOnClickListener(this);
        mIvPushAllChat.setOnClickListener(this);
        mIvPushVoice_h.setOnClickListener(this);
        mIvPushFlash_h.setOnClickListener(this);
        mIvPushMirror_h.setOnClickListener(this);
        mIvPushBeauty_h.setOnClickListener(this);
        mIvPushAllChat_h.setOnClickListener(this);
        mIvPushBitrate_h.setOnClickListener(this);
        mPushIvShare.setOnClickListener(this);
        mPushTvOnline.setOnClickListener(this);
        mPushIvAvatar.setOnClickListener(this);
        mPushOnlinePersonIv1.setOnClickListener(this);
        mPushOnlinePersonIv2.setOnClickListener(this);
        mPushOnlinePersonIv3.setOnClickListener(this);
        //??????????????????
        mzApiRequestStopLive.setResultListener(new MZApiDataListener() {
            @Override
            public void dataResult(String s, Object o, Page page, int status) {
                if (mIPushClickListener != null) {
                    mIPushClickListener.onStopLive();
                }
            }

            @Override
            public void errorResult(String s, int i, String s1) {
            }
        });
        //????????????????????????
        mzApiRequestOnline.setResultListener(new MZApiDataListener() {
            @Override
            public void dataResult(String apiType, Object dto, Page page, int status) {
                personAvatars.clear();
                boolean isContainsMe = false;
                List<MZOnlineUserListDto> mzOnlineUserListDto = (List<MZOnlineUserListDto>) dto;
                for (int i = 0; i < mzOnlineUserListDto.size(); i++) {
                    if (Long.parseLong(mzOnlineUserListDto.get(i).getUid()) < Long.parseLong("5000000000")) {
                        personAvatars.add(mzOnlineUserListDto.get(i));
                    }
                    if (mzOnlineUserListDto.get(i).getUid().equals(mPlayInfoDto.getChat_uid())) {
                        isContainsMe = true;
                    }
                }
                if (!isContainsMe) {
                    MZOnlineUserListDto userListDto = new MZOnlineUserListDto();
                    UserDto userInfoDto = MyUserInfoPresenter.getInstance().getUserInfo();
                    userListDto.setUid(mPlayInfoDto.getChat_uid());
                    userListDto.setAvatar(userInfoDto.getAvatar());
                    userListDto.setNickname(userInfoDto.getNickname());
                    personAvatars.add(userListDto);
                }
                initOnlineAvatar();
            }

            @Override
            public void errorResult(String apiType, int code, String msg) {
                Log.d("gm", "errorResult: API_TYPE_ONLINE_USER_LIST");
            }
        });
        //??????????????????
        mzApiRequestAllChat.setResultListener(new MZApiDataListener() {
            @Override
            public void dataResult(String s, Object o, Page page, int status) {
                mIvPushAllChat.setImageResource(isAllChat ? R.mipmap.mz_icon_allchat_on : R.mipmap.mz_icon_allchat_default);
                mIvPushAllChat_h.setImageResource(isAllChat ? R.mipmap.mz_icon_allchat_on : R.mipmap.mz_icon_allchat_default);
            }

            @Override
            public void errorResult(String s, int i, String s1) {
                Log.e("wzh", s1 + "ssss" + i);
            }
        });
        //??????????????????
        mzApiRequestBanChat.setResultListener(new MZApiDataListener() {
            @Override
            public void dataResult(String s, Object o, Page page, int status) {
                if (mIPushClickListener != null) {
                    mIPushClickListener.onALLBanChat();
                }
            }

            @Override
            public void errorResult(String s, int i, String s1) {

            }
        });
        //?????????????????????
        MZChatManager.getInstance(mActivity).registerListener(MZPlugFlowFragement.class.getSimpleName(), new MZChatMessager());
        //??????????????????????????????
        mChatFragment.setOnChatAvatarClickListener(new ChatAvatarClick());
        mzPushManager.setStreamingListener(new MZPushStreamListener());
    }

    private void loadData() {

        mzApiRequestOnline.createRequest(getActivity(), MZApiRequest.API_TYPE_ONLINE_USER_LIST);
        mzApiRequestStopLive.createRequest(getActivity(), MZApiRequest.API_TYPE_LIVE_STOP);
        mzApiRequestBanChat.createRequest(getActivity(), MZApiRequest.API_TYPE_ROOM_FORBIDDEN);
        mzApiRequestAllChat.createRequest(getActivity(), MZApiRequest.API_TYPE_ROOM_ALLOWCHATALL);
        //??????????????????api
        mzApiRequestOnline.startData(MZApiRequest.API_TYPE_ONLINE_USER_LIST, true, ticketId);
    }

    private class MZPushStreamListener implements PushStreamingListener {
        //??????
        @Override
        public void onScreenShotResult(Bitmap bitmap) {

        }

        //????????????
        @Override
        public void onOpenConnectionResult(int result) {
            if (!isBackStage) { //???????????????????????????????????????????????????????????????
                startTime();
                setConfig();
                audio_anim_view.loop(true);
                audio_anim_view.playAnimation();
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isAllChat = mLiveConfigDto.getAllBanChat() == 0;
                    mzApiRequestAllChat.startData(MZApiRequest.API_TYPE_ROOM_ALLOWCHATALL, mPlayInfoDto.getTicket_id(), mPlayInfoDto.getChannel_id(), isAllChat ? 0 : 1);
                }
            }, 1000);
            //Toast.makeText(mActivity, "start=" + result, Toast.LENGTH_SHORT).show();
        }

        //????????????
        @Override
        public void onWriteError(int result) {
            Log.e("mengzhu", "onWriteError:    " + result);
            //Toast.makeText(mActivity, "error=" + result, Toast.LENGTH_SHORT).show();
        }

        //????????????
        @Override
        public void onCloseConnectionResult(int result) {
            if (!isBackStage) {
                audio_anim_view.cancelAnimation();
            }
            //Toast.makeText(mActivity, "close=" + result, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onReConnecting() {
            Log.e("mengzhu", "onReConnecting: " + "????????????");
        }

        @Override
        public void onReConnectFail() {
            Log.e("mengzhu", "onReConnectFail: " + "????????????");
            MessageDialog messageDialog = new MessageDialog(getActivity());
            messageDialog.setTitle("????????????");
            messageDialog.setContentMessage("??????");
            messageDialog.setMessageDialogCallBack(new MessageDialog.OnMessageDialogCallBack() {
                @Override
                public void onClick(boolean isConfirm) {
                    if (!isConfirm && mzPushManager != null) {
                        mzPushManager.stopStreaming();
                        mzPushManager.startStreaming();
                    }
                }
            });
            messageDialog.show();
            //Toast.makeText(mActivity, "????????????", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onReConnectSuccess() {
            Log.e("mengzhu", "onReConnectSuccess: " + "????????????");
            //Toast.makeText(mActivity, "????????????", Toast.LENGTH_SHORT).show();
        }
    }

    public void setPushClickListener(IPushClickListener listener) {
        mIPushClickListener = listener;
    }

    /**
     * ?????????????????????
     */
    private class StartLiveCountDownTimer extends CountDownTimer {

        public StartLiveCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            int mill = ((int) ((millisUntilFinished + 100) / 1000));
            if (mill - 1 >= 0) {
                mCount.setText(mill + "");
            }
        }

        @Override
        public void onFinish() {
            if (!mzPushManager.isStreaming()) {
                mzPushManager.startStreaming();
            }
            mConstraintLayout.setVisibility(View.GONE);
        }
    }

    @SuppressLint("RtlHardcoded")
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.push_iv_playerfragment_close) {
            if (!messageDialog.isShowing())
                messageDialog.show();
        }
        if (v.getId() == R.id.iv_activity_live_broadcast_close_out) {
            if (!messageDialog.isShowing())
                messageDialog.show();
//            if (mIPushClickListener != null) {
//                mIPushClickListener.onStopLive();
//            }
        }
        if (v.getId() == R.id.push_rl_playerfragment_send_chat) {
            ActivityUtils.startLandscapeTransActivity(getActivity());
        }
        if (v.getId() == R.id.push_iv_playerfragment_cut) {
            mzPushManager.swapCamera();
            isFlash = false;
            mIvPushFlash.setImageResource(isFlash ? R.mipmap.mz_icon_flash_on : R.mipmap.mz_icon_flash_default);
            mIvPushFlash_h.setImageResource(isFlash ? R.mipmap.mz_icon_flash_on : R.mipmap.mz_icon_flash_default);
        }
        if (v.getId() == R.id.push_iv_playerfragment_config) {
            isPushConfig = !isPushConfig;
            mPushIvConfig.setImageResource(isPushConfig ? R.mipmap.mz_icon_config_off : R.mipmap.mz_icon_config);
            mConfigGroup.setVisibility(mConfigGroup.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        }
        if (v.getId() == R.id.push_iv_playerfragment_allchat
                || v.getId() == R.id.push_iv_playerfragment_allchat_h) {
            isAllChat = !isAllChat;
            mzApiRequestAllChat.startData(MZApiRequest.API_TYPE_ROOM_ALLOWCHATALL, mPlayInfoDto.getTicket_id(), mPlayInfoDto.getChannel_id(), isAllChat ? 0 : 1);
        }
        if (v.getId() == R.id.push_iv_playerfragment_voice
                || v.getId() == R.id.push_iv_playerfragment_voice_h) {
            isVoice = !isVoice;
            mIvPushVoice.setImageResource(isVoice ? R.mipmap.mz_icon_voice_on : R.mipmap.mz_icon_voice_default);
            mIvPushVoice_h.setImageResource(isVoice ? R.mipmap.mz_icon_voice_on : R.mipmap.mz_icon_voice_default);
            if (mzPushManager != null) {
                mzPushManager.StartorStopAudio(isVoice);
            }
        }
        if (v.getId() == R.id.push_iv_playerfragment_flash
                || v.getId() == R.id.push_iv_playerfragment_flash_h) {
            isFlash = !isFlash;
            mIvPushFlash.setImageResource(isFlash ? R.mipmap.mz_icon_flash_on : R.mipmap.mz_icon_flash_default);
            mIvPushFlash_h.setImageResource(isFlash ? R.mipmap.mz_icon_flash_on : R.mipmap.mz_icon_flash_default);
            if (mzPushManager != null) {
                mzPushManager.toggleFlashLight();
            }
        }
        if (v.getId() == R.id.push_iv_playerfragment_mirror
                || v.getId() == R.id.push_iv_playerfragment_mirror_h) {
            isMirror = !isMirror;
            mIvPushMirror.setImageResource(isMirror ? R.mipmap.mz_icon_mirror_on : R.mipmap.mz_icon_mirror_default);
            mIvPushMirror_h.setImageResource(isMirror ? R.mipmap.mz_icon_mirror_on : R.mipmap.mz_icon_mirror_default);
            if (isMirror) {
                if (mzPushManager != null) {
                    mzPushManager.setMirror(true, true, true);
                }
            } else {
                if (mzPushManager != null) {
                    mzPushManager.setMirror(true, false, false);
                }
            }
        }
        if (v.getId() == R.id.push_iv_playerfragment_beauty) {
            if (beautyPopWindow == null) {
                beautyPopWindow = new BeautyPopWindow(getContext(), beautyValue);
                beautyPopWindow.setOnbeautySelectListener(new BeautyPopWindow.OnbeautySelectListener() {
                    @Override
                    public void onSelectbeauty(float beauty) {
                        beautyValue = beauty;
                        if (mzPushManager != null) {
                            mzPushManager.changeFilter(beautyValue);
                        }
                        mIvPushBeauty.setImageResource(beautyValue > 0 ? R.mipmap.mz_icon_beauty_on : R.mipmap.mz_icon_beauty_default);
                    }
                });
            }
            if (beautyPopWindow.isShowing()) {
                return;
            }
            beautyPopWindow.setBeautyValue(beautyValue);
            beautyPopWindow.showAtCustomerLocation(mIvPushBeauty, Gravity.NO_GRAVITY, 0, 0, 0);
        }

        if (v.getId() == R.id.push_iv_playerfragment_beauty_h) {
            if (beautyPopWindow == null) {
                beautyPopWindow = new BeautyPopWindow(getContext(), beautyValue);
                beautyPopWindow.setOnbeautySelectListener(new BeautyPopWindow.OnbeautySelectListener() {
                    @Override
                    public void onSelectbeauty(float beauty) {
                        beautyValue = beauty;
                        if (mzPushManager != null) {
                            mzPushManager.changeFilter(beautyValue);
                        }
                        mIvPushBeauty_h.setImageResource(beautyValue > 0 ? R.mipmap.mz_icon_beauty_on : R.mipmap.mz_icon_beauty_default);
                    }
                });
            }
            if (beautyPopWindow.isShowing()) {
                return;
            }
            beautyPopWindow.setBeautyValue(beautyValue);
            beautyPopWindow.showAtCustomerLocation(mIvPushBeauty_h, Gravity.NO_GRAVITY, 2, 0, 0);
        }

        if (v.getId() == R.id.push_iv_playerfragment_share) {
            if (mIPushClickListener != null) {
                mIPushClickListener.onShare(mPlayInfoDto);
            }
        }
        if (v.getId() == R.id.push_tv_playerfragment_person || v.getId() == R.id.push_civ_activity_live_online_person1
                || v.getId() == R.id.push_civ_activity_live_online_person2 || v.getId() == R.id.push_civ_activity_live_online_person3) {
            PersonListPopupWindow personListPopupWindow = new PersonListPopupWindow(mActivity, mPlayInfoDto);
            personListPopupWindow.showAtLocation(mPushLiveContent, Gravity.CENTER, 0, 0);
            personListPopupWindow.setOnPersonListClickCallBack(new PersonListPopupWindow.PersonListClickedCallBack() {
                @Override
                public void onPersonItemClicked(MZOnlineUserListDto onlineUserListDto) {
                    if (mIPushClickListener != null) {
                        mIPushClickListener.onOnlineNum(onlineUserListDto);
                    }
                }
            });
        }
        if (v.getId() == R.id.push_civ_playerfragment_avatar) {
            if (mIPushClickListener != null) {
                mIPushClickListener.onLiveAvatar();
            }
        }
        if (v.getId() == R.id.push_iv_playerfragment_bitrate) {
            if (bitratePopupWindow == null) {
                bitratePopupWindow = new BitratePopupWindow(getContext(), bitrateValue);
                bitratePopupWindow.setOnBitrateSelectListener(new BitratePopupWindow.OnBitrateSelectListener() {
                    @Override
                    public void onSelectBitrate(int bitrate) {
                        bitrateValue = bitrate;
                        setBitrate();
                    }
                });
            }
            if (bitratePopupWindow.isShowing()) {
                return;
            }
            bitratePopupWindow.setBitrateValue(bitrateValue);
            bitratePopupWindow.showAtCustomerLocation(mIvPushBitrate, Gravity.NO_GRAVITY, 0, -10, 0);
        }
        if (v.getId() == R.id.push_iv_playerfragment_bitrate_h) {
            if (bitratePopupWindow == null) {
                bitratePopupWindow = new BitratePopupWindow(getContext(), bitrateValue);
                bitratePopupWindow.setOnBitrateSelectListener(new BitratePopupWindow.OnBitrateSelectListener() {
                    @Override
                    public void onSelectBitrate(int bitrate) {
                        bitrateValue = bitrate;
                        setBitrate();
                    }
                });
            }
            if (bitratePopupWindow.isShowing()) {
                return;
            }
            bitratePopupWindow.setBitrateValue(bitrateValue);
            bitratePopupWindow.showAtCustomerLocation(mIvPushBitrate_h, Gravity.NO_GRAVITY, 2, 0, 0);
        }
    }

    /**
     * ??????????????????????????????
     */
    private class ChatAvatarClick implements PlayerChatListFragment.OnChatAvatarClickListener {
        @Override
        public void onChatAvatarClick(ChatTextDto dto) {
            if (null != mIPushClickListener) {
                mIPushClickListener.onChatAvatar(dto);
            }
            setBanChat(mPlayInfoDto.getId(), mPlayInfoDto.getTicket_id(), "1");
        }
    }

    private String mTotalPerson;
    private long mCountTime;
    private Timer timer;

    /**
     * ????????????
     */
    private void startTime() {
        if (timer != null) {
            return;
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mCountTime += 1000;
                        mTvKb.setText((mzPushManager.getAVSpeed() / 1024) * 8 + "Kb/s");
                        mTvLiveTime.setText(String_Utils.converLongTimeToStr(mCountTime));
                    }
                });
            }
        }, 0, 1000);
    }

    /**
     * ??????????????????
     */
    int current = 0;

    private class MZChatMessager implements MZChatMessagerListener {
        @Override
        public void dataResult(Object o, Page page, int i) {
        }

        @Override
        public void errorResult(int i, String s) {
        }

        @Override
        public void monitorInformResult(String s, Object o) {
            ChatMessageDto mChatMessage = (ChatMessageDto) o;
            ChatTextDto mChatText = mChatMessage.getText();
            BaseDto mBase = mChatText.getBaseDto();
            switch (s) {
                case ChatMessageObserver.ONLINE_TYPE: {//???????????????
                    ChatOnlineDto mChatOnline = (ChatOnlineDto) mBase;
                    mTotalPerson = mPushTvOnline.getText().toString();
                    mPushChatOnlineView.startOnline(mActivity, mChatMessage);
                    long popular = Integer.parseInt(mPlayInfoDto.getPopular()) + mChatOnline.getLast_pv();
                    mPlayInfoDto.setPopular(popular + "");
                    //????????????
                    mPushTvPopular.setText("??????" + String_Utils.convert2W0_0(popular + ""));
                    if (String_Utils.isNumeric(mTotalPerson) && mChatOnline.getIs_hidden() != 1 && !personAvatars.isEmpty()) {
                        boolean isContainsMe = false;
                        for (int i = 0; i < personAvatars.size(); i++) {
                            if (personAvatars.get(i).getUid().equals(mChatMessage.getText().getUser_id())) {
                                isContainsMe = true;
                            }
                        }
                        if (isContainsMe) {
                            break;
                        }
                        if (Long.parseLong(mChatMessage.getText().getUser_id()) < Long.parseLong("5000000000")) {
                            MZOnlineUserListDto dto = new MZOnlineUserListDto();
                            dto.setUid(mChatMessage.getText().getUser_id());
                            dto.setAvatar(mChatText.getAvatar());
                            dto.setIs_gag(mChatOnline.getIs_gag());
                            dto.setRole_name(mChatOnline.getRole());
                            dto.setNickname(mChatText.getUser_name());
                            personAvatars.add(dto);
                            initOnlineAvatar();
                            int current = Integer.valueOf(mTotalPerson) + 1;
                            if (current < 0) {
                                current = 0;
                            }
                            mPushTvOnline.setText(String_Utils.convert2W0_0(current + ""));
                        }
                    }
                    break;
                }
                case ChatMessageObserver.OFFLINE_TYPE:
                    mTotalPerson = mPushTvOnline.getText().toString();
                    if (String_Utils.isNumeric(mTotalPerson) && !personAvatars.isEmpty()) {
                        int current = Integer.valueOf(mTotalPerson) - 1;
                        if (current < 0) {
                            current = 0;
                        }
                        mPushTvOnline.setText(String_Utils.convert2W0_0(current + ""));

                        Iterator<MZOnlineUserListDto> iterator = personAvatars.iterator();
                        while (iterator.hasNext()) {
                            MZOnlineUserListDto userListDto = iterator.next();
                            if (userListDto.getUid().equals(mChatText.getUser_id()) && !mChatText.getUser_id().equals(mPlayInfoDto.getChat_uid())) {
                                iterator.remove();
                            }
                        }
                        initOnlineAvatar();
                    }
                    if (personAvatars.size() < 4) {
                        mzApiRequestOnline.startData(MZApiRequest.API_TYPE_ONLINE_USER_LIST, true, ticketId);
                    }
                    break;
                case ChatMessageObserver.CMD_TYPE:
                    ChatCmdDto mChatCmd = (ChatCmdDto) mBase;
                    switch (mChatCmd.getType()) {
                        case ChatCmdDto.DISABLE_CHAT:
//                            //????????????
//                            if (mPlayInfoDto.getChat_uid().equals(((ChatCmdDto) mBase).getUser_id())) {
//                                mPlayInfoDto.setUser_status(3);
//                                initBanChat(true);
//                            }
                            break;
                        case ChatCmdDto.PERMIT_CHAT:
                            //??????????????????
//                            if (mPlayInfoDto.getChat_uid().equals(((ChatCmdDto) mBase).getUser_id())) {
//                                mPlayInfoDto.setUser_status(1);
//                                initBanChat(false);
//                            }
                            break;
                        case ChatCmdDto.LIVE_OVER:
                            //????????????
//                            mRlLiveOver.setVisibility(View.VISIBLE);
//                            mLiveContent.setText("?????????????????????\n????????????????????????");
//                            mzVideoView.pause();
                            break;
                        case "*liveEnd":
                            //????????????
//                            mRlLiveOver.setVisibility(View.VISIBLE);
//                            mLiveContent.setText("???????????????");
//                            mzVideoView.stopPlayback();
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }
        }

        @Override
        public void monitorInformError(String s, int i, String s1) {
        }

        @Override
        public void monitorInformResult(String type, Object obj, Object extend) {

        }
    }

    /**
     * ????????????????????????????????????
     */
    private void initOnlineAvatar() {
        if (personAvatars.size() >= 3) {
            mPushOnlinePersonIv1.setVisibility(View.VISIBLE);
            mPushOnlinePersonIv2.setVisibility(View.VISIBLE);
            mPushOnlinePersonIv3.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(personAvatars.get(personAvatars.size() - 1).getAvatar(), mPushOnlinePersonIv1, avatarOptions);
            ImageLoader.getInstance().displayImage(personAvatars.get(personAvatars.size() - 2).getAvatar(), mPushOnlinePersonIv2, avatarOptions);
            ImageLoader.getInstance().displayImage(personAvatars.get(personAvatars.size() - 3).getAvatar(), mPushOnlinePersonIv3, avatarOptions);
        } else if (personAvatars.size() == 2) {
            mPushOnlinePersonIv1.setVisibility(View.VISIBLE);
            mPushOnlinePersonIv2.setVisibility(View.VISIBLE);
            mPushOnlinePersonIv3.setVisibility(View.GONE);
            ImageLoader.getInstance().displayImage(personAvatars.get(personAvatars.size() - 1).getAvatar(), mPushOnlinePersonIv1, avatarOptions);
            ImageLoader.getInstance().displayImage(personAvatars.get(personAvatars.size() - 2).getAvatar(), mPushOnlinePersonIv2, avatarOptions);

        } else if (personAvatars.size() == 1) {
            mPushOnlinePersonIv1.setVisibility(View.VISIBLE);
            mPushOnlinePersonIv2.setVisibility(View.GONE);
            mPushOnlinePersonIv3.setVisibility(View.GONE);
            ImageLoader.getInstance().displayImage(personAvatars.get(personAvatars.size() - 1).getAvatar(), mPushOnlinePersonIv1, avatarOptions);
        }
    }

    /**
     * ????????????
     *
     * @param uId      ??????id
     * @param ticketId ????????????id
     * @param type     0?????? 1??????
     */
    public void setBanChat(String uId, String ticketId, String type) {
        if (mzApiRequestBanChat != null) {
            mzApiRequestBanChat.startData(MZApiRequest.API_TYPE_ROOM_FORBIDDEN, uId, ticketId, type);
        }
    }

    private void setConfig() {
        if (mLiveConfigDto.isCbbeauty()) {
            mIvPushBeauty.setImageResource(beautyValue > 0 ? R.mipmap.mz_icon_beauty_on : R.mipmap.mz_icon_beauty_default);
            mIvPushBeauty_h.setImageResource(beautyValue > 0 ? R.mipmap.mz_icon_beauty_on : R.mipmap.mz_icon_beauty_default);
            mzPushManager.changeFilter(beautyValue);
        }
        if (mLiveConfigDto.isCbAudio()) {
            isVoice = true;
            mIvPushVoice.setImageResource(isVoice ? R.mipmap.mz_icon_voice_on : R.mipmap.mz_icon_voice_default);
            mIvPushVoice_h.setImageResource(isVoice ? R.mipmap.mz_icon_voice_on : R.mipmap.mz_icon_voice_default);
            mzPushManager.StartorStopAudio(isVoice);
        }
        if (mLiveConfigDto.isCblater()) {
            mzPushManager.swapCamera();
        }
        bitrateValue = mLiveConfigDto.getBitrate();
        setBitrate();
        if (mLiveConfigDto.getFps() < 15) {
            mzPushManager.reSetVideoFPS(15);
        } else if (mLiveConfigDto.getFps() > 30) {
            mzPushManager.reSetVideoFPS(30);
        } else {
            mzPushManager.reSetVideoFPS(mLiveConfigDto.getFps());
        }
    }

    public void setBitrate() {
        mzPushManager.reSetVideoBitrate(bitrateValue);
        if (bitrateValue <= RB360) {
            mIvPushBitrate.setImageResource(R.mipmap.mz_icon_biaoqing_select);
            mIvPushBitrate_h.setImageResource(R.mipmap.mz_icon_biaoqing_select);
        } else if (bitrateValue > RB360 && bitrateValue <= RB480) {
            mIvPushBitrate.setImageResource(R.mipmap.mz_icon_gaoqing_select);
            mIvPushBitrate_h.setImageResource(R.mipmap.mz_icon_gaoqing_select);
        } else if (bitrateValue > RB480 && bitrateValue <= RB720) {
            mIvPushBitrate.setImageResource(R.mipmap.mz_icon_chaoqing_select);
            mIvPushBitrate_h.setImageResource(R.mipmap.mz_icon_chaoqing_select);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        if (mzPushManager != null) {
            mzPushManager.destroy();
        }
    }

}
