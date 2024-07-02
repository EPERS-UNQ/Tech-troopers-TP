package ar.edu.unq.eperdemic.services.impl

import ar.edu.unq.eperdemic.exceptions.NoExisteElVector
import ar.edu.unq.eperdemic.exceptions.NoExisteLaEspecie
import ar.edu.unq.eperdemic.exceptions.NoExisteLaUbicacion
import ar.edu.unq.eperdemic.modelo.Especie
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

    override fun crear(vectorGlobal: VectorGlobal): VectorGlobal {
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
        val vectorElastisPersistido = vectorElasticDAO.save(vectorGlobal.aElastic())
        val vectorJpaPersistido     = vectorJpaDAO.save(vectorJpa)
        vectorGlobal.setId(vectorJpaPersistido.getId())
        vectorGlobal.idElastic = vectorElastisPersistido.id
        return vectorGlobal
    }

    override fun updatear(vectorGlobal: VectorGlobal) {
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
        val vectorElastic = vectorGlobal.aElastic()
        vectorElastic.id = vectorGlobal.idElastic
        vectorElasticDAO.save(vectorElastic)
        vectorJpa.setId(vectorGlobal.getId())
        vectorJpaDAO.save(vectorJpa)
    }

    override fun recuperar(vectorId: Long): Vector {

        val vector = vectorJpaDAO.findByIdOrNull(vectorId)
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
        if      ( especie == null ) { NoExisteLaEspecie() }
        else if ( vector == null )  { NoExisteElVector()  }
        vector.infectar(especie)
        vectorJpaDAO.save(vector)
        especieDAO.save(especie)

        // la logica de si es la especie nueva

    }

    override fun enfermedades(vectorId: Long): List<Especie> {
        return (vectorJpaDAO.findByIdOrNull(vectorId)!!).enfermedadesDelVector()
    }

    override fun deleteAll() {
        vectorElasticDAO.deleteAll()
    }

}