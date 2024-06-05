package ar.edu.unq.eperdemic.exceptions

import org.springframework.dao.DataIntegrityViolationException

class ErrorYaExisteLaEntidad(message: String) : DataIntegrityViolationException(message) {
    constructor() : this("La entidad ya existe en la base de datos.")
}