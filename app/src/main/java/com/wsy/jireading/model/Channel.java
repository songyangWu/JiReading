package com.wsy.jireading.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 * Created by songYangWu
 * Date:2020/7/28
 */
public class Channel implements MultiItemEntity, Serializable {
    public static final int TYPE_MY = 1;//我的频道title
    public static final int TYPE_OTHER = 2;//推荐频道title
    public static final int TYPE_MY_CHANNEL = 3;//我的频道
    public static final int TYPE_OTHER_CHANNEL = 4;//推荐频道

    private int itemType;
    private String title;
    private String channelCode;

    public Channel(String title, String channelCode) {
        this(TYPE_MY_CHANNEL, title, channelCode);
    }

    public Channel(int itemType, String title, String channelCode) {
        this.title = title;
        this.channelCode = channelCode;
        this.itemType = itemType;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }
}
