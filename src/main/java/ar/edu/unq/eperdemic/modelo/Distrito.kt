package ar.edu.unq.eperdemic.modelo

import ar.edu.unq.eperdemic.controller.dto.DistritoDTO
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.annotation.Id

@Document
class Distrito(
    private val nombre: String,
    private val coordenadas: HashSet<Coordenada>
) {
    @Id
    private val id: String? = null

    var ubicaciones: MutableSet<UbicacionMongo> = HashSet()

    fun aDTO() : DistritoDTO {
        val coordenadasDTO = this.coordenadas.map { coordenada -> coordenada.aDTO() }.toMutableSet()
        return DistritoDTO(this.id, this.nombre, coordenadasDTO)
    }

}
