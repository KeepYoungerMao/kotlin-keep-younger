package com.mao.config

import com.mao.entity.Server
import com.mao.util.SnowFlake
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * id生成器
 */
@Component
class IdBuilder {

    @Autowired private lateinit var server: Server

    private val snowFlake: SnowFlake by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        SnowFlake(server.dataCenter,server.machine)
    }

    fun nextId() : Long {
        return snowFlake.nextId()
    }

}
