package ar.edu.unq.eperdemic.controller.dto

import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.controller.dto.VectorDTO
import ar.edu.unq.eperdemic.controller.dto.PatogenoDTO

class EspecieDTO ( val id:           Long?,
                   val nombre:       String?,
                   val paisDeOrigen: String?,
                   val vectores:     MutableSet<VectorDTO>?,
                   val patogeno:     PatogenoDTO?) {

    companion object {
        fun desdeModelo(especie: Especie) =
            EspecieDTO(
                id           = especie.getId(),
                nombre       = especie.nombre,
                paisDeOrigen = especie.paisDeOrigen,
                vectores     = especie.vectores
                    .map { vector -> VectorDTO.desdeModelo(vector) }
                    .toCollection(HashSet()),
                patogeno     = especie.patogeno
            )
    }

    fun aModelo(): Especie {
        val especie = Especie()
        especie.nombre  = this.nombre
        especie.patogeno = this.patogeno.aModelo()
        especie.paisDeOrigen = this.paisDeOrigen
        especie.vectores = this.vectores
             .map { vectorDTO -> VectorDTO.aModelo(vectorDTO) }
            .toCollection(HashSet())
        return especie
    }

}