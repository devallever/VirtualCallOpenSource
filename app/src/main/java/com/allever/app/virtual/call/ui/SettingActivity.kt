package com.allever.app.virtual.call.ui

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.allever.app.virtual.call.R
import com.allever.app.virtual.call.app.BaseActivity
import com.allever.app.virtual.call.ui.mvp.presenter.SettingPresenter
import com.allever.app.virtual.call.ui.mvp.view.SettingView
import com.allever.app.virtual.call.util.SystemUtils
import com.allever.lib.common.util.FeedbackHelper

class SettingActivity : BaseActivity<SettingView, SettingPresenter>(),
    SettingView, View.OnClickListener {
    override fun getContentView(): Any = R.layout.activity_setting

    override fun initView() {
        findViewById<View>(R.id.setting_tv_share).setOnClickListener(this)
        findViewById<TextView>(R.id.setting_tv_feedback).setOnClickListener(this)
        findViewById<TextView>(R.id.setting_tv_about).setOnClickListener(this)
        findViewById<ImageView>(R.id.iv_left).setOnClickListener(this)
        findViewById<TextView>(R.id.tv_label).text = getString(R.string.setting)
    }

    override fun initData() {
    }

    override fun createPresenter(): SettingPresenter =
        SettingPresenter()

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.setting_tv_share -> {
                val intent = SystemUtils.getShareIntent(this, "输入分享内容")
                startActivity(intent)
            }
            R.id.setting_tv_feedback -> {
                FeedbackHelper.feedback(this)
            }
            R.id.setting_tv_about -> {
                AboutActivity.start(this)
            }
            R.id.iv_left -> {
                finish()
            }

        }
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, SettingActivity::class.java)
            context.startActivity(intent)
        }
    }
}