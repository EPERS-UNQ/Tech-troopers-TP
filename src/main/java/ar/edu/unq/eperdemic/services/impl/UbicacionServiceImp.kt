package ar.edu.unq.eperdemic.services.impl

import ar.edu.unq.eperdemic.exceptions.ErrorDeMovimiento
import ar.edu.unq.eperdemic.exceptions.NoExisteLaUbicacion
import ar.edu.unq.eperdemic.exceptions.UbicacionMuyLejana
import ar.edu.unq.eperdemic.exceptions.UbicacionNoAlcanzable
import ar.edu.unq.eperdemic.modelo.RandomGenerator.RandomGenerator
import ar.edu.unq.eperdemic.modelo.Ubicacion
import ar.edu.unq.eperdemic.modelo.camino.Camino
import ar.edu.unq.eperdemic.modelo.camino.TipoDeCamino
import ar.edu.unq.eperdemic.modelo.vector.Vector
import ar.edu.unq.eperdemic.persistencia.dao.neo4j.Neo4jUbicacionDAO
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
    @Autowired private lateinit var ubicacionNeoDAO: Neo4jUbicacionDAO
    @Autowired private lateinit var vectorDAO: VectorDAO

    override fun crear(ubicacion: Ubicacion) : Ubicacion {
        ubicacionDAO.save(ubicacion)
        ubicacionNeoDAO.save(ubicacion)
        return ubicacion
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

        this.comprobarViabilidadUbi(vector.ubicacion!!.getNombre()!!, nuevaUbicacion.getNombre()!!,
                                        TipoDeCamino.puedeCruzar(vector.getTipo()))

        val nodosHastaDestino = ubicacionNeoDAO.caminoIdeal(vector.ubicacion!!.getNombre()!!, nuevaUbicacion.getNombre()!!,
            TipoDeCamino.puedeCruzar(vector.getTipo()))

        for (nodo in nodosHastaDestino) {
            this.moverHasta(vector, nodo)
        }
    }

    private fun moverHasta(vector: Vector, ubicacion: Ubicacion) {
        vector.ubicacion = ubicacion
        vectorDAO.save(vector)

        val todosLosVectores = vectorDAO.recuperarTodosDeUbicacion(ubicacion.getId()!!)

        if (vector.estaInfectado()) {
            todosLosVectores.map {
                vector.contargiarA(it)
                vectorDAO.save(it)
            }
        }
    }

    private fun comprobarViabilidadUbi(nomUbiInicio: String, nomUbiFin: String, tiposPermitidos: List<String>) {
        if(!ubicacionNeoDAO.esUbicacionCercana(nomUbiInicio,nomUbiFin)) {
            throw UbicacionMuyLejana()
        }
        if(!ubicacionNeoDAO.esUbicacionAlcanzable(nomUbiFin, nomUbiFin, tiposPermitidos)) {
            throw UbicacionNoAlcanzable()
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
        val caminoAConvertir: Camino = Camino()
        ubicacionNeoDAO.conectarCaminos(nombreDeUbicacion1, nombreDeUbicacion2, caminoAConvertir.convertirACamino(tipoCamino))
    }

    override fun conectados(nombreDeUbicacion: String): List<Ubicacion> {
        return ubicacionNeoDAO.ubicacionesConectadas(nombreDeUbicacion)
    }

    override fun moverPorCaminoMasCorto(vectorId: Long, nombreDeUbicacion: String) {
        TODO("Not yet implemented") // algoritmo de dijkstra
    }
}


















