package ar.edu.unq.eperdemic.persistencia.dao

import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.vector.Vector

interface EspecieDAO {

    fun crear(entity: Especie) : Especie

    fun actualizar(entity: Especie)

    fun recuperar(id: Long?) : Especie

    fun recuperarTodos() : List<Especie>

    fun lider() : Especie

    fun todosLosLideres() : List<Especie>

    fun especiePrevalente(vectoresUbicados: List<Vector>): String

}