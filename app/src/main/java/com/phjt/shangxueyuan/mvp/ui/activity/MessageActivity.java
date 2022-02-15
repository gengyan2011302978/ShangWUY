package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.MessageBean;
import com.phjt.shangxueyuan.di.component.DaggerMessageComponent;
import com.phjt.shangxueyuan.mvp.contract.MessageContract;
import com.phjt.shangxueyuan.mvp.presenter.MessagePresenter;
import com.phjt.shangxueyuan.mvp.ui.adapter.MessageAdapter;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.ConstantUmeng;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.shangxueyuan.utils.UmengUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;

/**
 * @author: Created by Template
 * date: 2020/03/30 13:46
 * company: 普华集团
 * description: 描述
 */
public class MessageActivity extends BaseActivity<MessagePresenter> implements MessageContract.View {

    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.rv_message)
    RecyclerView rvList;
    private MessageAdapter mAdapter;
    private View mEmptyView;


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        //如找不到该类,请编译一下项目
        DaggerMessageComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.getListMessage();
        }
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_message;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvCommonTitle.setText("消息");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvList.setLayoutManager(layoutManager);
        mAdapter = new MessageAdapter(this);
        mEmptyView = LayoutInflater.from(this).inflate(R.layout.empty_layout, null);
        mAdapter.setEmptyView(mEmptyView);
        mEmptyView.setVisibility(View.GONE);
        rvList.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                List<MessageBean> data = adapter.getData();
                Intent intent = null;
                if (data.get(position).getType()==1 ) {
                    intent = new Intent(MessageActivity.this, SystemMessageActivity.class);
                } else if (data.get(position).getType()==2) {
                    intent = new Intent(MessageActivity.this, InteractionMessageActivity.class);
                    intent.putExtra(Constant.MESSAGE_HEADER,"课程提醒");
                    intent.putExtra(Constant.MESSAGE_TYPE,2);
                } else if (data.get(position).getType()==3) {
                    intent = new Intent(MessageActivity.this, InteractionMessageActivity.class);
                    intent.putExtra(Constant.MESSAGE_HEADER,"互动提醒");
                    intent.putExtra(Constant.MESSAGE_TYPE,3);
                } else if (data.get(position).getType()==4) {
                    intent = new Intent(MessageActivity.this, InteractionMessageActivity.class);
                    intent.putExtra(Constant.MESSAGE_HEADER,"活动公告");
                    intent.putExtra(Constant.MESSAGE_TYPE,4);
                }

                if (intent != null) {
                    startActivity(intent);
                    UmengUtil.umengCount(MessageActivity.this, ConstantUmeng.MINE_CLICK_MESSAGE_STATION);
                }
            }
        });

    }

    @OnClick(R.id.iv_common_back)
    public void onViewClicked(View view) {
        if (view.getId() == R.id.iv_common_back) {
            finish();
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
        TipsUtil.showTips(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArchitectUtils.startActivity(intent);
    }


    @Override
    public void getLoadSucceed(List<MessageBean> beanList) {

        if (null == beanList){
            return;
        }
        if (beanList.size()>0){
            mAdapter.setNewData(beanList);
        }else {
            if (mEmptyView != null) {
                mEmptyView.setVisibility(View.VISIBLE);
            }

        }
    }

    @Override
    public void getLoadFail() {
        if (mEmptyView != null) {
            mEmptyView.setVisibility(View.VISIBLE);
        }
    }
}
