package com.phjt.shangxueyuan.mvp.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.gyf.immersionbar.ImmersionBar;
import com.mzmedia.utils.String_Utils;
import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.integration.EventBusManager;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.annotate.SingleClick;
import com.phjt.shangxueyuan.bean.ShareBean;
import com.phjt.shangxueyuan.bean.TrainingDetailBean;
import com.phjt.shangxueyuan.bean.event.TrainingBuySuccessEvent;
import com.phjt.shangxueyuan.common.AppImageLoader;
import com.phjt.shangxueyuan.di.component.DaggerTrainingCampDetailComponent;
import com.phjt.shangxueyuan.interf.IWithoutImmersionBar;
import com.phjt.shangxueyuan.mvp.contract.TrainingCampDetailContract;
import com.phjt.shangxueyuan.mvp.presenter.TrainingCampDetailPresenter;
import com.phjt.shangxueyuan.mvp.ui.adapter.TrainingIconAdapter;
import com.phjt.shangxueyuan.mvp.ui.fragment.DataFragment;
import com.phjt.shangxueyuan.mvp.ui.fragment.TrainingCatalogFragment;
import com.phjt.shangxueyuan.mvp.ui.fragment.TrainingCourseDetailFragment;
import com.phjt.shangxueyuan.utils.BitmapPhjtUtils;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.ShareUtils;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.shangxueyuan.utils.TrainingUtils;
import com.phjt.shangxueyuan.widgets.dialog.DialogUtils;
import com.phjt.sharestatistic.ShareInit;
import com.phjt.view.roundView.RoundTextView;
import com.phsxy.utils.LogUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;


/**
 * @author: Created by GengYan
 * date: 01/18/2021 13:53
 * company: ????????????
 * description: ??????????????????
 */
