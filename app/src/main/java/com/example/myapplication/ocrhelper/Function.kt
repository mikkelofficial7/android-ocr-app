package com.example.myapplication.ocrhelper

import java.math.BigDecimal

abstract class Function {
    abstract fun call(arguments: List<BigDecimal>): BigDecimal
}