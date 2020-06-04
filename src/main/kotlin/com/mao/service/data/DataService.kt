package com.mao.service.data

import com.mao.entity.ResponseData
import javax.servlet.http.HttpServletRequest


/**
 * create by mao at 2020/6/4 21:18
 */
interface DataService {

    fun dataOperation(req: String, data: String, type: String, request: HttpServletRequest) : ResponseData<*>

}