package ar.edu.unq.eperdemic.persistencia.dao

import ar.edu.unq.eperdemic.modelo.Ubicacion
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.data.neo4j.repository.query.Query
import org.springframework.stereotype.Repository

@Repository
interface Neo4jUbicacionDAO : Neo4jRepository<Ubicacion, Long> {

    @Query(
        """
            MERGE (u1:Ubicacion {nombre: ${'$'} nombreDeUbicacion1})
            MERGE (u2:Ubicacion {nombre: ${'$'} nombreDeUbicacion2})
            MERGE (u1) - [:Camino {tipo: ${'$'} tipoCamino}] -> (u2)
        """
    )
    fun conectarCaminos(nombreDeUbicacion1: String, nombreDeUbicacion2: String, tipoCamino: String)

    @Query(
        """
            MATCH(c:Camino)
            MATCH(u:Ubicacion {nombre: ${'$'}}nombreDeUbicacion)
            MATCH (u)-[CAMINO]-(c)
            RETURN c
        """
    )
    fun caminosDesde(nombreDeUbicacion:String): List<Ubicacion>

}
