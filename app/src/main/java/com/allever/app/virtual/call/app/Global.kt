package com.allever.app.virtual.call.app

import com.allever.app.virtual.call.bean.ContactBean
import com.allever.app.virtual.call.bean.RingtoneItem
import com.allever.app.virtual.call.bean.WallPagerItem
import com.allever.app.virtual.call.function.SettingHelper

object Global {
    var ringtoneItemList = mutableListOf<RingtoneItem>()
    var ringtoneItemMap = mutableMapOf<String, RingtoneItem>()

    var wallPagerItemList = mutableListOf<WallPagerItem>()
    var wallPagerItemMap = mutableMapOf<String, WallPagerItem>()

    var leftRepeatCount = SettingHelper.getRepeatCount()

    var contactList = mutableListOf<ContactBean>()
}