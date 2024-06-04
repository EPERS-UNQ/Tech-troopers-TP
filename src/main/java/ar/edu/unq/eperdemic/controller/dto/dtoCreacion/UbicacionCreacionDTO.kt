package ar.edu.unq.eperdemic.controller.dto.dtoCreacion

import ar.edu.unq.eperdemic.modelo.UbicacionJpa

class UbicacionCreacionDTO( val nombre: String?) {

    // CAMBIAR A UBICACION GLOBAL
    fun aModelo(): UbicacionJpa {
        return UbicacionJpa(this.nombre!!)
    }

}