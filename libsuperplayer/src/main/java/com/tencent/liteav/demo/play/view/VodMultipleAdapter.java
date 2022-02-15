package com.tencent.liteav.demo.play.view;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tencent.liteav.demo.play.R;
import com.tencent.liteav.demo.play.bean.TCMultipleBean;

import java.util.List;

/**
 * @author: gengyan
 * date:    2020/6/3 11:53
 * company: 普华集团
 * description: 描述
 */
public class VodMultipleAdapter extends RecyclerView.Adapter<VodMultipleAdapter.MyHolder> {

    private List<TCMultipleBean> multipleBeanList;
    private IItemClickListener clickListener;

    public void setClickListener(IItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public VodMultipleAdapter(List<TCMultipleBean> multipleBeanList) {
        this.multipleBeanList = multipleBeanList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_vod_multiple, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int i) {
        final TCMultipleBean multipleBean = multipleBeanList.get(i);
        if (multipleBean != null) {
            holder.mTvSpeed.setText(String.format("%sX", multipleBean.getSpeedLevel()));
            holder.mTvSpeed.setSelected(multipleBean.isSelect());

            holder.mTvSpeed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) {
                        clickListener.onItemClick(multipleBean.getSpeedLevel());
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return multipleBeanList == null ? 0 : multipleBeanList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        TextView mTvSpeed;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            mTvSpeed = itemView.findViewById(R.id.tv_speed_item);
        }
    }

    public interface IItemClickListener{
        void onItemClick(float speedLevel);
    }
}
