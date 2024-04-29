package ar.edu.unq.eperdemic.exceptions

import java.lang.RuntimeException

class ErrorDeMovimiento() : RuntimeException() {

    override val message: String?
        get() = "La ubicacion o el vector no existe."

}