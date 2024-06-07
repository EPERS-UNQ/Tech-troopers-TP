package ar.edu.unq.eperdemic.exceptions

import java.lang.RuntimeException

class ErrorYaExisteUnaUbicacionEnEsaCoordenada: RuntimeException(){

    override val message: String?
        get() = "Ya existe una ubicación en esa coordenada."

}