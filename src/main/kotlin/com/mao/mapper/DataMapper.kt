package com.mao.mapper

import com.mao.entity.*
import org.apache.ibatis.annotations.Mapper
import org.springframework.stereotype.Repository

@Repository
@Mapper
interface BookMapper {
    fun getBookById() : Book
    fun getBooks() : MutableList<Book>
    fun getBookByPage() : MutableList<Book>
    fun getBookClassify() : MutableList<Any>
    fun getBookChapterById() : BookChapter
    fun getBookChapters() : MutableList<BookChapter>
    fun updateBook()
    fun updateBooks()
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