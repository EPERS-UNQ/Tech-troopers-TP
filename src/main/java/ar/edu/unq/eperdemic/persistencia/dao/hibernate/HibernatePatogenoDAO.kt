package ar.edu.unq.eperdemic.persistencia.dao.hibernate

import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.Patogeno
import ar.edu.unq.eperdemic.persistencia.dao.PatogenoDAO
import ar.edu.unq.eperdemic.services.runner.HibernateTransactionRunner

open class HibernatePatogenoDAO : HibernateDAO<Patogeno>(Patogeno::class.java),
    PatogenoDAO {

    override fun recuperarATodos(): List<Patogeno> {

            val session = HibernateTransactionRunner.currentSession
            val hql = """
                     select p
                     from Patogeno p
                     order by p.tipo ASC 
        """
            val query = session.createQuery(hql, Patogeno::class.java)

        return query.resultList
    }

}