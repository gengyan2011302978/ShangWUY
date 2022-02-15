package com.phjt.shangxueyuan.mvp.ui.adapter;

import android.content.Context;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.phjt.shangxueyuan.R;
import com.phjt.shangxueyuan.bean.DataBean;
import com.phjt.shangxueyuan.widgets.ProgressTextView;

import java.util.List;

/**
 * @author: gengyan
 * date:    2020/6/5 18:26
 * company: 普华集团
 * description: 资料页面
 */
public class DataAdapter extends BaseQuickAdapter<DataBean, BaseViewHolder> {

    private Context mContext;

    public DataAdapter(Context context, @Nullable List<DataBean> data) {
        super(R.layout.item_data, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, DataBean item) {
        helper.setText(R.id.tv_name_item, item.getRealName())
                .setText(R.id.iv_pdf_item, item.getFileType())
                .addOnClickListener(R.id.pb_tv_item)
                .addOnClickListener(R.id.tv_name_item);

        ProgressTextView pTv = helper.getView(R.id.pb_tv_item);
        pTv.setProgress(0);
        if (item.getDownloadStatus() == 0) {
            pTv.setmTextPaint("下载");
        }
        if (item.getDownloadStatus() == 1) {
            pTv.setmTextPaint("下载中");
        }
        if (item.getDownloadStatus() == 4) {
            pTv.setmTextPaint("已下载");
        }
    }
}
