package ar.edu.unq.eperdemic.testServicios.distritoTest

import ar.edu.unq.eperdemic.exceptions.CoordenadaDistritoIntersectionException
import ar.edu.unq.eperdemic.exceptions.DistritoNoExistenteException
import ar.edu.unq.eperdemic.exceptions.NoHayDistritoInfectado
import ar.edu.unq.eperdemic.exceptions.NombreDeDistritoExistenteException
import ar.edu.unq.eperdemic.helper.dao.HibernateDataDAO
import ar.edu.unq.eperdemic.helper.service.DataService
import ar.edu.unq.eperdemic.helper.service.DataServiceImpl
import ar.edu.unq.eperdemic.modelo.*
import ar.edu.unq.eperdemic.modelo.ubicacion.UbicacionJpa
import ar.edu.unq.eperdemic.modelo.ubicacion.UbicacionMongo
import ar.edu.unq.eperdemic.modelo.vector.Vector
import ar.edu.unq.eperdemic.persistencia.dao.UbicacionJpaDAO
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


@ExtendWith
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DistritoServiceImpTest {

    @Autowired private lateinit var distritoService: DistritoService
    @Autowired private lateinit var ubicacionServiceImpl: UbicacionService
    @Autowired private lateinit var vectorService: VectorService

    @Autowired private lateinit var ubicacionJpaDAO: UbicacionJpaDAO
    @Autowired private lateinit var ubicacionNeo4jDAO: UbicacionNeo4jDAO
    @Autowired private lateinit var ubicacionMongoDBDAO: UbicacionMongoDAO
    @Autowired private lateinit var patogenoService: PatogenoService


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
    private lateinit var ubicacionElPiave: UbicacionMongo
    private lateinit var ubicacionBurgerKing: UbicacionJpa
    private lateinit var ubicacionSubway: UbicacionJpa
    private lateinit var ubicacionMostaza: UbicacionJpa
    private lateinit var vectorMartin: Vector
    private lateinit var vectorTomas: Vector
    private lateinit var vectorBullo: Vector
    private lateinit var vectorFirulais: Vector
    private lateinit var ubicacionMcDonals: UbicacionJpa
    private lateinit var patogenoVirus: Patogeno
    private lateinit var especieCovid: Especie
    @BeforeEach
    fun setUp(){

        dataService = DataServiceImpl(HibernateDataDAO())

        ubicacionElPiave = UbicacionMongo("Berazategui", GeoJsonPoint(2.0, 2.0))

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

        forma1 = GeoJsonPolygon(coordenadas)
        forma2 = GeoJsonPolygon(coordenadas2)
        forma3 = GeoJsonPolygon(coordenadas3)
        distritoBernal = Distrito("Bernal", forma1)
        distritoQuilmes = Distrito("Quilmes", forma2)
        distritoBerazategui = Distrito ("Berazategui", forma3)

        ubicacionMcDonals = UbicacionJpa("McDonals")
        ubicacionBurgerKing = UbicacionJpa("BurgerKing")
        ubicacionSubway = UbicacionJpa("Subway")
        ubicacionMostaza = UbicacionJpa("Mostaza")

        patogenoVirus = Patogeno("Virus", 6, 73, 52, 32, 33)
        patogenoService.crear(patogenoVirus)
    }

    @Test
    fun testAlCrearUnDistritoYRecuperarloSeObtienenObjetosSimilares() {
        distritoService.crear(distritoBernal)
        val distritoRecuperado = distritoService.recuperarPorNombre(distritoBernal.getNombre()!!)

        Assertions.assertEquals(distritoBernal.getNombre()!!, distritoRecuperado.getNombre()!!)
        Assertions.assertEquals(distritoBernal.getForma(), distritoRecuperado.getForma())
        Assertions.assertEquals(distritoBernal.getUbicaiones(), distritoRecuperado.getUbicaiones())
    }


    @Test
    fun testCuandoPreguntoCualEsElDistritoMasEnfermoYNoHayInfectadosSeLanzaNoHayDistritosConUbicacionesInfectadasException(){
        distritoService.crear(distritoBernal)
        distritoService.crear(distritoQuilmes)
        distritoService.crear(distritoBerazategui)

        assertThrows<NoHayDistritoInfectado> { distritoService.distritoMasEnfermo() }
    }
    @Test
    fun testAlCrearUnDistritoConUbicacionesYRecuperarloSeObtienenObjetosSimilares() {
        distritoBernal.setUbicacion(ubicacionElPiave)
        distritoService.crear(distritoBernal)
        val distritoRecuperado = distritoService.recuperarPorNombre(distritoBernal.getNombre()!!)

        Assertions.assertEquals(distritoBernal.getForma(), distritoRecuperado.getForma())

        // CORREGIR
        //Assertions.assertEquals(distritoBernal.getUbicaiones(), distritoRecuperado.getUbicaiones())
        Assertions.assertEquals(distritoBernal.getNombre()!!, distritoRecuperado.getNombre()!!)
    }

    @Test
    fun testAlActualizarUnDistritoSeActualizaCorrectamente() {
        distritoService.crear(distritoBernal)
        var distritoRecuperado = distritoService.recuperarPorNombre(distritoBernal.getNombre()!!)

        Assertions.assertEquals(distritoBernal.getNombre(), distritoRecuperado.getNombre())
        Assertions.assertEquals(distritoBernal.getForma(), distritoRecuperado.getForma())
        Assertions.assertEquals(distritoBernal.getUbicaiones(), distritoRecuperado.getUbicaiones())

        distritoBernal.setUbicacion(ubicacionElPiave)
        distritoService.actualizarDistrito(distritoBernal)
        distritoRecuperado = distritoService.recuperarPorNombre(distritoBernal.getNombre()!!)

        Assertions.assertEquals(1, distritoRecuperado.getUbicaiones().size)

        // CORREGIR !!
        //Assertions.assertTrue(distritoRecuperado.getUbicaiones().contains(ubicacionElPiave))
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
        ubicacionNeo4jDAO.detachDelete()
        ubicacionMongoDBDAO.deleteAll()
    }
}