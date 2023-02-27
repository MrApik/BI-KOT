package fit.bikot.heap

fun main() {
    val pq = PriorityQueue(1.0, 333.0, 2.0, 10.0, 8.0, 4.0, 4.0)

    println(pq)

    for (e in pq) println(e)

    pq.add(1.1)
    pq.add(1111.1)

    println(pq)

    while (!pq.isEmpty()) {
        println(pq.poll())
        println(pq)
    }

}