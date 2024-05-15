package ar.edu.unq.eperdemic.exceptions

class ErrorUbicacionNoAlcanzable(private val idUbicacion: Long) : RuntimeException() {

    override val message: String?
        get() = "El id [$idUbicacion] de la ubicacion no dispone de un camino que pueda cruzar para el vector"

    companion object {

        private val serialVersionUID = 1L
    }

}