package ar.edu.unq.eperdemic.persistencia.dao.jdbc

import ar.edu.unq.eperdemic.services.runner.HibernateTransactionRunner


open class HibernateDAO<T>(private val entityType: Class<T>) {

    open fun crear(entity: T): T {
        val session = HibernateTransactionRunner.currentSession
        session.persist(entity)
        //session.flush() // Sincroniza la sesión con la base de datos.
        //session.refresh(entity) // Para obtener la versión persistida de la entidad.
        return entity
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
