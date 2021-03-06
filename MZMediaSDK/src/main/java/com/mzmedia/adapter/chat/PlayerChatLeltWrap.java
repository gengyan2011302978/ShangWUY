package com.mzmedia.adapter.chat;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mengzhu.live.sdk.business.dto.chat.ChatMessageDto;
import com.mengzhu.live.sdk.business.dto.chat.ChatTextDto;
import com.mengzhu.live.sdk.business.dto.chat.impl.ChatMegTxtDto;
import com.mengzhu.sdk.R;
import com.mzmedia.activity.ImageShowActivity;
import com.mzmedia.adapter.base.BaseViewObtion;
import com.mzmedia.utils.String_Utils;
import com.mzmedia.widgets.CircularImage;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * Created by DELL on 2016/7/8.
 */
public class PlayerChatLeltWrap extends BaseViewObtion {

    private Context mContext;
    private ViewHolder mHolder;

    public PlayerChatLeltWrap(Context context) {
        mContext = context;
    }

    @Override
    public View createView(Object o, int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            mHolder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.mz_halfplayer_chat_left_item, null);
            mHolder.mPlayerChatIcon = convertView.findViewById(R.id.player_chat_icon);
            mHolder.mPlayerChatLayout = convertView.findViewById(R.id.player_chat_avatar_layout);
            mHolder.mPlayerChatContent = convertView.findViewById(R.id.player_chat_content);
            mHolder.mPlayerChatUsername = convertView.findViewById(R.id.player_chat_username);
            mHolder.mPlayerGiftLayout = convertView.findViewById(R.id.player_chat_gift_layout);
            mHolder.mPlayerGiftIcon = convertView.findViewById(R.id.player_chat_gift_icon);
            mHolder.mPlayerGiftNum = convertView.findViewById(R.id.player_chat_gift_num);
            mHolder.mContentLayout = convertView.findViewById(R.id.ll_content);
            mHolder.mIvContent = convertView.findViewById(R.id.iv_content);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    @Override
    public void updateView(Object o, int position, View convertView) {
        ChatMessageDto dto = (ChatMessageDto) o;
        ViewHolder holder = (ViewHolder) convertView.getTag();
        ChatTextDto textDto = dto.getText();
        holder.mPlayerChatUsername.setText(textDto.getUser_name() + ": ");
        holder.mPlayerChatIcon.setTag(textDto);
        holder.mPlayerChatLayout.setOnClickListener(new ChatLeltItemtListener(textDto));

        ImageLoader.getInstance().displayImage(textDto.getAvatar(), holder.mPlayerChatIcon, new DisplayImageOptions.Builder()
                .showStubImage(R.mipmap.icon_default_avatar)
                .showImageForEmptyUri(R.mipmap.icon_default_avatar)
                .showImageOnFail(R.mipmap.icon_default_avatar)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .displayer(new RoundedBitmapDisplayer(20))
                .build());
        mHolder.mPlayerGiftLayout.setVisibility(View.GONE);
        ChatMegTxtDto megTxtDto = (ChatMegTxtDto) textDto.getBaseDto();
        if (megTxtDto != null) {
            if (megTxtDto.getImgSrc() != null) {
                mHolder.mIvContent.setVisibility(View.VISIBLE);
                mHolder.mContentLayout.setVisibility(View.GONE);

                ImageLoader.getInstance().displayImage(megTxtDto.getImgSrc(), mHolder.mIvContent, new DisplayImageOptions.Builder()
                        .cacheInMemory(true)
                        .cacheOnDisc(true)
                        .build());
                //String_Utils.handlerContent("??????????????????", holder.mPlayerChatContent, R.color.mz_at_name_color);
                mHolder.mIvContent.setOnClickListener(v -> {
                    Intent intent = new Intent(mContext, ImageShowActivity.class);
                    intent.putExtra(ImageShowActivity.URL_IMAGE, megTxtDto.getImgSrc());
                    mContext.startActivity(intent);
                });
            } else {
                mHolder.mIvContent.setVisibility(View.GONE);
                mHolder.mContentLayout.setVisibility(View.VISIBLE);

                String_Utils.handlerContent(megTxtDto.getText(), holder.mPlayerChatContent, R.color.mz_at_name_color);
            }
        }
    }

    private OnChatIconClickListener mOnChatIconClickListener;

    public interface OnChatIconClickListener {
        void onChatIconClick(ChatTextDto dto);
    }

    public void setOnChatIconClickListener(OnChatIconClickListener listener) {
        mOnChatIconClickListener = listener;
    }

    class ChatLeltItemtListener implements View.OnClickListener {
        private ChatTextDto mDto;

        public ChatLeltItemtListener(ChatTextDto dto) {
            this.mDto = dto;
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.player_chat_avatar_layout) {
                if (mOnChatIconClickListener != null) {
                    mOnChatIconClickListener.onChatIconClick(mDto);
                }
            }
        }
    }

    class ViewHolder {
        LinearLayout mPlayerChatLayout;
        CircularImage mPlayerChatIcon;
        TextView mPlayerChatContent;
        TextView mPlayerChatUsername;
        RelativeLayout mPlayerGiftLayout;
        ImageView mPlayerGiftIcon;
        TextView mPlayerGiftNum;
        LinearLayout mContentLayout;
        ImageView mIvContent;
    }

    private boolean isLandscape;
    private boolean isHalfPlayer;

    public void setIsLandscape(boolean isLandscape) {
        this.isLandscape = isLandscape;
    }

    public void setHalfPlayer(boolean halfPlayer) {
        isHalfPlayer = halfPlayer;
    }

}
