package ar.edu.unq.eperdemic.persistencia.dao

import ar.edu.unq.eperdemic.modelo.vector.Vector
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface VectorDAO : CrudRepository<Vector, Long> {

    /*
    @Query("""
        from Vector v
        where v.ubicacion.id = ?1
    """)
    fun findAllByUbicacionIs(ubicacionId: Long): List<Vector>

    @Query("""
        select count(distinct v.ubicacion)
        from Vector v
        where ?1 in (select e.id from v.especies e)
    """)
    fun countDistinctByUbicacionEqualsAndEspeciesIn(unaEspecieId : Long) : Int

    @Query("""
        from Vector v
        where v.ubicacion.id = ?1
        and size(v.especies) > 0
    """)
    fun findAllByUbicacionIsAndEspeciesIsNotEmpty(ubicacionId: Long): List<Vector>

    @Query("""
        select count (v)
        from Vector v
        where ?1 in (select e.id from v.especies e)
    """)
    fun countVectorsByEspeciesIn(especieId: Long): Int
    */

    @Query("from Vector v where v.ubicacion.id = ?1")
    fun recuperarTodosDeUbicacion(ubicacionId: Long): List<Vector>

    @Query(
            """ 
                select count(distinct v.ubicacion)
                from Vector v
                where ?1 in (select e.id from v.especies e)
            """
    )
    fun cantidadDeUbicacionesDeVectoresConEspecieId(unaEspecieId : Long) : Int

    @Query(
            """ 
                select v
                from Vector v
                where v.ubicacion.id = ?1 
                and size(v.especies) > 0
            """
    )
    fun recuperarTodosDeUbicacionInfectados(ubicacionId: Long): List<Vector>

    @Query(
            """
                select count(*)
                from Vector v
                where ?1 in (select e.id from v.especies e)
            """
    )
    fun cantidadDeVectoresConEspecie(especieId: Long): Int

}