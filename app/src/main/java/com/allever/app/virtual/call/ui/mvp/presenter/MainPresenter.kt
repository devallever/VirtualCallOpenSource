package com.allever.app.virtual.call.ui.mvp.presenter

import android.content.Intent
import android.provider.ContactsContract
import com.allever.app.virtual.call.ui.mvp.view.MainView
import com.allever.lib.common.app.App
import com.allever.lib.common.mvp.BasePresenter
import com.allever.lib.common.util.log
import com.allever.lib.common.util.toast

class MainPresenter : BasePresenter<MainView>() {

    fun getContacts(data: Intent) {
        val contactUri = data.data ?: return
        var phoneNumber = ""
        var name = ""
        val cursor = App.context.contentResolver.query(contactUri, null, null, null, null);
        if (cursor?.moveToFirst() == true) {
            try {
                name = cursor
                    .getString(
                        cursor
                            .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                    )
                mViewRef?.get()?.updateContact(name)
                var hasPhone = cursor
                    .getString(
                        cursor
                            .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)
                    )
                val id = cursor.getString(
                    cursor
                        .getColumnIndex(ContactsContract.Contacts._ID)
                )
                hasPhone = if (hasPhone == "1") {
                    "true"
                } else {
                    "false"
                }
                if (hasPhone.toBoolean()) {
                    val phones = App.context.contentResolver
                        .query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                    + " = " + id, null, null
                        )
                    if (phones?.moveToNext() == true) {
                        phoneNumber = phones
                            .getString(
                                phones
                                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                            )
                        log("$name : $phoneNumber")
                        mViewRef?.get()?.updatePhone(phoneNumber)
                    }
                    phones?.close()
                }
                cursor.close()
            } catch (e: Exception) {
                e.printStackTrace()
                toast("请选择合法的联系人")
            } finally {
                cursor.close()
            }
        }
    }

}