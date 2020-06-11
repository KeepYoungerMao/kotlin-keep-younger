package com.mao.service.log

import com.mao.config.IdBuilder
import com.mao.config.WebSecurityConfigurer
import com.mao.entity.Log
import com.mao.mapper.LogMapper
import com.mao.service.data.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.function.Supplier
import javax.servlet.http.HttpServletRequest

interface LogService {
    fun saveLog(user: String?, request: HttpServletRequest, status: Int, remark: String?)
}

@Service
class DefaultLogService : LogService {

    companion object {
        private const val LOCALHOST = "0:0:0:0:0:0:0:1"
        private const val LOCALHOST_S = "127.0.0.1"
        private val asyncService: ExecutorService = Executors.newFixedThreadPool(4)!!
    }

    @Autowired private lateinit var idBuilder: IdBuilder
    @Autowired private lateinit var logMapper: LogMapper

    override fun saveLog(user: String?, request: HttpServletRequest, status: Int, remark: String?) {
        saveLog(user,request.requestURI, request.remoteAddr,status,remark)
    }

    /**
     * 日志数据保存方法
     * 采用异步处理
     * 方法内容的处理限制了请求数据类型Enum类中的书写：必须所有的Enum的ERROR类型在 0 的位置（首位）
     * 要不然就要主动设置ordinal值
     * 详见 com.mao.service.Request.kt
     */
    private fun saveLog(user: String?, uri: String, ip: String, status: Int, remark: String?) {
        CompletableFuture.supplyAsync(Supplier {
            if (uri.startsWith(WebSecurityConfigurer.API_PRE)) {
                val ips = if (ip == LOCALHOST) LOCALHOST_S else ip
                val id = idBuilder.nextId()
                val splits = uri.substring(WebSecurityConfigurer.API_PRE.length.inc()).split("/")
                val resource = if (splits.isNotEmpty()) TypeOperation.resourceType(splits[0]).ordinal else ResourceType.ERROR.ordinal
                val operation = if (splits.size > 1) TypeOperation.requestType(splits[1]).ordinal else RequestType.ERROR.ordinal
                val data = if (splits.size > 2) {
                    when (resource) {
                        ResourceType.DATA.ordinal -> TypeOperation.dataType(splits[2]).ordinal
                        ResourceType.HIS.ordinal -> TypeOperation.hisType(splits[2]).ordinal
                        else -> DataType.ERROR.ordinal
                    }
                } else {
                    DataType.ERROR.ordinal
                }
                val method = if (splits.size > 3) {
                    when (resource) {
                        ResourceType.DATA.ordinal -> TypeOperation.dataMethod(splits[3]).ordinal
                        ResourceType.HIS.ordinal -> TypeOperation.hisMethod(splits[3]).ordinal
                        else -> DataMethod.ERROR.ordinal
                    }
                } else {
                    DataMethod.ERROR.ordinal
                }
                val log = Log(id.toString(),user,uri,ips,operation,resource,data,method,status,remark,System.currentTimeMillis())
                logMapper.saveLog(log)
            }
            true
        }, asyncService)
    }

}