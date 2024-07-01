package ar.edu.unq.eperdemic.services

import ar.edu.unq.eperdemic.modelo.vector.Vector
import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.ubicacion.UbicacionMongo
import ar.edu.unq.eperdemic.modelo.vector.VectorElastic

interface VectorService {

    fun crear(vector: Vector): Vector

    fun updatear(vector: Vector)

    fun recuperar(vectorId: Long): Vector

    fun recuperarTodos(): List<Vector>

    fun recuperarTodosElastic(): List<VectorElastic>

    fun infectar(vectorId: Long, especieId: Long)

    fun enfermedades(vectorId: Long): List<Especie>

    fun historialDelSuperVector(vectorId: Long): List<UbicacionMongo>

    fun deleteAll()
}