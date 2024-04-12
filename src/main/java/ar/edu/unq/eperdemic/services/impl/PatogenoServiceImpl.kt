package ar.edu.unq.eperdemic.services.impl

import ar.edu.unq.eperdemic.modelo.Patogeno
import ar.edu.unq.eperdemic.persistencia.dao.PatogenoDAO
import ar.edu.unq.eperdemic.persistencia.dao.hibernate.HibernatePatogenoDAO
import ar.edu.unq.eperdemic.services.PatogenoService
import ar.edu.unq.eperdemic.services.runner.HibernateTransactionRunner.runTrx


class PatogenoServiceImpl(val patogenoDAO: PatogenoDAO) : PatogenoService {

    private val dao: PatogenoDAO = HibernatePatogenoDAO()

    override fun crearPatogeno(patogeno: Patogeno): Patogeno {
        return runTrx { dao.crear(patogeno) }
    }

    override fun updatearPatogeno(patogeno: Patogeno) {
        runTrx { dao.actualizar(patogeno) }
    }

    override fun recuperarPatogeno(id: Long): Patogeno {
        return runTrx { dao.recuperar(id) }
    }

    override fun recuperarATodosLosPatogenos(): List<Patogeno> {
        return runTrx { dao.recuperarATodos() }
    }

    override fun agregarEspecie(idDePatogeno: Long, nombreEspecie: String, paisDeOrigen: String): Especie {
        /*return runTrx {
            val patogeno: Patogeno = dao.recuperar(idDePatogeno)
            val especie: Especie = patogeno.crearEspecie(nombreEspecie, paisDeOrigen)
            dao.actualizar(patogeno)
        }
        */
        TODO("Not yet implemented")
    }

    override fun especiesDePatogeno(patogenoId: Long): List<Especie> {
        TODO("Not yet implemented")
    }

    override fun esPademia(especieId: Long): Boolean {
        TODO("Not yet implemented")
    }

}