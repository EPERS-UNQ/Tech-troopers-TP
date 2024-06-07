package ar.edu.unq.eperdemic.controller.dto

import ar.edu.unq.eperdemic.modelo.UbicacionJpa
import ar.edu.unq.eperdemic.modelo.vector.Vector
import ar.edu.unq.eperdemic.modelo.vector.TipoVector

class VectorDTO ( val id: Long?,
                  val nombre: String?,
                  val ubicacionId: Long,
                  val tipo: String,
                  val especies: MutableSet<EspecieDTO> ) {

    fun aModelo(ubicacion: UbicacionJpa): Vector {
        val vector = Vector()
        vector.setId(this.id!!)
        vector.nombre = this.nombre
        vector.ubicacion = ubicacion
        vector.setTipo(enumValueOf<TipoVector>(this.tipo))
        vector.especies = this.especies
            .map { especie -> especie.aModelo() }
            .toCollection(HashSet())
        return vector
    }

}