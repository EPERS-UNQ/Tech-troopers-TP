package ar.edu.unq.eperdemic.exceptions

class DistritoAbiertoException: RuntimeException() {
    override val message: String
        get() = "El distrito debe estar cerrado"
}