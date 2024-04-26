package ar.edu.unq.eperdemic.services.impl

import ar.edu.unq.eperdemic.exceptions.ErrorValorDePaginacionIvalido
import ar.edu.unq.eperdemic.exceptions.NoExisteElPatogeno
import ar.edu.unq.eperdemic.exceptions.NoHayVectorException
import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.Patogeno
import ar.edu.unq.eperdemic.modelo.Direccion
import ar.edu.unq.eperdemic.modelo.RandomGenerator.RandomGenerator
import ar.edu.unq.eperdemic.modelo.vector.Vector
import ar.edu.unq.eperdemic.persistencia.dao.EspecieDAO
import ar.edu.unq.eperdemic.persistencia.dao.PatogenoDAO
import ar.edu.unq.eperdemic.persistencia.dao.UbicacionDAO
import ar.edu.unq.eperdemic.persistencia.dao.VectorDAO
import ar.edu.unq.eperdemic.services.PatogenoService
import ar.edu.unq.eperdemic.services.runner.HibernateTransactionRunner.runTrx
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class PatogenoServiceImpl() : PatogenoService {

    @Autowired private lateinit var patogenoDAO: PatogenoDAO
    /*@Autowired private lateinit var especieDAO: EspecieDAO
    @Autowired private lateinit var ubicacionDAO: UbicacionDAO
    @Autowired private lateinit var vectorDAO: VectorDAO*/


    override fun crear(patogeno: Patogeno): Patogeno {
        return patogenoDAO.save(patogeno)
    }

    override fun updatear(patogeno: Patogeno) {
        //runTrx { patogenoDAO.actualizar(patogeno) }
        TODO()
    }

    override fun recuperar(id: Long): Patogeno {
        return patogenoDAO.findByIdOrNull(id)!!
    }

    override fun recuperarTodos(): List<Patogeno> {
        //return runTrx { patogenoDAO.recuperarATodos() }
        TODO()
    }

    override fun agregarEspecie(idDePatogeno: Long, nombreEspecie: String, ubicacionId: Long): Especie {

        /*return runTrx {

            val patogeno: Patogeno = patogenoDAO.recuperar(idDePatogeno)
            val especie = patogeno.crearEspecie(nombreEspecie, ubicacionDAO.recuperarPorNombre(ubicacionId))
            val vectoresEnUbicacion: List<Vector> = vectorDAO.recuperarTodosDeUbicacion(ubicacionId)
            if (vectoresEnUbicacion.isEmpty()) {
                throw NoHayVectorException()
            }
            val vectorAInfectar = RandomGenerator.getInstance().getElementoRandomEnLista(vectoresEnUbicacion)
            vectorAInfectar.infectar(especie)
            patogenoDAO.actualizar(patogeno)
            especieDAO.crear(especie)
            especie

        }*/
        TODO()
    }

    override fun especiesDePatogeno(patogenoId: Long, direccion: Direccion, pagina: Int, cantidadPorPagina:Int): List<Especie> {
        /*return runTrx {
            if (pagina == null || pagina < 0 || cantidadPorPagina < 0) {
                throw ErrorValorDePaginacionIvalido()
            }
            val especies = especieDAO.especiesDelPatogenoId(patogenoId, direccion, pagina, cantidadPorPagina)
            especies
        }*/
        TODO()
    }

    override fun esPandemia(especieId: Long): Boolean {
        //return runTrx { vectorDAO.cantidadDeUbicacionesDeVectoresConEspecieId(especieId) > ubicacionDAO.cantidadDeUbicaciones() / 2 }
        TODO()
    }

}