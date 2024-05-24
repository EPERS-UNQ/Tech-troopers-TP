package ar.edu.unq.eperdemic.modelo.vector

import ar.edu.unq.eperdemic.modelo.mutacion.ElectroBranqueas
import ar.edu.unq.eperdemic.modelo.mutacion.PropulsionMotora

enum class TipoVector() {
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

    companion object {
        fun puedeCruzar(vector: Vector): List<String> {
            if(vector.getTipo() == HUMANO && vector.tieneMutacionPropulsionMotora()){
                return when (vector.getTipo()) {
                    HUMANO -> listOf("Terrestre", "Maritimo", "Aereo")
                    ANIMAL -> listOf("Terrestre", "Maritimo", "Aereo")
                    INSECTO -> listOf("Terrestre", "Aereo")
                }
            }
            else if (vector.getTipo() == INSECTO && vector.tieneMutacionElectroBranqueas()){
                return when (vector.getTipo()) {
                    HUMANO -> listOf("Terrestre", "Maritimo")
                    ANIMAL -> listOf("Terrestre", "Maritimo", "Aereo")
                    INSECTO -> listOf("Terrestre", "Aereo", "Maritimo")
                }
            }
            else {
                return when (vector.getTipo()) {
                    HUMANO -> listOf("Terrestre", "Maritimo")
                    ANIMAL -> listOf("Terrestre", "Maritimo", "Aereo")
                    INSECTO -> listOf("Terrestre", "Aereo")
                }
            }

        }
    }
}
