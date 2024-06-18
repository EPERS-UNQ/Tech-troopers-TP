package ar.edu.unq.eperdemic.exceptions

class NoHayUbicaciones() : RuntimeException() {

    override val message: String?
        get() = "No hay ubicaciones."

}