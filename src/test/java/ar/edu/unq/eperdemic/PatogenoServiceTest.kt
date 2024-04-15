package ar.edu.unq.eperdemic

import ar.edu.unq.eperdemic.helper.dao.HibernateDataDAO
import ar.edu.unq.eperdemic.helper.service.DataService
import ar.edu.unq.eperdemic.helper.service.DataServiceImpl
import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.Patogeno
import ar.edu.unq.eperdemic.persistencia.dao.EspecieDAO
import ar.edu.unq.eperdemic.persistencia.dao.PatogenoDAO
import ar.edu.unq.eperdemic.persistencia.dao.UbicacionDAO
import ar.edu.unq.eperdemic.persistencia.dao.hibernate.HibernateEspecieDAO
import ar.edu.unq.eperdemic.persistencia.dao.hibernate.HibernatePatogenoDAO
import ar.edu.unq.eperdemic.persistencia.dao.hibernate.HibernateUbicacionDAO
import ar.edu.unq.eperdemic.services.PatogenoService
import ar.edu.unq.eperdemic.services.impl.PatogenoServiceImpl
import org.junit.jupiter.api.*
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS

@TestInstance(PER_CLASS)
class PatogenoServiceTest {

    lateinit var dataService : DataService
    lateinit var covid: Patogeno
    lateinit var salmonella: Patogeno
    lateinit var servicio: PatogenoService

    private val patogenoDao: PatogenoDAO = HibernatePatogenoDAO()
    private val especieDao: EspecieDAO = HibernateEspecieDAO()
    private val ubicacionDao: UbicacionDAO = HibernateUbicacionDAO()

    @BeforeEach
    fun crearModelo() {

        dataService = DataServiceImpl( HibernateDataDAO() )
        this.servicio = PatogenoServiceImpl(patogenoDao, especieDao, ubicacionDao)
        this.covid = Patogeno("Coronavirus")
        this.salmonella = Patogeno("Salmonella")

        this.servicio.crear(covid)

    }

    @Test
    fun testCrearYRecuperarPatogeno() {

        val covid = this.servicio.recuperar(covid.id!!)

        Assertions.assertEquals("Coronavirus",covid.toString())
        Assertions.assertEquals(1, covid.id)

    }

    @Test
    fun testActualizarPatogeno() {

        val covid = this.servicio.recuperar(covid.id!!)
        covid.crearEspecie("Especie 1", "Arg")
        this.servicio.updatear(covid)

        Assertions.assertEquals(1, covid.cantidadDeEspecies)
        Assertions.assertEquals(1, covid.id)

    }

    @Test
    fun seRecuperanTodosLosPatogenos() {

        val covid = this.servicio.recuperar(covid.id!!)
        val patogenos = this.servicio.recuperarTodos()

        Assertions.assertEquals(patogenos[0].toString(), covid.toString())

    }

    @Test
    fun seAgregaUnaEspecieNueva() {

        val especie: Especie = servicio.agregarEspecie(covid.id!!, "Virus", "Corea")
        val covid = this.servicio.recuperar(covid.id!!)

        Assertions.assertEquals(covid.cantidadDeEspecies, 1)
        Assertions.assertEquals(especie.patogeno.toString(), covid.toString())

    }

    @Test
    fun seRecuperanTodasLasEspeciesDelPatogeno() {

        this.servicio.crear(salmonella)

        val enterica: Especie = servicio.agregarEspecie(salmonella.id!!, "Enterica", "Corea")
        val bongori: Especie = servicio.agregarEspecie(salmonella.id!!, "Bongori", "Corea")

        val especies = this.servicio.especiesDePatogeno(salmonella.id!!)

        Assertions.assertEquals(especies.size, 2)
        Assertions.assertEquals(especies[0].nombre, enterica.nombre)
        Assertions.assertEquals(especies[1].nombre, bongori.nombre)

    }

    @AfterEach
    fun borrarRegistros() {
        dataService.cleanAll()
    }

}