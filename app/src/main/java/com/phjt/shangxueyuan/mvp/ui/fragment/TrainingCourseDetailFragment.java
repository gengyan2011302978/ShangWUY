package com.phjt.shangxueyuan.mvp.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.phjt.base.base.BaseFragment;
import com.phjt.base.di.component.AppComponent;

import com.phjt.shangxueyuan.bean.TrainingDetailBean;
import com.phjt.shangxueyuan.di.component.DaggerTrainingCourseDetailComponent;
import com.phjt.shangxueyuan.mvp.contract.TrainingCourseDetailContract;
import com.phjt.shangxueyuan.mvp.presenter.TrainingCourseDetailPresenter;

import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.zzhoujay.richtext.RichText;

import butterknife.BindView;

import static com.phjt.base.utils.Preconditions.checkNotNull;


/**
 * @author: Created by GengYan
 * date: 01/19/2021 10:30
 * company: 普华集团
 * description: 模版请保持更新
 */
public class TrainingCourseDetailFragment extends BaseFragment<TrainingCourseDetailPresenter> implements TrainingCourseDetailContract.View {

    @BindView(R.id.tv_training_detail)
    TextView mTvTrainingDetail;

    public static TrainingCourseDetailFragment newInstance() {
        Bundle args = new Bundle();
        TrainingCourseDetailFragment fragment = new TrainingCourseDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerTrainingCourseDetailComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_training_course_detail, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        RichText.initCacheDir(mContext);
    }

    public void showData(TrainingDetailBean detailBean) {
        if (detailBean != null && mTvTrainingDetail != null && !TextUtils.isEmpty(detailBean.getTrainingCampDescribe())) {
            RichText.from(detailBean.getTrainingCampDescribe()).into(mTvTrainingDetail);
        }
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
        TipsUtil.show(message);
    }
}
