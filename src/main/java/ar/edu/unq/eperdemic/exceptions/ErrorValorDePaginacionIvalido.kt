package ar.edu.unq.eperdemic.exceptions

import java.lang.RuntimeException

class ErrorValorDePaginacionIvalido() : RuntimeException(){

    override val message: String?
        get() = "El número de página es menor a 0 o la cantida de elementos por pagina es menor a 0."

}