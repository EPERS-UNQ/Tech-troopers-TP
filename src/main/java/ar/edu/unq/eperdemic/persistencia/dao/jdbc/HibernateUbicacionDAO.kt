package ar.edu.unq.eperdemic.persistencia.dao.jdbc

import ar.edu.unq.eperdemic.services.runner.HibernateTransactionRunner

import ar.edu.unq.eperdemic.persistencia.dao.UbicacionDAO;
import ar.edu.unq.eperdemic.modelo.Ubicacion;


open class HibernateUbicacionDAO : HibernateDAO<Ubicacion>(Ubicacion::class.java),
    UbicacionDAO {

    override fun recuperarTodos(): Collection<Ubicacion> {
        val session = HibernateTransactionRunner.currentSession
        val hql = "select i" +
                  " from ubicacion i"
        val query = session.createQuery(hql, Ubicacion::class.java)

        return query.resultList
    }


}
