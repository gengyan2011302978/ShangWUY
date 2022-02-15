package com.phjt.shangxueyuan.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.phjt.base.base.BaseLazyLoadFragment;
import com.phjt.base.di.component.AppComponent;
import com.phjt.base.utils.ArchitectUtils;

import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.annotate.VipStateCheck;
import com.phjt.shangxueyuan.bean.CourseCatalogSecondBean;
import com.phjt.shangxueyuan.bean.MyNotesBean;
import com.phjt.shangxueyuan.bean.event.EventBean;
import com.phjt.shangxueyuan.di.component.DaggerNoteComponent;
import com.phjt.shangxueyuan.mvp.contract.NoteContract;
import com.phjt.shangxueyuan.mvp.presenter.NotePresenter;
import com.phjt.shangxueyuan.mvp.ui.activity.CourseDetailActivity;
import com.phjt.shangxueyuan.mvp.ui.activity.OpenVipActivity;
import com.phjt.shangxueyuan.mvp.ui.adapter.MyHomeAdapter;
import com.phjt.shangxueyuan.utils.Constant;
import com.phjt.shangxueyuan.utils.TipsUtil;
import com.phjt.shangxueyuan.widgets.dialog.DialogUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

import static com.phjt.base.utils.Preconditions.checkNotNull;
import static com.phjt.shangxueyuan.mvp.ui.activity.CourseDetailActivity.columnName;
import static com.phjt.shangxueyuan.mvp.ui.fragment.NotesEditingFragment.BUNDLE_NOTES_EDITING;
import static com.phjt.shangxueyuan.mvp.ui.fragment.NotesEditingFragment.BUNDLE_TYPE;
import static com.phjt.shangxueyuan.mvp.ui.fragment.NotesEditingFragment.CURRENT_TIME;


/**
 * Created by Template
 *
 * @author :笔记
 * description :
 * company     : Copyright (c) 2019 普华集团 All rights reserved
 * @date : 2020/06/04 10:53
 */

public class NoteFragment extends BaseLazyLoadFragment<NotePresenter> implements NoteContract.View {

    @BindView(R.id.tab_course_detail)
    SlidingTabLayout tabCourseDetail;
    @BindView(R.id.tv_new_notes)
    TextView tvNewNotes;
    @BindView(R.id.vp_course_detail)
    ViewPager vpCourseDetail;

    /**
     * 课程id
     */
    private String courseId;
    /**
     * 当前课程状态 1.免费；2.会员
     */
    private int freeType;
    /**
     * 笔记数量
     */
    private int noteNum;

    public static final String COURSE_FREE_TYPE = "course_free_type";
    public static final String COURSE_VIP_STATES = "course_vip_states";
    public static final String COURSE_NOTE_NUM = "course_note_num";

    private CurriculumNoteFragment curriculumNoteFragment, curriculumNoteFragment2;

    /**
     * 0.普通用户；1.普通vip；2.永久vip；3.vip已过
     */
    private int vipState;
    /**
     * 小节id
     */
    private String pointId;
    private NotesEditingFragment mNotesEditingFragment;
    private FragmentManager fragmentManager;
    private long currentTime;
    private String courseType;

