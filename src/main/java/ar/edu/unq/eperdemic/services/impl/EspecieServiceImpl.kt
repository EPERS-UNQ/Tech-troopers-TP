package ar.edu.unq.eperdemic.services.impl

import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.persistencia.dao.jdbc.HibernateEspecieDAO
import ar.edu.unq.eperdemic.services.EspecieService
import ar.edu.unq.eperdemic.services.runner.HibernateTransactionRunner.runTrx

class EspecieServiceImpl (
    private val especieDAO: HibernateEspecieDAO
    ) : EspecieService {

    override fun crear(especie: Especie) {
        runTrx { especieDAO.crear(especie) }
    }

    override fun updatear(especie: Especie) {
        runTrx { especieDAO.actualizar(especie) }
    }

    override fun recuperar(especieID: Long): Especie {
        return runTrx { especieDAO.recuperar(especieID) }
    }

    override fun recuperarTodos(): MutableList<Especie> {
        return runTrx { especieDAO.recuperarTodos() }
    }

    override fun cantidadDeInfectados(especieId: Long): Int {
        TODO("Not yet implemented")
    }


}