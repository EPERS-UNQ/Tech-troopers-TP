package ar.edu.unq.eperdemic.exceptions

import java.lang.RuntimeException

class NoExisteElVector() : RuntimeException() {

    override val message: String?
        get() = "No hay ningun Vector con el id registrado"

}