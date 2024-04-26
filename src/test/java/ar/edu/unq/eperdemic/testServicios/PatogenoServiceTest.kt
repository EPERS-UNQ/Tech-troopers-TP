package ar.edu.unq.eperdemic.testServicios

import ar.edu.unq.eperdemic.exceptions.ErrorValorDePaginacionIvalido
import ar.edu.unq.eperdemic.exceptions.NoExisteElPatogeno
import ar.edu.unq.eperdemic.exceptions.NoHayVectorException
import ar.edu.unq.eperdemic.helper.dao.HibernateDataDAO
import ar.edu.unq.eperdemic.helper.service.DataService
import ar.edu.unq.eperdemic.helper.service.DataServiceImpl
import ar.edu.unq.eperdemic.modelo.Direccion
import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.Patogeno
import ar.edu.unq.eperdemic.modelo.RandomGenerator.NoAleatorioStrategy
import ar.edu.unq.eperdemic.modelo.RandomGenerator.RandomGenerator
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
    lateinit var random : RandomGenerator

    private val patogenoDao: PatogenoDAO = HibernatePatogenoDAO()
    private val especieDao: EspecieDAO = HibernateEspecieDAO()
    private val vectorDao: VectorDAO = HibernateVectorDAO()
    private val ubicacionDao: UbicacionDAO = HibernateUbicacionDAO()

    @BeforeEach
    fun crearModelo() {

        dataService = DataServiceImpl(HibernateDataDAO())
        this.servicio = PatogenoServiceImpl(patogenoDao, especieDao, ubicacionDao, vectorDao)
        this.servicioUbicacion = UbicacionServiceImp(ubicacionDao, vectorDao)
        this.servicioVector = VectorServiceImp(vectorDao, especieDao)
        this.covid = Patogeno("Coronavirus", 90, 5, 1, 60, 95)
        this.salmonella = Patogeno("Salmonella", 70, 10, 15, 30, 66)
        this.china = Ubicacion("China")
        this.corea = Ubicacion("Corea")
        this.humano = Vector("Pedro", corea, TipoVector.HUMANO)
        this.humano1 = Vector("Pepe", china, TipoVector.HUMANO)

        this.servicioUbicacion.crear(corea)
        this.servicioUbicacion.crear(china)

        random = RandomGenerator.getInstance()
        random.setStrategy(NoAleatorioStrategy())
        random.setNumeroGlobal(0)

    }

    @Test
    fun testCrearYRecuperarPatogeno() {

        servicio.crear(covid)
        val covid = servicio.recuperar(covid.getId()!!)

        Assertions.assertEquals(covid.toString(), "Coronavirus")
        Assertions.assertEquals(covid.getId(), 1)

    }

    @Test
    fun testSeTrataDeRecuperarUnPatogenoQueNoExiste() {

        Assertions.assertThrows(NoExisteElPatogeno::class.java) {
            servicio.recuperar(15)
        }

    }

    @Test
    fun testActualizarPatogeno() {

        servicio.crear(covid)

        val covid = servicio.recuperar(covid.getId()!!)
        covid.crearEspecie("Especie 1", "Arg")

        servicio.updatear(covid)

        Assertions.assertEquals(covid.cantidadDeEspecies, 1)
        Assertions.assertEquals(covid.getId(), 1)

    }

    @Test
    fun seRecuperanTodosLosPatogenos() {

        servicio.crear(covid)
        servicio.crear(salmonella)

        val covid = servicio.recuperar(covid.getId()!!)
        val patogenos = servicio.recuperarTodos()

        Assertions.assertEquals(patogenos.size, 2)
        Assertions.assertEquals(patogenos[0].toString(), covid.toString())

    }

    @Test
    fun seTrataDeRecuperarTodosLosPatogenosPeroNoHay() {

        val patogenos = servicio.recuperarTodos()

        Assertions.assertEquals(patogenos.size, 0)

    }

    @Test
    fun seAgregaUnaEspecieNueva() {

        servicio.crear(covid)
        servicioVector.crear(humano)

        val especie: Especie = servicio.agregarEspecie(covid.getId()!!, "Virus", corea.getId()!!)
        val covid = servicio.recuperar(covid.getId()!!)

        Assertions.assertEquals(covid.cantidadDeEspecies, 1)
        Assertions.assertEquals(especie.patogeno.toString(), covid.toString())

    }

    @Test
    fun seRecuperanTodasLasEspeciesDelPatogenoDeManeraAscendente() {

        servicio.crear(salmonella)
        servicioVector.crear(humano)
        servicioVector.crear(humano1)

        servicio.agregarEspecie(salmonella.getId()!!, "Enterica", china.getId()!!)
        servicio.agregarEspecie(salmonella.getId()!!, "Bongori", corea.getId()!!)
        servicio.agregarEspecie(salmonella.getId()!!, "Varicela", corea.getId()!!)
        servicio.agregarEspecie(salmonella.getId()!!, "Quetzal", corea.getId()!!)
        servicio.agregarEspecie(salmonella.getId()!!, "Ahuehuete", corea.getId()!!)

        val especiesPagina1 = servicio.especiesDePatogeno(salmonella.getId()!!, Direccion.ASCENDENTE, 1, 2)
        Assertions.assertTrue(
            especiesPagina1.elementAt(0).nombre.equals("Enterica")
        )
        Assertions.assertTrue(
            especiesPagina1.elementAt(1).nombre.equals("Quetzal")
        )

        val especiesPagina5 = servicio.especiesDePatogeno(salmonella.getId()!!, Direccion.ASCENDENTE, 5, 2)
        Assertions.assertTrue(especiesPagina5.isEmpty())

    }

    @Test
    fun seRecuperanTodasLasEspeciesDelPatogenoDeManeraDescendente() {

        servicio.crear(salmonella)
        servicioVector.crear(humano)
        servicioVector.crear(humano1)

        servicio.agregarEspecie(salmonella.getId()!!, "Enterica", china.getId()!!)
        servicio.agregarEspecie(salmonella.getId()!!, "Bongori", corea.getId()!!)
        servicio.agregarEspecie(salmonella.getId()!!, "Varicela", corea.getId()!!)
        servicio.agregarEspecie(salmonella.getId()!!, "Quetzal", corea.getId()!!)
        servicio.agregarEspecie(salmonella.getId()!!, "Ahuehuete", corea.getId()!!)

        val especiesPagina1 = servicio.especiesDePatogeno(salmonella.getId()!!, Direccion.DESCENDENTE, 1, 2)
        Assertions.assertTrue(
            especiesPagina1.elementAt(0).nombre.equals("Enterica")
        )
        Assertions.assertTrue(
            especiesPagina1.elementAt(1).nombre.equals("Bongori")
        )

        val especiesPagina5 = servicio.especiesDePatogeno(salmonella.getId()!!, Direccion.DESCENDENTE, 5, 2)
        Assertions.assertTrue(especiesPagina5.isEmpty())

    }

    @Test
    fun comprobacionDeErrorAlPedirUnaPaginaNegativaCuandoSeBuscanLasEspeciesDeUnPatogeno(){
        servicio.crear(salmonella)
        Assertions.assertThrows(ErrorValorDePaginacionIvalido::class.java) {
            servicio.especiesDePatogeno(salmonella.getId()!!, Direccion.ASCENDENTE, -2, 2)
        }
    }

    @Test
    fun comprobacionDeErrorAlPedirUnaCantidadDeElementosPorPaginaNegativaCuandoSeBuscanLasEspeciesDeUnPatogeno(){
        servicio.crear(salmonella)
        Assertions.assertThrows(ErrorValorDePaginacionIvalido::class.java) {
            servicio.especiesDePatogeno(salmonella.getId()!!, Direccion.ASCENDENTE, 1, -5)
        }
    }

    @Test
    fun seTrataDeRecuperarTodasLasEspeciesDelPatogenoEsteNoTiene() {

        servicio.crear(covid)
        val especies = servicio.especiesDePatogeno(covid.getId()!!, Direccion.DESCENDENTE, 1, 2)

        Assertions.assertEquals(especies.size, 0)

    }

    @Test
    fun seSabeSiNoEsPandemia() {

        servicio.crear(salmonella)
        servicioVector.crear(humano)

        val enterica: Especie = servicio.agregarEspecie(salmonella.getId()!!, "Enterica", corea.getId()!!)

        servicioVector.infectar(humano.getId()!!,enterica.getId()!!)

        Assertions.assertFalse(servicio.esPandemia(enterica.getId()!!))

    }

    @Test
    fun seSabeSiEsPandemia() {

        servicio.crear(salmonella)
        servicioVector.crear(humano)
        servicioVector.crear(humano1)

        val enterica: Especie = servicio.agregarEspecie(salmonella.getId()!!, "Enterica", corea.getId()!!)

        servicioVector.infectar(humano.getId()!!,enterica.getId()!!)
        servicioVector.infectar(humano1.getId()!!,enterica.getId()!!)

        Assertions.assertTrue(servicio.esPandemia(enterica.getId()!!))

    }

    @Test
    fun errorNoHayVectoresEnUbicacion() {

        Assertions.assertThrows(NoHayVectorException::class.java) {
            servicio.crear(salmonella)
            servicio.agregarEspecie(salmonella.getId()!!, "Enterica", corea.getId()!!)
        }

    }

    @AfterEach
    fun borrarRegistros() {
        dataService.cleanAll()
    }

}
