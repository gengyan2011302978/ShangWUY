package com.mzmedia.fragment.gift;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mengzhu.live.sdk.business.dto.CommontConstant;
import com.mengzhu.live.sdk.business.dto.gift.GiftDto;
import com.mengzhu.live.sdk.business.dto.gift.PushWrapperDto;
import com.mengzhu.live.sdk.business.dto.play.PlayInfoDto;
import com.mengzhu.live.sdk.core.utils.ToastUtils;
import com.mengzhu.live.sdk.ui.api.MZApiDataListener;
import com.mengzhu.live.sdk.ui.api.MZApiRequest;
import com.mengzhu.live.sdk.ui.widgets.CirclePageIndicator;
import com.mengzhu.live.sdk.ui.widgets.MzStateView;
import com.mengzhu.sdk.R;
import com.mzmedia.adapter.base.TabPagerAdapter;
import com.mzmedia.presentation.dto.MZEvent;
import com.mzmedia.utils.String_Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import tv.mengzhu.core.wrap.library.utils.UiUtils;
import tv.mengzhu.core.wrap.netwock.Page;

/**
 * 礼物列表弹窗
 */
public class SendGiftDialogFragment extends DialogFragment implements MZApiDataListener, View.OnClickListener, SendGiftContainerFragment.onColumnItemListener {

    private View root;
    private Dialog mDialog = null;
    private LinearLayout mContainer;
    private LinearLayout mOutLayout;
    private LinearLayout mRootView;
    private ViewPager mViewPager;
    private CirclePageIndicator mIndicator;
    private MzStateView mStateView;
    private Button mBtnSend;
    private TextView mTvIntegral;
    private boolean isSelect = false; //是否选择礼物

    private List<Fragment> mFragmentList = new ArrayList<>();
    private TabPagerAdapter mAdapter = null;
    private MZApiRequest mzGiftListRequest;//礼物列表
    private MZApiRequest mzSendGiftRequest;//发送礼物

    private PlayInfoDto mPlayInfoDto;
    private GiftDto mGift;
    private RelativeLayout fragment_send_gift_btn_layout;
    private int pageSize = 8;

    /**
     * 积分
     */
    private Double mIntegral;
    public static final String BUNDLE_INTEGRAL = "bundle_integral";
    /**
     * 直播id
     */
    private String mLiveId;
    public static final String BUNDLE_LIVE_ID = "live_id";

    public static SendGiftDialogFragment newInstance(PlayInfoDto mPlayInfo, String liveId, Double integral) {
        SendGiftDialogFragment bottomFragment = new SendGiftDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(CommontConstant.PLAY_INFO, mPlayInfo);
        args.putDouble(BUNDLE_INTEGRAL, integral);
        args.putString(BUNDLE_LIVE_ID, liveId);
        bottomFragment.setArguments(args);
        return bottomFragment;
    }

