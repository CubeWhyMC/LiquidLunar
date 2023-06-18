package org.cubewhy.lunarcn.utils

class MSTimer {
    var time = -1L

    fun hasTimePassed(ms: Long): Boolean {
        return System.currentTimeMillis() >= time + ms
    }

    fun hasTimeLeft(ms: Long): Long {
        return ms + time - System.currentTimeMillis()
    }

    fun timePassed(): Long {
        return System.currentTimeMillis() - time
    }

    fun reset() {
        time = System.currentTimeMillis()
    }
}
