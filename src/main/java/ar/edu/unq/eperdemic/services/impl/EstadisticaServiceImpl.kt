package ar.edu.unq.eperdemic.services.impl

import ar.edu.unq.eperdemic.exceptions.ErrorDireccionInvalida
import ar.edu.unq.eperdemic.exceptions.ErrorValorDePaginacionIvalido
import ar.edu.unq.eperdemic.modelo.Direccion
import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.ReporteDeContagios
import ar.edu.unq.eperdemic.modelo.vector.TipoVector
import ar.edu.unq.eperdemic.persistencia.dao.EstadisticaDAO
import ar.edu.unq.eperdemic.services.EstadisticaService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class EstadisticaServiceImpl ( ) : EstadisticaService {
    @Autowired private lateinit var estadisticaDAO : EstadisticaDAO

    override fun especieLider(): Especie {
        return estadisticaDAO.findTopByVectoresTipoOrderByVectoresDesc(TipoVector.HUMANO)
    }

    override fun lideres(direccion: Direccion, pagina: Int, cantidadPorPagina: Int): List<Especie> {

        var listaDeLideres : List<Especie>

        if (pagina == null || pagina < 0 || cantidadPorPagina < 0) {
            throw ErrorValorDePaginacionIvalido()
        }

        val pageable: Pageable = PageRequest.of(pagina, cantidadPorPagina)

        if ( direccion == Direccion.DESCENDENTE ) {
            listaDeLideres = estadisticaDAO.todosLosLideresDesc(pageable)
        } else if ( direccion == Direccion.ASCENDENTE ) {
            listaDeLideres = estadisticaDAO.todosLosLideresAsc(pageable)
        } else { throw  ErrorDireccionInvalida("La dirección es inválida") }

        return listaDeLideres
    }

    override fun reporteDeContagios(nombreDeLaUbicacion: String): ReporteDeContagios {
        val pageable: Pageable = PageRequest.of(0, 1)

        return ReporteDeContagios(
                estadisticaDAO.cantidadDeVectoresEn(nombreDeLaUbicacion),
                estadisticaDAO.cantidadDeInfectadosEnUbicacion(nombreDeLaUbicacion),
                estadisticaDAO.findTopEspeciePrevalente(nombreDeLaUbicacion, pageable).firstOrNull() ?: "No existe una Especie prevalente."
        )
    }

}