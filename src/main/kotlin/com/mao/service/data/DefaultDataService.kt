package com.mao.service.data

import com.mao.entity.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest


/**
 * 数据处理
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

    /**
     * 数据处理
     */
    override fun dataOperation(operation: String, data: String, type: String, request: HttpServletRequest): ResponseData<*> {
        val ope = TypeOperation.requestType(operation)
        if (!TypeOperation.canRequest(ope,request))
            return Response.notAllowed("request method ${request.method} not allowed.")
        val method = TypeOperation.dataMethod(type)
        return when (TypeOperation.dataType(data)) {
            DataType.BOOK -> bookOperation(ope,method)
            DataType.BJX -> bjxOperation(ope,method)
            DataType.BUDDHIST -> buddhistOperation(ope,method)
            DataType.LIVE -> liveOperation(ope,method)
            DataType.MOVIE -> movieOperation(ope,method)
            DataType.PIC -> picOperation(ope,method)
            else -> dataError(data)
        }
    }

    private fun bookOperation(operation: RequestType, method: DataMethod) : ResponseData<*> {
        return when (operation) {
            RequestType.SEARCH ->
                when (method) {
                    DataMethod.SRC -> bookService.getBookById()
                    DataMethod.LIST -> bookService.getBooks()
                    DataMethod.PAGE -> bookService.getBookByPage()
                    DataMethod.CHAPTER -> bookService.getBookChapterById()
                    DataMethod.CHAPTERS -> bookService.getBookChapters()
                    else -> methodError(method.name)
                }
            else -> operationError(operation.name)
        }
    }

    private fun bjxOperation(operation: RequestType, method: DataMethod) : ResponseData<*> {
        return when (operation) {
            RequestType.SEARCH ->
                when (method) {
                    DataMethod.SRC -> bjxService.getBjxById()
                    DataMethod.LIST -> bjxService.getBjxS()
                    DataMethod.PAGE -> bjxService.getBjxByPage()
                    else -> methodError(method.name)
                }
            else -> operationError(operation.name)
        }
    }

    private fun buddhistOperation(operation: RequestType, method: DataMethod) : ResponseData<*> {
        return when (operation) {
            RequestType.SEARCH ->
                when (method) {
                    DataMethod.SRC -> buddhistService.getBuddhistById()
                    DataMethod.LIST -> buddhistService.getBuddhists()
                    DataMethod.PAGE -> buddhistService.getBuddhistByPage()
                    DataMethod.CHAPTER -> buddhistService.getBuddhistChapterById()
                    DataMethod.CHAPTERS -> buddhistService.getBuddhistChapters()
                    else -> methodError(method.name)
                }
            else -> operationError(operation.name)
        }
    }

    private fun liveOperation(operation: RequestType, method: DataMethod) : ResponseData<*> {
        return when (operation) {
            RequestType.SEARCH ->
                when (method) {
                    DataMethod.SRC -> liveService.getLiveById()
                    DataMethod.LIST -> liveService.getLives()
                    DataMethod.PAGE -> liveService.getLiveByPage()
                    else -> methodError(method.name)
                }
            else -> operationError(operation.name)
        }
    }

    private fun movieOperation(operation: RequestType, method: DataMethod) : ResponseData<*> {
        return when (operation) {
            RequestType.SEARCH ->
                when (method) {
                    DataMethod.SRC -> movieService.getMovieById()
                    DataMethod.LIST -> movieService.getMovies()
                    DataMethod.PAGE -> movieService.getMovieByPage()
                    else -> methodError(method.name)
                }
            else -> operationError(operation.name)
        }
    }

    private fun picOperation(operation: RequestType, method: DataMethod) : ResponseData<*> {
        return when (operation) {
            RequestType.SEARCH ->
                when (method) {
                    DataMethod.SRC -> picService.getPicById()
                    DataMethod.LIST -> picService.getPics()
                    DataMethod.PAGE -> picService.getPicByPage()
                    else -> methodError(method.name)
                }
            else -> operationError(operation.name)
        }
    }

    private fun operationError(name: String) : ResponseData<String> {
        return Response.error("not support operation type $name yet.")
    }

    private fun dataError(name: String) : ResponseData<String> {
        return Response.error("not support data type $name yet.")
    }

    private fun methodError(name: String) : ResponseData<String> {
        return Response.error("not support method type $name yet.")
    }

}

@Service
class DefaultBookService : BookService {
    override fun getBookById(): ResponseData<*> {
        TODO("Not yet implemented")
    }

    override fun getBooks(): ResponseData<*> {
        TODO("Not yet implemented")
    }

    override fun getBookByPage(): ResponseData<*> {
        TODO("Not yet implemented")
    }

    override fun getBookChapterById(): ResponseData<*> {
        TODO("Not yet implemented")
    }

    override fun getBookChapters(): ResponseData<*> {
        TODO("Not yet implemented")
    }
}

@Service
class DefaultBjxService : BjxService {
    override fun getBjxById(): ResponseData<*> {
        TODO("Not yet implemented")
    }

    override fun getBjxS(): ResponseData<*> {
        TODO("Not yet implemented")
    }

    override fun getBjxByPage(): ResponseData<*> {
        TODO("Not yet implemented")
    }
}

@Service
class DefaultBuddhistService : BuddhistService {
    override fun getBuddhistById(): ResponseData<*> {
        TODO("Not yet implemented")
    }

    override fun getBuddhists(): ResponseData<*> {
        TODO("Not yet implemented")
    }

    override fun getBuddhistByPage(): ResponseData<*> {
        TODO("Not yet implemented")
    }

    override fun getBuddhistChapterById(): ResponseData<*> {
        TODO("Not yet implemented")
    }

    override fun getBuddhistChapters(): ResponseData<*> {
        TODO("Not yet implemented")
    }
}

@Service
class DefaultLiveService : LiveService {
    override fun getLiveById(): ResponseData<*> {
        TODO("Not yet implemented")
    }

    override fun getLives(): ResponseData<*> {
        TODO("Not yet implemented")
    }

    override fun getLiveByPage(): ResponseData<*> {
        TODO("Not yet implemented")
    }
}

@Service
class DefaultMovieService : MovieService {
    override fun getMovieById(): ResponseData<*> {
        TODO("Not yet implemented")
    }

    override fun getMovies(): ResponseData<*> {
        TODO("Not yet implemented")
    }

    override fun getMovieByPage(): ResponseData<*> {
        TODO("Not yet implemented")
    }
}

@Service
class DefaultPicService : PicService {
    override fun getPicById(): ResponseData<*> {
        TODO("Not yet implemented")
    }

    override fun getPics(): ResponseData<*> {
        TODO("Not yet implemented")
    }

    override fun getPicByPage(): ResponseData<*> {
        TODO("Not yet implemented")
    }
}