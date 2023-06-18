package com.example.myapplication.base

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewbinding.ViewBinding
import com.example.myapplication.R

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {
    var viewBinding: VB? = null
    private var mToolbar: Toolbar? = null

    private lateinit var progress: Dialog
    private lateinit var rootView: ViewGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = getUiBinding()
        setContentView(viewBinding?.root)
        rootView = window.decorView.findViewById(android.R.id.content)
        initProgressDialog()
        setupToolbar()
        onFirstLaunch(savedInstanceState)
        initUiListener()
    }

    abstract fun bindToolbar(): Toolbar?
    abstract fun enableBackButton(): Boolean
    abstract fun getUiBinding(): VB
    abstract fun onFirstLaunch(savedInstanceState: Bundle?)
    abstract fun initUiListener()

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> false
    }

    private fun initProgressDialog() {
        if (!::progress.isInitialized) {
            progress = Dialog(this)
            val inflate = LayoutInflater.from(this).inflate(R.layout.progress_loading, null)
            progress.setContentView(inflate)
            progress.setCancelable(false)
            progress.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            progress.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    private fun setupToolbar() {
        bindToolbar()?.let {
            mToolbar = it
            setSupportActionBar(mToolbar)
            supportActionBar?.apply {
                setDisplayShowTitleEnabled(false)
                setDisplayHomeAsUpEnabled(enableBackButton())
                setHomeAsUpIndicator(R.drawable.ic_arrow_back)
            }
        }
    }

    fun showProgress() {
        if (!progress.isShowing) progress.show()
    }

    fun hideProgress() {
        if (progress.isShowing) progress.dismiss()
    }

}