package com.mao.util

object SU {

    fun isNumber(str: String) : Boolean {
        return try {
            str.toLong() > 0
        } catch (e: Exception) {
            false
        }
    }

}