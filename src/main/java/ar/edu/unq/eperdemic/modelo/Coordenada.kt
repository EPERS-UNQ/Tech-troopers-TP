package ar.edu.unq.eperdemic.modelo

import ar.edu.unq.eperdemic.controller.dto.CoordenadaDTO

class Coordenada(lat: Double, lon: Double) {

    var lat: Double = lat
    var lon: Double = lon

    fun aDTO(): CoordenadaDTO {
        return CoordenadaDTO(this.lat, this.lon)
    }

}