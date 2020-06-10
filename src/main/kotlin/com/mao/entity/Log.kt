package com.mao.entity

import com.mao.config.NoArg

/**
 * 日志数据
 * 主要记录用户在请求api时的请求行为数据
 * 谁请求的、请求了什么、从哪里请求、请求是否通过
 * @param id 主键
 * @param user 操作用户
 * @param uri 请求地址
 * @param ip 操作用户ip
 * @param operation 操作类型：对应OperationType
 * @param resource 资源类型：对应ResourceType
 * @param data 数据类型：对应DataType和HisType
 * @param method 数据处理方式类型：对应DataMethod和HisMethod
 * @param status 返回状态
 * @param remark 错误信息
 * @param date 操作时间戳
 */
@NoArg
data class Log(var id: String,
               var user: String?,
               var uri: String?,
               var ip:String,
               var operation: Int,
               var resource: Int,
               var data: Int,
               var method: Int,
               var status: Int,
               var remark: String?,
               var date: Long)