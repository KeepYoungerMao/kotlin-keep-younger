package com.mao.service.data

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.io.BufferedReader
import java.io.InputStreamReader
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
            RequestType.valueOf(operation?.toUpperCase()?:"ERROR")
        } catch (e: Exception) {
            RequestType.ERROR
        }
    }

    fun dataType(data: String?) : DataType {
        return try {
            DataType.valueOf(data?.toUpperCase()?:"ERROR")
        } catch (e: Exception) {
            DataType.ERROR
        }
    }

    fun dataMethod(type: String?) : DataMethod {
        return try {
            DataMethod.valueOf(type?.toUpperCase()?:"ERROR")
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

/**
 * 参数封装
 */
object ParamPackage {

    /**
     * 请求参数封装
     * 将请求参数封装至实体类中
     * 【注】此方法只适用于kotlin中
     * 【注】封装的实体类必须是data class类型
     *      由于data class类型只有一个全属性的构造器，这里直接使用该构造器构造，没有使用空构造
     *      项目中配置了无参构造插件，但我没有对请求参数封装类作注解，使用全参构造就行，懒得判断
     * 【注】实体类中只处理基本类型参数，其他参数一律不写入（目前不会）
     * 【问题】：使用 parameter.type（也就是Class<?>）去判断类型时
     *      只有 String::class.java 可以识别，其它识别不了。
     *      也就是：判断 java.lang.Integer 时，使用 Int::class.java 判断为 false
     *      【替代】：目前使用的是使用 parameter.type.name 进行判断
     */
    @Suppress("UNCHECKED_CAST")
    fun <T> paramForm(request: HttpServletRequest, clazz: Class<T>) : T? {
        val params = request.parameterMap
        val proxy = clazz.constructors
        if (proxy.isEmpty())
            return null
        val constructor = proxy[0]
        val parameters = constructor.parameters
        if (parameters.isEmpty())
            return null
        val args = arrayOfNulls<Any?>(parameters.size)
        try {
            parameters.forEachIndexed { i, parameter -> kotlin.run {
                val name = parameter.name
                val value = params[name]
                when (parameter.type.name) {
                    "java.lang.Integer" -> args[i] = value?.get(0)?.toInt()?:0
                    "java.lang.Short" -> args[i] = value?.get(0)?.toShort()?:0
                    "java.lang.Byte" -> args[i] = value?.get(0)?.toByte()?:0
                    "java.lang.Boolean" -> args[i] = value?.get(0)?.toBoolean()
                    "java.lang.Long" -> args[i] = value?.get(0)?.toLong()?:0
                    "java.lang.Float" -> args[i] = value?.get(0)?.toFloat()?:0
                    "java.lang.Double" -> args[i] = value?.get(0)?.toDouble()?:0
                    "java.lang.String" -> args[i] = value?.get(0)
                    "int" -> args[i] = value?.get(0)?.toInt()?:0
                    "short" -> args[i] = value?.get(0)?.toInt()?:0
                    "byte" -> args[i] = value?.get(0)?.toInt()?:0
                    "long" -> args[i] = value?.get(0)?.toInt()?:0
                    "float" -> args[i] = value?.get(0)?.toInt()?:0
                    "double" -> args[i] = value?.get(0)?.toInt()?:0
                    "boolean" -> args[i] = value?.get(0)?.toInt()?:false
                    else -> throw Exception("not supported parameter type: ${parameter.type.name}")
                }
            } }
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
        return constructor.newInstance(*args) as T
    }

    /**
     * 请求参数封装
     * body数据封装至实体类中
     * 直接使用Jackson读取转化即可。
     */
    fun <T> paramBody(request: HttpServletRequest, t: Class<T>) : T? {
        val reader = BufferedReader(InputStreamReader(request.inputStream))
        val sb = StringBuilder()
        reader.forEachLine { sb.append(it) }
        return try {
            jacksonObjectMapper().readValue(sb.toString(),t)
        } catch (e: Exception) {
            null
        }
    }

}