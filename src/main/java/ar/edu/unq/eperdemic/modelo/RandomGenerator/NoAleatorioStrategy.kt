package ar.edu.unq.eperdemic.modelo.RandomGenerator

class NoAleatorioStrategy : RandomStrategy {
    override fun getNumeroRandom(num: Int): Int {
        if (num in 1..10) {
            return num
        }
        throw IllegalArgumentException("El número debe estar entre 1 y 10.")
    }

    override fun <T> getElementoRandomEnLista(list: List<T>, num: Int): T {
        return list[num]
    }

    override fun porcentajeExistoso(porcentaje: Int, bool: Boolean): Boolean {
        return bool
    }

}