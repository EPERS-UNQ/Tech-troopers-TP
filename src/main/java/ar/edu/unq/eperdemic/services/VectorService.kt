package ar.edu.unq.eperdemic.services

import ar.edu.unq.eperdemic.modelo.vector.Vector
import ar.edu.unq.eperdemic.modelo.Especie

interface VectorService {

    fun crear(vector: Vector): Vector

    fun updatear(vector: Vector)

    fun recuperar(vectorId: Long): Vector

    fun recuperarTodos(): List<Vector>

    fun infectar(vectorId: Long, especieId: Long)

    fun enfermedades(vectorId: Long): List<Especie>
}