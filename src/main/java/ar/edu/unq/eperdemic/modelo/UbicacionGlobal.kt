package ar.edu.unq.eperdemic.modelo

import ar.edu.unq.eperdemic.controller.dto.UbicacionDTO
import ar.edu.unq.eperdemic.exceptions.ErrorNombre

class UbicacionGlobal() {

    private var id: String? = null
    private var nombre: String? = null
    private var coordenada: Coordenada? = null

    constructor(nombre: String, coordenada: Coordenada) : this() {
        if(nombre.isBlank()){
            throw ErrorNombre("El nombre no puede ser vacio.")
        }
        this.coordenada = coordenada
        this.nombre = nombre
    }

    fun getNombre(): String {
        return this.nombre!!
    }

    fun getCoordenada(): Coordenada {
        return this.coordenada!!
    }

    fun getId(): String{
        return this.id!!
    }

    fun aDTO(): UbicacionDTO? {
        return UbicacionDTO(this.getId(), this.nombre) // ¿Como obtenemos el id? ¿Por jpa? Lo necesito para el dto...
    }

    fun setNombre(nombre: String) {
        this.nombre = nombre
    }

    fun setId(id: String) {
        this.id = id
    }
}