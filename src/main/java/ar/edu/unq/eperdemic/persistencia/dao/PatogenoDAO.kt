package ar.edu.unq.eperdemic.persistencia.dao

import ar.edu.unq.eperdemic.modelo.Patogeno
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface PatogenoDAO : CrudRepository<Patogeno, Long> {

    fun recuperar(id: Long): Patogeno {
        return this.findById(id).orElse(null)
    }

    @Query("select p from Patogeno p order by p.nombre ASC ")
    fun recuperarTodos() : List<Patogeno>
}
