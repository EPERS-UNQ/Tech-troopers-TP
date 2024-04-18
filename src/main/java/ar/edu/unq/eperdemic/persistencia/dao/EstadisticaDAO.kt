package ar.edu.unq.eperdemic.persistencia.dao

import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.ReporteDeContagios

interface EstadisticaDAO {

    fun lider() : Especie

    fun todosLosLideres() : List<Especie>

    fun reporteContagios( nombreDeLaUbicacion: String ) : ReporteDeContagios

}