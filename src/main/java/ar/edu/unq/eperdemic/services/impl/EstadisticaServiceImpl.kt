package ar.edu.unq.eperdemic.services.impl

import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.ReporteDeContagios
import ar.edu.unq.eperdemic.persistencia.dao.EstadisticaDAO
import ar.edu.unq.eperdemic.services.EstadisticaService
import ar.edu.unq.eperdemic.services.runner.HibernateTransactionRunner.runTrx

class EstadisticaServiceImpl (
    private val estadisticaDAO : EstadisticaDAO
   ) : EstadisticaService {

    override fun especieLider(): Especie {
        return runTrx { estadisticaDAO.lider() }
    }

    override fun lideres(): List<Especie> {
        return runTrx { estadisticaDAO.todosLosLideres() }
    }

    override fun reporteDeContagios(nombreDeLaUbicacion: String): ReporteDeContagios {
        return runTrx { estadisticaDAO.reporteContagios(nombreDeLaUbicacion)  }
    }

}