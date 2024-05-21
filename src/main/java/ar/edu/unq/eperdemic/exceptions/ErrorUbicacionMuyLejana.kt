package ar.edu.unq.eperdemic.exceptions

class ErrorUbicacionMuyLejana() : RuntimeException() {

    override val message: String?
        get() = "La ubicacion no es posible de alcanzar desde la ubicacion actual"

    companion object {

        private val serialVersionUID = 1L
    }

}