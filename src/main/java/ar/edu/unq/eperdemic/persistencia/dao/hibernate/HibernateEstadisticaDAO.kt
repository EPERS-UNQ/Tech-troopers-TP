package ar.edu.unq.eperdemic.persistencia.dao.hibernate

import ar.edu.unq.eperdemic.modelo.Direccion
import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.ReporteDeContagios
import ar.edu.unq.eperdemic.modelo.vector.Vector
import ar.edu.unq.eperdemic.persistencia.dao.EstadisticaDAO
import ar.edu.unq.eperdemic.services.runner.HibernateTransactionRunner

class HibernateEstadisticaDAO : EstadisticaDAO{

    override fun lider(): Especie {
        val session = HibernateTransactionRunner.currentSession

        val hql   = """
                     select e
                     from Especie e
                     join e.vectores v
                     where v.tipo = 'HUMANO'
                     group by e
                     order by count(v) desc
                    """

        val query = session.createQuery(hql, Especie::class.java)
        query.maxResults = 1

        return query.singleResult
    }

    override fun todosLosLideres(direccion: Direccion, pagina: Int, cantidadPorPagina: Int): List<Especie> {
        val session = HibernateTransactionRunner.currentSession
        val ordenamiento = if (direccion == Direccion.ASCENDENTE) "asc" else "desc"

        val hql = """
                   select e
                   from Especie e
                   join e.vectores v
                   where v.tipo = 'HUMANO' OR v.tipo = 'ANIMAL'
                   group by e
                   order by count(v) $ordenamiento
                  """

        val query = session.createQuery(hql, Especie::class.java)
        query.firstResult = pagina * cantidadPorPagina
        query.maxResults  = cantidadPorPagina

        return query.resultList
    }

    override fun reporteContagios(nombreDeUbicacion: String): ReporteDeContagios {
        return ReporteDeContagios(cantidadDeVectoresEn(nombreDeUbicacion),
                                  cantidadDeInfectadosEnUbicacion(nombreDeUbicacion),
                                  especiePrevalente(nombreDeUbicacion).nombre!!)
    }

    fun recuperarTodosLosVectoresPorNombreDeUbicacion(nombreDeUbicacion: String) : List<Vector> {
        val session = HibernateTransactionRunner.currentSession
        val hql = """ 
                      select v
                      from Vector v
                      where v.ubicacion.nombre = :nombreDeUbicacion
                  """
        val query = session.createQuery(hql, Vector::class.java)
        query.setParameter("nombreDeUbicacion", nombreDeUbicacion)

        return query.resultList
    }

    fun cantidadDeVectoresEn(nombreDeUbicacion: String) : Int {
        val session = HibernateTransactionRunner.currentSession
        val hql = """ 
                      select v
                      from Vector v
                      where v.ubicacion.nombre = :nombreDeUbicacion
                  """
        val query = session.createQuery(hql, Vector::class.java)
        query.setParameter("nombreDeUbicacion", nombreDeUbicacion)

        return query.resultList.size
    }

    fun cantidadDeInfectadosEnUbicacion(nombreDeUbicacion: String) : Int {
        val vectoresUbicados = recuperarTodosLosVectoresPorNombreDeUbicacion(nombreDeUbicacion)
        var cantidadInfectados = 0;

        vectoresUbicados.forEach { vector ->
            if (vector.estaInfectado()) {
                cantidadInfectados++
            }
        }

        return cantidadInfectados
    }

    fun especiePrevalente(nombreDeUbicacion: String) : Especie {
        val session = HibernateTransactionRunner.currentSession

        val hql   = """
                        select e
                        from Especie e
                        join e.vectores v
                        where v.ubicacion.nombre = :nombreDeUbicacion
                        group by e
                        order by count(v) desc
                    """

        val query = session.createQuery(hql, Especie::class.java)
        query.setParameter("nombreDeUbicacion", nombreDeUbicacion)
        query.maxResults = 1

        return query.singleResult
    }

}