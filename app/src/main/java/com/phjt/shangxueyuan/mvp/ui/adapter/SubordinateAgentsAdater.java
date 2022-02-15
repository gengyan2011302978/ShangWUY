package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Context;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ctetin.expandabletextviewlibrary.ExpandableTextView;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.AgentsBean;
import com.phjt.shangxueyuan.bean.TutorAnsweringQuestionsBean;
import com.phjt.shangxueyuan.common.AppImageLoader;
import com.phjt.view.roundImg.RoundedImageView;

import java.util.List;

/**
 * @author: Roy
 * date:   2021/9/13
 * company: 普华集团
 * description:
 */
public class SubordinateAgentsAdater extends BaseQuickAdapter<AgentsBean, BaseViewHolder> {
    private Context mContext;
    private int mType;

    public SubordinateAgentsAdater(Context context, int tpye) {
        super(R.layout.item_subordinate_agents);
        this.mContext = context;
        this.mType = tpye;
    }

    @Override
    protected void convert(BaseViewHolder helper, AgentsBean item) {
        helper.setText(R.id.tv_amount_num, "10088学豆")
                .setText(R.id.tv_phone_num, "132****8036")
                .setText(R.id.tv_date, "2021/08/20")
                .setText(R.id.tv_user_name, "耿宴");

    }

}
