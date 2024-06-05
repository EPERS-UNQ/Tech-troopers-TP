package ar.edu.unq.eperdemic.modelo

import ar.edu.unq.eperdemic.controller.dto.CoordenadaDTO
import ar.edu.unq.eperdemic.exceptions.ErrorCoordenadaInvalida
import org.springframework.data.mongodb.core.mapping.Document

@Document
class Coordenada(latitud: Double, longitud: Double) {

    private var latitud:  Double? = latitud
    private var longitud: Double? = longitud

    fun aDTO(): CoordenadaDTO {
        return CoordenadaDTO(this.latitud!!, this.longitud!!)
    }

    private fun esCoordenadaValida(latitud: Double, longitud: Double): Boolean {
        val esLongitudValida = -180 <= longitud && longitud <= 180
        val esLatitudValida  = -90  <= latitud  && latitud  <= 90
        return esLongitudValida && esLatitudValida
    }

    fun getLatitud() : Double {
        return this.latitud!!
    }

    fun getLongitud() : Double {
        return this.longitud!!
    }

    init {
        if(!this.esCoordenadaValida(latitud, longitud)){
            throw ErrorCoordenadaInvalida()
        }
    }
}