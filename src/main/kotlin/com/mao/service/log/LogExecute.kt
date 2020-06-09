package com.mao.service.log

import org.springframework.stereotype.Service

interface LogService {
    fun saveLog()
}

@Service
class DefaultLogService : LogService {
    override fun saveLog() {

    }
}