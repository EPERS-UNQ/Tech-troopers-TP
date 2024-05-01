package ar.edu.unq.eperdemic.persistencia.dao

import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.Direccion
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface EspecieDAO : CrudRepository<Especie, Long> {

    /*
    @Query(
        " select e " +
        " from Especie e " +
        " where e.patogeno.id = ?1" +
        " order by e.nombre "
    )
    fun findEspeciesByPatogenoEqualsOrderByNombre(patogenoId: Long, direccion: Direccion, pagina: Int, cantidadPorPagina: Int): List<Especie>
     */
}