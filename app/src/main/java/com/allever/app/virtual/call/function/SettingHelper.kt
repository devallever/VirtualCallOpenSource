package com.allever.app.virtual.call.function

import com.allever.app.virtual.call.util.SharedPrefUtils

object SettingHelper {

    private const val KEY_PHONE = "PHONE"
    private const val KEY_TIME = "TIME"
    private const val KEY_CONTACT = "CONTACT"
    private const val KEY_LOCAL = "LOCAL"
    private const val KEY_RINGTONE_URI = "KEY_RINGTONE_URI"
    private const val KEY_RINGTONE_TITLE = "KEY_RINGTONG_TITLE"
    private const val KEY_VIBRATOR = "KEY_VIBRATOR"
    private const val KEY_WALL_PAGER_TITLE = "KEY_WALL_PAGER_TITLE"
    private const val KEY_REPEAT_SWITCH = "KEY_REPEAT_SWITCH"
    private const val KEY_REPEAT_INTERVAL = "KEY_REPEAT_INTERVAL"
    private const val KEY_REPEAT_COUNT = "KEY_REPEAT_COUNT"
    private const val KEY_RADOM_CONTACT_SWITCH = "KEY_RADOM_CONTACT_SWITCH"
    private const val KEY_AVATAR = "KEY_AVATAR"

    fun setPhone(phone: String) {
        SharedPrefUtils.putString(KEY_PHONE, phone)
    }

    fun getPhone(): String? {
        return SharedPrefUtils.getString(KEY_PHONE, "13800138000")
    }

    fun setContact(contact: String) {
        SharedPrefUtils.putString(KEY_CONTACT, contact)
    }

    fun getContact(): String? {
        return SharedPrefUtils.getString(KEY_CONTACT, "张三")
    }

    fun setLocal(local: String) {
        SharedPrefUtils.putString(KEY_LOCAL, local)
    }

    fun getLocal(): String? {
        return SharedPrefUtils.getString(KEY_LOCAL, "北京")
    }

    fun setTime(time: Int) {
        SharedPrefUtils.putInt(KEY_TIME, time)
    }

    fun getTime(): Int? {
        return SharedPrefUtils.getInt(KEY_TIME, 5)
    }



    fun setRingtoneUri(uri: String) {
        SharedPrefUtils.putString(KEY_RINGTONE_URI, uri)
    }

    fun getRingtoneUri(): String? {
        return SharedPrefUtils.getString(KEY_RINGTONE_URI, "")
    }


    fun setRingtoneTitle(ringtoneTitle: String) {
        SharedPrefUtils.putString(KEY_RINGTONE_TITLE, ringtoneTitle)
    }

    fun getRingtoneTitle(): String? {
        return SharedPrefUtils.getString(KEY_RINGTONE_TITLE, "默认")
    }

    fun setWallPagerTitle(wallPagerTitle: String) {
        SharedPrefUtils.putString(KEY_WALL_PAGER_TITLE, wallPagerTitle)
    }

    fun getWallPagerTitle(): String {
        return SharedPrefUtils.getString(KEY_WALL_PAGER_TITLE, "默认")
    }

    fun setVibrator(vibrator: Boolean) {
        SharedPrefUtils.putBoolean(KEY_VIBRATOR, vibrator)
    }

    fun getVibrator(): Boolean {
        return SharedPrefUtils.getBoolean(KEY_VIBRATOR, true)
    }

    /*
        private const val KEY_REPEAT_SWITCH = "KEY_REPEAT_SWITCH"
    private const val KEY_REPEAT_INTERVAL = "KEY_REPEAT_INTERVAL"
    private const val KEY_REPEAT_COUNT = "KEY_REPEAT_COUNT"

     */

    fun setRepeat(repeat: Boolean) {
        SharedPrefUtils.putBoolean(KEY_REPEAT_SWITCH, repeat)
    }
    fun getRepeat(): Boolean {
        return SharedPrefUtils.getBoolean(KEY_REPEAT_SWITCH, false)
    }

    fun setRepeatInterval(interval: Int) {
        SharedPrefUtils.putInt(KEY_REPEAT_INTERVAL, interval)
    }
    fun getRepeatInterval(): Int {
        return SharedPrefUtils.getInt(KEY_REPEAT_INTERVAL, 2)
    }

    fun setRepeatCount(interval: Int) {
        SharedPrefUtils.putInt(KEY_REPEAT_COUNT, interval)
    }
    fun getRepeatCount(): Int {
        return SharedPrefUtils.getInt(KEY_REPEAT_COUNT, 2)
    }

    fun setRandomContact(random: Boolean) {
        SharedPrefUtils.putBoolean(KEY_RADOM_CONTACT_SWITCH, random)
    }
    fun getRandomContact(): Boolean {
        return SharedPrefUtils.getBoolean(KEY_RADOM_CONTACT_SWITCH, false)
    }
    fun setAvatarPath(avatar: String) {
        SharedPrefUtils.putString(KEY_AVATAR, avatar)
    }

    fun getAvatarPath(): String {
        return SharedPrefUtils.getString(KEY_AVATAR, "")
    }

}