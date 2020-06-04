package com.mao.util

import java.io.*
import java.net.URL
import java.net.URLConnection
import java.util.zip.GZIPInputStream

object HttpUtil {

    private const val CHARSET = "UTF-8"

    fun get(url: String, params: Map<String, String>?) : String? {
        val sb = StringBuilder()
        try {
            val newUrl: String = makeUrl(url, params)
            val requestUrl = URL(newUrl)
            val connection: URLConnection = requestUrl.openConnection()
            setProperty(connection)
            val reader = BufferedReader(InputStreamReader(connection.getInputStream(), CHARSET))
            reader.forEachLine{ sb.append(it) }
            reader.close()
        } catch (e: IOException){
            e.printStackTrace()
        }
        return sb.toString()
    }

    fun getGZip(url: String, params: Map<String, String>?) : String? {
        val sb = StringBuilder()
        try {
            val newUrl: String = makeUrl(url, params)
            val connection: URLConnection = URL(newUrl).openConnection()
            setProperty(connection)
            val reader = BufferedReader(InputStreamReader(GZIPInputStream(connection.getInputStream())))
            reader.forEachLine{ sb.append(it) }
            reader.close()
        } catch (e: IOException){
            e.printStackTrace()
        }
        return sb.toString()
    }

    private fun setProperty(connection: URLConnection) {
        connection.setRequestProperty("accept", "*/*")
        connection.setRequestProperty("connection", "Keep-Alive")
        connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)")
    }

    private fun makeUrl(url: String, params: Map<String, String>?) : String {
        var start = false
        var res = url
        if (params != null) {
            for ((key: String, value: String) in params) {
                res += if (start) "&$key=$value" else "?$key=$value"
                start = !start
            }
        }
        return res
    }

}