package com.mao.service.data

import com.mao.entity.Response
import com.mao.entity.ResponseData
import com.mao.mapper.LiveMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest

/**
 * 直播源数据处理
 * @author create by mao at 2020/06/05 16:47:21
 */
@Service
class DefaultLiveService : LiveService {

    @Autowired private lateinit var liveMapper: LiveMapper

    override fun getLiveById(request: HttpServletRequest): ResponseData<*> {
        return Response.ok(liveMapper.getLiveById())
    }

    override fun getLives(request: HttpServletRequest): ResponseData<*> {
        return Response.ok(liveMapper.getLives())
    }

    override fun getLiveByPage(request: HttpServletRequest): ResponseData<*> {
        return Response.ok(liveMapper.getLiveByPage())
    }

    override fun getLiveClassify(request: HttpServletRequest): ResponseData<*> {
        return Response.ok(liveMapper.getLiveClassify())
    }

    override fun updateLive(request: HttpServletRequest): ResponseData<*> {
        liveMapper.updateLive()
        return Response.ok("SUCCESS")
    }

    override fun updateLives(request: HttpServletRequest): ResponseData<*> {
        liveMapper.updateLives()
        return Response.ok("SUCCESS")
    }

    override fun saveLive(request: HttpServletRequest): ResponseData<*> {
        liveMapper.saveLive()
        return Response.ok("SUCCESS")
    }

    override fun saveLives(request: HttpServletRequest): ResponseData<*> {
        liveMapper.saveLives()
        return Response.ok("SUCCESS")
    }

    override fun deleteLive(request: HttpServletRequest): ResponseData<*> {
        liveMapper.deleteLive()
        return Response.ok("SUCCESS")
    }

}