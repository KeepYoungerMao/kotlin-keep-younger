package com.mao.config

import org.apache.http.client.config.RequestConfig
import org.apache.http.impl.DefaultConnectionReuseStrategy
import org.apache.http.impl.client.*
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

/**
 * http client 配置
 * 配置属性参见：classpath:http_client.properties
 * 问题发现：
 * 使用原生HTTP请求时，遇见 http_encoding=gzip 压缩数据时，需要使用GZIPInputStream进行处理
 * 使用HttpClient后，会自动解压，无需自己处理
 */
@Component
@PropertySource("classpath:http_client.properties")
class HttpClientConfig {

    @Value("\${http.client.max_total}")
    private var maxTotal: Int = 100
    @Value("\${http.client.default_max_per_route}")
    private var defaultMaxPerRoute: Int = 10
    @Value("\${http.client.validate_after_inactivity}")
    private var validateAfterInactivity: Int = 5000
    @Value("\${http.client.connection_request_timeout}")
    private var connectionRequestTimeout: Int = 1000
    @Value("\${http.client.connect_time_out}")
    private var connectTimeOut: Int = 1000
    @Value("\${http.client.socket_timeout}")
    private var socketTimeout: Int = 1000

    /**
     * PoolingHttpClientConnectionManager 连接池管理器
     *      maxTotal： 设置最大请求数
     *      defaultMaxPerRoute： 设置每个路由最大并发数
     *      validateAfterInactivity： 连接不活跃时设置多长时间验证一次
     * RequestConfig    连接请求参数
     *      connectionRequestTimeout： 从连接池中获取请求的等待时间
     *      connectTimeOut： 连接超时设置
     *      socketTimeout：  读取数据超时设置
     * HttpClientBuilder    客户端创建参数
     *      setConnectionManagerShared： 设置连接池为非共享模式
     *      evictIdleConnections    定期回收空闲连接：此处设置每60秒检查回收
     *      evictExpiredConnections 回收过期连接
     *      setConnectionTimeToLive 设置请求存活时间：此处设置最长60秒
     *      setConnectionReuseStrategy  连接重用策略：是否能KEEP_ALIVE
     *      setKeepAliveStrategy    长连接设置：获取长连接多长时间
     *      setRetryHandler     设置重试策略：此处设置禁止重试
     */
    @Bean
    fun httpClient() : CloseableHttpClient {
        val manager = PoolingHttpClientConnectionManager()
        manager.maxTotal = this.maxTotal
        manager.defaultMaxPerRoute = this.defaultMaxPerRoute
        manager.validateAfterInactivity = this.validateAfterInactivity
        val requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .setConnectTimeout(connectTimeOut)
                .setSocketTimeout(socketTimeout)
                .build()
        return HttpClientBuilder.create()
                .setConnectionManager(manager)
                .setConnectionManagerShared(false)
                .evictIdleConnections(60,TimeUnit.SECONDS)
                .evictExpiredConnections()
                .setConnectionTimeToLive(60,TimeUnit.SECONDS)
                .setDefaultRequestConfig(requestConfig)
                .setConnectionReuseStrategy(DefaultConnectionReuseStrategy.INSTANCE)
                .setKeepAliveStrategy(DefaultConnectionKeepAliveStrategy.INSTANCE)
                .setRetryHandler(DefaultHttpRequestRetryHandler(0,false))
                .build()
    }

}
