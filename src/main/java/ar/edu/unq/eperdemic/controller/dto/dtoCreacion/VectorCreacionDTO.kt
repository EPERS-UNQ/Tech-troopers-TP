package ar.edu.unq.eperdemic.controller.dto.dtoCreacion

import ar.edu.unq.eperdemic.controller.dto.UbicacionDTO
import ar.edu.unq.eperdemic.modelo.vector.TipoVector
import ar.edu.unq.eperdemic.modelo.vector.Vector

class VectorCreacionDTO (val nombre: String?,
                         val ubicacion: UbicacionDTO, // ubicacionID: Long
                         val tipo: String, ) {

    fun aModelo(): Vector {
        return Vector(this.nombre!!, this.ubicacion.aModelo(), enumValueOf<TipoVector>(this.tipo))
    }

}