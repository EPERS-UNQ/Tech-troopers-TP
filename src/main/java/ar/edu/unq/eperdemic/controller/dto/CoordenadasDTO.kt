package ar.edu.unq.eperdemic.controller.dto

import ar.edu.unq.eperdemic.modelo.Coordenada
import ar.edu.unq.eperdemic.modelo.Especie

class CoordenadasDTO ( val latitud: Int,
                       val longitud: Int ) {

    fun aModelo(): Coordenada {
        return Coordenada(latitud, longitud)
    }

}