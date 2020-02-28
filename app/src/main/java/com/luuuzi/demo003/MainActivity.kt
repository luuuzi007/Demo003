package com.luuuzi.demo003

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.luuuzi.demo003.ui.CircleActivity
import com.luuuzi.demo003.ui.FlowActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<AppCompatTextView>(R.id.tv1).setOnClickListener(this)
        findViewById<AppCompatTextView>(R.id.tv2).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv1 -> {
                startActivity(Intent(this, CircleActivity::class.java))
            }
            R.id.tv2 ->{
                startActivity(Intent(this,FlowActivity::class.java))
            }
        }
    }
}
