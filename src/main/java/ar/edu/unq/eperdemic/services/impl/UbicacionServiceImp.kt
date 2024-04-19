package ar.edu.unq.eperdemic.services.impl

import ar.edu.unq.eperdemic.exceptions.ErrorNombreEnUso
import ar.edu.unq.eperdemic.modelo.RandomGenerator
import ar.edu.unq.eperdemic.modelo.Ubicacion
import ar.edu.unq.eperdemic.persistencia.dao.UbicacionDAO
import ar.edu.unq.eperdemic.persistencia.dao.VectorDAO
import ar.edu.unq.eperdemic.services.UbicacionService
import ar.edu.unq.eperdemic.services.runner.HibernateTransactionRunner.runTrx


class UbicacionServiceImp(
    private val daoUbicacion: UbicacionDAO,
    private val daoVector: VectorDAO
) : UbicacionService {

    override fun crear(ubicacion: Ubicacion) : Ubicacion {
        return runTrx {
            val existeNombre = daoUbicacion.recuperarTodos().stream().anyMatch { u -> u.getNombre() == ubicacion.getNombre() }
            if (existeNombre) {
                throw ErrorNombreEnUso()
            }
            daoUbicacion.crear(ubicacion) }
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

            val todosLosVectores = daoVector.recuperarTodosDeUbicacion(nuevaUbicacion.getId()!!)

            if(vector.estaInfectado()) {
                todosLosVectores.map {
                    vector.contargiarA(it)
                    daoVector.actualizar(it)
                }
            }

        }
    }

    override fun expandir( ubicacionId: Long) {
        runTrx {
            val todosLosVectores = daoVector.recuperarTodosDeUbicacion(ubicacionId)
            val todosLosVectoresInf = todosLosVectores.filter { it.estaInfectado() }

            if (todosLosVectoresInf.isNotEmpty()) {
                val random = RandomGenerator()
                val vectorInf = random.getElementoRandomEnLista(todosLosVectoresInf)

                for (v in todosLosVectores) {
                    vectorInf.contargiarA(v)
                    daoVector.actualizar(v)
                }

            }
        }
    }

}

















