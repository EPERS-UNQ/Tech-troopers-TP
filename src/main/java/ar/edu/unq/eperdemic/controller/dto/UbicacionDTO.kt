package ar.edu.unq.eperdemic.controller.dto

import ar.edu.unq.eperdemic.modelo.UbicacionJpa

class UbicacionDTO ( val id: Long?,
                     val nombre: String? ) {

    fun aModelo(): UbicacionJpa {
        val ubicacion = UbicacionJpa()
        ubicacion.setId(this.id!!)
        ubicacion.setNombre(this.nombre!!)
        return ubicacion
    }


}