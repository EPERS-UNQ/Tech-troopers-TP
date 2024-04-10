package ar.edu.unq.eperdemic.persistencia.dao.jdbc

import ar.edu.unq.eperdemic.modelo.Ubicacion
import ar.edu.unq.eperdemic.services.runner.HibernateTransactionRunner


open class HibernateDAO<T>(private val entityType: Class<T>) {

    open fun crear(entity: T) {
        val session = HibernateTransactionRunner.currentSession
        session.save(entity)
    }

    open fun actualizar(entity: T) {
        val session = HibernateTransactionRunner.currentSession
        session.update(entity)
    }

    open fun recuperar(id: Long?): T {
        val session = HibernateTransactionRunner.currentSession
        return session.get(entityType, id)
    }

    open fun eliminar(entity: T) {
        val session = HibernateTransactionRunner.currentSession
        session.delete(entity)
    }

}
