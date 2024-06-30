package ar.edu.unq.eperdemic.controller.dto

class VectorElasticDTO ( val id: String?,
                         val nombre: String?,
                         val ubicaciones: MutableList<UbicacionDTO>,
                         val tipo: String,
                         val especies: MutableSet<EspecieDTO>?)
                         //val ubicacionActual: UbicacionDTO)
 {
}