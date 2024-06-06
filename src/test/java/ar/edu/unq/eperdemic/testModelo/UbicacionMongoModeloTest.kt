package ar.edu.unq.eperdemic.testModelo

import ar.edu.unq.eperdemic.exceptions.ErrorNombre
import ar.edu.unq.eperdemic.modelo.ubicacion.UbicacionMongo
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.data.mongodb.core.geo.GeoJsonPoint


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UbicacionMongoModeloTest {

    lateinit var ubicacion: UbicacionMongo

    @BeforeEach
    fun crearModelo(){
        ubicacion = UbicacionMongo("Argentina", GeoJsonPoint(25.00, 20.00))
    }

    @Test
    fun seSabeElNombreDeLaUbicacion() {
        Assertions.assertEquals("Argentina", ubicacion.getNombre())
    }

    @Test
    fun errorAlIntentarCrearUnaUbicacionSinNombre(){

        val errorMensaje = Assertions.assertThrows(ErrorNombre::class.java){
            UbicacionMongo("", GeoJsonPoint(25.00, 20.00))
        }

        Assertions.assertEquals("El nombre no puede ser vacio.", errorMensaje.message)
    }

}