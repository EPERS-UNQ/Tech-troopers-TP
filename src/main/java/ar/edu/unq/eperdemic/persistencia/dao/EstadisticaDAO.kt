package ar.edu.unq.eperdemic.persistencia.dao

import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.vector.TipoVector
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface EstadisticaDAO : CrudRepository<Especie, Long> {

//    @Query(
//            """
//              select e
//              from Especie e
//              join e.vectores v
//              where v.tipo = 'HUMANO'
//              group by e
//              order by count(v) desc
//            """
//    )
    fun findTopByVectoresTipoOrderByVectoresDesc(tipo: TipoVector): Especie

    @Query(
            """
                select e
                from Especie e
                join e.vectores v
                where v.tipo = 'HUMANO' OR v.tipo = 'ANIMAL'
                group by e
                order by count(v)
            """
    )
    fun todosLosLideres(direccion: String, pageable: Pageable) : List<Especie>

    @Query(
            """ 
                select COALESCE(count(v), 0)
                from Vector v
                where v.ubicacion.nombre = ?1
            """
    )
    fun cantidadDeVectoresEn(nombreDeUbicacion: String) : Int

    @Query(
            """ 
                select COALESCE(count(v), 0)
                from Vector v
                where v.ubicacion.nombre = ?1
                    and size(v.especies) > 0
            """
    )
    fun cantidadDeInfectadosEnUbicacion(nombreDeUbicacion: String) : Int

    @Query(
            """
                select e
                from Especie e
                join e.vectores v
                where v.ubicacion.nombre = ?1
                group by e
                order by count(v) desc
            """
    )
    fun findTopEspeciePrevalente(nombreDeUbicacion: String) : Especie
}