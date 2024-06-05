package ar.edu.unq.eperdemic.testServicios

import ar.edu.unq.eperdemic.modelo.Coordenada
import ar.edu.unq.eperdemic.modelo.UbicacionMongo
import ar.edu.unq.eperdemic.persistencia.dao.UbicacionMongoDAO
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension



@SpringBootTest
class PruebaTest {
    @Autowired private lateinit var ubicacionMongo : UbicacionMongoDAO

    lateinit var ubi1: UbicacionMongo
    lateinit var coordenada: Coordenada
    lateinit var coordenada2: Coordenada

    @BeforeEach
    fun crearModelo() {
        coordenada = Coordenada(10.00, 10.00)

        ubicacionMongo.save(UbicacionMongo("Peru", coordenada))

    }

    @Test
    fun algo (){
        val ubi1 = ubicacionMongo.findByNombre("Argentina")
        Assertions.assertEquals(ubi1.getNombre(), "Argentina")
    }

    @Test
    /*fun algo2 (){
        val coordenada2 = ubicacionMongo.findCoordenadaByNombre("Argentina")!!
        Assertions.assertEquals(coordenada.getLongitud(), coordenada2.getLongitud())
    }*/

    @AfterEach
    fun borrarRegistros() {


    }
}