public class TrainingCampDetailActivity extends BaseActivity<TrainingCampDetailPresenter> implements
        TrainingCampDetailContract.View, IWithoutImmersionBar {

    @BindView(R.id.rv_training_look)
    RecyclerView mRvTrainingLook;
    @BindView(R.id.iv_training_top)
    ImageView mIvTrainingTop;
    @BindView(R.id.tv_training_look)
    TextView mTvTrainingLook;
    @BindView(R.id.tv_training_title)
    TextView mTvTrainingTitle;
    @BindView(R.id.tv_training_des)
    TextView mTvTrainingDes;
    @BindView(R.id.tv_training_time)
    TextView mTvTrainingTime;
    @BindView(R.id.tv_training_group)
    TextView mTvTrainingGroup;
    @BindView(R.id.stl_training)
    SlidingTabLayout mStlTraining;
    @BindView(R.id.vp_training)
    ViewPager mVpTraining;
    @BindView(R.id.tv_training_buy)
    TextView mTvTrainingBuy;
    @BindView(R.id.tv_free)
    RoundTextView mTvFree;
    @BindView(R.id.group_training_bottom)
    Group mGroupBottom;
    @BindView(R.id.group_training)
    Group mTrainingGroup;
    @BindView(R.id.tv_training_time_bottom)
    TextView mTvTrainingTimeBottom;

    @BindView(R.id.cl_group_share_save)
    ConstraintLayout mClGroupSave;
    @BindView(R.id.tv_group_save_title)
    TextView mTvGroupSaveTitle;
    @BindView(R.id.tv_group_save_desc)
    TextView mtvGroupSaveDesc;
    @BindView(R.id.iv_group_save_qrcode)
    ImageView mIvGroupSaveQrcode;

    private ArrayList<Fragment> fragments = new ArrayList<>();
    private TrainingCourseDetailFragment courseDetailFragment;
    private TrainingCatalogFragment catalogFragment;
    private DataFragment dataFragment;
    /**
     * ?????????id
     */
    private String id;
    /**
     * ???????????????
     */
    private TrainingDetailBean mDetailBean;

    private RxPermissions rxPermissions;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerTrainingCampDetailComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_training_camp_detail;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        rxPermissions = new RxPermissions(this);
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(R.color.color_333333).statusBarDarkFont(false).init();

        id = getIntent().getStringExtra(Constant.BUNDLE_TRAINING_CAMP_ID);

        courseDetailFragment = TrainingCourseDetailFragment.newInstance();
        catalogFragment = TrainingCatalogFragment.newInstance();
        dataFragment = DataFragment.newInstance(id, "2");

        if (mPresenter != null) {
            mPresenter.getDataListAndTraining(id);
        }
    }

    @Override
    public void showTitleDataAndTraining(TrainingDetailBean detailBean) {
        int dataCount = detailBean.getDataCount();
        String[] titles;
        if (dataCount > 0) {
            titles = new String[]{"??????", "??????", "?????? (" + dataCount + ")"};
            fragments.add(courseDetailFragment);
            fragments.add(catalogFragment);
            fragments.add(dataFragment);
        } else {
            titles = new String[]{"??????", "??????"};
            fragments.add(courseDetailFragment);
            fragments.add(catalogFragment);
        }
        mStlTraining.setViewPager(mVpTraining, titles, this, fragments);
        mVpTraining.setOffscreenPageLimit(titles.length);

        boolean buy = detailBean.isBuy();
        mVpTraining.setCurrentItem(buy ? 1 : 0);

        showTrainingDetail(detailBean);
    }

    @Override
    public void showTrainingDetail(TrainingDetailBean detailBean) {
        mTvTrainingGroup.setText(detailBean.getLabelName());
        this.mDetailBean = detailBean;
        if (courseDetailFragment != null) {
            courseDetailFragment.showData(detailBean);
        }
        if (catalogFragment != null) {
            catalogFragment.showCatalogData(detailBean);
        }
        if (dataFragment != null) {
            dataFragment.setBuyTraining(detailBean.isBuy());
            dataFragment.setDownLoadInfo(detailBean.getTrainingCampName(), detailBean.getTrainingCampDesc(), detailBean.getCoverImg());
        }

        AppImageLoader.loadResUrl(detailBean.getCoverImg(), mIvTrainingTop);
        List<String> photoList = detailBean.getPhotoList();
        if (photoList != null && !photoList.isEmpty() && detailBean.getNumber() != 0) {
            //??????????????????
            mTvTrainingLook.setVisibility(View.VISIBLE);
            mRvTrainingLook.setVisibility(View.VISIBLE);
            if (photoList.size() > 5) {
                photoList = photoList.subList(0, 5);
            }
            mRvTrainingLook.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            mRvTrainingLook.setAdapter(new TrainingIconAdapter(photoList));
            mTvTrainingLook.setText(String.format(Locale.getDefault(), "%d?????????", detailBean.getNumber()));
        } else {
            mRvTrainingLook.setVisibility(View.INVISIBLE);
            mTvTrainingLook.setVisibility(View.INVISIBLE);
        }
        mTvTrainingTitle.setText(detailBean.getTrainingCampName());
        mTvTrainingDes.setText(detailBean.getTrainingCampDesc());
        mTvTrainingTime.setText(String.format("???????????????%s-%s", detailBean.getCurriculumStartTime(), detailBean.getCurriculumEndTime()));
        //?????????????????????0.?????? 1.?????????
        int joinSettingType = detailBean.getJoinSettingType();
        mTrainingGroup.setVisibility(joinSettingType == 1 ? View.VISIBLE : View.GONE);
        mTvTrainingGroup.setText(detailBean.getLabelName());
        mTvGroupSaveTitle.setText(mDetailBean.getGuidanceDesc());
        mtvGroupSaveDesc.setText(mDetailBean.getCodeTitle());
        AppImageLoader.loadResUrl(mDetailBean.getCodePicture(), mIvGroupSaveQrcode);

        setBottomView();

        //?????????????????????
        EventBusManager.getInstance().post(detailBean);
    }

    /**
     * ????????????view
     */
    public void setBottomView() {
        //???????????? false ?????? true
        boolean buy = mDetailBean.isBuy();
        if (buy) {
            mGroupBottom.setVisibility(View.GONE);
            //?????????????????????????????????????????????
            try {
                String startTime = mDetailBean.getCurriculumStartTime();
                String endTime = mDetailBean.getCurriculumEndTime();
                String nowTime = mDetailBean.getNowTime();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                Date startDate = df.parse(startTime);
                Date endDate = df.parse(endTime);
                Date nowDate = df.parse(nowTime);
                if (nowDate.before(startDate)) {
                    mTvTrainingTimeBottom.setVisibility(View.VISIBLE);
                    mTvTrainingTimeBottom.setText(String.format("???????????????%s?????????????????????", startTime));
                    mTvTrainingTimeBottom.setTextColor(ContextCompat.getColor(this, R.color.color_2673FE));
                    mTvTrainingTimeBottom.setBackgroundColor(ContextCompat.getColor(this, R.color.color_F2F2F2));
                } else if (nowDate.after(endDate)) {
                    mTvTrainingTimeBottom.setVisibility(View.VISIBLE);
                    mTvTrainingTimeBottom.setText("???????????????????????????????????????");
                    mTvTrainingTimeBottom.setTextColor(ContextCompat.getColor(this, R.color.color_999999));
                    mTvTrainingTimeBottom.setBackgroundColor(ContextCompat.getColor(this, R.color.color_F2F2F2));
                } else {
                    mTvTrainingTimeBottom.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                e.printStackTrace();
                LogUtils.e("=======================" + e.toString());
            }
        } else if (mDetailBean.getWhetherStopSell() == 1) {
            // ???????????????0.??? 1.??????
            mGroupBottom.setVisibility(View.GONE);
            mTvTrainingTimeBottom.setVisibility(View.VISIBLE);
            mTvTrainingTimeBottom.setText("???????????????");
            mTvTrainingTimeBottom.setTextColor(ContextCompat.getColor(this, R.color.color_999999));
            mTvTrainingTimeBottom.setBackgroundColor(ContextCompat.getColor(this, R.color.color_F2F2F2));
        } else {
            mGroupBottom.setVisibility(View.VISIBLE);
            mTvTrainingTimeBottom.setVisibility(View.GONE);
            //???????????????1.?????? 2.?????????
            int sellType = mDetailBean.getSellType();
            if (sellType == 1) {
                mTvTrainingBuy.setText("????????????");
                mTvFree.setVisibility(View.VISIBLE);
            } else if (sellType == 2) {
                mTvTrainingBuy.setText(String.format("????????????: %s??????", String_Utils.linearNmber(mDetailBean.getSellPrice())));
                mTvFree.setVisibility(View.GONE);
            }
            //?????????????????????????????????????????????
            String startTime = mDetailBean.getRecruitStudentStartTime();
            String nowTime = mDetailBean.getNowTime();
            String endTime = mDetailBean.getRecruitStudentEndTime();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            try {
                Date startDate = df.parse(startTime);
                Date nowDate = df.parse(nowTime);
                Date endDate = df.parse(endTime);

                if (nowDate.before(startDate)) {
                    mTvTrainingBuy.setText("???????????????");
                } else if (nowDate.after(endDate)) {
                    mTvTrainingBuy.setText("???????????????");
                    mTvFree.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                e.printStackTrace();
                LogUtils.e("=======================" + e.toString());
            }
        }

        //1. ?????? 2.??????
        int upStatus = mDetailBean.getUpStatus();
        if (upStatus == 2) {
            mGroupBottom.setVisibility(View.GONE);
            mTvTrainingTimeBottom.setVisibility(View.VISIBLE);
            mTvTrainingTimeBottom.setText("?????????????????????????????????");
            mTvTrainingTimeBottom.setTextColor(ContextCompat.getColor(this, R.color.color_999999));
            mTvTrainingTimeBottom.setBackgroundColor(ContextCompat.getColor(this, R.color.color_F2F2F2));
        }
    }

    /**
     * ???????????????????????????????????????
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (mPresenter != null && !TextUtils.isEmpty(id) && !fragments.isEmpty()) {
            mPresenter.getTrainingDetail(id);
        }
    }

    @SingleClick
    @OnClick({R.id.iv_training_back, R.id.iv_training_share, R.id.tv_training_group_look, R.id.tv_training_get_info, R.id.tv_training_buy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_training_back:
                finish();
                break;
            case R.id.iv_training_share:
                if (mPresenter != null) {
                    mPresenter.getTrainingShare(id);
                }
                break;
            case R.id.tv_training_group_look:
                if (mDetailBean != null) {
                    if (mDetailBean.isBuy()) {
                        checkPermissionAndSaveBitmap();
                    } else {
                        checkBuyTime(true);
                    }
                }
                break;
            case R.id.tv_training_get_info:
                //????????????
                Intent intent = new Intent(this, MyWebViewActivity.class);
                intent.putExtra(Constant.BUNDLE_WEB_TITLE, "????????????");
                intent.putExtra(Constant.BUNDLE_WEB_URL, Constant.CUSTOMER_SERVICE_URL);
                startActivity(intent);
                break;
            case R.id.tv_training_buy:
                checkBuyTime(false);
                break;
            default:
                break;
        }
    }

    /**
     * ????????????
     *
     * @param isShowDialog ????????????dialog
     */
    public void checkBuyTime(boolean isShowDialog) {
        TrainingUtils.checkBuyTime(this, mDetailBean, isShowDialog);
    }

    /**
     * ????????????????????????
     */
    private void checkPermissionAndSaveBitmap() {
        DialogUtils.showSaveQrCode(this, mDetailBean.getCodeTitle(),
                mDetailBean.getGuidanceDesc(), mDetailBean.getCodePicture(),
                new DialogUtils.OnClickDialogListener() {
                    @Override
                    public void clickOk() {
                        saveBitmap();
                    }
                });
    }

    @SuppressLint("CheckResult")
    private void saveBitmap() {
        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) {
                        Bitmap viewBitmap = BitmapPhjtUtils.getViewBitmap(mClGroupSave);
                        BitmapPhjtUtils.bitmapToFile(TrainingCampDetailActivity.this, viewBitmap);
                    } else {
                        TipsUtil.show("????????????????????????");
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEvent(TrainingBuySuccessEvent buySuccessEvent) {
        if (buySuccessEvent != null) {
            //????????????  ????????????
            onResume();
            // ??????????????????
            if (mTrainingGroup != null && mTrainingGroup.getVisibility() == View.VISIBLE) {
                checkPermissionAndSaveBitmap();
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEvent(String text) {
        //????????????????????????????????????????????????
        if (TextUtils.equals(text, Constant.EVENT_TRAINING_CAMP)) {
            onResume();
        }
    }

    @Override
    public void showTrainingShare(ShareBean shareBean) {
        ShareUtils.showSharePop(this, shareBean);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ShareInit.getInstance().onActivityResult(this, requestCode, resultCode, data);
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
        TipsUtil.show(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArchitectUtils.startActivity(intent);
    }

}
