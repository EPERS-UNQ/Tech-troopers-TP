package ar.edu.unq.eperdemic.persistencia.dao

import ar.edu.unq.eperdemic.modelo.Coordenada
import ar.edu.unq.eperdemic.modelo.UbicacionMongo
import org.springframework.data.mongodb.repository.Aggregation
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface UbicacionMongoDAO : MongoRepository<UbicacionMongo, String> {

    @Query("{nombre: '?0'}")
    fun findByNombre(nombre: String): UbicacionMongo

    @Query("{nombre: '?0'}", fields = "{coordenada: 1}")
    fun findCoordenadaByNombre(nombre: String): Coordenada?
}
