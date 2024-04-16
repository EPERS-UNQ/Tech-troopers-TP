package ar.edu.unq.eperdemic.services.impl

import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.persistencia.dao.EspecieDAO
import ar.edu.unq.eperdemic.services.EstadisticaService
import ar.edu.unq.eperdemic.services.runner.HibernateTransactionRunner.runTrx

class EstadisticaServiceImpl (
    private val especieDAO : EspecieDAO
   ) : EstadisticaService {
    override fun especieLider(): Especie {
        return runTrx { especieDAO.lider() }
    }

    override fun lideres(): List<Especie> {
        TODO("Not yet implemented")
    }

}