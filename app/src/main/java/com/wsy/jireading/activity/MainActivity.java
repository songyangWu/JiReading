package com.wsy.jireading.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.wsy.jireading.R;
import com.wsy.jireading.fragment.bookcase.BookcaseFragment;
import com.wsy.jireading.fragment.bookstore.BookstoreFragment;
import com.wsy.jireading.fragment.found.FoundFragment;
import com.wsy.jireading.fragment.me.MeFragment;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    private BookstoreFragment bookstoreFragment;
    private BookcaseFragment bookcaseFragment;
    private FoundFragment foundFragment;
    private MeFragment meFragment;

    private RadioGroup radioGroup;
    private RadioButton nav_bookstore;
    private RadioButton nav_bookcase;
    private RadioButton nav_found;
    private RadioButton nav_me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponent();
    }

    private void initComponent() {
        bookstoreFragment = new BookstoreFragment();
        bookcaseFragment = new BookcaseFragment();
        foundFragment = new FoundFragment();
        meFragment = new MeFragment();

        radioGroup = findViewById(R.id.navigation_bar);
        nav_bookstore = findViewById(R.id.nav_bookstore);
        nav_bookcase = findViewById(R.id.nav_bookcase);
        nav_found = findViewById(R.id.nav_found);
        nav_me = findViewById(R.id.nav_me);

        radioGroup.setOnCheckedChangeListener(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,bookstoreFragment).commit();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.nav_bookstore:
                if (bookstoreFragment == null) {
                    bookstoreFragment = new BookstoreFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,bookstoreFragment).commit();
                break;
            case R.id.nav_bookcase:
                if (bookcaseFragment == null) {
                    bookcaseFragment = new BookcaseFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,bookcaseFragment).commit();
                break;
            case R.id.nav_found:
                if (foundFragment == null) {
                    foundFragment = new FoundFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,foundFragment).commit();
                break;
            case R.id.nav_me:
                if (meFragment == null) {
                    meFragment = new MeFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,meFragment).commit();
        }
    }
}
