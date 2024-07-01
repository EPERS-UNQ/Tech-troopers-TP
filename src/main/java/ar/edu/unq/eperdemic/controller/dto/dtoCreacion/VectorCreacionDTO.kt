package ar.edu.unq.eperdemic.controller.dto.dtoCreacion

import ar.edu.unq.eperdemic.modelo.ubicacion.UbicacionGlobal
import ar.edu.unq.eperdemic.modelo.ubicacion.UbicacionJpa
import ar.edu.unq.eperdemic.modelo.vector.TipoVector
import ar.edu.unq.eperdemic.modelo.vector.Vector
import ar.edu.unq.eperdemic.modelo.vector.VectorGlobal

class VectorCreacionDTO (val nombre: String?,
                         val ubicacionId: Long?,
                         val tipo: String) {

    fun aModelo(ubicacion: UbicacionGlobal): VectorGlobal {
        return VectorGlobal(this.nombre!!, ubicacion, enumValueOf<TipoVector>(this.tipo))
    }

}