package ar.edu.unq.eperdemic.services.impl

import ar.edu.unq.eperdemic.modelo.Vector
import ar.edu.unq.eperdemic.persistencia.dao.VectorDAO
import ar.edu.unq.eperdemic.services.VectorService
import ar.edu.unq.eperdemic.services.runner.HibernateTransactionRunner.runTrx

class VectorServiceImp (
    private val VectorDAO: VectorDAO
) : VectorService {
    override fun crear(vector: Vector): Vector {
        return runTrx { VectorDAO.crear(vector) }
    }


}