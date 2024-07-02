package ar.edu.unq.eperdemic.testServicios

import ar.edu.unq.eperdemic.exceptions.ErrorValorDePaginacionIvalido
import ar.edu.unq.eperdemic.exceptions.NoExisteElPatogeno
import ar.edu.unq.eperdemic.exceptions.NoHayVectorException
import ar.edu.unq.eperdemic.helper.dao.HibernateDataDAO
import ar.edu.unq.eperdemic.helper.service.DataService
import ar.edu.unq.eperdemic.helper.service.DataServiceImpl
import ar.edu.unq.eperdemic.modelo.*
import ar.edu.unq.eperdemic.modelo.RandomGenerator.NoAleatorioStrategy
import ar.edu.unq.eperdemic.modelo.RandomGenerator.RandomGenerator
import ar.edu.unq.eperdemic.modelo.ubicacion.UbicacionGlobal
import ar.edu.unq.eperdemic.modelo.vector.TipoVector
import ar.edu.unq.eperdemic.services.PatogenoService
import ar.edu.unq.eperdemic.modelo.vector.Vector
import ar.edu.unq.eperdemic.modelo.vector.VectorGlobal
import ar.edu.unq.eperdemic.services.UbicacionService
import ar.edu.unq.eperdemic.services.VectorService
import org.junit.jupiter.api.*
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
@TestInstance(PER_CLASS)
class PatogenoServiceTest {

    @Autowired lateinit var servicioPatogeno: PatogenoService
    @Autowired lateinit var servicioVector: VectorService
    @Autowired lateinit var servicioUbicacion: UbicacionService

    lateinit var random : RandomGenerator
    lateinit var dataService: DataService

    lateinit var covid: Patogeno
    lateinit var salmonella: Patogeno
    lateinit var china: UbicacionGlobal
    lateinit var corea: UbicacionGlobal
    lateinit var india: UbicacionGlobal
    lateinit var pepe: VectorGlobal
    lateinit var pedro: VectorGlobal
    lateinit var coordenada1: GeoJsonPoint
    lateinit var coordenada2: GeoJsonPoint
    lateinit var coordenada3: GeoJsonPoint

    @BeforeEach
    fun crearModelo() {

        dataService = DataServiceImpl(HibernateDataDAO())
        covid = Patogeno("Coronavirus", 90, 5, 1, 60, 95)
        salmonella = Patogeno("Salmonella", 70, 10, 15, 30, 66)
        coordenada1 = GeoJsonPoint(45.00, 40.00)
        coordenada2 = GeoJsonPoint(46.00, 40.00)
        coordenada3 = GeoJsonPoint(47.00, 45.00)
        china = UbicacionGlobal("China", coordenada1)
        corea = UbicacionGlobal("Corea", coordenada2)
        india = UbicacionGlobal("India", coordenada3)

        servicioUbicacion.crear(corea)
        servicioUbicacion.crear(china)
        servicioUbicacion.crear(india)

        pedro = VectorGlobal("Pedro", corea, TipoVector.HUMANO)
        pepe = VectorGlobal("Pepe", china, TipoVector.HUMANO)

        servicioPatogeno.crear(salmonella)

        pedro = servicioVector.crear(pedro)
        pepe  = servicioVector.crear(pepe)

        random = RandomGenerator.getInstance()
        random.setStrategy(NoAleatorioStrategy())
        random.setNumeroGlobal(1)

    }

    @Test
    fun testCrearYRecuperarPatogeno() {

        servicioPatogeno.crear(covid)
        val patogenoRecuperado = servicioPatogeno.recuperar(covid.getId())!!

        Assertions.assertEquals("Coronavirus", patogenoRecuperado.toString())
        Assertions.assertEquals(2, patogenoRecuperado.getId())

    }

    @Test
    fun errorCuandoSeTrataDeRecuperarUnPatogenoQueNoExiste() {

        Assertions.assertThrows(NoExisteElPatogeno::class.java) {
            servicioPatogeno.recuperar(15)
        }

    }

    @Test
    fun testActualizarPatogeno() {

        servicioPatogeno.crear(covid)

        val covid = servicioPatogeno.recuperar(covid.getId())!!
        covid.crearEspecie("Virus", "Corea")

        servicioPatogeno.updatear(covid)

        Assertions.assertEquals(1, covid.cantidadDeEspecies)
        Assertions.assertEquals(2, covid.getId())

    }

    @Test
    fun seRecuperanTodosLosPatogenos() {

        servicioPatogeno.crear(covid)

        val patogenos = servicioPatogeno.recuperarTodos()

        Assertions.assertEquals(2, patogenos.size)
        Assertions.assertEquals("Coronavirus", patogenos[1].toString())

    }

    @Test
    fun seAgregaUnaEspecieNueva() {

        servicioPatogeno.crear(covid)

        val especie: Especie = servicioPatogeno.agregarEspecie(covid.getId(), "Virus", corea.getId())
        val cantidadDeEspecies = servicioPatogeno.recuperar(covid.getId())!!.cantidadDeEspecies

        Assertions.assertEquals(1, cantidadDeEspecies)
        Assertions.assertEquals("Coronavirus", especie.nombrePatogeno())

    }

