package ar.edu.unq.eperdemic.exceptions

class ErrorTipoVectorInvalido() : IllegalArgumentException() {

    override val message: String?
        get() = "ttipoVector no puede ser nulo para BioalteracionGenetica"

}