package com.wsy.jireading.listener;

/**
 * Created by songYangWu
 * Date:2020/7/28
 */
public interface OnChannelListener {
    void onItemMove(int startPos, int endPos);
    void onMoveToMyChannel(int startPos, int endPos);
    void onMoveToOtherChannel(int startPos, int endPos);
}
