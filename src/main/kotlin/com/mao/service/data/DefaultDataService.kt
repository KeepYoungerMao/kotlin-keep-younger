package com.mao.service.data

import com.mao.entity.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest


/**
 * 数据处理
 * 此类只用于请求分发
 * 验证分类参数，调用合适的方法
 * create by mao at 2020/6/4 21:18
 */
@Service
class DefaultDataService : DataService {

    @Autowired private lateinit var bookService: BookService
    @Autowired private lateinit var bjxService: BjxService
    @Autowired private lateinit var buddhistService: BuddhistService
    @Autowired private lateinit var liveService: LiveService
    @Autowired private lateinit var movieService: MovieService
    @Autowired private lateinit var picService: PicService

    companion object {
        const val OPERATION = "operation"
        const val DATA = "data"
        const val METHOD = "method"
    }

    /**
     * 数据处理
     */
    override fun dataOperation(operation: String, data: String, type: String, request: HttpServletRequest): ResponseData<*> {
        val ope = TypeOperation.requestType(operation)
        if (!TypeOperation.canRequest(ope,request))
            return requestError(request.method)
        return when (TypeOperation.dataType(data)) {
            DataType.BOOK -> bookOperation(ope,type,request)
            DataType.BJX -> bjxOperation(ope,type,request)
            DataType.BUDDHIST -> buddhistOperation(ope,type,request)
            DataType.LIVE -> liveOperation(ope,type,request)
            DataType.MOVIE -> movieOperation(ope,type,request)
            DataType.PIC -> picOperation(ope,type,request)
            else -> dataError(data)
        }
    }

    /**
     * 古籍数据操作
     */
    private fun bookOperation(operation: RequestType, type: String, request: HttpServletRequest) : ResponseData<*> {
        val method = TypeOperation.dataMethod(type)
        return when (operation) {
            RequestType.SEARCH ->
                when (method) {
                    DataMethod.SRC -> bookService.getBookById(request)
                    DataMethod.LIST -> bookService.getBooks(request)
                    DataMethod.PAGE -> bookService.getBookByPage(request)
                    DataMethod.CLASSIFY -> bookService.getBookClassify(request)
                    DataMethod.CHAPTER -> bookService.getBookChapterById(request)
                    DataMethod.CHAPTERS -> bookService.getBookChapters(request)
                    else -> methodError(type)
                }
            RequestType.SAVE ->
                when (method) {
                    DataMethod.SRC -> bookService.saveBook(request)
                    DataMethod.LIST -> bookService.saveBooks(request)
                    DataMethod.CHAPTER -> bookService.saveBookChapter(request)
                    DataMethod.CHAPTERS -> bookService.saveBookChapters(request)
                    else -> methodError(type)
                }
            RequestType.EDIT ->
                when (method) {
                    DataMethod.SRC -> bookService.updateBook(request)
                    DataMethod.LIST -> bookService.updateBooks(request)
                    DataMethod.CHAPTER -> bookService.updateBookChapter(request)
                    DataMethod.CHAPTERS -> bookService.updateBookChapters(request)
                    else -> methodError(type)
                }
            RequestType.REMOVE ->
                when (method) {
                    DataMethod.SRC -> bookService.deleteBook(request)
                    DataMethod.CHAPTER -> bookService.deleteBookChapter(request)
                    else -> methodError(type)
                }
            else -> operationError(operation.name)
        }
    }

    /**
     * 百家姓数据操作
     */
    private fun bjxOperation(operation: RequestType, type: String, request: HttpServletRequest) : ResponseData<*> {
        val method = TypeOperation.dataMethod(type)
        return when (operation) {
            RequestType.SEARCH ->
                when (method) {
                    DataMethod.SRC -> bjxService.getBjxById(request)
                    DataMethod.LIST -> bjxService.getBjxS(request)
                    DataMethod.PAGE -> bjxService.getBjxByPage(request)
                    DataMethod.CLASSIFY -> bjxService.getBjxClassify(request)
                    else -> methodError(type)
                }
            RequestType.SAVE ->
                when (method) {
                    DataMethod.SRC -> bjxService.saveBjx(request)
                    DataMethod.LIST -> bjxService.saveBjxS(request)
                    else -> methodError(type)
                }
            RequestType.EDIT ->
                when (method) {
                    DataMethod.SRC -> bjxService.updateBjx(request)
                    DataMethod.LIST -> bjxService.updateBjxS(request)
                    else -> methodError(type)
                }
            RequestType.REMOVE ->
                when (method) {
                    DataMethod.SRC -> bjxService.deleteBjx(request)
                    else -> methodError(type)
                }
            else -> operationError(operation.name)
        }
    }

