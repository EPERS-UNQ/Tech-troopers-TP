package ar.edu.unq.eperdemic.controller.dto

import ar.edu.unq.eperdemic.modelo.Ubicacion

class UbicacionDTO ( val id: Long?,
                     val nombre: String? ) {

    fun aModelo(): Ubicacion {
        val ubicacion = Ubicacion()
        ubicacion.setId(this.id!!)
        ubicacion.setNombre(this.nombre!!)
        return ubicacion
    }


}