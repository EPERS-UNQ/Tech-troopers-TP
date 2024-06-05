package ar.edu.unq.eperdemic.controller.dto

import ar.edu.unq.eperdemic.modelo.Distrito

class DistritoDTO(  val id: String?,
                    val nombre: String,
                    val coordenadas: MutableSet<CoordenadaDTO> ) {

    fun aModelo(): Distrito {
        val coordenadasModelo = this.coordenadas.map { coordenada -> coordenada.aModelo() }.toCollection(HashSet())
        return Distrito(this.nombre, coordenadasModelo)
    }

}