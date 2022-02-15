package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.ThemeBean;
import com.phjt.shangxueyuan.di.component.DaggerCalendarComponent;
import com.phjt.shangxueyuan.mvp.contract.CalendarContract;
import com.phjt.shangxueyuan.mvp.presenter.CalendarPresenter;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.crash.Utils;
import com.phsxy.utils.ToastUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;


/**
 * @author: Created by GengYan
 * date: 01/28/2021 15:14
 * company: 普华集团
 * description: 模版请保持更新
 */
public class CalendarActivity extends BaseActivity<CalendarPresenter> implements CalendarContract.View, CalendarView.OnCalendarInterceptListener,
        CalendarView.OnCalendarSelectListener,
        CalendarView.OnMonthChangeListener, CalendarView.OnYearChangeListener, CalendarView.OnViewChangeListener {

    @BindView(R.id.tv_common_left)
    TextView tvCommonLeft;
    @BindView(R.id.iv_common_back)
    ImageView ivCommonBack;
    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.tv_common_right)
    TextView tvCommonRight;
    @BindView(R.id.ic_common_right)
    ImageView icCommonRight;
    @BindView(R.id.tv_common_right_collection)
    TextView tvCommonRightCollection;
    @BindView(R.id.ic_common_right_sweep)
    ImageView icCommonRightSweep;
    @BindView(R.id.ic_common_right_collect)
    ImageView icCommonRightCollect;
    @BindView(R.id.ic_common_right_collection)
    ImageView icCommonRightCollection;
    @BindView(R.id.tv_main_info)
    TextView tvMainInfo;
    @BindView(R.id.rl_calendar_left)
    RelativeLayout rlCalendarLeft;
    @BindView(R.id.tv_today_date)
    TextView tvTodayDate;
    @BindView(R.id.rl_calendar_right)
    RelativeLayout rlCalendarRight;
    @BindView(R.id.calendarView)
    CalendarView calendarView;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat simpleDateFormatClick = new SimpleDateFormat("yyyyMMdd");
    SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH:mm:ss");
    List listDay = new ArrayList();
    List listMonth = new ArrayList();
    @BindView(R.id.tv_calendarMonth_right)
    ImageView tvCalendarMonthRight;
    @BindView(R.id.tv_reissueCardNum)
    TextView tvReissueCardNum;
    @BindView(R.id.tv_punchCardTime)
    TextView tvPunchCardTime;
    @BindView(R.id.tv_punchCardType)
    TextView tvPunchCardType;
    @BindView(R.id.iv_left_month)
    ImageView ivLeftMonth;
    @BindView(R.id.iv_left_year)
    ImageView ivLeftYear;
    @BindView(R.id.tv_calendyear_right)
    ImageView tvCalendyearRight;
    private int chooseYear = 0, chooseMonth = 0, chooseDay = 0;
    ThemeBean bean;
    private int initCardType;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCalendarComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_calendar;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvCommonTitle.setText("打卡日历");

        tvReissueCardNum.setVisibility(View.GONE);
        calendarView.setOnCalendarInterceptListener(this);
        calendarView.setOnCalendarSelectListener(this);
        calendarView.setOnYearChangeListener(this);
        calendarView.setOnMonthChangeListener(this);
        calendarView.setOnViewChangeListener(this);
        tvTodayDate.setText(calendarView.getCurYear() + "年" + calendarView.getCurMonth() + "月" + calendarView.getCurDay() + "日");
        calendarView.scrollToCalendar(calendarView.getCurYear(), calendarView.getCurMonth(), calendarView.getCurDay());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.homeCalendar(getIntent().getStringExtra("id"));
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
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArchitectUtils.startActivity(intent);
    }


    @Override
    public void homeCalendarSucceed(ThemeBean themeBean) {
        bean = themeBean;
        tvReissueCardNum.setText("剩余次数：" + themeBean.getReissueCardNum());
        tvPunchCardTime.setText("有效打卡时段：" + themeBean.getPunchCardStartTime() + "--" + themeBean.getPunchCardEndTime());
        Map<String, Calendar> map = new HashMap<>();
        int punchCardType = 0;
        try {
            Date startDate = simpleDateFormat.parse(themeBean.getPunchCardStartDate());
            Date endDate = simpleDateFormat.parse(themeBean.getPunchCardEndDate());
            for (int i = 0; i < differentDaysByMillisecond(startDate, endDate) + 1; i++) {
                String[] strNow1 = new SimpleDateFormat("yyyy-MM-dd").format(getTime(startDate, i)).toString().split("-");
                if (TextUtils.isEmpty(themeBean.getReissueCardNum()) || themeBean.getReissueCardNum().equals("0")) {
                    punchCardType = 1;
                } else {
                    punchCardType = 2;
                }
                if (themeBean.getRecordVos().size() > 0) {
                    for (int j = 0; j < themeBean.getRecordVos().size(); j++) {
                        if (null != themeBean.getRecordVos().get(j).getCalendarDate()) {
                                if (simpleDateFormat.parse(chooseYear + "-" + chooseMonth + "-" + chooseDay).equals(simpleDateFormat.parse(themeBean.getRecordVos().get(j).getCalendarDate()))) {
                                    tvPunchCardType.setText("已打卡");
                                    tvPunchCardType.setClickable(false);
                                    tvPunchCardType.setBackgroundResource(R.drawable.bg_e6e6e6_rectangle);
                                    tvReissueCardNum.setVisibility(View.VISIBLE);
                            }
                            if (simpleDateFormat.parse(themeBean.getRecordVos().get(j).getCalendarDate()).equals(simpleDateFormat.parse(strNow1[0] + "-" + strNow1[1] + "-" + strNow1[2]))) {
                                if (simpleDateFormat.parse(themeBean.getRecordVos().get(j).getCalendarDate()).equals(simpleDateFormat.parse(bean.getNowDate().split("\\s+")[0]))) {
                                    tvPunchCardType.setText("已打卡");
                                    tvPunchCardType.setClickable(false);
                                    tvPunchCardType.setBackgroundResource(R.drawable.bg_e6e6e6_rectangle);
                                    tvReissueCardNum.setVisibility(View.GONE);
                                    initCardType = 1;
                                }

                                punchCardType = 3;
                                break;
                            }
                        }
                    }
                } else if (themeBean.getNowDate().split("\\s+")[0].contains(strNow1[0] + "-" + strNow1[1] + "-" + strNow1[2])) {
                    punchCardType = 1;
                }
                if (simpleDateFormat.parse(bean.getNowDate().split("\\s+")[0]).before(simpleDateFormat.parse(strNow1[0] + "-" + strNow1[1] + "-" + strNow1[2]))) {
                    punchCardType = 1;
                    if (calendarView.getCurDay() == Integer.parseInt(strNow1[2]) && punchCardType == 3) {
                        punchCardType = 3;
                    }
                }
                if (themeBean.getNowDate().split("\\s+")[0].contains(strNow1[0] + "-" + strNow1[1] + "-" + strNow1[2])) {
                    punchCardType = 1;
                }
                map.put(getSchemeCalendar(Integer.parseInt(strNow1[0]), Integer.parseInt(strNow1[1]), Integer.parseInt(strNow1[2]), punchCardType, "假").toString(),
                        getSchemeCalendar(Integer.parseInt(strNow1[0]), Integer.parseInt(strNow1[1]), Integer.parseInt(strNow1[2]), punchCardType, "假"));
                listDay.add(Integer.parseInt(strNow1[2]));
                listMonth.add((strNow1[0] + strNow1[1] + strNow1[2]));

            }
            calendarView.setSchemeDate(map);
            calendarView.getSelectedCalendar();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private Date getTime(Date date, int numb) {
        java.util.Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, numb); //把日期往后增加一天,整数  往后推,负数往前移动
        return calendar.getTime();
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDaysByMillisecond(Date date1, Date date2) {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
        return days;
    }

    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        return calendar;
    }

    @Override
    public void homeCalendarFail() {

    }

    @Override
    public boolean onCalendarIntercept(Calendar calendar) {
        int day = calendar.getDay();
        int month = calendar.getMonth();
        if (listMonth.contains(calendar.toString())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onCalendarInterceptClick(Calendar calendar, boolean isClick) {
//        Toast.makeText(this, calendar.toString() + "不在打卡范围", Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.iv_left_month, R.id.iv_left_year, R.id.tv_punchCardType, R.id.iv_common_back, R.id.tv_today_date, R.id.rl_calendar_left, R.id.tv_calendyear_right, R.id.tv_calendarMonth_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_common_back:
                finish();
                break;
            case R.id.iv_left_year:
//                calendarView.getmYearViewPager().setCurrentItem(calendarView.getmYearViewPager().getCurrentItem() - 1);
                calendarView.getMonthViewPager().setCurrentItem(calendarView.getMonthViewPager().getCurrentItem() - 12);
//                if (chooseDay!=0||chooseMonth!=0||chooseYear!=0){
//                    calendarView.scrollToCalendar(chooseYear-1, chooseMonth, chooseDay);
//                }else {
//                    calendarView.scrollToCalendar(calendarView.getCurYear()-1, calendarView.getCurMonth(), calendarView.getCurDay());
//                }
                break;
            case R.id.iv_left_month:
                calendarView.getMonthViewPager().setCurrentItem(calendarView.getMonthViewPager().getCurrentItem() - 1);
                break;
            case R.id.tv_calendyear_right:
                calendarView.getMonthViewPager().setCurrentItem(calendarView.getMonthViewPager().getCurrentItem() + 12);
//                calendarView.getmYearViewPager().setCurrentItem(calendarView.getmYearViewPager().getCurrentItem() + 1);
//                if (chooseDay!=0||chooseMonth!=0||chooseYear!=0){
//                    calendarView.scrollToCalendar(chooseYear+1, chooseMonth, chooseDay);
//                }else {
//                    calendarView.scrollToCalendar(calendarView.getCurYear()+1, calendarView.getCurMonth(), calendarView.getCurDay());
//                }
                break;
            case R.id.tv_calendarMonth_right:
                calendarView.getMonthViewPager().setCurrentItem(calendarView.getMonthViewPager().getCurrentItem() + 1);
                break;
            case R.id.tv_punchCardType:
                if (!TextUtils.isEmpty(bean.getReissueCardNum())) {
                    try {
                        if (bean.getReissueCardNum().equals("0") && (simpleDateFormat.parse(chooseYear + "-" + chooseMonth + "-" + chooseDay)).before(simpleDateFormat.parse(bean.getNowDate().split("\\s+")[0]))) {
                            ToastUtils.show("剩余补卡次数为0");
                            return;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    if (Utils.belongCalendar(simpleDateFormat.parse(bean.getNowDate().split("\\s+")[1]), simpleDateFormat.parse(bean.getPunchCardStartTime()), simpleDateFormat.parse(bean.getPunchCardEndTime()))) {
                        if (!tvPunchCardType.getText().toString().contains("补")) {
                            ToastUtils.show("未到打卡时间");
                            return;
                        }
                    }
                } catch (ParseException e) {

                }

                try {
                    if (simpleDateFormat.parse(bean.getNowDate().split("\\s+")[0]).after(simpleDateFormat.parse(bean.getPunchCardEndDate()))) {
                        if (!tvPunchCardType.getText().toString().contains("补")) {
                            ToastUtils.show("未到打卡时间");
                            return;
                        }
                    }
                    if (simpleTimeFormat.parse(bean.getNowDate().split("\\s+")[1]).before(simpleTimeFormat.parse(bean.getPunchCardStartTime()))) {
                        if (!tvPunchCardType.getText().toString().contains("补")) {
                            ToastUtils.show("未到打卡时间");
                            return;
                        }
                    }
                    if (simpleTimeFormat.parse(bean.getNowDate().split("\\s+")[1]).after(simpleTimeFormat.parse(bean.getPunchCardEndTime()))) {
                        if (!tvPunchCardType.getText().toString().contains("补")) {
                            ToastUtils.show("未到打卡时间");
                            return;
                        }
                    }
                    if (simpleDateFormat.parse(chooseYear + "-" + chooseMonth + "-" + chooseDay).after(simpleDateFormat.parse(bean.getNowDate().split("\\s+")[0]))) {
                        if (!tvPunchCardType.getText().toString().contains("补")) {
                            ToastUtils.show("未到打卡时间");
                            return;
                        }
                    }
                    if (!Utils.belongCalendar(simpleDateFormat.parse(chooseYear + "-" + chooseMonth + "-" + chooseDay), simpleDateFormat.parse(bean.getPunchCardStartDate()), simpleDateFormat.parse(bean.getPunchCardEndDate()))) {
//                        ToastUtils.show("未到打卡时间");
                    } else {
                        String punchCardId = "";
                        String mMonth = "";
                        String mDay = "";
                        if (chooseMonth < 10) {
                            mMonth = "-0" + chooseMonth;
                        } else {
                            mMonth = "-" + chooseMonth;
                        }
                        if (chooseDay < 10) {
                            mDay = "-0" + chooseDay;
                        } else {
                            mDay = "-" + chooseDay;
                        }
                        String mDate = "" + chooseYear + mMonth + mDay;
                        Intent intent = new Intent(CalendarActivity.this, JournalActivity.class);
                        intent.putExtra(Constant.BUNDLE_ADD_DIARY_ID, bean.getId() + "");
                        intent.putExtra(Constant.BUNDLE_ADD_CALENDAR_DATE, mDate);
                        for (int i = 0; i < bean.getMotifs().size(); i++) {
                            if (simpleDateFormat.parse(bean.getMotifs().get(i).getMotifDate()).equals(simpleDateFormat.parse(chooseYear + "-" + chooseMonth + "-" + chooseDay))) {
                                intent.putExtra(Constant.BUNDLE_ADD_MOTIF_ID, bean.getMotifs().get(i).getId() + "");
                                punchCardId = bean.getMotifs().get(i).getPunchCardId() + "";
                                break;
                            }
                        }
                        if (TextUtils.isEmpty(punchCardId)) {
                            punchCardId = getIntent().getStringExtra("id");
                        }
                        if (tvPunchCardType.getText().toString().contains("补")) {
                            intent.putExtra(Constant.BUNDLE_ADD_REISSUE_CARD_TYPE, 1);
                        }
                        intent.putExtra(Constant.BUNDLE_ADD_PUNCH_CARD_ID, punchCardId);
                        startActivity(intent);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        tvTodayDate.setText(calendar.getYear() + "年" + calendar.getMonth() + "月" + calendar.getDay() + "日");
        chooseYear = calendar.getYear();
        chooseMonth = calendar.getMonth();
        chooseDay = calendar.getDay();
        if (calendar.isCurrentDay()){
            tvPunchCardTime.setVisibility(View.VISIBLE);
        }else {
            tvPunchCardTime.setVisibility(View.GONE);
        }
        if (calendar.getSchemeColor() == 1) {
            tvPunchCardType.setText("立即打卡");
            tvPunchCardType.setClickable(true);
            tvPunchCardType.setBackgroundResource(R.drawable.bg_2675fe_rectangle);
            tvReissueCardNum.setVisibility(View.GONE);
        }
        if (calendar.getSchemeColor() == 2) {
            tvPunchCardType.setText("立即补打卡");
            tvPunchCardType.setClickable(true);
            tvPunchCardType.setBackgroundResource(R.drawable.bg_2675fe_rectangle);
            tvReissueCardNum.setVisibility(View.VISIBLE);
        }
        if (calendar.getSchemeColor() == 3) {
            tvPunchCardType.setText("已打卡");
            tvPunchCardType.setClickable(false);
            tvPunchCardType.setBackgroundResource(R.drawable.bg_e6e6e6_rectangle);
            tvReissueCardNum.setVisibility(View.GONE);
        }
        if (null != bean) {
            try {
                if (simpleDateFormat.parse(chooseYear + "-" + chooseMonth + "-" + chooseDay).before(simpleDateFormat.parse(bean.getNowDate().split("\\s+")[0]))) {
                    tvReissueCardNum.setVisibility(View.VISIBLE);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        judgeWhetherPunch(calendar);
    }

    /**
     * 判断是当前是否打卡
     *
     * @param calendar
     */
    private void judgeWhetherPunch(Calendar calendar) {
        if (null != bean && !TextUtils.isEmpty(bean.getNowDate())) {
            String data = "";
            String mMonth = "";
            String mDay = "";
            if (calendar.getMonth() < 10) {
                mMonth = "-0" + calendar.getMonth();
            } else {
                mMonth = "-" + calendar.getMonth();
            }
            if (calendar.getDay() < 10) {
                mDay = "-0" + calendar.getDay();
            } else {
                mDay = "-" + calendar.getDay();
            }
            String mDate = "" + calendar.getYear() + mMonth + mDay;
            if (bean.getNowDate().length() >= 10) {
                data = bean.getNowDate().substring(0, 10);
            }
            if (initCardType == 1 && mDate.equals(data)) {
                tvPunchCardType.setText("已打卡");
                tvPunchCardType.setClickable(false);
                tvPunchCardType.setBackgroundResource(R.drawable.bg_e6e6e6_rectangle);
                tvReissueCardNum.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onMonthChange(int year, int month) {
        Calendar calendar = calendarView.getSelectedCalendar();
        tvTodayDate.setText(year + "年" + month + "月");
    }

    @Override
    public void onYearChange(int year) {

    }

    @Override
    public void onViewChange(boolean isMonthView) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
