package com.example.myapplication.ui

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.base.BaseViewModel
import com.example.myapplication.extension.closeFile
import com.example.myapplication.extension.openFile
import com.example.myapplication.model.ScanModel
import com.example.myapplication.room.DBConfig
import com.example.myapplication.util.Constant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class MainCalculatorActivityVM(
    private val file: File,
    private val secretKey: String,
    private val roomConfig: DBConfig
) : BaseViewModel() {

    internal var readingFileEvent: MutableLiveData<Boolean> = MutableLiveData()
    internal var readingDatabaseEvent: MutableLiveData<Boolean> = MutableLiveData()

    internal var listScannedFromFile: MutableLiveData<ArrayList<ScanModel>> = MutableLiveData()
    internal var listScannedFromDatabase: MutableLiveData<ArrayList<ScanModel>> = MutableLiveData()

    internal fun readFileTxtEvent() {
        readingFileEvent.postValue(true)
    }

    internal fun readDatabaseEvent() {
        readingDatabaseEvent.postValue(true)
    }

    internal fun writeDataToFile(context: Context, expression: String) {
        isLoadingLiveData.postValue(true)

        safeScopeFun {
            isLoadingLiveData.postValue(false)
        }.launch(Dispatchers.IO) {
            openFile(context, secretKey)

            try {
                val data = "\n${expression}\n${Constant.TXT_DELIMITER}"
                val fileOutputStream: FileOutputStream = context.openFileOutput(file.name, Context.MODE_APPEND)
                fileOutputStream.write(data.toByteArray())
                fileOutputStream.flush()
                fileOutputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            closeFile(context, secretKey)
            readFileTxtEvent()
        }
    }

    internal fun readFileData(context: Context) {
        isLoadingLiveData.postValue(true)

        safeScopeFun {
            isLoadingLiveData.postValue(false)
        }.launch(Dispatchers.IO) {
            openFile(context, secretKey)

            try {
                val fileInputStream = context.openFileInput(file.name)
                var charNum: Int
                val tempString = StringBuilder()
                while (fileInputStream.read().also { charNum = it } != -1) {
                    tempString.append(charNum.toChar())
                }

                extractToModel(tempString.toString())
                fileInputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            closeFile(context, secretKey)
            isLoadingLiveData.postValue(false)
        }
   }

    private fun extractToModel(text: String) {
        val textRead = text.split(Constant.TXT_DELIMITER)

        val listScannedData = ArrayList<ScanModel>()
        textRead.mapIndexed { index, value ->
            if(value.isNotEmpty()) {
                listScannedData.add(ScanModel(index, value))
            }
        }
        listScannedFromFile.postValue(listScannedData)
    }

    internal fun getAllDataFromDatabase() {
        isLoadingLiveData.postValue(true)

        safeScopeFun {
            isLoadingLiveData.postValue(false)
        }.launch(Dispatchers.IO) {
            listScannedFromDatabase.postValue(roomConfig.dataDao().getAllScanData() as ArrayList<ScanModel>?)

            isLoadingLiveData.postValue(false)
        }
    }

    internal fun insertDataToDatabase(data: ScanModel) {
        isLoadingLiveData.postValue(true)

        safeScopeFun {
            isLoadingLiveData.postValue(false)
        }.launch(Dispatchers.IO) {
            roomConfig.dataDao().insert(data)
            readDatabaseEvent()
        }
    }

}