package com.allever.app.virtual.call.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.allever.app.virtual.call.R
import com.allever.lib.common.app.App
import com.allever.lib.common.app.BaseFragment

class GuideFragment : BaseFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return LayoutInflater.from(App.context).inflate(R.layout.fragment_guide, container, false)
    }
}