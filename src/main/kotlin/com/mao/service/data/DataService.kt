package com.mao.service.data

import com.mao.entity.ResponseData
import javax.servlet.http.HttpServletRequest


/**
 * create by mao at 2020/6/4 21:18
 */
interface DataService {

    /**
     * 数据的操作
     * 包括增删改查
     * 操作类型：查询、保存、更新、删除。详见操作类型enum
     * @see com.mao.entity.RequestType
     * 数据类型：处理的各种数据。详见数据类型enum
     * @see com.mao.entity.DataType
     * 请求类型：表示操作此数据的方式。
     * @param operation 操作类型
     * @param data 数据类型
     * @param type 请求类型
     * @param request HttpServletRequest 用于判断操作类型
     */
    fun dataOperation(operation: String, data: String, type: String, request: HttpServletRequest) : ResponseData<*>

}

interface BookService {
    fun getBookById() : ResponseData<*>
    fun getBooks() : ResponseData<*>
    fun getBookByPage() : ResponseData<*>
    fun getBookChapterById() : ResponseData<*>
    fun getBookChapters() : ResponseData<*>
}

interface BjxService {
    fun getBjxById() : ResponseData<*>
    fun getBjxS() : ResponseData<*>
    fun getBjxByPage() : ResponseData<*>
}

interface BuddhistService {
    fun getBuddhistById() : ResponseData<*>
    fun getBuddhists() : ResponseData<*>
    fun getBuddhistByPage() : ResponseData<*>
    fun getBuddhistChapterById() : ResponseData<*>
    fun getBuddhistChapters() : ResponseData<*>
}

interface LiveService {
    fun getLiveById() : ResponseData<*>
    fun getLives() : ResponseData<*>
    fun getLiveByPage() : ResponseData<*>
}

interface MovieService {
    fun getMovieById() : ResponseData<*>
    fun getMovies() : ResponseData<*>
    fun getMovieByPage() : ResponseData<*>
}

interface PicService {
    fun getPicById() : ResponseData<*>
    fun getPics() : ResponseData<*>
    fun getPicByPage() : ResponseData<*>
}