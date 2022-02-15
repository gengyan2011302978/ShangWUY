package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.annotate.SingleClick;
import com.phjt.shangxueyuan.app.MyApplication;
import com.phjt.shangxueyuan.bean.event.ChangeEvent;
import com.phjt.shangxueyuan.bean.model.File;
import com.phjt.shangxueyuan.di.component.DaggerDocDownloadListComponent;
import com.phjt.shangxueyuan.greendao.gen.DaoSession;
import com.phjt.shangxueyuan.greendao.gen.FileDao;
import com.phjt.shangxueyuan.mvp.contract.DocDownloadListContract;
import com.phjt.shangxueyuan.mvp.presenter.DocDownloadListPresenter;
import com.phjt.shangxueyuan.mvp.ui.adapter.DocDownloadAdapter;
import com.phjt.shangxueyuan.service.DownloadService;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phsxy.utils.NetworkUtils;
import com.phsxy.utils.ToastUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;


/**
 * @author: Created by Template
 * date: 06/09/2020 17:37
 * company: 普华集团
 * description: 模版请保持更新
 */
public class DocDownloadListActivity extends BaseActivity<DocDownloadListPresenter> implements DocDownloadListContract.View {


    @BindView(R.id.rv_download_video)
    RecyclerView rvDownloadVideo;
    @BindView(R.id.image_edit)
    TextView mImageEdit;
    @BindView(R.id.tv_delete)
    TextView mTvDelete;
    @BindView(R.id.title_commont)
    TextView titleCommont;
    DaoSession daoSession;
    DocDownloadAdapter downloadAdapter;
    List<File> learnBeanList;
    public static List<File> deleteBeanList;
    @BindView(R.id.image_back)
    ImageView imageBack;
    @BindView(R.id.relat_title)
    RelativeLayout relatTitle;
    private HashMap<String, DownloadReceiver> downloadReceiverMap = new HashMap<>();
    public static boolean isChange = false;//是否编辑

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerDocDownloadListComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_doc_download_list;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        isChange = false;
        daoSession = MyApplication.instance().getDaoSession();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvDownloadVideo.setLayoutManager(layoutManager);
        learnBeanList = new ArrayList<>();
        deleteBeanList = new ArrayList<>();
        List<File> userInfos = daoSession.getFileDao().queryBuilder().where(FileDao.Properties.FileLevel.eq(getIntent().getStringExtra("levelName")), FileDao.Properties.FileType.notEq(("video"))).build().list();
        learnBeanList = userInfos;
        getData();
        if (userInfos.size() > 0) {
            titleCommont.setText(userInfos.get(0).getFileLevel());
        }
        downloadAdapter = new DocDownloadAdapter(this, learnBeanList);
        rvDownloadVideo.setAdapter(downloadAdapter);
        downloadAdapter.setEmptyView(R.layout.empty_layout, rvDownloadVideo);
        downloadAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @SingleClick
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (!NetworkUtils.isConnected()) {
                    TipsUtil.show("暂无网络连接");
                    return;
                }
                if (view.getId() == R.id.pb_tv_item || view.getId() == R.id.tv_name_item) {
                    File file = learnBeanList.get(position);
                    Intent intent = null;
                    switch (file.getStatus()) {
                        case File.DOWNLOAD_PAUSE://暂停->开始
                            file.setSpeed("");
                            downloadAdapter.notifyItemChanged(position, R.id.pb_tv_item);
                            intent = new Intent(DocDownloadListActivity.this, DownloadService.class);
                            intent.putExtra("filename", file.getFileName());
                            intent.putExtra("url", file.getUrl());
                            intent.putExtra("id", file.getId() + "");
                            intent.putExtra("seq", file.getSeq());
                            startService(intent);
                            file.setStatus(File.DOWNLOAD_PROCEED);
                            break;
                        case File.DOWNLOAD_PROCEED://开始->暂停
                            System.out.println("开始->暂停");
//                            file.setStatus(File.DOWNLOAD_PAUSE);
                            ToastUtils.show("资料下载中，请下载完成后打开");
//                            MyApplication.downloadTaskHashMap.get(file.getId()).cancel();
                            break;
                        case File.DOWNLOAD_ERROR://出错
                            file.setStatus(File.DOWNLOAD_PROCEED);
                            file.setSpeed("---");
                            downloadAdapter.notifyItemChanged(position, R.id.pb_tv_item);
                            intent = new Intent(DocDownloadListActivity.this, DownloadService.class);
                            intent.putExtra("filename", file.getFileName());
                            intent.putExtra("url", file.getUrl());
                            intent.putExtra("id", file.getId() + "");
                            intent.putExtra("seq", file.getSeq());
                            startService(intent);
                            break;
                        case File.DOWNLOAD_COMPLETE://完成
                            intent = new Intent(DocDownloadListActivity.this, FileOpenActivity.class);
                            intent.putExtra(Constant.FILE_SAVE_PATH, file.getPath());
                            startActivity(intent);
                            break;
                        case File.DOWNLOAD_REDYA://准备下载 ->开始
                            file.setSpeed("");
                            downloadAdapter.notifyItemChanged(position, R.id.pb_tv_item);
                            intent = new Intent(DocDownloadListActivity.this, DownloadService.class);
                            intent.putExtra("filename", file.getFileName());
                            intent.putExtra("url", file.getUrl());
                            intent.putExtra("id", file.getId() + "");
                            intent.putExtra("seq", file.getSeq());
                            startService(intent);
                            file.setStatus(File.DOWNLOAD_PROCEED);
                            break;
                        default:
                            break;
                    }
                }
            }
        });
        mImageEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isChange) {
                    mImageEdit.setText("");
                    mImageEdit.setBackgroundResource(R.drawable.btn_edit_icon);
                    isChange = false;
                    deleteBeanList.clear();
                    downloadAdapter.notifyDataSetChanged();
                    mTvDelete.setVisibility(View.GONE);
                } else {
                    mImageEdit.setText("取消");
                    mImageEdit.setBackground(null);
                    isChange = true;
                    downloadAdapter.notifyDataSetChanged();
                    mTvDelete.setVisibility(View.VISIBLE);
                }

            }
        });
        mTvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deleteBeanList.size() > 0) {
                    for (int i = 0; i < deleteBeanList.size(); i++) {
                        MyApplication.instance().getDaoSession().getFileDao().delete(deleteBeanList.get(i));
                        learnBeanList.remove(deleteBeanList.get(i));
                        downloadAdapter.notifyDataSetChanged();
                    }
                    if (learnBeanList.size() == 0) {
                        mImageEdit.setText("");
                        mImageEdit.setBackgroundResource(R.drawable.btn_edit_icon);
                        mTvDelete.setVisibility(View.GONE);
                    }
                    mImageEdit.setText("");
                    mImageEdit.setBackgroundResource(R.drawable.btn_edit_icon);
                    isChange = false;
                    deleteBeanList.clear();
                    downloadAdapter.notifyDataSetChanged();
                    mTvDelete.setVisibility(View.GONE);
                } else {
                    ToastUtils.show("请选择要删除的目录");
                }
                deleteBeanList.clear();
                if (learnBeanList.size() == 0) {
                    mImageEdit.setVisibility(View.GONE);
                }
            }
        });
    }

    private void getData() {
        for (int i = 0; i < learnBeanList.size(); i++) {
            IntentFilter filter = new IntentFilter();
            DownloadReceiver receiver = new DownloadReceiver();
            filter.addAction("com.phjt.shangxueyuan" + learnBeanList.get(i).getId());
            registerReceiver(receiver, filter);
            downloadReceiverMap.put(learnBeanList.get(i).getSeq() + "", receiver);
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

    @OnClick({R.id.image_back, R.id.relat_title})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_back:
                finish();
                break;
            case R.id.relat_title:
                break;
            default:
                break;
        }
    }

    class DownloadReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            for (int i = 0; i < learnBeanList.size(); i++) {
                File file = learnBeanList.get(i);
                if (intent.getAction().equals("com.phjt.shangxueyuan" + file.getId())) {
                    String speedS = intent.getStringExtra("speed");
                    String sizeS = intent.getStringExtra("size");
                    String totalSize = intent.getStringExtra("totalSize");
                    int pencent = intent.getIntExtra("percent", 0);
                    int status = intent.getIntExtra("status", 0);
                    if (status == File.DOWNLOAD_PROCEED) {
                        file.setSpeed(speedS);
                        file.setProgress(pencent);
                        file.setSizeStr(sizeS);
                        file.setStatus(File.DOWNLOAD_PROCEED);
                    }
                    if (status == File.DOWNLOAD_COMPLETE) {//完成
                        file.setStatus(File.DOWNLOAD_COMPLETE);
                        file.setProgress(pencent);
                        file.setSizeStr(totalSize);
                        MyApplication.downloadTaskHashMap.remove(file.getId());
                    }
                    if (status == File.DOWNLOAD_ERROR) {
                        file.setStatus(File.DOWNLOAD_ERROR);
                        MyApplication.downloadTaskHashMap.remove(file.getId());
                    }
                    downloadAdapter.notifyItemChanged(i, R.id.pb_tv_item);
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEvent(ChangeEvent msgEvent) {
        if (deleteBeanList.size() == 0) {
            mTvDelete.setBackgroundResource(R.drawable.bg_eeeeee_rectangle);
        } else {
            mTvDelete.setBackgroundResource(R.drawable.bg_2076ff_rectangle);
        }
    }
}
