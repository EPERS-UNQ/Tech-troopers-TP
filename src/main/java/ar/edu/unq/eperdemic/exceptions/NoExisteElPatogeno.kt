package ar.edu.unq.eperdemic.exceptions

import java.lang.RuntimeException

class NoExisteElPatogeno() : RuntimeException() {

    override val message: String?
        get() = "No hay ningun Patogeno con el id registrado"

}