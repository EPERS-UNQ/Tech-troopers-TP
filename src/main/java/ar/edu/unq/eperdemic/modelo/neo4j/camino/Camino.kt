package ar.edu.unq.eperdemic.modelo.neo4j.camino

import ar.edu.unq.eperdemic.exceptions.ErrorTipoCaminoInvalido
import ar.edu.unq.eperdemic.modelo.UbicacionJpa
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

    fun Camino(tipo: TipoDeCamino) {
        this.tipo = tipo;
    }

    fun convertirACamino(camino: String): TipoDeCamino {
        return when (camino.lowercase()) {
            "terrestre" -> TipoDeCamino.TERRESTRE
            "maritimo"  -> TipoDeCamino.MARITIMO
            "aereo"     -> TipoDeCamino.AEREO
            else -> { throw ErrorTipoCaminoInvalido("El tipo de camino es invalido.") }
        }
    }
}