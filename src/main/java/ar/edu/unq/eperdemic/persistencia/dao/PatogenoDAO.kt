package ar.edu.unq.eperdemic.persistencia.dao

import ar.edu.unq.eperdemic.modelo.Patogeno
import org.springframework.data.repository.CrudRepository

interface PatogenoDAO : CrudRepository<Patogeno, Long> {

    fun recuperar(id: Long?): Patogeno
    fun actualizar(patogeno: Patogeno)
    fun recuperarATodos() : List<Patogeno>
    fun eliminar(patogeno: Patogeno)
}
