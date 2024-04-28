package ar.edu.unq.eperdemic.services.impl

import ar.edu.unq.eperdemic.exceptions.NoExisteLaEspecie
import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.persistencia.dao.EspecieDAO
import ar.edu.unq.eperdemic.persistencia.dao.VectorDAO
import ar.edu.unq.eperdemic.services.EspecieService
import ar.edu.unq.eperdemic.services.runner.HibernateTransactionRunner.runTrx
/*
class EspecieServiceImpl (
    private val especieDAO : EspecieDAO,
    private val vectorDAO : VectorDAO
    ) : EspecieService {

    override fun updatear(especie: Especie) {
        runTrx { especieDAO.actualizar(especie) }
    }

    override fun recuperar(idEspecie: Long): Especie {
        return runTrx {
            val especie = especieDAO.recuperar(idEspecie)
            if (especie == null) {
                throw NoExisteLaEspecie()
            }
            especie
        }
    }

    override fun recuperarTodos(): List<Especie> {
        return runTrx { especieDAO.recuperarTodos() }
    }

    override fun cantidadDeInfectados(especieId: Long): Int {
        return runTrx {
            vectorDAO.cantidadDeVectoresConEspecie(especieId)
        }
    }
}
*/