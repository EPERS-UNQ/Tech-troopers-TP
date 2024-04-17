package ar.edu.unq.eperdemic.modelo

import java.util.Random
class RandomGenerator {
    private val random = Random()

    fun getNumberoRandom(): Int {
        return (1..10).random()
    }

    fun getNumeroEspecifico(num: Int): Int {
        if (num in 1..10) {
            return num
        }
        throw IllegalArgumentException("El número debe estar entre 1 y 10.")
    }

    fun <T> getElementoRandomEnLista(list: List<T>): T {
        if (list.isEmpty()) {
            throw IllegalArgumentException("La lista no puede estar vacía.")
        }
        val rIndex = random.nextInt(list.size)
        return list[rIndex]
    }

    fun porcentajeExistoso(porcentaje: Int): Boolean {
        if (1 <= porcentaje && porcentaje <= 100) {
            return (porcentaje - random.nextInt(100)) > 0
        }
        throw IllegalArgumentException("El porcentaje debe estar entre 1 y 100.")
    }
}