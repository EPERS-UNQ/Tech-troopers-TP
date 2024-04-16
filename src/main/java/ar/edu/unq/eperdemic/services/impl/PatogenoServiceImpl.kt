package ar.edu.unq.eperdemic.services.impl

import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.Patogeno
import ar.edu.unq.eperdemic.modelo.Ubicacion
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
    private val vectoresDAO: VectorDAO
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

    override fun agregarEspecie(idDePatogeno: Long, nombreEspecie: String, ubicacionId: Long): Especie {
        return runTrx {
            val patogeno: Patogeno = patogenoDAO.recuperar(idDePatogeno)
            val paisDeOrigen = ubicacionDAO.recuperar(ubicacionId)
            val especie = patogeno.crearEspecie(nombreEspecie, paisDeOrigen.nombre!!)

            //val ubicaciones = ubicacionDAO.recuperarTodos()
            //val ubicacion = ubicaciones.find { it.nombre == paisDeOrigen }
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

    override fun esPandemia(especieId: Long): Boolean {
        return runTrx {

            val especie = especieDAO.recuperar(especieId)
            val cantUbicaciones = ubicacionDAO.recuperarTodos().size
            val vectores = vectoresDAO.recuperarTodos()
            //val vectoresConEspecie = vectores.stream().filter {
            //    it.especies.contains(especie)
            //}
            val ubicacionesDeVectoresConEspecie = HashSet<Ubicacion>()
            for (v in vectores) {
                if (v.especies.contains(especie)) {
                    ubicacionesDeVectoresConEspecie.add(v.ubicacion!!)
                }
            }

            ubicacionesDeVectoresConEspecie.size > cantUbicaciones/ 2
        }
    }

}