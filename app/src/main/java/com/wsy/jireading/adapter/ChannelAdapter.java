package com.wsy.jireading.adapter;

import android.graphics.Bitmap;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wsy.jireading.R;
import com.wsy.jireading.listener.OnChannelDragListener;
import com.wsy.jireading.model.Channel;

import java.util.List;

/**
 * 频道适配器
 * Created by songYangWu
 * Date:2020/7/28
 */
public class ChannelAdapter extends BaseMultiItemQuickAdapter<Channel, BaseViewHolder> {
    private BaseViewHolder mEditViewHolder;
    private boolean isEdit;
    private long startTime;
    // 点触间隔时间 用于分辨是否是“点击”
    private static final long SPACE_TIME = 100;
    private RecyclerView mRecyclerView;

    private OnChannelDragListener onChannelDragListener;
    //动画时长
    private int ANIM_TIME = 360;

    public ChannelAdapter(List<Channel> data) {
        super(data);
        //默认没有编辑
        isEdit = false;
        addItemType(Channel.TYPE_MY, R.layout.item_channel_title);
        addItemType(Channel.TYPE_MY_CHANNEL, R.layout.item_channel);
        addItemType(Channel.TYPE_OTHER, R.layout.item_channel_title);
        addItemType(Channel.TYPE_OTHER_CHANNEL, R.layout.item_channel);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mRecyclerView = (RecyclerView) parent;
        return super.onCreateViewHolder(parent, viewType);
    }

