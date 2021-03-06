package com.mzmedia.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.mengzhu.live.sdk.business.dto.AnchorInfoDto;
import com.mengzhu.live.sdk.business.dto.MZAllSettingDto;
import com.mengzhu.live.sdk.business.dto.MZOnlineUserListDto;
import com.mengzhu.live.sdk.business.dto.ad.MZAdRollAdvertDto;
import com.mengzhu.live.sdk.business.dto.chat.ChatMessageDto;
import com.mengzhu.live.sdk.business.dto.chat.ChatTextDto;
import com.mengzhu.live.sdk.business.dto.chat.RightBean;
import com.mengzhu.live.sdk.business.dto.chat.impl.ChatCmdDto;
import com.mengzhu.live.sdk.business.dto.chat.impl.ChatCompleteDto;
import com.mengzhu.live.sdk.business.dto.chat.impl.ChatMegTxtDto;
import com.mengzhu.live.sdk.business.dto.chat.impl.ChatOnlineDto;
import com.mengzhu.live.sdk.business.dto.play.PlayInfoDto;
import com.mengzhu.live.sdk.business.presenter.chat.ChatMessageObserver;
import com.mengzhu.live.sdk.business.presenter.chat.ChatPresenter;
import com.mengzhu.live.sdk.core.MZSDKInitManager;
import com.mengzhu.live.sdk.core.SDKInitListener;
import com.mengzhu.live.sdk.core.utils.DensityUtil;
import com.mengzhu.live.sdk.ui.api.MZApiDataListener;
import com.mengzhu.live.sdk.ui.api.MZApiRequest;
import com.mengzhu.live.sdk.ui.chat.MZChatManager;
import com.mengzhu.live.sdk.ui.chat.MZChatMessagerListener;
import com.mengzhu.live.sdk.ui.fragment.ViewDocumentFragment;
import com.mengzhu.live.sdk.ui.widgets.ChannelDlnaDialogFragment;
import com.mengzhu.live.sdk.ui.widgets.MZADBannerView;
import com.mengzhu.live.sdk.ui.widgets.popupwindow.SpeedBottomDialogFragment;
import com.mengzhu.sdk.R;
import com.mzmedia.IPlayerClickListener;
import com.mzmedia.adapter.base.WatchTitleBarAdapter;
import com.mzmedia.presentation.dto.MZEvent;
import com.mzmedia.utils.String_Utils;
import com.mzmedia.widgets.CircleImageView;
import com.mzmedia.widgets.DanmakuViewCacheStuffer;
import com.mzmedia.widgets.HackyViewPager;
import com.mzmedia.widgets.magicindicator.MagicIndicator;
import com.mzmedia.widgets.magicindicator.ViewPagerHelper;
import com.mzmedia.widgets.magicindicator.buildins.commonnavigator.CommonNavigator;
import com.mzmedia.widgets.popupwindow.PersonListPopupWindow;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import tv.mengzhu.core.frame.coreutils.PreferencesUtils;
import tv.mengzhu.core.module.model.dto.BaseDto;
import tv.mengzhu.core.wrap.netwock.Page;
import tv.mengzhu.core.wrap.user.modle.UserDto;
import tv.mengzhu.core.wrap.user.presenter.MyUserInfoPresenter;
import tv.mengzhu.dlna.DLNAController;
import tv.mengzhu.dlna.entity.RemoteItem;
import tv.mengzhu.sdk.module.IMZPlayerManager;
import tv.mengzhu.sdk.module.MZPlayerManager;
import tv.mengzhu.sdk.module.MZPlayerView;
import tv.mengzhu.sdk.module.PlayerEventListener;
import tv.mengzhu.sdk.module.player.PlayerController;

public class HalfPlayerFragment extends Fragment implements View.OnClickListener, MZApiDataListener {

    private static final String USER_INFO = "userDto";
    public static final String APP_ID = "appid";
    public static final String AVATAR = "avatar";
    public static final String NICKNAME = "nickName";
    public static final String UNIQUE_ID = "unique_id";
    public static final String TICKET_ID = "ticket_id";
    public static final String LIVE_ID = "live_id";
    public static final String LIVE_CAN_PLAY = "live_can_play";
    /**
     * ????????????????????????
     */
    private boolean isCanPlay = true;

    private static String CLASSNAME = HalfPlayerFragment.class.getSimpleName();
    private static String GIFT_KEY = "gift";
    private static String KICK_OUT_KEY = "kick_out";
    private static String NEW_REPLY = "new_reply";
    private MZPlayerManager mManager;
    private FrameLayout mVideoFrame;
    private MZPlayerView mzPlayerView;
    private AppBarLayout mAppBarLayout;
    private CircleImageView mIvAvatar; //??????
    private CircleImageView mOnlinePersonIv1, mOnlinePersonIv2, mOnlinePersonIv3; //??????????????????
    private TextView mTvNickName; //??????
    private TextView mTvPopular; //??????
    private TextView mTvLiveType;
    private TextView mTVLiveTypeText; //????????????
    public TextView mTvAttention; //??????
    private TextView mTvOnline; //????????????
    private ImageView mIvClose; //??????
    private RelativeLayout mRlLiveOver; //????????????
    private TextView mLiveContent; //??????????????????

    private String mVideoUrl; //????????????
    private String mUid;
    private LinearLayout mBottomLayout;
    private MagicIndicator mMagicIndicator;
    private HackyViewPager mVpContainer;
    private Activity mActivity;
    private DisplayImageOptions avatarOptions;
    private MZApiRequest mzApiRequest;
    private MZApiRequest mzApiRequestOnline;
    private MZApiRequest mzApiRequestAnchorInfo;
    private MZApiRequest mzGetSettingRequest;
    private String ticketId; //??????id
    private String mLiveId;  //??????id
    private String mTotalPerson;
    private List<MZOnlineUserListDto> personAvatars = new ArrayList<>();
    private PlayInfoDto mPlayInfoDto;
    private AnchorInfoDto anchorInfoDto; //????????????

