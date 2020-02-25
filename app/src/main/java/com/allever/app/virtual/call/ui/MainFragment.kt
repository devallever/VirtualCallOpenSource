package com.allever.app.virtual.call.ui

import android.Manifest
import android.app.Activity
import android.view.View
import android.widget.EditText
import com.allever.app.virtual.call.R
import com.allever.app.virtual.call.app.BaseFragment
import com.allever.app.virtual.call.app.Global
import com.allever.app.virtual.call.function.SettingHelper
import com.allever.app.virtual.call.service.VirtualCallService
import com.allever.app.virtual.call.ui.mvp.presenter.MainPresenter
import com.allever.app.virtual.call.ui.mvp.view.MainView
import com.allever.lib.common.app.App
import com.allever.lib.common.util.ActivityCollector
import android.provider.ContactsContract
import android.content.Intent
import android.content.pm.PackageManager
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.allever.lib.common.util.toast
import com.allever.lib.permission.PermissionManager


class MainFragment : BaseFragment<MainView, MainPresenter>(),
    MainView, View.OnClickListener {
    private lateinit var mEtTime: EditText
    private lateinit var mEtPhone: EditText
    private lateinit var mEtContact: EditText
    private lateinit var mEtLocal: EditText
    private lateinit var mTvChooseContact: TextView

    override fun getContentView(): Int = R.layout.fragment_main

    override fun initView(root: View) {
        root.findViewById<View>(R.id.setting_btn_start).setOnClickListener(this)
        val tvAdvanceSetting = root.findViewById<TextView>(R.id.setting_item_advanced)
        tvAdvanceSetting.setOnClickListener(this)
        tvAdvanceSetting.setText(
            "${getString(R.string.ringtone)}/${getString(R.string.vibrate)}/${getString(R.string.wallpaper)}/${getString(R.string.repeat)}"
        )
        mEtTime = root.findViewById(R.id.setting_item_et_time)
        mEtPhone = root.findViewById(R.id.setting_item_et_phone)
        mEtContact = root.findViewById(R.id.setting_item_et_contact)
        mEtLocal = root.findViewById(R.id.setting_item_et_local)

        mEtTime.setText(SettingHelper.getTime().toString())
        mEtPhone.setText(SettingHelper.getPhone())
        mEtContact.setText(SettingHelper.getContact())
        mEtLocal.setText(SettingHelper.getLocal())
        root.findViewById<View>(R.id.tvChooseContact).setOnClickListener(this)

    }

    override fun initData() {}

    override fun createPresenter(): MainPresenter = MainPresenter()

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.setting_btn_start -> {
                saveIncomeSetting()
                Global.leftRepeatCount = SettingHelper.getRepeatCount()
                VirtualCallService.start(App.context)
                ActivityCollector.finishAll()
            }
            R.id.setting_item_advanced -> {
                AdvancedIncomeSettingActivity.start(activity!!)
            }
            R.id.tvChooseContact -> {
                if (PermissionManager.hasPermissions(Manifest.permission.READ_CONTACTS)) {
                    try {
                        val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
                        startActivityForResult(intent, PICK_CONTACT)
                    } catch (e: Exception) {
                        toast("没有找到联系人的软件，请手动输入")
                        e.printStackTrace()
                    }
                } else {
                    requestAdPermission(activity!!, getString(R.string.permission_tips))
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        mPresenter.getContacts(data ?: return)
    }

    override fun onDestroy() {
        saveIncomeSetting()
        super.onDestroy()
    }

    private fun saveIncomeSetting() {
        SettingHelper.setTime(mEtTime.text.toString().toIntOrNull() ?: SettingHelper.getTime() ?: 5)
        SettingHelper.setPhone(mEtPhone.text.toString())
        SettingHelper.setContact(mEtContact.text.toString())
        SettingHelper.setLocal(mEtLocal.text.toString())
    }

    override fun updateContact(name: String) {
        mEtContact.setText(name)
    }

    override fun updatePhone(phone: String) {
        mEtPhone.setText(phone)
    }

    companion object {
        private const val PICK_CONTACT = 0X01
    }

    fun requestAdPermission(activity: Activity, msg: String) {
        // 如果api >= 23 需要显式申请权限
        if (ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.READ_PHONE_STATE
            ) !== PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) !== PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.READ_CONTACTS
            ) !== PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.INTERNET
            ) !== PackageManager.PERMISSION_GRANTED
        ) {

            AlertDialog.Builder(activity)
                .setMessage(msg)
                .setPositiveButton("同意") { dialog, which ->
                    dialog.dismiss()
                    ActivityCompat.requestPermissions(
                        activity,
                        arrayOf(
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.READ_CONTACTS
                        ),
                        0
                    )
                }
                .setNegativeButton("拒绝") { dialog, which ->
                    dialog.dismiss()
                }
                .create()
                .show()
        }
    }

}