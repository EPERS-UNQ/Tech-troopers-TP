package ar.edu.unq.eperdemic.persistencia.dao

import ar.edu.unq.eperdemic.modelo.Ubicacion
import org.springframework.data.repository.CrudRepository
import org.springframework.data.jpa.repository.Query

interface UbicacionDAO : CrudRepository<Ubicacion, Long> {

    @Query("""
        select count(u)
        from Ubicacion u
    """)
    fun countByNombre(): Int

    @Query("select u.nombre from Ubicacion u where u.id = ?1")
    fun recuperarPorNombre(ubicacionId: Long?): String

    @Query("select u from Ubicacion u where u.nombre = ?1")
    fun recuperarPorNombreReal(ubicacion: String): Ubicacion

}