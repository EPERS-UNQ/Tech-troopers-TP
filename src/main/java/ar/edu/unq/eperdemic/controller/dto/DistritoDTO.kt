package ar.edu.unq.eperdemic.controller.dto

import ar.edu.unq.eperdemic.modelo.Distrito
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon

class DistritoDTO(  val id: String?,
                    val nombre: String,
                    val forma: GeoJsonPolygon?,
                    val ubicaciones: MutableSet<UbicacionDTO>) {

    fun aModelo(): Distrito {
        val distrito = Distrito()
        distrito.setId(this.id!!)
        distrito.setNombre(this.nombre)
        distrito.setForma(this.forma!!)
        distrito.setUbicaciones(this.ubicaciones.map { it.aModeloMongo() }.toCollection(mutableListOf()))
        return distrito
    }

}