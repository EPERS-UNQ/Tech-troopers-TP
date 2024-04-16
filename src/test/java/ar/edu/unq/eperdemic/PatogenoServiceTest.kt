package ar.edu.unq.eperdemic

import ar.edu.unq.eperdemic.helper.dao.HibernateDataDAO
import ar.edu.unq.eperdemic.helper.service.DataService
import ar.edu.unq.eperdemic.helper.service.DataServiceImpl
import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.Patogeno
import ar.edu.unq.eperdemic.modelo.Ubicacion
import ar.edu.unq.eperdemic.modelo.vector.TipoVector
import ar.edu.unq.eperdemic.persistencia.dao.EspecieDAO
import ar.edu.unq.eperdemic.persistencia.dao.PatogenoDAO
import ar.edu.unq.eperdemic.persistencia.dao.UbicacionDAO
import ar.edu.unq.eperdemic.persistencia.dao.VectorDAO
import ar.edu.unq.eperdemic.persistencia.dao.hibernate.HibernateEspecieDAO
import ar.edu.unq.eperdemic.persistencia.dao.hibernate.HibernatePatogenoDAO
import ar.edu.unq.eperdemic.persistencia.dao.hibernate.HibernateUbicacionDAO
import ar.edu.unq.eperdemic.persistencia.dao.hibernate.HibernateVectorDAO
import ar.edu.unq.eperdemic.services.PatogenoService
import ar.edu.unq.eperdemic.services.UbicacionService
import ar.edu.unq.eperdemic.services.impl.PatogenoServiceImpl
import ar.edu.unq.eperdemic.services.impl.UbicacionServiceImp
import ar.edu.unq.eperdemic.modelo.vector.Vector
import ar.edu.unq.eperdemic.services.VectorService
import ar.edu.unq.eperdemic.services.impl.VectorServiceImp
import org.junit.jupiter.api.*
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS

@TestInstance(PER_CLASS)
class PatogenoServiceTest {

    lateinit var dataService: DataService
    lateinit var covid: Patogeno
    lateinit var salmonella: Patogeno
    lateinit var china: Ubicacion
    lateinit var corea: Ubicacion
    lateinit var humano: Vector
    lateinit var humano1: Vector
    lateinit var servicio: PatogenoService
    lateinit var servicioUbicacion: UbicacionService
    lateinit var servicioVector: VectorService

    private val patogenoDao: PatogenoDAO = HibernatePatogenoDAO()
    private val especieDao: EspecieDAO = HibernateEspecieDAO()
    private val vectorDao: VectorDAO = HibernateVectorDAO()
    private val ubicacionDao: UbicacionDAO = HibernateUbicacionDAO()

    @BeforeEach
    fun crearModelo() {

        dataService = DataServiceImpl(HibernateDataDAO())
        this.servicio = PatogenoServiceImpl(patogenoDao, especieDao, ubicacionDao, vectorDao)
        this.servicioUbicacion = UbicacionServiceImp()
        this.servicioVector = VectorServiceImp(vectorDao, especieDao)
        this.covid = Patogeno("Coronavirus")
        this.salmonella = Patogeno("Salmonella")
        this.china = Ubicacion("China")
        this.corea = Ubicacion("Corea")
        this.humano = Vector("Pedro", corea, TipoVector.HUMANO)
        this.humano1 = Vector("Pepe", china, TipoVector.HUMANO)

        this.servicio.crear(covid)
        this.servicioUbicacion.crear(corea)
        this.servicioUbicacion.crear(china)

    }

    @Test
    fun testCrearYRecuperarPatogeno() {

        val covid = this.servicio.recuperar(covid.id!!)

        Assertions.assertEquals("Coronavirus", covid.toString())
        Assertions.assertEquals(1, covid.id)

    }

    @Test
    fun testActualizarPatogeno() {

        val covid = this.servicio.recuperar(covid.id!!)
        covid.crearEspecie("Especie 1", "Arg")
        this.servicio.updatear(covid)

        Assertions.assertEquals(covid.cantidadDeEspecies, 1)
        Assertions.assertEquals(covid.id, 1)

    }

    @Test
    fun seRecuperanTodosLosPatogenos() {

        val covid = this.servicio.recuperar(covid.id!!)
        val patogenos = this.servicio.recuperarTodos()

        Assertions.assertEquals(patogenos[0].toString(), covid.toString())

    }

    @Test
    fun seAgregaUnaEspecieNueva() {

        val especie: Especie = servicio.agregarEspecie(covid.id!!, "Virus", corea.id!!)
        val covid = this.servicio.recuperar(covid.id!!)

        Assertions.assertEquals(covid.cantidadDeEspecies, 1)
        Assertions.assertEquals(especie.patogeno.toString(), covid.toString())

    }

    @Test
    fun seRecuperanTodasLasEspeciesDelPatogeno() {

        this.servicio.crear(salmonella)

        val enterica: Especie = servicio.agregarEspecie(salmonella.id!!, "Enterica", china.id!!)
        val bongori: Especie = servicio.agregarEspecie(salmonella.id!!, "Bongori", corea.id!!)

        val especies = this.servicio.especiesDePatogeno(salmonella.id!!)

        Assertions.assertEquals(especies.size, 2)
        Assertions.assertEquals(especies[0].nombre, enterica.nombre)
        Assertions.assertEquals(especies[1].nombre, bongori.nombre)

    }

    @Test
    fun seSabeSiEsPandemia() {

        this.servicio.crear(salmonella)
        this.servicioVector.crear(humano)
        this.servicioVector.crear(humano1)

        val enterica: Especie = servicio.agregarEspecie(salmonella.id!!, "Enterica", corea.id!!)

        this.servicioVector.infectar(humano.getId()!!,enterica.id!!)
        this.servicioVector.infectar(humano1.getId()!!,enterica.id!!)

        Assertions.assertTrue(servicio.esPandemia(enterica.id!!))

    }

    @AfterEach
    fun borrarRegistros() {
        dataService.cleanAll()
    }

}