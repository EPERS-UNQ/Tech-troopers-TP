package ar.edu.unq.eperdemic.services.impl

import ar.edu.unq.eperdemic.exceptions.NoExisteLaEspecie
import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.persistencia.dao.EspecieDAO

import ar.edu.unq.eperdemic.persistencia.dao.PatogenoDAO
import ar.edu.unq.eperdemic.persistencia.dao.UbicacionDAO
import ar.edu.unq.eperdemic.persistencia.dao.VectorDAO
import ar.edu.unq.eperdemic.services.EspecieService
import ar.edu.unq.eperdemic.services.runner.HibernateTransactionRunner.runTrx
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class EspecieServiceImpl () : EspecieService {
    @Autowired private lateinit var especieDAO : EspecieDAO
    @Autowired private lateinit var vectorDAO : VectorDAO

    override fun updatear(especie: Especie) {
        especieDAO.save(especie)
    }

    override fun recuperar(idEspecie: Long): Especie {

        val especie = especieDAO.findById(idEspecie).orElse(null)
        if (especie == null) {
            throw NoExisteLaEspecie()
        }

        return especie
    }

    override fun recuperarTodos(): List<Especie> {
        return especieDAO.findAll().toList()
    }

    override fun cantidadDeInfectados(especieId: Long): Int {
       //return vectorDAO.countVectorsByEspeciesIn(especieId)
        TODO()
    }

}