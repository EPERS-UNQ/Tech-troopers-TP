package ar.edu.unq.eperdemic.persistencia.dao.hibernate

import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.persistencia.dao.EspecieDAO
import ar.edu.unq.eperdemic.services.runner.HibernateTransactionRunner

open class HibernateEspecieDAO : HibernateDAO<Especie>(Especie::class.java),
    EspecieDAO {

    override fun recuperarTodos(): List<Especie> {
        val session = HibernateTransactionRunner.currentSession

        val hql   = "select e from Especie e"
        val query = session.createQuery(hql, Especie::class.java)

        return query.resultList
    }

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

    override fun todosLosLideres(): List<Especie> {
        val session = HibernateTransactionRunner.currentSession

        val hql   = """
                        select e
                        from Especie e
                        join e.vectores v
                        where v.tipo = 'HUMANO' OR v.tipo = 'ANIMAL'
                        group by e
                        order by count(v) desc
                    """

        val query = session.createQuery(hql, Especie::class.java)

        return query.resultList
    }

}