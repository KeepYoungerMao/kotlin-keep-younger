package com.mao.service.data

import com.mao.entity.Response
import com.mao.entity.ResponseData
import com.mao.mapper.BjxMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest

/**
 * 百家姓数据处理
 * @author create by mao at 2020/06/05 16:45:09
 */
@Service
class DefaultBjxService : BjxService {

    @Autowired private lateinit var bjxMapper: BjxMapper

    override fun getBjxById(request: HttpServletRequest): ResponseData<*> {
        return Response.ok(bjxMapper.getBjxById())
    }

    override fun getBjxS(request: HttpServletRequest): ResponseData<*> {
        return Response.ok(bjxMapper.getBjxS())
    }

    override fun getBjxByPage(request: HttpServletRequest): ResponseData<*> {
        return Response.ok(bjxMapper.getBjxByPage())
    }

    override fun getBjxClassify(request: HttpServletRequest): ResponseData<*> {
        return Response.ok(bjxMapper.getBjxClassify())
    }

    override fun updateBjx(request: HttpServletRequest): ResponseData<*> {
        bjxMapper.updateBjx()
        return Response.ok("SUCCESS")
    }

    override fun updateBjxS(request: HttpServletRequest): ResponseData<*> {
        bjxMapper.updateBjxS()
        return Response.ok("SUCCESS")
    }

    override fun saveBjx(request: HttpServletRequest): ResponseData<*> {
        bjxMapper.saveBjx()
        return Response.ok("SUCCESS")
    }

    override fun saveBjxS(request: HttpServletRequest): ResponseData<*> {
        bjxMapper.saveBjxS()
        return Response.ok("SUCCESS")
    }

    override fun deleteBjx(request: HttpServletRequest): ResponseData<*> {
        bjxMapper.deleteBjx()
        return Response.ok("SUCCESS")
    }

}