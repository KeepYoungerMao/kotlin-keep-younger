package com.mao.service.data

import com.mao.entity.*
import com.mao.mapper.PicMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest

/**
 * 图片数据处理
 * @author create by mao at 2020/06/05 16:49:25
 */
@Service
class DefaultPicService : PicService {

    @Autowired private lateinit var picMapper: PicMapper

    override fun getPicById(request: HttpServletRequest): ResponseData<*> {
        return Response.ok(picMapper.getPicById())
    }

    override fun getPics(request: HttpServletRequest): ResponseData<*> {
        return Response.ok(picMapper.getPics())
    }

    override fun getPicByPage(request: HttpServletRequest): ResponseData<*> {
        return Response.ok(picMapper.getPicByPage())
    }

    override fun getPicClassify(request: HttpServletRequest): ResponseData<*> {
        return Response.ok(picMapper.getPicClassify())
    }

    override fun updatePic(request: HttpServletRequest): ResponseData<*> {
        picMapper.updatePic()
        return Response.ok("SUCCESS")
    }

    override fun updatePics(request: HttpServletRequest): ResponseData<*> {
        picMapper.updatePics()
        return Response.ok("SUCCESS")
    }

    override fun savePic(request: HttpServletRequest): ResponseData<*> {
        picMapper.savePic()
        return Response.ok("SUCCESS")
    }

    override fun savePics(request: HttpServletRequest): ResponseData<*> {
        picMapper.savePics()
        return Response.ok("SUCCESS")
    }

    override fun deletePic(request: HttpServletRequest): ResponseData<*> {
        picMapper.deletePic()
        return Response.ok("SUCCESS")
    }

}