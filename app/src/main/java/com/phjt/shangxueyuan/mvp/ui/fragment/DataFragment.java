package com.phjt.shangxueyuan.mvp.ui.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.phjt.base.base.BaseLazyLoadFragment;
import com.phjt.base.di.component.AppComponent;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.annotate.SingleClick;
import com.phjt.shangxueyuan.app.MyApplication;
import com.phjt.shangxueyuan.bean.DataBean;
import com.phjt.shangxueyuan.bean.model.File;
import com.phjt.shangxueyuan.di.component.DaggerDataFragmentComponent;
import com.phjt.shangxueyuan.greendao.gen.DaoSession;
import com.phjt.shangxueyuan.greendao.gen.FileDao;
import com.phjt.shangxueyuan.mvp.contract.DataFragmentContract;
import com.phjt.shangxueyuan.mvp.presenter.DataFragmentPresenter;
import com.phjt.shangxueyuan.mvp.ui.activity.CourseDetailActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.ExerciseListActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.FileOpenActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.OpenVipActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.PunchClockListActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.TrainingCampDetailActivity;
import com.phjt.shangxueyuan.mvp.ui.adapter.DataAdapter;
import com.phjt.shangxueyuan.service.DownloadService;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.ConstantUmeng;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.shangxueyuan.utils.UmengUtil;
import com.phjt.shangxueyuan.widgets.dialog.DialogUtils;
import com.phsxy.utils.LogUtils;
import com.phsxy.utils.NetworkUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tencent.smtt.sdk.QbSdk;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

import static com.phjt.base.utils.Preconditions.checkNotNull;
import static com.phjt.shangxueyuan.mvp.ui.activity.CourseDetailActivity.columnName;
import static com.phjt.shangxueyuan.mvp.ui.activity.CourseDetailActivity.realPrice;


/**
 * @author: Created by GengYan
 * date: 06/05/2020 18:12
 * company: 普华集团
 * description: 课程——资料页面
 */
public class DataFragment extends BaseLazyLoadFragment<DataFragmentPresenter> implements DataFragmentContract.View, OnRefreshLoadMoreListener {

    @BindView(R.id.srf_data)
    SmartRefreshLayout mSrfData;
    @BindView(R.id.rv_data)
    RecyclerView mRvData;

    /**
     * 课程id
     */
    private String courseId;
    /**
     * 当前课程状态 1.免费；2.会员
     */
    private Integer freeType;
    /**
     * 播放权限，作用于商品兑换：1-无播放权限 2-有播放权限
     */
    private int playPermission;
    /**
     * 打开数量
     */
    private int punchCardNum;
    /**
     * 底部打开布局
     */
    private View footerView;

    private DataAdapter adapter;

    private int currentPage;
    public final int PAGE_SIZE = 10;
    private HashMap<String, DownloadReceiver> downloadReceiverMap = new HashMap<>();
    List<DataBean> dataBeanList = new ArrayList<>();

    private RxPermissions rxPermissions;
    private DaoSession daoSession;
    /**
     * 资料类型 0课程资料（默认） 1专栏资料  2训练营
     */
    private String courseType;
    private int exerciseNum;

    /**
     * 训练营是否购买
     */
    private boolean isBuyTraining;

    private String name, des, img;

    public void setDownLoadInfo(String name, String des, String img) {
        this.name = name;
        this.des = des;
        this.img = img;
    }

    public void setBuyTraining(boolean buyTraining) {
        isBuyTraining = buyTraining;
    }

    public static DataFragment newInstance(String courseId, int freeType, int playPermission, String courseType, int punchCardNum, int exerciseNum) {
        Bundle args = new Bundle();
        args.putString(Constant.BUNDLE_COURSE_ID, courseId);
        args.putString(Constant.BUNDLE_COURSE_TYPE, courseType);
        args.putInt(Constant.BUNDLE_COURSE_FREE_TYPE, freeType);
        args.putInt(Constant.BUNDLE_COURSE_PLAY_PERMISSION, playPermission);
        args.putInt(Constant.BUNDLE_COURSE_PUNCHCARDNUM, punchCardNum);
        args.putInt(Constant.BUNDLE_COURSE_EXERCISENUM, exerciseNum);
        DataFragment fragment = new DataFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static DataFragment newInstance(String courseId, String courseType) {
        Bundle args = new Bundle();
        args.putString(Constant.BUNDLE_COURSE_ID, courseId);
        args.putString(Constant.BUNDLE_COURSE_TYPE, courseType);
        DataFragment fragment = new DataFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerDataFragmentComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_data, container, false);
    }

