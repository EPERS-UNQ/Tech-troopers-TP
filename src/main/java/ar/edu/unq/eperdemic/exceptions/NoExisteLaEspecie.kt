package ar.edu.unq.eperdemic.exceptions

import java.lang.RuntimeException

class NoExisteLaEspecie() : RuntimeException() {

    override val message: String?
        get() = "No hay ninguna Especie con el id registrado"

}