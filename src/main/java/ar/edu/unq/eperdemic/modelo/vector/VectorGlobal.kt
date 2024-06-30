package ar.edu.unq.eperdemic.modelo.vector

import ar.edu.unq.eperdemic.exceptions.ErrorNombre
import ar.edu.unq.eperdemic.modelo.ubicacion.UbicacionGlobal
import ar.edu.unq.eperdemic.modelo.ubicacion.UbicacionJpa

class VectorGlobal() {

    private lateinit var tipo: TipoVector
    var nombre: String? = null
    var ubicacion: UbicacionGlobal? = null

    constructor(nombre: String, ubicacion: UbicacionGlobal, tipoVector: TipoVector):this() {
        if(nombre.isBlank()){
            throw ErrorNombre("El nombre del vector no puede estar vacio.")
        }
        this.nombre = nombre
        this.ubicacion = ubicacion
        this.tipo = tipoVector
    }

    fun aJPA(): Vector {
        return Vector(this.nombre!!, UbicacionJpa(this.ubicacion!!.getNombre()), this.tipo)
    }

    fun aElastic(): VectorElastic {
        return VectorElastic(this.nombre!!, this.ubicacion!!.aElastic(), this.tipo)
    }

}