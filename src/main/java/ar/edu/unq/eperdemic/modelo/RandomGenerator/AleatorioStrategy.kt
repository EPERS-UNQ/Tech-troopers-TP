package ar.edu.unq.eperdemic.modelo.RandomGenerator

class AleatorioStrategy : RandomStrategy {
    override fun getNumeroRandom(): Int {
        return (1..10).random()
    }

    override fun <T> getElementoRandomEnLista(list: List<T>, num: Int): T {
        if (list.isEmpty()) {
            throw IllegalArgumentException("La lista no puede estar vacía.")
        }
        val rIndex = random.nextInt(list.size)
        return list[rIndex]
    }

    override fun porcentajeExistoso(porcentaje: Int, bool: Boolean): Boolean {
        if (1 <= porcentaje && porcentaje <= 110) {
            return (porcentaje - random.nextInt(100)) > 0
        }
        throw IllegalArgumentException("El porcentaje debe estar entre 1 y 110.")
    }


}