    private WatchTitleBarAdapter mTitleBarAdapter;
    private TabPagerAdapter mAdapter;
    private int mCurrentIndexTab;

    private WatchBottomFragment mWatchBottomFragment;
    private ViewDocumentFragment mViewDocumentFragment;
    private BottomQAFragment mBottomQAFragment;
    private PlayDescriptionFragment mDescriptionFragment;

    private boolean isLandSpace; //???????????????

    private List<String> mTabTitleList = new ArrayList<>(); //tab??????????????????
    private List<Fragment> mTabFragmentList = new ArrayList<>(); //tabfragment????????????

    private int liveStatus; //????????????

    private int mSpeedIndex = 1; //???????????????
    private float[] speeds = new float[]{0.75f, 1, 1.25f, 1.5f, 2};
    SpeedBottomDialogFragment speedDialog;

    private DLNAController controller; //????????????

    private List<HashMap<String, Fragment>> bottomTabs = new ArrayList<>();

    private MZADBannerView mzADBannerView; //????????????banner

    public static HalfPlayerFragment newInstance(String Appid, String avatar, String nickName, String unique_id,
                                                 String ticketId, String liveId, boolean isCanPlay) {
        HalfPlayerFragment fragment = new HalfPlayerFragment();
        Bundle args = new Bundle();
        args.putSerializable(APP_ID, Appid);
        args.putSerializable(AVATAR, avatar);
        args.putSerializable(NICKNAME, nickName);
        args.putSerializable(UNIQUE_ID, unique_id);
        args.putString(TICKET_ID, ticketId);
        args.putString(LIVE_ID, liveId);
        args.putBoolean(LIVE_CAN_PLAY, isCanPlay);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ticketId = getArguments().getString(TICKET_ID);
            mLiveId = getArguments().getString(LIVE_ID);
            isCanPlay = getArguments().getBoolean(LIVE_CAN_PLAY);
        }

        //????????????????????????
        MZSDKInitManager.getInstance().initLive();
        MZSDKInitManager.getInstance().registerInitListener(new SDKInitListener() {
            @Override
            public void dataResult(int i) {
                //????????????????????????api
                mzApiRequest.startData(MZApiRequest.API_TYPE_PLAY_INFO, ticketId);
            }

            @Override
            public void errorResult(int i, String s) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_halfplayer, container, false);
        mTvOnline = rootView.findViewById(R.id.tv_playerfragment_person);
        mIvClose = rootView.findViewById(R.id.iv_playerfragment_close);
        mIvAvatar = rootView.findViewById(R.id.civ_playerfragment_avatar);
        mOnlinePersonIv1 = rootView.findViewById(R.id.civ_activity_live_online_person1);
        mOnlinePersonIv2 = rootView.findViewById(R.id.civ_activity_live_online_person2);
        mOnlinePersonIv3 = rootView.findViewById(R.id.civ_activity_live_online_person3);
        mTvNickName = rootView.findViewById(R.id.tv_playerfragment_nickname);
        mTvPopular = rootView.findViewById(R.id.tv_playerfragment_popularity);
        mTvLiveType = rootView.findViewById(R.id.tv_playerfragment_livetype_tag);
        mTVLiveTypeText = rootView.findViewById(R.id.tv_playerfragment_livetype);
        mTvAttention = rootView.findViewById(R.id.tv_playerfragment_attention);
        mVideoFrame = rootView.findViewById(R.id.video_halfplayerfragment_frame);
        mzPlayerView = rootView.findViewById(R.id.video_playerfragment_view);
        mAppBarLayout = rootView.findViewById(R.id.sv_activity_content_appbar);
        mRlLiveOver = rootView.findViewById(R.id.rl_activity_broadcast_live_over);
        mLiveContent = rootView.findViewById(R.id.tv_activity_broadcast_live_over);
        mBottomLayout = rootView.findViewById(R.id.video_halfplayerfragment_bottom_layout);
        mMagicIndicator = rootView.findViewById(R.id.video_halfplayerfragment_bottom_tab);
        mBottomLayout = rootView.findViewById(R.id.video_halfplayerfragment_bottom_layout);
        mMagicIndicator = rootView.findViewById(R.id.video_halfplayerfragment_bottom_tab);
        mVpContainer = rootView.findViewById(R.id.vp_halffragment_watch_bottom);
        mzADBannerView = rootView.findViewById(R.id.banner_halfplayerfragment_ad);
        return rootView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //??????????????????????????????
        avatarOptions = new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.icon_default_avatar)
                .showImageForEmptyUri(R.mipmap.icon_default_avatar)
                .showImageOnFail(R.mipmap.icon_default_avatar)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .build();
        //?????????view??????
        initView();
        //???????????????tab
        initBottomFragment();
        //???????????????
        initListener();
        //??????api????????????
        loadData();

