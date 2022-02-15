package com.phjt.shangxueyuan.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
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
 * @author: Roy
 * date:   2021/3/18
 * company: 普华集团
 * description:
 */
public class MineGuideView extends RelativeLayout {


    /**
     * guide index6 show
     */
    @BindView(R.id.rv_all)
    RelativeLayout relatAll;
    @BindView(R.id.iv_bottom_six_top)
    ImageView ivSixTop;
    @BindView(R.id.iv_bottom_six_elite)
    ImageView ivSixElite;

    @BindView(R.id.iv_i_know)
    ImageView ivKnow;
    private View view;
    private Unbinder unbinder;
    public IDoAfterGuide doAfterGuide;

    public MineGuideView(Context context) {
        super(context);
        init(context);
    }

    public MineGuideView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MineGuideView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.view_user_mine_guide, this, true);
        unbinder = ButterKnife.bind(this, view);
    }

    @OnClick(R.id.iv_i_know)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_i_know:
                relatAll.setVisibility(View.GONE);
                SPUtils.getInstance().put(Constant.BUNDLE_MINE_BG, 2);
                if (doAfterGuide != null) {
                    doAfterGuide.onAfterGuide();
                }
                if (unbinder != null) {
                    unbinder.unbind();
                }
                break;
            default:
                break;
        }
    }

    public void setDoAfterGuide(IDoAfterGuide doAfterGuide) {
        this.doAfterGuide = doAfterGuide;
    }

    public interface IDoAfterGuide {
        void onAfterGuide();
    }


}
