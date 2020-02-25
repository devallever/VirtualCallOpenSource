package com.allever.app.virtual.call.ui

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import com.allever.app.virtual.call.R
import com.allever.app.virtual.call.app.BaseActivity
import com.allever.app.virtual.call.function.SettingHelper
import com.allever.app.virtual.call.ui.mvp.presenter.AdvancedIncomeSettingPresenter
import com.allever.app.virtual.call.ui.mvp.view.AdvancedIncomeSettingView
import com.allever.lib.common.util.SystemUtils
import com.allever.lib.common.util.toast

class AdvancedIncomeSettingActivity :
    BaseActivity<AdvancedIncomeSettingView, AdvancedIncomeSettingPresenter>(),
    AdvancedIncomeSettingView,
    View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private lateinit var mTvRingtoneTitle: TextView
    private lateinit var mTvWallPagerTitle: TextView
    private lateinit var mSwitchVibrator: SwitchCompat
    private lateinit var mSwitchRepeat: SwitchCompat
    private lateinit var mSwitchRadomContact: SwitchCompat
    private lateinit var mRepeatIntervalContainer: ViewGroup
    private lateinit var mRepeatCountContainer: ViewGroup
    private lateinit var mEtRepeatInterval: EditText
    private lateinit var mEtRepeatCount: EditText
    private lateinit var mTvAvatarPath: TextView
    private lateinit var mIvAvatar: ImageView

    override fun getContentView(): Any = R.layout.activity_advanced_income_setting

    override fun initView() {
        findViewById<View>(R.id.iv_left).setOnClickListener(this)
        findViewById<TextView>(R.id.tv_label).text = getString(R.string.advanced_setting)
        mTvRingtoneTitle = findViewById(R.id.setting_item_tv_ringtone)
        mTvRingtoneTitle.setOnClickListener(this)
        mTvWallPagerTitle = findViewById(R.id.setting_item_tv_background)
        mTvWallPagerTitle.setOnClickListener(this)
        mSwitchVibrator = findViewById(R.id.setting_item_switch_vibrator)
        mSwitchVibrator.isChecked = SettingHelper.getVibrator()
        mRepeatCountContainer = findViewById(R.id.setting_item_repeat_count)
        mRepeatIntervalContainer = findViewById(R.id.setting_item_repeat_interval)
        mSwitchRepeat = findViewById(R.id.setting_item_switch_repeat)
        mSwitchRepeat.setOnCheckedChangeListener(this)
        mSwitchRepeat.isChecked = SettingHelper.getRepeat()
        showRepeatSetting(mSwitchRepeat.isChecked)
        mSwitchRadomContact = findViewById(R.id.setting_item_switch_redom_contact)
        mSwitchRadomContact.setOnCheckedChangeListener(this)
        mSwitchRadomContact.isChecked = SettingHelper.getRandomContact()

        mEtRepeatInterval = findViewById(R.id.setting_item_et_repeat_interval)
        mEtRepeatInterval.setText(SettingHelper.getRepeatInterval().toString())
        mEtRepeatCount = findViewById(R.id.setting_item_et_repeat_count)
        mEtRepeatCount.setText(SettingHelper.getRepeatCount().toString())

        findViewById<View>(R.id.tvResetAvatar).setOnClickListener(this)
        findViewById<View>(R.id.setting_item_avatar).setOnClickListener(this)
        mTvAvatarPath = findViewById(R.id.tvAvatarPath)

        mIvAvatar = findViewById(R.id.ivAvatar)

    }

    override fun initData() {

    }

    override fun createPresenter(): AdvancedIncomeSettingPresenter =
        AdvancedIncomeSettingPresenter()

    override fun onResume() {
        super.onResume()
        mTvRingtoneTitle.text = SettingHelper.getRingtoneTitle()
        mTvWallPagerTitle.text = SettingHelper.getWallPagerTitle()
        mTvAvatarPath.text = SettingHelper.getAvatarPath()
        loadAvatar()
    }
    override fun onDestroy() {
        SettingHelper.setVibrator(mSwitchVibrator.isChecked)
        SettingHelper.setRepeat(mSwitchRepeat.isChecked)
        SettingHelper.setRepeatInterval(mEtRepeatInterval.text.toString().toIntOrNull()?: SettingHelper.getRepeatInterval())
        SettingHelper.setRepeatCount(mEtRepeatCount.text.toString().toIntOrNull()?:SettingHelper.getRepeatCount())
        SettingHelper.setRandomContact(mSwitchRadomContact.isChecked)
        SettingHelper.setAvatarPath(mTvAvatarPath.text.toString())

        setResult(Activity.RESULT_OK)
        super.onDestroy()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_left -> {
                finish()
            }
            R.id.setting_item_tv_ringtone -> {
                RingtonePickerActivity.start(this)
            }
            R.id.setting_item_tv_background -> {
                WallPagerPickerActivity.start(this)
            }
            R.id.setting_item_avatar -> {
                SystemUtils.chooseImageFromGallery(this, RC_PICK_IMAGE)
            }
            R.id.tvResetAvatar -> {
                mTvAvatarPath.text = ""
                SettingHelper.setAvatarPath("")
                loadAvatar()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                RC_PICK_IMAGE -> {
                    val uri = data?.data?.toString()?:""
                    mTvAvatarPath.text = uri
                    SettingHelper.setAvatarPath(uri)
                    loadAvatar()
                }
                else -> {
                }
            }
        }
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        when(buttonView?.id) {
            R.id.setting_item_switch_repeat -> {
                showRepeatSetting(isChecked)
            }
        }
    }

    private fun showRepeatSetting(show: Boolean) {
        if (show) {
            mRepeatCountContainer.visibility = View.VISIBLE
            mRepeatIntervalContainer.visibility = View.VISIBLE
        } else {
            mRepeatCountContainer.visibility = View.GONE
            mRepeatIntervalContainer.visibility = View.GONE
        }
    }

    private fun loadAvatar() {
        val uriString = SettingHelper.getAvatarPath()
        if (uriString.isNotEmpty()) {
            val uri = Uri.parse(uriString)
            mIvAvatar.setImageURI(uri)
        } else {
            mIvAvatar.setImageResource(R.drawable.ic_contact)
        }
    }

    companion object {
        private const val RC_PICK_IMAGE = 0x01
        fun start(context: Activity) {
            val intent = Intent(context, AdvancedIncomeSettingActivity::class.java)
            context.startActivityForResult(intent, 0)
        }
    }
}