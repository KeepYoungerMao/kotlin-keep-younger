package com.mao.config

/**
 * 无参构造注解
 * kotlin编译器插件的使用
 * data class类型对数据类的写法简便了很多，但是该类是一个final类
 * 且只有一个全参的构造函数
 * 使用该注解（使用该插件）可以在数据类编译后为该类生成一个无参构造。
 * 虽然这样的无参构造不能在代码中使用，但是可以通过反射构造。
 * 举例：
 * 在 mybatis 查询数据，使用data class类封装的时候，没有无参构造会出现一些问题；
 * 在没有使用无参构造插件时，data class类需要与数据库中的表字段的顺序一致，否则会出现映射错误
 * 如：
 * 数据库表中字段：    id[Int] name[String] auth[String] type[Int]
 * 代码数据封装类：    id[Int] type[Int]    auth[String] name[String]
 * mybatis映射的时候，name字段会映射type字段，数据类型不一致，报映射错误
 * 因此没有使用该插件时，需要与数据库表中字段顺序一致
 * 但是还有一个问题：
 * 当封装类中存在表中没有的字段时，也不能完成映射，会报outOfIndex
 * 原因也很简单：只存在一个全参数构造器，数据库表中没有这么多字段，因此无法构造
 *
 * 使用该插件后，将该注解注解于封装类上，mybatis便会使用无参构造函数。再对字段逐个映射。
 * 这种情况下，封装类中可以存在其他参数（表中没有的字段）。
 *
 * 配置详情参见 pom.xml
 * 使用详情参见 Data.kt
 */
annotation class NoArg

/**
 * 可继承注解
 * kotlin编译器插件的使用
 * kotlin中的class不能被继承，配置该注解可被其他类继承
 * 配置详情参见 pom.xml
 * 使用详情参见 Data.kt
 */
annotation class AllOpen

//========================== 数据检验注解 ========================

/**
 * 是否为数字检验
 * 用于判断字符串类型字段是否可以转化为数字
 * NeedNumber按执行逻辑已经包含NeedNotNull
 */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class NeedNumber

/**
 * 是否不为null检验
 * 用于检验字符串字段不允许为空
 */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class NeedNotNull

/**
 * 用于VARCHAR字段检验
 * 需自己设定最大长度
 * 默认该类型的检验：在字段为null时不做处理
 */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class NeedRangeLength(val max: Int)

/**
 * 用于text字段检验
 * mysql中text字段最大支持<64KB数据
 * 此处默认存储32KB数据
 * 默认该类型的检验：在字段为null时不做处理
 */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class NeedRangeText(val max: Int = 32)