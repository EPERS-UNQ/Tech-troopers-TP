package ar.edu.unq.eperdemic.persistencia.dao

import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.Patogeno
import ar.edu.unq.eperdemic.modelo.RandomGenerator
import ar.edu.unq.eperdemic.modelo.Ubicacion
import ar.edu.unq.eperdemic.modelo.vector.Vector
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface VectorDAO : CrudRepository<Patogeno, Long> {

    fun recuperarTodos() : List<Vector>
    fun enfermedades(vector: Vector): List<Especie>
    fun recuperarTodosDeUbicacion(ubicacionId: Long): List<Vector>
    fun cantidadDeUbicacionesDeVectoresConEspecieId(unaEspecieId : Long) : Int
    fun recuperarTodosDeUbicacionInfectados(ubicacionId: Long): List<Vector>
    fun cantidadDeVectoresConEspecie(especieId: Long): Int

}