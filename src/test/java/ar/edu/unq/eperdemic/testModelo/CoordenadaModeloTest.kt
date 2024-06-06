package ar.edu.unq.eperdemic.testModelo

import ar.edu.unq.eperdemic.exceptions.ErrorCoordenadaInvalida
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.data.mongodb.core.geo.GeoJsonPoint

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CoordenadaModeloTest {

    @Test
    fun creacionDeUnaCoordenada() {
        val coordenada = GeoJsonPoint(80.0, -40.0)

        Assertions.assertEquals(coordenada.x, 80.0)
        Assertions.assertEquals(coordenada.y, -40.0)
    }

    @Test
    fun errorAlInicializarLaCoordenadaConLatitudInvalidaPorMínimo() {
        Assertions.assertThrows(ErrorCoordenadaInvalida::class.java) {
            GeoJsonPoint(-91.0, 80.0)
        }
    }

    @Test
    fun errorAlInicializarLaCoordenadaConLatitudInvalidaPorMáximo() {
        Assertions.assertThrows(ErrorCoordenadaInvalida::class.java) {
            GeoJsonPoint(91.0, 80.0)
        }
    }

    @Test
    fun errorAlInicializarLaCoordenadaConLongitudInvalidaPorMínimo() {
        Assertions.assertThrows(ErrorCoordenadaInvalida::class.java) {
            GeoJsonPoint(45.0, -181.0)
        }
    }

    @Test
    fun errorAlInicializarLaCoordenadaConLongitudInvalidaPorMáximo() {
        Assertions.assertThrows(ErrorCoordenadaInvalida::class.java) {
            GeoJsonPoint(45.0, 181.0)
        }
    }

}