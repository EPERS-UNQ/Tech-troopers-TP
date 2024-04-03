package ar.edu.unq.eperdemic.services.impl

import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.Patogeno
import ar.edu.unq.eperdemic.persistencia.dao.PatogenoDAO
import ar.edu.unq.eperdemic.persistencia.dao.jdbc.JDBCPatogenoDAO
import ar.edu.unq.eperdemic.services.PatogenoService


class PatogenoServiceImpl(val patogenoDAO: PatogenoDAO) : PatogenoService {
    private val dao: PatogenoDAO = JDBCPatogenoDAO()
    override fun crearPatogeno(patogeno: Patogeno): Patogeno {
        return dao.crear(patogeno)
    }

    override fun recuperarPatogeno(id: Long): Patogeno {
        return dao.recuperar(id)
    }

    override fun recuperarATodosLosPatogenos(): List<Patogeno> {
        return dao.recuperarATodos()
    }

    override fun agregarEspecie(idDePatogeno: Long, nombreEspecie: String, paisDeOrigen: String): Especie {
        val patogeno: Patogeno = dao.recuperar(idDePatogeno)
        val especie: Especie = patogeno.crearEspecie(nombreEspecie, paisDeOrigen)
        dao.actualizar(patogeno)
        return especie
    }

}