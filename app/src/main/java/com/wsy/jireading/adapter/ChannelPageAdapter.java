package com.wsy.jireading.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.wsy.jireading.model.Channel;

import java.util.List;

/**
 * Created by songYangWu
 * Date:2020/7/27
 */
public class ChannelPageAdapter extends FragmentStatePagerAdapter {

    private List<Channel> Channels;
    private List<Fragment> fragmentList;

    //函数构造
    public ChannelPageAdapter(@NonNull FragmentManager fm, List<Channel> mChannels, List<Fragment> fragmentList) {
        super(fm);
        this.Channels = mChannels;
        this.fragmentList = fragmentList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return Channels.get(position).getTitle();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

    }
}
