package com.mao

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * 关键字解析：
 * internal     修饰方法上，修饰后的方法只适用于该模块，别的模块不能调用
 * expect       公共模块中的声明，可用在类上或方法上，方法或该类的方法只提供声明，不提供实现
 * actual       JVM模块提供实现声明和相应的实现
 *
 *              expect class Demo {
 *                  fun pf()
 *              }
 *
 *              actual class Demo {
 *                  actual fun pf() {
 *                      println("hello world")
 *                  }
 *              }
 *
 * typealias    用于在实现声明中，JVM模块中有实现类的，可直接使用该关键字对其实现
 *
 *              expect class DemoList<E> {
 *                  fun size() : Int
 *                  fun add(E e) : E
 *              }
 *              //这样使用DemoList时便是使用了ArrayList的实现
 *              actual typealias DemoList<E> = java.util.ArrayList<E>
 *
 * inline       内联函数，用于方法上，调用方法是一个压栈和出栈的过程：将栈帧压入方法栈、执行方法体、方法结束时将栈帧出栈
 *              压栈出栈会耗费资源，inline关键字可用于使用频繁的简单的方法上。
 *              这种方法的定义：
 *              1. 经常被调用
 *              2. 形参不会再传递到其它的方法上（否则其它方法执行时会报错）
 *              使用inline关键字后，编译后，会将调用该方法的地方，将调用方法的地方全部换成该方法的内容
 *              这样虽然增加了代码量，但是提升了性能
 *
 *              fun demo() {
 *                  val a = "hello world"
 *                  pf(a)
 *              }
 *              inline fun pf(a: String) {
 *                  println(a)
 *              }
 *
 *              ------------- 编译后 -------------
 *
 *              fun demo() {
 *                  val a =  "hello world"
 *                  println(a)
 *              }
 *
 * in           逆变：如果你的类是将泛型作为函数的参数，那么可用in
 *              相当于java中的 <? super E>
 *              super：指定了泛型的下线，此时E为E类或者E的父类，? 则只能是 E或E的子类
 * out          协变：如果你的类是将泛型作为内部方法的返回值，那么可用out
 *              相当于java中的 <? extend E>
 *              extend：指定了泛型的上线，此时E为E类或者E的子类，?则只能是E或E的子类
 *
 */
@SpringBootApplication
class KotlinKeepYoungerApplication

fun main(args: Array<String>) {
    runApplication<KotlinKeepYoungerApplication>(*args)
}
