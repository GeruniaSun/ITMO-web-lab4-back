package lt.shgg.weblab4.mbeans

import java.util.concurrent.atomic.AtomicInteger

interface RatioStatMBean {
    val totalAttempts: AtomicInteger
    val totalMisses: AtomicInteger
    val missesRatio: Double
}