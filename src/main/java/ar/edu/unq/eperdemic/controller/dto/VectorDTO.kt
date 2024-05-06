package ar.edu.unq.eperdemic.controller.dto

import ar.edu.unq.eperdemic.modelo.vector.Vector
import ar.edu.unq.eperdemic.modelo.vector.TipoVector

class VectorDTO ( val id: Long?,
                  val nombre: String?,
                  val ubicacion: UbicacionDTO,
                  val tipo: String,
                  val especies: MutableSet<EspecieDTO> ) {

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