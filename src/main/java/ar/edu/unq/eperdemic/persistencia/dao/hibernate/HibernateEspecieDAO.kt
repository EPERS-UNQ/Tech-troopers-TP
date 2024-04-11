package ar.edu.unq.eperdemic.persistencia.dao.hibernate

import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.persistencia.dao.EspecieDAO
import ar.edu.unq.eperdemic.services.runner.HibernateTransactionRunner

open class HibernateEspecieDAO : HibernateDAO<Especie>(Especie::class.java),
    EspecieDAO {
    override fun recuperar(especieId: Long): Especie {
        TODO("Not yet implemented")
    }

    override fun recuperarTodos(): List<Especie> {
        val session = HibernateTransactionRunner.currentSession

        val hql   = "select e from Especie e"
        val query = session.createQuery(hql, Especie::class.java)

        return query.resultList
    }

}