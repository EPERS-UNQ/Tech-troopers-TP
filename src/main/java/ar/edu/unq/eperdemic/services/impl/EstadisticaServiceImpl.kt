package ar.edu.unq.eperdemic.services.impl

import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.ReporteDeContagios
import ar.edu.unq.eperdemic.persistencia.dao.EspecieDAO
import ar.edu.unq.eperdemic.persistencia.dao.UbicacionDAO
import ar.edu.unq.eperdemic.persistencia.dao.VectorDAO
import ar.edu.unq.eperdemic.services.EstadisticaService
import ar.edu.unq.eperdemic.services.runner.HibernateTransactionRunner.runTrx

class EstadisticaServiceImpl (
    private val especieDAO   : EspecieDAO,
    private val ubicacionDAO : UbicacionDAO,
    private val vectorDAO    : VectorDAO
   ) : EstadisticaService {

    override fun especieLider(): Especie {
        return runTrx { especieDAO.lider() }
    }

    override fun lideres(): List<Especie> {
        return runTrx { especieDAO.todosLosLideres() }
    }

    override fun reporteDeContagios(nombreDeLaUbicacion: String): ReporteDeContagios {
        return runTrx {
            val idUbicacion = ubicacionDAO.recuperarPorNombre(nombreDeLaUbicacion).id
            val vectoresUbicados = vectorDAO.recuperarTodosDeUbicacion(idUbicacion!!)

            ReporteDeContagios(vectoresUbicados.size,
                               vectorDAO.cantidadDeInfectados(idUbicacion!!),
                               especieDAO.especiePrevalente(vectoresUbicados))
        }
    }

}