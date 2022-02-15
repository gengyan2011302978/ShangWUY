package com.phjt.shangxueyuan.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.phjt.base.base.BaseFragment;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.app.MyApplication;
import com.phjt.shangxueyuan.bean.FileLevelBean;
import com.phjt.shangxueyuan.bean.event.ChangeEvent;
import com.phjt.shangxueyuan.bean.event.ChangeInfoEvent;
import com.phjt.shangxueyuan.bean.model.File;
import com.phjt.shangxueyuan.di.component.DaggerVideoDownloadComponent;
import com.phjt.shangxueyuan.greendao.gen.DaoSession;
import com.phjt.shangxueyuan.greendao.gen.FileDao;
import com.phjt.shangxueyuan.mvp.contract.VideoDownloadContract;
import com.phjt.shangxueyuan.mvp.presenter.VideoDownloadPresenter;
import com.phjt.shangxueyuan.mvp.ui.activity.VideoDownloadListActivity;
import com.phjt.shangxueyuan.mvp.ui.adapter.DownloadAdapter;
import com.phjt.shangxueyuan.service.DownLoadCacheService;
import com.phsxy.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

import static com.phjt.base.utils.Preconditions.checkNotNull;
import static com.phjt.shangxueyuan.mvp.ui.activity.MyDowloadActivity.isChange;


/**
 * @author: Created by Template
 * date: 06/03/2020 17:45
 * company: 普华集团
 * description: 模版请保持更新
 */
public class VideoDownloadFragment extends BaseFragment<VideoDownloadPresenter> implements VideoDownloadContract.View {

    @BindView(R.id.rv_download_video)
    RecyclerView rvDownloadVideo;

    DaoSession daoSession;
    DownloadAdapter downloadAdapter;
    List<FileLevelBean> learnBeanList;
    public static List<FileLevelBean> mDeleteVideoBeanList;
    FileDao fileDao = MyApplication.instance().getDaoSession().getFileDao();
    @BindView(R.id.tv_delete)
    TextView tvDelete;


    public static VideoDownloadFragment newInstance() {
        VideoDownloadFragment fragment = new VideoDownloadFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerVideoDownloadComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video_download, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mDeleteVideoBeanList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rvDownloadVideo.setLayoutManager(layoutManager);

        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDeleteVideoBeanList.size() > 0) {
                    for (int i = 0; i < mDeleteVideoBeanList.size(); i++) {
                        List<File> files = MyApplication.instance().getDaoSession().getFileDao().queryBuilder().where(FileDao.Properties.FileLevel.eq(mDeleteVideoBeanList.get(i).getActivityName()),FileDao.Properties.FileType.eq("video")).build().list();
                        for (int j = 0; j < files.size(); j++) {
                            DownLoadCacheService.txDeleteDownloadVideo(files.get(j));
                            fileDao.deleteByKey(files.get(j).getId());
                        }
                        learnBeanList.remove(mDeleteVideoBeanList.get(i));
                    }
                    downloadAdapter.notifyDataSetChanged();
                    mDeleteVideoBeanList.clear();
                    tvDelete.setVisibility(View.GONE);
                    isChange = false;
                    if (learnBeanList.size()==0){
                        EventBus.getDefault().post(new ChangeInfoEvent(3));
                    }else {
                        EventBus.getDefault().post(new ChangeInfoEvent(1));
                    }
                } else {
                    ToastUtils.show("请选择要删除的目录");
                }
                changeStatus();
            }
        });
    }
    void changeStatus() {
        List<File> userInfos = daoSession.getFileDao().queryBuilder().where(FileDao.Properties.FileType.eq("video")).build().list();
        if (learnBeanList.size()==0){
            tvDelete.setVisibility(View.GONE);
            EventBus.getDefault().post(new ChangeInfoEvent(3));
        }
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            daoSession = MyApplication.instance().getDaoSession();
            List<File> userInfos = daoSession.getFileDao().queryBuilder().where(FileDao.Properties.FileType.eq("video")).build().list();
            if (userInfos.size()>0){
                EventBus.getDefault().post(new ChangeInfoEvent(1));
            }else {
                EventBus.getDefault().post(new ChangeInfoEvent(3));
//                tvDelete.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        daoSession = MyApplication.instance().getDaoSession();
        learnBeanList = new ArrayList<>();
        List<File> userInfos = daoSession.getFileDao().queryBuilder().where(FileDao.Properties.FileType.eq("video")).orderDesc(FileDao.Properties.CreateTime).build().list();
        List<FileLevelBean> fileLevelBeans = new ArrayList<>();
        List<FileLevelBean> fileLevelBeans1 = new ArrayList<>();
        if (userInfos.size()==0){
            tvDelete.setVisibility(View.GONE);
        }
        for (int i = 0; i < userInfos.size(); i++) {
            List<File> userInfos1 = daoSession.getFileDao().queryBuilder().where(FileDao.Properties.FileLevel.eq(userInfos.get(i).getFileLevel()), FileDao.Properties.FileType.eq("video")).build().list();
            int count = 0;
            long countT = 0;
            for (int j = 0; j < userInfos1.size(); j++) {
                if (userInfos1.get(j).getStatus() == 1 || userInfos1.get(j).getStatus() == 2) {
                    count++;
                }
                countT = countT + userInfos1.get(j).getSize();
            }
            FileLevelBean fileLevelBean = new FileLevelBean();
            fileLevelBean.setActivityDesc(userInfos.get(i).getFileLevelDesc());
            fileLevelBean.setActivityName(userInfos.get(i).getFileLevel());
            fileLevelBean.setActivityUrl(userInfos.get(i).getFileLevelUrl());
            fileLevelBean.setActivityStatus(count);
            fileLevelBean.setActivityTotal(userInfos1.size());
            fileLevelBean.setActivityTotalSize(countT);
            fileLevelBeans.add(fileLevelBean);
        }
        for (int i = 0; i < fileLevelBeans.size(); i++) {
            if (fileLevelBeans1.size() == 0) {
                fileLevelBeans1.add(fileLevelBeans.get(i));
            }
            for (int k = 0; k < fileLevelBeans1.size(); k++) {
                if (fileLevelBeans1.get(k).getActivityName().equals(fileLevelBeans.get(i).getActivityName())) {
                    fileLevelBeans1.remove(k);
                    k= 0;
                }
            }
            fileLevelBeans1.add(fileLevelBeans.get(i));
        }
        learnBeanList = fileLevelBeans1;
        downloadAdapter = new DownloadAdapter(mContext, learnBeanList);
        rvDownloadVideo.setAdapter(downloadAdapter);
        downloadAdapter.setEmptyView(R.layout.empty_layout, rvDownloadVideo);
        downloadAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), VideoDownloadListActivity.class);
                intent.putExtra("levelName", learnBeanList.get(position).getActivityName());
                startActivity(intent);
            }
        });
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void setData(@Nullable Object data) {

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEvent(ChangeEvent msgEvent) {
        downloadAdapter.notifyDataSetChanged();
        if (msgEvent.getType() == 1) {
            if (learnBeanList.size()==0){
                tvDelete.setVisibility(View.GONE);
            }else {
                tvDelete.setVisibility(View.VISIBLE);
            }
        } else {
            tvDelete.setVisibility(View.GONE);
        }
    }
}
