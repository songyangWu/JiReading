package com.wsy.jireading.fragment.bookcase;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wsy.jireading.R;

public class BookcaseFragment extends Fragment {

    private Toolbar toolbar;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookcase, container, false);

        initComponent(view);

        return view;
    }

    private void initComponent(View view) {
        toolbar = view.findViewById(R.id.bookcase_toolbar);
    }

}
