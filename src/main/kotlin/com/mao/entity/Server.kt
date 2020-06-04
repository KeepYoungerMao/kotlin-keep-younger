package com.mao.entity

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component

@Component
@PropertySource("classpath:/server.properties")
class Server {

    @Value("\${server.name}")
    lateinit var name: String
    @Value("\${server.link}")
    private lateinit var link: String
    @Value("\${server.version}")
    private lateinit var version: String
    @Value("\${server.description}")
    private lateinit var description: String
    @Value("\${server.ip}")
    private lateinit var ip: String
    @Value("\${server.port}")
    private lateinit var port: String

}