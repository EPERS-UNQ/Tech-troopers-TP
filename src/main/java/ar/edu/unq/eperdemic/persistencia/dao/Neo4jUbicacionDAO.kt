package ar.edu.unq.eperdemic.persistencia.dao

import ar.edu.unq.eperdemic.modelo.Ubicacion
import ar.edu.unq.eperdemic.modelo.camino.TipoDeCamino
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
    fun conectarCaminos(nombreDeUbicacion1: String, nombreDeUbicacion2: String, tipoCamino: TipoDeCamino)

    @Query(
        """
            MATCH (u1:Ubicacion)
            MATCH (u2:Ubicacion {nombre: ${'$'}} nombreDeUbicacion)
            MATCH (u2)-[CAMINO]-(u1)
            RETURN u1
        """
    )
    fun caminosDesde(nombreDeUbicacion:String): List<Ubicacion>

}
