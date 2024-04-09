package ar.edu.unq.eperdemic.persistencia.dao.impl


import ar.edu.unq.eperdemic.modelo.Patogeno
import ar.edu.unq.eperdemic.persistencia.dao.PatogenoDAO
import ar.edu.unq.eperdemic.services.runner.HibernateTransactionRunner


open class HibernatePatogenoDAO : HibernateDAO<Patogeno>(Patogeno::class.java), PatogenoDAO {

    override fun crear(patogeno: Patogeno): Patogeno {
        this.guardar(patogeno)
        return patogeno
    }

    override fun recuperar(id: Long): Patogeno {
        return this.recuperar(id)
    }
    override fun actualizar(patogeno: Patogeno) {

        val pat = this.recuperar(patogeno.id!!)
        if (pat.id == null) {
            throw RuntimeException("El id del patogeno no puede ser null")
        }
        this.guardar(pat)
    }

    override fun recuperarATodos(): List<Patogeno> {

            val session = HibernateTransactionRunner.currentSession

            val hql = """
                     select p
                     from Patogeno p
        """
            val query = session.createQuery(hql, Patogeno::class.java)

        return query.resultList
    }

    override fun eliminar(patogeno: Patogeno) {

        val session = HibernateTransactionRunner.currentSession
        val pat = (this.recuperar(patogeno.id!!))

        val hql = """
                     delete 
                     from Patogeno p
                     where id = ${pat.id}
        """
        session.createQuery(hql, Patogeno::class.java)

    }

}

