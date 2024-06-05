package ar.edu.unq.eperdemic.controller.dto.dtoCreacion

import ar.edu.unq.eperdemic.modelo.UbicacionJpa
import ar.edu.unq.eperdemic.modelo.vector.TipoVector
import ar.edu.unq.eperdemic.modelo.vector.Vector

class VectorCreacionDTO (val nombre: String?,
                         val ubicacionId: Long?,
                         val tipo: String) {

    fun aModelo(ubicacion: UbicacionJpa): Vector {
        return Vector(this.nombre!!, ubicacion, enumValueOf<TipoVector>(this.tipo))
    }

}