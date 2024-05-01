package ar.edu.unq.eperdemic.services.impl

import ar.edu.unq.eperdemic.exceptions.ErrorDeMovimiento
import ar.edu.unq.eperdemic.modelo.RandomGenerator.RandomGenerator
import ar.edu.unq.eperdemic.modelo.Ubicacion
import ar.edu.unq.eperdemic.persistencia.dao.UbicacionDAO
import ar.edu.unq.eperdemic.persistencia.dao.VectorDAO
import ar.edu.unq.eperdemic.services.UbicacionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UbicacionServiceImp() : UbicacionService {

    @Autowired private lateinit var ubicacionDAO: UbicacionDAO
    @Autowired private lateinit var vectorDAO: VectorDAO

    override fun crear(ubicacion: Ubicacion) : Ubicacion {
        return ubicacionDAO.save(ubicacion)
    }

    override fun updatear(ubicacion: Ubicacion) {
        ubicacionDAO.save(ubicacion)
    }

    override fun recuperar(id: Long): Ubicacion {
        return ubicacionDAO.findByIdOrNull(id)!!
    }

    override fun recuperarTodos(): Collection<Ubicacion> {
        return ubicacionDAO.findAll().toList()
    }

    override fun mover(vectorId: Long, ubicacionId: Long) {
        TODO("Not yet implemented")
    }

    override fun expandir(ubicacionId: Long) {
        TODO("Not yet implemented")
    }
    /*
    override fun mover(vectorId: Long, ubicacionId: Long) {
        runTrx {
            val vector = daoVector.recuperar(vectorId)
            val nuevaUbicacion = daoUbicacion.recuperar(ubicacionId)

            if (nuevaUbicacion == null || vector == null) {
                throw ErrorDeMovimiento()
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
            val todosLosVectoresInf = daoVector.recuperarTodosDeUbicacionInfectados(ubicacionId)

            if (todosLosVectoresInf.isNotEmpty()) {
                val random = RandomGenerator.getInstance()
                val vectorInf = random.getElementoRandomEnLista(todosLosVectoresInf)

                for (v in todosLosVectores) {
                    vectorInf.contargiarA(v)
                    daoVector.actualizar(v)
                }

            }
        }
    }
     */
}


















