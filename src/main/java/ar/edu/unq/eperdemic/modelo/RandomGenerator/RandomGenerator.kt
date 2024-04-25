package ar.edu.unq.eperdemic.modelo.RandomGenerator

class RandomGenerator private constructor() {

    private var strategy: RandomStrategy = AleatorioStrategy()

    companion object {
        private var instance: RandomGenerator? = null

        fun getInstance(): RandomGenerator {
            if (instance == null) {
                instance = RandomGenerator()
            }
            return instance!!
        }
    }

    fun setStrategy(newStrategy: RandomStrategy) {
        this.strategy = newStrategy
    }

    fun getNumeroRandom(): Int {
        return strategy.getNumeroRandom()
    }

    fun <T> getElementoRandomEnLista(list: List<T>, num: Int = 0): T {
        return strategy.getElementoRandomEnLista(list, num)
    }

    fun porcentajeExistoso(porcentaje: Int, bool: Boolean = true): Boolean {
        return strategy.porcentajeExistoso(porcentaje, bool)
    }

}

// Ejemplo de uso:
/*
    val randomGen = RandomGenerator.getInstance()
    println(randomGen.getNumeroRandom())
    println(randomGen.getNumeroEspecifico(5))
    println(randomGen.getElementoRandomEnLista(listOf(1, 2, 3, 4, 5)))
    println(randomGen.porcentajeExistoso(80))
*/


