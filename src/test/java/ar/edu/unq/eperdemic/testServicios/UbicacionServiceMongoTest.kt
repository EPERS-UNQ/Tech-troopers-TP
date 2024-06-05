package ar.edu.unq.eperdemic.testServicios

import ar.edu.unq.eperdemic.modelo.Coordenada
import ar.edu.unq.eperdemic.modelo.UbicacionMongo
import ar.edu.unq.eperdemic.persistencia.dao.UbicacionMongoDAO
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UbicacionServiceMongoTest {

    @Autowired private lateinit var ubicacionMongo : UbicacionMongoDAO // Cambiar a service...

    lateinit var ubi1: UbicacionMongo
    lateinit var coordenada: Coordenada
    lateinit var coordenada2: Coordenada

    @BeforeEach
    fun crearModelo() {

        coordenada = Coordenada(10.00, 10.00)

        ubicacionMongo.save(UbicacionMongo("Peru", coordenada))

    }

    @Test
    fun chequearUbiPersistida () {
        val ubi1 = ubicacionMongo.findByNombre("Peru")
        Assertions.assertEquals(ubi1!!.getNombre(), "Peru")
    }

    @Test
    /*fun chequearCoordenadasDeUbiPersistida (){
        val coordenada2 = ubicacionMongo.findCoordenadaByNombre("Argentina")!!
        Assertions.assertEquals(coordenada.getLongitud(), coordenada2.getLongitud())
    }*/

    @AfterEach
    fun borrarRegistros() {


    }
}