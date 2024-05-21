package ar.edu.unq.eperdemic.modelo.neo4j

import ar.edu.unq.eperdemic.modelo.Ubicacion
import ar.edu.unq.eperdemic.modelo.neo4j.UbicacionNeo4j
import ar.edu.unq.eperdemic.modelo.neo4j.camino.TipoDeCamino
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.data.neo4j.repository.query.Query
import org.springframework.stereotype.Repository

@Repository
interface Neo4jUbicacionDAO : Neo4jRepository<UbicacionNeo4j, Long> {

    @Query(
        """
            MATCH (u1:UbicacionNeo4j {nombre: ${'$'}nombreDeUbicacion1})
            MATCH (u2:UbicacionNeo4j {nombre: ${'$'}nombreDeUbicacion2})
            CREATE (u1) -[:Camino {tipo: ${'$'}tipoCamino}]-> (u2)
        """
    )
    fun conectarCaminos(nombreDeUbicacion1: String, nombreDeUbicacion2: String, tipoCamino: String)

    @Query(
        """
            MATCH (u1:UbicacionNeo4j)
            MATCH (u2:UbicacionNeo4j {nombre: ${'$'}nombreDeUbicacion})
            MATCH (u2)-[CAMINO]->(u1)
            RETURN u1
        """
    )
    fun ubicacionesConectadas(nombreDeUbicacion:String): List<UbicacionNeo4j>

    @Query(
        """
            MATCH (n:Ubicacion {nombre: ${'$'}nomUbiInicio}), (m:Ubicacion {nombre: ${'$'}nomUbiFin})
            RETURN exists((n)-[:Camino*]->(m)) AS estan_conectados
        """
    )
    fun esUbicacionCercana(nomUbiInicio: String, nomUbiFin: String): Boolean

    @Query(
        """
            MATCH (n:Ubicacion {nombre: ${'$'}nomUbiInicio}), (m:Ubicacion {nombre: ${'$'}nomUbiFin})
            MATCH path = (n)-[:Camino*]->(m)
            WHERE ALL(rel IN relationships(path) WHERE rel.tipo IN ${'$'}tiposPermitidos)
            RETURN EXISTS((n)-[:Camino*]->(m)) AS existeCamino
        """
    )
    fun esUbicacionAlcanzable(nomUbiInicio: String, nomUbiFin: String, tiposPermitidos: List<String>): Boolean

    @Query(
        """
            MATCH (n:Ubicacion {nombre: ${'$'}nomUbiInicio}), (m:Ubicacion {nombre: ${'$'}nomUbiFin})
            MATCH path = (n)-[:Camino*]->(m)
            WHERE ALL(rel IN relationships(path) WHERE rel.tipo IN ${'$'}tiposPermitidos)
            RETURN [node IN nodes(path) | node.name] AS nombres_ubicaciones
            ORDER BY nombres_ubicaciones
            LIMIT 1
        """
    )
    fun caminoIdeal(nomUbiInicio: String, nomUbiFin: String, tiposPermitidos: List<String>): List<String>

}

