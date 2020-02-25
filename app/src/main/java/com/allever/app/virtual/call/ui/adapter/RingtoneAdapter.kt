package com.allever.app.virtual.call.ui.adapter

import android.content.Context
import android.graphics.PorterDuff
import android.widget.ImageView
import com.allever.app.virtual.call.R
import com.allever.app.virtual.call.bean.RingtoneItem
import com.allever.lib.common.ui.widget.recycler.BaseRecyclerViewAdapter
import com.allever.lib.common.ui.widget.recycler.BaseViewHolder

class RingtoneAdapter(context: Context, resId: Int, data: MutableList<RingtoneItem>): BaseRecyclerViewAdapter<RingtoneItem>(context, resId, data) {
    override fun bindHolder(holder: BaseViewHolder, position: Int, item: RingtoneItem) {
        holder.setText(R.id.item_ringtone_tv_title, item.title!!)

        holder.setVisible(R.id.item_ringtone_iv_check, item.checked)

        if (item.checked) {
            holder.setTextColorRes(R.id.item_ringtone_tv_title, R.color.item_ringtone_selected)
            holder.getView<ImageView>(R.id.item_ringtone_iv_ring)?.setColorFilter(mContext.resources.getColor(R.color.default_theme_color), PorterDuff.Mode.SRC_IN)
        } else {
            holder.setTextColorRes(R.id.item_ringtone_tv_title, R.color.item_ringtone_un_selected)
            holder.getView<ImageView>(R.id.item_ringtone_iv_ring)?.colorFilter = null
        }
    }
}