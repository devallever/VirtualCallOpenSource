package com.allever.app.virtual.call.ui.mvp.presenter

import com.allever.app.virtual.call.R
import com.allever.app.virtual.call.app.Global
import com.allever.app.virtual.call.bean.WallPagerItem
import com.allever.app.virtual.call.function.SettingHelper
import com.allever.app.virtual.call.ui.mvp.view.WallPagerPickerView
import com.allever.lib.common.mvp.BasePresenter

class WallPagerPickerPresenter : BasePresenter<WallPagerPickerView>() {
    fun getWallPagerData() {
        if (Global.wallPagerItemList.isNotEmpty()) {
            Global.wallPagerItemList.map {
                it.checked = false
            }
        } else {
            var item = WallPagerItem()
            item.title = "默认"
            item.checked = false
            item.resId = R.drawable.default_bg
            Global.wallPagerItemList.add(item)

            item = WallPagerItem()
            item.title = "Xiaomi"
            item.checked = false
            item.resId = R.drawable.xiaomi_bg
            Global.wallPagerItemList.add(item)

            item = WallPagerItem()
            item.title = "HUAWEI"
            item.checked = false
            item.resId = R.drawable.huawei_bg
            Global.wallPagerItemList.add(item)

            item = WallPagerItem()
            item.title = "OPPO"
            item.checked = false
            item.resId = R.drawable.oppo_bg
            Global.wallPagerItemList.add(item)

            item = WallPagerItem()
            item.title = "VIVO"
            item.checked = false
            item.resId = R.drawable.vivo_bg
            Global.wallPagerItemList.add(item)

            Global.wallPagerItemList.map {
                Global.wallPagerItemMap[it.title!!] = it
            }
        }

        val selectedItem = Global.wallPagerItemMap[SettingHelper.getWallPagerTitle()]
        selectedItem?.checked = true
        var mSaveRingtoneItemPosition = 0
        if (selectedItem != null) {
            mSaveRingtoneItemPosition = Global.wallPagerItemList.indexOf(selectedItem)
        }

        mViewRef?.get()?.refreshWallPagerList(Global.wallPagerItemList, mSaveRingtoneItemPosition)
    }

    fun saveWallPager(wallPagerItem: WallPagerItem) {
        SettingHelper.setWallPagerTitle(wallPagerItem.title!!)
    }
}