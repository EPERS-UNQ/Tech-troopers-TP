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

    override fun recuperarUbicacionesDeVectoresConEspecie(unaEspecie : Especie) : List<Ubicacion> {

        val session = HibernateTransactionRunner.currentSession
        val hql = """ 
                      select v.ubicacion
                      from Vector v
                      where :unaEspecie member of v.especies
                  """
        val query = session.createQuery(hql, Ubicacion::class.java)
        query.setParameter("unaEspecie", unaEspecie)

        return query.resultList.toList()

    }

    override fun recuperarTodosDeUbicacionInfectados(ubicacionId: Long): List<Vector> {
        val session = HibernateTransactionRunner.currentSession
        val hql = """ 
                      from Vector v
                      where v.ubicacion.id = :unaUbicacionId 
                      and exists (select '*'
                                  from Especie e 
                                  where e member of v.especies)
                  """
        val query = session.createQuery(hql, Vector::class.java)
        query.setParameter("unaUbicacionId", ubicacionId)

        return query.resultList
    }

    // opcion en caso de que las especies no deban conocer a los vectores.

    override fun cantidadDeVectoresConEspecie(especie: Especie): Int {
        val session = HibernateTransactionRunner.currentSession
        val hql = """
                      from Vector v
                      where :especie member of v.especies
                      
                  """
        val query = session.createQuery(hql, Vector::class.java)
        query.setParameter("especie", especie)

        return query.resultList.size
    }

}