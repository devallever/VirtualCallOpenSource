package com.allever.app.virtual.call.ui

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.allever.app.virtual.call.R
import com.allever.app.virtual.call.app.BaseActivity
import com.allever.app.virtual.call.bean.RingtoneItem
import com.allever.app.virtual.call.ui.adapter.RingtoneAdapter
import com.allever.app.virtual.call.ui.mvp.presenter.RingtonePickerPresenter
import com.allever.app.virtual.call.ui.mvp.view.RingtonePickerView

import com.allever.lib.common.ui.widget.recycler.BaseViewHolder
import com.allever.lib.common.ui.widget.recycler.ItemListener

class RingtonePickerActivity : BaseActivity<RingtonePickerView, RingtonePickerPresenter>(),
    RingtonePickerView,
    View.OnClickListener {

    private lateinit var mAdapter: RingtoneAdapter
    private lateinit var mRv: RecyclerView
    private var mSelectedPosition = 0
    private val mRingtoneItemList = mutableListOf<RingtoneItem>()

    override fun getContentView(): Any = R.layout.activity_ringtone_picker

    override fun initView() {
        findViewById<View>(R.id.iv_left).setOnClickListener(this)
        findViewById<TextView>(R.id.tv_label).text = getString(R.string.ringtone_picker)

        mRv = findViewById(R.id.ringtone_picker_rv)
        mRv.layoutManager = LinearLayoutManager(this)
        mAdapter = RingtoneAdapter(this, R.layout.item_ringtone, mRingtoneItemList)
        mRv.adapter = mAdapter
        mAdapter.setItemListener(object : ItemListener {
            override fun onItemClick(position: Int, holder: BaseViewHolder) {
                //播放
                val lastItem = mRingtoneItemList[mSelectedPosition]
                lastItem.checked = false
                mAdapter.notifyItemChanged(mSelectedPosition, mSelectedPosition)

                val currentItem = mRingtoneItemList[position]
                currentItem.checked = true
                mAdapter.notifyItemChanged(position, position)

                mPresenter.playRingtone(currentItem)

                mPresenter.saveRingtone(currentItem)

                mSelectedPosition = position
            }
        })
    }

    override fun initData() {
        mPresenter.getRingtoneData()
    }

    override fun createPresenter(): RingtonePickerPresenter = RingtonePickerPresenter()

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_left -> {
                finish()
            }
        }
    }

    override fun onDestroy() {
        mPresenter.destroy()
        super.onDestroy()
    }

    override fun refreshRingtoneList(data: MutableList<RingtoneItem>, saveIndex: Int) {
        mRingtoneItemList.clear()
        mRingtoneItemList.addAll(data)
        mAdapter.notifyDataSetChanged()
        mSelectedPosition = saveIndex
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, RingtonePickerActivity::class.java)
            context.startActivity(intent)
        }
    }
}