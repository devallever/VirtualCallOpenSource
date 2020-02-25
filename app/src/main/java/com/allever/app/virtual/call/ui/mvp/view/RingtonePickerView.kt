package com.allever.app.virtual.call.ui.mvp.view

import com.allever.app.virtual.call.bean.RingtoneItem

interface RingtonePickerView {
    fun refreshRingtoneList(data: MutableList<RingtoneItem>, saveIndex: Int)
}