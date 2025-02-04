package lt.shgg.weblab4.utils

class Calculator {
    companion object {
        fun calcHit(x: Double?, y: Double?, r: Double?): Boolean {
            if (x == null || y == null || r == null) throw IllegalArgumentException("че то хуита")
            return if ((x >= 0.0) and (y >= 0.0)) x * x + y * y <= r * r / 4       // 1-я четверть
                else if ((x >= 0.0) and (y < 0.0)) y > x - r / 2                // 4-я четверть
                else if ((x < 0.0) and (y <= 0.0)) (y > -r / 2) and (x > -r)    // 3-я четверть
                else false                                                      // 2-я четверть
        }
    }
}