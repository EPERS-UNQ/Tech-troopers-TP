package ar.edu.unq.eperdemic.persistencia.dao

import ar.edu.unq.eperdemic.modelo.Ubicacion
import ar.edu.unq.eperdemic.modelo.UbicacionJPA
import org.springframework.data.repository.CrudRepository
import org.springframework.data.jpa.repository.Query

interface UbicacionDAO : CrudRepository<UbicacionJPA, Long> {

    @Query("""
        select count(u)
        from Ubicacion u
    """)
    fun countByNombre(): Int

    @Query("select u.nombre from Ubicacion u where u.id = ?1")
    fun recuperarPorNombre(ubicacionId: Long?): String

}