package com.mao.service.data

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.mao.config.NeedNotNull
import com.mao.config.NeedNumber
import com.mao.config.NeedRangeLength
import com.mao.config.NeedRangeText
import com.mao.util.SU
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
    ERROR, SEARCH, SAVE, EDIT, REMOVE
}

/**
 * 最顶层请求类型：表示什么范围的请求
 * HIS：his类型的请求，主要为外部调用请求
 * DATA：数据请求，主要为数据库的数据调用
 */
enum class ResourceType {
    ERROR, HIS, DATA
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
    ERROR, BOOK, BJX, BUDDHIST, LIVE, MOVIE, PIC
}

/**
 * 外部数据类型
 * IP_ADDRESS：  ip地址查询
 * CITY_WEATHER：城市天气信息查询
 * SUDO_KU：     数独解析
 */
enum class HisType {
    ERROR, IP_ADDRESS, CITY_WEATHER, SUDO_KU
}

/**
 * 外部请求数据类型
 * SRC：         请求内容
 * 如有分类内容再说
 */
enum class HisMethod {
    ERROR, SRC
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
    ERROR, SRC, LIST, PAGE, CHAPTER, CHAPTERS, CLASSIFY
}

/**
 * 分发参数的处理
 * 资源类型转化
 * 操作类型转化
 * 数据类型转化
 * 数据处理方式类型转化
 * 请求方式是否正确的判断
 */
object TypeOperation {

    fun resourceType(resource: String?) : ResourceType {
        return try {
            ResourceType.valueOf(resource?.toUpperCase()?:"ERROR")
        } catch (e: Exception) {
            ResourceType.ERROR
        }
    }

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

    fun hisType(his: String?) : HisType {
        return try {
            HisType.valueOf(his?.toUpperCase()?:"ERROR")
        } catch (e: Exception) {
            HisType.ERROR
        }
    }

    fun dataMethod(type: String?) : DataMethod {
        return try {
            DataMethod.valueOf(type?.toUpperCase()?:"ERROR")
        } catch (e: Exception) {
            DataMethod.ERROR
        }
    }

    fun hisMethod(type: String?) : HisMethod {
        return try {
            HisMethod.valueOf(type?.toUpperCase()?:"ERROR")
        } catch (e: Exception) {
            HisMethod.ERROR
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
object ParamUtil {

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
                    "java.lang.Integer" -> args[i] = value?.get(0)?.toInt()
                    "java.lang.Short" -> args[i] = value?.get(0)?.toShort()
                    "java.lang.Byte" -> args[i] = value?.get(0)?.toByte()
                    "java.lang.Boolean" -> args[i] = value?.get(0)?.toBoolean()
                    "java.lang.Long" -> args[i] = value?.get(0)?.toLong()
                    "java.lang.Float" -> args[i] = value?.get(0)?.toFloat()
                    "java.lang.Double" -> args[i] = value?.get(0)?.toDouble()
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
        val json = bodyString(request)
        return try {
            jacksonObjectMapper().readValue(json, t)
        } catch (e: Exception) {
            null
        }
    }

    fun <T> paramListBody(request: HttpServletRequest, t: Class<T>) : List<T>? {
        val json = bodyString(request)?: return null
        return try {
            jacksonObjectMapper().readValue(json, jacksonObjectMapper().typeFactory.constructParametricType(List::class.java,t))
        } catch (e: Exception) {
            null
        }
    }

    private fun bodyString(request: HttpServletRequest) : String? {
        val reader = BufferedReader(InputStreamReader(request.inputStream))
        val sb = StringBuilder()
        reader.forEachLine { sb.append(it) }
        return if (sb.isNotEmpty()) sb.toString() else null
    }

    /**
     * 参数检查
     * 主要检查使用参数校验注解的类
     * 具体注解参见 com.mao.config.Plugins.kt 的参数校验注解部分
     * @param param 需要校验的类
     * @param ignore 需要忽视的字段名称
     */
    fun check(param: Any, vararg ignore: String) : String? {
        val fields = param::class.java.declaredFields
        fields.forEach {
            it.isAccessible = true
            val name = it.name
            val value = it.get(param)?.toString()
            if (!ignore.contains(name)) {
                val annotations = it.declaredAnnotations
                if (annotations.isNotEmpty()){
                    annotations.forEach { a ->
                        run {
                            if (a is NeedNumber) {
                                if (null == value || !SU.isNumber(value))
                                    return "invalid param[$name] : $value. it must be a number."
                            } else if (a is NeedNotNull) {
                                if (null == value || value.isBlank())
                                    return "param[$name] must be not null"
                            } else if (a is NeedRangeLength) {
                                if (null != value && value.length > a.max)
                                    return "param[$name] cannot longer than ${a.max} length."
                            } else if (a is NeedRangeText) {
                                if (null != value && value.toByteArray().size > a.max.times(1024))
                                    return "param[$name] cannot bigger than ${a.max}KB."
                            }
                        }
                    }
                }
            }
        }
        return null
    }

}