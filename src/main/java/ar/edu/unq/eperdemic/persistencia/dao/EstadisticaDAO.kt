package ar.edu.unq.eperdemic.persistencia.dao

import ar.edu.unq.eperdemic.modelo.Direccion
import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.ReporteDeContagios

interface EstadisticaDAO {

    fun lider() : Especie

    fun todosLosLideres(direccion: Direccion, pagina: Int, cantidadPorPagina: Int) : List<Especie>

    fun reporteContagios( nombreDeLaUbicacion: String ) : ReporteDeContagios

}