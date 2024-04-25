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

class PatogenoServiceImpl(
    private val patogenoDAO: PatogenoDAO,
    private val especieDAO: EspecieDAO,
    private val ubicacionDAO: UbicacionDAO,
    private val vectorDAO: VectorDAO
    ) : PatogenoService {


    override fun crear(patogeno: Patogeno): Patogeno {
        return runTrx { patogenoDAO.crear(patogeno) }
    }

    override fun updatear(patogeno: Patogeno) {
        runTrx { patogenoDAO.actualizar(patogeno) }
    }

    override fun recuperar(id: Long): Patogeno {
        return runTrx {
            val patogeno = patogenoDAO.recuperar(id)
            if (patogeno == null) {
                throw NoExisteElPatogeno()
            }
            patogeno
        }
    }

    override fun recuperarTodos(): List<Patogeno> {
        return runTrx { patogenoDAO.recuperarATodos() }
    }

    override fun agregarEspecie(idDePatogeno: Long, nombreEspecie: String, ubicacionId: Long): Especie {

        return runTrx {

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

        }
    }

    override fun especiesDePatogeno(patogenoId: Long, direccion: Direccion, pagina: Int, cantidadPorPagina:Int): List<Especie> {
        return runTrx {
            if (pagina == null || pagina < 0 || cantidadPorPagina < 0) {
                throw ErrorValorDePaginacionIvalido()
            }
            val especies = especieDAO.especiesDelPatogenoId(patogenoId, direccion, pagina, cantidadPorPagina)
            especies
        }
    }

    override fun esPandemia(especieId: Long): Boolean {
        return runTrx { vectorDAO.cantidadDeUbicacionesDeVectoresConEspecieId(especieId) > ubicacionDAO.cantidadDeUbicaciones() / 2 }
    }

}