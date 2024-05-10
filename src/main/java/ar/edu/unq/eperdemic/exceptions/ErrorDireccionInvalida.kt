package ar.edu.unq.eperdemic.exceptions

import java.lang.RuntimeException

class ErrorDireccionInvalida (override val message: String?) : RuntimeException()  {
}