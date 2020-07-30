package com.wsy.jireading.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * 频道Fragment适配器
 * Created by songYangWu
 * Date:2020/7/30
 */
public class BookListPagerAdapter extends FragmentStatePagerAdapter {
    ArrayList<Fragment> list = new ArrayList<>();

    public BookListPagerAdapter(@NonNull FragmentManager fm, ArrayList<Fragment> list) {
        super(fm);
        this.list = list;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
