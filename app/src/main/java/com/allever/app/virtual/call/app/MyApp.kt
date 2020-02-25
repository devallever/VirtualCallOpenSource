package com.allever.app.virtual.call.app

import com.allever.app.virtual.call.BuildConfig
import com.allever.lib.common.app.App
import com.allever.lib.recommend.RecommendGlobal
import com.allever.lib.umeng.UMeng

class MyApp : App() {

    override fun onCreate() {
        super.onCreate()

        com.android.absbase.App.setContext(this)

        if (!BuildConfig.DEBUG) {
            UMeng.init(this)
        }

//        AdChainHelper.init(this, AdContract.adData, AdFactory())

        RecommendGlobal.init(UMeng.getChannel())
    }
}