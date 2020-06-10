package com.mao.service.his

import com.mao.entity.ResponseData
import javax.servlet.http.HttpServletRequest

interface HisService {

    /**
     * 根据ip地址查询该ip所在的位置数据
     * 包括经纬度、所在地
     * 通过百度提供API查询
     * ip地址必须为外网地址
     */
    fun addressIp(request: HttpServletRequest) : ResponseData<*>

    /**
     * 根据城市查询该城市最近天气信息数据
     * 该数据为简略信息，包括昨日天气数据以及未来5天的天气数据
     * city需为市名称，其他名称查询失败
     * 通过中国气象局API查询
     */
    fun weatherCity(request: HttpServletRequest) : ResponseData<*>

    /**
     * 数独解析
     * 根据输入的数独数据，计算出可能的数独结果
     * 输入数独格式需为9*9的二维数组格式数据
     * 未作答的位置使用0填充
     * 返回的数据为可以作为答案的已经将0替换为正确数字的二位数组的集合
     * 该集合最多返回5个
     */
    fun sudoKu(request: HttpServletRequest) : ResponseData<*>

}