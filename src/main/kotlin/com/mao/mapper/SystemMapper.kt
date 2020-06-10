package com.mao.mapper

import com.mao.entity.Log
import org.apache.ibatis.annotations.Mapper
import org.springframework.stereotype.Repository

@Repository
@Mapper
interface LogMapper {

    fun saveLog(log: Log)

}