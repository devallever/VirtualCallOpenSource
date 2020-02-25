package com.allever.app.virtual.call.ui

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.allever.app.virtual.call.R
import com.allever.app.virtual.call.app.BaseActivity
import com.allever.app.virtual.call.bean.WallPagerItem
import com.allever.app.virtual.call.ui.adapter.WallPagerAdapter
import com.allever.app.virtual.call.ui.mvp.presenter.WallPagerPickerPresenter
import com.allever.app.virtual.call.ui.mvp.view.WallPagerPickerView

import com.allever.lib.common.ui.widget.recycler.BaseViewHolder
import com.allever.lib.common.ui.widget.recycler.ItemListener

class WallPagerPickerActivity : BaseActivity<WallPagerPickerView, WallPagerPickerPresenter>(),
    WallPagerPickerView,
    View.OnClickListener {

    private lateinit var mAdapter: WallPagerAdapter
    private lateinit var mRv: RecyclerView
    private var mSelectedPosition = 0
    private val mWallPagerItemList = mutableListOf<WallPagerItem>()


    override fun getContentView(): Any = R.layout.activity_wall_pager_picker

    override fun initView() {
        findViewById<View>(R.id.iv_left).setOnClickListener(this)
        findViewById<TextView>(R.id.tv_label).text = getString(R.string.wall_pager_picker)

        mRv = findViewById(R.id.wall_pager_picker_rv)
        mRv.layoutManager = GridLayoutManager(this, 2)
        mAdapter = WallPagerAdapter(this, R.layout.item_wall_pager, mWallPagerItemList)
        mRv.adapter = mAdapter
        mAdapter.setItemListener(object : ItemListener {
            override fun onItemClick(position: Int, holder: BaseViewHolder) {
                val lastItem = mWallPagerItemList[mSelectedPosition]
                lastItem.checked = false
                mAdapter.notifyItemChanged(mSelectedPosition, mSelectedPosition)

                val currentItem = mWallPagerItemList[position]
                currentItem.checked = true
                mAdapter.notifyItemChanged(position, position)

                mPresenter.saveWallPager(currentItem)
            }
        })
    }

    override fun initData() {
        mPresenter.getWallPagerData()
    }

    override fun createPresenter(): WallPagerPickerPresenter = WallPagerPickerPresenter()

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_left -> {
                finish()
            }
        }
    }

    override fun refreshWallPagerList(data: MutableList<WallPagerItem>, saveIndex: Int) {
        mWallPagerItemList.clear()
        mWallPagerItemList.addAll(data)
        mAdapter.notifyDataSetChanged()
        mSelectedPosition = saveIndex
    }

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, WallPagerPickerActivity::class.java)
            context.startActivity(intent)
        }
    }
}