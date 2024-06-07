package ar.edu.unq.eperdemic.testServicios.distritoTest

import ar.edu.unq.eperdemic.exceptions.CoordenadaDistritoIntersectionException
import ar.edu.unq.eperdemic.exceptions.DistritoNoExistenteException
import ar.edu.unq.eperdemic.exceptions.NoHayDistritoInfectado
import ar.edu.unq.eperdemic.exceptions.NombreDeDistritoExistenteException
import ar.edu.unq.eperdemic.helper.dao.HibernateDataDAO
import ar.edu.unq.eperdemic.helper.service.DataService
import ar.edu.unq.eperdemic.helper.service.DataServiceImpl
import ar.edu.unq.eperdemic.modelo.*
import ar.edu.unq.eperdemic.modelo.ubicacion.UbicacionGlobal
import ar.edu.unq.eperdemic.modelo.ubicacion.UbicacionJpa
import ar.edu.unq.eperdemic.modelo.ubicacion.UbicacionMongo
import ar.edu.unq.eperdemic.modelo.vector.TipoVector
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
class DistritoServiceImpTest2 {

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
    fun testCuandoPreguntoCualEsElDistritoMasEnfermoYHayUnoSoloConInfectadosMeDevuelveElMismoDistrito() {
        distritoQuilmes = distritoService.crear(distritoQuilmes)

        // OBTENEMOS LA UBICACIONJPA Y LA AGREGAMOS AL VECTOR RECIEN CREADO
        val ubi1 =  ubicacionServiceImpl.crear(UbicacionGlobal("ubiPrueba", GeoJsonPoint(13.0, 16.0)))
        //ubicacionSubway = ubicacionJpaDAO.recuperarPorNombreReal(ubi1.getNombre())!!
        val vectorMartine = Vector("Martin", ubi1.aJPA(), TipoVector.HUMANO)
        vectorMartin = vectorService.crear(vectorMartine)

        // CREAMOS UNA ESPECIE E INFECTAMOS AL VECTOR CON ELLA
        especieCovid = patogenoService.agregarEspecie(patogenoVirus.id!!, "COVID",ubi1.getId()!!)
        vectorService.infectar(vectorMartin.getId(), especieCovid.getId()!!)


        // AGREGAMOS LA UBICACIONMONGO AL DISTRITO
        val quilmes = distritoService.recuperarPorNombre("Quilmes")
        val ubiMongo = ubicacionMongoDBDAO.findByNombre(ubi1.getNombre())
        quilmes.setUbicacion(ubiMongo!!)
        distritoService.actualizarDistrito(quilmes)

        Assertions.assertEquals(distritoQuilmes.getNombre(), distritoService.distritoMasEnfermo().getNombre())
    }


    @Test
    fun testCuandoPreguntoCualEsElDistritoMasEnfermoEntreVariosMeDevuelveElDistritoEsperado() {
        // CREAMOS VARIOS DISTRITOS, CON UNO DE PRUEBA
        val distritoBernal = distritoService.crear(distritoBernal)
        distritoService.crear(distritoQuilmes)
        distritoService.crear(distritoBerazategui)

        // CREAMOS UBICACIONES GLOBALES
        val ubi1 = ubicacionServiceImpl.crear(UbicacionGlobal("ubicacionBurgerKing", GeoJsonPoint(2.0, 3.0)))
        val ubi2 = ubicacionServiceImpl.crear(UbicacionGlobal("ubicacionSubway", GeoJsonPoint(3.0, 3.0)))
        val ubi3 = ubicacionServiceImpl.crear(UbicacionGlobal("ubicacionMcDonals", GeoJsonPoint(4.0,3.0)))
        val ubi4 = ubicacionServiceImpl.crear(UbicacionGlobal("ubicacionMostaza", GeoJsonPoint(5.0, 3.0)))

        // OBTENEMOS LAS UBICACIONESJPA
        ubicacionBurgerKing = ubicacionJpaDAO.recuperarPorNombreReal(ubi1.getNombre())!!
        ubicacionSubway = ubicacionJpaDAO.recuperarPorNombreReal(ubi2.getNombre())!!
        ubicacionMcDonals =  ubicacionJpaDAO.recuperarPorNombreReal(ubi3.getNombre())!!
        ubicacionMostaza = ubicacionJpaDAO.recuperarPorNombreReal(ubi4.getNombre())!!

        // CREAMOS LOS VECTORES
        vectorMartin = Vector("Martin", ubicacionBurgerKing, TipoVector.HUMANO)
        vectorTomas = Vector("Tomas", ubicacionMcDonals, TipoVector.HUMANO)
        vectorFirulais = Vector("Firulais" , ubicacionMcDonals, TipoVector.ANIMAL)
        vectorBullo = Vector("Bullo" , ubicacionMostaza, TipoVector.ANIMAL)
        vectorMartin = vectorService.crear(vectorMartin)
        vectorTomas = vectorService.crear(vectorTomas)
        vectorFirulais = vectorService.crear(vectorFirulais)
        vectorBullo = vectorService.crear(vectorBullo)

        // AGREGAMOS UNA ESPECIE
        especieCovid = patogenoService.agregarEspecie(patogenoVirus.id!!, "COVID", ubicacionMcDonals.getId()!!)

        vectorService.infectar(vectorMartin.id!!, especieCovid.getId()!!)
        vectorService.infectar(vectorTomas.id!!, especieCovid.getId()!!)
        vectorService.infectar(vectorBullo.id!!, especieCovid.getId()!!)
        vectorService.infectar(vectorFirulais.id!!, especieCovid.getId()!!)

        // AGREGAMOS LA UBICACIONMONGO AL DISTRITO
        val ubiMongo1 = ubicacionMongoDBDAO.findByNombre(ubi1.getNombre())
        val ubiMongo2 = ubicacionMongoDBDAO.findByNombre(ubi2.getNombre())
        val ubiMongo3 = ubicacionMongoDBDAO.findByNombre(ubi3.getNombre())
        val ubiMongo4 = ubicacionMongoDBDAO.findByNombre(ubi4.getNombre())

        // ACTUALIZO EL DISTRITO DE LA PRUEBA
        val distritoMongo = distritoService.recuperarPorNombre("Bernal")
        distritoMongo.setUbicacion(ubiMongo1!!)
        distritoMongo.setUbicacion(ubiMongo2!!)
        distritoMongo.setUbicacion(ubiMongo3!!)
        distritoMongo.setUbicacion(ubiMongo4!!)

        distritoService.actualizarDistrito(distritoMongo)

        Assertions.assertEquals(distritoBernal.getNombre(), distritoService.distritoMasEnfermo().getNombre())
    }

