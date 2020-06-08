package com.mao.web

import com.mao.entity.Response
import com.mao.entity.ResponseData
import com.mao.service.data.DataService
import com.mao.service.his.HisService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.MethodParameter
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice
import javax.servlet.http.HttpServletRequest

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
    fun addressIp(ip: String?) : ResponseData<*> {
        return hisService.addressIp(ip)
    }

    @GetMapping("his/weather/city")
    fun cityWeather(city: String?) : ResponseData<*> {
        return hisService.weatherCity(city)
    }

    @PostMapping("his/sudoKu")
    fun sudoKu(@RequestBody array: Array<Array<Int>>?) : ResponseData<*> {
        return hisService.sudoKu(array)
    }

    @RequestMapping("data/{operation}/{data}/{type}")
    fun dataOperation(@PathVariable("operation") operation: String,
                      @PathVariable("data") data: String,
                      @PathVariable("type") type: String, request: HttpServletRequest) : ResponseData<*> {
        return dataService.dataOperation(operation, data, type, request)
    }

}

@RestControllerAdvice
class ExceptionAdvice {

    @ExceptionHandler(Exception::class)
    fun globalHandler(e: Exception, request: HttpServletRequest) : ResponseData<String> {
        if (e is NoHandlerFoundException)
            return Response.notFound("no source for path: ${request.requestURI}")
        return Response.error(e.message?:"request error")
    }

}

@RestControllerAdvice(basePackages = ["com.mao.web"])
class ResponseAdvice : ResponseBodyAdvice<ResponseData<*>> {

    /**
     * 判断是否需要拦截
     */
    override fun supports(param: MethodParameter, clazz: Class<out HttpMessageConverter<*>>): Boolean {
        return true
    }

    override fun beforeBodyWrite(data: ResponseData<*>?,
                                 param: MethodParameter,
                                 type: MediaType,
                                 clazz: Class<out HttpMessageConverter<*>>,
                                 request: ServerHttpRequest,
                                 response: ServerHttpResponse): ResponseData<*>? {
        println("拦截成功。数据返回：${data?.code}")
        return data
    }

}