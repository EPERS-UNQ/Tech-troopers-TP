package ar.edu.unq.eperdemic.persistencia.dao

import ar.edu.unq.eperdemic.modelo.Ubicacion
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface UbicacionDAO : CrudRepository<Ubicacion, Long> {

    @Query("select u.nombre from Ubicacion u where u.id = ?1")
    fun recuperarPorNombre(ubicacionId: Long?): String

    @Query("select u from Ubicacion u order by u.nombre ASC")
    fun recuperarTodos() : List<Ubicacion>

}