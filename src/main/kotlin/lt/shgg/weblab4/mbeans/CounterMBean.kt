package lt.shgg.weblab4.mbeans

import java.util.concurrent.atomic.AtomicInteger

interface CounterMBean {
    val totalAttempts: AtomicInteger
    val totalHits: AtomicInteger
    fun checkMissStreak()
}