    /**
     * 佛经数据操作
     */
    private fun buddhistOperation(operation: RequestType, type: String, request: HttpServletRequest) : ResponseData<*> {
        val method = TypeOperation.dataMethod(type)
        return when (operation) {
            RequestType.SEARCH ->
                when (method) {
                    DataMethod.SRC -> buddhistService.getBuddhistById(request)
                    DataMethod.LIST -> buddhistService.getBuddhists(request)
                    DataMethod.PAGE -> buddhistService.getBuddhistByPage(request)
                    DataMethod.CLASSIFY -> buddhistService.getBuddhistClassify(request)
                    DataMethod.CHAPTER -> buddhistService.getBuddhistChapterById(request)
                    DataMethod.CHAPTERS -> buddhistService.getBuddhistChapters(request)
                    else -> methodError(type)
                }
            RequestType.SAVE ->
                when (method) {
                    DataMethod.SRC -> buddhistService.saveBuddhist(request)
                    DataMethod.LIST -> buddhistService.saveBuddhists(request)
                    DataMethod.CHAPTER -> buddhistService.saveBuddhistChapter(request)
                    DataMethod.CHAPTERS -> buddhistService.saveBuddhistChapters(request)
                    else -> methodError(type)
                }
            RequestType.EDIT ->
                when (method) {
                    DataMethod.SRC -> buddhistService.updateBuddhist(request)
                    DataMethod.LIST -> buddhistService.updateBuddhists(request)
                    DataMethod.CHAPTER -> buddhistService.updateBuddhistChapter(request)
                    DataMethod.CHAPTERS -> buddhistService.updateBuddhistChapters(request)
                    else -> methodError(type)
                }
            RequestType.REMOVE ->
                when (method) {
                    DataMethod.SRC -> buddhistService.deleteBuddhist(request)
                    DataMethod.CHAPTER -> buddhistService.deleteBuddhistChapter(request)
                    else -> methodError(type)
                }
            else -> operationError(operation.name)
        }
    }

    /**
     * 直播源数据操作
     */
    private fun liveOperation(operation: RequestType, type: String, request: HttpServletRequest) : ResponseData<*> {
        val method = TypeOperation.dataMethod(type)
        return when (operation) {
            RequestType.SEARCH ->
                when (method) {
                    DataMethod.SRC -> liveService.getLiveById(request)
                    DataMethod.LIST -> liveService.getLives(request)
                    DataMethod.PAGE -> liveService.getLiveByPage(request)
                    DataMethod.CLASSIFY -> liveService.getLiveClassify(request)
                    else -> methodError(type)
                }
            RequestType.SAVE ->
                when (method) {
                    DataMethod.SRC -> liveService.saveLive(request)
                    DataMethod.LIST -> liveService.saveLives(request)
                    else -> methodError(type)
                }
            RequestType.EDIT ->
                when (method) {
                    DataMethod.SRC -> liveService.updateLive(request)
                    DataMethod.LIST -> liveService.updateLives(request)
                    else -> methodError(type)
                }
            RequestType.REMOVE ->
                when (method) {
                    DataMethod.SRC -> liveService.deleteLive(request)
                    else -> methodError(type)
                }
            else -> operationError(operation.name)
        }
    }

    /**
     * 电影数据操作
     */
    private fun movieOperation(operation: RequestType, type: String, request: HttpServletRequest) : ResponseData<*> {
        val method = TypeOperation.dataMethod(type)
        return when (operation) {
            RequestType.SEARCH ->
                when (method) {
                    DataMethod.SRC -> movieService.getMovieById(request)
                    DataMethod.LIST -> movieService.getMovies(request)
                    DataMethod.PAGE -> movieService.getMovieByPage(request)
                    DataMethod.CLASSIFY -> movieService.getMovieClassify(request)
                    else -> methodError(type)
                }
            RequestType.SAVE ->
                when (method) {
                    DataMethod.SRC -> movieService.saveMovie(request)
                    DataMethod.LIST -> movieService.saveMovies(request)
                    else -> methodError(type)
                }
            RequestType.EDIT ->
                when (method) {
                    DataMethod.SRC -> movieService.updateMovie(request)
                    DataMethod.LIST -> movieService.updateMovies(request)
                    else -> methodError(type)
                }
            RequestType.REMOVE ->
                when (method) {
                    DataMethod.SRC -> movieService.deleteMovie(request)
                    else -> methodError(type)
                }
            else -> operationError(operation.name)
        }
    }

    /**
     * 图片数据操作
     */
    private fun picOperation(operation: RequestType, type: String, request: HttpServletRequest) : ResponseData<*> {
        val method = TypeOperation.dataMethod(type)
        return when (operation) {
            RequestType.SEARCH ->
                when (method) {
                    DataMethod.SRC -> picService.getPicById(request)
                    DataMethod.LIST -> picService.getPics(request)
                    DataMethod.PAGE -> picService.getPicByPage(request)
                    DataMethod.CLASSIFY -> picService.getPicClassify(request)
                    else -> methodError(type)
                }
            RequestType.SAVE ->
                when (method) {
                    DataMethod.SRC -> picService.savePic(request)
                    DataMethod.LIST -> picService.savePics(request)
                    else -> methodError(type)
                }
            RequestType.EDIT ->
                when (method) {
                    DataMethod.SRC -> picService.updatePic(request)
                    DataMethod.LIST -> picService.updatePics(request)
                    else -> methodError(type)
                }
            RequestType.REMOVE ->
                when (method) {
                    DataMethod.SRC -> picService.deletePic(request)
                    else -> methodError(type)
                }
            else -> operationError(operation.name)
        }
    }

    //请求类型错误
    private fun requestError(method: String) : ResponseData<String> = Response.notAllowed("request method[$method] not allowed.")
    //操作类型错误返回
    private fun operationError(name: String) : ResponseData<String> = oError(OPERATION,name)
    //数据类型错误返回
    private fun dataError(name: String) : ResponseData<String> = oError(DATA,name)
    //数据处理类型错误返回
    private fun methodError(name: String) : ResponseData<String> = oError(METHOD,name)
    //错误返回处理
    private fun oError(type: String, name: String) : ResponseData<String> = Response.notAllowed("not support $type type: $name yet.")

}