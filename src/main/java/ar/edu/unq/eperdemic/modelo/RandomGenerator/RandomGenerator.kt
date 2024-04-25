package ar.edu.unq.eperdemic.modelo.RandomGenerator

class RandomGenerator private constructor() {

    private var strategy: RandomStrategy = AleatorioStrategy()
    private var numeroGlobal = 1
    private var booleanGlobal = true

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

    fun setNumeroGlobal(newNumero: Int) {
        this.numeroGlobal = newNumero
    }

    fun setBooleanoGlobal(bool: Boolean) {
        this.booleanGlobal = bool
    }

    fun getNumeroRandom(): Int {
        return strategy.getNumeroRandom(numeroGlobal)
    }

    fun <T> getElementoRandomEnLista(list: List<T>): T {
        return strategy.getElementoRandomEnLista(list, numeroGlobal)
    }

    fun porcentajeExistoso(porcentaje: Int) : Boolean {
        return strategy.porcentajeExistoso(porcentaje, booleanGlobal)
    }

}
