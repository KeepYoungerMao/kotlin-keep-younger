package com.mao.web

import com.mao.entity.ResponseData
import com.mao.service.data.DataService
import com.mao.service.his.HisService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("api2")
class ApiController {

    @Autowired
    private lateinit var hisService: HisService
    @Autowired
    private lateinit var dataService: DataService

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

    @RequestMapping("data/{request}/{data}/by/{type}")
    fun dataOperation(@PathVariable("request") req: String,
                      @PathVariable("data") data: String,
                      @PathVariable("type") type: String, request: HttpServletRequest) : ResponseData<*> {
        return dataService.dataOperation(req, data, type, request)
    }

}