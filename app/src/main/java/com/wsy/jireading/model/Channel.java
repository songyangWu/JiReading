package com.wsy.jireading.model;

import androidx.fragment.app.Fragment;

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
    private transient Fragment fragment;

    public Channel(String title, Fragment fragment) {
        this.title = title;
        this.fragment = fragment;
    }

    public Channel(int itemType, String title) {
        this.itemType = itemType;
        this.title = title;
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

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