    @Test
    fun seRecuperanTodasLasEspeciesDelPatogenoDeManeraAscendente() {

        servicioPatogeno.agregarEspecie(salmonella.getId(), "Enterica", china.getId())
        servicioPatogeno.agregarEspecie(salmonella.getId(), "Bongori", corea.getId())
        servicioPatogeno.agregarEspecie(salmonella.getId(), "Varicela", corea.getId())
        servicioPatogeno.agregarEspecie(salmonella.getId(), "Quetzal", corea.getId())
        servicioPatogeno.agregarEspecie(salmonella.getId(), "Ahuehuete", corea.getId())

        val especiesPagina1 = servicioPatogeno.especiesDePatogeno(salmonella.getId(), Direccion.ASCENDENTE, 1, 2)
        Assertions.assertTrue(
            especiesPagina1.elementAt(0).nombre.equals("Enterica")
        )
        Assertions.assertTrue(
            especiesPagina1.elementAt(1).nombre.equals("Quetzal")
        )

        val especiesPagina5 = servicioPatogeno.especiesDePatogeno(salmonella.getId(), Direccion.ASCENDENTE, 5, 2)
        Assertions.assertTrue(especiesPagina5.isEmpty())

    }

    @Test
    fun seRecuperanTodasLasEspeciesDelPatogenoDeManeraDescendente() {

        servicioPatogeno.agregarEspecie(salmonella.getId(), "Enterica", china.getId())
        servicioPatogeno.agregarEspecie(salmonella.getId(), "Bongori", corea.getId())
        servicioPatogeno.agregarEspecie(salmonella.getId(), "Varicela", corea.getId())
        servicioPatogeno.agregarEspecie(salmonella.getId(), "Quetzal", corea.getId())
        servicioPatogeno.agregarEspecie(salmonella.getId(), "Ahuehuete", corea.getId())

        val especiesPagina1 = servicioPatogeno.especiesDePatogeno(salmonella.getId(), Direccion.DESCENDENTE, 1, 2)
        Assertions.assertTrue(
            especiesPagina1.elementAt(0).nombre.equals("Enterica")
        )
        Assertions.assertTrue(
            especiesPagina1.elementAt(1).nombre.equals("Quetzal")
        )

        val especiesPagina5 = servicioPatogeno.especiesDePatogeno(salmonella.getId(), Direccion.DESCENDENTE, 5, 2)
        Assertions.assertTrue(especiesPagina5.isEmpty())

    }

    @Test
    fun comprobacionDeErrorAlPedirUnaPaginaNegativaCuandoSeBuscanLasEspeciesDeUnPatogeno(){

        Assertions.assertThrows(ErrorValorDePaginacionIvalido::class.java) {
            servicioPatogeno.especiesDePatogeno(salmonella.getId(), Direccion.ASCENDENTE, -2, 2)
        }

    }

    @Test
    fun comprobacionDeErrorAlPedirUnaCantidadDeElementosPorPaginaNegativaCuandoSeBuscanLasEspeciesDeUnPatogeno(){

        Assertions.assertThrows(ErrorValorDePaginacionIvalido::class.java) {
            servicioPatogeno.especiesDePatogeno(salmonella.getId(), Direccion.ASCENDENTE, 1, -5)
        }

    }

    @Test
    fun seTrataDeRecuperarTodasLasEspeciesDelPatogenoEsteNoTiene() {

        servicioPatogeno.crear(covid)
        val especies = servicioPatogeno.especiesDePatogeno(covid.getId(), Direccion.DESCENDENTE, 1, 2)

        Assertions.assertEquals(especies.size, 0)

    }

    @Test
    fun seSabeSiNoEsPandemia() {

        val enterica: Especie = servicioPatogeno.agregarEspecie(salmonella.getId(), "Enterica", corea.getId())

        servicioVector.infectar(pedro.getId(),enterica.getId()!!)

        Assertions.assertFalse(servicioPatogeno.esPandemia(enterica.getId()!!))

    }

    @Test
    fun seSabeSiEsPandemia() {

        val enterica: Especie = servicioPatogeno.agregarEspecie(salmonella.getId(), "Enterica", corea.getId())

        servicioVector.infectar(pepe.getId(),enterica.getId()!!)
        servicioVector.infectar(pedro.getId(),enterica.getId()!!)

        Assertions.assertTrue(servicioPatogeno.esPandemia(enterica.getId()!!))

    }

    @Test
    fun errorNoHayVectoresEnUbicacion() {

        Assertions.assertThrows(NoHayVectorException::class.java) {
            servicioPatogeno.crear(salmonella)
            servicioPatogeno.agregarEspecie(salmonella.getId(), "Enterica", india.getId())
        }

    }

    @Test
    fun errorCuandoSeIntentaCrearDosPatogenosConElMismoNombre(){

        servicioPatogeno.crear(covid)

        Assertions.assertThrows(DataIntegrityViolationException::class.java){
            servicioPatogeno.crear(Patogeno("Coronavirus", 1, 1, 1, 1, 1))
        }
    }

    @AfterEach
    fun tearDown() {
        dataService.cleanAll()
        servicioVector.deleteAll()
    }

}