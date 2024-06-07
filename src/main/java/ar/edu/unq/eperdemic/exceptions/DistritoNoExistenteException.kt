package ar.edu.unq.eperdemic.exceptions

class DistritoNoExistenteException(private val nombreDistrito: String) : RuntimeException() {
    override val message: String
        get() = "El distrito con el nombre ${nombreDistrito} no existe"
}