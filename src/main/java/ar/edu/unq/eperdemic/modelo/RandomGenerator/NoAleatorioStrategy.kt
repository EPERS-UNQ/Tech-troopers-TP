package ar.edu.unq.eperdemic.modelo.RandomGenerator

class NoAleatorioStrategy : RandomStrategy {

    private var numeroGlobal = 0
    override fun getNumeroRandom(): Int {
        if (numeroGlobal in 1..10) {
            return numeroGlobal
        }
        throw IllegalArgumentException("El número debe estar entre 1 y 10.")
    }

    override fun <T> getElementoRandomEnLista(list: List<T>, num: Int): T {
        return list[num]
    }

    override fun porcentajeExistoso(porcentaje: Int, bool: Boolean): Boolean {
        return bool
    }

    fun setNumeroGlobal(newNumero: Int) {
        numeroGlobal = newNumero
    }

}