package com.mao.mapper

import com.mao.entity.*
import com.mao.service.data.DataType
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Param
import org.springframework.stereotype.Repository

@Repository
@Mapper
interface BookMapper {
    fun getBookById(@Param("id") id: Long) : Book
    fun getBooks(param: BookParam) : MutableList<Book>
    fun getBookByPage(param: BookParam) : MutableList<Book>
    fun getBookTotalPage(param: BookParam) : Int
    fun getBookChapterById(@Param("id") id: Long) : BookChapter
    fun getBookChapters(@Param("book_id") book_id: Long) : MutableList<BookChapter>
    fun updateBook(book: Book)
    fun updateBookChapter()
    fun updateBookChapters()
    fun saveBook()
    fun saveBooks()
    fun saveBookChapter()
    fun saveBookChapters()
    fun deleteBook()
    fun deleteBookChapter()
}

@Repository
@Mapper
interface BjxMapper {
    fun getBjxById() : Bjx
    fun getBjxS() : MutableList<Bjx>
    fun getBjxByPage() : MutableList<Bjx>
    fun getBjxClassify() : MutableList<Any>
    fun updateBjx()
    fun updateBjxS()
    fun saveBjx()
    fun saveBjxS()
    fun deleteBjx()
}

@Repository
@Mapper
interface BuddhistMapper {
    fun getBuddhistById() : Buddhist
    fun getBuddhists() : MutableList<Buddhist>
    fun getBuddhistByPage() : MutableList<Buddhist>
    fun getBuddhistClassify() : MutableList<Any>
    fun getBuddhistChapterById() : BuddhistChapter
    fun getBuddhistChapters() : MutableList<BuddhistChapter>
    fun updateBuddhist()
    fun updateBuddhists()
    fun updateBuddhistChapter()
    fun updateBuddhistChapters()
    fun saveBuddhist()
    fun saveBuddhists()
    fun saveBuddhistChapter()
    fun saveBuddhistChapters()
    fun deleteBuddhist()
    fun deleteBuddhistChapter()
}

@Repository
@Mapper
interface LiveMapper {
    fun getLiveById() : Live
    fun getLives() : MutableList<Live>
    fun getLiveByPage() : MutableList<Live>
    fun getLiveClassify() : MutableList<Any>
    fun updateLive()
    fun updateLives()
    fun saveLive()
    fun saveLives()
    fun deleteLive()
}

@Repository
@Mapper
interface MovieMapper {
    fun getMovieById() : Movie
    fun getMovies() : MutableList<Movie>
    fun getMovieByPage() : MutableList<Movie>
    fun getMovieClassify() : MutableList<Any>
    fun updateMovie()
    fun updateMovies()
    fun saveMovie()
    fun saveMovies()
    fun deleteMovie()
}

@Repository
@Mapper
interface PicMapper {
    fun getPicById() : Pic
    fun getPics() : MutableList<Pic>
    fun getPicByPage() : MutableList<Pic>
    fun getPicClassify() : MutableList<Any>
    fun updatePic()
    fun updatePics()
    fun savePic()
    fun savePics()
    fun deletePic()
}

@Repository
@Mapper
interface DataDictMapper {
    fun getDict(@Param("data") data: DataType) : MutableList<DataDict>
}