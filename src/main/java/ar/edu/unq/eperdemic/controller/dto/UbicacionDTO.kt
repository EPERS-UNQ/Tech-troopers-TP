package ar.edu.unq.eperdemic.controller.dto

import ar.edu.unq.eperdemic.modelo.UbicacionGlobal

class UbicacionDTO ( val id: Long?,
                     val nombre: String? ) {

    fun aModelo(): UbicacionGlobal {
        val ubicacion = UbicacionGlobal()
        ubicacion.setId(this.id!!)
        ubicacion.setNombre(this.nombre!!)
        return ubicacion
    }


}