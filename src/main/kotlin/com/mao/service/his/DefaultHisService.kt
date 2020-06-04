package com.mao.service.his

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.mao.entity.Response
import com.mao.entity.ResponseData
import com.mao.entity.WeatherResult
import com.mao.util.HttpUtil
import org.springframework.stereotype.Service
import java.util.regex.Pattern

@Service
class DefaultHisService : HisService {

    companion object {
        const val AK = "Po86Y8fZwYv5fpcQIX7MVk1DaMOl3VwB"
        const val GET_LOCATION_URL = "http://api.map.baidu.com/location/ip"
        const val GET_IP_ADDRESS_ERROR = "cannot request any message form SDK, please check whether ip is correct";
        const val GET_WEATHER_URL = "http://wthrcdn.etouch.cn/weather_mini"
        const val GET_WEATHER_ERROR = "request service api failed. connect failed."
    }

    override fun addressIp(ip: String?): ResponseData<*> {
        if (null == ip)
            return Response.error("loss param: ip")
        val map: Map<String, String> = mapOf("ip" to ip, "ak" to AK)
        val result: String? = HttpUtil.get(GET_LOCATION_URL, map)
        return Response.ok(result)
    }

    override fun weatherCity(city: String?): ResponseData<*> {
        if (null == city)
            return Response.error("loss param: city")
        val map: Map<String, String> = mapOf("city" to city)
        val result: String = HttpUtil.getGZip(GET_WEATHER_URL,map)?:""
        val data = try {
            jacksonObjectMapper().readValue<WeatherResult>(result)
        } catch (e: JsonProcessingException) {
            null
        }?: return Response.error(GET_WEATHER_ERROR)
        if (data.status != 1000)
            return Response.error(GET_WEATHER_ERROR)
        transWeatherData(data)
        return Response.ok(data.data)
    }

    private fun transWeatherData(result: WeatherResult) {
        result.data.yesterday.fl = removeK(result.data.yesterday.fl)
        result.data.forecast.forEach { it.fengli = removeK(it.fengli) }
    }

    private fun removeK(str: String) : String {
        val pattern = Pattern.compile(".*<!\\[CDATA\\[(.*)]]>.*")
        val matcher = pattern.matcher(str)
        return if (matcher.matches()) matcher.group(1) else str
    }

    override fun sudoKu(array: Array<Array<Int>>?): ResponseData<*> {
        val check = checkSudoKu(array)
        if (null != check)
            return Response.error(check)
        val sudoKu = SudoKu()
        sudoKu.analyse(array)
        return Response.ok(sudoKu.result)
    }

    /**
     * 数独数据的检查
     */
    private fun checkSudoKu(array: Array<Array<Int>>?) : String? {
        if (null == array || array.isEmpty() || array.size > 9 )
            return "invalid sudoKu array."
        array.forEach {
            i -> run {
                i.forEach {
                    if (it < 0 || it > 9)
                        return "invalid sudoKu array."
                }
            }
        }
        return null
    }

}