    public static NoteFragment newInstance(String courseId, String pointId, int freeType, int vipStates, int noteNum, String courseType) {
        Bundle args = new Bundle();
        args.putString(Constant.BUNDLE_COURSE_ID, courseId);
        args.putString(Constant.BUNDLE_POINT_ID, pointId);
        args.putString(Constant.BUNDLE_COURSE_TYPE, courseType);
        args.putInt(COURSE_FREE_TYPE, freeType);
        args.putInt(COURSE_VIP_STATES, vipStates);
        args.putInt(COURSE_NOTE_NUM, noteNum);
        NoteFragment fragment = new NoteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerNoteComponent //Dagger 编译时生成代码,报错先请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        fragmentManager = getChildFragmentManager();
        Bundle bundle = getArguments();
        if (bundle != null) {
            courseId = bundle.getString(Constant.BUNDLE_COURSE_ID);
            pointId = bundle.getString(Constant.BUNDLE_POINT_ID);
            courseType = bundle.getString(Constant.BUNDLE_COURSE_TYPE);
            freeType = bundle.getInt(COURSE_FREE_TYPE);
            vipState = bundle.getInt(COURSE_VIP_STATES);
            noteNum = bundle.getInt(COURSE_NOTE_NUM);
        }
        //0:我的笔记 1:所有笔记
        String[] titles = {"所有笔记", "我的笔记"};
        ArrayList<Fragment> fragments = new ArrayList<>();
        curriculumNoteFragment = CurriculumNoteFragment.newInstance(courseId, pointId, freeType, vipState, 1, courseType);
        curriculumNoteFragment2 = CurriculumNoteFragment.newInstance(courseId, pointId, freeType, vipState, 0, courseType);
        fragments.add(curriculumNoteFragment);
        fragments.add(curriculumNoteFragment2);
        FragmentManager fragmentManager = getChildFragmentManager();

        MyHomeAdapter adapter = new MyHomeAdapter(fragmentManager, fragments);
        vpCourseDetail.setAdapter(adapter);
        vpCourseDetail.setOffscreenPageLimit(1);
        vpCourseDetail.setCurrentItem(0);
        tabCourseDetail.setViewPager(vpCourseDetail, titles);

        vpCourseDetail.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                if (0 == i && null != curriculumNoteFragment) {
                    curriculumNoteFragment.getPage(1, pointId);
                }
                if (1 == i && null != curriculumNoteFragment2) {
                    curriculumNoteFragment2.getPage(0, pointId);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    public void lazyLoadData() {

    }

    /**
     * 设置Vip状态
     *
     * @param vipStates
     */
    public void setVipState(int vipStates) {
        vipState = vipStates;
    }

    public void getPointId(String pointIds) {
        pointId = pointIds;
    }

    @OnClick(R.id.tv_new_notes)
    public void onClick() {
        if ("1".equals(courseType) && CourseDetailActivity.playPermission == 1) {
            DialogUtils.SubscribeDialog(getActivity(), new DialogUtils.OnCancelSureClick() {
                @Override
                public void clickSure() {
                    Intent intent1 = new Intent(getActivity(), OpenVipActivity.class);
                    intent1.putExtra(Constant.BUNDLE_ORDER_COMMODITYID, courseId);
                    intent1.putExtra(Constant.BUNDLE_ORDER_ACTIVITYSTATE, "1");
                    intent1.putExtra(Constant.BUNDLE_ORDER_NAME, columnName);
                    intent1.putExtra(Constant.BUNDLE_ORDER_REALPRICE, CourseDetailActivity.realPrice);
                    intent1.putExtra(Constant.BUNDLE_ORDER_TYPE, 2);
                    startActivity(intent1);
                }
            });

        } else if (CourseDetailActivity.playPermission == 2) {
            toEditPage();
        } else if (freeType == 2) {
            checkCourseVipState();
        } else {
            toEditPage();
        }
    }

    @VipStateCheck
    public void checkCourseVipState() {
        toEditPage();
    }

    /**
     * 新增笔记
     */
    public void toEditPage() {
        if (null == mNotesEditingFragment) {
            mNotesEditingFragment = new NotesEditingFragment();
        }
        if (!mNotesEditingFragment.isAdded()) {
            FragmentActivity activity = getActivity();
            if (activity instanceof CourseDetailActivity) {
                CourseDetailActivity mActivitys = (CourseDetailActivity) activity;
                mActivitys.stopPlay();
                currentTime = mActivitys.getCurrentTime();
            }
            Bundle bundle = new Bundle();
            bundle.putString(Constant.BUNDLE_POINT_ID, pointId);
            bundle.putString(Constant.BUNDLE_COURSE_ID, courseId);
            bundle.putString(Constant.BUNDLE_COURSE_TYPE, courseType);
            bundle.putInt(BUNDLE_TYPE, 0);
            bundle.putLong(CURRENT_TIME, currentTime);
            mNotesEditingFragment.setArguments(bundle);
            mNotesEditingFragment.show(fragmentManager, "note_dialog");
        }
    }

    /**
     * 编辑笔记
     */
    public void toEditPage(int pointId, int courseId, long currentTime, MyNotesBean myNotesBean) {
        if (null == mNotesEditingFragment) {
            mNotesEditingFragment = new NotesEditingFragment();
        }
        if (!mNotesEditingFragment.isAdded()) {
            Bundle bundle = new Bundle();
            bundle.putString(Constant.BUNDLE_POINT_ID, "" + pointId);
            bundle.putString(Constant.BUNDLE_COURSE_ID, "" + courseId);
            bundle.putString(Constant.BUNDLE_COURSE_TYPE, courseType);
            bundle.putLong(CURRENT_TIME, currentTime);
            bundle.putInt(BUNDLE_TYPE, 1);
            bundle.putSerializable(BUNDLE_NOTES_EDITING, myNotesBean);
            mNotesEditingFragment.setArguments(bundle);
            mNotesEditingFragment.show(fragmentManager, "update_dialog");
            FragmentActivity activity = getActivity();
            if (activity instanceof CourseDetailActivity) {
                CourseDetailActivity activitys = (CourseDetailActivity) activity;
                activitys.stopPlay();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment addFragment = getChildFragmentManager().findFragmentByTag("note_dialog");
        if (addFragment != null) {
            addFragment.onActivityResult(requestCode, resultCode, data);
        }
        Fragment updateFragment = getChildFragmentManager().findFragmentByTag("update_dialog");
        if (updateFragment != null) {
            updateFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEvent(CourseCatalogSecondBean secondBean) {
        pointId = secondBean.getPointId();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventBean(EventBean eventBean) {
        if (eventBean != null) {
            int type = eventBean.getType();
            if (type == EventBean.NOTES_REVIEW_PUBLIC) {
                if (getActivity() instanceof CourseDetailActivity) {
                    CourseDetailActivity activity = (CourseDetailActivity) getActivity();
                    noteNum += 1;
                    activity.setTitleCount(3, noteNum);
                }
            }
        }
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
        checkNotNull(message);
        TipsUtil.show(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArchitectUtils.startActivity(intent);
    }
}
