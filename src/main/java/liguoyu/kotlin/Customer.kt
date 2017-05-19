package liguoyu.kotlin

/**
 * Created by zixiao@wacai.com on 2017/5/19.
 */
class Customer{

    var name : String? = null
    var id : String? = null

    constructor()

    override fun toString(): String {
        return "Customer(name=$name, id=$id)"
    }


}

fun main(args: Array<String>) {
    val customer:Customer = Customer()
    customer.name = "customerName"
    customer.id = "customerid"
    print(customer)
}
