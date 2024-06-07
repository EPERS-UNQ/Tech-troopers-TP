package ar.edu.unq.eperdemic.exceptions

import javax.persistence.PersistenceException

class ErrorTipoCaminoInvalido (override val message: String) : PersistenceException() {

}
