package com.wsy.jireading.widget.banner;

import com.wsy.jireading.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songYangWu
 * Date:2020/7/21
 */
public class DataBean {
    public Integer imageRes;
    public String imageUrl;
    public String title;
    public int viewType;

    public DataBean(Integer imageRes, String title, int viewType) {
        this.imageRes = imageRes;
        this.title = title;
        this.viewType = viewType;
    }

    public DataBean(String imageUrl, String title, int viewType) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.viewType = viewType;
    }

    public static List<DataBean> getDataByRes() {
        List<DataBean> list = new ArrayList<>();
        list.add(new DataBean(R.mipmap.bookstore_banner1,null,1));
        list.add(new DataBean(R.mipmap.bookstore_banner2,null,1));
        list.add(new DataBean(R.mipmap.bookstore_banner3,null,1));
        list.add(new DataBean(R.mipmap.bookstore_banner4,null,1));
        list.add(new DataBean(R.mipmap.bookstore_banner5,null,1));
        list.add(new DataBean(R.mipmap.bookstore_banner6,null,1));
        list.add(new DataBean(R.mipmap.bookstore_banner7,null,1));
        list.add(new DataBean(R.mipmap.bookstore_banner8,null,1));
        return list;
    }

    public static List<DataBean> getDataByUrl() {
        List<DataBean> list = new ArrayList<>();
        return list;
    }

}
