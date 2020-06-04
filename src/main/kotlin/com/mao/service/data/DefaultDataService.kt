package com.mao.service.data

import com.mao.entity.ResponseData
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest


/**
 * create by mao at 2020/6/4 21:18
 */
@Service
class DefaultDataService : DataService {

    override fun dataOperation(req: String, data: String, type: String, request: HttpServletRequest): ResponseData<*> {
        TODO("Not yet implemented")
    }

}