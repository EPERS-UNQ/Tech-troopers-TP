package ar.edu.unq.eperdemic.services.impl

import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.Patogeno
import ar.edu.unq.eperdemic.persistencia.dao.EspecieDAO
import ar.edu.unq.eperdemic.persistencia.dao.PatogenoDAO
import ar.edu.unq.eperdemic.persistencia.dao.UbicacionDAO
import ar.edu.unq.eperdemic.services.PatogenoService
import ar.edu.unq.eperdemic.services.runner.HibernateTransactionRunner.runTrx


class PatogenoServiceImpl(
    private val patogenoDAO: PatogenoDAO,
    private val especieDAO: EspecieDAO,
    private val ubicacionDAO: UbicacionDAO
    ) : PatogenoService {


    override fun crear(patogeno: Patogeno): Patogeno {
        return runTrx { patogenoDAO.crear(patogeno) }
    }

    override fun updatear(patogeno: Patogeno) {
        runTrx { patogenoDAO.actualizar(patogeno) }
    }

    override fun recuperar(id: Long): Patogeno {
        return runTrx { patogenoDAO.recuperar(id) }
    }

    override fun recuperarTodos(): List<Patogeno> {
        return runTrx { patogenoDAO.recuperarATodos() }
    }

    override fun agregarEspecie(idDePatogeno: Long, nombreEspecie: String, paisDeOrigen: String): Especie {
        return runTrx {
            val patogeno: Patogeno = patogenoDAO.recuperar(idDePatogeno)
            val especie = patogeno.crearEspecie(nombreEspecie, paisDeOrigen)
            patogenoDAO.actualizar(patogeno)
            especieDAO.crear(especie)
            especie
            //falta infectar uno al azar
        }
    }

    override fun especiesDePatogeno(patogenoId: Long): List<Especie> {
        return runTrx {
            val patogeno: Patogeno = patogenoDAO.recuperar(patogenoId)
            val especies = especieDAO.especiesDelPatogeno(patogeno)
            especies
        }
    }

    override fun esPademia(especieId: Long): Boolean {
        return runTrx {
            val ubicaciones = ubicacionDAO.recuperarTodos()
            //val resultado = ubicacionDAO.apareceEnMasDePaises(ubicaciones, especieId)
            true
        }
    }

}