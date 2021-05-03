package com.ozgursakizli.starshipdelivery.utilities

import kotlin.math.pow
import kotlin.math.sqrt

object DistanceCalculator {

    fun calculateDistance(startX: Float, startY: Float, destinationX: Float, destinationY: Float): Int {
        val diffX = destinationX - startX
        val diffY = destinationY - startY
        val powSum = diffX.toDouble().pow(2.0) + diffY.toDouble().pow(2.0)
        val distance = sqrt(powSum)
        return distance.toInt()
    }

}