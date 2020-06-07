package com.mao.entity

import com.mao.config.AllOpen
import com.mao.config.NoArg

@NoArg @AllOpen class Operation(var update: Long?, var delete: Boolean)

@AllOpen class Page(var page: Int, var row: Int) {
    fun correct() {
        if (page <= 0) page = 1
        if (row <= 0) row = 20
    }
}

@NoArg
data class Book(var id: String,
                var name: String?,
                var auth: String?,
                var image: String?,
                var s_image: String?,
                var intro: String?,
                var guide: String?,
                var guide_auth: String?,
                var score: String?,
                var type: String?,
                var type_id: Int?,
                var dynasty: String?,
                var dynasty_id: Int?,
                var count: Int?,
                var free: Boolean?,
                var off_sale: Boolean?,
                override var update: Long?,
                override var delete: Boolean): Operation(update, delete)

@NoArg
data class BookChapter(var id: String,
                       var order: Int?,
                       var name: String?,
                       var book_id: String,
                       var content: String?)

data class BookParam(var name: String?,
                     var auth: String?,
                     var type: Int?,
                     var dynasty: Int?,
                     var free: Boolean?,
                     var off_sale: Boolean?,
                     override var page: Int,
                     override var row: Int): Page(page, row) {
    companion object {
        fun default() : BookParam {
            return BookParam(null,null,0,0,null,null,1,20)
        }
    }
}

@NoArg
data class Buddhist(var id: String,
                    var name: String?,
                    var auth: String?,
                    var image: String?,
                    var type: String?,
                    var type_id: Int?,
                    var intro: String?,
                    var chapters: List<BuddhistChapter>?,
                    override var update: Long?,
                    override var delete: Boolean): Operation(update, delete)

@NoArg
data class BuddhistChapter(var id: String,
                           var pid: String,
                           var order: Int?,
                           var title: String?,
                           var src: String?)

data class BuddhistParam(var name: String?,
                         var auth: String?,
                         var type: String?,
                         override var page: Int,
                         override var row: Int): Page(page, row)

@NoArg
data class Bjx(var id: String,
               var name: String?,
               var py: String?,
               var src: String?,
               override var update: Long?,
               override var delete: Boolean): Operation(update, delete)

data class BjxParam(var name: String?,
                    var py: String?,
                    override var page: Int,
                    override var row: Int): Page(page, row)

@NoArg
data class Live(var id: String,
                var name: String?,
                var url: String?,
                var type: Int?,
                var kind: String?,
                var image: String?,
                var tips: String?,
                var useful: Boolean?)

@NoArg
data class Movie(var id: String,
                 var name: String?,
                 var image: String?,
                 var actor: String?,
                 var type: String?,
                 var type_id: Int?,
                 var time: String?,
                 var place: String?,
                 var place_id: Int?,
                 var weight: String?,
                 var intro: String?,
                 var m3u8: String?,
                 var urls: List<MovieM3u8>,
                 override var update: Long?,
                 override var delete: Boolean): Operation(update, delete)

@NoArg
data class MovieM3u8(var type: String?, var url: String?)

data class MovieParam(var name: String?,
                      var actor: String?,
                      var type: String?,
                      var type_id: Int?,
                      var time: String?,
                      var place: String?,
                      var place_id: Int?,
                      override var page: Int,
                      override var row: Int): Page(page, row)

@NoArg
data class Pic(var id: String,
               var name: String?,
               var prl: Int,
               var psl: Int,
               var pid: Long,
               var sid: Long,
               var s_image: String?,
               var image: String?,
               var key: String?,
               override var update: Long?,
               override var delete: Boolean): Operation(update, delete)

data class PicParam(var name: String?, var pid: Long?, var sid: Long?)

/**
 * 分页数据包装类
 * @param total 总页数
 * @param current 当前页数
 * @param param 请求参数
 * @param data 数据列表
 */
data class PageData<T>(var total: Int, var current: Int, var param: Page, var data: MutableList<T>)

/**
 * 数据类型字典
 * 数据的分类字段
 */
@NoArg
data class DataDict(var id: Int,
                    var data: String,
                    var type: String,
                    var type_id: Int,
                    var type_pid: Int,
                    var type_name: String,
                    var enabled: Boolean,
                    override var update: Long?,
                    override var delete: Boolean): Operation(update, delete)