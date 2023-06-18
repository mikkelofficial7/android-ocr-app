package com.example.myapplication.extension

import com.example.myapplication.ocrhelper.enum.BasicOperator
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

fun BigDecimal.pows(n: BigDecimal, mathContext: MathContext): BigDecimal {
    var right = n
    val signOfRight = right.signum()
    right = right.multiply(signOfRight.toBigDecimal())
    val remainderOfRight = right.remainder(BigDecimal.ONE)
    val n2IntPart = right.subtract(remainderOfRight)
    val intPow = pow(n2IntPart.intValueExact(), mathContext)
    val doublePow = BigDecimal(Math
        .pow(toDouble(), remainderOfRight.toDouble()))

    var result = intPow.multiply(doublePow, mathContext)
    if (signOfRight == -1) result = BigDecimal
        .ONE.divide(result, mathContext.precision, RoundingMode.HALF_UP)

    return result
}

fun calculate(left: BigDecimal, right: BigDecimal, operator: String) : String {
    return when(operator) {
        BasicOperator.PLUS.symbol -> BigDecimal.valueOf((left + right).toLong()).toPlainString()
        BasicOperator.MINUS.symbol -> BigDecimal.valueOf((left - right).toLong()).toPlainString()
        BasicOperator.STAR.symbol -> BigDecimal.valueOf((left * right).toLong()).toPlainString()
        BasicOperator.SLASH.symbol -> left.divide(right,  MathContext.DECIMAL64).toPlainString()
        BasicOperator.MODULO.symbol -> left.remainder(right,  MathContext.DECIMAL64).toPlainString()
        BasicOperator.EXPONENT.symbol -> left.pows(right, MathContext.DECIMAL64).toPlainString()
        else -> "SYNTAX ERR: $left $operator $right"
    }
}