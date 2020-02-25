//package com.allever.app.template.ui.widget
//
//import android.content.Context
//import android.util.AttributeSet
//import android.view.View
//import android.widget.ImageView
//import android.widget.RelativeLayout
//import android.widget.TextView
//import com.allever.app.template.R
//
//class TopBarView @JvmOverloads constructor(
//    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
//) : RelativeLayout(context, attrs, defStyleAttr) {
//    private lateinit var mLeftView: ImageView
//    private lateinit var mRightView: ImageView
//    private lateinit var mTitle: TextView
//
//    override fun onFinishInflate() {
//        super.onFinishInflate()
//        mLeftView = findViewById(R.id.iv_left)
//        mRightView = findViewById(R.id.iv_right)
//        mTitle = findViewById(R.id.tv_label)
//        mLeftView.setImageResource(R.drawable.icon_back)
//    }
//
//    fun init(
//        title: String? = null,
//        leftIcon: Int? = null,
//        rightIcon: Int? = null,
//        leftListener: OnClickListener? = null,
//        rightListener: OnClickListener? = null
//    ) {
//        title?.let {
//            setTitle(it)
//        }
//
//        leftIcon?.let {
//            setLeftViewIcon(it)
//        }
//
//        rightIcon?.let {
//            setRightViewIcon(it)
//        }
//
//        leftListener?.let {
//            setListener(mLeftView, it)
//        }
//
//        rightListener?.let {
//            setListener(mRightView, it)
//        }
//    }
//
//    fun setLeftViewIcon(resId: Int) {
//        setIcon(mLeftView, resId)
//    }
//
//    fun setRightViewIcon(resId: Int) {
//        setIcon(mRightView, resId)
//    }
//
//    fun setLeftListener(listener: OnClickListener) {
//        setListener(mLeftView, listener)
//    }
//
//    fun setRightListener(listener: OnClickListener) {
//        setListener(mRightView, listener)
//    }
//
//    fun setTitle(title: String) {
//        mTitle.text = title
//    }
//
//    fun setTitleVisibility(show: Boolean) {
//        setVisibility(mTitle, show)
//    }
//
//    fun setLeftVisibility(show: Boolean) {
//        setVisibility(mLeftView, show)
//    }
//
//    fun setRightVisibility(show: Boolean) {
//        setVisibility(mRightView, show)
//    }
//
//    private fun setVisibility(target: View, show: Boolean) {
//        if (show) {
//            target.visibility = View.VISIBLE
//        } else {
//            target.visibility = View.GONE
//        }
//    }
//
//    private fun setListener(target: View, listener: OnClickListener) {
//        target.setOnClickListener(listener)
//        target.visibility = View.VISIBLE
//    }
//
//    private fun setIcon(target: ImageView, resId: Int) {
//        target.setImageResource(resId)
//        target.visibility = View.VISIBLE
//    }
//
//}