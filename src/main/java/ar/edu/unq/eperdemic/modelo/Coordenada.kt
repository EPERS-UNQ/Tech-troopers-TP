package ar.edu.unq.eperdemic.modelo

import ar.edu.unq.eperdemic.controller.dto.CoordenadasDTO

class Coordenada (
    private val latitud: Int,
    private val longitud: Int) {

    fun aDTO(): CoordenadasDTO {
        return CoordenadasDTO(this.latitud, this.longitud)
    }

}