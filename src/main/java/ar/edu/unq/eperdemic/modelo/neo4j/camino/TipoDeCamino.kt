package ar.edu.unq.eperdemic.modelo.neo4j.camino

import ar.edu.unq.eperdemic.modelo.vector.TipoVector

enum class TipoDeCamino {

    TERRESTRE,
    MARITIMO,
    AEREO;

    companion object {
        fun puedeCruzar(tipo: TipoVector): List<String> {
            return when (tipo) {
                TipoVector.HUMANO  -> listOf("Terrestre", "Maritimo")
                TipoVector.ANIMAL  -> listOf("Terrestre", "Maritimo", "Aereo")
                TipoVector.INSECTO -> listOf("Terrestre", "Aereo")
            }
        }
    }

}
