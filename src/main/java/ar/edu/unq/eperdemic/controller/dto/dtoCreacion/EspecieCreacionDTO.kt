package ar.edu.unq.eperdemic.controller.dto.dtoCreacion

import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.Patogeno

class EspecieCreacionDTO ( val patogenoId: Long,
                           val nombre: String,
                           val ubicacionId: Long) {}