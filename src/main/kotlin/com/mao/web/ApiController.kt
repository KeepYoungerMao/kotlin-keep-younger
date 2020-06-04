package com.mao.web

import com.mao.entity.ResponseData
import com.mao.service.his.HisService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api2")
class ApiController {

    @Autowired
    private lateinit var hisService: HisService

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

}