package ar.edu.unq.eperdemic.modelo.vector

enum class TipoVector {
    HUMANO,
    ANIMAL,
    INSECTO;

    fun puedeContagiarA(tipo: TipoVector): Boolean {
        return when (this) {
            HUMANO -> tipo == HUMANO || tipo == INSECTO
            ANIMAL -> tipo == HUMANO || tipo == INSECTO
            INSECTO -> tipo == HUMANO || tipo == ANIMAL
        }
    }
}
