package com.wsy.jireading.fragment.bookstore;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.flyco.tablayout.SlidingTabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wsy.jireading.R;
import com.wsy.jireading.adapter.ChannelPageAdapter;
import com.wsy.jireading.constant.Constant;
import com.wsy.jireading.fragment.bookstore.booklist.BookListFragment;
import com.wsy.jireading.fragment.bookstore.booklist.BookListFragmentB;
import com.wsy.jireading.fragment.bookstore.booklist.BookListFragmentC;
import com.wsy.jireading.fragment.bookstore.booklist.BookListFragmentD;
import com.wsy.jireading.fragment.bookstore.booklist.BookListFragmentE;
import com.wsy.jireading.fragment.bookstore.booklist.BookListFragmentF;
import com.wsy.jireading.fragment.bookstore.booklist.BookListFragmentG;
import com.wsy.jireading.fragment.bookstore.booklist.BookListFragmentH;
import com.wsy.jireading.fragment.bookstore.booklist.BookListFragmentI;
import com.wsy.jireading.fragment.channeldialog.ChannelDialogFragment;
import com.wsy.jireading.listener.OnChannelListener;
import com.wsy.jireading.model.Channel;

import java.util.ArrayList;
import java.util.List;

public class BookstoreFragment extends Fragment implements View.OnClickListener,OnChannelListener {
    private SlidingTabLayout mChannelTab;
    private ViewPager pager;
    private ImageView channelMore;

    private BookListFragment bookListFragment = new BookListFragment();
    private BookListFragmentB bookListFragmentB = new BookListFragmentB();
    private BookListFragmentC bookListFragmentC = new BookListFragmentC();
    private BookListFragmentD bookListFragmentD = new BookListFragmentD();
    private BookListFragmentE bookListFragmentE = new BookListFragmentE();
    private BookListFragmentF bookListFragmentF = new BookListFragmentF();
    private BookListFragmentG bookListFragmentG = new BookListFragmentG();
    private BookListFragmentH bookListFragmentH = new BookListFragmentH();
    private BookListFragmentI bookListFragmentI = new BookListFragmentI();

    private List<Channel> mSelectedChannels = new ArrayList<>();
    private List<Channel> mUnselectedChannels = new ArrayList<>();
    private List<Fragment> fragmentList = new ArrayList<>();
    private Gson gson = new Gson();
    private ChannelPageAdapter channelPageAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookstore, container, false);

        //初始化控件
        initComponent(view);

        return view;
    }

    private void initComponent(View view) {
        mChannelTab = view.findViewById(R.id.channel_tab);
        channelMore = view.findViewById(R.id.channel_more);
        pager = view.findViewById(R.id.viewPager_content);

        initChannelData();
        initChannelFragments();

        channelPageAdapter = new ChannelPageAdapter(getChildFragmentManager(), mSelectedChannels, fragmentList);
        pager.setAdapter(channelPageAdapter);
        mChannelTab.setViewPager(pager);

        channelMore.setOnClickListener(this);
    }

    /**
     * 初始化已选频道和未选频道数据
     */
    private void initChannelData() {
        String selectedChannelJson = getContext().getSharedPreferences(Constant.CONFIG_FILE_NAME, Context.MODE_PRIVATE).getString(Constant.SELECTED_CHANNEL_JSON,"");
        String unSelectedChannelJson = getContext().getSharedPreferences(Constant.CONFIG_FILE_NAME, Context.MODE_PRIVATE).getString(Constant.UNSELECTED_CHANNEL_JSON,"");

        //如果本地都没有ChannelTitle
        if (TextUtils.isEmpty(selectedChannelJson) || TextUtils.isEmpty(unSelectedChannelJson)) {
            String[] channels = getResources().getStringArray(R.array.channel);
            Fragment[] fragments = {bookListFragment, bookListFragmentB, bookListFragmentC, bookListFragmentD, bookListFragmentE, bookListFragmentF, bookListFragmentG, bookListFragmentH, bookListFragmentI};
            //默认添加了全部频道
            for (int i = 0; i < channels.length; i++) {
                String title = channels[i];
                Fragment fragment = fragments[i];
                mSelectedChannels.add(new Channel(title,fragment));
            }
            //将 选中频道 的集合转换成json字符串
            selectedChannelJson = gson.toJson(mSelectedChannels);
            //保存到sp
            getContext().getSharedPreferences(Constant.CONFIG_FILE_NAME, Context.MODE_PRIVATE).edit().putString(Constant.SELECTED_CHANNEL_JSON, selectedChannelJson);
        } else {//之前添加过
            List<Channel> selectedChannel = gson.fromJson(selectedChannelJson, new TypeToken<List<Channel>>() {}.getType());
            List<Channel> unSelectedChannel = gson.fromJson(unSelectedChannelJson, new TypeToken<List<Channel>>() {}.getType());
            mSelectedChannels.addAll(selectedChannel);
            mUnselectedChannels.addAll(unSelectedChannel);
        }
    }

    /**
     * 初始化已选频道的fragment
     */
    private void initChannelFragments() {
        for (int i = 0; i < mSelectedChannels.size(); i++) {
            Fragment fragment = mSelectedChannels.get(i).getFragment();
            fragmentList.add(fragment);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.channel_more:
                ChannelDialogFragment dialogFragment = ChannelDialogFragment.bookInstance(mSelectedChannels, mUnselectedChannels);
                dialogFragment.setOnChannelListener(this);
                dialogFragment.show(getChildFragmentManager(),"CHANNEL");
                dialogFragment.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        channelPageAdapter.notifyDataSetChanged();

                        //保存选中和未选中的channel
                        getContext().getSharedPreferences(Constant.CONFIG_FILE_NAME, Context.MODE_PRIVATE).edit().putString(Constant.SELECTED_CHANNEL_JSON, gson.toJson(mSelectedChannels));
                        getContext().getSharedPreferences(Constant.CONFIG_FILE_NAME, Context.MODE_PRIVATE).edit().putString(Constant.UNSELECTED_CHANNEL_JSON, gson.toJson(mUnselectedChannels));
                    }
                });
                break;
        }
    }

    @Override
    public void onItemMove(int startPos, int endPos) {
        listMove(mSelectedChannels, startPos, endPos);
        listMove(fragmentList, startPos, endPos);
    }

    @Override
    public void onMoveToMyChannel(int startPos, int endPos) {
        //移动到我的频道
        Channel channel = mUnselectedChannels.remove(startPos);
        mSelectedChannels.add(endPos, channel);

        fragmentList.add(channel.getFragment());
    }

    @Override
    public void onMoveToOtherChannel(int startPos, int endPos) {
        //移动到频道推荐
        mUnselectedChannels.add(endPos,mSelectedChannels.remove(startPos));
        fragmentList.remove(startPos);
    }

    private void listMove(List data, int starPos, int endPos) {
        Object obj = data.get(starPos);
        //先删除之前的位置
        data.remove(starPos);
        //添加到现在的位置
        data.add(endPos, obj);
    }

}
