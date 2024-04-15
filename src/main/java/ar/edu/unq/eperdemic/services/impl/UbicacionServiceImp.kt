package ar.edu.unq.eperdemic.services.impl

import ar.edu.unq.eperdemic.modelo.Ubicacion
import ar.edu.unq.eperdemic.modelo.vector.Vector
import ar.edu.unq.eperdemic.persistencia.dao.EspecieDAO

import ar.edu.unq.eperdemic.persistencia.dao.UbicacionDAO
import ar.edu.unq.eperdemic.persistencia.dao.VectorDAO

import ar.edu.unq.eperdemic.persistencia.dao.hibernate.HibernateUbicacionDAO
import ar.edu.unq.eperdemic.persistencia.dao.hibernate.HibernateVectorDAO

import ar.edu.unq.eperdemic.services.UbicacionService
import ar.edu.unq.eperdemic.services.runner.HibernateTransactionRunner.runTrx


class UbicacionServiceImp(
    private val daoUbicacion: UbicacionDAO,
    private val daoVector: VectorDAO
) : UbicacionService {

    override fun crear(ubicacion: Ubicacion) : Ubicacion {
        return runTrx { daoUbicacion.crear(ubicacion) }
    }

    override fun updatear(ubicacion: Ubicacion) {
        runTrx { daoUbicacion.actualizar(ubicacion) }
    }

    override fun recuperar(id: Long): Ubicacion {
        return runTrx { daoUbicacion.recuperar(id) }
    }

    override fun recuperarTodos(): Collection<Ubicacion> {
        return runTrx { daoUbicacion.recuperarTodos() }
    }

    override fun mover(vectorId: Long, ubicacionId: Long) {
        runTrx {
            val vector = daoVector.recuperar(vectorId)
            val nuevaUbicacion = daoUbicacion.recuperar(ubicacionId)

            if (nuevaUbicacion == null || vector == null) {
                throw IllegalArgumentException("La ubicación o el vector no existen," +
                        " por lo que no se puede mover el vector")
            }

            vector.ubicacion = nuevaUbicacion
            daoVector.actualizar(vector)

            val todosLosVectores = daoVector.recuperarTodosDeUbicacion(nuevaUbicacion.id!!)

            if(vector.estaInfectado()) {
                todosLosVectores.map {
                    vector.contargiarA(it)
                    daoVector.actualizar(it)
                }
            }

        }
    }

}