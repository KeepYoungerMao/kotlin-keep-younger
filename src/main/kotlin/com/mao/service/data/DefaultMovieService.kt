package com.mao.service.data

import com.mao.entity.Response
import com.mao.entity.ResponseData
import com.mao.mapper.MovieMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest

/**
 * 电影数据处理
 * @author create by mao at 2020/06/05 16:48:01
 */
@Service
class DefaultMovieService : MovieService {

    @Autowired private lateinit var movieMapper: MovieMapper

    override fun getMovieById(request: HttpServletRequest): ResponseData<*> {
        return Response.ok(movieMapper.getMovieById())
    }

    override fun getMovies(request: HttpServletRequest): ResponseData<*> {
        return Response.ok(movieMapper.getMovies())
    }

    override fun getMovieByPage(request: HttpServletRequest): ResponseData<*> {
        return Response.ok(movieMapper.getMovieByPage())
    }

    override fun getMovieClassify(request: HttpServletRequest): ResponseData<*> {
        return Response.ok(movieMapper.getMovieClassify())
    }

    override fun updateMovie(request: HttpServletRequest): ResponseData<*> {
        movieMapper.updateMovie()
        return Response.ok("SUCCESS")
    }

    override fun updateMovies(request: HttpServletRequest): ResponseData<*> {
        movieMapper.updateMovies()
        return Response.ok("SUCCESS")
    }

    override fun saveMovie(request: HttpServletRequest): ResponseData<*> {
        movieMapper.saveMovie()
        return Response.ok("SUCCESS")
    }

    override fun saveMovies(request: HttpServletRequest): ResponseData<*> {
        movieMapper.saveMovies()
        return Response.ok("SUCCESS")
    }

    override fun deleteMovie(request: HttpServletRequest): ResponseData<*> {
        movieMapper.deleteMovie()
        return Response.ok("SUCCESS")
    }

}