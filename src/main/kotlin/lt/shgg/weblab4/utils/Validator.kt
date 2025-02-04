package lt.shgg.weblab4.utils

import lt.shgg.weblab4.models.Point

class Validator {    
    companion object {
        private const val X_MAX = 5.0
        private const val X_MIN = -5.0
        private const val Y_MAX = 5.0
        private const val Y_MIN = -5.0
        private const val R_MAX = 5.0
        private const val R_MIN = 0.0

        fun pointIsValid(p: Point): Boolean {
            return validateX(p.x) and validateY(p.y) and validateR(p.r)
        }

        private fun validateX(x: Double?): Boolean {
            return x != null && x in X_MIN..X_MAX
        }

        private fun validateY(y: Double?): Boolean {
            return y != null && y in Y_MIN..Y_MAX
        }

        private fun validateR(r: Double?): Boolean {
            return r != null && r in R_MIN..R_MAX
        }
    }
}