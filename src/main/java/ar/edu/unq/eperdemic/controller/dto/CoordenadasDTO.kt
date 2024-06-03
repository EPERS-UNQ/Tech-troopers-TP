package ar.edu.unq.eperdemic.controller.dto

import ar.edu.unq.eperdemic.modelo.Coordenada

class CoordenadasDTO ( val latitud: Double,
                       val longitud: Double ) {

    fun aModelo(): Coordenada {
        return Coordenada(latitud, longitud)
    }

}