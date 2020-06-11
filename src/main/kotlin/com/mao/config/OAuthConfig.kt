package com.mao.config

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.mao.entity.Response
import com.mao.service.auth.JdbcUserDetailsService
import com.mao.service.log.LogService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfigurer: WebSecurityConfigurerAdapter() {

    companion object {
        const val API_PRE = "/api"
    }

    @Autowired
    private lateinit var jdbcUserDetailsService: JdbcUserDetailsService

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    override fun configure(http: HttpSecurity) {
        http.httpBasic().disable().csrf().disable()
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(jdbcUserDetailsService).passwordEncoder(passwordEncoder())
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

}

@Configuration
@EnableAuthorizationServer
class AuthorizationServerConfigurer: AuthorizationServerConfigurerAdapter() {

    @Autowired
    private lateinit var redisConnectionFactory: RedisConnectionFactory
    @Autowired
    private lateinit var authenticationManager: AuthenticationManager
    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Bean
    fun tokenStore(): TokenStore {
        return RedisTokenStore(redisConnectionFactory)
    }

    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients.inMemory()
                .withClient("system")
                .secret(passwordEncoder.encode("system"))
                .scopes("read","write","all")
                .authorizedGrantTypes("password","authorization_code","refresh_token")
                .autoApprove(true)
                .authorities("system")
                .accessTokenValiditySeconds(7200)
                .refreshTokenValiditySeconds(7200)
                .redirectUris("https://www.baidu.com")
    }

    override fun configure(security: AuthorizationServerSecurityConfigurer) {
        security.checkTokenAccess("isAuthenticated()")
                .tokenKeyAccess("permitAll()")
                .allowFormAuthenticationForClients()
    }

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        endpoints.tokenStore(tokenStore()).authenticationManager(authenticationManager)
    }

}

@Configuration
@EnableResourceServer
class ResourceServerConfigurer: ResourceServerConfigurerAdapter() {

    @Autowired private lateinit var defaultAccessDeniedHandler: DefaultAccessDeniedHandler

    override fun configure(http: HttpSecurity) {
        http.antMatcher("${WebSecurityConfigurer.API_PRE}/**")
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable()
    }

    override fun configure(resources: ResourceServerSecurityConfigurer) {
        resources.accessDeniedHandler(defaultAccessDeniedHandler)
                .authenticationEntryPoint(defaultAccessDeniedHandler)
    }

}

@Component
class DefaultAccessDeniedHandler : OAuth2AccessDeniedHandler(), AuthenticationEntryPoint, AuthenticationFailureHandler {

    @Autowired private lateinit var logService: LogService

    override fun handle(request: HttpServletRequest?, response: HttpServletResponse?, exception: AccessDeniedException?) {
        doHandler(request,response,exception)
    }

    override fun commence(request: HttpServletRequest?, response: HttpServletResponse?, authException: AuthenticationException?) {
        doHandler(request,response,authException)
    }

    override fun onAuthenticationFailure(request: HttpServletRequest?, response: HttpServletResponse?, authException: AuthenticationException?) {
        doHandler(request,response,authException)
    }

    private fun doHandler(request: HttpServletRequest?, response: HttpServletResponse?, e: RuntimeException?) {
        val status = 401
        val remark = e?.message?:"no permission"
        if (null!=request)
            logService.saveLog(request.userPrincipal?.name,request,status,remark)
        response?.status = 200
        response?.contentType = "application/json; charset=utf-8"
        response?.writer?.write(jacksonObjectMapper().writeValueAsString(Response.permission(remark)))
        response?.flushBuffer()
    }

}