    private Activity mActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.TransparentPushDialog);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_send_gift, container);
        initDialog();
        initView();
        initData();
        initListener();
        loadData();
        return root;
    }

    private void initDialog() {
        mDialog = getDialog();
        mDialog.getWindow().getAttributes().windowAnimations = R.style.dialogNoAnim;
        // 设置宽度为屏宽, 靠近屏幕底部。
        Window window = mDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT; // 宽度持平
        window.setAttributes(lp);
        mDialog.setCanceledOnTouchOutside(true); // 外部点击取消
        window.requestFeature(Window.FEATURE_NO_TITLE);
        Bundle args = getArguments();
        if (null != args && args.containsKey(CommontConstant.PLAY_INFO)) {
            mPlayInfoDto = (PlayInfoDto) args.getSerializable(CommontConstant.PLAY_INFO);
            mIntegral = args.getDouble(BUNDLE_INTEGRAL);
            mLiveId = args.getString(BUNDLE_LIVE_ID);
        }
    }

    ObjectAnimator objectAnimator;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void initView() {
        mContainer = (LinearLayout) root.findViewById(R.id.layout_fragment_send_gift_container);
        mRootView = (LinearLayout) root.findViewById(R.id.layout_fragment_send_gift_root);
        mViewPager = (ViewPager) root.findViewById(R.id.vp_fragment_send_gift);
        mIndicator = (CirclePageIndicator) root.findViewById(R.id.cpi_fragment_send_gift);
        mStateView = (MzStateView) root.findViewById(R.id.errorview_fragment_send_gift);
        mStateView.setContentView(mContainer);
        mBtnSend = (Button) root.findViewById(R.id.fragment_buy_gift_send_btn);
        mTvIntegral = root.findViewById(R.id.tv_integral);
        mOutLayout = (LinearLayout) root.findViewById(R.id.layout_gift_outside);
        fragment_send_gift_btn_layout = (RelativeLayout) root.findViewById(R.id.fragment_send_gift_btn_layout);
        objectAnimator = ObjectAnimator.ofFloat(mRootView, "translationY", 1200f, 0f);
        objectAnimator.setDuration(250);
        objectAnimator.start();
    }

    private void initData() {
        mAdapter = new TabPagerAdapter(getChildFragmentManager(), mFragmentList);
        mzGiftListRequest = new MZApiRequest();
        mzGiftListRequest.createRequest(getContext(), MZApiRequest.API_TYPE_GIFT_LIST);
        mzGiftListRequest.setResultListener(this);

        mzSendGiftRequest = new MZApiRequest();
        mzSendGiftRequest.createRequest(getContext(), MZApiRequest.API_TYPE_SEND_GIFT);
        mzSendGiftRequest.setResultListener(sendGiftListener);

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    private void loadData() {
        mStateView.show(MzStateView.NetState.LOADING);
        if (Configuration.ORIENTATION_PORTRAIT == getContext().getResources().getConfiguration().orientation) {
            pageSize = 8;
        } else {
            pageSize = 7;
        }
        mzGiftListRequest.startData(MZApiRequest.API_TYPE_GIFT_LIST, mPlayInfoDto.getTicket_id(), 0, 50);

        mTvIntegral.setText("我的学豆：" + String_Utils.linearNmber(String.valueOf(mIntegral)));
    }

    private void initListener() {
        mBtnSend.setOnClickListener(this);
        mOutLayout.setOnClickListener(this);
        fragment_send_gift_btn_layout.setOnClickListener(this);
        mDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                    exitDialog();
                }
                return false;
            }
        });
    }

    @Override
    public void dataResult(String apiType, Object dto, Page page, int status) {
        if (MZApiRequest.API_TYPE_GIFT_LIST.equals(apiType)) {
            if (dto != null) {
                List<GiftDto> mGiftDto = (ArrayList<GiftDto>) dto;
                mBtnSend.setEnabled(true);
                if (null == mGiftDto || mGiftDto.isEmpty()) {
                    mStateView.show(MzStateView.NetState.EMPTY);
                    mBtnSend.setEnabled(false);
                    return;
                }
                List<PushWrapperDto> mList = new ArrayList<PushWrapperDto>();
                if (mList.size() > 0) {
                    mList.clear();
                }
                for (int i = 0; i < mGiftDto.size(); i++) {
                    if (!TextUtils.isEmpty(mGiftDto.get(i).getName())) {
                        mList.add(mGiftDto.get(i));
                    }
                }
                List<List<PushWrapperDto>> mGifts = UiUtils.splitList(mList, pageSize);
                SendGiftContainerFragment mConFragment;
                for (int i = 0; i < mGifts.size(); i++) {
                    mConFragment = SendGiftContainerFragment.newInstance((ArrayList<PushWrapperDto>) mGifts.get(i), i);
                    mConFragment.setOnColumnItemListener(this);
                    mFragmentList.add(mConFragment);
                }
                mViewPager.setAdapter(mAdapter);
                mViewPager.setOffscreenPageLimit(mFragmentList.size());
                mIndicator.setViewPager(mViewPager);
                mAdapter.notifyDataSetChanged();
                if (mGifts.size() <= 1) {
                    mIndicator.setVisibility(View.GONE);
                } else {
                    mIndicator.setVisibility(View.VISIBLE);
                }
                mStateView.show(MzStateView.NetState.CONTENT);
            } else {
                mStateView.show(MzStateView.NetState.EMPTY);
                mBtnSend.setEnabled(false);
            }
        }
    }

    @Override
    public void errorResult(String apiType, int code, String msg) {
        if (MZApiRequest.API_TYPE_GIFT_LIST.equals(apiType)) {
            mStateView.show(MzStateView.NetState.LOADERROR);
            mBtnSend.setEnabled(false);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.layout_gift_outside) {
            exitDialog();
        } else if (id == R.id.fragment_buy_gift_send_btn) {
            sendGift();
        }
    }

    private void exitDialog() {
        objectAnimator = ObjectAnimator.ofFloat(mRootView, "translationY", 0f, 1200f);
        objectAnimator.setDuration(300);
        objectAnimator.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        }, 250);
    }

    public void sendGift() {
        if (mPlayInfoDto != null && mGift != null) {
            EventBus.getDefault().post(new MZEvent(MZEvent.TYPE_SEND_GIF, mLiveId,
                    Double.valueOf(mGift.getPrice()), mGift.getName()));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventBean(MZEvent mzEvent) {
        if (mzEvent != null) {
            int type = mzEvent.type;
            if (type == MZEvent.TYPE_SEND_GIF_SUCCESS) {
                mzSendGiftRequest.startData(MZApiRequest.API_TYPE_SEND_GIFT, mPlayInfoDto.getTicket_id(), mGift.getId() + "", "1");
            }else if (type == MZEvent.TYPE_CLOSE_GIF_DIALOG){
                exitDialog();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onColumnItemClicked(PushWrapperDto item) {
        mGift = (GiftDto) item;
    }

    MZApiDataListener sendGiftListener = new MZApiDataListener() {
        @Override
        public void dataResult(String apiType, Object dto, Page page, int status) {
            if (status == 200) {
                ToastUtils.popUpToast("发送成功");
                exitDialog();
            } else {
                ToastUtils.popUpToast("发送失败");
            }
        }

        @Override
        public void errorResult(String apiType, int code, String msg) {
            ToastUtils.popUpToast("发送失败");
        }
    };
}
