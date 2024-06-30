package ar.edu.unq.eperdemic.testServicios

import ar.edu.unq.eperdemic.helper.dao.HibernateDataDAO
import ar.edu.unq.eperdemic.helper.service.DataService
import ar.edu.unq.eperdemic.helper.service.DataServiceImpl
import ar.edu.unq.eperdemic.modelo.ubicacion.UbicacionGlobal
import ar.edu.unq.eperdemic.modelo.vector.TipoVector
import ar.edu.unq.eperdemic.modelo.vector.Vector
import ar.edu.unq.eperdemic.services.UbicacionService
import ar.edu.unq.eperdemic.services.VectorService
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
@TestInstance(PER_CLASS)
class VectorElasticServiceTest {

    @Autowired lateinit var service: VectorService
    @Autowired lateinit var serviceUbicacion: UbicacionService

    lateinit var dataService: DataService

    lateinit var vector: Vector
    lateinit var ubicacion: UbicacionGlobal

    @BeforeEach
    fun crearModelo() {
        ubicacion = UbicacionGlobal("McDonalds", GeoJsonPoint(20.00, 20.00))
        serviceUbicacion.crear(ubicacion)

        vector = Vector("Pedro", ubicacion.aJPA(), TipoVector.HUMANO)
        service.crear(vector)

        dataService = DataServiceImpl(HibernateDataDAO())
    }

    @Test
    fun test() {
        Assertions.assertTrue(true)
    }

   /* @Test
    fun testDeRecuperacionDeTodosLosVectoresElastic() {
        val vector2 = Vector("Fatiga", ubicacion.aJPA(), TipoVector.ANIMAL)
        service.crear(vector2)

        val vectoresElasticRecuperados = service.recuperarTodosElastic()

        Assertions.assertEquals(2, vectoresElasticRecuperados.size)
        Assertions.assertEquals("Pedro", vectoresElasticRecuperados[0].nombre)
        Assertions.assertEquals("McDonalds", vectoresElasticRecuperados[0].ubicaciones[0].getNombre())
        Assertions.assertEquals("Fatiga", vectoresElasticRecuperados[1].nombre)
        Assertions.assertEquals("McDonalds", vectoresElasticRecuperados[1].ubicaciones[0].getNombre())
    }*/

    @AfterEach
    fun borrarModelo() {
        service.deleteAll()
        dataService.cleanAll()
        serviceUbicacion.deleteAll()
    }

}