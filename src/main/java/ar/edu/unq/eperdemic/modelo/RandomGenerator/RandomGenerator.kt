package ar.edu.unq.eperdemic.modelo.RandomGenerator

class RandomGenerator private constructor() {

    private var strategy: RandomStrategy = AleatorioStrategy()
    private var numeroGlobal  = 1
    private var booleanInfeccionGlobal = true
    private var booleanMutacionGlobal  = true


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
        this.booleanInfeccionGlobal = bool
    }

    fun setBooleanoAltGlobal(bool: Boolean) {
        this.booleanMutacionGlobal = bool
    }

    fun getNumeroRandom(): Int {
        return this.strategy.getNumeroRandom(numeroGlobal)
    }

    fun <T> getElementoRandomEnLista(list: List<T>): T {
        return this.strategy.getElementoRandomEnLista(list, numeroGlobal)
    }

    fun porcentajeExistoso(porcentaje: Int) : Boolean {
        return this.strategy.porcentajeExitoso(porcentaje, booleanInfeccionGlobal)
    }

    fun porcentajeAltExistoso(porcentaje: Int): Boolean {
        return this.strategy.porcentajeAltExitoso(porcentaje, booleanMutacionGlobal)
    }

}
