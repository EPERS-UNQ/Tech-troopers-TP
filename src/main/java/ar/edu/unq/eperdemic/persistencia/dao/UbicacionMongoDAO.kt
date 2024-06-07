package ar.edu.unq.eperdemic.persistencia.dao

import ar.edu.unq.eperdemic.modelo.UbicacionMongo
import org.springframework.data.mongodb.repository.MongoRepository

interface UbicacionMongoDAO : MongoRepository<UbicacionMongo, String> {
}