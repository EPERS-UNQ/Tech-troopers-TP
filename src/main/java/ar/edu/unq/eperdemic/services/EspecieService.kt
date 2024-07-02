package ar.edu.unq.eperdemic.services

import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.ubicacion.UbicacionElastic

interface EspecieService {

    fun updatear(especie : Especie)
    fun recuperar(especieID : Long) : Especie
    fun recuperarTodos() : List<Especie>
    fun cantidadDeInfectados(especieId: Long): Int
    fun superInfectador(especieId: Long): List<UbicacionElastic>

}