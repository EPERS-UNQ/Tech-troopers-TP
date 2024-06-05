package ar.edu.unq.eperdemic.controller.dto.dtoCreacion

import ar.edu.unq.eperdemic.controller.dto.CoordenadaDTO
import ar.edu.unq.eperdemic.modelo.UbicacionGlobal

class UbicacionCreacionDTO( val nombre: String,
                            val coordenadaDTO: CoordenadaDTO ) {

    fun aModelo(): UbicacionGlobal {
        return UbicacionGlobal(this.nombre, coordenadaDTO.latitud, coordenadaDTO.longitud)
    }

}