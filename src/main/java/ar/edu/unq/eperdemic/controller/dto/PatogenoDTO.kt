package ar.edu.unq.eperdemic.controller.dto

import ar.edu.unq.eperdemic.modelo.Patogeno

class PatogenoDTO ( val id: Long?,
                    val tipo: String?,
                    val contagioHumano: Int,
                    val contagioAnimal: Int,
                    val contagioInsecto: Int,
                    val defensa: Int,
                    val capacidadBiomecanizacion: Int) {

    companion object {
        fun desdeModelo(patogeno: Patogeno) =
            PatogenoDTO(
                id                       = patogeno.getId(),
                tipo                     = patogeno.tipo,
                contagioHumano           = patogeno.capContagioHumano,
                contagioAnimal           = patogeno.capContagioAnimal,
                contagioInsecto          = patogeno.capContagioInsecto,
                defensa                  = patogeno.defensa,
                capacidadBiomecanizacion = patogeno.capDeBiomecanizacion
            )
    }

    fun aModelo(): Patogeno {
        val patogeno = Patogeno()
        // patogeno.id  = this.id  // se deberia hacer un setter o no se deberia colocar, ya que se genera automaticamente en el back?
        patogeno.tipo                 = this.tipo
        patogeno.capContagioHumano    = this.contagioHumano
        patogeno.capContagioAnimal    = this.contagioAnimal
        patogeno.capContagioInsecto   = this.contagioInsecto
        patogeno.defensa              = this.defensa
        patogeno.capDeBiomecanizacion = this.capacidadBiomecanizacion
        return patogeno
    }

}