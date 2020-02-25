package com.allever.app.virtual.call.ui

import android.Manifest
import android.os.Bundle
import com.allever.app.virtual.call.R
import com.allever.app.virtual.call.app.Global
import com.allever.app.virtual.call.bean.ContactBean
import com.allever.app.virtual.call.function.SettingHelper
import com.allever.app.virtual.call.util.SystemUtils
import com.allever.lib.common.app.BaseActivity
import com.allever.lib.common.util.ActivityCollector
import com.allever.lib.permission.PermissionManager

class SplashActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        mHandler.postDelayed({
            gotoMain()
        }, 5000)

        if (PermissionManager.hasPermissions(Manifest.permission.READ_CONTACTS)) {
            SystemUtils.getContactList()
        } else {
            Global.contactList.clear()
            val contact = ContactBean()
            contact.name = SettingHelper.getContact()?:"张三"
            contact.phone = SettingHelper.getPhone()?:"13800138000"
            Global.contactList.add(contact)
        }
    }

    override fun onBackPressed() {

    }

    private fun gotoMain() {
        ActivityCollector.startActivity(this, HomeActivity::class.java)
        finish()
    }
}