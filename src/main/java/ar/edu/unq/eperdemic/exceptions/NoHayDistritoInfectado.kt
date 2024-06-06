package ar.edu.unq.eperdemic.exceptions

class NoHayDistritoInfectado : RuntimeException() {
    override val message: String
        get() = "No se encuentran distritos con ubicaciones infectadas"
}