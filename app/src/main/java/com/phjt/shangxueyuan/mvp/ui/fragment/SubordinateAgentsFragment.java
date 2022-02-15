package com.phjt.shangxueyuan.mvp.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.chad.library.adapter.base.BaseQuickAdapter;

import com.phjt.base.base.BaseFragment;
import com.phjt.base.di.component.AppComponent;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.AgentsBean;
import com.phjt.shangxueyuan.di.component.DaggerSubordinateAgentsComponent;
import com.phjt.shangxueyuan.mvp.contract.SubordinateAgentsContract;
import com.phjt.shangxueyuan.mvp.presenter.SubordinateAgentsPresenter;
import com.phjt.shangxueyuan.mvp.ui.adapter.SubordinateAgentsAdater;
import com.phjt.shangxueyuan.widgets.popupwindow.MyTimePickerView;
import com.phsxy.utils.LogUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

/**
 * @author : gengyan
 * @company : Copyright (c) 2021 普华集团 All rights reserved
 * @date : 2021/09/09 18:15
 * @description :直属邀请/下级代理商
 */
public class SubordinateAgentsFragment extends BaseFragment<SubordinateAgentsPresenter> implements SubordinateAgentsContract.View,
        BaseQuickAdapter.OnItemChildClickListener, OnRefreshLoadMoreListener {


    public static final String BUNDLE_NAME = "name";
    @BindView(R.id.rv_agents)
    RecyclerView rvAgents;
    @BindView(R.id.srl_agents)
    SmartRefreshLayout srlAgents;
    private SubordinateAgentsAdater mAdapter;
    private int mType;

    private String startTime;
    private String endTime;
    private OptionsPickerView pvCustomOptions;
    private MyTimePickerView pvTime;

    public static SubordinateAgentsFragment newInstance(int type) {
        Bundle args = new Bundle();
        SubordinateAgentsFragment fragment = new SubordinateAgentsFragment();
        args.putInt(BUNDLE_NAME, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerSubordinateAgentsComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_subordinate_agents, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        mType = bundle.getInt(BUNDLE_NAME, 0);
        rvAgents.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new SubordinateAgentsAdater(getActivity(), mType);
        rvAgents.setAdapter(mAdapter);
        setEmptyView();
        setHeaderView();

        mAdapter.setOnItemChildClickListener(this);
        AgentsBean bean = new AgentsBean(1);
        AgentsBean bean1 = new AgentsBean(2);
        AgentsBean bean2 = new AgentsBean(3);
        List<AgentsBean> list = new ArrayList<>();
        list.add(bean);
        list.add(bean1);
        list.add(bean2);
        mAdapter.setNewData(list);
    }


    public void setEmptyView() {
        View emptyView = LayoutInflater.from(mContext).inflate(R.layout.empty_layout, null);
        mAdapter.setEmptyView(emptyView);
        TextView tvNodata = emptyView.findViewById(R.id.tv_nodata);
        ImageView imageNodata = emptyView.findViewById(R.id.image_nodata);
        if (mType == 0) {
            imageNodata.setImageResource(R.drawable.ic_agents_nodata);
            tvNodata.setText("您还没有直属邀请用户～");
        } else {
            imageNodata.setImageResource(R.drawable.ic_lower_nodata);
            tvNodata.setText("您还没有下属代理商～");
        }

        emptyView.setVisibility(View.GONE);
    }


    public void setHeaderView() {
        View mHeaderView = View.inflate(getContext(), R.layout.item_agents_header, null);
        mHeaderView.setVisibility(View.VISIBLE);
        TextView tvInvitationsNum = mHeaderView.findViewById(R.id.tv_invitations_num);
        TextView tvAmountNum = mHeaderView.findViewById(R.id.tv_amount_num);
        TextView tvDateStart = mHeaderView.findViewById(R.id.tv_date_start);
        TextView tvDateEnd = mHeaderView.findViewById(R.id.tv_date_end);

        tvDateStart.setOnClickListener(view -> showTimePicker(tvDateStart));
        tvDateEnd.setOnClickListener(view -> showTimePicker(tvDateEnd));

        tvInvitationsNum.setText("20243456人");
        tvAmountNum.setText("20243456学豆");
        tvDateStart.setText("2021.04.22");
        tvDateEnd.setText("2021.07.22");

        mAdapter.setHeaderView(mHeaderView);
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onLoadMore(@NonNull @NotNull RefreshLayout refreshLayout) {

    }

    @Override
    public void onRefresh(@NonNull @NotNull RefreshLayout refreshLayout) {

    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }


    /**
     * 选择时间
     *
     * @param textView
     */
    public void showTimePicker(TextView textView) {
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        SimpleDateFormat formatter_year = new SimpleDateFormat("yyyy ");
        String year_str = formatter_year.format(curDate);
        int year_int = (int) Double.parseDouble(year_str);


        SimpleDateFormat formatter_mouth = new SimpleDateFormat("MM ");
        String mouth_str = formatter_mouth.format(curDate);
        int mouth_int = (int) Double.parseDouble(mouth_str);

        SimpleDateFormat formatter_day = new SimpleDateFormat("dd ");
        String day_str = formatter_day.format(curDate);
        int day_int = (int) Double.parseDouble(day_str);


        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(1900, 0, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(year_int, mouth_int - 1, day_int);

            //时间选择器
            //年月日时分秒 的显示与否，不设置则默认全部显示
            pvTime = new MyTimePickerView.Builder(getActivity(), (date, v) -> {
                //选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                textView.setText(getTime(date));
            }).setType(new boolean[]{true, true, true, false, false, false})
                    //默认设置为年月日时分秒
                    .setLabel("", "", "", "", "", "")//默认设置为年月日时分秒
                    .isCenterLabel(false)
                    .setOutSideCancelable(false)
                    .setDividerColor(ContextCompat.getColor(getActivity(), R.color.color_FFE2E2E2))
                    //设置选中项的颜色
                    .setTextColorCenter(ContextCompat.getColor(getActivity(), R.color.color_FF000000))
                    //设置没有被选中项的颜色
                    .setTextColorOut(ContextCompat.getColor(getActivity(), R.color.color_99000000))
                    .setContentSize(18)
                    .setDate(selectedDate)
                    .setLineSpacingMultiplier(3.0f)
                    .setRangDate(startDate, endDate)
                    //设置外部遮罩颜色
                    .setBackgroundId(ContextCompat.getColor(getActivity(), R.color.transparent6))
                    .setDecorView(null)
                    .build();
            pvTime.setDate(Calendar.getInstance());
            pvTime.show();

    }


    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }


}