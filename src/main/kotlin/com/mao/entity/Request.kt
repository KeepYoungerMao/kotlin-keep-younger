package com.mao.entity

import javax.servlet.http.HttpServletRequest

enum class RequestType {
    SEARCH, SAVE, EDIT, REMOVE, ERROR
}

enum class DataType {
    BOOK, BOOK_CHAPTER, BJX, BUDDHIST, BUDDHIST_CHAPTER, LIVE, MOVIE, PIC, ERROR
}

object TypeOperation {

    fun requestType(req: String?) : RequestType {
        return try {
            RequestType.valueOf(req?:"ERROR")
        } catch (e: Exception) {
            RequestType.ERROR
        }
    }

    fun dataType(data: String?) : DataType {
        return try {
            DataType.valueOf(data?:"ERROR")
        } catch (e: Exception) {
            DataType.ERROR
        }
    }

    fun canRequest(type: RequestType, request: HttpServletRequest) : Boolean {
        val method = request.method.toUpperCase()
        return when (type) {
            RequestType.SEARCH -> method == "GET"
            RequestType.SAVE -> method == "PUT"
            RequestType.EDIT -> method == "POST"
            RequestType.REMOVE -> method == "DELETE"
            else -> false
        }
    }

}