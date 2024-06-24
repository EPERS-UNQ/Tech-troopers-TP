package ar.edu.unq.eperdemic.services.impl

import ar.edu.unq.eperdemic.exceptions.NoExisteElVector
import ar.edu.unq.eperdemic.exceptions.NoExisteLaEspecie
import ar.edu.unq.eperdemic.exceptions.NoExisteLaUbicacion
import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.vector.Vector
import ar.edu.unq.eperdemic.modelo.vector.VectorElastic
import ar.edu.unq.eperdemic.persistencia.dao.EspecieDAO
import ar.edu.unq.eperdemic.persistencia.dao.UbicacionJpaDAO
import ar.edu.unq.eperdemic.persistencia.dao.VectorElasticDAO
import ar.edu.unq.eperdemic.persistencia.dao.VectorJpaDAO
import ar.edu.unq.eperdemic.services.VectorService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class VectorServiceImpl () : VectorService {

    @Autowired private lateinit var especieDAO: EspecieDAO
    @Autowired private lateinit var vectorJpaDAO: VectorJpaDAO
    @Autowired private lateinit var vectorElasticDAO: VectorElasticDAO
    @Autowired private lateinit var ubicacionJpaDAO: UbicacionJpaDAO

    override fun crear(vector: Vector): Vector {
        val ubicacion = vector.ubicacion
        if ( ubicacion != null ) {
            val ubicacionPersistida = ubicacionJpaDAO.recuperarPorNombreReal(ubicacion.getNombre()!!)
            if ( ubicacionPersistida != null ) {
                vector.ubicacion = ubicacionPersistida
            } else {
                throw NoExisteLaUbicacion()
            }
        }
        vectorElasticDAO.save(VectorElastic(vector.getNombre(), vector.getUbicacion(), vector.getTipo()))
        // en teoria se crea pero queda a la espera de que se infecte con el superPatogeno
        // y ahi empezar a tener registro de sus movimientos
        vectorJpaDAO.save(vector)
        return vector
    }

    override fun updatear(vector: Vector) {
        vectorJpaDAO.save(vector)
        // ver si hay que actualizar el vectorElastic ya que solo importa el historial de ubicaciones
        // y ese se guarda cada vez que se mueve
    }

    override fun recuperar(idVector: Long): Vector {

        val vector = vectorJpaDAO.findByIdOrNull(idVector)
        if (vector == null) {
            throw NoExisteElVector()

        }
        return vector
    }

    override fun recuperarTodos(): List<Vector> {
        return vectorJpaDAO.findAll().toList()
    }

    override fun infectar(vectorId: Long, especieId: Long) {
        val especie = especieDAO.findById(especieId).orElse(null)
        val vector  = vectorJpaDAO.findById(vectorId).orElse(null)
        if      ( especie == null ) { NoExisteLaEspecie() }
        else if ( vector == null )  { NoExisteElVector()  }
        vector.infectar(especie)
        vectorJpaDAO.save(vector)
        especieDAO.save(especie)

        // la logica de si es la especie nueva

    }

    override fun enfermedades(vectorId: Long): List<Especie> {
        return (vectorJpaDAO.findByIdOrNull(vectorId)!!).enfermedadesDelVector()
    }

}