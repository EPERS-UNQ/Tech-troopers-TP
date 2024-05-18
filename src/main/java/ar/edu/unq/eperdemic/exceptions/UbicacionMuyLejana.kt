package ar.edu.unq.eperdemic.exceptions

import java.lang.RuntimeException

class UbicacionMuyLejana() : RuntimeException() {

    override val message: String?
        get() = "Ubicacion muy lejana, no hay caminos que conecten"
}