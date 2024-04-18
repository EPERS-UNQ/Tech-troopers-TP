package ar.edu.unq.eperdemic.persistencia.dao.hibernate

import ar.edu.unq.eperdemic.exceptions.NoHayVectorException
import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.RandomGenerator
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

    override fun infectar(vector: Vector, especie: Especie) {
        vector.infectar(especie)
        this.actualizar(vector)
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

}