package ar.edu.unq.eperdemic.exceptions

import javax.persistence.PersistenceException

class ErrorNombreEnUso() : PersistenceException() {

    override val message: String?
        get() = "El nombre ya esta en uso"

}