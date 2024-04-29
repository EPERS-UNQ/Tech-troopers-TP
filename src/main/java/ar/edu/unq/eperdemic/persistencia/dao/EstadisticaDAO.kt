package ar.edu.unq.eperdemic.persistencia.dao

import ar.edu.unq.eperdemic.modelo.Direccion
import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.ReporteDeContagios
import org.springframework.data.jpa.repository.Query

interface EstadisticaDAO {

    @Query(
            """
              select e
              from Especie e
              join e.vectores v
              where v.tipo = 'HUMANO'
              group by e
              order by count(v) desc
              limit 1
            """
    )
    fun lider() : Especie

    @Query(
            """
                select e
                from Especie e
                join e.vectores v
                where v.tipo = 'HUMANO' OR v.tipo = 'ANIMAL'
                group by e
                order by count(v) ?1
                limit ?3
                offset (?2 * ?3) 
            """
    )
    fun todosLosLideres(direccion: Direccion, pagina: Int, cantidadPorPagina: Int) : List<Especie>

    fun reporteContagios( nombreDeLaUbicacion: String ) : ReporteDeContagios

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
                where v.ubicacion.nombre = :nombreDeUbicacion 
                    and size(v.especies) > 0
            """
    )
    fun cantidadDeInfectadosEnUbicacion(nombreDeUbicacion: String) : Int

    @Query(
            """
                select e
                from Especie e
                join e.vectores v
                where v.ubicacion.nombre = :nombreDeUbicacion
                group by e
                order by count(v) desc
                limit 1
            """
    )
    fun especiePrevalente(nombreDeUbicacion: String) : Especie
}