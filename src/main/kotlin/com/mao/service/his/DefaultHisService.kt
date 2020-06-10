package com.mao.service.his

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.mao.entity.Response
import com.mao.entity.ResponseData
import com.mao.entity.WeatherResult
import com.mao.service.data.ParamUtil
import com.mao.util.HttpUtil
import org.apache.http.impl.client.CloseableHttpClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.regex.Pattern
import javax.servlet.http.HttpServletRequest

@Service
class DefaultHisService : HisService {

    companion object {
        const val AK = "Po86Y8fZwYv5fpcQIX7MVk1DaMOl3VwB"
        const val GET_LOCATION_URL = "http://api.map.baidu.com/location/ip"
        const val GET_IP_ADDRESS_ERROR = "cannot request any message form SDK, please check whether ip is correct";
        const val GET_WEATHER_ERROR = "request service api failed. connect failed."
    }

    @Autowired private lateinit var closeableHttpClient: CloseableHttpClient

    override fun addressIp(request: HttpServletRequest): ResponseData<*> {
        val ip = request.getParameter("ip") ?: return Response.error("loss param: ip")
        val json = HttpUtil.get("http://api.map.baidu.com/location/ip?ip=$ip&ak=$AK",closeableHttpClient)
        return Response.ok(json)
    }

    override fun weatherCity(request: HttpServletRequest): ResponseData<*> {
        val city = request.getParameter("city") ?: return Response.error("loss param: city")
        val json = HttpUtil.get("http://wthrcdn.etouch.cn/weather_mini?city=$city", closeableHttpClient)
        return try {
            val result = jacksonObjectMapper().readValue<WeatherResult>(json ?: "")
            transWeatherData(result)
            Response.ok(result.data)
        } catch (e: Exception) {
            Response.error(GET_WEATHER_ERROR)
        }
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

    override fun sudoKu(request: HttpServletRequest): ResponseData<*> {
        val array = ParamUtil.paramBody(request,Array<Array<Int>>::class.java)
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