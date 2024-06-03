package ar.edu.unq.eperdemic.exceptions

class ErrorCantidadDeCoordenadasMinimas : RuntimeException() {

    override val message: String?
        get() = "Deben haber al menos 3 coordenadas para poder crear el Distrito."

}