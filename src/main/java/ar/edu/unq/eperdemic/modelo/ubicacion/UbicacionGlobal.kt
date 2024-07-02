package ar.edu.unq.eperdemic.modelo.ubicacion

import ar.edu.unq.eperdemic.controller.dto.UbicacionDTO
import ar.edu.unq.eperdemic.exceptions.ErrorNombre
import ar.edu.unq.eperdemic.modelo.Coordenada
import org.springframework.data.mongodb.core.geo.GeoJsonPoint

class UbicacionGlobal() {

    private var id: Long? = null
    private var nombre: String? = null
    private var coordenada: GeoJsonPoint? = null

    constructor(nombre: String, coordenada: GeoJsonPoint) : this() {
        if(nombre.isBlank()){
            throw ErrorNombre("El nombre no puede ser vacio.")
        }
        this.coordenada = coordenada
        this.nombre = nombre
    }

    fun getNombre(): String {
        return this.nombre!!
    }

    fun getCoordenada(): GeoJsonPoint {
        return this.coordenada!!
    }

    fun getId(): Long{
        return this.id!!
    }

    fun aDTO(): UbicacionDTO? {
        return UbicacionDTO(this.getId(), this.nombre, this.coordenada)
    }

    fun setNombre(nombre: String) {
        this.nombre = nombre
    }

    fun setId(id: Long) {
        this.id = id
    }

    fun aJPA(): UbicacionJpa {
        val ubicacionJPA = UbicacionJpa(this.nombre!!)
        ubicacionJPA.setId(this.id!!)
        return ubicacionJPA
    }

    fun aElastic(): UbicacionElastic {
        return UbicacionElastic(this.nombre!!, Coordenada(this.coordenada!!.y, this.coordenada!!.x))
    }
}