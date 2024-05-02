package ar.edu.unq.eperdemic.exceptions

import java.lang.RuntimeException
import javax.persistence.PersistenceException

class ErrorNombre(override val message: String) : PersistenceException(){

}