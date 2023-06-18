package com.example.myapplication.extension

import android.content.Context
import android.graphics.Bitmap
import com.example.myapplication.ocrhelper.MathExpression
import com.example.myapplication.ocrhelper.enum.BasicOperator
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.text.TextRecognizer
import java.math.BigDecimal

fun Bitmap.convertImageToText(context: Context) : String {
    val txtOcr = this.readOCRBitmapToString(context)
    val exp = txtOcr.replace("\n","")

    val result = try {
        MathExpression().eval(exp).toString()
    } catch (e:Exception){
        ""
    }

    return if(result.isNotEmpty()) {
        var firstOperator = ""
        var firstNumber = ""
        var secondNumber = ""
        var resultFirstOperator = ""

        exp.toCharArray().map {
            val isFound = BasicOperator.values().find { opr -> opr.symbol == it.toString() }

            if(isFound == null) {
                if(firstOperator.isNotEmpty()) {
                    if(it.isNumeric()) secondNumber += it
                } else {
                    if(it.isNumeric()) firstNumber += it
                }
            } else {
                if(firstNumber.isNotEmpty() && secondNumber.isNotEmpty() && firstOperator.isNotEmpty()) {
                    val calculateResult = calculate(BigDecimal(firstNumber.trim()), BigDecimal(secondNumber.trim()), firstOperator)
                    resultFirstOperator = "${firstNumber.trim()} ${firstOperator.trim()} ${secondNumber.trim()} = $calculateResult"

                    return@map
                } else {
                    if(firstOperator.isEmpty()) {
                        firstOperator = isFound.symbol
                    }
                }
            }
        }

        resultFirstOperator
    } else {
        "image capture is blur..\n\nResult cannot be count"
    }
}

fun Bitmap.readOCRBitmapToString(context: Context) : String {
    var resultValue = ""
    val recognizer = TextRecognizer.Builder(context).build()
    if (!recognizer.isOperational) {
        context.showToast("error data")
    } else {
        val frame = Frame.Builder().setBitmap(this).build()
        val items = recognizer.detect(frame)
        val sb = StringBuilder()
        for (i in 0 until items.size()) {
            val myItem = items.valueAt(i)
            sb.append(myItem.value+"\n")
        }
        resultValue =  sb.toString()
    }
    return resultValue
}