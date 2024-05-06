package ar.edu.unq.eperdemic.controller.dto

import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.controller.dto.VectorDTO
import ar.edu.unq.eperdemic.controller.dto.PatogenoDTO
import ar.edu.unq.eperdemic.modelo.vector.Vector

class EspecieDTO ( val id:           Long?,
                   val nombre:       String?,
                   val paisDeOrigen: String?,
                   val patogeno:     PatogenoDTO?) {

    companion object {
        fun desdeModelo(especie: Especie) =
            EspecieDTO(
                id           = especie.getId(),
                nombre       = especie.nombre,
                paisDeOrigen = especie.paisDeOrigen,
                patogeno     = especie.patogeno!!.aDTO()
            )
    }

    fun aModelo(): Especie {
        val especie = Especie()
        especie.setId(this.id)
        especie.nombre  = this.nombre
        especie.patogeno = this.patogeno!!.aModelo()
        especie.paisDeOrigen = this.paisDeOrigen
        return especie
    }

}