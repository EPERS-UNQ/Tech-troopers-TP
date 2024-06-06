package ar.edu.unq.eperdemic.exceptions

class CoordenadaDistritoIntersectionException : RuntimeException() {
    override val message: String
        get() = "El distrito intersecta con otro"
}