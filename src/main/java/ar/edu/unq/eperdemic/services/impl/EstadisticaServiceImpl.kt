package ar.edu.unq.eperdemic.services.impl

import ar.edu.unq.eperdemic.exceptions.ErrorValorDePaginacionIvalido
import ar.edu.unq.eperdemic.modelo.Direccion
import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.ReporteDeContagios
import ar.edu.unq.eperdemic.persistencia.dao.EstadisticaDAO
import ar.edu.unq.eperdemic.services.EstadisticaService
import ar.edu.unq.eperdemic.services.runner.HibernateTransactionRunner.runTrx
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class EstadisticaServiceImpl (
    private val estadisticaDAO : EstadisticaDAO
   ) : EstadisticaService {

    override fun especieLider(): Especie {
        return estadisticaDAO.lider()
    }

    override fun lideres(direccion: Direccion, pagina: Int, cantidadPorPagina: Int): List<Especie> {

        if (pagina == null || pagina < 0 || cantidadPorPagina < 0) {
            throw ErrorValorDePaginacionIvalido()
        }

        val pageable: Pageable = PageRequest.of(pagina, cantidadPorPagina)

        return estadisticaDAO.todosLosLideres(direccion.getExp(), pageable)
    }

    override fun reporteDeContagios(nombreDeLaUbicacion: String): ReporteDeContagios {
        return ReporteDeContagios(
                estadisticaDAO.cantidadDeVectoresEn(nombreDeLaUbicacion),
                estadisticaDAO.cantidadDeInfectadosEnUbicacion(nombreDeLaUbicacion),
                estadisticaDAO.especiePrevalente(nombreDeLaUbicacion).nombre!!
        )
    }

}