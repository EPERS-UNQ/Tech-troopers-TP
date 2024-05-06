package ar.edu.unq.eperdemic.controller.dto

import ar.edu.unq.eperdemic.modelo.vector.Vector
import ar.edu.unq.eperdemic.controller.dto.UbicacionDTO
import ar.edu.unq.eperdemic.controller.dto.EspecieDTO
import ar.edu.unq.eperdemic.modelo.Direccion
import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.vector.TipoVector

class VectorDTO ( val id: Long?,
                  val nombre: String?,
                  val ubicacion: UbicacionDTO,
                  val tipo: String,
                  val especies: MutableSet<EspecieDTO> ) {

    companion object {
        fun desdeModelo(vector: Vector) =
            VectorDTO(
                id           = vector.getId(),
                nombre       = vector.nombre,
                ubicacion    = vector.ubicacion!!.aDTO()!!,
                tipo         = vector.getTipo().toString(),
                especies     = vector.especies
                    .map { especie -> EspecieDTO.desdeModelo(especie) }
                    .toCollection(HashSet())
            )
    }

    fun aModelo(): Vector {
        val vector = Vector()
        val ubicacionDTO = UbicacionDTO(this.ubicacion.id, this.ubicacion.nombre)
        vector.setId(this.id!!)
        vector.nombre = this.nombre
        vector.ubicacion = ubicacionDTO.aModelo()
        vector.setTipo(enumValueOf<TipoVector>(this.tipo))
        vector.especies = this.especies
            .map { especie -> especie.aModelo() }
            .toCollection(HashSet())
        return vector
    }

}