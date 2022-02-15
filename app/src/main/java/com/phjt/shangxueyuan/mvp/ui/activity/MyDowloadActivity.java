package com.phjt.shangxueyuan.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.tablayout.SlidingTabLayout;
import com.phjt.base.base.BaseActivity;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.app.MyApplication;
import com.phjt.shangxueyuan.bean.event.ChangeEvent;
import com.phjt.shangxueyuan.bean.event.ChangeInfoEvent;
import com.phjt.shangxueyuan.di.component.DaggerMyDowloadComponent;
import com.phjt.shangxueyuan.greendao.gen.DaoSession;
import com.phjt.shangxueyuan.mvp.contract.MyDowloadContract;
import com.phjt.shangxueyuan.mvp.presenter.MyDowloadPresenter;
import com.phjt.shangxueyuan.mvp.ui.adapter.MyDownloadAdapter;
import com.phjt.shangxueyuan.mvp.ui.fragment.DocDownloadFragment;
import com.phjt.shangxueyuan.mvp.ui.fragment.VideoDownloadFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;


/**
 * @author: Created by Template
 * date: 06/02/2020 10:34
 * company: 普华集团
 * description: 模版请保持更新
 */
public class MyDowloadActivity extends BaseActivity<MyDowloadPresenter> implements MyDowloadContract.View {

    @BindView(R.id.stl_study)
    SlidingTabLayout stlStudy;
    @BindView(R.id.image_back)
    ImageView imageBack;
    @BindView(R.id.image_edit)
    TextView imageEdit;
    @BindView(R.id.vp_study)
    ViewPager vpStudy;

    private VideoDownloadFragment videoDownloadFragment;
    private DocDownloadFragment docDownloadFragment;
    DaoSession daoSession;
    public static boolean isChange = false;//是否编辑

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMyDowloadComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_my_dowload;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        String[] titles = {"视频", "文档"};
        isChange = false;
        daoSession = MyApplication.instance().getDaoSession();
        FragmentManager fragmentManager = getSupportFragmentManager();
        ArrayList<Fragment> fragments = new ArrayList<>();
        videoDownloadFragment = VideoDownloadFragment.newInstance();
        docDownloadFragment = DocDownloadFragment.newInstance();
        fragments.add(videoDownloadFragment);
        fragments.add(docDownloadFragment);
        MyDownloadAdapter myDownloadAdapter = new MyDownloadAdapter(fragmentManager, fragments);
        vpStudy.setAdapter(myDownloadAdapter);
        vpStudy.setCurrentItem(0);
        vpStudy.setOffscreenPageLimit(2);
        stlStudy.setViewPager(vpStudy, titles);
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
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArchitectUtils.startActivity(intent);
    }

    @OnClick({R.id.image_back, R.id.image_edit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_back:
                finish();
                break;
            case R.id.image_edit:
                if (isChange) {
                    imageEdit.setText("");
                    imageEdit.setBackgroundResource(R.drawable.btn_edit_icon);
                    isChange = false;
                    EventBus.getDefault().post(new ChangeEvent(2));
                } else {
                    imageEdit.setText("取消");
                    imageEdit.setBackground(null);
                    isChange = true;
                    EventBus.getDefault().post(new ChangeEvent(1));
                }
                break;
            default:
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEvent(ChangeInfoEvent msgEvent) {
        if (msgEvent.getType() == 1) {
            imageEdit.setVisibility(View.VISIBLE);
            imageEdit.setText("");
            imageEdit.setBackgroundResource(R.drawable.btn_edit_icon);
            isChange = false;
            EventBus.getDefault().post(new ChangeEvent(2));
        } else if (msgEvent.getType() == 3) {
            imageEdit.setVisibility(View.GONE);
        }
    }
}
