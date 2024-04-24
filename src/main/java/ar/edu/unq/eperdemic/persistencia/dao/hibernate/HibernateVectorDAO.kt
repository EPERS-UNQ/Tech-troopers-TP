package ar.edu.unq.eperdemic.persistencia.dao.hibernate

import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.Ubicacion
import ar.edu.unq.eperdemic.modelo.vector.Vector
import ar.edu.unq.eperdemic.persistencia.dao.VectorDAO
import ar.edu.unq.eperdemic.services.runner.HibernateTransactionRunner

open class HibernateVectorDAO : HibernateDAO<Vector>(Vector::class.java),
    VectorDAO {

    override fun recuperarTodos(): List<Vector> {
        val session = HibernateTransactionRunner.currentSession
        val hql = "select v " +
                  "from Vector v"
        val query = session.createQuery(hql, Vector::class.java)

        return query.resultList
    }

    override fun enfermedades(vector: Vector): List<Especie> {
        return vector.enfermedadesDelVector()
    }

    override fun recuperarTodosDeUbicacion(ubicacionId: Long): List<Vector> {
        val session = HibernateTransactionRunner.currentSession
        val hql = """ 
                      from Vector v
                      where v.ubicacion.id = :unaUbicacionId
                  """
        val query = session.createQuery(hql, Vector::class.java)
        query.setParameter("unaUbicacionId", ubicacionId)

        return query.resultList
    }

    override fun cantidadDeUbicacionesDeVectoresConEspecie(unaEspecie : Especie) : Int {

        val session = HibernateTransactionRunner.currentSession
        val hql = """ 
                      select count(distinct v.ubicacion)
                      from Vector v
                      where :unaEspecie member of v.especies
                  """
        val query = session.createQuery(hql, java.lang.Long::class.java)
        query.setParameter("unaEspecie", unaEspecie)

        val count = query.singleResult ?: 0L

        return count.toInt()

    }

    override fun recuperarTodosDeUbicacionInfectados(ubicacionId: Long): List<Vector> {
        val session = HibernateTransactionRunner.currentSession
        val hql = """ 
                      from Vector v
                      where v.ubicacion.id = :unaUbicacionId 
                      and size(v.especies) > 0
                  """
        val query = session.createQuery(hql, Vector::class.java)
        query.setParameter("unaUbicacionId", ubicacionId)

        return query.resultList
    }

    override fun cantidadDeVectoresConEspecie(especieId: Long): Int {
        val session = HibernateTransactionRunner.currentSession
        val hql = """
                      select count(v)
                      from Vector v
                      where :unaEspecieId in (select e.id from v.especies e)
                  """
        val query = session.createQuery(hql, java.lang.Long::class.java)
        query.setParameter("unaEspecieId", especieId)

        val count = query.singleResult ?: 0L

        return count.toInt()
    }

}