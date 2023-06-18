package com.example.myapplication.ui

import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.example.myapplication.base.BaseActivityVM
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.extension.*
import com.example.myapplication.util.PermissionUtil
import com.example.myapplication.model.ScanModel
import com.example.myapplication.routing.*

class MainCalculatorActivity : BaseActivityVM<ActivityMainBinding, MainCalculatorActivityVM>(
    MainCalculatorActivityVM::class) {
    private var cameraImageUri: Uri? = null
    private val viewAdapter by lazy {
        ItemAdapter(this)
    }

    private val captureImageFromCameraResult = registerForActivityResult(ActivityResultContracts.TakePicture()){ isSuccess ->
        if(!isSuccess) return@registerForActivityResult
        cameraImageUri?.let { uri ->
            val expressionResult = uri.uriToBitmap(contentResolver)?.convertImageToText(this)
            saveExpression(expressionResult.orEmpty())
        }
    }

    private val selectImageFromGalleryResult = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            val expressionResult = it.uriToBitmap(contentResolver)?.convertImageToText(this)
            saveExpression(expressionResult.orEmpty())
        }
    }

    override fun observeViewModel(viewModel: MainCalculatorActivityVM) {
        observe(viewModel.isLoadingLiveData, ::handleLoading)
        observe(viewModel.readingFileEvent) { handleReadDataFromFile() }
        observe(viewModel.readingDatabaseEvent) { handleReadDataFromDatabase() }
        observe(viewModel.listScannedFromFile, ::handleScannedData)
        observe(viewModel.listScannedFromDatabase, ::handleScannedData)
    }

    override fun bindToolbar(): Toolbar? = null

    override fun enableBackButton(): Boolean = true

    override fun getUiBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onFirstLaunch(savedInstanceState: Bundle?) {
        getRadioButtonStorageType(
            viewBinding?.rgStorage?.checkedRadioButtonId ?: 0,
            onFileSelected = {
                baseViewModel.readFileTxtEvent()
            },
            onDatabaseSelected = {
                baseViewModel.readDatabaseEvent()
            }
        )

        viewBinding?.rvCapture?.apply {
            layoutManager = LinearLayoutManager(this@MainCalculatorActivity, VERTICAL, false)
            adapter = viewAdapter
        }
    }

    override fun initUiListener() {
        viewBinding?.btnMethod?.setOnClickListener {
            askPermission()
        }

        viewBinding?.rgStorage?.setOnCheckedChangeListener { group, checkedId ->
            getRadioButtonStorageType(
                checkedId,
                onFileSelected = {
                    baseViewModel.readFileTxtEvent()
                },
                onDatabaseSelected = {
                    baseViewModel.readDatabaseEvent()
                }
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionUtil.onRequestPermissionsResult(this, requestCode, permissions, grantResults, resultGranted = {
            if(it) {
                selectPicker()
            } else {
                showErrorPermission()
            }
        })
    }

    internal fun openCamera() {
        lifecycleScope.launchWhenStarted {
            cameraImageUri = getCameraResultUri()
            launchCamera(captureImageFromCameraResult, cameraImageUri!!)
        }
    }

    internal fun openGallery() {
        launchGallery(selectImageFromGalleryResult)
    }

    private fun handleReadDataFromFile() {
        baseViewModel.readFileData(this@MainCalculatorActivity)
    }

    private fun handleReadDataFromDatabase() {
        baseViewModel.getAllDataFromDatabase()
    }

    private fun handleScannedData(list: ArrayList<ScanModel>?) {
        viewAdapter.setData(list ?: listOf())
    }

    private fun saveExpression(expression: String) {
        getRadioButtonStorageType(
            viewBinding?.rgStorage?.checkedRadioButtonId ?: 0,
            onFileSelected = {
                baseViewModel.writeDataToFile(this, expression.trim())
            },
            onDatabaseSelected = {
                baseViewModel.insertDataToDatabase(ScanModel(value = expression.trim()))
            }
        )
    }
}