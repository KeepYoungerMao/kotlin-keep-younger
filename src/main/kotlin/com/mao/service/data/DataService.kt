package com.mao.service.data

import com.mao.entity.ResponseData
import javax.servlet.http.HttpServletRequest


/**
 * 数据处理
 * create by mao at 2020/6/4 21:18
 */
interface DataService {

    /**
     * 数据的操作
     * 包括增删改查
     * 操作类型：查询、保存、更新、删除。详见操作类型enum
     * @see com.mao.service.data.RequestType
     * 数据类型：处理的各种数据。详见数据类型enum
     * @see com.mao.service.data.DataType
     * 请求类型：表示操作此数据的方式。
     * @param operation 操作类型
     * @param data 数据类型
     * @param type 请求类型
     * @param request HttpServletRequest 用于判断操作类型
     */
    fun dataOperation(operation: String, data: String, type: String, request: HttpServletRequest) : ResponseData<*>

}

/**
 * 古籍数据处理
 */
interface BookService {
    fun getBookById(request: HttpServletRequest) : ResponseData<*>
    fun getBooks(request: HttpServletRequest) : ResponseData<*>
    fun getBookByPage(request: HttpServletRequest) : ResponseData<*>
    fun getBookClassify(request: HttpServletRequest) : ResponseData<*>
    fun getBookChapterById(request: HttpServletRequest) : ResponseData<*>
    fun getBookChapters(request: HttpServletRequest) : ResponseData<*>
    fun updateBook(request: HttpServletRequest) : ResponseData<*>
    fun updateBooks(request: HttpServletRequest) : ResponseData<*>
    fun updateBookChapter(request: HttpServletRequest) : ResponseData<*>
    fun updateBookChapters(request: HttpServletRequest) : ResponseData<*>
    fun saveBook(request: HttpServletRequest) : ResponseData<*>
    fun saveBooks(request: HttpServletRequest) : ResponseData<*>
    fun saveBookChapter(request: HttpServletRequest) : ResponseData<*>
    fun saveBookChapters(request: HttpServletRequest) : ResponseData<*>
    fun deleteBook(request: HttpServletRequest) : ResponseData<*>
    fun deleteBookChapter(request: HttpServletRequest) : ResponseData<*>
}

/**
 * 百家姓数据处理
 */
interface BjxService {
    fun getBjxById(request: HttpServletRequest) : ResponseData<*>
    fun getBjxS(request: HttpServletRequest) : ResponseData<*>
    fun getBjxByPage(request: HttpServletRequest) : ResponseData<*>
    fun getBjxClassify(request: HttpServletRequest) : ResponseData<*>
    fun updateBjx(request: HttpServletRequest) : ResponseData<*>
    fun updateBjxS(request: HttpServletRequest) : ResponseData<*>
    fun saveBjx(request: HttpServletRequest) : ResponseData<*>
    fun saveBjxS(request: HttpServletRequest) : ResponseData<*>
    fun deleteBjx(request: HttpServletRequest) : ResponseData<*>
}

/**
 * 佛经数据处理
 */
interface BuddhistService {
    fun getBuddhistById(request: HttpServletRequest) : ResponseData<*>
    fun getBuddhists(request: HttpServletRequest) : ResponseData<*>
    fun getBuddhistByPage(request: HttpServletRequest) : ResponseData<*>
    fun getBuddhistClassify(request: HttpServletRequest) : ResponseData<*>
    fun getBuddhistChapterById(request: HttpServletRequest) : ResponseData<*>
    fun getBuddhistChapters(request: HttpServletRequest) : ResponseData<*>
    fun updateBuddhist(request: HttpServletRequest) : ResponseData<*>
    fun updateBuddhists(request: HttpServletRequest) : ResponseData<*>
    fun updateBuddhistChapter(request: HttpServletRequest) : ResponseData<*>
    fun updateBuddhistChapters(request: HttpServletRequest) : ResponseData<*>
    fun saveBuddhist(request: HttpServletRequest) : ResponseData<*>
    fun saveBuddhists(request: HttpServletRequest) : ResponseData<*>
    fun saveBuddhistChapter(request: HttpServletRequest) : ResponseData<*>
    fun saveBuddhistChapters(request: HttpServletRequest) : ResponseData<*>
    fun deleteBuddhist(request: HttpServletRequest) : ResponseData<*>
    fun deleteBuddhistChapter(request: HttpServletRequest) : ResponseData<*>
}

/**
 * 直播源数据处理
 */
interface LiveService {
    fun getLiveById(request: HttpServletRequest) : ResponseData<*>
    fun getLives(request: HttpServletRequest) : ResponseData<*>
    fun getLiveByPage(request: HttpServletRequest) : ResponseData<*>
    fun getLiveClassify(request: HttpServletRequest) : ResponseData<*>
    fun updateLive(request: HttpServletRequest) : ResponseData<*>
    fun updateLives(request: HttpServletRequest) : ResponseData<*>
    fun saveLive(request: HttpServletRequest) : ResponseData<*>
    fun saveLives(request: HttpServletRequest) : ResponseData<*>
    fun deleteLive(request: HttpServletRequest) : ResponseData<*>
}

/**
 * 电影数据处理
 */
interface MovieService {
    fun getMovieById(request: HttpServletRequest) : ResponseData<*>
    fun getMovies(request: HttpServletRequest) : ResponseData<*>
    fun getMovieByPage(request: HttpServletRequest) : ResponseData<*>
    fun getMovieClassify(request: HttpServletRequest) : ResponseData<*>
    fun updateMovie(request: HttpServletRequest) : ResponseData<*>
    fun updateMovies(request: HttpServletRequest) : ResponseData<*>
    fun saveMovie(request: HttpServletRequest) : ResponseData<*>
    fun saveMovies(request: HttpServletRequest) : ResponseData<*>
    fun deleteMovie(request: HttpServletRequest) : ResponseData<*>
}

/**
 * 图片数据处理
 */
interface PicService {
    fun getPicById(request: HttpServletRequest) : ResponseData<*>
    fun getPics(request: HttpServletRequest) : ResponseData<*>
    fun getPicByPage(request: HttpServletRequest) : ResponseData<*>
    fun getPicClassify(request: HttpServletRequest) : ResponseData<*>
    fun updatePic(request: HttpServletRequest) : ResponseData<*>
    fun updatePics(request: HttpServletRequest) : ResponseData<*>
    fun savePic(request: HttpServletRequest) : ResponseData<*>
    fun savePics(request: HttpServletRequest) : ResponseData<*>
    fun deletePic(request: HttpServletRequest) : ResponseData<*>
}