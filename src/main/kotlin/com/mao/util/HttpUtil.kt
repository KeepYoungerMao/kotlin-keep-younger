package com.mao.util

import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.CloseableHttpClient
import java.io.*

object HttpUtil {

    fun get(url: String, httpClient: CloseableHttpClient) : String? {
        val sb = StringBuilder()
        try {
            val response = httpClient.execute(HttpGet(url))
            val reader = BufferedReader(InputStreamReader(response.entity.content))
            reader.forEachLine { sb.append(it) }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return sb.toString()
    }

}