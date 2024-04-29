package ar.edu.unq.eperdemic.exceptions

class NoExisteLaUbicacion() : RuntimeException() {

    override val message: String?
        get() = "No hay ninguna ubicacion con el id registrado."

}