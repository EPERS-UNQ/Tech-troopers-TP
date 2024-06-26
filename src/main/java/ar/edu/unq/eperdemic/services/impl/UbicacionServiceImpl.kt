package ar.edu.unq.eperdemic.services.impl

import ar.edu.unq.eperdemic.exceptions.*
import ar.edu.unq.eperdemic.modelo.RandomGenerator.RandomGenerator
import ar.edu.unq.eperdemic.modelo.ubicacion.UbicacionGlobal
import ar.edu.unq.eperdemic.modelo.ubicacion.UbicacionJpa
import ar.edu.unq.eperdemic.modelo.ubicacion.UbicacionMongo
import ar.edu.unq.eperdemic.modelo.vector.Vector
import ar.edu.unq.eperdemic.modelo.ubicacion.UbicacionNeo4j
import ar.edu.unq.eperdemic.modelo.vector.TipoVector
import ar.edu.unq.eperdemic.persistencia.dao.*
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
    @Autowired private lateinit var ubicacionMongoDAO: UbicacionMongoDAO
    @Autowired private lateinit var vectorDAO: VectorDAO
    @Autowired private lateinit var distritoDao: DistritoDAO

    override fun crear(ubicacion: UbicacionGlobal) : UbicacionGlobal {
        val ubicacionExistente = ubicacionJpaDAO.recuperarPorNombreReal(ubicacion.getNombre())
        if ( ubicacionExistente != null ) {
            throw ErrorYaExisteLaEntidad("La ubicación con ese nombre ya existe.")
        }

        val ubicacionPersistida = ubicacionJpaDAO.save(UbicacionJpa(ubicacion.getNombre()))
        ubicacionNeoDAO.save(UbicacionNeo4j(ubicacion.getNombre()))
        ubicacionMongoDAO.save(UbicacionMongo(ubicacion.getNombre(), ubicacion.getCoordenada())) // Ver si la coordenada existe.
        ubicacion.setId(ubicacionPersistida.getId()!!)

        return ubicacion
    }

    override fun updatear(ubicacion: UbicacionGlobal) {
        val ubicacionExistente = ubicacionJpaDAO.findByIdOrNull(ubicacion.getId()) ?: throw NoExisteLaUbicacion()

        ubicacionExistente.setNombre(ubicacion.getNombre())
        ubicacionJpaDAO.save(ubicacionExistente)
        ubicacionNeoDAO.save(UbicacionNeo4j(ubicacion.getNombre()))
        ubicacionMongoDAO.save(UbicacionMongo(ubicacion.getNombre(), ubicacion.getCoordenada()))

    }

    override fun recuperar(id: Long): UbicacionGlobal {

        val ubicacionJpa = ubicacionJpaDAO.findByIdOrNull(id)
            ?: throw NoExisteLaUbicacion()

        ubicacionNeoDAO.findByNombre(ubicacionJpa.getNombre()!!)
            ?: throw NoExisteLaUbicacion()

        val ubicacionMongo = ubicacionMongoDAO.findByNombre(ubicacionJpa.getNombre()!!)
            ?: throw NoExisteLaUbicacion()

        val ubicacionGlobal = UbicacionGlobal(ubicacionMongo.getNombre(), ubicacionMongo.getCordenada())

        ubicacionGlobal.setId(id)

        return ubicacionGlobal
    }


    override fun recuperarTodos(): List<UbicacionGlobal> {

        val listaUbicacionesGlobal: MutableList<UbicacionGlobal> = mutableListOf()
        val listaUbicacionMongo = ubicacionMongoDAO.findAll()

        for (ubi in listaUbicacionMongo) {
            listaUbicacionesGlobal.add(
                UbicacionGlobal(ubi.getNombre(), ubi.getCordenada())
            )
        }

        return listaUbicacionesGlobal
    }

    override fun moverPorCaminoMasCorto(vectorId: Long, nombreDeUbicacion: String) {

        val vector = vectorDAO.findById(vectorId).orElse(null)
        val nuevaUbicacion = ubicacionJpaDAO.recuperarPorNombreReal(nombreDeUbicacion)

        if (nuevaUbicacion == null || vector == null) {
            throw ErrorDeMovimiento()
        }

        if (vector.ubicacion!!.getNombre()!! == nuevaUbicacion.getNombre()!!) {
            return
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

    override fun mover(vectorId: Long, ubicacionId: Long) {

        val vector = vectorDAO.findById(vectorId).orElse(null)
        val nuevaUbicacion = ubicacionJpaDAO.findById(ubicacionId).orElse(null)

        if (nuevaUbicacion == null || vector == null) {
            throw ErrorDeMovimiento()
        }

        if (vector.ubicacion!!.getNombre()!! != nuevaUbicacion.getNombre()!!) {
            this.verificarSiPuedeMoverA(
                vector.ubicacion!!.getNombre()!!, nuevaUbicacion.getNombre()!!,TipoVector.puedeCruzar(vector)
            )
        }
        this.moverHasta(vector, nuevaUbicacion.getNombre()!!)

    }

    private fun moverHasta(vector: Vector, nombreUbi: String) {

        val nuevaUbicacion = ubicacionJpaDAO.recuperarPorNombreReal(nombreUbi)!!

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
        val ubicacionOrigen = ubicacionMongoDAO.findByNombre(nomUbiInicio)!!
        val ubicacionDestino = ubicacionMongoDAO.findByNombre(nomUbiFin)!!

        if(!ubicacionNeoDAO.esUbicacionLindante(nomUbiInicio,nomUbiFin)
            || ubicacionMongoDAO.ubicacionesAMenosDe100Km(ubicacionOrigen.coordenada!!, ubicacionDestino.getNombre()).isEmpty()) {
            throw ErrorUbicacionMuyLejana()
        }
        if(!ubicacionNeoDAO.hayCaminoCruzable(nomUbiInicio, nomUbiFin, tiposPermitidos)) {
            throw ErrorUbicacionNoAlcanzable()
        }
    }

    private fun comprobarViabilidadUbi(nomUbiInicio: String, nomUbiFin: String, tiposPermitidos: List<String>) {
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


    override fun conectados(nombreDeUbicacion: String): List<UbicacionGlobal> {

        val ubicacionJpa = ubicacionJpaDAO.recuperarPorNombreReal(nombreDeUbicacion)
        if (ubicacionJpa == null) {
            throw NoExisteLaUbicacion()
        }

        val ubicacionNeo = ubicacionNeoDAO.ubicacionesConectadas(nombreDeUbicacion)
        val ubicaciones: MutableList<UbicacionGlobal> = mutableListOf()

        for(u in ubicacionNeo) {
            val ubiMongo = ubicacionMongoDAO.findByNombre(u.getNombre()!!)

            ubicaciones.add(UbicacionGlobal(ubiMongo!!.getNombre(), ubiMongo.getCordenada()))
        }
        return ubicaciones
    }

    override fun deleteAll() {
        ubicacionNeoDAO.detachDelete()
        ubicacionMongoDAO.deleteAll()
    }

}
