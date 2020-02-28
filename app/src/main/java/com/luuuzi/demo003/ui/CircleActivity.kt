package com.luuuzi.demo003.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.luuuzi.demo003.R
import com.luuuzi.demo003.widget.CircleView

/**
 *    author : Luuuzi
 *    e-mail : wang1143303@163.com
 *    date   : 2020/2/28 0028 14:18
 *    desc   : 自定义view
 */
class CircleActivity :AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_circle)
        val circleView = CircleView(this)
    }
}