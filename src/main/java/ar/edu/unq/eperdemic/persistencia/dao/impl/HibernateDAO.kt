package ar.edu.unq.eperdemic.persistencia.dao.impl

import ar.edu.unq.eperdemic.modelo.Patogeno
import ar.edu.unq.eperdemic.services.runner.HibernateTransactionRunner


open class HibernateDAO<T>(private val entityType: Class<T>) {

    open fun guardar(entity: T) {
        val session = HibernateTransactionRunner.currentSession
        session.save(entity)
    }

    fun recuperar(id: Long?): T {
        val session = HibernateTransactionRunner.currentSession
        return session.get(entityType, id)
    }

    open fun actualizar(entity: T) {
        val session = HibernateTransactionRunner.currentSession
        session.update(entity)
    }

    open fun eliminar(entity: T) {
        val session = HibernateTransactionRunner.currentSession
        session.delete(entity)
    }
    
}
