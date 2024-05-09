package ar.edu.unq.eperdemic.exceptions

class ErrorNoExisteEseTipoDeMutacion  : IllegalArgumentException() {

    override val message: String?
        get() = "No existe ninguna sub-clase de Mutacion que coincida"

}