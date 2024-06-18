package ar.edu.unq.eperdemic.testServicios

import ar.edu.unq.eperdemic.exceptions.*
import ar.edu.unq.eperdemic.helper.dao.HibernateDataDAO
import ar.edu.unq.eperdemic.helper.service.DataService
import ar.edu.unq.eperdemic.helper.service.DataServiceImpl
import ar.edu.unq.eperdemic.modelo.*
import ar.edu.unq.eperdemic.modelo.ubicacion.UbicacionGlobal
import ar.edu.unq.eperdemic.modelo.ubicacion.UbicacionJpa
import ar.edu.unq.eperdemic.modelo.ubicacion.UbicacionMongo
import ar.edu.unq.eperdemic.modelo.vector.TipoVector
import ar.edu.unq.eperdemic.modelo.vector.Vector
import ar.edu.unq.eperdemic.persistencia.dao.UbicacionMongoDAO
import ar.edu.unq.eperdemic.persistencia.dao.UbicacionNeo4jDAO
import ar.edu.unq.eperdemic.services.DistritoService
import ar.edu.unq.eperdemic.services.PatogenoService
import ar.edu.unq.eperdemic.services.UbicacionService
import ar.edu.unq.eperdemic.services.VectorService
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon
import org.springframework.util.Assert


