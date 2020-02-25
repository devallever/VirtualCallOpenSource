package com.allever.app.virtual.call.ui.mvp.view

import com.allever.app.virtual.call.bean.WallPagerItem

interface WallPagerPickerView {
    fun refreshWallPagerList(data: MutableList<WallPagerItem>, saveIndex: Int)
}