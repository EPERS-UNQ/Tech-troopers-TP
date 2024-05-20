package ar.edu.unq.eperdemic.persistencia.dao

import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.vector.TipoVector
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface EstadisticaDAO : CrudRepository<Especie, Long> {

    fun findTopByVectoresTipoOrderByVectoresDesc(tipo: TipoVector): Especie

    @Query(
            """
                select e
                from Especie e
                join e.vectores v
                where v.tipo = 'HUMANO' OR v.tipo = 'ANIMAL'
                group by e
                order by count(v) desc
            """
    )
    fun todosLosLideresDesc(pageable: Pageable) : List<Especie>

    @Query(
        """
                select e
                from Especie e
                join e.vectores v
                where v.tipo = 'HUMANO' OR v.tipo = 'ANIMAL'
                group by e
                order by count(v) asc
            """
    )
    fun todosLosLideresAsc(pageable: Pageable) : List<Especie>

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
                select COALESCE(e.nombre, null)
                from Especie e
                join e.vectores v
                left join v.ubicacion u
                where (?1 IS NULL OR u.nombre = ?1)
                group by e
                order by count(v) desc
            """
    )
    fun findTopEspeciePrevalente(nombreDeUbicacion: String, pageable: Pageable) : List<String>
}