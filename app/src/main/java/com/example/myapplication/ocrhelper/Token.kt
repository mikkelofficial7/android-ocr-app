package com.example.myapplication.ocrhelper

import com.example.myapplication.ocrhelper.enum.TokenType

internal class Token(val type: TokenType,
                     val lexeme: String,
                     val literal: Any?) {
    override fun toString(): String {
        return "$type $lexeme $literal"
    }
}