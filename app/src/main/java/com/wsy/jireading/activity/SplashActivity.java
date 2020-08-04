package com.wsy.jireading.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wsy.jireading.R;

/**
 * Created by songYangWu
 * Date:2020/8/4
 */
public class SplashActivity extends AppCompatActivity {
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            toMainActivity();
        }
    };

    //跳转到主页
    private void toMainActivity() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //延迟4秒跳转
        handler.postDelayed(runnable,40);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //防止内存泄露
        handler.removeCallbacks(runnable);
    }
}
