package com.luuuzi.demo003.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.annotation.StyleableRes
import com.luuuzi.demo003.R

/**
 *    author : Luuuzi
 *    e-mail : wang1143303@163.com
 *    date   : 2020/2/28 0028 14:25
 *    desc   : 自定义
 */
class CircleView : View {
    var tag: String = javaClass.simpleName
    val minWidth: Int = 300
    val minHeight: Int = 300
    var defaultColor: Int = Color.RED

    lateinit var mPaint: Paint

    constructor(context: Context?) : this(context, null) {
        Log.i(tag, "我的一个参数的构造方法")
    }

    constructor(context: Context?, attributeSet: AttributeSet?) : this(context, attributeSet, 0) {
        Log.i(tag, "我是2个参数的构造方法")
    }

    constructor(context: Context?, attributeSet: AttributeSet?, default: Int) : super(
        context,
        attributeSet,
        default
    ) {
        Log.i(tag, "我的3个参数的构造方法")
        val obtainStyledAttributes =
            context?.obtainStyledAttributes(attributeSet, R.styleable.dfafa)
        defaultColor = obtainStyledAttributes?.getColor(R.styleable.dfafa_circle_color, Color.RED)!!
        obtainStyledAttributes.recycle()//防止内存泄漏
        init()
    }

    private fun init() {
        mPaint = Paint()
        mPaint.color = defaultColor
    }

    /**
     * 解决wrap_content：无效问题
     * kt 版本
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        //当宽高设置为wrap_content 时指定一个默认宽高
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(minWidth, minHeight)
        } else if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(minWidth, heightSize)
        } else if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSize, minHeight)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        var radius: Int =
            Math.min(width - paddingLeft - paddingRight, height - paddingTop - paddingBottom) / 2
        canvas?.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), radius.toFloat(), mPaint)

    }
}