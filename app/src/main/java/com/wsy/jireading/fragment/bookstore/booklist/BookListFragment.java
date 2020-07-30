package com.wsy.jireading.fragment.bookstore.booklist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wsy.jireading.R;
import com.wsy.jireading.widget.banner.DataBean;
import com.wsy.jireading.widget.banner.ImageAdapter;
import com.youth.banner.Banner;
import com.youth.banner.indicator.RectangleIndicator;

public class BookListFragment extends Fragment {
    private Banner mBanner;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);

        initComponent(view);

        return view;
    }

    private void initComponent(View view) {
        mBanner = view.findViewById(R.id.banner);
        ImageAdapter adapter = new ImageAdapter(DataBean.getDataByRes(),view.getContext());
        mBanner.setAdapter(adapter)// 设置banner的适配器
                .setBannerGalleryEffect(0,2)// 设置画廊效果
                .setIndicator(new RectangleIndicator(view.getContext()));// 设置指示器
    }


}
