package ar.edu.unq.eperdemic.controller.dto

import ar.edu.unq.eperdemic.exceptions.ErrorNoExisteEseTipoDeMutacion
import ar.edu.unq.eperdemic.exceptions.ErrorTipoVectorInvalido
import ar.edu.unq.eperdemic.modelo.mutacion.*
import ar.edu.unq.eperdemic.modelo.vector.TipoVector

class MutacionDTO( val tipoDeMutacion: String,
                   val potencia      : Int?,
                   val tipoVector    : String?) {

    fun aModelo(): Mutacion {
        return when (tipoDeMutacion.lowercase()) {
            "propulsionmotora" -> PropulsionMotora()
            "electrobranqueas" -> ElectroBranqueas()
            "supresionbiomecanica" -> SupresionBiomecanica(potencia ?: 0)
            "bioalteraciongenetica" -> {
                if (tipoVector != null) {
                    BioalteracionGenetica(enumValueOf<TipoVector>(tipoVector))
                } else {
                    throw ErrorTipoVectorInvalido()
                }
            }
            else -> {
                throw ErrorNoExisteEseTipoDeMutacion()
            }
        }
    }

}