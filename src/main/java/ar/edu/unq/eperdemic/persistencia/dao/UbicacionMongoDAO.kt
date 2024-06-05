package ar.edu.unq.eperdemic.persistencia.dao

import ar.edu.unq.eperdemic.modelo.UbicacionMongo
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface UbicacionMongoDAO : MongoRepository<UbicacionMongo, String> {

    @Query("{nombre: '?0'}")
    fun findByNombre(nombre: String): UbicacionMongo?

    //@Query("""  """)
    //fun estaADistanciaAlcanzable(nomUbiInicio: String, nomUbiFin: String): Boolean

    //@Query("{nombre: '?0'}", fields = "{coordenada: 1}")
    //fun findCoordenadaByNombre(nombre: String): Coordenada?
}
