package ar.edu.unq.eperdemic.persistencia.dao.jdbc

import ar.edu.unq.eperdemic.modelo.Vector
import ar.edu.unq.eperdemic.persistencia.dao.VectorDAO
import ar.edu.unq.eperdemic.services.runner.HibernateTransactionRunner

//Con la palabra "open" quiere decir que la clase puede ser heredada. Sin esta palabra, es final por defecto.
open class HibernateVectorDAO : HibernateDAO<Vector>(Vector::class.java),
    VectorDAO {
    override fun recuperar(vectorId: Long): Vector {
        val session = HibernateTransactionRunner.currentSession
        return session.get(Vector::class.java, vectorId)
    }

    override fun recuperarTodos(): Collection<Vector> {
        val session = HibernateTransactionRunner.currentSession
        val hql = "select i " +
                  "from vector i"
        val query = session.createQuery(hql, Vector::class.java)

        return query.resultList
    }


}