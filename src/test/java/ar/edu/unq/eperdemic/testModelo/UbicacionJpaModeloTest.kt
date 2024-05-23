package ar.edu.unq.eperdemic.testModelo

import ar.edu.unq.eperdemic.exceptions.ErrorNombre
import ar.edu.unq.eperdemic.modelo.UbicacionJpa
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UbicacionJpaModeloTest {

    lateinit var ubicacion: UbicacionJpa

    @BeforeEach
    fun crearModelo(){
        ubicacion = UbicacionJpa("Argentina")
    }

    @Test
    fun seSabeElNombreDeLaUbicacion() {
        Assertions.assertEquals("Argentina", ubicacion.getNombre())
    }

    @Test
    fun errorAlIntentarCrearUnaUbicacionSinNombre(){

        val errorMensaje = Assertions.assertThrows(ErrorNombre::class.java){
            UbicacionJpa("")
        }

        Assertions.assertEquals("El nombre no puede ser vacio.", errorMensaje.message)
    }
}