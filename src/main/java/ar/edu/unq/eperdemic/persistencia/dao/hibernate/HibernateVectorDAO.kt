package ar.edu.unq.eperdemic.persistencia.dao.hibernate

import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.vector.Vector
import ar.edu.unq.eperdemic.persistencia.dao.VectorDAO
import ar.edu.unq.eperdemic.services.runner.HibernateTransactionRunner


open class HibernateVectorDAO : HibernateDAO<Vector>(Vector::class.java),
    VectorDAO {

        override fun recuperarTodos(): List<Vector> {
        val session = HibernateTransactionRunner.currentSession
        val hql = "select i " +
                  "from Vector i"
        val query = session.createQuery(hql, Vector::class.java)

        return query.resultList
    }


    override fun infectar(vector: Vector, especie: Especie) {
        vector.infectar(especie)
        this.actualizar(vector)
    }

    override fun enfermedades(vector: Vector): List<Especie> {
        return vector.enfermedadesDelVector()
    }

    override fun recuperarTodosDe(ubicacionId: Long): List<Vector> {
        val session = HibernateTransactionRunner.currentSession
        val hql = """ 
                      from Vector v
                      where v.ubicacion.id = :unaUbicacionId
                  """
        val query = session.createQuery(hql, Vector::class.java)
        query.setParameter("unaUbicacionId", ubicacionId)

        return query.resultList
    }



}