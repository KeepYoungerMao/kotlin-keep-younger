package com.mao.web

import com.mao.entity.Response
import com.mao.entity.ResponseData
import com.mao.entity.Server
import com.mao.service.data.DataService
import com.mao.service.his.HisService
import com.mao.service.log.LogService
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.*
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.core.MethodParameter
import org.springframework.dao.DataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice
import java.io.ByteArrayOutputStream
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

    @Autowired private lateinit var hisService: HisService
    @Autowired private lateinit var dataService: DataService

    @GetMapping("his/address/ip")
    fun addressIp(ip: String?) : ResponseData<*> = hisService.addressIp(ip)

    @GetMapping("his/weather/city")
    fun cityWeather(city: String?) : ResponseData<*> = hisService.weatherCity(city)

    @PostMapping("his/sudoKu")
    fun sudoKu(@RequestBody array: Array<Array<Int>>?) : ResponseData<*> = hisService.sudoKu(array)

    @RequestMapping("data/{operation}/{data}/{type}")
    fun dataOperation(@PathVariable("operation") operation: String,
                      @PathVariable("data") data: String,
                      @PathVariable("type") type: String, request: HttpServletRequest) : ResponseData<*> {
        return dataService.dataOperation(operation, data, type, request)
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
        val msg = when (e) {
            is NoHandlerFoundException ->
                if (uri == FAVICON) FAVICON_ERROR
                else "no source for path: ${request.requestURI}"
            is SQLException -> SQL_ERROR
            is DataAccessException -> SQL_ERROR
            else -> e.message?: SERVER_ERROR
        }
        return Response.error(msg)
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
     * @param data 返回的数据
     * @param param
     * @param type
     * @param clazz
     * @param request
     * @param response
     */
    override fun beforeBodyWrite(data: Any?,
                                 param: MethodParameter,
                                 type: MediaType,
                                 clazz: Class<out HttpMessageConverter<*>>,
                                 request: ServerHttpRequest,
                                 response: ServerHttpResponse): Any? {
        /*if (null != data && data is ResponseData<*>)
            response.setStatusCode(HttpStatus.valueOf(data.code))*/
        return data
    }

}

@Aspect
@Component
class LogAspect {

    @After("execution(* com.mao.web.ApiController.*(..))")
    fun around(joinPoint: JoinPoint) {
        joinPoint.args.forEach { println("param value: $it") }
        println("method name: ${joinPoint.signature.name}")
        (joinPoint.signature as MethodSignature).parameterNames.forEach { println("param name: $it") }
        val sra = RequestContextHolder.getRequestAttributes() as ServletRequestAttributes
        val request = sra.request
        val response = sra.response
        println(response?.status)
    }

}