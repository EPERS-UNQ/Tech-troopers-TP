package ar.edu.unq.eperdemic.exceptions

class NoHayVectorException() : RuntimeException() {

    override val message: String?
        get() = "No hay ningun Vector en la Ubicacion"

}