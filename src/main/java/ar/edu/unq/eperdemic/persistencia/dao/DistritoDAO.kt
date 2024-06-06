package ar.edu.unq.eperdemic.persistencia.dao

import ar.edu.unq.eperdemic.modelo.Distrito
import ar.edu.unq.eperdemic.modelo.UbicacionMongo
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface DistritoDAO : MongoRepository<Distrito, String> {

    @Query("{nombre: '?0'}")
    fun findByNombre(nombre: String): Distrito?

}
