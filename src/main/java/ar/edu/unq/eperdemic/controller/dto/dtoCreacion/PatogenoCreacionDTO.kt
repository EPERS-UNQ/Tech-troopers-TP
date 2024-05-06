package ar.edu.unq.eperdemic.controller.dto.dtoCreacion

import ar.edu.unq.eperdemic.modelo.Patogeno

class PatogenoCreacionDTO( val tipo: String?,
                           val contagioHumano: Int,
                           val contagioAnimal: Int,
                           val contagioInsecto: Int,
                           val defensa: Int,
                           val capacidadBiomecanizacion: Int) {

    fun aModelo(): Patogeno {
        return Patogeno(this.tipo!!, this.contagioHumano, this.contagioAnimal, this.contagioInsecto, this.defensa, this.capacidadBiomecanizacion)
    }

}