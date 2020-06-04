package com.mao.entity

import javax.servlet.http.HttpServletRequest

enum class RequestType {
    SEARCH, SAVE, EDIT, REMOVE, ERROR
}

enum class DataType {
    BOOK, BJX, BUDDHIST, LIVE, MOVIE, PIC, ERROR
}

enum class DataMethod {
    SRC, LIST, PAGE, CHAPTER, CHAPTERS, ERROR
}

object TypeOperation {

    fun requestType(operation: String?) : RequestType {
        return try {
            RequestType.valueOf(operation?:"ERROR")
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

    fun dataMethod(type: String?) : DataMethod {
        return try {
            DataMethod.valueOf(type?:"ERROR")
        } catch (e: Exception) {
            DataMethod.ERROR
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