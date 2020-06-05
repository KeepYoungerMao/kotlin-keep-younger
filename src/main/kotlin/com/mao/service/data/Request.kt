package com.mao.service.data

import javax.servlet.http.HttpServletRequest

/**
 * 请求类型
 * SEARCH：      GET请求
 * SAVE：        PUT请求
 * EDIT：        post请求
 * REMOVE：      delete请求
 * 请求类型错误时转化至ERROR
 */
enum class RequestType {
    SEARCH, SAVE, EDIT, REMOVE, ERROR
}

/**
 * 数据类型
 * BOOK：        古籍
 * BJX：         百家姓
 * BUDDHIST：    佛经
 * LIVE：        直播源
 * MOVIE：       电影
 * PIC：         图片
 * 数据类型错误时转化至ERROR
 */
enum class DataType {
    BOOK, BJX, BUDDHIST, LIVE, MOVIE, PIC, ERROR
}

/**
 * 数据处理方式类型
 * SRC：         单个数据
 * LIST：        多个数据
 * PAGE：        分页数据
 * CHAPTER：     特殊使用：章节数据
 * CHAPTER：     特殊使用：多个章节数据
 * CLASSIFY：    分类数据
 * 处理方式类型错误时转化至ERROR
 */
enum class DataMethod {
    SRC, LIST, PAGE, CHAPTER, CHAPTERS, CLASSIFY, ERROR
}

/**
 * 分发参数的处理
 * 操作类型转化
 * 数据类型转化
 * 数据处理方式类型转化
 * 请求方式是否正确的判断
 */
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