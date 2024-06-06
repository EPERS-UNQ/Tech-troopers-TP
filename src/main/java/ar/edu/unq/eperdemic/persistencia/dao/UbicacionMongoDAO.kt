package ar.edu.unq.eperdemic.persistencia.dao

import ar.edu.unq.eperdemic.modelo.ubicacion.UbicacionMongo
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.data.mongodb.repository.Aggregation
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface UbicacionMongoDAO : MongoRepository<UbicacionMongo, String> {

    @Query("{nombre: '?0'}")
    fun findByNombre(nombre: String): UbicacionMongo?

    @Aggregation(
        pipeline = [
            "{ '\$geoNear': { 'near': ?0, 'distanceField': 'distance_calc', 'maxDistance': 100000, 'spherical': true } }",
            "{ '\$match': { 'nombre': ?1 } }",
        ]
    )
    fun ubicacionesAMenosDe100Km(puntoSalida: GeoJsonPoint, nombreUbicacionDestino: String): List<UbicacionMongo>
}
