package com.mao.service.data

import com.mao.entity.Response
import com.mao.entity.ResponseData
import com.mao.mapper.BuddhistMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest

/**
 * 佛经数据处理
 * @author create by mao at 2020/06/05 16:46:45
 */
@Service
class DefaultBuddhistService : BuddhistService {

    @Autowired private lateinit var buddhistMapper: BuddhistMapper

    override fun getBuddhistById(request: HttpServletRequest): ResponseData<*> {
        return Response.ok(buddhistMapper.getBuddhistById())
    }

    override fun getBuddhists(request: HttpServletRequest): ResponseData<*> {
        return Response.ok(buddhistMapper.getBuddhists())
    }

    override fun getBuddhistByPage(request: HttpServletRequest): ResponseData<*> {
        return Response.ok(buddhistMapper.getBuddhistByPage())
    }

    override fun getBuddhistClassify(request: HttpServletRequest): ResponseData<*> {
        return Response.ok(buddhistMapper.getBuddhistClassify())
    }

    override fun getBuddhistChapterById(request: HttpServletRequest): ResponseData<*> {
        return Response.ok(buddhistMapper.getBuddhistChapterById())
    }

    override fun getBuddhistChapters(request: HttpServletRequest): ResponseData<*> {
        return Response.ok(buddhistMapper.getBuddhistChapters())
    }

    override fun updateBuddhist(request: HttpServletRequest): ResponseData<*> {
        buddhistMapper.updateBuddhist()
        return Response.ok("SUCCESS")
    }

    override fun updateBuddhists(request: HttpServletRequest): ResponseData<*> {
        buddhistMapper.updateBuddhists()
        return Response.ok("SUCCESS")
    }

    override fun updateBuddhistChapter(request: HttpServletRequest): ResponseData<*> {
        buddhistMapper.updateBuddhistChapter()
        return Response.ok("SUCCESS")
    }

    override fun updateBuddhistChapters(request: HttpServletRequest): ResponseData<*> {
        buddhistMapper.updateBuddhistChapters()
        return Response.ok("SUCCESS")
    }

    override fun saveBuddhist(request: HttpServletRequest): ResponseData<*> {
        buddhistMapper.saveBuddhist()
        return Response.ok("SUCCESS")
    }

    override fun saveBuddhists(request: HttpServletRequest): ResponseData<*> {
        buddhistMapper.saveBuddhists()
        return Response.ok("SUCCESS")
    }

    override fun saveBuddhistChapter(request: HttpServletRequest): ResponseData<*> {
        buddhistMapper.saveBuddhistChapter()
        return Response.ok("SUCCESS")
    }

    override fun saveBuddhistChapters(request: HttpServletRequest): ResponseData<*> {
        buddhistMapper.saveBuddhistChapters()
        return Response.ok("SUCCESS")
    }

    override fun deleteBuddhist(request: HttpServletRequest): ResponseData<*> {
        buddhistMapper.deleteBuddhist()
        return Response.ok("SUCCESS")
    }

    override fun deleteBuddhistChapter(request: HttpServletRequest): ResponseData<*> {
        buddhistMapper.deleteBuddhistChapter()
        return Response.ok("SUCCESS")
    }

}