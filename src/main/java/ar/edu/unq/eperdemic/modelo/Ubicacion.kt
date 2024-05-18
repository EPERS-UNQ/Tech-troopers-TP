package ar.edu.unq.eperdemic.modelo

import ar.edu.unq.eperdemic.controller.dto.UbicacionDTO
import ar.edu.unq.eperdemic.exceptions.ErrorNombre
import ar.edu.unq.eperdemic.modelo.camino.Camino

class Ubicacion() {

    private var id: Long? = null
    private var nombre: String? = null
    var camino: Camino? = null

    fun getId(): Long? {
        return this.id
    }
    fun setId(nuevoId: Long) {
        this.id = nuevoId
    }

    constructor(nombre: String) : this() {
        if(nombre.isBlank()){
            throw ErrorNombre("El nombre no puede ser vacio.")
        }
        this.nombre = nombre
    }

    constructor(neo: UbicacionNeo4j, jpa: UbicacionJPA) : this() {
        this.id = jpa.getId()
        this.nombre = jpa.getNombre()
        this.camino = neo.camino
    }

    fun getNombre(): String? {
        return this.nombre
    }

    fun setNombre(nombre: String) {
        this.nombre = nombre
    }

    fun aDTO(): UbicacionDTO? {
        return UbicacionDTO(this.getId(), this.nombre)
    }

    fun aJPA(): UbicacionJPA {
        return UbicacionJPA(this.nombre!!)
    }

    fun aNeo4j(): UbicacionNeo4j {
        return UbicacionNeo4j(this.nombre!!)
    }


}