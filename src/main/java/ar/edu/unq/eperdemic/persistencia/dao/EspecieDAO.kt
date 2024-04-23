package ar.edu.unq.eperdemic.persistencia.dao

import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.Direccion
import ar.edu.unq.eperdemic.modelo.Patogeno

interface EspecieDAO {

    fun crear(entity: Especie) : Especie
    fun actualizar(entity: Especie)
    fun recuperar(id: Long?) : Especie
    fun recuperarTodos() : List<Especie>
    fun especiesDelPatogeno(patogeno: Patogeno, direccion: Direccion, pagina: Int, cantidadPorPagina: Int): List<Especie>

}