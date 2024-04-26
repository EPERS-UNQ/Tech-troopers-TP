package ar.edu.unq.eperdemic.persistencia.dao

import ar.edu.unq.eperdemic.modelo.Patogeno
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface PatogenoDAO : CrudRepository<Patogeno, Long> {

    @Query( """
         from Patogeno p
         order by p.tipo asc
    """ )
    fun recuperarATodos(): List<Patogeno>

}