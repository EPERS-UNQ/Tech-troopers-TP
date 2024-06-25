package ar.edu.unq.eperdemic.modelo.ubicacion

import ar.edu.unq.eperdemic.controller.dto.UbicacionDTO
import ar.edu.unq.eperdemic.exceptions.ErrorNombre
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed
import org.springframework.data.mongodb.core.mapping.Document
import javax.persistence.Id
import kotlin.math.*

@Document("ubicacionMongo")
class UbicacionMongo() {

    @Id
    private var id: String? = null

    private var nombre: String? = null

    @GeoSpatialIndexed
    var coordenada: GeoJsonPoint? = null

    constructor(nombre: String, coordenada: GeoJsonPoint) : this() {
        if(nombre.isBlank()){
            throw ErrorNombre("El nombre no puede ser vacio.")
        }
        this.nombre = nombre
        this.coordenada = coordenada
    }

    fun getCordenada() : GeoJsonPoint {
        return this.coordenada!!
    }
    fun getNombre() : String {
        return this.nombre!!
    }

    fun setId(id: String) {
        this.id = id
    }

    fun getId(): String {
        return this.id!!
    }

    fun setNombre(nombre: String) {
        this.nombre = nombre
    }

    fun aDTO(): UbicacionDTO {
        return UbicacionDTO(this.id!!.toLong(), this.nombre, this.coordenada)
    }

}
