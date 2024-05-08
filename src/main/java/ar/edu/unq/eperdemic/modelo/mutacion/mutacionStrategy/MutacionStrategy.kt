package ar.edu.unq.eperdemic.modelo.mutacion.mutacionStrategy

import ar.edu.unq.eperdemic.modelo.Especie

open class MutacionStrategy {

    var condition: Boolean = false

    open fun porcentajeDeMutacionExitoso(especie: Especie): Boolean {
        return this.condition
    }

}