package ar.edu.unq.eperdemic.modelo

import org.springframework.data.mongodb.core.mapping.Document

@Document
class Distrito {

    var ubicaciones: MutableSet<UbicacionMongo> = HashSet()

}
