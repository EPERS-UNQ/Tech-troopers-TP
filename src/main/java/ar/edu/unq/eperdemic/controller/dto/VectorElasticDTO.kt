package ar.edu.unq.eperdemic.controller.dto

class VectorElasticDTO ( val id: String?,
                         val nombre: String?,
                         val tipo: String,
                         val especies: MutableSet<EspecieDTO>?,
                         val ubicacionActual: UbicacionElasticDTO) {}