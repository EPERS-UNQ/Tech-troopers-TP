package ar.edu.unq.eperdemic.exceptions

class NombreDeDistritoExistenteException(private val nombre: String):
    RuntimeException() {
        override val message: String
        get() = "El distrito con el nombre ${nombre} ya existe"

}