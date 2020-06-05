package com.mao.web

import com.mao.entity.ResponseData
import com.mao.service.data.DataService
import com.mao.service.his.HisService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.RestTemplate
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