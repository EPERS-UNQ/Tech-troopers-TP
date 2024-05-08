package ar.edu.unq.eperdemic.modelo.RandomGenerator

import java.util.Random

interface RandomStrategy {

    val random: Random
        get() = Random()

    fun getNumeroRandom(num: Int = 0): Int
    fun <T> getElementoRandomEnLista(list: List<T>, num: Int = 0): T
    fun porcentajeExitoso(porcentaje: Int, bool: Boolean = true): Boolean
    fun porcentajeAltExitoso(porcentaje: Int, booleanGlobal: Boolean): Boolean

}