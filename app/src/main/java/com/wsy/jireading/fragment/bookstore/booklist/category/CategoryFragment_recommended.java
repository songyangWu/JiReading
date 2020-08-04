package com.wsy.jireading.fragment.bookstore.booklist.category;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wsy.jireading.R;
import com.wsy.jireading.adapter.SecKillAdapter;
import com.wsy.jireading.model.Book;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment_recommended extends Fragment {
    private RecyclerView recyclerView;
    private List<Book> bookList = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_recommended, container, false);

        initData();
        initComponent(view);

        return view;
    }

    private void initData() {
        for (int i = 0; i < 9; i++) {
            Book book = new Book();
            book.setBookName("标题" + i);
            book.setAuthor("作者" + i);
            bookList.add(book);
        }
    }
    private void initComponent(View view) {
        recyclerView = view.findViewById(R.id.category_rv1);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(),3);
        recyclerView.setLayoutManager(gridLayoutManager);
        SecKillAdapter secKillAdapter = new SecKillAdapter(view.getContext(),bookList);
        recyclerView.setAdapter(secKillAdapter);
    }
}