package ar.edu.unq.eperdemic

import ar.edu.unq.eperdemic.modelo.Patogeno
import ar.edu.unq.eperdemic.persistencia.dao.PatogenoDAO
import ar.edu.unq.eperdemic.persistencia.dao.impl.HibernatePatogenoDAO
import ar.edu.unq.eperdemic.services.PatogenoService
import ar.edu.unq.eperdemic.services.impl.PatogenoServiceImpl
import org.junit.jupiter.api.*
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.api.assertThrows

@TestInstance(PER_CLASS)
class PatogenoServiceTest {

    private val dao: PatogenoDAO = HibernatePatogenoDAO()
    lateinit var patogeno: Patogeno
    lateinit var servicio: PatogenoService

    @BeforeEach
    fun crearModelo() {

        this.servicio = PatogenoServiceImpl(dao)
        this.patogeno = Patogeno("Coronavirus")
        dao.crear(patogeno)

    }

    @Test
    fun testCrearPatogeno() {
        servicio.crearPatogeno(patogeno)

    val covid = servicio.recuperarPatogeno(patogeno.id!!)
    Assertions.assertEquals("Coronavirus",covid.toString())

    }



}