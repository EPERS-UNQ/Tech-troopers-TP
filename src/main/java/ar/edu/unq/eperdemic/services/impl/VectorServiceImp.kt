package ar.edu.unq.eperdemic.services.impl

import ar.edu.unq.eperdemic.exceptions.NoExisteElVector
import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.vector.Vector
import ar.edu.unq.eperdemic.persistencia.dao.EspecieDAO
import ar.edu.unq.eperdemic.persistencia.dao.UbicacionDAO
import ar.edu.unq.eperdemic.persistencia.dao.VectorDAO
import ar.edu.unq.eperdemic.services.VectorService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class VectorServiceImp () : VectorService {

    @Autowired private lateinit var especieDAO: EspecieDAO
    @Autowired private lateinit var vectorDAO: VectorDAO

    override fun crear(vector: Vector): Vector {
        return vectorDAO.save(vector)
    }

    override fun updatear(vector: Vector) {
        vectorDAO.save(vector)
    }

    override fun recuperar(idVector: Long): Vector {

        val vector = vectorDAO.findByIdOrNull(idVector)!!
        if (vector == null) {
            throw NoExisteElVector()

        }
        return vector
    }

    override fun recuperarTodos(): List<Vector> {
        return vectorDAO.findAll().toList()
    }

    override fun infectar(vectorId: Long, especieId: Long) {
        val especie = especieDAO.findById(especieId).orElse(null)
        val vector  = vectorDAO.findById(vectorId).orElse(null) //Tirar error si no encuentra?
        vector.infectar(especie)
        vectorDAO.save(vector)
        especieDAO.save(especie)
    }

    override fun enfermedades(vectorId: Long): List<Especie> {
        return (vectorDAO.findByIdOrNull(vectorId)!!).enfermedadesDelVector()
    }

}