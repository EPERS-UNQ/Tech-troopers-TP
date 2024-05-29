package ar.edu.unq.eperdemic.modelo

import ar.edu.unq.eperdemic.controller.dto.CoordenadasDTO
import ar.edu.unq.eperdemic.exceptions.ErrorCoordenadaInvalida

class Coordenada(latitud: Int, longitud: Int) {

    private var latitud:  Int? = latitud
    private var longitud: Int? = longitud

    fun aDTO(): CoordenadasDTO {
        return CoordenadasDTO(this.latitud!!, this.longitud!!)
    }

    private fun esCoordenadaValida(latitud: Int, longitud: Int): Boolean {
        val esLongitudValida = -180 <= longitud && longitud >= 180
        val esLatitudValida  = -90  <= latitud  && latitud  >= 90
        return esLongitudValida && esLatitudValida
    }

    init {
        if(!this.esCoordenadaValida(latitud, longitud)){
            throw ErrorCoordenadaInvalida()
        }
    }

}