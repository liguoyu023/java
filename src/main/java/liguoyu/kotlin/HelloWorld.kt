package liguoyu.kotlin

/**
 * Created by zixiao@wacai.com on 2017/5/19.
 */
fun main(args: Array<String>) {
//    println("hello world")
//    var a = 1;
//    var b = 2;
//    println(sum(a,b))

//    if(args.size == 0)return
//    print("First argument:${args[0]}")
//    print(max(1,2))
//    condition(21321321132345656);

    var arr = arrayOf("a","b","c")
    testLambda(arr)
}


//fun sum(a:Int,b:Int):Int=a+b
//fun max(a:Int,b:Int)=if(a>b) a else b;

//fun condition(obj:Any){
//    when(obj){
//        1 -> print("one")
//        "hello" -> print("hello")
//        is Long -> print("Long")
//        !is String -> print("Not a String")
//        else -> print("Unknown")
//    }
//}

fun testLambda(str:Array<String>){

    str.filter { !it.startsWith("a") }
            .sortedBy { it }
            .map(String::toUpperCase)
            .forEach(::println)
}