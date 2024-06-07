package ar.edu.unq.eperdemic.controller.dto

import ar.edu.unq.eperdemic.modelo.Coordenada
import ar.edu.unq.eperdemic.modelo.Distrito

class DistritoDTO(  val id: String?,
                    val nombre: String,
                    val coordenadas: MutableSet<CoordenadasDTO> ) {

    fun aModelo(): Distrito {
        val coordenadasModelo = this.coordenadas.map { coordenada -> coordenada.aModelo() }.toCollection(HashSet())
        return Distrito(this.nombre, coordenadasModelo)
    }

}