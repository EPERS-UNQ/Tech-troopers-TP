package ar.edu.unq.eperdemic.services.impl

import ar.edu.unq.eperdemic.exceptions.NoExisteLaEspecie
import ar.edu.unq.eperdemic.modelo.mutacion.Mutacion
import ar.edu.unq.eperdemic.persistencia.dao.EspecieDAO
import ar.edu.unq.eperdemic.persistencia.dao.MutacionDAO
import ar.edu.unq.eperdemic.services.MutacionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MutacionServiceImpl() : MutacionService {

    @Autowired private lateinit var mutacionDAO: MutacionDAO
    @Autowired private lateinit var especieDAO: EspecieDAO

    override fun agregarMutacion(especieId: Long, mutacion: Mutacion) {

        val especie = especieDAO.findByIdOrNull(especieId)!!

        if (especie == null) {
            throw NoExisteLaEspecie()
        }
        especie.agregarNuevaMutacionPosible(mutacion)
        especieDAO.save(especie)
        mutacionDAO.save(mutacion)

    }

}