        if (mManager != null) {
            mManager.setPlayerReConnectViewShow(true);
            mManager.setNetWorkConnectReload(true);
        }
    }

    public void reloadLiveStatus() {
        isCanPlay = true;

        if (TextUtils.isEmpty(mPlayInfoDto.getVideo().getUrl())) {
            mzApiRequest.startData(MZApiRequest.API_TYPE_PLAY_INFO, ticketId);
        } else {
            mManager.reset();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mManager.reload();
                }
            }, 500);
        }
    }

    public void setBottomTabs(List<HashMap<String, Fragment>> bottomTabs) {
        this.bottomTabs = bottomTabs;
    }

    private void initBottomFragment() {
        mTabTitleList.add("??????");
        mTabFragmentList.add(mWatchBottomFragment);
        if (bottomTabs != null)
            for (int i = 0; i < bottomTabs.size(); i++) {
                String tabKey = bottomTabs.get(i).keySet().iterator().next();
                Fragment tabFragment = bottomTabs.get(i).get(tabKey);
                if (tabFragment != null) {
                    mTabTitleList.add(tabKey);
                    mTabFragmentList.add(tabFragment);
                }
            }
    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(getActivity()));
        mWatchBottomFragment = new WatchBottomFragment();
        mManager = new MZPlayerManager();
        mManager.init(mzPlayerView);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mManager != null) {
            mManager.configurationChanged(newConfig);
        }
        //???????????????
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            isLandSpace = false;
            changePortrait();
        }
        //???????????????
        else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            isLandSpace = true;
            changeLandscape();
        }
    }

    public void changeLandscape() {
        mCurrentIndexTab = mVpContainer.getCurrentItem();
        mVpContainer.setCurrentItem(0, false);
        mVpContainer.setLocked(true);
        ConstraintLayout.LayoutParams appBarLayoutParams = (ConstraintLayout.LayoutParams) mAppBarLayout.getLayoutParams();
        appBarLayoutParams.height = DensityUtil.dip2px(mActivity, 85);
        mAppBarLayout.setLayoutParams(appBarLayoutParams);
        ConstraintLayout.LayoutParams videoFrameLayoutParams = (ConstraintLayout.LayoutParams) mVideoFrame.getLayoutParams();
        videoFrameLayoutParams.height = ConstraintLayout.LayoutParams.MATCH_PARENT;
        videoFrameLayoutParams.width = ConstraintLayout.LayoutParams.MATCH_PARENT;
        videoFrameLayoutParams.topMargin = 0;
        mVideoFrame.setLayoutParams(videoFrameLayoutParams);
        ConstraintLayout.LayoutParams bottomLayoutParams = (ConstraintLayout.LayoutParams) mBottomLayout.getLayoutParams();
        bottomLayoutParams.topMargin = DensityUtil.dip2px(mActivity, 106);
        mBottomLayout.setLayoutParams(bottomLayoutParams);

        mMagicIndicator.setVisibility(View.GONE);
        hideBottomUIMenu();
        mzADBannerView.setVisibility(View.GONE);
    }

    public void changePortrait() {
        mVpContainer.setCurrentItem(mCurrentIndexTab, false);
        mVpContainer.setLocked(false);
        ConstraintLayout.LayoutParams appBarLayoutParams = (ConstraintLayout.LayoutParams) mAppBarLayout.getLayoutParams();
        appBarLayoutParams.height = DensityUtil.dip2px(mActivity, 104);
        mAppBarLayout.setLayoutParams(appBarLayoutParams);
        ConstraintLayout.LayoutParams videoFrameLayoutParams = (ConstraintLayout.LayoutParams) mVideoFrame.getLayoutParams();
        videoFrameLayoutParams.height = DensityUtil.dip2px(mActivity, 210);
        videoFrameLayoutParams.width = ConstraintLayout.LayoutParams.MATCH_PARENT;
        videoFrameLayoutParams.topMargin = DensityUtil.dip2px(mActivity, 104);
        mVideoFrame.setLayoutParams(videoFrameLayoutParams);
        ConstraintLayout.LayoutParams bottomLayoutParams = (ConstraintLayout.LayoutParams) mBottomLayout.getLayoutParams();
        bottomLayoutParams.topMargin = DensityUtil.dip2px(mActivity, 314);
        mBottomLayout.setLayoutParams(bottomLayoutParams);

        mMagicIndicator.setVisibility(View.VISIBLE);

        mzADBannerView.changePortrait();
    }

    /**
     * ?????????????????????????????????
     */
    protected void hideBottomUIMenu() {
        //?????????????????????????????????
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {
            View v = mActivity.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = mActivity.getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    private void initListener() {
        mTvOnline.setOnClickListener(this);
        mIvAvatar.setOnClickListener(this);
        mTvAttention.setOnClickListener(this);
        mIvClose.setOnClickListener(this);
        mOnlinePersonIv1.setOnClickListener(this);
        mOnlinePersonIv2.setOnClickListener(this);
        mOnlinePersonIv3.setOnClickListener(this);


        mzADBannerView.loadData(ticketId);
        mzADBannerView.setOnMZRollADItemClickListener(new MZADBannerView.OnMZRollADItemClickListener() {
            @Override
            public void onItemClickListener(MZAdRollAdvertDto mzAdRollAdvertDto) {
                Log.e("TAG", "onItemClickListener: " + mzAdRollAdvertDto.getType());
            }
        });

        //?????????????????????
        MZChatManager.getInstance(mActivity).registerListener(CLASSNAME, new MZChatMessagerListener() {

            @Override
            public void dataResult(Object obj, Page page, int status) {

            }

            @Override
            public void errorResult(int code, String msg) {

            }

            @Override
            public void monitorInformResult(String type, Object obj, Object extend) {
                if (ChatMessageObserver.MESSAGE_TYPE.equals(type) && !(boolean) extend) { //???????????? ??????
                    if (mPlayInfoDto.isInputDanmuku() && PreferencesUtils.loadPrefBoolean(mActivity, PreferencesUtils.DANMAKU_SWITCH_KEY, true))
                        receiveSendDanmaku(obj);
                }
            }

            @Override
            public void monitorInformResult(String s, Object o) {
                ChatMessageDto mChatMessage = (ChatMessageDto) o;
                ChatTextDto mChatText = mChatMessage.getText();
                BaseDto mBase = mChatText.getBaseDto();
                switch (s) {
                    case ChatMessageObserver.ONLINE_TYPE: {//???????????????
                        ChatOnlineDto mChatOnline = (ChatOnlineDto) mBase;
                        mTotalPerson = mTvOnline.getText().toString();
                        long popular = Integer.valueOf(mPlayInfoDto.getPopular()) + mChatOnline.getLast_pv();
                        mPlayInfoDto.setPopular(popular + "");
                        //????????????
                        mTvPopular.setText("??????" + String_Utils.convert2W0_0(popular + ""));
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
                                mTvOnline.setText(String_Utils.convert2W0_0(current + ""));
                            }
                        }
                        break;
                    }
                    case ChatMessageObserver.OFFLINE_TYPE:
                        mTotalPerson = mTvOnline.getText().toString();
                        if (String_Utils.isNumeric(mTotalPerson) && !personAvatars.isEmpty()) {
                            int current = Integer.valueOf(mTotalPerson) - 1;
                            if (current <= 0) {
                                current = 1;
                            }
                            mTvOnline.setText(String_Utils.convert2W0_0(current + ""));

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
                            case ChatCmdDto.LIVE_OVER:
                                //????????????
                                mRlLiveOver.setVisibility(View.VISIBLE);
                                mLiveContent.setText("?????????????????????\n????????????????????????");
                                mManager.stop();
                                mManager.reset();
                                break;
                            case ChatCmdDto.LIVE_END:
                                //????????????
                                mRlLiveOver.setVisibility(View.VISIBLE);
                                mLiveContent.setText("???????????????");
                                mManager.stop();
                                break;
                            case ChatCmdDto.LIVE_PUBLISH_PAUSE:
                                break;
                            case ChatCmdDto.LIVE_PUBLISH_START:
                                mRlLiveOver.setVisibility(View.GONE);
                                if (TextUtils.isEmpty(mPlayInfoDto.getVideo().getUrl())) {
                                    mzApiRequest.startData(MZApiRequest.API_TYPE_PLAY_INFO, ticketId);
                                } else {
                                    mManager.reset();
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            mManager.reload();
                                        }
                                    }, 500);
                                }
                                break;
                            case ChatCmdDto.LIVE_CHANNEL_START:
                                if (ticketId.equals(mChatCmd.getTicket_id()))
                                    mzApiRequest.startData(MZApiRequest.API_TYPE_PLAY_INFO, ticketId);
                                mRlLiveOver.setVisibility(View.GONE);
                                break;
                            case ChatPresenter.DOCSWITCHPAGE:
                                // ??????
                                ArrayList<String> list = new ArrayList<>();
                                list.add(mChatCmd.getUrl());
                                if (mViewDocumentFragment != null) {
                                    mViewDocumentFragment.initViewPager(list);
                                    mViewDocumentFragment.setDocumentName(mChatCmd.getFile_name());
                                }
                                break;
                            case ChatPresenter.WEBINAR_VIEW_CONFIG_UPDATE: //???????????????????????????,???????????????
                                updateConfigs(mChatCmd.getWebinar_content());
                                break;
                            case ChatPresenter.NEW_REPLY: //??????????????????
                                if (mBottomQAFragment != null && mBottomQAFragment.isAdded()) {
                                    mBottomQAFragment.setNewsInfo(mChatCmd.getCount());
                                }
                                break;
                            case "*remove_message":
                                //????????????
                                String data = mChatText.getData();
                                if (data != null) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(data);
                                        if (jsonObject.has("msg_unique_id")) {
                                            JSONArray jsonArray = jsonObject.getJSONArray("msg_unique_id");
                                            String uniqueId = (String) jsonArray.get(0);
                                            Log.e("TAG", "monitorInformResult:================= " + uniqueId);

                                            if (mWatchBottomFragment != null) {
                                                mWatchBottomFragment.removeMsg(uniqueId);
                                            }
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
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
        });

        MZChatManager.getInstance(getActivity()).registerListener(GIFT_KEY, new ChatMessageObserver.MesGiftCallback() {
            @Override
            public void monitorGiftResult(ChatMessageDto chatMessageDto) {
                Log.e("Gift", "monitorGiftResult: " + ((ChatCompleteDto) chatMessageDto.getText().getBaseDto()).getName());
            }
        });

        //?????????  ????????????????????????
        MZChatManager.getInstance(getActivity()).registerListener(KICK_OUT_KEY, new ChatMessageObserver.MesKickOutCallback() {
            @Override
            public void monitorKickOutResult(ChatMessageDto chatMessageDto) {
                Log.e("Kick_out", "monitorKickOutResult: " + ((ChatCmdDto) chatMessageDto.getText().getBaseDto()).getType());

                EventBus.getDefault().post(new MZEvent(MZEvent.TYPE_KICK_OUT, ""));
            }
        });

        MZChatManager.getInstance(getActivity()).registerListener(NEW_REPLY, new ChatMessageObserver.MesReplyCallback() {
            @Override
            public void monitorReplyResult(ChatMessageDto chatMessageDto) {
                Log.e("new_reply", "monitorReplyResult: " + ((ChatCmdDto) chatMessageDto.getText().getBaseDto()).getCount());
            }
        });

        mzApiRequest = new MZApiRequest();
        mzApiRequestOnline = new MZApiRequest();
        mzApiRequestAnchorInfo = new MZApiRequest();
        mzGetSettingRequest = new MZApiRequest();

        //????????????????????????????????????
        mzApiRequest.setResultListener(this);
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

            }
        });
        //??????????????????
        mzApiRequestAnchorInfo.setResultListener(new MZApiDataListener() {
            @Override
            public void dataResult(String s, Object o, Page page, int status) {
                anchorInfoDto = (AnchorInfoDto) o;
                if (mListener != null) {
                    mListener.resultAnchorInfo(anchorInfoDto);
                }
                // ????????????UID ????????????????????????
                MZChatManager.getInstance(mActivity).setAnchorUid(anchorInfoDto.getUid());
                mTvNickName.setText(anchorInfoDto.getNickname());
                ImageLoader.getInstance().displayImage(anchorInfoDto.getAvatar() + String_Utils.getPictureSizeAvatar(), mIvAvatar, avatarOptions);
            }

            @Override
            public void errorResult(String s, int i, String s1) {

            }
        });

        //??????????????????
        mzGetSettingRequest.setResultListener(new MZApiDataListener() {
            @Override
            public void dataResult(String apiType, Object dto, Page page, int status) {
                if (dto instanceof MZAllSettingDto) {
                    MZAllSettingDto mzAllSettingDto = (MZAllSettingDto) dto;
                    if (mzAllSettingDto.getTools() != null && mzAllSettingDto.getTools().size() > 0) {
                        List<MZAllSettingDto.SettingDto> settingDtoList = mzAllSettingDto.getTools();
                        List<RightBean> configList = new ArrayList<>();
                        for (int i = 0; i < settingDtoList.size(); i++) {
                            RightBean rightBean = new RightBean();
                            rightBean.setType(settingDtoList.get(i).getTools());
                            rightBean.setIs_open(settingDtoList.get(i).getIs_open());
                            configList.add(rightBean);
                        }
                        updateConfigs(configList);
                    }
                }
            }

            @Override
            public void errorResult(String apiType, int code, String msg) {

            }
        });

    }

    //??????????????????
    public void receiveSendDanmaku(Object obj) {
        ChatMessageDto mChatMessage = (ChatMessageDto) obj;
        ChatTextDto mChatText = mChatMessage.getText();
        ChatMegTxtDto megTxtDto = (ChatMegTxtDto) mChatText.getBaseDto();
        boolean isSelf;
        if (TextUtils.isEmpty(megTxtDto.getUniqueID())) {
            isSelf = false;
        } else {
            isSelf = megTxtDto.getUniqueID().equals(MyUserInfoPresenter.getInstance().getUserInfo().getUniqueID());
        }
        if (isSelf) {
            mzPlayerView.setDanmakuCustomTextColor(getResources().getColor(R.color.color_fff45c));
        } else {
            mzPlayerView.setDanmakuCustomTextColor(getResources().getColor(R.color.white));
        }
        if (!MZChatManager.getInstance(mActivity).isOnlyAnchor())
            mzPlayerView.sendDanmaku(mChatText.getUser_name() + ":  " + megTxtDto.getText(), liveStatus == 1, mChatText.getAvatar(), new DanmakuViewCacheStuffer(mActivity, mzPlayerView.getDanmakuView()));
    }

    private void loadData() {
        mzApiRequest.createRequest(mActivity, MZApiRequest.API_TYPE_PLAY_INFO);
        mzApiRequestOnline.createRequest(mActivity, MZApiRequest.API_TYPE_ONLINE_USER_LIST);
        mzApiRequestAnchorInfo.createRequest(mActivity, MZApiRequest.API_TYPE_ANCHOR_INFO);
        mzGetSettingRequest.createRequest(mActivity, MZApiRequest.API_GET_ALL_SETTING);

        //??????????????????api
        mzApiRequestAnchorInfo.startData(MZApiRequest.API_TYPE_ANCHOR_INFO, ticketId);
    }

    //???????????????fragment???activity????????????????????????
    private IPlayerClickListener mListener;
    private PlayerEventListener mPlayerEventListener = new PlayerEventListener() {
        @Override
        public void hideAllEvent() {
            HalfPlayerFragment.this.showAllEvent();
        }

        @Override
        public void showAllEvent() {
            HalfPlayerFragment.this.hideAllEvent();
        }

        @Override
        public void onBackClick(boolean isBack) {

        }

        @Override
        public void onPausePlayer() {
            MZChatManager.getInstance(mActivity).sendMessageEndEvent(ticketId);
        }

        @Override
        public void onStartPlayer() {
            MZChatManager.getInstance(mActivity).sendMessagePlayEvent(ticketId, speeds[mSpeedIndex] + "");
        }

        @Override
        public void onForbid(boolean isForbid) {

        }

        @Override
        public void onSwitchFullBtnClick(int orientation) {

        }

        @Override
        public void onTvClick() {
            showDlanDialog();
        }

        @Override
        public void onSpeedClick() {
            showSpeedSelectDialog();
        }
    };

    public void setIPlayerClickListener(IPlayerClickListener listener) {
        mListener = listener;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.civ_playerfragment_avatar) { //??????????????????
            if (mListener != null) {
                mListener.onAvatarClick(anchorInfoDto);
            }

        }
        if (view.getId() == R.id.tv_playerfragment_attention) { //????????????
            if (mListener != null) {
                mListener.onAttentionClick(mPlayInfoDto, mTvAttention);
            }

        }
        if (view.getId() == R.id.tv_playerfragment_person || view.getId() == R.id.civ_activity_live_online_person1
                || view.getId() == R.id.civ_activity_live_online_person2 || view.getId() == R.id.civ_activity_live_online_person3) { //??????????????????
            if (mListener != null) {
                PersonListPopupWindow personListPopupWindow = new PersonListPopupWindow(mActivity, mPlayInfoDto);
                personListPopupWindow.showAtLocation(mzPlayerView, Gravity.CENTER, 0, 0);
                personListPopupWindow.setOnPersonListClickCallBack(new PersonListPopupWindow.PersonListClickedCallBack() {
                    @Override
                    public void onPersonItemClicked(MZOnlineUserListDto onlineUserListDto) {
                        mListener.onOnlineClick(onlineUserListDto);
                    }
                });
            }
        }
        if (view.getId() == R.id.iv_playerfragment_close) { //????????????
            if (mListener != null) {
                mListener.onCloseClick(mPlayInfoDto);
            }
        }
    }

    /**
     * ???????????????????????????
     */
    @SuppressLint("WrongConstant")
    @Override
    public void dataResult(String s, Object o, Page page, int status) {
        mPlayInfoDto = (PlayInfoDto) o;
        updateConfigs(mPlayInfoDto.getRight());
        UserDto userDto = MyUserInfoPresenter.getInstance().getUserInfo();
        userDto.setUid(mPlayInfoDto.getChat_uid());
        MyUserInfoPresenter.getInstance().saveUserinfo(userDto);
        //????????????????????????
        mzGetSettingRequest.startData(MZApiRequest.API_GET_ALL_SETTING, ticketId);
        liveStatus = mPlayInfoDto.getStatus();
        //??????????????????
        mVideoUrl = mPlayInfoDto.getVideo().getUrl();
        if (!TextUtils.isEmpty(mVideoUrl)) {
            controller = DLNAController.getInstance(mActivity.getApplication());
            controller.initPresenter(mActivity.getApplication());
            controller.registerListener(mActivity.getClass().getSimpleName(), new DLNAListener());
            RemoteItem itemurl1 = new RemoteItem("name", "1111", "wwwww",
                    0, "0", "", mVideoUrl);
            controller.setUrl(itemurl1);
            int liveType = PlayerController.TYPE_LIVE;
            if (liveStatus == 2) {
                liveType = PlayerController.TYPE_VIDEO;
            }
            mManager.setBroadcastType(liveType, mPlayInfoDto.getLive_type() == 1).setMediaQuality(IMZPlayerManager.MZ_MEDIA_QUALITY_HIGH);
            if (mPlayerEventListener != null) {
                mManager.setEventListener(mPlayerEventListener);
            }
            //????????????
            mManager.setVideo_advert(mPlayInfoDto.getVideo_advert());
            mManager.setTicket_id(ticketId);
            //????????????
            //????????????
//            mManager.setTVSkipText("??????????????????");
//            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT , RelativeLayout.LayoutParams.WRAP_CONTENT);
//            layoutParams.topMargin = 100;
//            mManager.setTVSkipLayoutParams(layoutParams);
//            mManager.setIsCanPlayVideo(true); //?????????true
//            mManager.setOnADClickListener(new OnADClickListener() {
//                @Override
//                public void onSkipClick(MZPlayAdVideoDto videoAdvertDto) {
//                    ToastUtils.popUpToast("????????????");
//                }
//
//                @Override
//                public void onADVideoClick(MZPlayAdVideoDto videoAdvertDto) {
//                    ToastUtils.popUpToast("????????????");
//                    mManager.pauseADCountDown();
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            mManager.resumeADCountDown();
//                        }
//                    } , 10000);
//                }
//            });
//            mManager.setOnADCountDownListener(new OnADCountDownListener() {
//                @Override
//                public void onCountDown(long countDownTime) {
//                    Log.e("Tag", "onCountDown: " + countDownTime);
//                }
//
//                @Override
//                public void onCountDownEnd(MZPlayAdVideoDto videoAdvertDto) {
//                    ToastUtils.popUpToast("???????????????????????????");
////                    mManager.start();
//                }
//            });
            //????????????
            mManager.setVideoPath(mVideoUrl);
            mManager.showPreviewImage(mPlayInfoDto.getCover());
            mManager.setIsOpenRandom(true);
            mzPlayerView.enableDanmaku(true);
            mManager.setRandomData("test");
            mManager.start();
            if (!isCanPlay) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mManager.stop();
                    }
                }, 500);
            }
            MZChatManager.getInstance(mActivity).setPlayinfo(mPlayInfoDto);
            MZChatManager.getInstance(mActivity).sendMessagePlayEvent(ticketId, speeds[mSpeedIndex] + "");
        } else {
            //????????????
            mzPlayerView.setVideo_advert(mPlayInfoDto.getVideo_advert());
            mzPlayerView.setTicket_id(ticketId);
            //????????????
            mzPlayerView.onlyStartAD();
            if (liveStatus == 3) {
                mRlLiveOver.setVisibility(View.VISIBLE);
                mLiveContent.setText("?????????????????????\n????????????????????????");
                mManager.pause();
            } else {
                mManager.showPreviewImage(mPlayInfoDto.getCover());
            }
        }
        if (liveStatus != 2 && mPlayInfoDto.getLive_type() == 1) {
            mzPlayerView.hideDlnaIV();
        }
        //??????????????????api
        mzApiRequestOnline.startData(MZApiRequest.API_TYPE_ONLINE_USER_LIST, true, ticketId);
        //????????????
        mTvPopular.setText("??????" + String_Utils.convert2W0_0(mPlayInfoDto.getPopular()));
        //??????????????????
        initLiveStatus();
        if (mViewDocumentFragment != null && mViewDocumentFragment.isAdded()) {
            mViewDocumentFragment.setPlayInfoDto(mPlayInfoDto);
            mViewDocumentFragment.loadData();
            return;
        }
        if (mWatchBottomFragment.isAdded()) {
            return;
        }
        mTvOnline.setText(mPlayInfoDto.getWebinar_onlines() + "");
        Bundle bundle = new Bundle();
        bundle.putSerializable(WatchBottomFragment.PLAY_INFO_KEY, mPlayInfoDto);
        bundle.putString(LIVE_ID, mLiveId);
        mWatchBottomFragment.setArguments(bundle);
        mWatchBottomFragment.setIPlayerClickListener(mListener);

        mDescriptionFragment = PlayDescriptionFragment.newInstance(ticketId, mLiveId);
        mTabTitleList.add("??????");
        mTabFragmentList.add(mDescriptionFragment);

        if (mPlayInfoDto.isDocumentShow()) {
            if (mViewDocumentFragment == null) {
                mViewDocumentFragment = ViewDocumentFragment.newInstance(mPlayInfoDto, false);
            }
            mTabTitleList.add("??????");
            mTabFragmentList.add(mViewDocumentFragment);
        }

        mBottomQAFragment = BottomQAFragment.newInstance(mPlayInfoDto);
