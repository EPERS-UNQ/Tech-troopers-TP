package ar.edu.unq.eperdemic.testServicios

import ar.edu.unq.eperdemic.helper.dao.HibernateDataDAO
import ar.edu.unq.eperdemic.helper.service.DataService
import ar.edu.unq.eperdemic.helper.service.DataServiceImpl
import ar.edu.unq.eperdemic.modelo.ubicacion.UbicacionGlobal
import ar.edu.unq.eperdemic.services.UbicacionService
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.geo.GeoJsonPoint

@SpringBootTest
class UbicacionServiceMongoTest {

    @Autowired private lateinit var serviceUbicacion: UbicacionService

    lateinit var dataService         : DataService

    lateinit var ubicacion: UbicacionGlobal
    lateinit var ubicacionPersistida: UbicacionGlobal


    @BeforeEach
    fun crearModelo() {
        dataService = DataServiceImpl(HibernateDataDAO())

        ubicacion = UbicacionGlobal("Argentina", GeoJsonPoint(20.00, 25.00))

        ubicacionPersistida = serviceUbicacion.crear(ubicacion)

    }

    @Test
    fun cuandoSeRecuperaUnaUbicacionMongoPersistidaSeObtienenObjetosSimilares () {
        val ubicacionRecuperada = serviceUbicacion.recuperar(ubicacionPersistida.getId())

        Assertions.assertEquals(ubicacionPersistida.getNombre(), ubicacionRecuperada.getNombre())
        Assertions.assertEquals(ubicacionPersistida.getCoordenada().x, ubicacionRecuperada.getCoordenada().x)
        Assertions.assertEquals(ubicacionPersistida.getCoordenada().y, ubicacionRecuperada.getCoordenada().y)
    }


    @AfterEach
    fun borrarRegistros() {
        serviceUbicacion.deleteAll()
        dataService.cleanAll()
    }
}