package com.mao.entity

import com.mao.config.NoArg

/**
 * 日志数据
 * 主要记录用户在请求api时的请求行为数据
 * 谁请求的、请求了什么、
 */
@NoArg
data class Log(var id: String)