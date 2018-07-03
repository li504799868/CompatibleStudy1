package com.lzp.scrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ScrollView;

/**
 * @author li.zhipeng
 */
public class MyScrollview extends ScrollView {

    private OnScrollToBottomListener onScrollToBottom;

    public MyScrollview(Context context) {
        super(context);
    }

    public MyScrollview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 重写此方法，监听ScrollView滑动到底部的事件
     * */
    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX,
                                  boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        // scrollY != 0 是为了排除滑动到顶部clampedY = true的情况
        if (scrollY != 0 && null != onScrollToBottom) {
            // 如果是false，需要额外判断是否正好滑动到底部，解决部分手机的兼容问题，例如锤子
            if (!clampedY) {
                // 滑动的距离加上展示内容的高度 是否等于 第一个子View的高度
                if (getScrollY() + getHeight() - getPaddingTop() - getPaddingBottom() == getChildAt(0).getHeight()) {
                    onScrollToBottom.onScrollBottomListener(true);
                    return;
                }
            }
            onScrollToBottom.onScrollBottomListener(clampedY);
        }
    }

    public void setOnScrollToBottomLintener(OnScrollToBottomListener listener) {
        onScrollToBottom = listener;
    }

    public interface OnScrollToBottomListener {
        /**
         * 滚动到底部
         *
         * @param isBottom 是否到底部
         */
        void onScrollBottomListener(boolean isBottom);
    }
}