package fit.bikot.hello

class Person(val name:String, val age:Int) {

    override fun toString(): String {
        return "Person(name=${name}, age=${age})"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return true
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}