package com.allever.app.virtual.call.ui

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.allever.app.virtual.call.R
import com.allever.app.virtual.call.app.BaseActivity
import com.allever.app.virtual.call.function.SettingHelper
import com.allever.app.virtual.call.ui.mvp.presenter.InComeCallSettingPresenter
import com.allever.app.virtual.call.ui.mvp.view.InComeCallSettingView

class InComeCallSettingActivity : BaseActivity<InComeCallSettingView, InComeCallSettingPresenter>(),
    InComeCallSettingView, View.OnClickListener {

    private lateinit var mEtTime: EditText
    private lateinit var mEtPhone: EditText
    private lateinit var mEtContact: EditText
    private lateinit var mEtLocal: EditText
    override fun getContentView(): Any = R.layout.activity_in_come_call_setting

    override fun initView() {
        findViewById<TextView>(R.id.tv_label).text = getString(R.string.setting_in_come_call)
        findViewById<View>(R.id.iv_left).setOnClickListener(this)

        mEtTime = findViewById(R.id.setting_item_et_time)
        mEtPhone = findViewById(R.id.setting_item_et_phone)
        mEtContact = findViewById(R.id.setting_item_et_contact)
        mEtLocal = findViewById(R.id.setting_item_et_local)

        mEtTime.setText(SettingHelper.getTime().toString())
        mEtPhone.setText(SettingHelper.getPhone())
        mEtContact.setText(SettingHelper.getContact())
        mEtLocal.setText(SettingHelper.getLocal())
    }

    override fun initData() {
    }

    override fun createPresenter(): InComeCallSettingPresenter =
        InComeCallSettingPresenter()

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_left -> {
                finish()
            }
        }
    }

    override fun onDestroy() {
        SettingHelper.setTime(mEtTime.text.toString().toIntOrNull() ?: 5)
        SettingHelper.setPhone(mEtPhone.text.toString())
        SettingHelper.setContact(mEtContact.text.toString())
        SettingHelper.setLocal(mEtLocal.text.toString())
        super.onDestroy()
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, InComeCallSettingActivity::class.java)
            context.startActivity(intent)
        }
    }
}