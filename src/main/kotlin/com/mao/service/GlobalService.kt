package com.mao.service

import com.mao.entity.Response
import com.mao.entity.ResponseData
import com.mao.service.data.*
import com.mao.service.his.HisService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest

interface GlobalService {

    /**
     * 请求总方法
     * @param resource 请求的资源类型
     * @param operation 操作类型
     * @param data 数据类型
     * @param type 请求类型
     * @param request HttpServletRequest 用于判断操作类型
     */
    fun operation(resource: String, operation: String, data: String, type: String, request: HttpServletRequest) : ResponseData<*>

}

@Service
class DefaultGlobalService : GlobalService {

    @Autowired private lateinit var dataService: DataService
    @Autowired private lateinit var hisService : HisService

    /**
     * 数据处理
     */
    override fun operation(resource: String, operation: String, data: String, type: String, request: HttpServletRequest): ResponseData<*> {
        return when (TypeOperation.resourceType(resource)) {
            ResourceType.HIS -> hisOperation(operation, data, type, request)
            ResourceType.DATA -> dataService.dataOperation(operation, data, type, request)
            else -> Response.error("no resource type: $resource")
        }
    }

    /**
     * his数据类型处理
     */
    private fun hisOperation(operation: String, data: String, type: String, request: HttpServletRequest) : ResponseData<*> {
        val ope = TypeOperation.requestType(operation)
        if (ope != RequestType.SEARCH)
            return Response.error("resource type[his data] only support: search type.")
        val req = request.method.toUpperCase()
        if (req != "POST")
            return Response.error("resource type[his data] only support request type: POST.")
        val method = TypeOperation.hisMethod(type)
        if (method == HisMethod.ERROR)
            return Response.error("not support method type: $method")
        return when (TypeOperation.hisType(data)) {
            HisType.IP_ADDRESS -> hisService.addressIp(request)
            HisType.CITY_WEATHER -> hisService.weatherCity(request)
            HisType.SUDO_KU -> hisService.sudoKu(request)
            HisType.ERROR -> Response.error("no $data type at resource his data.")
        }
    }

}