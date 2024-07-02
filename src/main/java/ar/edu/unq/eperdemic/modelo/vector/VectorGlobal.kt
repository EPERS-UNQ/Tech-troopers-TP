package ar.edu.unq.eperdemic.modelo.vector

import ar.edu.unq.eperdemic.controller.dto.EspecieDTO
import ar.edu.unq.eperdemic.controller.dto.VectorDTO
import ar.edu.unq.eperdemic.exceptions.ErrorNombre
import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.ubicacion.UbicacionGlobal
import ar.edu.unq.eperdemic.modelo.ubicacion.UbicacionJpa

class VectorGlobal() {

    var id: Long? = null
    var idElastic: String? = null
    private lateinit var tipo: TipoVector
    var nombre: String? = null
    var ubicacion: UbicacionGlobal? = null
    var especies: HashSet<Especie> = HashSet()

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

    fun setId(id: Long) {
        this.id = id
    }

    fun setTipo(nuevoTipo: TipoVector) {
        this.tipo = nuevoTipo
    }

    fun getId(): Long {
        return this.id!!
    }

    fun aDTO(): VectorDTO {
        val especiesDTO : List<EspecieDTO> = especies.map { especie -> especie.aDTO()!! }
        return VectorDTO(this.getId(), this.nombre, this.ubicacion!!.getId(), this.tipo.toString(), especiesDTO.toMutableSet())
    }

}