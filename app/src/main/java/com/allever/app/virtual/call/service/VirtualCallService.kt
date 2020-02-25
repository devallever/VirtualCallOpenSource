package com.allever.app.virtual.call.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import com.allever.app.virtual.call.function.SettingHelper
import com.allever.app.virtual.call.ui.IncomeCallActivity
import com.allever.lib.common.util.log

class VirtualCallService : Service() {
    companion object {
        private const val MSG_VIRTUAL_CALL = 0x01
        private const val EXTRA_IS_REPEAT = "EXTRA_IS_REPEAT"

        fun start(context: Context, isRepeat: Boolean = false) {
            val intent = Intent(context, VirtualCallService::class.java)
            intent.putExtra(EXTRA_IS_REPEAT, isRepeat)
            context.startService(intent)
        }
    }

    private var mHandler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message?) {
            when (msg?.what) {
                MSG_VIRTUAL_CALL -> {
                    //启动界面
                    log("启动来电界面")
                    IncomeCallActivity.start(this@VirtualCallService)
                }
                else -> {

                }
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mHandler.removeMessages(MSG_VIRTUAL_CALL)
        val needRepeat = intent?.getBooleanExtra(EXTRA_IS_REPEAT, false)
        val second = if (needRepeat == true) {
            SettingHelper.getRepeatInterval()
        } else {
            SettingHelper.getTime() ?: 5
        }
//        val second = SettingHelper.getTime() ?: 5
        mHandler.removeMessages(MSG_VIRTUAL_CALL)
        mHandler.sendEmptyMessageDelayed(MSG_VIRTUAL_CALL, (second * 1000).toLong())
        log("发送延时消息")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}