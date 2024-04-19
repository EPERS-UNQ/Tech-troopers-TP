package ar.edu.unq.eperdemic.services

import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.ReporteDeContagios
import ar.edu.unq.eperdemic.modelo.Direccion

interface EstadisticaService {

    fun especieLider() : Especie

    fun lideres(direccion: Direccion, pagina: Int, cantidadPorPagina: Int) : List<Especie>

    fun reporteDeContagios(nombreDeLaUbicacion: String): ReporteDeContagios

}