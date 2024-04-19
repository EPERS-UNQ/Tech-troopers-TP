package ar.edu.unq.eperdemic.services.impl

import ar.edu.unq.eperdemic.exceptions.NoExisteElVector
import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.vector.Vector
import ar.edu.unq.eperdemic.persistencia.dao.EspecieDAO
import ar.edu.unq.eperdemic.persistencia.dao.VectorDAO
import ar.edu.unq.eperdemic.services.VectorService
import ar.edu.unq.eperdemic.services.runner.HibernateTransactionRunner.runTrx

class VectorServiceImp (
    private val vectorDAO: VectorDAO,
    private val especieDAO: EspecieDAO
) : VectorService {
    override fun crear(vector: Vector): Vector {
        return runTrx { vectorDAO.crear(vector) }
    }

    override fun updatear(vector: Vector) {
        return runTrx { vectorDAO.actualizar(vector) }
    }

    override fun recuperar(idVector: Long): Vector {
        return runTrx {
            val vector = vectorDAO.recuperar(idVector)
            if (vector == null) {
                throw NoExisteElVector()
            }
            vector
        }
    }

    override fun recuperarTodos(): List<Vector> {
        return runTrx { vectorDAO.recuperarTodos() }
    }

    override fun infectar(vectorId: Long, especieId: Long) {
        runTrx {
            val especie = especieDAO.recuperar(especieId)
            val vector  = vectorDAO.recuperar(vectorId)
            vector.infectar(especie)
            vectorDAO.actualizar(vector)
            especieDAO.actualizar(especie)
        }
    }

    override fun enfermedades(vectorId: Long): List<Especie> {
        return runTrx { vectorDAO.enfermedades(vectorDAO.recuperar(vectorId)) }
    }
}