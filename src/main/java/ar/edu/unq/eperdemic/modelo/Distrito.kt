package ar.edu.unq.eperdemic.modelo

import ar.edu.unq.eperdemic.controller.dto.DistritoDTO
import ar.edu.unq.eperdemic.controller.dto.UbicacionDTO
import ar.edu.unq.eperdemic.exceptions.ErrorCantidadDeCoordenadasMinimas
import ar.edu.unq.eperdemic.exceptions.ErrorNombre
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon

@Document
class Distrito(nombre: String?, forma: GeoJsonPolygon?) {

    @Id
    private var id: String? = null

    private var nombre: String? = nombre
    private var forma: GeoJsonPolygon? = forma
    private var ubicaciones: MutableSet<UbicacionMongo> = HashSet()

    constructor() : this(null, null) {}

    fun aDTO() : DistritoDTO {
        val ubicacionesDTO = this.ubicaciones.map { ubicacion -> ubicacion.aDTO() }.toCollection(mutableSetOf())
        return DistritoDTO(this.id, this.nombre!!, this.forma, ubicacionesDTO)
    }

    fun getNombre(): String {
        return nombre!!
    }

    fun getForma(): GeoJsonPolygon {
        return this.forma!!
    }

    fun setId(id: String) {
        this.id = id
    }

    fun setNombre(nombre: String) {
        this.nombre = nombre
    }

    fun setForma(forma: GeoJsonPolygon) {
        this.forma = forma
    }

    fun setUbicaciones(ubicaciones: MutableSet<UbicacionMongo>) {
        this.ubicaciones = ubicaciones
    }

    init {
        if(nombre!!.isBlank()){
            throw ErrorNombre("El nombre del Distrito no puede ser vacio.")
        }
        if(forma != null && forma.points.size < 4) {
            throw ErrorCantidadDeCoordenadasMinimas()
        }
    }

}
