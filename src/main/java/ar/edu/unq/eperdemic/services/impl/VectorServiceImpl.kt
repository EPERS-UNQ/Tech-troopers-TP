package ar.edu.unq.eperdemic.services.impl

import ar.edu.unq.eperdemic.exceptions.NoExisteElVector
import ar.edu.unq.eperdemic.exceptions.NoExisteLaEspecie
import ar.edu.unq.eperdemic.exceptions.NoExisteLaUbicacion
import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.ubicacion.UbicacionElastic
import ar.edu.unq.eperdemic.modelo.ubicacion.UbicacionGlobal
import ar.edu.unq.eperdemic.modelo.ubicacion.UbicacionMongo
import ar.edu.unq.eperdemic.modelo.vector.Vector
import ar.edu.unq.eperdemic.modelo.vector.VectorElastic
import ar.edu.unq.eperdemic.modelo.vector.VectorGlobal
import ar.edu.unq.eperdemic.persistencia.dao.EspecieDAO
import ar.edu.unq.eperdemic.persistencia.dao.UbicacionJpaDAO
import ar.edu.unq.eperdemic.persistencia.dao.VectorElasticDAO
import ar.edu.unq.eperdemic.persistencia.dao.VectorJpaDAO
import ar.edu.unq.eperdemic.services.VectorService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class VectorServiceImpl () : VectorService {

    @Autowired private lateinit var especieDAO: EspecieDAO
    @Autowired private lateinit var vectorJpaDAO: VectorJpaDAO
    @Autowired private lateinit var vectorElasticDAO: VectorElasticDAO
    @Autowired private lateinit var ubicacionJpaDAO: UbicacionJpaDAO

    override fun crear(vectorGlobal: VectorGlobal): Vector {
        val ubicacion = vectorGlobal.ubicacion
        val vectorJpa = vectorGlobal.aJPA()

        if ( ubicacion != null ) {
            val ubicacionPersistida = ubicacionJpaDAO.recuperarPorNombreReal(ubicacion.getNombre())
            if ( ubicacionPersistida != null ) {
                vectorJpa.ubicacion = ubicacionPersistida
            } else {
                throw NoExisteLaUbicacion()
            }
        }
        vectorElasticDAO.save(vectorGlobal.aElastic())
        return vectorJpaDAO.save(vectorJpa)
    }

    override fun updatear(vector: VectorGlobal) {
        vectorJpaDAO.save(vector.aJPA())
        vectorElasticDAO.save(vector.aElastic())
    }

    override fun recuperar(idVector: Long): Vector {

        val vector = vectorJpaDAO.findByIdOrNull(idVector)
        if (vector == null) {
            throw NoExisteElVector()

        }
        return vector
    }

    override fun recuperarTodos(): List<Vector> {
        return vectorJpaDAO.findAll().toList()
    }

    override fun recuperarTodosElastic(): List<VectorElastic> {
        return vectorElasticDAO.findAll().toList()
    }

    override fun infectar(vectorId: Long, especieId: Long) {

        val especie = especieDAO.findById(especieId).orElse(null)
        val vector  = vectorJpaDAO.findById(vectorId).orElse(null)
        val vectorElastic = vectorElasticDAO.findByIdOrNull(vectorId)
        if      ( especie == null ) { NoExisteLaEspecie() }
        else if ( vector == null )  { NoExisteElVector()  }
        vector.infectar(especie)
        vectorJpaDAO.save(vector)
        vectorElasticDAO.save(vectorElastic!!)
        especieDAO.save(especie)

    }

    override fun enfermedades(vectorId: Long): List<Especie> {
        return (vectorJpaDAO.findByIdOrNull(vectorId)!!).enfermedadesDelVector()
    }

    override fun ubicacionesInfectadasCercanas(ubicacionActual: UbicacionGlobal): List<UbicacionElastic> {
        TODO("Not yet implemented")
    }

    override fun deleteAll() {
        vectorElasticDAO.deleteAll()
    }

}