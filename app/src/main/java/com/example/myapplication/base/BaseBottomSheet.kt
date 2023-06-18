package com.example.myapplication.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.view.ContextThemeWrapper
import androidx.viewbinding.ViewBinding
import com.example.myapplication.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheet<VB : ViewBinding> : BottomSheetDialogFragment() {
    var viewBinding: VB? = null
    private var setCancelable: Boolean = true
    lateinit var cloneLayoutInflater: LayoutInflater

    abstract fun getUiBinding(): VB
    abstract fun onFirstLaunch(view: View, savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogTopRounded)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val contextThemeWrapper = ContextThemeWrapper(activity, R.style.Theme_BottomSheet)
        cloneLayoutInflater = layoutInflater.cloneInContext(contextThemeWrapper)

        if (viewBinding == null) viewBinding = getUiBinding()

        dialog?.setOnShowListener { dialog ->
            val layout: FrameLayout? = (dialog as BottomSheetDialog).
            findViewById(com.google.android.material.R.id.design_bottom_sheet)
            layout?.let {
                val behavior = BottomSheetBehavior.from(it)
                behavior.isDraggable = setCancelable
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
                behavior.skipCollapsed = true
            }
        }
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onFirstLaunch(view, savedInstanceState)
    }

    override fun onDestroyView() {
        viewBinding = null
        super.onDestroyView()
    }
}