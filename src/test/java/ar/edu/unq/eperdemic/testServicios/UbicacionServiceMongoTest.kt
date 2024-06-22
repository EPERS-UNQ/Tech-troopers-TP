package ar.edu.unq.eperdemic.testServicios

import ar.edu.unq.eperdemic.exceptions.ErrorCoordenadaInvalida
import ar.edu.unq.eperdemic.exceptions.ErrorUbicacionMuyLejana
import ar.edu.unq.eperdemic.exceptions.NoHayDistritoInfectado
import ar.edu.unq.eperdemic.helper.dao.HibernateDataDAO
import ar.edu.unq.eperdemic.helper.service.DataService
import ar.edu.unq.eperdemic.helper.service.DataServiceImpl
import ar.edu.unq.eperdemic.modelo.ubicacion.UbicacionGlobal
import ar.edu.unq.eperdemic.modelo.vector.TipoVector
import ar.edu.unq.eperdemic.modelo.vector.Vector
import ar.edu.unq.eperdemic.services.UbicacionService
import ar.edu.unq.eperdemic.services.VectorService
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.geo.GeoJsonPoint

@SpringBootTest
class UbicacionServiceMongoTest {

    @Autowired private lateinit var serviceUbicacion: UbicacionService
    @Autowired private lateinit var serviceVector: VectorService

    lateinit var dataService         : DataService

    lateinit var ubicacion: UbicacionGlobal
    lateinit var ubicacionPersistida: UbicacionGlobal
    lateinit var coordenada: GeoJsonPoint


    @BeforeEach
    fun crearModelo() {

        coordenada =  GeoJsonPoint(20.00, 25.00)
        dataService = DataServiceImpl(HibernateDataDAO())
        ubicacion = UbicacionGlobal("Argentina", coordenada)
        ubicacionPersistida = serviceUbicacion.crear(ubicacion)

    }

    @Test
    fun cuandoSeRecuperaUnaUbicacionMongoPersistidaSeObtienenObjetosSimilares () {
        val ubicacionRecuperada = serviceUbicacion.recuperar(ubicacionPersistida.getId())

        Assertions.assertEquals(ubicacionPersistida.getNombre(), ubicacionRecuperada.getNombre())
        Assertions.assertEquals(ubicacionPersistida.getCoordenada().x, ubicacionRecuperada.getCoordenada().x)
        Assertions.assertEquals(ubicacionPersistida.getCoordenada().y, ubicacionRecuperada.getCoordenada().y)
    }
    
    @Test
    fun seTrataDeGuardarUnaUbicacionConCoordenadasDeUnaUbicacionYaExistente() {
        assertThrows<ErrorCoordenadaInvalida> {
            serviceUbicacion.crear(UbicacionGlobal("Peru", coordenada))
        }
    }

    @Test
    fun seTrataDeUpdatearUnaUbicacionConCoordenadasDeUnaUbicacionYaEnUso() {

        serviceUbicacion.crear(UbicacionGlobal("Peru", GeoJsonPoint(25.00, 30.00)))
        ubicacionPersistida.setCoordenada(GeoJsonPoint(25.00, 30.00))

        val ubicacionRecuperada = serviceUbicacion.recuperar(ubicacionPersistida.getId())
        ubicacionRecuperada.setCoordenada(GeoJsonPoint(25.00, 30.00))

        Assertions.assertEquals("Argentina" ,ubicacionRecuperada.getNombre())
        assertThrows<ErrorCoordenadaInvalida> { serviceUbicacion.updatear(ubicacionRecuperada) }
    }

    @Test
    fun errorCuandoSeIntentaMoverUnVectorAUnaUbicacionAMasDe100KM(){
        var ubicacionLaBoca = UbicacionGlobal("La Boca", GeoJsonPoint(0.0, 0.0))
        var ubicacionCordillera = UbicacionGlobal("Coordillera de los Andes", GeoJsonPoint(5.0, 5.0))

        ubicacionLaBoca = serviceUbicacion.crear(ubicacionLaBoca)
        ubicacionCordillera = serviceUbicacion.crear(ubicacionCordillera)

        var vectorGaviota = Vector("Gaviota", ubicacionLaBoca.aJPA(), TipoVector.ANIMAL)

        serviceUbicacion.conectar(ubicacionLaBoca.getNombre(), ubicacionCordillera.getNombre(), "AEREO")

        vectorGaviota = serviceVector.crear(vectorGaviota)

        Assertions.assertThrows(ErrorUbicacionMuyLejana::class.java) {
            serviceUbicacion.mover(vectorGaviota.id!!, ubicacionCordillera.getId())
        }
    }

    @AfterEach
    fun borrarRegistros() {
        serviceUbicacion.deleteAll()
        dataService.cleanAll()
    }
}