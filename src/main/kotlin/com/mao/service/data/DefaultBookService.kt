package com.mao.service.data

import com.mao.entity.*
import com.mao.mapper.BookMapper
import com.mao.mapper.DataDictMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest

/**
 * 古籍数据处理
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
        val param = ParamUtil.paramForm(request,BookParam::class.java)?:BookParam.default()
        param.correct()
        return Response.ok(bookMapper.getBooks(param))
    }

    override fun getBookByPage(request: HttpServletRequest): ResponseData<*> {
        val param = ParamUtil.paramForm(request,BookParam::class.java)?: BookParam.default()
        param.correct()
        val current = param.page
        param.page = if (current <= 0) 0 else current.dec().times(param.row)
        val list = bookMapper.getBookByPage(param)
        val total = bookMapper.getBookTotalPage(param)
        param.page = current
        return Response.ok(PageData(totalPage(total,param.row),param.page,param,list))
    }

    override fun getBookClassify(request: HttpServletRequest): ResponseData<*> {
        return Response.ok(dataDictMapper.getDict(DataType.BOOK))
    }

    override fun getBookChapterById(request: HttpServletRequest): ResponseData<*> {
        val param = request.getParameter("id")
        val id = try {
            param?.toLong()?:-1
        } catch (e: NumberFormatException) {
            return Response.error("invalid param id: $param")
        }
        return Response.ok(bookMapper.getBookChapterById(id))
    }

    override fun getBookChapters(request: HttpServletRequest): ResponseData<*> {
        val param = request.getParameter("book_id")
        val bookId = try {
            param?.toLong()?:-1
        } catch (e: NumberFormatException) {
            return Response.error("invalid param book_id: $param")
        }
        return Response.ok(bookMapper.getBookChapters(bookId))
    }

    override fun updateBook(request: HttpServletRequest): ResponseData<*> {
        val book = ParamUtil.paramBody(request,Book::class.java) ?: return Response.error("loss param body")
        val check = ParamUtil.check(book)
        if (null != check)
            return Response.error(check)
        //bookMapper.updateBook()
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

    /**
     * 获取分页数据的总页数
     * 举例
     * total    row    d    equals    result
     * 100      20     5    true      5
     * 101      20     5    false     5+1
     */
    private fun totalPage(total: Int, row: Int) : Int {
        if (total <= 0)
            return 0
        val d = total.div(row)
        return if (row.times(d) == total) d else d.inc()
    }

}