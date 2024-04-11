package ar.edu.unq.eperdemic.persistencia.dao

import ar.edu.unq.eperdemic.modelo.Ubicacion

interface UbicacionDAO {

    fun crear(vector: Ubicacion): Ubicacion

    fun actualizar(vector: Ubicacion)

    fun recuperar(vectorId: Long): Ubicacion

    fun recuperarTodos(): List<Ubicacion>

}