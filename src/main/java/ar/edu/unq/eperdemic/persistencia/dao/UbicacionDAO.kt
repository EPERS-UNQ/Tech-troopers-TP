package ar.edu.unq.eperdemic.persistencia.dao

import ar.edu.unq.eperdemic.modelo.Ubicacion
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface UbicacionDAO : CrudRepository<Ubicacion, Long> {


    @Query("""
        select count(u)
        from Ubicacion u
    """)
    fun countUbicaciones(): Int

    @Query("select u.nombre from Ubicacion u where u.id = ?1")
    fun recuperarNombrePorID(ubicacionId: Long?): String

}