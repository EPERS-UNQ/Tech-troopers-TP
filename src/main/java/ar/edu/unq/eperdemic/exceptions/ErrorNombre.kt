package ar.edu.unq.eperdemic.exceptions

import java.lang.RuntimeException

class ErrorNombre() : RuntimeException(){

    override val message: String?
        get() = "El nombre no puede ser nulo."

}