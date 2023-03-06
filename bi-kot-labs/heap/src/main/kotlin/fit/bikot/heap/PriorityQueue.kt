package fit.bikot.heap

import java.lang.RuntimeException
import java.util.*

class PriorityQueue(vararg element: Double, var maxSize: Int = 1000) : Queue<Double> {

    private val dataBuffer = Array<Double>(Math.max(element.size, maxSize)) { ind ->
        if (ind < element.size) element[ind] else 0.0
    }

    private fun data(offset: Int) = dataBuffer.copyOfRange(offset, size)

    init {
        buildHeap(dataBuffer, lastInd)
    }

    private val lastInd: Int
        get() = size - 1

    private var _size = element.size

    override fun add(element: Double): Boolean {
        if (isFull()) throw RuntimeException("is full")
        dataBuffer[size] = element
        _size++

        var topInd = lastInd
        do {
            topInd = parentInd(topInd)
            repairTop(topInd, dataBuffer, lastInd)
        } while (topInd > 0)
        return true
    }

    override fun toString() = data(0).joinToString(prefix = "[", postfix = "]")

    override fun addAll(elements: Collection<Double>): Boolean {
        TODO("Not yet implemented")
    }

    override fun clear() {
        dataBuffer.fill(0.0)
        _size = 0
    }

    override fun iterator(): MutableIterator<Double> =
        object : MutableIterator<Double> {
            var curr = 0

            override fun hasNext(): Boolean = curr < lastInd

            override fun next(): Double = dataBuffer[++curr]

            override fun remove() {
                TODO("Not yet implemented")
            }
        }


    override fun remove(): Double {
        if (isEmpty()) throw NoSuchElementException()
        val rm = dataBuffer[0]
        removeOnInd(0)
        return rm
    }

    override fun retainAll(elements: Collection<Double>): Boolean {
        TODO("Not yet implemented")
    }

    override fun removeAll(elements: Collection<Double>): Boolean {
        TODO("Not yet implemented")
    }

    override fun remove(element: Double): Boolean {
        val elemInd = data(0).indexOf(element)
        return removeOnInd(elemInd)
    }

    private fun removeOnInd(elemInd: Int): Boolean {
        if (elemInd == -1) return false
        dataBuffer[elemInd] = dataBuffer[lastInd]
        _size--
        repairTop(elemInd, dataBuffer, lastInd)
        return true
    }

    fun isFull(): Boolean = size >= maxSize

    override fun isEmpty(): Boolean = size == 0


    override fun poll(): Double? {
        return try {
            remove()
        } catch (e: NoSuchElementException) {
            null
        }
    }

    override fun element(): Double {
        if (isEmpty()) throw NoSuchElementException()
        return dataBuffer[0]
    }

    override fun peek(): Double? =
        try {
            element()
        } catch (e: NoSuchElementException) {
            null
        }


    override fun offer(p0: Double): Boolean {
        TODO("Not yet implemented")
    }

    override fun containsAll(elements: Collection<Double>): Boolean {
        for (e in elements)
            if (!contains(e)) return false
        return true
    }

    override fun contains(element: Double): Boolean = element in data(0)

    override val size: Int
        get() = _size

    companion object {
        fun leftChildInd(parentInd: Int) = parentInd * 2 + 1

        fun parentInd(childInt: Int) = (childInt - 1) / 2
        fun swap(i: Int, j: Int, dataBuffer: Array<Double>) {
            val tmp = dataBuffer[i]
            dataBuffer[i] = dataBuffer[j]
            dataBuffer[j] = tmp
        }

        fun repairTop(parentInd: Int, dataBuffer: Array<Double>, lastInd: Int) {
            if (parentInd == lastInd) return
            val leftChildInd = leftChildInd(parentInd)
            if (leftChildInd > lastInd) return

            val rightChildInd = leftChildInd + 1

            val maxChildInd = if (rightChildInd > lastInd)
                leftChildInd
            else if (dataBuffer[leftChildInd] >= dataBuffer[rightChildInd])
                leftChildInd
            else
                rightChildInd

            if (dataBuffer[parentInd] < dataBuffer[maxChildInd]) {
                swap(parentInd, maxChildInd, dataBuffer)
                repairTop(maxChildInd, dataBuffer, lastInd)
            }
        }

        fun buildHeap(dataBuffer: Array<Double>, lastInd: Int) {
            for (topInd in lastInd downTo 0)
                repairTop(topInd, dataBuffer, lastInd)
        }

    }

}