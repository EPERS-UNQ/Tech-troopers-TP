package ar.edu.unq.eperdemic.services.impl

import ar.edu.unq.eperdemic.modelo.Ubicacion
import ar.edu.unq.eperdemic.persistencia.dao.UbicacionDAO
import ar.edu.unq.eperdemic.persistencia.dao.jdbc.HibernateUbicacionDAO
import ar.edu.unq.eperdemic.services.UbicacionService
import ar.edu.unq.eperdemic.services.runner.HibernateTransactionRunner.runTrx


class UbicacionServiceImp : UbicacionService {

    private val dao: UbicacionDAO = HibernateUbicacionDAO()

    override fun crear(ubicacion: Ubicacion) : Ubicacion {
        return runTrx { dao.crear(ubicacion) }
    }

    override fun updatear(ubicacion: Ubicacion) {
        runTrx { dao.actualizar(ubicacion) }
    }

    override fun recuperar(id: Long): Ubicacion {
        return runTrx { dao.recuperar(id) }
    }

    override fun recuperarTodos(): Collection<Ubicacion> {
        return runTrx { dao.recuperarTodos() }
    }

    override fun mover(vectorId: Long, ubicacionId: Long) {
        runTrx {


        }
    }

}