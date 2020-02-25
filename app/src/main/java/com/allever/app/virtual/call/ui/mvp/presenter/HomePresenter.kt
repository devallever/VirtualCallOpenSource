package com.allever.app.virtual.call.ui.mvp.presenter

import android.Manifest
import android.app.Activity
import androidx.appcompat.app.AlertDialog
import com.allever.app.virtual.call.R
import com.allever.app.virtual.call.ui.mvp.view.HomeView
import com.allever.lib.common.mvp.BasePresenter
import com.allever.lib.common.util.toast
import com.allever.lib.permission.PermissionListener
import com.allever.lib.permission.PermissionManager
import com.allever.lib.permission.PermissionUtil

class HomePresenter: BasePresenter<HomeView>() {
    fun requestPermission(activity: Activity) {
        if (!PermissionManager.hasPermissions(
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_CONTACTS
            )
        ) {
            //弹窗
            AlertDialog.Builder(activity)
                .setMessage(R.string.permission_tips)
                .setPositiveButton(R.string.permission_accept) { dialog, which ->
                    dialog.dismiss()
                    PermissionManager.request(
                        object : PermissionListener {
                            override fun onGranted(grantedList: MutableList<String>) {}

                            override fun onDenied(deniedList: MutableList<String>) {
                                super.onDenied(deniedList)
                                toast("拒绝权限将不能使用某些功能")
                            }

                        }, Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.READ_CONTACTS
                    )
                }
                .setNegativeButton(R.string.permission_reject) { dialog, which ->
                    dialog.dismiss()
                    toast("拒绝权限将不能使用某些功能")
                }
                .create()
                .show()
        } else {
            //TODO 好像不起作用
            //检查唤醒
            if (!PermissionManager.hasPermissions(Manifest.permission.WAKE_LOCK)) {
//弹窗
                AlertDialog.Builder(activity)
                    .setMessage("若要使用屏锁来电功能，请允许【锁屏显示】的权限")
                    .setPositiveButton("前往设置") { dialog, which ->
                        dialog.dismiss()
                        PermissionUtil.GoToSetting(activity)
                    }
                    .setNegativeButton(R.string.permission_reject) { dialog, which ->
                        dialog.dismiss()
                        toast("屏锁来电功能暂时无法使用，请前往设置。")
                    }
                    .create()
                    .show()
            }
        }
    }
}