package ar.edu.unq.eperdemic.services.impl

import ar.edu.unq.eperdemic.exceptions.ErrorValorDePaginacionIvalido
import ar.edu.unq.eperdemic.exceptions.IDDePatogenoRepetido
import ar.edu.unq.eperdemic.exceptions.NoExisteElPatogeno
import ar.edu.unq.eperdemic.exceptions.NoHayVectorException
import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.Patogeno
import ar.edu.unq.eperdemic.modelo.Direccion
import ar.edu.unq.eperdemic.modelo.RandomGenerator
import ar.edu.unq.eperdemic.modelo.Ubicacion
import ar.edu.unq.eperdemic.modelo.vector.Vector
import ar.edu.unq.eperdemic.persistencia.dao.EspecieDAO
import ar.edu.unq.eperdemic.persistencia.dao.PatogenoDAO
import ar.edu.unq.eperdemic.persistencia.dao.UbicacionDAO
import ar.edu.unq.eperdemic.persistencia.dao.VectorDAO
import ar.edu.unq.eperdemic.services.PatogenoService
import ar.edu.unq.eperdemic.services.runner.HibernateTransactionRunner.runTrx
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class PatogenoServiceImpl(

    private val especieDAO: EspecieDAO,
    private val ubicacionDAO: UbicacionDAO,
    private val vectorDAO: VectorDAO
    /*@Autowired private lateinit var especieDAO: EspecieDAO,
    @Autowired private lateinit var ubicacionDAO: UbicacionDAO,
    @Autowired private lateinit var vectorDAO: VectorDAO*/
    ) : PatogenoService {

    @Autowired private lateinit var patogenoDAO: PatogenoDAO

    override fun crear(patogeno: Patogeno): Patogeno {
        return try {
            patogenoDAO.save(patogeno)
        } catch (e: DataIntegrityViolationException) {
            throw IDDePatogenoRepetido(patogeno.getId()!!)
        }
    }

    override fun updatear(patogeno: Patogeno) {
        runTrx { patogenoDAO.actualizar(patogeno) }
    }

    override fun recuperar(id: Long): Patogeno? {
        return patogenoDAO.findByIdOrNull(id)
    }

    override fun recuperarTodos(): List<Patogeno> {
        return patogenoDAO.findAll().toList()
    }

    override fun agregarEspecie(idDePatogeno: Long, nombreEspecie: String, ubicacionId: Long): Especie {

        return runTrx {

            val randomize = RandomGenerator()
            val patogeno: Patogeno = patogenoDAO.recuperar(idDePatogeno)
            val paisDeOrigen = ubicacionDAO.recuperar(ubicacionId)
            val especie = patogeno.crearEspecie(nombreEspecie, paisDeOrigen.getNombre()!!)
            val vectoresEnUbicacion: List<Vector> = vectorDAO.recuperarTodosDeUbicacion(ubicacionId)
            if (vectoresEnUbicacion.isEmpty()) {
                throw NoHayVectorException()
            }
            val vectorAInfectar = randomize.getElementoRandomEnLista(vectoresEnUbicacion)
            vectorAInfectar.infectar(especie)
            patogenoDAO.actualizar(patogeno)
            especieDAO.crear(especie)
            especie
        }
    }

    override fun especiesDePatogeno(patogenoId: Long, direccion: Direccion, pagina: Int, cantidadPorPagina:Int): List<Especie> {
        return runTrx {
            if (pagina == null || pagina < 0 || cantidadPorPagina < 0) {
                throw ErrorValorDePaginacionIvalido()
            }
            val patogeno: Patogeno = patogenoDAO.recuperar(patogenoId)
            val especies = especieDAO.especiesDelPatogeno(patogeno, direccion, pagina, cantidadPorPagina)
            especies
        }
    }

    override fun esPandemia(especieId: Long): Boolean {
        return runTrx {

            val especie = especieDAO.recuperar(especieId)
            val cantUbicaciones = ubicacionDAO.recuperarTodos().size
            val vectores = vectorDAO.recuperarTodos()
            val ubicacionesDeVectoresConEspecie = HashSet<Ubicacion>()
            for (v in vectores) {
                if (v.estaInfectadoCon(especie)) {
                    ubicacionesDeVectoresConEspecie.add(v.ubicacion!!)
                }
            }
            ubicacionesDeVectoresConEspecie.size > cantUbicaciones/ 2
        }
    }

}