    @Test
    fun testCuandoPreguntoCualEsElDistritoMasEnfermoYHayDosConLaMismaCantidadDeInfectadosSeDevuelveElPrimeroQueSePersistio() {
        distritoBerazategui = distritoService.crear(distritoBerazategui)
        distritoBernal = distritoService.crear(distritoBernal)

        // CREAMOS UBICACIONES GLOBALES
        val ubi1 = ubicacionServiceImpl.crear(UbicacionGlobal("ubicacionBurgerKing", GeoJsonPoint(2.0, 3.0)))
        val ubi2 = ubicacionServiceImpl.crear(UbicacionGlobal("ubicacionSubway", GeoJsonPoint(3.0, 3.0)))
        val ubi3 = ubicacionServiceImpl.crear(UbicacionGlobal("ubicacionMcDonals", GeoJsonPoint(4.0,3.0)))
        val ubi4 = ubicacionServiceImpl.crear(UbicacionGlobal("ubicacionMostaza", GeoJsonPoint(5.0, 3.0)))

        // OBTENEMOS LAS UBICACIONESJPA
        ubicacionBurgerKing = ubicacionJpaDAO.recuperarPorNombreReal(ubi1.getNombre())!!
        ubicacionSubway = ubicacionJpaDAO.recuperarPorNombreReal(ubi2.getNombre())!!
        ubicacionMcDonals =  ubicacionJpaDAO.recuperarPorNombreReal(ubi3.getNombre())!!
        ubicacionMostaza = ubicacionJpaDAO.recuperarPorNombreReal(ubi4.getNombre())!!

        // CREAMOS LOS VECTORES
        vectorMartin = Vector("Martin", ubicacionBurgerKing, TipoVector.HUMANO)
        vectorTomas = Vector("Tomas",ubicacionSubway, TipoVector.HUMANO)
        vectorFirulais = Vector("Firulais" , ubicacionMcDonals, TipoVector.ANIMAL)
        vectorBullo = Vector("Bullo" , ubicacionMostaza, TipoVector.ANIMAL)
        vectorMartin = vectorService.crear(vectorMartin)
        vectorTomas = vectorService.crear(vectorTomas)
        vectorFirulais = vectorService.crear(vectorFirulais)
        vectorBullo = vectorService.crear(vectorBullo)

        // AGREGAMOS UNA ESPECIE
        especieCovid = patogenoService.agregarEspecie(patogenoVirus.getId(), "COVID", ubicacionMcDonals.getId()!!)

        vectorService.infectar(vectorMartin.id!!, especieCovid.getId()!!)
        vectorService.infectar(vectorTomas.id!!, especieCovid.getId()!!)
        vectorService.infectar(vectorBullo.id!!, especieCovid.getId()!!)
        vectorService.infectar(vectorFirulais.id!!, especieCovid.getId()!!)

        // AGREGAMOS LA UBICACIONMONGO AL DISTRITO
        val ubiMongo1 = ubicacionMongoDBDAO.findByNombre(ubi1.getNombre())
        val ubiMongo2 = ubicacionMongoDBDAO.findByNombre(ubi2.getNombre())
        val ubiMongo3 = ubicacionMongoDBDAO.findByNombre(ubi3.getNombre())
        val ubiMongo4 = ubicacionMongoDBDAO.findByNombre(ubi4.getNombre())

        // ACTUALIZO EL DISTRITO DE LA PRUEBA
        val distritoMongo = distritoService.recuperarPorNombre("Bernal")
        val distritoMongo2 = distritoService.recuperarPorNombre("Berazategui")
        distritoMongo.setUbicacion(ubiMongo1!!)
        distritoMongo.setUbicacion(ubiMongo2!!)
        distritoMongo2.setUbicacion(ubiMongo3!!)
        distritoMongo2.setUbicacion(ubiMongo4!!)

        distritoService.actualizarDistrito(distritoMongo)

        Assertions.assertEquals(distritoBernal.getNombre(), distritoService.distritoMasEnfermo().getNombre())
    }


    @AfterEach
    fun tearDown() {
        distritoService.deleteAll()
        dataService.cleanAll()
        ubicacionNeo4jDAO.detachDelete()
        ubicacionMongoDBDAO.deleteAll()
    }
}