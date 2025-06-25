package lt.shgg.weblab4.mbeans

import org.springframework.jmx.export.annotation.*
import org.springframework.stereotype.Component
import java.io.Serializable
import java.util.concurrent.atomic.AtomicInteger
import javax.management.*


@Component
@ManagedResource("lt.shgg.weblab4.services:type=Counter")
class Counter : CounterMBean, Serializable, NotificationEmitter, NotificationListener {
    @get:ManagedAttribute
    override val totalAttempts = AtomicInteger(0)

    @get:ManagedAttribute
    override val totalHits = AtomicInteger(0)

    private val missStreak = AtomicInteger(0)
    private val broadcaster = NotificationBroadcasterSupport()

    override fun getNotificationInfo(): Array<MBeanNotificationInfo?> {
        return arrayOfNulls(0)
    }

    @ManagedOperation
    fun updateAttempt(hit: Boolean) {
        totalAttempts.incrementAndGet()
        if (hit) {
            totalHits.incrementAndGet()
            missStreak.set(0)
        } else {
            missStreak.incrementAndGet()
        }

        checkMissStreak()
    }

    override fun checkMissStreak() {
        if (missStreak.get() == 3) {
            broadcaster.sendNotification(Notification(
                "misses.streak",
                this,
                System.currentTimeMillis(),
                "damn, 3 misses in a row"
            ))

            missStreak.set(0)
        }
    }

    @Throws(ListenerNotFoundException::class)
    override fun removeNotificationListener(
        listener: NotificationListener?,
        filter: NotificationFilter?,
        handback: Any?
    ) {
        broadcaster.removeNotificationListener(listener, filter, handback)
    }

    override fun handleNotification(notification: Notification?, handback: Any?) {
        println(notification)
    }

    override fun addNotificationListener(listener: NotificationListener?, filter: NotificationFilter?, handback: Any?) {
        broadcaster.addNotificationListener(listener, filter, handback)
    }

    @Throws(ListenerNotFoundException::class)
    override fun removeNotificationListener(listener: NotificationListener?) {
        broadcaster.removeNotificationListener(listener)
    }
}