package com.allever.app.virtual.call.ui

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.TextView
import com.allever.app.virtual.call.BuildConfig
import com.allever.app.virtual.call.R
import com.allever.app.virtual.call.app.BaseActivity
import com.allever.app.virtual.call.ui.mvp.presenter.AboutPresenter
import com.allever.app.virtual.call.ui.mvp.view.AboutView
import com.allever.app.virtual.call.util.SystemUtils
import com.allever.lib.common.app.App

class AboutActivity : BaseActivity<AboutView, AboutPresenter>(),
    AboutView, View.OnClickListener {


    override fun getContentView(): Any = R.layout.activity_about

    override fun initView() {
        findViewById<View>(R.id.about_privacy).setOnClickListener(this)
        findViewById<View>(R.id.iv_left).setOnClickListener(this)
        findViewById<TextView>(R.id.tv_label).text = getString(R.string.about)
        val channel = SystemUtils.getManifestDataByKey("UMENG_CHANNEL")
        val last = if (BuildConfig.DEBUG) {
            "(Debug)-$channel"
        } else {
            ""
        }
        findViewById<TextView>(R.id.about_app_version).text = "v${BuildConfig.VERSION_NAME}$last"
    }

    override fun initData() {
    }

    override fun createPresenter(): AboutPresenter =
        AboutPresenter()

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_left -> {
                finish()
            }
            R.id.about_privacy -> {
                val privacyUrl = "http://x.xiniubaba.com/doc/b561db61e0dd43fc110f6ccf0cfe1f16_sa.php"
                SystemUtils.startWebView(App.context, privacyUrl)
            }
        }
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, AboutActivity::class.java)
            context.startActivity(intent)
        }
    }
}