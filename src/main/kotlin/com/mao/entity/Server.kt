package com.mao.entity

import com.mao.config.NoArg
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component

@NoArg
@Component
@PropertySource("classpath:/server.properties")
@ConfigurationProperties(prefix = "server")
data class Server(var name: String, var link: String, var version: String, var description: String, var ip: String, var port: String)