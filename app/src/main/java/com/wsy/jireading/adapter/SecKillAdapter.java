package com.wsy.jireading.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wsy.jireading.R;
import com.wsy.jireading.model.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * 今日秒杀图书适配器
 * Created by songYangWu
 * Date:2020/8/2
 */
public class SecKillAdapter extends RecyclerView.Adapter<SecKillAdapter.SecKillViewHolder> {
    private LayoutInflater layoutInflater;
    private Context context;
    private List<Book> bookList = new ArrayList<>();

    public SecKillAdapter(Context context, List<Book> bookList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.bookList = bookList;
    }

    /**
     * 加载 item 的布局文件
     */
    @NonNull
    @Override
    public SecKillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_booklist_type_seckill, null);
        SecKillViewHolder secKillViewHolder = new SecKillViewHolder(view);
        return secKillViewHolder;
    }

    /**
     * 将数据与 item 视图进行绑定
     */
    @Override
    public void onBindViewHolder(@NonNull SecKillViewHolder holder, int position) {
        //Glide.with(context).load(bookList.get(position).getBookImage()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    /**
     * 自定义RecycleView 中的 ViewHolder
     */
    class SecKillViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView bookName;
        private TextView author;

        public SecKillViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_cover);
            bookName = itemView.findViewById(R.id.tv_bookName);
            author = itemView.findViewById(R.id.tv_author);
        }
    }
}
