package ar.edu.unq.eperdemic.persistencia.dao.hibernate

import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.Direccion
import ar.edu.unq.eperdemic.modelo.Patogeno
import ar.edu.unq.eperdemic.persistencia.dao.EspecieDAO
import ar.edu.unq.eperdemic.services.runner.HibernateTransactionRunner

open class HibernateEspecieDAO : HibernateDAO<Especie>(Especie::class.java),
    EspecieDAO {

    override fun recuperarTodos(): List<Especie> {
        val session = HibernateTransactionRunner.currentSession

        val hql   = "select e from Especie e "
        val query = session.createQuery(hql, Especie::class.java)

        return query.resultList
    }

    override fun especiesDelPatogenoId(patogenoId: Long, direccion: Direccion, pagina: Int, cantidadPorPagina: Int): List<Especie> {

        val session = HibernateTransactionRunner.currentSession
        val ordenamiento = if (direccion == Direccion.ASCENDENTE) "asc" else "desc"

        val hql = (" select e "
                 + " from Especie e "
                 + " where e.patogeno.id = :unPatogenoId"
                 + " order by e.nombre $ordenamiento")

        val query = session.createQuery(hql, Especie::class.java)
        query.setParameter("unPatogenoId", patogenoId)
        query.firstResult = pagina * cantidadPorPagina
        query.maxResults  = cantidadPorPagina

        return query.resultList

    }

}