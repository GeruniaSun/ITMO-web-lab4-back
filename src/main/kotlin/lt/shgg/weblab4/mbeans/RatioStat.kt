package lt.shgg.weblab4.mbeans

import org.springframework.jmx.export.annotation.ManagedAttribute
import org.springframework.jmx.export.annotation.ManagedOperation
import org.springframework.jmx.export.annotation.ManagedResource
import org.springframework.stereotype.Service
import java.io.Serializable
import java.util.concurrent.atomic.AtomicInteger

@ManagedResource("lt.shgg.weblab4.services:type=RatioStat")
@Service
class RatioStat : RatioStatMBean, Serializable {
    @get:ManagedAttribute
    override val totalAttempts: AtomicInteger = AtomicInteger(0)

    @get:ManagedAttribute
    override val totalMisses: AtomicInteger = AtomicInteger(0)

    @get:ManagedAttribute
    override val missesRatio: Double
        get() = totalMisses.get().toDouble() / totalAttempts.get() * 100

    @ManagedOperation
    fun updateStat(hit: Boolean) {
        totalAttempts.incrementAndGet()
        if (!hit) {
            totalMisses.incrementAndGet()
        }
    }
}