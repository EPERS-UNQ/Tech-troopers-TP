package ar.edu.unq.eperdemic.services.impl

import ar.edu.unq.eperdemic.modelo.Vector
import ar.edu.unq.eperdemic.persistencia.dao.VectorDAO
import ar.edu.unq.eperdemic.services.VectorService
import ar.edu.unq.eperdemic.services.runner.HibernateTransactionRunner.runTrx

class VectorServiceImp (
    private val vectorDAO: VectorDAO
) : VectorService {
    override fun crear(vector: Vector): Vector {
        return runTrx { vectorDAO.crear(vector) }
    }

    override fun updatear(vector: Vector) {
        return runTrx { vectorDAO.actualizar(vector) }
    }

    override fun recuperar(vectorId: Long): Vector {
        return runTrx { vectorDAO.recuperar(vectorId) }
    }

    override fun recuperarTodos(): List<Vector> {
        return runTrx { vectorDAO.recuperarTodos() }
    }

}