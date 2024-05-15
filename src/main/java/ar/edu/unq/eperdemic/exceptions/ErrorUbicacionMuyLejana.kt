package ar.edu.unq.eperdemic.exceptions

class ErrorUbicacionMuyLejana(private val idUbicacion: Long) : RuntimeException() {

    override val message: String?
        get() = "El id [$idUbicacion] de la ubicacion no es posible de alcanzar desde la ubicacion actual"

    companion object {

        private val serialVersionUID = 1L
    }

}