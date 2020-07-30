package com.wsy.jireading.widget;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatEditText;

import com.wsy.jireading.R;

/**
 * Created by songYangWu
 * Date:2020/7/20
 *
 * 自定义搜索框
 */
public class EditTextSearch extends AppCompatEditText {

    private Drawable searchDrawable; // 左侧图标
    private Drawable clearDrawable; // 一键删除图标
    private Drawable voiceDrawable; // 语音图标

    private Context mContext;
    private Boolean isShowClear;

    public EditTextSearch(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public EditTextSearch(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public EditTextSearch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    /**
     * 初始化 图标资源
     */
    private void init() {
        searchDrawable = getResources().getDrawable(R.drawable.ic_search);
        clearDrawable = getResources().getDrawable(R.drawable.ic_delete);
        voiceDrawable = getResources().getDrawable(R.drawable.ic_voice);

        setCompoundDrawablesWithIntrinsicBounds(searchDrawable, null, voiceDrawable, null);
        // setCompoundDrawablesWithIntrinsicBounds(Drawable left, Drawable top, Drawable right, Drawable bottom)
        // 在EditText左、上右、下设置图标（相当于android:drawableLeft=""  android:drawableRight=""）若不想在某个地方显示，则设置为null
    }

    /**
     * 通过重写EditText本身的方法来确定是否显示删除图标
     * onTextChanged（）--当输入框文本内容变化时
     * @param text
     * @param start
     * @param lengthBefore
     * @param lengthAfter
     */
    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        setClearIconVisible(hasFocus() && text.length() > 0);
        // hasFocus()--返回是否获得EditTEXT的焦点，即是否选中
    }

    /**
     * onFocusChanged()--焦点发生变化时
     * @param focused
     * @param direction
     * @param previouslyFocusedRect
     */
    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        setClearIconVisible(focused && length() >0);
        // focused = 是否获得焦点
    }

    /**
     * 判断是否显示一键删除图标
     * 当显示一键删除图标时就隐藏语音图标
     * @param visible true时显示一键删除图标 false显示语音图标
     */
    private void setClearIconVisible(boolean visible) {
        isShowClear = visible;
        setCompoundDrawablesWithIntrinsicBounds(searchDrawable, null, visible ? clearDrawable : voiceDrawable, null);
    }

    /**
     * 对删除图标区域设置点击事件，即 点击 = 清空搜索框内容
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                //isShowClear：为true,点击一键删除图标；为false，点击语音图标
                if (isShowClear) {
                    Drawable drawable = clearDrawable;
                    if (drawable != null && event.getX() <= (getWidth() - getPaddingRight()) && event.getX() >= (getWidth() - getPaddingRight() - drawable.getBounds().width())) {
                        setText("");
                    }
                } else {
                    Drawable drawable = voiceDrawable;
                    if (drawable != null && event.getX() <= (getWidth() - getPaddingRight()) && event.getX() >= (getWidth() - getPaddingRight() - drawable.getBounds().width())) {

                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}
