package ar.edu.unq.eperdemic.modelo

import ar.edu.unq.eperdemic.controller.dto.ReporteDeContagiosDTO

class ReporteDeContagios(
    val cantidadVectores   : Int,
    val cantidadInfectados : Int,
    val especiePrevalente  : String
) {
    fun aDTO(): ReporteDeContagiosDTO {
        return ReporteDeContagiosDTO(cantidadVectores, cantidadInfectados, especiePrevalente)
    }
}