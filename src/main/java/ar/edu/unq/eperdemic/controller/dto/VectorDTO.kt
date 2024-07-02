package ar.edu.unq.eperdemic.controller.dto

import ar.edu.unq.eperdemic.modelo.ubicacion.UbicacionGlobal
import ar.edu.unq.eperdemic.modelo.vector.TipoVector
import ar.edu.unq.eperdemic.modelo.vector.VectorGlobal

class VectorDTO ( val id: Long?,
                  val nombre: String?,
                  val ubicacionId: Long,
                  val tipo: String,
                  val especies: MutableSet<EspecieDTO> ) {

    fun aModelo(ubicacion: UbicacionGlobal): VectorGlobal {
        val vector = VectorGlobal()
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