//        mTabTitleList.add("??????");
//        mTabFragmentList.add(mBottomQAFragment);

        initMagicIndicator(mVpContainer);
        mTitleBarAdapter.setDataList(mTabTitleList);
        mTitleBarAdapter.notifyDataSetChanged();

        mAdapter = new TabPagerAdapter(getChildFragmentManager(), mTabFragmentList);
        mVpContainer.setOffscreenPageLimit(mTabFragmentList.size());
        mVpContainer.setAdapter(mAdapter);
        mVpContainer.setCurrentItem(0);

        //???????????????
        EventBus.getDefault().post(new MZEvent(MZEvent.TYPE_LOAD_IMG_BG, mPlayInfoDto.getCover()));
    }

    private void initLiveStatus() {
        if (liveStatus == 2) {
            mTvLiveType.setBackgroundResource(R.drawable.shape_video_type_ball);
            mTVLiveTypeText.setText("??????");
        } else if (liveStatus == 1) {
            mTvLiveType.setBackgroundResource(R.drawable.shape_live_type_ball);
            mTVLiveTypeText.setText("??????");
        } else if (liveStatus == 0) {
            mTvLiveType.setBackgroundResource(R.drawable.shape_video_type_ball);
            mTVLiveTypeText.setText("?????????");
        }
    }

    //    ??????????????????tab
    private void initMagicIndicator(ViewPager viewPager) {
        CommonNavigator commonNavigator = new CommonNavigator(mActivity);
        commonNavigator.setAdjustMode(true);
        mTitleBarAdapter = new WatchTitleBarAdapter(mActivity, viewPager);
        commonNavigator.setAdapter(mTitleBarAdapter);
        mMagicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mMagicIndicator, viewPager);
    }

    public class TabPagerAdapter extends FragmentStatePagerAdapter {

        private List<Fragment> fragments;

        public TabPagerAdapter(FragmentManager fm, List<Fragment> mHasTabsFragments) {
            super(fm);
            this.fragments = mHasTabsFragments;
        }

        @Override
        public int getCount() {
            return null == fragments ? 0 : fragments.size();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

    }

    @Override
    public void errorResult(String s, int i, String s1) {
        Log.e("gm", "errorResult: " + s);
    }

    //????????????????????????????????????
    private void initOnlineAvatar() {
        if (personAvatars.size() >= 3) {
            mOnlinePersonIv1.setVisibility(View.VISIBLE);
            mOnlinePersonIv2.setVisibility(View.VISIBLE);
            mOnlinePersonIv3.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(personAvatars.get(personAvatars.size() - 1).getAvatar(), mOnlinePersonIv1, avatarOptions);
            ImageLoader.getInstance().displayImage(personAvatars.get(personAvatars.size() - 2).getAvatar(), mOnlinePersonIv2, avatarOptions);
            ImageLoader.getInstance().displayImage(personAvatars.get(personAvatars.size() - 3).getAvatar(), mOnlinePersonIv3, avatarOptions);
        } else if (personAvatars.size() == 2) {
            mOnlinePersonIv1.setVisibility(View.VISIBLE);
            mOnlinePersonIv2.setVisibility(View.VISIBLE);
            mOnlinePersonIv3.setVisibility(View.GONE);
            ImageLoader.getInstance().displayImage(personAvatars.get(personAvatars.size() - 1).getAvatar(), mOnlinePersonIv1, avatarOptions);
            ImageLoader.getInstance().displayImage(personAvatars.get(personAvatars.size() - 2).getAvatar(), mOnlinePersonIv2, avatarOptions);

        } else if (personAvatars.size() == 1) {
            mOnlinePersonIv1.setVisibility(View.VISIBLE);
            mOnlinePersonIv2.setVisibility(View.GONE);
            mOnlinePersonIv3.setVisibility(View.GONE);
            ImageLoader.getInstance().displayImage(personAvatars.get(personAvatars.size() - 1).getAvatar(), mOnlinePersonIv1, avatarOptions);
        }
    }

    //????????????
    public void startDlan() {
        if (controller != null)
            controller.onExecute(DLNAController.PLAYER_DLNA);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MZChatManager.getInstance(mActivity).removeListener(CLASSNAME);
        MZChatManager.getInstance(getActivity()).removeListener(GIFT_KEY);
        MZChatManager.getInstance(getActivity()).removeListener(KICK_OUT_KEY);
        MZChatManager.getInstance(getActivity()).removeListener(NEW_REPLY);
        MZChatManager.destroyChat();
        mManager.onDestroy();
        if (controller != null) {
            controller.onDestroy();
            controller.unRegisterListener(mActivity.getClass().getSimpleName());
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (controller != null)
            controller.onExecute(DLNAController.STOP_DLNA);
    }

    public void hideAllEvent() {
        if (isLandSpace)
            mBottomLayout.setVisibility(View.GONE);
    }

    /**
     * ????????????????????????
     */
    public void showDlanDialog() {

        try {
            assert getFragmentManager() != null;
            ChannelDlnaDialogFragment dlanDialogFragment = (ChannelDlnaDialogFragment) getFragmentManager().findFragmentByTag("DLANDIALOGFRAGMENT");
            if (null == dlanDialogFragment) {
                ChannelDlnaDialogFragment.Builder builder = new ChannelDlnaDialogFragment.Builder(mActivity);
                builder.setOnDeviceItemSelectListener(new ChannelDlnaDialogFragment.OnDeviceItemSelectListener() {
                    @Override
                    public void onSelectDevice() {
                        startDlan();
                    }
                });
                builder.setTitleTextColor(getResources().getColor(R.color.color_782d16));
                builder.setTopRightBtnVisible(View.VISIBLE);
                dlanDialogFragment = builder.build();
            }
            if (!dlanDialogFragment.isAdded() && !dlanDialogFragment.isVisible() && !dlanDialogFragment.isRemoving()) {
                dlanDialogFragment.show(getFragmentManager(), "DLANDIALOGFRAGMENT");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showAllEvent() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mBottomLayout.setVisibility(View.VISIBLE);
            }
        }, 500);
    }

    private void showSpeedSelectDialog() {
        speedDialog = new SpeedBottomDialogFragment(mActivity, isLandSpace);
        speedDialog.showAtLocation(mzPlayerView, mSpeedIndex);
        speedDialog.setOnSelectSpeedListener(new SpeedBottomDialogFragment.OnSelectSpeedListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void OnSelectSpeed(int type) {
                mSpeedIndex = type;
                MZChatManager.getInstance(mActivity).sendMessageChangeSpeedEvent(ticketId, speeds[mSpeedIndex] + "");
                mzPlayerView.setSpeed(speeds[mSpeedIndex]);
                switch (type) {
                    case 0:
                        mzPlayerView.setSpeedDrawable(getResources().getDrawable(R.mipmap.icon_speed_075));
                        break;
                    case 1:
                        mzPlayerView.setSpeedDrawable(getResources().getDrawable(R.mipmap.icon_speed_100));
                        break;
                    case 2:
                        mzPlayerView.setSpeedDrawable(getResources().getDrawable(R.mipmap.icon_speed_125));
                        break;
                    case 3:
                        mzPlayerView.setSpeedDrawable(getResources().getDrawable(R.mipmap.icon_speed_150));
                        break;
                    case 4:
                        mzPlayerView.setSpeedDrawable(getResources().getDrawable(R.mipmap.icon_speed_200));
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * ??????????????????
     */
    private void updateConfigs(List<RightBean> rightBeans) {
        for (int i = 0; i < rightBeans.size(); i++) {
            boolean isOpen = rightBeans.get(i).getIs_open() == 1;
            switch (rightBeans.get(i).getType()) {
                case PlayInfoDto.DISABLE_CHAT: //??????
                    mPlayInfoDto.setDisable_chat(isOpen);
                    break;
                case PlayInfoDto.BARRAGE:
                    mPlayInfoDto.setInputDanmuku(isOpen);
                    break;
                case PlayInfoDto.RECORD_SCREEN:
                    if (mActivity != null) {
                        mPlayInfoDto.setRecordScreen(isOpen);
                        mzPlayerView.setIsOpenRandom(isOpen);
                    }
                    break;
                case PlayInfoDto.VOTE:
                    mPlayInfoDto.setVoteShow(isOpen);
                    break;
                case PlayInfoDto.SIGN:
                    mPlayInfoDto.setSignShow(isOpen);
                    break;
                case PlayInfoDto.DOCUMENTS:
                    mPlayInfoDto.setDocumentShow(isOpen);
                    break;
                case PlayInfoDto.PRIZE:
                    mPlayInfoDto.setLottoShow(isOpen);
                    break;
                case PlayInfoDto.CHAT_HISTORY:
                    mPlayInfoDto.setHide_chat_history(isOpen);
                    break;
                case PlayInfoDto.TIMES_SPEED:
                    mPlayInfoDto.setTimes_speed_open(isOpen);
                    if (mPlayInfoDto.isTimes_speed_open()) {
                        mzPlayerView.showSpeedIV();
                    } else {
                        mzPlayerView.hideSpeedIV();
                    }
                case PlayInfoDto.PAY_GIFT:
                    mPlayInfoDto.setPay_gift_open(isOpen);
                    break;
                case PlayInfoDto.OPEN_LIKE:
                    mPlayInfoDto.setLike_open(isOpen);
                    break;
                default:
                    break;
            }
            if (mWatchBottomFragment != null && mWatchBottomFragment.isAdded()) {
                mWatchBottomFragment.updateConfig(mPlayInfoDto);
            }
        }
    }

    class DLNAListener implements DLNAController.DLNAStateListener {

        @Override
        public void onSuccess(final DLNAController.DLNAState state) {

        }

        @Override
        public void onError(DLNAController.DLNAError error) {


        }
    }

    public void loginCallback(UserDto dto) {
        MyUserInfoPresenter.getInstance().saveUserinfo(dto);
        MZApiRequest apiRequest = new MZApiRequest();
        apiRequest.createRequest(mActivity, MZApiRequest.API_TYPE_PLAY_INFO);
        apiRequest.startData(MZApiRequest.API_TYPE_PLAY_INFO, ticketId);
        apiRequest.setResultListener(new MZApiDataListener() {
            @Override
            public void dataResult(String s, Object o, Page page, int status) {
                mPlayInfoDto = (PlayInfoDto) o;
                //??????????????????
                mVideoUrl = mPlayInfoDto.getVideo().getHttp_url();
            }

            @Override
            public void errorResult(String s, int i, String s1) {

            }
        });
    }

}
