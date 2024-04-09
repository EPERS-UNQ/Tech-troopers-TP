package ar.edu.unq.eperdemic.persistencia.dao.jdbc

import ar.edu.unq.eperdemic.modelo.Vector
import ar.edu.unq.eperdemic.persistencia.dao.VectorDAO
import ar.edu.unq.eperdemic.services.runner.HibernateTransactionRunner

open class HibernateVectorDAO : HibernateDAO<Vector>(Vector::class.java),
    VectorDAO {

    override fun recuperar(idVector: Long): Vector {
        val session = HibernateTransactionRunner.currentSession
        return session.get(Vector::class.java, idVector)
    }

    override fun recuperarTodos(): Collection<Vector> {
        val session = HibernateTransactionRunner.currentSession
        val hql = "select i " +
                  "from vector i"
        val query = session.createQuery(hql, Vector::class.java)

        return query.resultList
    }


}