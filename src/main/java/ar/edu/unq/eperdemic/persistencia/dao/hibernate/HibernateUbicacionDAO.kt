package ar.edu.unq.eperdemic.persistencia.dao.hibernate

import ar.edu.unq.eperdemic.services.runner.HibernateTransactionRunner

import ar.edu.unq.eperdemic.modelo.Ubicacion;
import ar.edu.unq.eperdemic.persistencia.dao.UbicacionDAO;


open class HibernateUbicacionDAO : HibernateDAO<Ubicacion>(Ubicacion::class.java),
    UbicacionDAO {

    override fun recuperarTodos(): List<Ubicacion> {

        val session = HibernateTransactionRunner.currentSession
        val hql = "select u from Ubicacion u order by u.nombre ASC "
        val query = session.createQuery(hql, Ubicacion::class.java)

        return query.resultList
    }

}
