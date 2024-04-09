package ar.edu.unq.eperdemic.services

import ar.edu.unq.eperdemic.modelo.Especie

interface EspecieService {

    fun crear(especie : Especie)

    fun updatear(especie : Especie)

    fun recuperar(especieID : Long) : Especie

    fun recuperarTodos() : MutableList<Especie>

    fun cantidadDeInfectados(especieId : Long) : Int
}