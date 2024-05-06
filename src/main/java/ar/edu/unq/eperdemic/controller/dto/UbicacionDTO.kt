package ar.edu.unq.eperdemic.controller.dto

import ar.edu.unq.eperdemic.modelo.Ubicacion

class UbicacionDTO ( val id: Long?,
                     val nombre: String? ) {

    companion object {
        fun desdeModelo(ubicacion: Ubicacion) =
            UbicacionDTO(
                id           = ubicacion.getId(),
                nombre       = ubicacion.getNombre()
            )
    }

    fun aModelo(): Ubicacion {
        val ubicacion = Ubicacion()
        ubicacion.setId(this.id)
        ubicacion.setNombre(this.nombre!!)
        return ubicacion
    }


}