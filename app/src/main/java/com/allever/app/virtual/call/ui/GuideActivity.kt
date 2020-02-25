package com.allever.app.virtual.call.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.allever.app.virtual.call.R
import com.allever.lib.common.app.BaseActivity

class GuideActivity : BaseActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide)

        findViewById<View>(R.id.iv_left).setOnClickListener(this)
        findViewById<TextView>(R.id.tv_label).text = "使用说明"

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_left -> {
                finish()
            }
        }
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, GuideActivity::class.java)
            context.startActivity(intent)
        }
    }
}