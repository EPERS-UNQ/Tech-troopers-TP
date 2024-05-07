package ar.edu.unq.eperdemic.services.impl

import ar.edu.unq.eperdemic.exceptions.ErrorDeMovimiento
import ar.edu.unq.eperdemic.exceptions.ErrorNombre
import ar.edu.unq.eperdemic.exceptions.NoExisteLaUbicacion
import ar.edu.unq.eperdemic.modelo.RandomGenerator.RandomGenerator
import ar.edu.unq.eperdemic.modelo.Ubicacion
import ar.edu.unq.eperdemic.persistencia.dao.UbicacionDAO
import ar.edu.unq.eperdemic.persistencia.dao.VectorDAO
import ar.edu.unq.eperdemic.services.UbicacionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UbicacionServiceImp() : UbicacionService {

    @Autowired private lateinit var ubicacionDAO: UbicacionDAO
    @Autowired private lateinit var vectorDAO: VectorDAO

    override fun crear(ubicacion: Ubicacion) : Ubicacion {

        val newUbicacion : Ubicacion
        try {
            newUbicacion = ubicacionDAO.save(ubicacion)
        } catch (e: DataIntegrityViolationException) {
            throw ErrorNombre("El nombre de la ubicacion ya existe")
        }
        return newUbicacion
    }

    override fun updatear(ubicacion: Ubicacion) {
        ubicacionDAO.save(ubicacion)
    }

    override fun recuperar(id: Long): Ubicacion {

        val ubicacion = ubicacionDAO.findByIdOrNull(id)
        if (ubicacion == null) {
            throw NoExisteLaUbicacion()
        }
        return ubicacion
    }

    override fun recuperarTodos(): Collection<Ubicacion> {
        return ubicacionDAO.findAll().toList()
    }

    override fun mover(vectorId: Long, ubicacionId: Long) {

        val vector = vectorDAO.findById(vectorId).orElse(null)
        val nuevaUbicacion = ubicacionDAO.findById(ubicacionId).orElse(null)

        if (nuevaUbicacion == null || vector == null) {
            throw ErrorDeMovimiento()
        }

        vector.ubicacion = nuevaUbicacion
        vectorDAO.save(vector)

        val todosLosVectores = vectorDAO.recuperarTodosDeUbicacion(nuevaUbicacion.getId()!!)

        if (vector.estaInfectado()) {
            todosLosVectores.map {
                vector.contargiarA(it)
                vectorDAO.save(it)
            }
        }
    }

    override fun expandir( ubicacionId: Long) {

        val todosLosVectores = vectorDAO.recuperarTodosDeUbicacion(ubicacionId)
        val todosLosVectoresInf = vectorDAO.recuperarTodosDeUbicacionInfectados(ubicacionId)

        if (todosLosVectoresInf.isNotEmpty()) {
            val random = RandomGenerator.getInstance()
            val vectorInf = random.getElementoRandomEnLista(todosLosVectoresInf)

            for (v in todosLosVectores) {
                vectorInf.contargiarA(v)
                vectorDAO.save(v)
            }

        }

    }
}


















