package ar.edu.unq.eperdemic.services.impl

import ar.edu.unq.eperdemic.exceptions.ErrorDeMovimiento
import ar.edu.unq.eperdemic.modelo.RandomGenerator.RandomGenerator
import ar.edu.unq.eperdemic.modelo.Ubicacion
import ar.edu.unq.eperdemic.persistencia.dao.UbicacionDAO
import ar.edu.unq.eperdemic.persistencia.dao.VectorDAO
import ar.edu.unq.eperdemic.services.UbicacionService
import ar.edu.unq.eperdemic.services.runner.HibernateTransactionRunner.runTrx
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UbicacionServiceImp() : UbicacionService {

    @Autowired private lateinit var daoUbicacion: UbicacionDAO
    @Autowired private lateinit var daoVector: VectorDAO

    override fun crear(ubicacion: Ubicacion) : Ubicacion {
        return daoUbicacion.save(ubicacion)
    }

    override fun updatear(ubicacion: Ubicacion) {
        daoUbicacion.save(ubicacion)
    }

    override fun recuperar(id: Long): Ubicacion {
        return daoUbicacion.recuperar(id)
    }


    override fun recuperarTodos(): Collection<Ubicacion> {
        return daoUbicacion.recuperarTodos()
    }

    override fun mover(vectorId: Long, ubicacionId: Long) {

        val vector = daoVector.recuperar(vectorId)
        val nuevaUbicacion = daoUbicacion.recuperar(ubicacionId)

        if (nuevaUbicacion == null || vector == null) {
            throw ErrorDeMovimiento()
        }

        vector.ubicacion = nuevaUbicacion
        daoVector.save(vector)

        val todosLosVectores = daoVector.recuperarTodosDeUbicacion(nuevaUbicacion.getId()!!)

        if (vector.estaInfectado()) {
            todosLosVectores.map {
                vector.contargiarA(it)
                daoVector.save(it)
            }
        }
    }

    override fun expandir( ubicacionId: Long) {

        val todosLosVectores = daoVector.recuperarTodosDeUbicacion(ubicacionId)
        val todosLosVectoresInf = daoVector.recuperarTodosDeUbicacionInfectados(ubicacionId)

        if (todosLosVectoresInf.isNotEmpty()) {
            val random = RandomGenerator.getInstance()
            val vectorInf = random.getElementoRandomEnLista(todosLosVectoresInf)

            for (v in todosLosVectores) {
                vectorInf.contargiarA(v)
                daoVector.save(v)
            }

        }

    }

}

















