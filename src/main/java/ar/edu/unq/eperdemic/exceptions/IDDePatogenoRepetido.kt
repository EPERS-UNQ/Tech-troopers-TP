package ar.edu.unq.eperdemic.exceptions

class IDDePatogenoRepetido(private val id: Long) : RuntimeException() {

    override val message: String?
    get() = "El id de patogeno [$id] ya esta siendo utilizado y no puede volver a crearse"

    companion object {

        private val serialVersionUID = 1L
    }

}