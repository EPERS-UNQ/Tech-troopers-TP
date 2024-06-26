package ar.edu.unq.eperdemic.exceptions

class ErrorUbicacionNoAlcanzable() : RuntimeException() {

    override val message: String?
        get() = "La ubicacion no dispone de un camino que pueda cruzar para el vector"

    companion object {
        private val serialVersionUID = 1L
    }

}