package ar.edu.unq.eperdemic.persistencia.dao

import ar.edu.unq.eperdemic.modelo.neo4j.UbicacionNeo4j
import org.springframework.data.neo4j.repository.Neo4jRepository
import org.springframework.data.neo4j.repository.query.Query
import org.springframework.stereotype.Repository

@Repository
interface UbicacionNeo4jDAO : Neo4jRepository<UbicacionNeo4j, Long> {

    @Query(
        """
            MATCH (u1:UbicacionNeo4j {nombre: ${'$'}nombreDeUbicacion1})
            MATCH (u2:UbicacionNeo4j {nombre: ${'$'}nombreDeUbicacion2})
            CREATE (u1) -[:Camino {tipo: ${'$'}tipoCamino, largo: ${'$'}largo}]-> (u2)
        """
    )
    fun conectarCaminos(nombreDeUbicacion1: String, nombreDeUbicacion2: String, tipoCamino: String, largo: Int)

    @Query(
        """
            MATCH (u1:UbicacionNeo4j)
            MATCH (u2:UbicacionNeo4j {nombre: ${'$'}nombreDeUbicacion})
            MATCH (u2)-[:Camino*]->(u1)
            RETURN DISTINCT u1
        """
    )
    fun ubicacionesConectadas(nombreDeUbicacion:String): List<UbicacionNeo4j>

    @Query(
        """
            MATCH (u1:UbicacionNeo4j {nombre: ${'$'}nomUbiInicio}), (u2:UbicacionNeo4j {nombre: ${'$'}nomUbiFin})
            MATCH path = (u1)-[:Camino*]->(u2)
            WITH path, relationships(path) AS rels
            WHERE ALL(rel IN rels WHERE reduce(s = 0, rel IN rels | s + rel.largo) <= ${'$'}distancia)
            RETURN COUNT(path) > 0 AS existeCamino
        """
    )
    fun esUbicacionCercana(nomUbiInicio: String, nomUbiFin: String, distancia: Int): Boolean

    @Query(
        """
            MATCH (u1:UbicacionNeo4j {nombre: ${'$'}nomUbiInicio}), (u2:UbicacionNeo4j {nombre: ${'$'}nomUbiFin})
            RETURN exists((u1)-[:Camino]->(u2)) AS estan_conectados
        """
    )
    fun esUbicacionLindante(nomUbiInicio: String, nomUbiFin: String): Boolean

    @Query(
        """
            MATCH (n:UbicacionNeo4j {nombre: ${'$'}nomUbiInicio})
            MATCH (m:UbicacionNeo4j {nombre: ${'$'}nomUbiFin})
            MATCH path = (n)-[c:Camino]->(m)
            WHERE ALL (rel IN relationships(path) WHERE rel.tipo IN ${'$'}tiposPermitidos) 
            RETURN COUNT(path) > 0 AS existeCamino
        """
    )
    fun hayCaminoCruzable(nomUbiInicio: String, nomUbiFin: String, tiposPermitidos: List<String>): Boolean


    @Query(
        """
            MATCH (n:UbicacionNeo4j {nombre: ${'$'}nomUbiInicio})
            OPTIONAL MATCH (m:UbicacionNeo4j {nombre:${'$'}nomUbiFin})
            WITH n, m
            OPTIONAL MATCH path = shortestPath((n)-[:Camino*]->(m))
            WHERE path IS NOT NULL AND ALL(rel IN relationships(path) WHERE rel.tipo IN ${'$'}tiposPermitidos)
            RETURN CASE WHEN path IS NOT NULL THEN true ELSE false END AS existeCamino
        """
    )
    fun esUbicacionAlcanzable(nomUbiInicio: String, nomUbiFin: String, tiposPermitidos: List<String>): Boolean

    @Query(
        """
            MATCH (n:UbicacionNeo4j {nombre: ${'$'}nomUbiInicio}), (m:UbicacionNeo4j {nombre: ${'$'}nomUbiFin})
            MATCH path = shortestPath((n)-[:Camino*]->(m))
            WHERE ALL(rel IN relationships(path) WHERE rel.tipo IN ${'$'}tiposPermitidos)
            RETURN [node IN nodes(path)] AS nombres_ubicaciones
            LIMIT 1
        """
    )
    fun caminoIdeal(nomUbiInicio: String, nomUbiFin: String, tiposPermitidos: List<String>): List<UbicacionNeo4j>

    @Query("MATCH(n) DETACH DELETE n")
    fun detachDelete()

}

