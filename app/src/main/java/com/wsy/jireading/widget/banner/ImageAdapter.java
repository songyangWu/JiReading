package com.wsy.jireading.widget.banner;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;

/**
 * Created by songYangWu
 * Date:2020/7/21
 */
public class ImageAdapter extends BannerAdapter<DataBean, ImageAdapter.BannerViewHolder> {

    private Context context;// 上下文对象

    /**
     * 函数构造器
     * @param data
     * @param context
     */
    public ImageAdapter(List<DataBean> data, Context context) {
        super(data);
        this.context = context;
    }

    /**
     * 加载 item 的布局文件
     */
    @Override
    public BannerViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(parent.getContext());
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                //必须设置为match_parent，这个是viewpager2强制要求的
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return new BannerViewHolder(imageView);
    }

    /**
     * 将数据与 item 视图进行绑定
     */
    @Override
    public void onBindView(BannerViewHolder holder, DataBean data, int position, int size) {
        Glide.with(context).load(data.imageRes).into(holder.imageView);
    }


    /**
     * 自定义RecycleView 中的 ViewHolder
     */
     class BannerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public BannerViewHolder(@NonNull ImageView itemView) {
            super(itemView);
            this.imageView = itemView;
        }
    }

}
