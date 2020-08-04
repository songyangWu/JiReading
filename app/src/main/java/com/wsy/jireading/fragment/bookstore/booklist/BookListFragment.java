package com.wsy.jireading.fragment.bookstore.booklist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.SlidingTabLayout;
import com.wsy.jireading.R;
import com.wsy.jireading.adapter.CategoryPageAdapter;
import com.wsy.jireading.adapter.SecKillAdapter;
import com.wsy.jireading.fragment.bookstore.booklist.category.CategoryFragment_VIP;
import com.wsy.jireading.fragment.bookstore.booklist.category.CategoryFragment_new;
import com.wsy.jireading.fragment.bookstore.booklist.category.CategoryFragment_recommended;
import com.wsy.jireading.model.Book;
import com.wsy.jireading.widget.HorizontalItemDecoration;
import com.wsy.jireading.widget.banner.DataBean;
import com.wsy.jireading.widget.banner.ImageAdapter;
import com.youth.banner.Banner;
import com.youth.banner.indicator.RectangleIndicator;

import java.util.ArrayList;
import java.util.List;

public class BookListFragment extends Fragment {
    private Banner mBanner;
    private RecyclerView recyclerView_SecKill;
    private SlidingTabLayout mTabLayout;
    private ViewPager mPager;

    private List<Book> bookList = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();
    private String[] categoryArrays = {"小编推荐","VIP","重磅上新"};

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);

        initData();
        initComponent(view);

        return view;
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            Book book = new Book();
            book.setBookName("标题" + i);
            book.setAuthor("作者" + i);
            bookList.add(book);
        }
    }

    /**
     * 初始化控件
     * @param view
     */
    private void initComponent(View view) {
        //初始化轮播
        mBanner = view.findViewById(R.id.banner);
        ImageAdapter adapter = new ImageAdapter(DataBean.getDataByRes(),view.getContext());
        mBanner.setAdapter(adapter)// 设置banner的适配器
                .setBannerGalleryEffect(0,2)// 设置画廊效果
                .setIndicator(new RectangleIndicator(view.getContext()));// 设置指示器

        //初始化 今日秒杀 RecycleView
        recyclerView_SecKill = view.findViewById(R.id.recycleView_SecKill);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView_SecKill.setLayoutManager(linearLayoutManager);
        recyclerView_SecKill.addItemDecoration(new HorizontalItemDecoration(20,view.getContext()));
        SecKillAdapter secKillAdapter = new SecKillAdapter(view.getContext(), bookList);
        recyclerView_SecKill.setAdapter(secKillAdapter);

        //初始化不同类别的Fragment
        CategoryFragment_recommended fragmentRecommended = new CategoryFragment_recommended();
        CategoryFragment_VIP fragmentVip = new CategoryFragment_VIP();
        CategoryFragment_new fragmentNew = new CategoryFragment_new();
        fragments.add(fragmentRecommended);
        fragments.add(fragmentVip);
        fragments.add(fragmentNew);

        //初始化不同类别的分页
        mTabLayout = view.findViewById(R.id.category_tab);
        mPager = view.findViewById(R.id.category_viewPager);
        CategoryPageAdapter categoryPageAdapter = new CategoryPageAdapter(getChildFragmentManager(),fragments);
        mPager.setAdapter(categoryPageAdapter);
        mTabLayout.setViewPager(mPager, categoryArrays);
    }

}
