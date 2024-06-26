package ar.edu.unq.eperdemic.persistencia.dao

import ar.edu.unq.eperdemic.modelo.ubicacion.UbicacionJpa
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UbicacionJpaDAO : JpaRepository<UbicacionJpa, Long> {

    @Query("""
        select count(u)
        from UbicacionJpa u
    """)
    fun countUbicaciones(): Int

    @Query("select u.nombre from UbicacionJpa u where u.id = ?1")
    fun recuperarNombrePorID(ubicacionId: Long?): String

    @Query("select u from UbicacionJpa u where u.nombre = ?1")
    fun recuperarPorNombreReal(ubicacion: String): UbicacionJpa?

    @Query("""
         SELECT DISTINCT u.nombre
         FROM UbicacionJpa u
         JOIN Vector v ON u.id = v.ubicacion.id
         WHERE v.estaInfectado = TRUE
        """)
    fun ubicacionesInfectadas(): List<String>

}