package ar.edu.unq.eperdemic.modelo.ubicacion.camino

import ar.edu.unq.eperdemic.modelo.ubicacion.UbicacionJpa
import org.springframework.data.neo4j.core.schema.RelationshipId
import org.springframework.data.neo4j.core.schema.RelationshipProperties
import org.springframework.data.neo4j.core.schema.TargetNode

@RelationshipProperties
class Camino() {

    @RelationshipId
    private var id: Long? = null

    var tipo: TipoDeCamino? = null

    @TargetNode
    var ubicacion: UbicacionJpa? = null

}