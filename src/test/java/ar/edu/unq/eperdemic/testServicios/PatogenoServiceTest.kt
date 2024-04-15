package ar.edu.unq.eperdemic.testServicios

import ar.edu.unq.eperdemic.helper.dao.HibernateDataDAO
import ar.edu.unq.eperdemic.helper.service.DataService
import ar.edu.unq.eperdemic.helper.service.DataServiceImpl
import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.Patogeno
import ar.edu.unq.eperdemic.persistencia.dao.EspecieDAO
import ar.edu.unq.eperdemic.persistencia.dao.PatogenoDAO
import ar.edu.unq.eperdemic.persistencia.dao.hibernate.HibernateEspecieDAO
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
    private val especieDao: EspecieDAO = HibernateEspecieDAO()

    @BeforeEach
    fun crearModelo() {

        dataService = DataServiceImpl( HibernateDataDAO() )
        this.servicio = PatogenoServiceImpl(dao, especieDao)
        this.patogeno = Patogeno("Coronavirus")

        this.servicio.crear(patogeno)

    }

    @Test
    fun testCrearYRecuperarPatogeno() {

        val covid = this.servicio.recuperar(patogeno.id!!)
        Assertions.assertEquals("Coronavirus",covid.toString())
        Assertions.assertEquals(1, covid.id)

    }

    /*@Test
    fun testActualizarPatogeno() {

        this.servicio.crearPatogeno(patogeno)

        this.servicio.updatearPatogeno(patogeno)
        Assertions.assertEquals("Coronavirus",covid.toString())

    }
    */

    @Test
    fun alAgregarUnaEspecieAUnPatogenoLaMismaApareceEnElPatogeno() {
        var especie: Especie = servicio.agregarEspecie(patogeno.id!!, "Virus", "Corea")

        var otroPatogeno : Patogeno = servicio.recuperar(patogeno.id!!)

        Assertions.assertTrue(otroPatogeno.especies.contains(especie))
    }

    @AfterEach
    fun borrarRegistros() {
        dataService.cleanAll()
    }

}