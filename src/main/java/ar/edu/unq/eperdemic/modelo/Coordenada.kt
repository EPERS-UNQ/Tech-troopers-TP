package ar.edu.unq.eperdemic.modelo

import ar.edu.unq.eperdemic.controller.dto.CoordenadasDTO
import ar.edu.unq.eperdemic.exceptions.ErrorCoordenadaInvalida
import org.springframework.data.mongodb.core.mapping.Document
import javax.persistence.Id

@Document
class Coordenada(latitud: Int, longitud: Int) {

    private var latitud:  Int? = latitud
    private var longitud: Int? = longitud

    fun aDTO(): CoordenadasDTO {
        return CoordenadasDTO(this.latitud!!, this.longitud!!)
    }

    private fun esCoordenadaValida(latitud: Int, longitud: Int): Boolean {
        val esLongitudValida = -180 <= longitud && longitud <= 180
        val esLatitudValida  = -90  <= latitud  && latitud  <= 90
        return esLongitudValida && esLatitudValida
    }



    fun getLatitud() : Int {
        return this.latitud!!
    }

    fun getLongitud() : Int {
        return this.longitud!!
    }
}