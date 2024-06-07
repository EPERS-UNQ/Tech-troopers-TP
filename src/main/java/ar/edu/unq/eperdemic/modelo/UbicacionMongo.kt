package ar.edu.unq.eperdemic.modelo

import org.springframework.data.mongodb.core.mapping.Document
import javax.persistence.Id

@Document("Ubicacion")
class UbicacionMongo() {

    @Id
    private var id: String? = null

}
