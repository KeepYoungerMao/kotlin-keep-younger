package com.mao.web

import com.mao.config.WebSecurityConfigurer
import com.mao.entity.Response
import com.mao.entity.ResponseData
import com.mao.entity.Server
import com.mao.service.GlobalService
import com.mao.service.log.LogService
import org.apache.ibatis.exceptions.PersistenceException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.MethodParameter
import org.springframework.dao.DataAccessException
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.http.server.ServletServerHttpRequest
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice
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
 * 【注】：对于更改数据的重复操作的解决方法（参考）
 *  提供一个获取验证码的请求
 *  每次进行更新等操作时，获取一个验证码，该验证码与用户进行绑定，
 *  以 用户名-验证码 形式存入redis，并设置过期时间
 *  调用更新等请求时，额外header中添加验证码参数
 *  后台进行比对，比对成功才能继续操作（同时删除redis缓存）；比对失败不能操作。
 *  目前尚未加入
 * @author create by mao at 2020/06/03 19:21:32
 */
@RestController
@RequestMapping(WebSecurityConfigurer.API_PRE)
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
 * 【经测试】：
 * 1. SQLException：在mybatis执行SQL出问题时会产生此类错误（顶级类错误）
 * 2. PersistenceException： mybatis的配置出问题时会产生此类错误（顶级类错误）
 * 3. 但是，1，2在ExceptionHandler都不会得到拦截，原因是spring对错误进行了包装，
 * 4. 这些错误都被包含在DataAccessException的cause中，最终以DataAccessException类的异常出现
 * 5. OAuth2Exception：oauth2产生错误抛出的异常（顶级类错误）
 * 6. 不过ExceptionHandler也无法拦截这个错误。按道理可以捕获的，不过不知道什么原因。。。
 */
@RestControllerAdvice
class ExceptionAdvice {

    companion object {
        private const val FAVICON = "/favicon.ico"
        private const val SERVER_ERROR = "request error"
        private const val SQL_ERROR = "some errors occurred while requesting database. it will be fixed asap."
        private const val FAVICON_ERROR = "not supported request type: Favicon."
        private const val PERMISSION_ERROR = "Unauthorized"
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
            is OAuth2Exception -> Response.permission(e.message?: PERMISSION_ERROR)
            else -> Response.error(e.message?: SERVER_ERROR)
        }
    }

}

/**
 * 数据返回拦截：主要作请求日志记录工作
 * 【注】：此方式只能拦截springMVC适配后的数据，
 * 对于像oauth拦截后返回的数据不会在此处停留，因此对401的请求需要做记录的话需要在oauth中操作
 * 详见：
 * @see com.mao.config.DefaultAccessDeniedHandler oauth错误处理器
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
    override fun beforeBodyWrite(data: Any?, param: MethodParameter, type: MediaType, clazz: Class<out HttpMessageConverter<*>>,
                                 request: ServerHttpRequest, response: ServerHttpResponse): Any? {
        if (null != data && data is ResponseData<*> && data.code != 404) {
            val remark = if (data.code != 200) data.data as String else null
            logService.saveLog(request.principal?.name, (request as ServletServerHttpRequest).servletRequest, data.code,remark)
        }
        return data
    }

}