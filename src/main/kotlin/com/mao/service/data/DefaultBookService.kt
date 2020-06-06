package com.mao.service.data

import com.mao.entity.Response
import com.mao.entity.ResponseData
import com.mao.mapper.BookMapper
import com.mao.mapper.DataDictMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest

/**
 * 估计数据处理
 * @author create by mao at 2020/06/05 16:34:21
 */
@Service
class DefaultBookService : BookService {

    @Autowired private lateinit var bookMapper: BookMapper
    @Autowired private lateinit var dataDictMapper: DataDictMapper

    override fun getBookById(request: HttpServletRequest): ResponseData<*> {
        val param = request.getParameter("id")
        val id = try {
            param?.toLong()?:-1
        } catch (e: NumberFormatException) {
            return Response.error("invalid param id: $param")
        }
        return Response.ok(bookMapper.getBookById(id))
    }

    override fun getBooks(request: HttpServletRequest): ResponseData<*> {
        return Response.ok(bookMapper.getBooks())
    }

    override fun getBookByPage(request: HttpServletRequest): ResponseData<*> {
        return Response.ok(bookMapper.getBookByPage())
    }

    override fun getBookClassify(request: HttpServletRequest): ResponseData<*> {
        return Response.ok(dataDictMapper.getDict(DataType.BOOK))
    }

    override fun getBookChapterById(request: HttpServletRequest): ResponseData<*> {
        return Response.ok(bookMapper.getBookChapterById())
    }

    override fun getBookChapters(request: HttpServletRequest): ResponseData<*> {
        return Response.ok(bookMapper.getBookChapters())
    }

    override fun updateBook(request: HttpServletRequest): ResponseData<*> {
        bookMapper.updateBook()
        return Response.ok("SUCCESS")
    }

    override fun updateBooks(request: HttpServletRequest): ResponseData<*> {
        bookMapper.updateBooks()
        return Response.ok("SUCCESS")
    }

    override fun updateBookChapter(request: HttpServletRequest): ResponseData<*> {
        bookMapper.updateBookChapter()
        return Response.ok("SUCCESS")
    }

    override fun updateBookChapters(request: HttpServletRequest): ResponseData<*> {
        bookMapper.updateBookChapters()
        return Response.ok("SUCCESS")
    }

    override fun saveBook(request: HttpServletRequest): ResponseData<*> {
        bookMapper.saveBook()
        return Response.ok("SUCCESS")
    }

    override fun saveBooks(request: HttpServletRequest): ResponseData<*> {
        bookMapper.saveBooks()
        return Response.ok("SUCCESS")
    }

    override fun saveBookChapter(request: HttpServletRequest): ResponseData<*> {
        bookMapper.saveBookChapter()
        return Response.ok("SUCCESS")
    }

    override fun saveBookChapters(request: HttpServletRequest): ResponseData<*> {
        bookMapper.saveBookChapters()
        return Response.ok("SUCCESS")
    }

    override fun deleteBook(request: HttpServletRequest): ResponseData<*> {
        bookMapper.deleteBook()
        return Response.ok("SUCCESS")
    }

    override fun deleteBookChapter(request: HttpServletRequest): ResponseData<*> {
        bookMapper.deleteBookChapter()
        return Response.ok("SUCCESS")
    }

}