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
        patogeno.setId(this.id)
        patogeno.tipo                 = this.tipo
        patogeno.capContagioHumano    = this.contagioHumano
        patogeno.capContagioAnimal    = this.contagioAnimal
        patogeno.capContagioInsecto   = this.contagioInsecto
        patogeno.defensa              = this.defensa
        patogeno.capDeBiomecanizacion = this.capacidadBiomecanizacion
        return patogeno
    }

}