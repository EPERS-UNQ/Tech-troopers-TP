package ar.edu.unq.eperdemic.modelo.camino

import ar.edu.unq.eperdemic.exceptions.ErrorTipoCaminoInvalido
import ar.edu.unq.eperdemic.modelo.Ubicacion
import org.springframework.data.neo4j.core.schema.RelationshipId
import org.springframework.data.neo4j.core.schema.RelationshipProperties
import org.springframework.data.neo4j.core.schema.TargetNode

@RelationshipProperties
class Camino() {

    @RelationshipId
    private var id: Long? = null

    var tipo: TipoDeCamino? = null // <- Tiene que ser de tipo TipoDeCamino pero lo pongo como String por la consulta en UbicacionDAO.

    @TargetNode
    var ubicacion: Ubicacion? = null

    fun convertirACamino(camino: String): TipoDeCamino {
        return when (camino.lowercase()) {
            "terrestre" -> TipoDeCamino.TERRESTRE
            "maritimo"  -> TipoDeCamino.MARITIMO
            "aereo"     -> TipoDeCamino.AEREO
            else -> { throw ErrorTipoCaminoInvalido("El tipo de camino es invalido.") }
        }
    }

}