@ExtendWith
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DistritoServiceTest {

    @Autowired private lateinit var distritoService: DistritoService

    @Autowired private lateinit var patogenoService: PatogenoService
    @Autowired private lateinit var ubicacionService: UbicacionService
    @Autowired private lateinit var vectorService: VectorService

    lateinit var dataService: DataService

    private lateinit var distritoBernal: Distrito
    private lateinit var distritoQuilmes: Distrito
    private lateinit var distritoBerazategui: Distrito
    private lateinit var coordenadas: List<GeoJsonPoint>
    private lateinit var coordenadas2: List<GeoJsonPoint>
    private lateinit var coordenadas3: List<GeoJsonPoint>
    private lateinit var forma1: GeoJsonPolygon
    private lateinit var forma2: GeoJsonPolygon
    private lateinit var forma3: GeoJsonPolygon
    private lateinit var ubicacionElPiave: UbicacionGlobal
    private lateinit var ubicacionBurgerKing: UbicacionGlobal
    private lateinit var ubicacionSubway: UbicacionGlobal
    private lateinit var ubicacionMostaza: UbicacionGlobal
    private lateinit var ubicacionMcDonals: UbicacionGlobal
    private lateinit var covid: Patogeno
    private lateinit var salmonella: Patogeno
    private lateinit var pepe: Vector
    private lateinit var pedro: Vector
    private lateinit var especie1: Especie
    private lateinit var especie2: Especie
    private lateinit var coordenada1: GeoJsonPoint
    private lateinit var coordenada2: GeoJsonPoint
    private lateinit var coordenada3: GeoJsonPoint
    private lateinit var coordenada4: GeoJsonPoint

    @BeforeEach
    fun setUp(){

        dataService = DataServiceImpl(HibernateDataDAO())

        ubicacionElPiave = UbicacionGlobal("Berazategui", GeoJsonPoint(2.0, 2.0))

        coordenadas = listOf(
            GeoJsonPoint(0.0, 0.0),
            GeoJsonPoint(3.0, 6.0),
            GeoJsonPoint(6.0, 1.0),
            GeoJsonPoint(0.0, 0.0)
        )

        coordenadas2 = listOf(
            GeoJsonPoint(10.0, 10.0),
            GeoJsonPoint(13.0, 16.0),
            GeoJsonPoint(16.0, 11.0),
            GeoJsonPoint(10.0, 10.0)
        )

        coordenadas3 = listOf(
            GeoJsonPoint(20.0, 0.0),
            GeoJsonPoint(23.0, 6.0),
            GeoJsonPoint(26.0, 1.0),
            GeoJsonPoint(20.0, 0.0)
        )

        coordenada1 = GeoJsonPoint(45.00, 40.00)
        coordenada2 = GeoJsonPoint(46.00, 40.00)
        coordenada3 = GeoJsonPoint(47.00, 40.00)
        coordenada4 = GeoJsonPoint(53.00, 40.00)

        forma1 = GeoJsonPolygon(coordenadas)
        forma2 = GeoJsonPolygon(coordenadas2)
        forma3 = GeoJsonPolygon(coordenadas3)
        distritoBernal = Distrito("Bernal", forma1)
        distritoQuilmes = Distrito("Quilmes", forma2)
        distritoBerazategui = Distrito ("Berazategui", forma3)

        covid = Patogeno("Coronavirus", 90, 5, 1, 60, 95)
        salmonella = Patogeno("Salmonella", 70, 10, 15, 30, 66)

        ubicacionMcDonals = UbicacionGlobal("McDonals", coordenada1)
        ubicacionBurgerKing = UbicacionGlobal("BurgerKing", coordenada2)
        ubicacionMostaza = UbicacionGlobal("Mostaza", coordenada2)
        ubicacionSubway = UbicacionGlobal("Subway", coordenada3)

        patogenoService.crear(covid)
        patogenoService.crear(salmonella)

    }

    @Test
    fun testAlCrearUnDistritoYRecuperarloSeObtienenObjetosSimilares() {
        distritoService.crear(distritoBernal)
        val distritoRecuperado = distritoService.recuperarPorNombre(distritoBernal.getNombre()!!)

        Assertions.assertEquals(distritoBernal.getNombre()!!, distritoRecuperado.getNombre()!!)
        Assertions.assertEquals(distritoBernal.getForma(), distritoRecuperado.getForma())
        Assertions.assertEquals(distritoBernal.getUbicaciones(), distritoRecuperado.getUbicaciones())
    }


    @Test
    fun testCuandoPreguntoCualEsElDistritoMasEnfermoYNoHayInfectadosSeLanzaNoHayDistritosConUbicacionesInfectadasException(){

        ubicacionService.crear(ubicacionMcDonals)

        distritoService.crear(distritoBernal)
        distritoService.crear(distritoQuilmes)
        distritoService.crear(distritoBerazategui)

        assertThrows<NoHayDistritoInfectado> { distritoService.distritoMasEnfermo() }
    }

    @Test
    fun testSePuedeSaberCualEsElDistritoMasInfectado() {

        ubicacionService.crear(ubicacionMcDonals)
        pedro = Vector("Pedro", ubicacionMcDonals.aJPA(), TipoVector.HUMANO)
        vectorService.crear(pedro)
        especie1 = patogenoService.agregarEspecie(covid.getId(),"Especie1", ubicacionMcDonals.getId())

        distritoService.crear(distritoBernal)

        distritoBernal.setUbicacion(ubicacionMcDonals)

        distritoService.actualizarDistrito(distritoBernal)

        Assertions.assertEquals(distritoBernal.getNombre(), distritoService.distritoMasEnfermo().getNombre())

    }

    @Test
    fun testSePuedeSaberCualEsElDistritoMasInfectadoCuandoTieneMismaCantidad() {

        ubicacionService.crear(ubicacionMcDonals)
        ubicacionService.crear(ubicacionBurgerKing)
        pedro = Vector("Pedro", ubicacionMcDonals.aJPA(), TipoVector.HUMANO)
        pepe = Vector("Pepe", ubicacionBurgerKing.aJPA(), TipoVector.HUMANO)
        vectorService.crear(pedro)
        vectorService.crear(pepe)
        especie1 = patogenoService.agregarEspecie(covid.getId(),"Especie1", ubicacionMcDonals.getId())
        especie2 = patogenoService.agregarEspecie(covid.getId(),"Especie2", ubicacionBurgerKing.getId())

        distritoService.crear(distritoBernal)
        distritoService.crear(distritoQuilmes)

        distritoBernal.setUbicacion(ubicacionMcDonals)
        distritoQuilmes.setUbicacion(ubicacionSubway)

        distritoService.actualizarDistrito(distritoBernal)
        distritoService.actualizarDistrito(distritoQuilmes)

        Assertions.assertEquals(distritoBernal.getNombre(), distritoService.distritoMasEnfermo().getNombre())

    }

    @Test
    fun testSetrataDeSaberCualEsElDistritoConMasInfectadoCuandoNoHayUbicaciones() {

        assertThrows<NoHayUbicaciones> { distritoService.distritoMasEnfermo() }

    }

    @Test
    fun testAlCrearUnDistritoConUbicacionesYRecuperarloSeObtienenObjetosSimilares() {
        distritoBernal.setUbicacion(ubicacionElPiave)
        distritoService.crear(distritoBernal)
        val distritoRecuperado = distritoService.recuperarPorNombre(distritoBernal.getNombre()!!)

        Assertions.assertEquals(distritoBernal.getForma(), distritoRecuperado.getForma())

        Assertions.assertEquals(distritoBernal.getNombre()!!, distritoRecuperado.getNombre()!!)
    }

    @Test
    fun testAlActualizarUnDistritoSeActualizaCorrectamente() {
        distritoService.crear(distritoBernal)
        var distritoRecuperado = distritoService.recuperarPorNombre(distritoBernal.getNombre()!!)

        Assertions.assertEquals(distritoBernal.getNombre(), distritoRecuperado.getNombre())
        Assertions.assertEquals(distritoBernal.getForma(), distritoRecuperado.getForma())
        Assertions.assertEquals(distritoBernal.getUbicaciones(), distritoRecuperado.getUbicaciones())

        distritoBernal.setUbicacion(ubicacionElPiave)
        distritoService.actualizarDistrito(distritoBernal)
        distritoRecuperado = distritoService.recuperarPorNombre(distritoBernal.getNombre()!!)

        Assertions.assertEquals(1, distritoRecuperado.getUbicaciones().size)
    }
    @Test
    fun testAlCrearUnDistritoConMismoNombreSeLanzaException() {
        distritoService.crear(distritoBernal)
        assertThrows<NombreDeDistritoExistenteException> { distritoService.crear(distritoBernal) }
    }

    @Test
    fun testAlCrearUnDistritoQueIntersectaConOtroSeLanzaExcepcion(){
        val coordenadasQueIntersectan = listOf(
            GeoJsonPoint(0.0, 0.0),
            GeoJsonPoint(1.0, 6.0),
            GeoJsonPoint(6.0, 1.0),
            GeoJsonPoint(0.0, 0.0)
        )

        distritoQuilmes = Distrito("Quilmes", GeoJsonPolygon(coordenadasQueIntersectan))
        distritoService.crear(distritoBernal)
        assertThrows<CoordenadaDistritoIntersectionException> { distritoService.crear(distritoQuilmes) }
    }

    @Test
    fun testAlIntentarRecuperarUnDistritoPorNombreNoPersistidoSeLanzaDistritoNoExisteException() {
        assertThrows<DistritoNoExistenteException> { distritoService.recuperarPorNombre("Belgrano") }
    }

    @Test
    fun testAlIntentarActualizarUnDistritoNoPersistidoSeLanzaDistritoNoExisteException() {
        val distritoBelgrano = Distrito("Belgrano", forma1)
        assertThrows<DistritoNoExistenteException> { distritoService.actualizarDistrito(distritoBelgrano) }
    }

    @Test
    fun testAlIntentarActualizarUnDistritoConUnNombreYaPersistidoSeLanzaDistritoConNombreYaExisteException() {
        distritoBernal.setNombre("Berazategui")
        assertThrows<DistritoNoExistenteException> { distritoService.actualizarDistrito(distritoBernal) }
    }

    @Test
    fun testAlIntentarActualizarUnDistritoQueIntersectariaConOtroSeLanzaDistritoIntersectaConOtroException() {
        distritoService.crear(distritoBernal)
        distritoService.crear(distritoQuilmes)
        distritoQuilmes.setForma(forma1)

        assertThrows<CoordenadaDistritoIntersectionException> { distritoService.actualizarDistrito(distritoQuilmes) }
    }

    @AfterEach
    fun tearDown() {
        distritoService.deleteAll()
        dataService.cleanAll()
        ubicacionService.deleteAll()
    }
}