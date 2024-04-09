package ar.edu.unq.eperdemic.persistencia.dao

import ar.edu.unq.eperdemic.modelo.Especie

interface EspecieDAO {

    fun crear(especie: Especie)

    fun actualizar(especie: Especie)

    fun recuperar(especieId: Long) : Especie

    fun recuperarTodos() : MutableList<Especie>

}