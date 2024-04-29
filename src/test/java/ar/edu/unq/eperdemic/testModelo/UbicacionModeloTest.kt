package ar.edu.unq.eperdemic.testModelo

import ar.edu.unq.eperdemic.exceptions.ErrorNombre
import ar.edu.unq.eperdemic.modelo.Ubicacion
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UbicacionModeloTest {

    lateinit var ubicacion: Ubicacion

    @BeforeEach
    fun crearModelo(){
        ubicacion = Ubicacion("Argentina")
    }

    @Test
    fun seSabeElNombreDeLaUbicacion() {
        Assertions.assertEquals("Argentina", ubicacion.getNombre())
    }

    @Test
    fun testCuandoSeIntentaCrearUnaUbicacionSinNombre(){

        val errorMensaje = Assertions.assertThrows(ErrorNombre::class.java){
            Ubicacion("")
        }

        Assertions.assertEquals("El nombre no puede ser vacio.", errorMensaje.message)
    }
}