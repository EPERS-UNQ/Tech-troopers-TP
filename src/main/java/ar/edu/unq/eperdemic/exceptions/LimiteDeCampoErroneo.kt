package ar.edu.unq.eperdemic.exceptions

import java.lang.RuntimeException

class LimiteDeCampoErroneo() : RuntimeException(){

    override val message: String?
        get() = "El valor de los campos tiene que ser entre 1 y 100"

}