    public void setOnChannelDragListener(OnChannelDragListener onChannelDragListener) {
        this.onChannelDragListener = onChannelDragListener;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Channel channel) {
        switch (baseViewHolder.getItemViewType()) {
            case Channel.TYPE_MY:
                //我的频道
                baseViewHolder.setText(R.id.channel_title, channel.getTitle())
                        .setOnClickListener(R.id.channel_tvEdit, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!isEdit) {
                                    startEditMode(true);
                                    baseViewHolder.setText(R.id.channel_tvEdit, "完成");
                                } else {
                                    startEditMode(false);
                                    baseViewHolder.setText(R.id.channel_tvEdit, "编辑");
                                }
                            }
                        });
                break;
            case Channel.TYPE_OTHER:
                //频道推荐
                baseViewHolder.setText(R.id.channel_title, channel.getTitle())
                        .setGone(R.id.channel_tvEdit, false);
                break;
            case Channel.TYPE_MY_CHANNEL:
                //我的频道 列表
                baseViewHolder.setVisible(R.id.channelDelete, isEdit && !(channel.getTitle().equals("推荐")))//编辑模式就显示删出按钮，除了“推荐”
                        .setOnLongClickListener(R.id.channel_item, new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {
                                if (!isEdit) {
                                    //开启编辑模式
                                    startEditMode(true);
                                    mEditViewHolder.setText(R.id.channel_tvEdit, "完成");
                                }
                                if (onChannelDragListener != null) {
                                    onChannelDragListener.onStartDrag(baseViewHolder);
                                }
                                return true;
                            }
                        }).setOnTouchListener(R.id.channel_tv, new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                if (!isEdit) {
                                    //正常模式无需监听触摸
                                    return false;
                                }
                                switch (event.getAction()) {
                                    case MotionEvent.ACTION_DOWN:// 当手指按下
                                         startTime = System.currentTimeMillis();
                                         break;
                                    case MotionEvent.ACTION_MOVE:
                                        if (System.currentTimeMillis() - startTime > SPACE_TIME) {
                                            //当MOVE事件与DOWN事件的触发的间隔时间大于100ms时，则认为是拖拽startDrag
                                            if (onChannelDragListener != null) {
                                                onChannelDragListener.onStartDrag(baseViewHolder);
                                            }
                                        }
                                        break;
                                    case MotionEvent.ACTION_CANCEL:
                                    case MotionEvent.ACTION_UP:// 当手指抬起
                                        startTime = 0;
                                        break;
                                }
                                return false;
                            }
                        }).getView(R.id.channelDelete).setTag(true);//在我的频道里面设置true标示，之后会根据这个标示来判断编辑模式是否显示

                baseViewHolder.setText(R.id.channel_tv, channel.getTitle()).setOnClickListener(R.id.channelDelete, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //执行删除，移动到推荐频道列表
                        if (isEdit) {
                            int otherFirstPosition = getOtherFirstPosition();//得到频道推荐列表中第一个频道的position
                            int currentPosition = baseViewHolder.getAdapterPosition() - getHeaderLayoutCount();
                            //获取到目标view(即将移动到该位置的view)
                            View targetView = mRecyclerView.getLayoutManager().findViewByPosition(otherFirstPosition);
                            //获取当前需要移动的view(即将被移动的view)
                            View currentView = mRecyclerView.getLayoutManager().findViewByPosition(currentPosition);
                            // 如果targetView不在屏幕内（即频道推荐为空）,则indexOfChild为-1  此时不需要添加动画,因为此时notifyItemMoved自带一个向目标移动的动画
                            // 如果在屏幕内,则添加一个位移动画
                            if (mRecyclerView.indexOfChild(targetView) >= 0 && otherFirstPosition != -1) {
                                RecyclerView.LayoutManager manager = mRecyclerView.getLayoutManager();
                                int spanCount = ((GridLayoutManager) manager).getSpanCount();
                                int targetX = targetView.getLeft();
                                int targetY = targetView.getTop();
                                int myChannelSize = getMyChannelSize();// 我的频道item个数
                                if (myChannelSize % spanCount == 1) {
                                    //我的频道最后一行 若只有一个频道 则移动到频道推荐后，会向上缩进
                                    targetY -= targetView.getHeight();
                                }

                                //我的频道 移动到 推荐频道的第一个
                                channel.setItemType(Channel.TYPE_OTHER_CHANNEL);//改为频道推荐类型

                                if (onChannelDragListener != null) {
                                    onChannelDragListener.onMoveToOtherChannel(currentPosition, otherFirstPosition - 1);
                                }
                                startAnimation(currentView, targetX, targetY);
                            } else {
                                channel.setItemType(Channel.TYPE_OTHER_CHANNEL);//改为频道推荐类型
                                if (otherFirstPosition == -1) {
                                    otherFirstPosition = mData.size();
                                    if (onChannelDragListener != null) {
                                        onChannelDragListener.onMoveToOtherChannel(currentPosition, otherFirstPosition - 1);
                                    }
                                }
                            }
                        }
                    }
                });
                break;
            case Channel.TYPE_OTHER_CHANNEL:
                //频道推荐 列表
                baseViewHolder.setText(R.id.channel_tv, channel.getTitle()).setVisible(R.id.channelDelete, false)
                        .setOnClickListener(R.id.channel_tv, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int myLastPosition = getMyLastPosition();// 获取我的频道列表的最后一个频道的position
                                int currentPosition = baseViewHolder.getLayoutPosition() - getHeaderLayoutCount();
                                //获取到目标view
                                View targetView = mRecyclerView.getLayoutManager().findViewByPosition(myLastPosition);
                                //获取当前需要移动的view
                                View currentView = mRecyclerView.getLayoutManager().findViewByPosition(currentPosition);
                                // 如果targetView不在屏幕内,则indexOfChild为-1  此时不需要添加动画,因为此时notifyItemMoved自带一个向目标移动的动画
                                // 如果在屏幕内,则添加一个位移动画
                                if (mRecyclerView.indexOfChild(targetView) >= 0 && myLastPosition != -1) {
                                    RecyclerView.LayoutManager manager = mRecyclerView.getLayoutManager();
                                    int spanCount = ((GridLayoutManager) manager).getSpanCount();
                                    int targetX = targetView.getLeft() + targetView.getWidth();
                                    int targetY = targetView.getTop();
                                    int mChannelSize = getMyChannelSize();//我的频道列表的 频道个数
                                    if (mChannelSize % spanCount == 0) {
                                        //由频道推荐添加到我的频道后会进行换行，所以找到倒数第4个的位置(也就是当前行的第一个的位置)
                                        View lastFourthView = mRecyclerView.getLayoutManager().findViewByPosition(getMyLastPosition() - 3);
                                        targetX = lastFourthView.getLeft();
                                        targetY = lastFourthView.getTop() + lastFourthView.getHeight();
                                    }

                                    // 频道推荐 移动到 我的频道的最后一个
                                    channel.setItemType(Channel.TYPE_MY_CHANNEL);//改为我的频道类型
                                    if (onChannelDragListener != null) {
                                        onChannelDragListener.onMoveToMyChannel(currentPosition, myLastPosition + 1);
                                    }
                                    startAnimation(currentView, targetX, targetY);
                                } else {
                                    channel.setItemType(Channel.TYPE_MY_CHANNEL);//改为我的频道类型
                                    if (myLastPosition == -1) {
                                        myLastPosition = 0;//我的频道没有了，改成0
                                    }
                                    if (onChannelDragListener != null) {
                                        onChannelDragListener.onMoveToMyChannel(currentPosition, myLastPosition + 1);
                                    }
                                }
                            }
                        });
                break;
        }
    }

    /**
     * 开启编辑模式
     * @param isEdit 是否是编辑模式
     */
    private void startEditMode(boolean isEdit) {
        this.isEdit = isEdit;
        int visibleChildCount = mRecyclerView.getChildCount();//当前页可见的item个数
        for (int i = 0; i < visibleChildCount; i++) {
            View view = mRecyclerView.getChildAt(i);
            ImageView imgEdit = (ImageView) view.findViewById(R.id.channelDelete);
            if (imgEdit != null) {
                boolean isVisible = imgEdit.getTag() == null ? false : (boolean) imgEdit.getTag();
                imgEdit.setVisibility(isVisible && !mData.get(i).getTitle().equals("推荐") ? View.VISIBLE : View.INVISIBLE);
            }
        }
    }

    /**
     * 获取频道推荐列表的第一个频道的position
     * @return
     */
    private int getOtherFirstPosition() {
        for (int i = 0; i < mData.size(); i++) {
            Channel channel = (Channel) mData.get(i);
            if (Channel.TYPE_OTHER_CHANNEL == channel.getItemType()) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 获取我的频道列表的频道个数
     * @return
     */
    public int getMyChannelSize() {
        int size = 0;
        for (int i = 0; i <mData.size(); i++) {
            Channel channel = (Channel) mData.get(i);
            if (Channel.TYPE_MY_CHANNEL == channel.getItemType()) {
                size++;
            }
        }
        return size;
    }

    private void startAnimation(View currentView, int targetX, int targetY) {
        final ViewGroup parent = (ViewGroup) mRecyclerView.getParent();
        final ImageView mirrorView = addMirrorView(parent, currentView);
        TranslateAnimation animation = getTranslateAnimator(targetX - currentView.getLeft(), targetY - currentView.getTop());
        currentView.setVisibility(View.INVISIBLE);//暂时隐藏
        mirrorView.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                parent.removeView(mirrorView);//删出添加的镜像
                if (currentView.getVisibility() == View.INVISIBLE) {
                    currentView.setVisibility(View.VISIBLE);//显示隐藏的view
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /**
     * 添加需要移动的 镜像View
     * @param parent
     * @param currentView
     * @return
     */
    private ImageView addMirrorView(ViewGroup parent, View currentView) {
        currentView.destroyDrawingCache();
        //首先开启Cache图片 ，然后调用view.getDrawingCache()就可以获取Cache图片
        currentView.setDrawingCacheEnabled(true);
        ImageView mirrorView = new ImageView(currentView.getContext());
        //获取该view的Cache图片
        Bitmap bitmap = Bitmap.createBitmap(currentView.getDrawingCache());
        mirrorView.setImageBitmap(bitmap);
        //销毁掉Cache图片
        currentView.setDrawingCacheEnabled(false);
        int[] locations = new int[2];
        currentView.getLocationOnScreen(locations);//获取当前View的坐标
        int[] parentLocations =  new int[2];
        mRecyclerView.getLocationOnScreen(parentLocations);//获取RecycleView所在坐标
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(bitmap.getWidth(), bitmap.getHeight());
        params.setMargins(locations[0], locations[1] - parentLocations[1], 0, 0);
        parent.addView(mirrorView, params);//在RecyclerView的Parent添加我们的镜像View，parent要是FrameLayout这样才可以放到那个坐标点
        return mirrorView;
    }

    private TranslateAnimation getTranslateAnimator(int targetX, int targetY) {
        TranslateAnimation translateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.ABSOLUTE, targetX,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.ABSOLUTE, targetX);
        // RecyclerView默认移动动画250ms 这里设置360ms 是为了防止在位移动画结束后 remove(view)过早 导致闪烁
        translateAnimation.setDuration(ANIM_TIME);
        translateAnimation.setFillAfter(true);
        return translateAnimation;
    }

    /**
     * 获取我的频道列表的最后一个频道的position
     * @return
     */
    private int getMyLastPosition() {
        for (int i = mData.size(); mData.size() - 1 > -1; i--) {
            Channel channel = (Channel) mData.get(i);
            if (Channel.TYPE_MY_CHANNEL == channel.getItemType()) {
                return i;
            }
        }
        return -1;
    }
}
