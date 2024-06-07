package ar.edu.unq.eperdemic.exceptions

import java.lang.RuntimeException

class ErrorCoordenadaInvalida: RuntimeException() {

    override val message: String?
        get() = "La coordenada no es valida."

}