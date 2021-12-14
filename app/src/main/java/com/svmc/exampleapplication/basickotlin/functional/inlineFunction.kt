
object ilineClass {
    fun doSomething () {
        print("do some thing start")
        dosomethingelse {
            print("do some thing else")
//        if add crossinline it mean can't add return
//            return
        }
        print(" do something end")

    }

    inline fun dosomethingelse (crossinline abc: () -> Unit) {
        abc()
    }

    //->
//    fun doSomething() {
//        print("doSomething start")
//        print("doSomethingElse")
//        print("doSomething end")
//    }
}

