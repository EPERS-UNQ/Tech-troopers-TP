package ar.edu.unq.eperdemic.services.impl

import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.persistencia.dao.EspecieDAO
import ar.edu.unq.eperdemic.services.EspecieService
import ar.edu.unq.eperdemic.services.runner.HibernateTransactionRunner.runTrx

class EspecieServiceImpl (
    private val especieDAO : EspecieDAO
    ) : EspecieService {

    override fun updatear(especie: Especie) {
        runTrx { especieDAO.actualizar(especie) }
    }

    override fun recuperar(especieID: Long): Especie {
        return runTrx { especieDAO.recuperar(especieID) }
    }

    override fun recuperarTodos(): List<Especie> {
        return runTrx { especieDAO.recuperarTodos() }
    }

    override fun cantidadDeInfectados(especieId: Long): Int {
        return runTrx {
            especieDAO.recuperar(especieId).vectores.size
        }
    }


}