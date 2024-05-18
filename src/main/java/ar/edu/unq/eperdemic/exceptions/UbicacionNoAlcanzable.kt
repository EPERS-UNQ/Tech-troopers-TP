package ar.edu.unq.eperdemic.exceptions

import java.lang.RuntimeException

class UbicacionNoAlcanzable() : RuntimeException() {

    override val message: String?
        get() = "La ubicacion no es alcanzable por el vector"
}