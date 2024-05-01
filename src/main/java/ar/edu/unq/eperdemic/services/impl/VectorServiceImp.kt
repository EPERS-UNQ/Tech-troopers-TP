package ar.edu.unq.eperdemic.services.impl

import ar.edu.unq.eperdemic.exceptions.NoExisteElVector
import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.vector.Vector
import ar.edu.unq.eperdemic.persistencia.dao.EspecieDAO
import ar.edu.unq.eperdemic.persistencia.dao.UbicacionDAO
import ar.edu.unq.eperdemic.persistencia.dao.VectorDAO
import ar.edu.unq.eperdemic.services.VectorService
import ar.edu.unq.eperdemic.services.runner.HibernateTransactionRunner.runTrx
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class VectorServiceImp () : VectorService {

    @Autowired private lateinit var especieDAO: EspecieDAO
    @Autowired private lateinit var vectorDAO: VectorDAO

    override fun crear(vector: Vector): Vector {
        return vectorDAO.save(vector)
    }

    override fun updatear(vector: Vector) {
        vectorDAO.save(vector)
    }

    override fun recuperar(idVector: Long): Vector {
        val vector = vectorDAO.findByIdOrNull(idVector)!!
        if (vector == null) {
            throw NoExisteElVector()
        }
        return vector
    }

    override fun recuperarTodos(): List<Vector> {
        return vectorDAO.findAll().toList()
    }

    override fun infectar(vectorId: Long, especieId: Long) {
        TODO("Not yet implemented")
    }

    override fun enfermedades(vectorId: Long): List<Especie> {
        return (vectorDAO.findByIdOrNull(vectorId)!!).enfermedadesDelVector()
    }
    /*
    override fun infectar(vectorId: Long, especieId: Long) {
        runTrx {
            val especie = especieDAO.recuperar(especieId)
            val vector  = vectorDAO.recuperar(vectorId)
            vector.infectar(especie)
            vectorDAO.actualizar(vector)
            especieDAO.actualizar(especie)
        }
    }
    */
}