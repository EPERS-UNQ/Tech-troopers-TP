package ar.edu.unq.eperdemic.modelo.ubicacion

import ar.edu.unq.eperdemic.controller.dto.UbicacionElasticDTO
import ar.edu.unq.eperdemic.modelo.Coordenada

class UbicacionElastic {

    var nombre: String? = null

    var coordenada: Coordenada? = null

    constructor(nombre: String, coordenada: Coordenada){
        this.nombre = nombre
        this.coordenada = coordenada
    }

    fun aDTO(): UbicacionElasticDTO {
        return UbicacionElasticDTO(this.nombre!!, this.coordenada!!.aDTO())
    }

}