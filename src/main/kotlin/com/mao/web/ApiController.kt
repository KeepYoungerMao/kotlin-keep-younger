package com.mao.web

import com.mao.entity.Response
import com.mao.entity.ResponseData
import com.mao.entity.Server
import com.mao.service.GlobalService
import com.mao.service.log.LogService
import org.apache.ibatis.binding.BindingException
import org.apache.ibatis.exceptions.PersistenceException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.MethodParameter
import org.springframework.dao.DataAccessException
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.http.server.ServletServerHttpRequest
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice
import java.net.BindException
import java.sql.SQLException
import javax.servlet.http.HttpServletRequest

/**
 * 基本请求：出api之外的请求
 */
@RestController
class MainController {

    @Autowired private lateinit var server: Server

    @GetMapping("")
    fun index() : ResponseData<Server> = Response.ok(server)

}

/**
 * api请求
 * @author create by mao at 2020/06/03 19:21:32
 */
@RestController
@RequestMapping("api2")
class ApiController {

    @Autowired private lateinit var globalService: GlobalService

    @RequestMapping("{resource}/{operation}/{data}/{type}")
    fun dataOperation(@PathVariable("resource") resource: String,
                      @PathVariable("operation") operation: String,
                      @PathVariable("data") data: String,
                      @PathVariable("type") type: String, request: HttpServletRequest) : ResponseData<*> {
        return globalService.operation(resource, operation, data, type, request)
    }

}

/**
 * 异常拦截
 */
@RestControllerAdvice
class ExceptionAdvice {

    companion object {
        private const val FAVICON = "/favicon.ico"
        private const val SERVER_ERROR = "request error"
        private const val SQL_ERROR = "some errors occurred while requesting database. it will be fixed asap."
        private const val FAVICON_ERROR = "not supported request type: Favicon."
    }

    @ExceptionHandler(Exception::class)
    fun globalHandler(e: Exception, request: HttpServletRequest) : ResponseData<String> {
        val uri = request.requestURI
        return when (e) {
            is NoHandlerFoundException ->
                if (uri == FAVICON) Response.notFound(FAVICON_ERROR)
                else Response.notFound("no source for path: ${request.requestURI}")
            is SQLException -> Response.error(SQL_ERROR)
            is PersistenceException -> Response.error(SQL_ERROR)
            is DataAccessException -> Response.error(SQL_ERROR)
            else -> Response.error(e.message?: SERVER_ERROR)
        }
    }

}

/**
 * 数据返回拦截：主要作请求日志记录工作
 */
@RestControllerAdvice(basePackages = ["com.mao.web"])
class ResponseAdvice : ResponseBodyAdvice<Any> {

    @Autowired private lateinit var logService: LogService

    /**
     * 判断是否需要拦截
     */
    override fun supports(param: MethodParameter, clazz: Class<out HttpMessageConverter<*>>): Boolean = true

    /**
     * 原样数据原样返回
     * 记录请求。对404不做记录
     * @param data 返回的数据
     * @param param 方法参数
     * @param type 返回数据类型：application/json
     * @param clazz do not know
     * @param request request
     * @param response response
     */
    override fun beforeBodyWrite(data: Any?,
                                 param: MethodParameter,
                                 type: MediaType,
                                 clazz: Class<out HttpMessageConverter<*>>,
                                 request: ServerHttpRequest,
                                 response: ServerHttpResponse): Any? {
        if (null != data && data is ResponseData<*> && data.code != 404) {
            val remark = if (data.code == 500) data.data as String else null
            logService.saveLog(request.principal?.name, (request as ServletServerHttpRequest).servletRequest, data.code,remark)
        }
        return data
    }

}