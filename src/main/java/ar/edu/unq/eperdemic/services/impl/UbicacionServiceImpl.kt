package ar.edu.unq.eperdemic.services.impl

import ar.edu.unq.eperdemic.exceptions.ErrorDeMovimiento
import ar.edu.unq.eperdemic.exceptions.ErrorUbicacionMuyLejana
import ar.edu.unq.eperdemic.exceptions.ErrorUbicacionNoAlcanzable
import ar.edu.unq.eperdemic.exceptions.NoExisteLaUbicacion
import ar.edu.unq.eperdemic.modelo.RandomGenerator.RandomGenerator
import ar.edu.unq.eperdemic.modelo.UbicacionJpa
import ar.edu.unq.eperdemic.modelo.UbicacionMongo
import ar.edu.unq.eperdemic.modelo.vector.Vector
import ar.edu.unq.eperdemic.persistencia.dao.UbicacionJpaDAO
import ar.edu.unq.eperdemic.persistencia.dao.VectorDAO
import ar.edu.unq.eperdemic.persistencia.dao.UbicacionNeo4jDAO
import ar.edu.unq.eperdemic.modelo.neo4j.UbicacionNeo4j
import ar.edu.unq.eperdemic.modelo.vector.TipoVector
import ar.edu.unq.eperdemic.persistencia.dao.UbicacionMongoDAO
import ar.edu.unq.eperdemic.services.UbicacionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional
class UbicacionServiceImpl() : UbicacionService {

    @Autowired private lateinit var ubicacionJpaDAO: UbicacionJpaDAO
    @Autowired private lateinit var ubicacionNeoDAO: UbicacionNeo4jDAO
    @Autowired private lateinit var ubicacionMongo: UbicacionMongoDAO
    @Autowired private lateinit var vectorDAO: VectorDAO

    override fun crear(ubicacion: UbicacionJpa) : UbicacionJpa {
        ubicacionJpaDAO.save(ubicacion)
        ubicacionNeoDAO.save(UbicacionNeo4j(ubicacion.getNombre()!!))
        ubicacionMongo.save(UbicacionMongo()) // ¿¿Lo persistimos con un constructor sin parámetros??
        return ubicacion
    }

    override fun updatear(ubicacion: UbicacionJpa) {
        ubicacionJpaDAO.save(ubicacion)
        ubicacionNeoDAO.save(UbicacionNeo4j(ubicacion.getNombre()!!))
    }

    override fun recuperar(id: Long): UbicacionJpa {

        val ubicacionJpa = ubicacionJpaDAO.findByIdOrNull(id)
        if (ubicacionJpa == null) {
            throw NoExisteLaUbicacion()
        }
        return ubicacionJpa
    }

    override fun recuperarTodos(): List<UbicacionJpa> {
        return ubicacionJpaDAO.findAll().toList()
    }

    override fun mover(vectorId: Long, ubicacionId: Long) {

        val vector = vectorDAO.findById(vectorId).orElse(null)
        val nuevaUbicacion = ubicacionJpaDAO.findById(ubicacionId).orElse(null)

        if (nuevaUbicacion == null || vector == null) {
            throw ErrorDeMovimiento()
        }

        this.verificarSiPuedeMoverA(vector.ubicacion!!.getNombre()!!, nuevaUbicacion.getNombre()!!,
            TipoVector.puedeCruzar(vector))

        this.moverHasta(vector, nuevaUbicacion.getNombre()!!)

    }

    private fun moverHasta(vector: Vector, nombreUbi: String) {

        val nuevaUbicacion = ubicacionJpaDAO.recuperarPorNombreReal(nombreUbi)

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

    private fun verificarSiPuedeMoverA(nomUbiInicio: String, nomUbiFin: String, tiposPermitidos: List<String>) {
        if(!ubicacionNeoDAO.esUbicacionLindante(nomUbiInicio,nomUbiFin)) {
            throw ErrorUbicacionMuyLejana()
        }
        if(!ubicacionNeoDAO.hayCaminoCruzable(nomUbiInicio, nomUbiFin, tiposPermitidos)) {
            throw ErrorUbicacionNoAlcanzable()
        }
    }

    private fun comprobarViabilidadUbi(nomUbiInicio: String, nomUbiFin: String, tiposPermitidos: List<String>) {
        if(!ubicacionNeoDAO.esUbicacionCercana(nomUbiInicio,nomUbiFin)) {
            throw ErrorUbicacionMuyLejana()
        }
        if(!ubicacionNeoDAO.esUbicacionAlcanzable(nomUbiInicio, nomUbiFin, tiposPermitidos)) {
            throw ErrorUbicacionNoAlcanzable()
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

    override fun conectar(nombreDeUbicacion1: String, nombreDeUbicacion2: String, tipoCamino: String) {
        ubicacionNeoDAO.conectarCaminos(nombreDeUbicacion1, nombreDeUbicacion2, tipoCamino)
    }

    override fun conectados(nombreDeUbicacion: String): List<UbicacionJpa> {
        val ubicacionNeo = ubicacionNeoDAO.ubicacionesConectadas(nombreDeUbicacion)
        val ubicacion: MutableList<UbicacionJpa> = mutableListOf()

        for(u in ubicacionNeo) {
            ubicacion.add(ubicacionJpaDAO.recuperarPorNombreReal(u.getNombre()!!))
        }
        return ubicacion
    }

    override fun moverPorCaminoMasCorto(vectorId: Long, nombreDeUbicacion: String) {

        val vector = vectorDAO.findById(vectorId).orElse(null)
        val nuevaUbicacion = ubicacionJpaDAO.recuperarPorNombreReal(nombreDeUbicacion)

        if (nuevaUbicacion == null || vector == null) {
            throw ErrorDeMovimiento()
        }

        this.comprobarViabilidadUbi(vector.ubicacion!!.getNombre()!!, nuevaUbicacion.getNombre()!!,
            TipoVector.puedeCruzar(vector))

        val nodosHastaDestino = ubicacionNeoDAO.caminoIdeal(vector.ubicacion!!.getNombre()!!, nuevaUbicacion.getNombre()!!,
            TipoVector.puedeCruzar(vector)).drop(0)

        val listaDeUbicaciones = nodosHastaDestino.map { it.getNombre()!! }

        for (ubi in listaDeUbicaciones) {
            this.moverHasta(vector, ubi)
        }
    }

    override fun deleteAll() {
        ubicacionNeoDAO.detachDelete()
    }

}
