package ar.edu.unq.eperdemic.modelo

import ar.edu.unq.eperdemic.controller.dto.CoordenadaDTO

class Coordenada(latitud: Double, longitud: Double) {

    var lat: Double = latitud
    var lon: Double = longitud

    fun aDTO(): CoordenadaDTO {
        return CoordenadaDTO(this.lat, this.lon)
    }

}