package ar.edu.unq.eperdemic.controller.dto.dtoCreacion

import ar.edu.unq.eperdemic.modelo.Ubicacion

class UbicacionCreacionDTO( val nombre: String? ) {

    fun aModelo(): Ubicacion {
        return Ubicacion(this.nombre!!)
    }

}