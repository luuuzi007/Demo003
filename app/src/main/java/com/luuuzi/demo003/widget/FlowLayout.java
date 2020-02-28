package com.luuuzi.demo003.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * author : Luuuzi
 * e-mail : wang1143303@163.com
 * date   : 2020/2/26 0026 11:10
 * desc   : 自定义流式布局
 */
public class FlowLayout extends ViewGroup {
    private String tag = getClass().getSimpleName();
    private float dashWDefine = 0;//子view默认宽间距
    private float dashHDefine = 0;//子view默认高间距

    private ArrayList<ArrayList<View>> viewLines = null;
    private ArrayList<Integer> viewHs = null;

    // new view的时候调用
    public FlowLayout(Context context) {
        super(context);
    }

    //反射解析xml布局的时候调用
    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //更换主题的时候调用
    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initMeasure() {
        if (viewLines == null) {
            viewLines = new ArrayList<>();
        } else {
            viewLines.clear();
        }
        if (viewHs == null) {
            viewHs = new ArrayList<>();
        } else {
            viewHs.clear();
        }
        dashWDefine = 20;
        dashHDefine = 30;
    }

    /**
     * 1.先测量子view
     * 2.在测量自己
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @SuppressLint("DrawAllocation")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i(tag, "onMeasure:");
        initMeasure();
        int pWMode = MeasureSpec.getMode(widthMeasureSpec);
        int pHMode = MeasureSpec.getMode(heightMeasureSpec);
        int pWSize = MeasureSpec.getSize(widthMeasureSpec);
        int pHSize = MeasureSpec.getSize(heightMeasureSpec);

        int currentUsedW = getPaddingLeft() + getPaddingRight();//记录已经用掉的宽度
        //测量子view
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            LayoutParams childLp = childAt.getLayoutParams();

            int childWMS = getChildMeasureSpec(widthMeasureSpec, getPaddingLeft() + getPaddingRight(), childLp.width);
            int childHMS = getChildMeasureSpec(heightMeasureSpec, getPaddingTop() + getPaddingBottom(), childLp.height);
            childAt.measure(childWMS, childHMS);

            //测量摆放空间(将子view 分行存储)
            if (viewLines.isEmpty() || currentUsedW + childAt.getMeasuredWidth() + dashWDefine > pWSize) {//新的一行
                ArrayList<View> views = new ArrayList<>();

                views.add(childAt);
                viewLines.add(views);
                viewHs.add(childAt.getMeasuredHeight());//存高度

                currentUsedW = getPaddingLeft() + getPaddingRight() + childAt.getMeasuredWidth() + (int) dashWDefine;

            } else {//同一行
                viewLines.get(viewLines.size() - 1).add(childAt);
                //存一行中的最大高度
                viewHs.set(viewHs.size() - 1, viewHs.get(viewHs.size() - 1) > childAt.getMeasuredHeight() ? viewHs.get(viewHs.size() - 1) : childAt.getMeasuredHeight());
                currentUsedW += childAt.getMeasuredWidth() + dashWDefine;
            }
        }

        //测量自己
        int meW;
        int meH;
//        if (pWMode == MeasureSpec.EXACTLY) {//精确模式
//            meW = pWSize;
//        } else {
        meW = getCurrentMaxLineW();
//        }
//        if (pHMode == MeasureSpec.EXACTLY) {//精确模式
//            meH = pHSize;
//        } else {
        meH = getcountChildHeight();
//        }
        setMeasuredDimension(meW, meH);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (viewLines == null || viewHs == null) {
            return;
        }
        int curTop = getPaddingTop();//view布局所对应的top坐标

        for (int i = 0; i < viewHs.size(); i++) {//一行一行开始摆放
            int curLeft = getPaddingLeft();//view布局所对应的left坐标
            curTop += i == 0 ? 0 : viewHs.get(i - 1) + dashHDefine;
            ArrayList<View> views = viewLines.get(i);
            for (int j = 0; j < views.size(); j++) {//对每行子view进行布局
                View child = views.get(j);
                child.layout(curLeft, curTop, curLeft + child.getMeasuredWidth(), curTop + child.getMeasuredHeight());
                curLeft += child.getMeasuredWidth() + dashWDefine;
            }
        }
    }

    // 获取所有行的最大宽度
    private int getCurrentMaxLineW() {
        if (viewLines.isEmpty()) {
            return 0;
        } else {
            int max = 0;
            for (int i = 0; i < viewLines.size(); i++) {
                ArrayList<View> views = viewLines.get(i);
                int w = 0;
                for (int j = 0; j < views.size(); j++) {
                    w += views.get(j).getMeasuredWidth() + (int) dashWDefine;
                }
                max = Math.max(w, max);
            }
            return max + getPaddingLeft() + getPaddingRight();
        }
    }

    private int getcountChildHeight() {
        if (viewHs.isEmpty()) {
            return 0;
        } else {
            int h = 0;
            for (int i = 0; i < viewHs.size(); i++) {
                h += viewHs.get(i) + dashHDefine;
            }
            return h + getPaddingTop() + getPaddingBottom();
        }
    }
}
