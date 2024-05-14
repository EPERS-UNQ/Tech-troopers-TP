package ar.edu.unq.eperdemic.persistencia.dao

import ar.edu.unq.eperdemic.modelo.Ubicacion
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface UbicacionDAO : CrudRepository<Ubicacion, Long> {


    @Query("""
        select count(u)
        from Ubicacion u
    """)
    fun countByNombre(): Int

    @Query("select u.nombre from Ubicacion u where u.id = ?1")
    fun recuperarPorNombre(ubicacionId: Long?): String

    @Query(
        """
            MERGE (u1:Ubicacion {nombre: ${'$'}} nombreDeUbicacion1)
            MERGE (u2:Ubicacion {nombre: ${'$'}} nombreDeUbicacion2)
            MERGE (u1) - [:Camino {tipo: ${'$'} tipoCamino}] -> (u2)
        """
    )
    fun conectarCaminos(nombreDeUbicacion1: String, nombreDeUbicacion2: String, tipoCamino: String)

}