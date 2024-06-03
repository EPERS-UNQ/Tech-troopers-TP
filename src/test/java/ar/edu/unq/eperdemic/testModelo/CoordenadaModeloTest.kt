package ar.edu.unq.eperdemic.testModelo

import ar.edu.unq.eperdemic.exceptions.ErrorCoordenadaInvalida
import ar.edu.unq.eperdemic.modelo.Coordenada
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CoordenadaModeloTest {

    @Test
    fun creacionDeUnaCoordenada() {
        val coordenada = Coordenada(80.0, -40.0)

        Assertions.assertEquals(coordenada.getLatitud(), 80.0)
        Assertions.assertEquals(coordenada.getLongitud(), -40.0)
    }

    @Test
    fun errorAlInicializarLaCoordenadaConLatitudInvalidaPorMínimo() {
        Assertions.assertThrows(ErrorCoordenadaInvalida::class.java) {
            Coordenada(-91.0, 80.0)
        }
    }

    @Test
    fun errorAlInicializarLaCoordenadaConLatitudInvalidaPorMáximo() {
        Assertions.assertThrows(ErrorCoordenadaInvalida::class.java) {
            Coordenada(91.0, 80.0)
        }
    }

    @Test
    fun errorAlInicializarLaCoordenadaConLongitudInvalidaPorMínimo() {
        Assertions.assertThrows(ErrorCoordenadaInvalida::class.java) {
            Coordenada(45.0, -181.0)
        }
    }

    @Test
    fun errorAlInicializarLaCoordenadaConLongitudInvalidaPorMáximo() {
        Assertions.assertThrows(ErrorCoordenadaInvalida::class.java) {
            Coordenada(45.0, 181.0)
        }
    }

}