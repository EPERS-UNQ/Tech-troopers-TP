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
import ar.edu.unq.eperdemic.persistencia.dao.UbicacionJpaDAO
import ar.edu.unq.eperdemic.persistencia.dao.VectorDAO
import ar.edu.unq.eperdemic.services.PatogenoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class PatogenoServiceImpl() : PatogenoService {

    @Autowired private lateinit var patogenoDAO: PatogenoDAO
    @Autowired private lateinit var especieDAO: EspecieDAO
    @Autowired private lateinit var ubicacionDAO: UbicacionJpaDAO
    @Autowired private lateinit var vectorDAO: VectorDAO

    override fun crear(patogeno: Patogeno): Patogeno {
        return patogenoDAO.save(patogeno)
    }

    override fun updatear(patogeno: Patogeno) {
        patogenoDAO.save(patogeno)
    }

    override fun recuperar(id: Long): Patogeno {

        val patogeno = patogenoDAO.findByIdOrNull(id)
        if (patogeno == null) {
            throw NoExisteElPatogeno()
        }
        return patogeno

    }

    override fun recuperarTodos(): List<Patogeno> {
        return patogenoDAO.findAll().toList()
    }

    override fun agregarEspecie(idDePatogeno: Long, nombreEspecie: String, ubicacionId: Long): Especie {

            val patogeno: Patogeno = patogenoDAO.findByIdOrNull(idDePatogeno)!!
            val especie = patogeno.crearEspecie(nombreEspecie, ubicacionDAO.recuperarNombrePorID(ubicacionId))
            val vectoresEnUbicacion: List<Vector> = vectorDAO.recuperarTodosDeUbicacion(ubicacionId)
            if (vectoresEnUbicacion.isEmpty()) {
                throw NoHayVectorException()
            }
            val vectorAInfectar = RandomGenerator.getInstance().getElementoRandomEnLista(vectoresEnUbicacion)
            vectorAInfectar.infectar(especie)
            patogenoDAO.save(patogeno)
            return especieDAO.save(especie)
    }

    override fun especiesDePatogeno(patogenoId: Long, direccion: Direccion, pagina: Int, cantidadPorPagina:Int): List<Especie> {

        if (pagina == null || pagina < 0 || cantidadPorPagina < 0) {
            throw ErrorValorDePaginacionIvalido()
        }
        val pageable: Pageable = PageRequest.of(pagina, cantidadPorPagina)

        val especies = especieDAO.especiesDelPatogenoId(patogenoId, direccion.getString(), pageable)
        return especies

    }

    override fun esPandemia(especieId: Long): Boolean {
        return vectorDAO.cantidadDeUbicacionesDeVectoresConEspecieId(especieId) > ubicacionDAO.countUbicaciones() / 2
    }
}