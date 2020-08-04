package com.wsy.jireading.widget;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 定义RecycleView水平方向的item间距
 * Created by songYangWu
 * Date:2020/8/2
 */
public class HorizontalItemDecoration extends RecyclerView.ItemDecoration {
    private int space;// 定义2个Item之间的距离

    public HorizontalItemDecoration(int space, Context context) {
        this.space = dip2x(space, context);
    }
    private int dip2x(float space, Context context) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (space * scale + 0.5f);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int totalCount = parent.getAdapter().getItemCount();
        if (position == 0) {//第一个
            outRect.left = 0;
            outRect.right = space / 2;
        } else if (position == totalCount - 1) {//最后一个
            outRect.left = space / 2;
            outRect.right = 0;
        } else {//其他中间的
            outRect.left = space / 2;
            outRect.right = space / 2;
        }
    }
}
