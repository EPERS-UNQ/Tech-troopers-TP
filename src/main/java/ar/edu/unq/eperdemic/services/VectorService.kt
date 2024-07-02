package ar.edu.unq.eperdemic.services

import ar.edu.unq.eperdemic.modelo.vector.Vector
import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.vector.VectorElastic
import ar.edu.unq.eperdemic.modelo.vector.VectorGlobal

interface VectorService {

    fun crear(vectorGlobal: VectorGlobal): VectorGlobal

    fun updatear(vectorGlobal: VectorGlobal)

    fun recuperar(vectorId: Long): Vector

    fun recuperarTodos(): List<Vector>

    fun recuperarTodosElastic(): List<VectorElastic>

    fun infectar(vectorId: Long, especieId: Long)

    fun enfermedades(vectorId: Long): List<Especie>

    fun deleteAll()
}