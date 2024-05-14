package ar.edu.unq.eperdemic.modelo

import org.springframework.data.neo4j.core.schema.RelationshipId
import org.springframework.data.neo4j.core.schema.RelationshipProperties
import org.springframework.data.neo4j.core.schema.TargetNode

@RelationshipProperties
class Camino {

    @RelationshipId
    private var id: Long? = null

    var tipo: String? = null // <- Tiene que ser de tipo TipoDeCamino pero lo pongo como String por la consulta en UbicacionDAO.

    @TargetNode
    var ubicacion: Ubicacion? = null

}
