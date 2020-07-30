package com.wsy.jireading.fragment.bookstore;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.SlidingTabLayout;
import com.wsy.jireading.R;
import com.wsy.jireading.adapter.BookListPagerAdapter;
import com.wsy.jireading.fragment.bookstore.booklist.BookListFragment;
import com.wsy.jireading.fragment.bookstore.booklist.BookListFragmentB;
import com.wsy.jireading.fragment.bookstore.booklist.BookListFragmentC;
import com.wsy.jireading.fragment.bookstore.booklist.BookListFragmentD;
import com.wsy.jireading.fragment.bookstore.booklist.BookListFragmentE;
import com.wsy.jireading.fragment.bookstore.booklist.BookListFragmentF;
import com.wsy.jireading.fragment.bookstore.booklist.BookListFragmentG;
import com.wsy.jireading.fragment.bookstore.booklist.BookListFragmentH;
import com.wsy.jireading.fragment.bookstore.booklist.BookListFragmentI;

import java.util.ArrayList;

public class BookstoreFragment extends Fragment {
    private SlidingTabLayout mChannelTab;
    private ViewPager pager;

    private BookListFragment bookListFragment;
    private BookListFragmentB bookListFragmentB;
    private BookListFragmentC bookListFragmentC;
    private BookListFragmentD bookListFragmentD;
    private BookListFragmentE bookListFragmentE;
    private BookListFragmentF bookListFragmentF;
    private BookListFragmentG bookListFragmentG;
    private BookListFragmentH bookListFragmentH;
    private BookListFragmentI bookListFragmentI;

    private ArrayList<Fragment> bookFragmentList = new ArrayList<>();
    private String[] channelTabArrays = {"精选","文学","经济","计算机","历史","艺术","旅游","时尚","其他"};

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookstore, container, false);

        //初始化控件
        initComponent(view);

        return view;
    }

    private void initComponent(View view) {
        mChannelTab = view.findViewById(R.id.channel_tab);
        pager = view.findViewById(R.id.viewPager_content);

        bookListFragment = new BookListFragment();
        bookListFragmentB = new BookListFragmentB();
        bookListFragmentC = new BookListFragmentC();
        bookListFragmentD = new BookListFragmentD();
        bookListFragmentE = new BookListFragmentE();
        bookListFragmentF = new BookListFragmentF();
        bookListFragmentG = new BookListFragmentG();
        bookListFragmentH = new BookListFragmentH();
        bookListFragmentI = new BookListFragmentI();

        bookFragmentList.add(bookListFragment);
        bookFragmentList.add(bookListFragmentB);
        bookFragmentList.add(bookListFragmentC);
        bookFragmentList.add(bookListFragmentD);
        bookFragmentList.add(bookListFragmentE);
        bookFragmentList.add(bookListFragmentF);
        bookFragmentList.add(bookListFragmentG);
        bookFragmentList.add(bookListFragmentH);
        bookFragmentList.add(bookListFragmentI);

        BookListPagerAdapter bookListPagerAdapter = new BookListPagerAdapter(getChildFragmentManager(), bookFragmentList);
        pager.setAdapter(bookListPagerAdapter);

        mChannelTab.setViewPager(pager, channelTabArrays);
    }
}
