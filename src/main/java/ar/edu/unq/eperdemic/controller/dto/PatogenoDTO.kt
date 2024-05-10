package ar.edu.unq.eperdemic.controller.dto

import ar.edu.unq.eperdemic.modelo.Patogeno

class PatogenoDTO ( val id: Long?,
                    val tipo: String?,
                    val contagioHumano: Int,
                    val contagioAnimal: Int,
                    val contagioInsecto: Int,
                    val defensa: Int,
                    val capacidadBiomecanizacion: Int) {

    fun aModelo(): Patogeno {
        val patogeno = Patogeno()
        patogeno.setId(this.id!!)
        patogeno.tipo                   = this.tipo
        patogeno.cap_contagio_humano    = this.contagioHumano
        patogeno.cap_contagio_animal    = this.contagioAnimal
        patogeno.cap_contagio_insecto   = this.contagioInsecto
        patogeno.defensa                = this.defensa
        patogeno.cap_de_biomecanizacion = this.capacidadBiomecanizacion
        return patogeno
    }

}