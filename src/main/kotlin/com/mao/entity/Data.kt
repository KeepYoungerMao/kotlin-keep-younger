package com.mao.entity

import com.mao.config.AllOpen
import com.mao.config.NoArg

@AllOpen class Operation(var update: Long?, var delete: Boolean?)

@AllOpen class Page(var page: Int?, var row: Int?)

data class Book(var id: String,
                var name: String?,
                var auth: String?,
                var s_image: String?,
                var image: String?,
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
                var intro: String?,
                var chapters: List<BookChapter>?,
                override var update: Long?,
                override var delete: Boolean?): Operation(update, delete)

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
                     override var page: Int?,
                     override var row: Int?): Page(page, row)

data class Buddhist(var id: String,
                    var name: String?,
                    var auth: String?,
                    var image: String?,
                    var type: String?,
                    var type_id: Int?,
                    var intro: String?,
                    var chapters: List<BuddhistChapter>?,
                    override var update: Long?,
                    override var delete: Boolean?): Operation(update, delete)

data class BuddhistChapter(var id: String,
                           var pid: String,
                           var order: Int?,
                           var title: String?,
                           var src: String?)

data class BuddhistParam(var name: String?,
                         var auth: String?,
                         var type: String?,
                         override var page: Int?,
                         override var row: Int?): Page(page, row)

data class Bjx(var id: String,
               var name: String?,
               var py: String?,
               var src: String?,
               override var update: Long?,
               override var delete: Boolean?): Operation(update, delete)

data class BjxParam(var name: String?,
                    var py: String?,
                    override var page: Int?,
                    override var row: Int?): Page(page, row)

data class Live(var id: String,
                var name: String?,
                var url: String?,
                var type: Int?,
                var kind: String?,
                var image: String?,
                var tips: String?,
                var useful: Boolean?)

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
                 override var delete: Boolean?): Operation(update, delete)

data class MovieM3u8(var type: String?, var url: String?)

data class MovieParam(var name: String?,
                      var actor: String?,
                      var type: String?,
                      var type_id: Int?,
                      var time: String?,
                      var place: String?,
                      var place_id: Int?,
                      override var page: Int?,
                      override var row: Int?): Page(page, row)

enum class MoviePlaceEnum(var id: Int, var place: String){
    y(3002,"英国"),
    h(2002,"韩国"),
    r(2003,"日本"),
    z(1001,"中国大陆"),
    m(2001,"美国"),
    x(1002,"中国香港"),
    q(9999,"其他"),
    j(3003,"加拿大"),
    e(2004,"俄罗斯"),
    t(3004,"泰国"),
    w(1003,"中国台湾"),
    d(3001,"印度"),
    p(4005,"新加坡"),
    f(2005,"法国"),
    b(3005,"西班牙"),
    a(4001,"澳大利亚"),
    l(4002,"马来西亚"),
    c(4003,"爱尔兰"),
    i(4004,"荷兰"),
    k(3006,"意大利"),
    n(3007,"葡萄牙"),
    g(2006,"德国")
}

enum class MovieTypeEnum(var id: Int, var type: String){
    a(1,"剧情片"),
    b(2,"恐怖片"),
    c(3,"爱情片"),
    d(4,"动作片"),
    e(5,"喜剧片"),
    f(6,"战争片"),
    g(7,"纪录片"),
    h(8,"科幻片")
}

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
               override var delete: Boolean?): Operation(update, delete)

data class PicClass(var id: Long, var pid: Long, var name: String)

data class PicClassVo(var id: Long, var name: String, var child: List<PicClassVo>)

data class PicParam(var name: String?, var pid: Long?, var sid: Long?)

data class PageData<T>(var total: Long, var current: Long, var param: Page, var data: T)