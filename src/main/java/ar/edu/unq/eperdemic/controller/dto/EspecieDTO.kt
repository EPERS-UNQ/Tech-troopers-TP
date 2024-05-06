package ar.edu.unq.eperdemic.controller.dto

import ar.edu.unq.eperdemic.modelo.Especie

class EspecieDTO ( val id:           Long?,
                   val nombre:       String?,
                   val paisDeOrigen: String?,
                   val patogeno:     PatogenoDTO?) {

    fun aModelo(): Especie {
        val especie = Especie()
        especie.setId(this.id)
        especie.nombre  = this.nombre
        especie.patogeno = this.patogeno!!.aModelo()
        especie.paisDeOrigen = this.paisDeOrigen
        return especie
    }

}