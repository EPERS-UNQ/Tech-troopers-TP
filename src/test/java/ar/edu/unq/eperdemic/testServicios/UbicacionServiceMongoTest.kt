package ar.edu.unq.eperdemic.testServicios

import ar.edu.unq.eperdemic.modelo.Distrito
import ar.edu.unq.eperdemic.persistencia.dao.UbicacionMongoDAO
import ar.edu.unq.eperdemic.services.DistritoService
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon

@SpringBootTest
class UbicacionServiceMongoTest {

    @Autowired private lateinit var serviceDistrito: DistritoService
    @Autowired private lateinit var ubicacionMongoDAO: UbicacionMongoDAO

    lateinit var distrito: Distrito
    lateinit var coordenadas: List<GeoJsonPoint>
    lateinit var coordenada2: GeoJsonPolygon

    lateinit var forma: GeoJsonPolygon

    @BeforeEach
    fun crearModelo() {

        coordenadas = listOf(
            GeoJsonPoint(0.0, 0.0),
            GeoJsonPoint(30.0, 60.0),
            GeoJsonPoint(60.0, 10.0),
            GeoJsonPoint(0.0, 0.0),
        )

        forma = GeoJsonPolygon(coordenadas)

        serviceDistrito.crear(Distrito("Peru", forma))

    }

    @Test
    fun chequearUbiPersistida () {
        val ubi1 = serviceDistrito.recuperarPorNombre("Peru")
        Assertions.assertEquals(ubi1.getNombre(), "Peru")
    }

    @Test
    /*fun chequearCoordenadasDeUbiPersistida (){
        val coordenada2 = ubicacionMongo.findCoordenadaByNombre("Argentina")!!
        Assertions.assertEquals(coordenada.getLongitud(), coordenada2.getLongitud())
    }*/

    @AfterEach
    fun borrarRegistros() {
        ubicacionMongoDAO.deleteAll()
    }
}