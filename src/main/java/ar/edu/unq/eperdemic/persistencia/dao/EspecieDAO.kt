package ar.edu.unq.eperdemic.persistencia.dao

import ar.edu.unq.eperdemic.modelo.Especie
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface EspecieDAO : CrudRepository<Especie, Long> {

    @Query("select e from Especie e")
    fun recuperarTodos(): List<Especie>

    @Query(
            """
                select e 
                from Especie e 
                where e.patogeno.id = ?1 
                order by e.nombre 
            """
    )
    fun especiesDelPatogenoId(patogenoId: Long, direccion: String, pageable: Pageable): List<Especie>

}