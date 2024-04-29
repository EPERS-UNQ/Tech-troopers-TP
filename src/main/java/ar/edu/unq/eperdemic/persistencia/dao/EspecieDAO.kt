package ar.edu.unq.eperdemic.persistencia.dao

import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.Direccion
import ar.edu.unq.eperdemic.modelo.vector.Vector
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface EspecieDAO : CrudRepository<Especie, Long> {

    fun recuperar(id: Long): Especie {
        return this.findById(id).orElse(null)
    }

    @Query("select e from Especie e ")
    fun recuperarTodos(): List<Especie>

    @Query(
            """
                select e 
                from Especie e 
                where e.patogeno.id = ?1 
                order by e.nombre ?2
                limit ?4
                offset (?3 * ?4)
            """
    )
    fun especiesDelPatogenoId(patogenoId: Long, direccion: Direccion, pagina: Int, cantidadPorPagina: Int): List<Especie>

}