package ar.edu.unq.eperdemic.persistencia.dao

import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.Direccion

interface EspecieDAO {

    fun crear(entity: Especie) : Especie
    fun actualizar(entity: Especie)
    fun recuperar(id: Long?) : Especie
    fun recuperarTodos() : List<Especie>
    fun especiesDelPatogenoId(patogenoId: Long, direccion: Direccion, pagina: Int, cantidadPorPagina: Int): List<Especie>

}