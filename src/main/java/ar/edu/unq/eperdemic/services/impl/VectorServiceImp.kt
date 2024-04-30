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
        return runTrx {
            val vector = vectorDAO.findById(idVector).orElse(null)
            if (vector == null) {
                throw NoExisteElVector()
            }
            vector
        }
    }

    override fun recuperarTodos(): List<Vector> {
        return vectorDAO.recuperarTodos()
    }

    override fun infectar(vectorId: Long, especieId: Long) {
        runTrx {
            val especie = especieDAO.findById(especieId).orElse(null)
            val vector  = vectorDAO.findById(vectorId).orElse(null)
            vector.infectar(especie)
            vectorDAO.save(vector)
            especieDAO.save(especie)
        }
    }

    override fun enfermedades(vectorId: Long): List<Especie> {
        var vector = vectorDAO.findById(vectorId).orElse(null)
        return vector.enfermedadesDelVector()
    }
}