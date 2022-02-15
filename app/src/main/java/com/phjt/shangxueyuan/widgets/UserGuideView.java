package com.phjt.shangxueyuan.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.utils.Constant;
import com.phsxy.utils.SPUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author: anjun
 * date:2020/6/23 11:19
 * company: 普华集团
 * description:
 */
public class UserGuideView extends RelativeLayout {

    @BindView(R.id.view_guide_three)
    ImageView viewGuideThree;
    @BindView(R.id.view_guide_four)
    ImageView viewGuideFour;
    @BindView(R.id.ll_icons)
    LinearLayout llIcons;
    @BindView(R.id.ll_iconst)
    LinearLayout llIconst;
    @BindView(R.id.iv_number)
    ImageView ivNumber;
    @BindView(R.id.iv_goal)
    ImageView ivGoal;
    @BindView(R.id.iv_china)
    ImageView ivChina;
    @BindView(R.id.iv_i_know)
    ImageView ivIKnow;
    private View view;
    @BindView(R.id.relat_all)
    RelativeLayout relatAll;

    /**
     * guide index0 default show
     */
    @BindView(R.id.iv_master_top)
    ImageView tvMasterTop;
    @BindView(R.id.iv_master)
    ImageView ivMaster;
    @BindView(R.id.iv_bottom_elite)
    ImageView ivBottomElite;
    @BindView(R.id.iv_bottom_elite_top)
    ImageView ivBottomEliteTop;

    /**
     * guide index1 show
     */
    @BindView(R.id.iv_practice)
    ImageView ivPractice;
    @BindView(R.id.iv_practice_top)
    ImageView ivPracticeTop;
    @BindView(R.id.iv_bottom_study)
    ImageView ivBottomStudy;
    @BindView(R.id.iv_bottom_study_top)
    ImageView ivBottomStudyTop;

    /**
     * guide index2 show
     */
    @BindView(R.id.tv_boc_course)
    TextView tvBocCourse;
    @BindView(R.id.iv_project_top)
    ImageView ivProjectTop;
    @BindView(R.id.iv_bottom_circle_top)
    ImageView ivBottomCircleTop;
    @BindView(R.id.iv_bottom_circle)
    ImageView ivBottomCircle;

    /**
     * guide index3 show
     */
    @BindView(R.id.tv_live_playback)
    TextView tvLivePlayback;
    @BindView(R.id.iv_live_playback_top)
    ImageView mIvLivePlayBackTop;
    @BindView(R.id.iv_information_operations_officer)
    ImageView mIvOperationsOfficer;
    @BindView(R.id.iv_information_operations_officer_top)
    ImageView mIvOperationsOfficerTop;

    /**
     * guide index4 show
     */
    @BindView(R.id.tv_digital_economy)
    TextView tvDigitalEconomy;
    @BindView(R.id.iv_digital_top)
    ImageView ivDigitalTop;
    @BindView(R.id.iv_architect)
    ImageView ivArchitect;
    @BindView(R.id.iv_architect_top)
    ImageView ivArchitectTop;

    /**
     * guide index5 show
     */
    @BindView(R.id.tv_passageway)
    TextView tvPassageway;
    @BindView(R.id.iv_passageway_top)
    ImageView ivPassagewayTop;

    private int index = 0;
    private Unbinder unbinder;

    public UserGuideView(Context context) {
        super(context);
        init(context);
    }

    public UserGuideView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public UserGuideView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.view_user_guide, this, true);
        unbinder = ButterKnife.bind(this, view);
    }

    @OnClick(R.id.iv_i_know)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_i_know:
                index++;
                viewShow(index == 0, tvMasterTop, ivPractice,ivBottomElite, ivBottomEliteTop);
                viewShow(index == 1, ivMaster, ivPracticeTop, ivBottomStudy, ivBottomStudyTop);
                viewShow(index == 2,  ivProjectTop,  viewGuideThree,viewGuideFour,mIvLivePlayBackTop);
                viewShow(index == 3, tvBocCourse,ivBottomCircleTop, ivBottomCircle,mIvOperationsOfficer, mIvOperationsOfficerTop,ivDigitalTop);
                viewShow(index == 4,  ivNumber, tvLivePlayback, ivArchitect, ivArchitectTop);
                viewShow(index == 5, tvDigitalEconomy,tvPassageway, ivChina,ivGoal);

                if (index > 5) {
                    relatAll.setVisibility(View.GONE);
                    SPUtils.getInstance().put(Constant.BUNDLE_HOMPAGE_BG, 2);
                    if (doAfterGuide != null) {
                        doAfterGuide.onAfterGuide();
                    }
                    if (unbinder != null) {
                        unbinder.unbind();
                    }
                }
                break;
            default:
                break;
        }
    }

    private void viewShow(boolean show, View... views) {
        for (View view : views) {
            view.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
        }
    }

    public IDoAfterGuide doAfterGuide;

    public void setDoAfterGuide(IDoAfterGuide doAfterGuide) {
        this.doAfterGuide = doAfterGuide;
    }

    public interface IDoAfterGuide {
        void onAfterGuide();
    }


}
