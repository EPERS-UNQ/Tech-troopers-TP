package ar.edu.unq.eperdemic

import ar.edu.unq.eperdemic.modelo.Patogeno
import ar.edu.unq.eperdemic.persistencia.dao.PatogenoDAO
import ar.edu.unq.eperdemic.persistencia.dao.hibernate.HibernatePatogenoDAO
import ar.edu.unq.eperdemic.services.PatogenoService
import ar.edu.unq.eperdemic.services.impl.PatogenoServiceImpl
import org.junit.jupiter.api.*
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS

@TestInstance(PER_CLASS)
class PatogenoServiceTest {

    lateinit var dataService : DataService
    lateinit var patogeno: Patogeno
    lateinit var servicio: PatogenoService

    private val dao: PatogenoDAO = HibernatePatogenoDAO()

    @BeforeEach
    fun crearModelo() {

        dataService = DataServiceImpl( HibernateDataDAO() )
        this.servicio = PatogenoServiceImpl(dao)
        this.patogeno = Patogeno("Coronavirus")
        dao.crear(patogeno)

    }

    @Test
    fun testCrearYRecuperarPatogeno() {

        this.servicio.crearPatogeno(patogeno)

        val covid = this.servicio.recuperarPatogeno(patogeno.id!!)
        Assertions.assertEquals("Coronavirus",covid.toString())

    }

    /*@Test
    fun testActualizarPatogeno() {

        this.servicio.crearPatogeno(patogeno)

        this.servicio.updatearPatogeno(patogeno)
        Assertions.assertEquals("Coronavirus",covid.toString())

    }
    */

    @AfterEach
    fun borrarRegistros() {
        dataService.cleanAll()
    }

}