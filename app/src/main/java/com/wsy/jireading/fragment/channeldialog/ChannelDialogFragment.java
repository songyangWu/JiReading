package com.wsy.jireading.fragment.channeldialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.wsy.jireading.R;
import com.wsy.jireading.adapter.ChannelAdapter;
import com.wsy.jireading.constant.Constant;
import com.wsy.jireading.listener.ItemDragHelperCallBack;
import com.wsy.jireading.listener.OnChannelDragListener;
import com.wsy.jireading.listener.OnChannelListener;
import com.wsy.jireading.model.Channel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChannelDialogFragment extends DialogFragment implements View.OnClickListener, OnChannelDragListener {
    private List<Channel> mData = new ArrayList<>();
    private ChannelAdapter mAdapter;

    private RecyclerView mRecyclerView;
    private ImageView imageView;

    private ItemTouchHelper mHelper;

    private OnChannelListener mOnChannelListener;
    private DialogInterface.OnDismissListener mOnDismissListener;

    public void setOnChannelListener(OnChannelListener onChannelListener) {
        mOnChannelListener = onChannelListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Light_NoTitleBar);
    }

    /**
     * ChannelDialogFragment创建时调用
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_channel_dialog, null);

        mRecyclerView = view.findViewById(R.id.channelRecycleView);
        imageView = view.findViewById(R.id.channel_exit);
        imageView.setOnClickListener(this);

        Dialog dialog = getDialog();
        if (dialog != null) {
            //添加动画
            dialog.getWindow().setWindowAnimations(R.style.dialogSlideAnim);
        }

        return view;
    }

    /**
     * onViewCreated是在onCreateView后被触发的事件
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //ButterKnife是一个专注于Android系统的View注入框架(需集成)
        //ButterKnife的优势：
        //1、强大的View绑定和Click事件处理功能，简化代码，提升开发效率
        //2、方便的处理Adapter里的ViewHolder绑定问题
        //3、运行时不会影响APP效率，使用配置方便
        //4、代码清晰，可读性强
        //ButterKnife.bind(this, view);
        processLogic();
    }

    //数据处理逻辑
    private void processLogic() {
        mData.add(new Channel(Channel.TYPE_MY, "我的频道"));

        Bundle bundle = getArguments();
        List<Channel> selectedData = (List<Channel>) bundle.getSerializable(Constant.DATA_SELECTED);
        List<Channel> unselectedData = (List<Channel>) bundle.getSerializable(Constant.DATA_UNSELECTED);
        setDataType(selectedData, Channel.TYPE_MY_CHANNEL);
        setDataType(unselectedData, Channel.TYPE_OTHER_CHANNEL);

        mData.addAll(selectedData);
        mData.add(new Channel(Channel.TYPE_OTHER, "频道推荐"));
        mData.addAll(unselectedData);

        mAdapter = new ChannelAdapter(mData);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),4);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int itemViewType = mAdapter.getItemViewType(position);
                return itemViewType == Channel.TYPE_MY_CHANNEL || itemViewType == Channel.TYPE_OTHER_CHANNEL ? 1 : 4;
            }
        });

        ItemDragHelperCallBack callBack = new ItemDragHelperCallBack(this);
        mHelper = new ItemTouchHelper(callBack);
        mAdapter.setOnChannelDragListener(this);
        mHelper.attachToRecyclerView(mRecyclerView);
    }

    /**
     * 为数据设置item类型
     * @param data 选中或未选中的List
     * @param itemType item类型（“我的频道 我的频道列表 频道推荐 频道推荐列表”）
     */
    private void setDataType(List<Channel> data, int itemType) {
        for (int i = 0; i < data.size(); i++) {
            data.get(i).setItemType(itemType);
        }
    }

    //初始化ChannelDialogFragment
    public static ChannelDialogFragment bookInstance(List<Channel> selectedData, List<Channel> unselectedData) {
        ChannelDialogFragment dialogFragment = new ChannelDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.DATA_SELECTED, (Serializable) selectedData);
        bundle.putSerializable(Constant.DATA_UNSELECTED, (Serializable) unselectedData);
        dialogFragment.setArguments(bundle);
        return dialogFragment;
    }

    public void onClick(View view) {
        dismiss();
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        mOnDismissListener = onDismissListener;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mOnDismissListener != null) {
            mOnDismissListener.onDismiss(dialog);
        }
    }

    @Override
    public void onStartDrag(BaseViewHolder baseViewHolder) {
        //开始拖动
        mHelper.startDrag(baseViewHolder);
    }

    @Override
    public void onItemMove(int startPos, int endPos) {
        //在我的频道之间移动
        if (mOnChannelListener != null) {
            mOnChannelListener.onItemMove(startPos - 1, endPos - 1);//去除标题所占的一个index
        }
        onMove(startPos, endPos);
    }

    private void onMove(int startPos, int endPos) {
        Channel startChannel = mData.get(startPos);
        //先删除之前的位置
        mData.remove(startPos);
        //添加到现在的位置
        mData.add(endPos, startChannel);
        mAdapter.notifyItemMoved(startPos, endPos);
    }

    @Override
    public void onMoveToMyChannel(int startPos, int endPos) {
        //移动到我的频道
        onMove(startPos, endPos);
        if (mOnChannelListener != null) {
            mOnChannelListener.onMoveToMyChannel(startPos - 1 - mAdapter.getMyChannelSize(), endPos);
        }
    }

    @Override
    public void onMoveToOtherChannel(int startPos, int endPos) {
        //移动到频道推荐
        onMove(startPos, endPos);
        if (mOnChannelListener != null) {
            mOnChannelListener.onMoveToOtherChannel(startPos - 1, endPos - 2 - mAdapter.getMyChannelSize());
        }
    }
}
