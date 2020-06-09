package com.mao.entity


object Response {

    fun <T> ok(data: T?) : ResponseData<T> = o(ResponseEnum.SUCCESS,data)

    fun permission(message: String?) : ResponseData<String> = o(ResponseEnum.PERMISSION,message)

    fun notFound(message: String?) : ResponseData<String> = o(ResponseEnum.NOTFOUND,message)

    fun notAllowed(message: String?) : ResponseData<String> = o(ResponseEnum.NOT_ALLOWED,message)

    fun error(message: String?) : ResponseData<String> = o(ResponseEnum.ERROR,message)

    private fun <T> o(type: ResponseEnum, data: T?) : ResponseData<T> = ResponseData(type.code,type.msg,data)

}

data class ResponseData<T>(var code: Int, var msg: String, var data: T?)

enum class ResponseEnum(val code: Int, val msg: String){
    SUCCESS(200,"ok"),
    PERMISSION(401,"no permission"),
    NOTFOUND(404, "not found"),
    NOT_ALLOWED(405,"not allowed"),
    ERROR(500,"server error")
}