    @SuppressLint("CheckResult")
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            courseId = bundle.getString(Constant.BUNDLE_COURSE_ID);
            courseType = bundle.getString(Constant.BUNDLE_COURSE_TYPE);
            freeType = bundle.getInt(Constant.BUNDLE_COURSE_FREE_TYPE);
            playPermission = bundle.getInt(Constant.BUNDLE_COURSE_PLAY_PERMISSION);
            punchCardNum = bundle.getInt(Constant.BUNDLE_COURSE_PUNCHCARDNUM);
            exerciseNum = bundle.getInt(Constant.BUNDLE_COURSE_EXERCISENUM);
        }

        rxPermissions = new RxPermissions(this);
        mSrfData.setOnRefreshLoadMoreListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mRvData.setLayoutManager(layoutManager);
        adapter = new DataAdapter(mContext, null);
        mRvData.setAdapter(adapter);

        footerView = LayoutInflater.from(mContext).inflate(R.layout.item_punch_the_clock_footer, null);
        adapter.setFooterView(footerView);
        ConstraintLayout clPunchClock = footerView.findViewById(R.id.cl_punch_clock);
        ConstraintLayout clExerciseClock = footerView.findViewById(R.id.cl_exercise_clock);
        TextView tvPunchClockCount = footerView.findViewById(R.id.tv_punch_clock_count);
        TextView tvExerciseClockCount = footerView.findViewById(R.id.tv_exercise_clock_count);
        CardView punchCd = footerView.findViewById(R.id.punch_cd);
        CardView exerciseCd = footerView.findViewById(R.id.exercise_cd);
        if (punchCardNum > 0 || exerciseNum > 0) {
            if (punchCardNum > 0) {
                footerView.setVisibility(View.VISIBLE);
                punchCd.setVisibility(View.VISIBLE);
                tvPunchClockCount.setText(String.format(Locale.getDefault(), "关联%d个打卡", punchCardNum));
            }
            if (exerciseNum > 0) {
                footerView.setVisibility(View.VISIBLE);
                exerciseCd.setVisibility(View.VISIBLE);
                tvExerciseClockCount.setText(String.format(Locale.getDefault(), "关联%d个作业", exerciseNum));
            }
        } else {
            footerView.setVisibility(View.GONE);
        }
        //去打卡列表
        clPunchClock.setOnClickListener(v -> permissionCheck(1, null, 0));
        clExerciseClock.setOnClickListener(v -> permissionCheck(2, null, 0));


        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @SingleClick
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                DataBean dataBean = (DataBean) adapter.getData().get(position);
                if (view.getId() == R.id.tv_name_item) {
                    if (dataBean.getDownloadStatus() == File.DOWNLOAD_COMPLETE) {
                        //去打开文件页面
                        String fileSavePath = dataBean.getFileSavePath();
                        Intent intent = new Intent(mContext, FileOpenActivity.class);
                        intent.putExtra(Constant.FILE_SAVE_PATH, fileSavePath);
                        DataFragment.this.startActivity(intent);
                        UmengUtil.umengCount(DataFragment.this.getActivity(), ConstantUmeng.COURSE_CLICK_DOWNLOAD);
                    }
                } else {
                    rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            .subscribe(granted -> {
                                if (granted) {
                                    permissionCheck(3, dataBean, position);
                                } else {
                                    TipsUtil.show("请先同意存储权限");
                                }
                            });
                }

                adapter.notifyItemChanged(position, R.id.pb_tv_item);
            }
        });
    }

    /**
     * 权限判断
     *
     * @param type     1、打卡 2、练习  3、资料下载
     * @param dataBean 资料实体      type为2时使用
     * @param position 资料position  type为2时使用
     */
    private void permissionCheck(int type, DataBean dataBean, int position) {
        if (TextUtils.equals(courseType, "2")) {
            //训练营
            if (isBuyTraining) {
                DataFragment.this.downLoad(dataBean, position);
            } else {
                if (getActivity() instanceof TrainingCampDetailActivity) {
                    TrainingCampDetailActivity campDetailActivity = (TrainingCampDetailActivity) getActivity();
                    campDetailActivity.checkBuyTime(true);
                }
            }
        } else if (freeType == 1 || CourseDetailActivity.playPermission == 2) {
            if (type == 1) {
                Intent intent = new Intent(mContext, PunchClockListActivity.class);
                intent.putExtra(Constant.BUNDLE_COURSE_ID, courseId);
                intent.putExtra(Constant.BUNDLE_COURSE_TYPE, courseType);
                startActivity(intent);
            } else if (type == 2) {
                Intent intent = new Intent(mContext, ExerciseListActivity.class);
                intent.putExtra(Constant.BUNDLE_COURSE_ID, courseId);
                intent.putExtra(Constant.BUNDLE_COURSE_TYPE, courseType);
                startActivity(intent);
            } else {
                DataFragment.this.downLoad(dataBean, position);
            }
        } else {
            if ("1".equals(courseType)) {
                DialogUtils.SubscribeDialog(getActivity(), new DialogUtils.OnCancelSureClick() {
                    @SuppressLint("CheckResult")
                    @Override
                    public void clickSure() {
                        Intent intent1 = new Intent(getActivity(), OpenVipActivity.class);
                        intent1.putExtra(Constant.BUNDLE_ORDER_COMMODITYID, courseId);
                        intent1.putExtra(Constant.BUNDLE_ORDER_ACTIVITYSTATE, "1");
                        intent1.putExtra(Constant.BUNDLE_ORDER_NAME, columnName);
                        intent1.putExtra(Constant.BUNDLE_ORDER_REALPRICE, realPrice);
                        intent1.putExtra(Constant.BUNDLE_ORDER_TYPE, 2);
                        startActivity(intent1);
                    }
                });
            } else {
                DialogUtils.showVipDialog(mContext);
            }
        }
    }

    /**
     * 下载资料
     */
    private void downLoad(DataBean dataBean, int position) {
        Intent intent = null;
        if (dataBean.getDownloadStatus() == File.DOWNLOAD_COMPLETE) {
            //去打开文件页面
            String fileSavePath = dataBean.getFileSavePath();
            intent = new Intent(mContext, FileOpenActivity.class);
            intent.putExtra(Constant.FILE_SAVE_PATH, fileSavePath);
            startActivity(intent);
        } else if (!NetworkUtils.isConnected()) {
            TipsUtil.show("暂无网络连接");
        } else if (dataBean.getDownloadStatus() == 999) {

            List<File> fileList = null;
            if (daoSession != null) {
                fileList = daoSession.getFileDao().queryBuilder()
                        .where(FileDao.Properties.Id.eq(Long.parseLong(dataBean.getId()))).list();
            }
            if (fileList != null && !fileList.isEmpty()) {
                return;
            }

            File file = new File();
            file.setId(Long.parseLong(dataBean.getId()));
            file.setSeq(Integer.parseInt(dataBean.getId()));
            file.setFileName(dataBean.getRealName());
            file.setCreateTime(new Date(System.currentTimeMillis()));
            if (TextUtils.equals(courseType, "2")) {
                file.setFileLevel(name);
                file.setFileLevelDesc(des);
                file.setFileLevelUrl(img);
            } else {
                file.setFileLevel(CourseDetailActivity.level_name);
                file.setFileLevelUrl(CourseDetailActivity.level_url);
                file.setFileLevelDesc(CourseDetailActivity.level_desc);
            }
            file.setProgress(0);
            file.setStatus(File.DOWNLOAD_PROCEED);
            file.setSize((long) 0);
            file.setFileType(dataBeanList.get(position).getFileType());
            file.setUrl(dataBean.getFileUrl());
            intent = new Intent(getActivity(), DownloadService.class);
            intent.putExtra("filename", dataBean.getRealName());
            intent.putExtra("level_name", file.getFileLevel());
            intent.putExtra("level_url", file.getFileLevelUrl());
            intent.putExtra("level_desc", file.getFileLevelDesc());
            intent.putExtra("url", file.getUrl());
            intent.putExtra("id", String.valueOf(file.getId()));
            intent.putExtra("seq", file.getSeq());
            FileDao fileDao = MyApplication.instance().getDaoSession().getFileDao();
            fileDao.insert(file);

            IntentFilter filter = new IntentFilter();
            DownloadReceiver receiver = new DownloadReceiver();
            filter.addAction("com.phjt.shangxueyuan" + dataBean.getId());
            getActivity().registerReceiver(receiver, filter);
            downloadReceiverMap.put(dataBean.getId() + "", receiver);
            TipsUtil.showTips("已加入下载，可在【我的下载】查看");
            QbSdk.initX5Environment(getActivity(), new QbSdk.PreInitCallback() {
                @Override
                public void onCoreInitFinished() {
                    LogUtils.e("=====onCoreInitFinished");
                }

                @Override
                public void onViewInitFinished(boolean b) {
                    //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                    LogUtils.e("=====X5InitFinished" + b);
                }
            });
            getActivity().startService(intent);
        } else if (dataBean.getDownloadStatus() == File.DOWNLOAD_REDYA || dataBean.getDownloadStatus() == File.DOWNLOAD_PAUSE) {
            File file = MyApplication.instance().getDaoSession().getFileDao().queryBuilder().where(FileDao.Properties.Url.eq(dataBean.getFileUrl())).build().list().get(0);
            intent = new Intent(getActivity(), DownloadService.class);
            intent.putExtra("filename", file.getFileName());
            intent.putExtra("url", file.getUrl());
            intent.putExtra("id", String.valueOf(file.getId()));
            getActivity().startService(intent);
            file.setStatus(File.DOWNLOAD_PROCEED);
        } else if (dataBean.getDownloadStatus() == File.DOWNLOAD_PROCEED) {
            TipsUtil.showTips("文档下载中请稍后...");
            File file = MyApplication.instance().getDaoSession().getFileDao().queryBuilder().where(FileDao.Properties.Url.eq(dataBean.getFileUrl())).build().list().get(0);
            intent = new Intent(getActivity(), DownloadService.class);
            intent.putExtra("filename", file.getFileName());
            intent.putExtra("url", file.getUrl());
            intent.putExtra("id", String.valueOf(file.getId()));
            getActivity().startService(intent);
            file.setStatus(File.DOWNLOAD_PROCEED);
        } else {
            File file = MyApplication.instance().getDaoSession().getFileDao().queryBuilder().where(FileDao.Properties.Url.eq(dataBean.getFileUrl())).build().list().get(0);
            file.setStatus(File.DOWNLOAD_PAUSE);
            if (null != MyApplication.downloadTaskHashMap.get(String.valueOf(file.getId()))) {
                MyApplication.downloadTaskHashMap.get(String.valueOf(file.getId())).cancel();
            }
        }
    }

    @Override
    public void lazyLoadData() {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (mPresenter != null) {
                mSrfData.autoRefresh();
            }
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        if (mPresenter != null) {
            currentPage = 1;
            mPresenter.getDataList(courseId, currentPage, PAGE_SIZE, true, courseType);
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (mPresenter != null) {
            currentPage += 1;
            mPresenter.getDataList(courseId, currentPage, PAGE_SIZE, false, courseType);
        }
    }

    @Override
    public void showDataListRefresh(List<DataBean> dataBeans) {
        dataBeanList = resetDataDownload(dataBeans);
        adapter.setNewData(dataBeanList);
    }

    @Override
    public void showDataListLoadMore(List<DataBean> dataBeans) {
        dataBeanList.addAll(resetDataDownload(dataBeans));
        adapter.addData(resetDataDownload(dataBeans));
    }

    @Override
    public void showDataCount(int num) {
        if (getActivity() instanceof CourseDetailActivity) {
            CourseDetailActivity activity = (CourseDetailActivity) getActivity();
            activity.setTitleCount(2, num);
        }
    }

    /**
     * 查询数据库中是否下载过
     */
    public List<DataBean> resetDataDownload(List<DataBean> dataBeans) {
        daoSession = MyApplication.instance().getDaoSession();
        for (int i = 0; i < dataBeans.size(); i++) {
            DataBean dataBean = dataBeans.get(i);
            List<File> fileList = null;
            if (daoSession != null) {
                fileList = daoSession.getFileDao().queryBuilder()
                        .where(FileDao.Properties.Id.eq(Long.parseLong(dataBean.getId()))).list();
            }
            if (fileList != null && !fileList.isEmpty()) {
                dataBean.setDownloadStatus(fileList.get(0).getStatus());
                dataBean.setFileSavePath(fileList.get(0).getPath());
            } else {
                dataBean.setDownloadStatus(999);
            }
        }
        return dataBeans;
    }

    class DownloadReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            for (int i = 0; i < dataBeanList.size(); i++) {
                DataBean file = dataBeanList.get(i);
                if (("com.phjt.shangxueyuan" + file.getId()).equals(intent.getAction())) {
                    int status = intent.getIntExtra("status", 0);
                    file.setDownloadStatus(status);
                    adapter.notifyItemChanged(i, R.id.pb_tv_item);
                    resetDataDownload(adapter.getData());
                }
            }
        }
    }

    @Override
    public void canLoadMore() {
        if (mSrfData != null) {
            mSrfData.setEnableLoadMore(true);
        }
    }

    @Override
    public void cannotLoadMore() {
        if (mSrfData != null) {
            mSrfData.setEnableLoadMore(false);
        }
    }

    @Override
    public void stopRefreshAndLoadMore() {
        if (mSrfData != null) {
            mSrfData.finishRefresh();
            mSrfData.finishLoadMore();
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        stopRefreshAndLoadMore();
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        TipsUtil.showTips(message);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (downloadReceiverMap != null && downloadReceiverMap.size() > 0) {
            for (String key : downloadReceiverMap.keySet()) {
                if (getActivity() != null) {
                    getActivity().unregisterReceiver(downloadReceiverMap.get(key));
                }
            }
            downloadReceiverMap.clear();
        }
    }
}
