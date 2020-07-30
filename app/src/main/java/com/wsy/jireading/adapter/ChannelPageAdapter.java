package com.wsy.jireading.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wsy.jireading.R;

import java.util.List;

/**
 * Created by songYangWu
 * Date:2020/7/27
 */
public class ChannelPageAdapter extends RecyclerView.Adapter<ChannelPageAdapter.ChannelViewHolder> {

    private LayoutInflater layoutInflater;
    private List<String> channelList;

    //函数构造
    public ChannelPageAdapter(Context context, List<String> channelList) {
        layoutInflater = LayoutInflater.from(context);
        this.channelList = channelList;
    }

    @NonNull
    @Override
    public ChannelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_bookstore_toolbar_channel, null);
        ChannelViewHolder channelViewHolder = new ChannelViewHolder(view);
        return channelViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChannelViewHolder holder, int position) {
        holder.textView.setText(channelList.get(position));
    }

    @Override
    public int getItemCount() {
        return channelList.size();
    }

    class ChannelViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ChannelViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_channel_text);
        }
    }
}
