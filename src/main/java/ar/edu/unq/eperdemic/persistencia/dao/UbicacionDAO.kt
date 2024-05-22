package ar.edu.unq.eperdemic.persistencia.dao

import ar.edu.unq.eperdemic.modelo.UbicacionJpa
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.data.jpa.repository.Query

interface UbicacionDAO : JpaRepository<UbicacionJpa, Long> {

    @Query("""
        select count(u)
        from UbicacionJpa u
    """)
    fun countByNombre(): Int

    @Query("select u.nombre from UbicacionJpa u where u.id = ?1")
    fun recuperarPorNombre(ubicacionId: Long?): String

    @Query("select u from UbicacionJpa u where u.nombre = ?1")
    fun recuperarPorNombreReal(ubicacion: String): UbicacionJpa

}