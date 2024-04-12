package ar.edu.unq.eperdemic.services.impl

import ar.edu.unq.eperdemic.modelo.Ubicacion
import ar.edu.unq.eperdemic.modelo.vector.Vector

import ar.edu.unq.eperdemic.persistencia.dao.UbicacionDAO
import ar.edu.unq.eperdemic.persistencia.dao.VectorDAO

import ar.edu.unq.eperdemic.persistencia.dao.hibernate.HibernateUbicacionDAO
import ar.edu.unq.eperdemic.persistencia.dao.hibernate.HibernateVectorDAO

import ar.edu.unq.eperdemic.services.UbicacionService
import ar.edu.unq.eperdemic.services.runner.HibernateTransactionRunner.runTrx


class UbicacionServiceImp : UbicacionService {

    private val dao: UbicacionDAO = HibernateUbicacionDAO()
    private val vec: VectorDAO = HibernateVectorDAO()


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
            val vector = vec.recuperar(vectorId)
            val nuevaUbicacion = dao.recuperar(ubicacionId)

            if (nuevaUbicacion == null || vector == null) {
                throw IllegalArgumentException("La ubicación o el vector no existen," +
                        " por lo que no se puede mover el vector")
            }

            vector.ubicacion = nuevaUbicacion
            vec.actualizar(vector)

            if(vector.especies.isNotEmpty()) {
                nuevaUbicacion.vectores.map {
                    // vector.contagiar(it)
                    // vec.actualizar(it)
                }
            }

        }
    }

}