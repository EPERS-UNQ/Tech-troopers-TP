package ar.edu.unq.eperdemic.modelo

import ar.edu.unq.eperdemic.modelo.vector.TipoVector

enum class TipoDeCamino {

    TERRESTRE,
    MARITIMO,
    AEREO;

    fun puedeSerCruzadoPor(tipo: TipoVector): Boolean{
        return when (this) {
            TERRESTRE -> tipo == TipoVector.HUMANO || tipo == TipoVector.ANIMAL || tipo == TipoVector.INSECTO
            MARITIMO  -> tipo == TipoVector.HUMANO || tipo == TipoVector.ANIMAL
            AEREO     -> tipo == TipoVector.ANIMAL || tipo == TipoVector.INSECTO
        }
    }

}
