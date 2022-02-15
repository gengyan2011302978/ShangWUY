package com.mzmedia.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;

import android.widget.TextView;

import com.mengzhu.sdk.R;
import com.mzmedia.presentation.dto.MZEvent;
import com.zzhoujay.richtext.RichText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @author: gengyan
 * date:    2021/4/9
 * company: 普华集团
 * description: 直播简介
 */
public class PlayDescriptionFragment extends BaseFragement {

    private TextView mTvDes;
    private String mTicketId;
    private String mLiveId;

    public static PlayDescriptionFragment newInstance(String ticketId, String liveId) {
        PlayDescriptionFragment fragment = new PlayDescriptionFragment();
        Bundle args = new Bundle();
        args.putString(HalfPlayerFragment.TICKET_ID, ticketId);
        args.putString(HalfPlayerFragment.LIVE_ID, liveId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (null == args || !args.containsKey(HalfPlayerFragment.TICKET_ID)) {
            return;
        }
        mTicketId = args.getString(HalfPlayerFragment.TICKET_ID, "");
        mLiveId = args.getString(HalfPlayerFragment.LIVE_ID, "");
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_play_description;
    }

    @Override
    public void initView() {
        mTvDes = mView.findViewById(R.id.tv_des);
    }

    @Override
    public void initData() {
        RichText.initCacheDir(getActivity());
    }

    @Override
    public void setListener() {

    }

    @Override
    public void loadData() {
        EventBus.getDefault().post(new MZEvent(MZEvent.TYPE_GET_LIVE_DES, mLiveId));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventBean(MZEvent mzEvent) {
        if (mzEvent != null) {
            int type = mzEvent.type;
            if (type == MZEvent.TYPE_LOAD_LIVE_DES) {
                RichText.from(mzEvent.liveDes).into(mTvDes);
            }
        }
    }
}
