package com.allever.app.virtual.call.ui

import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.allever.app.virtual.call.R
import com.allever.app.virtual.call.app.BaseFragment
import com.allever.app.virtual.call.ui.mvp.presenter.SettingPresenter
import com.allever.app.virtual.call.ui.mvp.view.SettingView
import com.allever.lib.common.app.App
import com.allever.lib.common.util.FeedbackHelper
import com.allever.lib.common.util.ShareHelper
import com.allever.lib.common.util.Tool
import com.allever.lib.common.util.toast
import com.allever.lib.permission.PermissionUtil
import com.allever.lib.recommend.RecommendGlobal

class SettingFragment : BaseFragment<SettingView, SettingPresenter>(), SettingView,
    View.OnClickListener {

    override fun getContentView(): Int = R.layout.fragment_setting

    override fun initView(root: View) {
        root.findViewById<View>(R.id.setting_tv_share).setOnClickListener(this)
        root.findViewById<TextView>(R.id.setting_tv_feedback).setOnClickListener(this)
        root.findViewById<TextView>(R.id.setting_tv_about).setOnClickListener(this)
        root.findViewById<TextView>(R.id.setting_tv_permission).setOnClickListener(this)
        root.findViewById<TextView>(R.id.setting_tv_support).setOnClickListener(this)

    }

    override fun initData() {
    }

    override fun createPresenter(): SettingPresenter = SettingPresenter()

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.setting_tv_permission -> {
                PermissionUtil.GoToSetting(activity)
            }
            R.id.setting_tv_share -> {
                var url = RecommendGlobal.getUrl(App.context.packageName)
                if (TextUtils.isEmpty(url)) {
                    url = "https://play.google.com/store/apps/details?id=${App.context.packageName}"
                }
                val msg = getString(R.string.share_content, getString(R.string.app_name), url)
                ShareHelper.shareText(this, msg)
            }
            R.id.setting_tv_feedback -> {
                FeedbackHelper.feedback(activity)
            }
            R.id.setting_tv_about -> {
                AboutActivity.start(activity!!)
            }
            R.id.setting_tv_support -> {
                Tool.openInGooglePlay(activity!!, App.context.packageName)
//                supportUs()
            }
        }
    }
}