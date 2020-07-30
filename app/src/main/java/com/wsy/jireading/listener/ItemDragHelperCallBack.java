package com.wsy.jireading.listener;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * Created by songYangWu
 * Date:2020/7/29
 */
public class ItemDragHelperCallBack extends ItemTouchHelper.Callback {
    private OnChannelDragListener onChannelDragListener;

    public ItemDragHelperCallBack(OnChannelDragListener onChannelDragListener) {
        this.onChannelDragListener = onChannelDragListener;
    }

    /**
     * 返回一个复合标志，该标志定义每个状态下启用的移动方向
     * @param recyclerView
     * @param viewHolder
     * @return
     */
    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        int dragFlags;
        //instanceof 是Java中的一个双目运算符 用作对象的判断
        if (manager instanceof GridLayoutManager || manager instanceof StaggeredGridLayoutManager) {
            //监听上下左右拖动

            //按位或运算符（|）
            //参加运算的两个对象，按二进制位进行“或”运算。
            //运算规则：0|0=0；   0|1=1；   1|0=1；    1|1=1；
            //即 ：参加运算的两个对象只要有一个为1，其值为1。
            //例如:3|5　即 0000 0011 | 0000 0101 = 0000 0111   因此，3|5的值得7

            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        } else {
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        }
        return makeMovementFlags(dragFlags, 0);
    }

    /**
     * 当ItemTouchHelper希望将拖动的项从原来的位置移动到新位置
     * @param recyclerView
     * @param viewHolder
     * @param target
     * @return
     */
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        //不同Type之间不可移动(即我的频道item不能拖动到频道推荐)
        if (viewHolder.getItemViewType() != target.getItemViewType()) {
            return false;
        }
        if (onChannelDragListener != null) {
            onChannelDragListener.onItemMove(viewHolder.getAdapterPosition(), target.getAbsoluteAdapterPosition());
        }
        return true;
    }

    /**
     * 当用户滑动ViewHolder时调用
     * @param viewHolder
     * @param direction
     */
    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

    }
}
