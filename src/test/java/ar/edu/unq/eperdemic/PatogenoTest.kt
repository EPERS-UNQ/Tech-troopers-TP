package ar.edu.unq.eperdemic

import ar.edu.unq.eperdemic.modelo.Patogeno
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PatogenoTest {

    lateinit var salmonella: Patogeno

    @BeforeEach
    fun prepare() {

        salmonella = Patogeno("Salmonella", 70, 10, 15, 30, 66)

        salmonella.id = 1
        salmonella.cantidadDeEspecies = 0

    }

    @Test
    fun seSabeElIdDeUnPatogeno() {
        Assertions.assertEquals(salmonella.id, 1)
    }

    @Test
    fun seSabeElTipoDeUnPatogeno() {
        Assertions.assertEquals(salmonella.toString(), "Salmonella")
    }

    @Test
    fun seSabeLaCantidadDeEspeciesDeUnPatogeno() {
        Assertions.assertEquals(salmonella.cantidadDeEspecies, 0)
    }

    @Test
    fun seSabeLaCapacidadDeContagioAHumanosDeUnPatogeno() {
        Assertions.assertEquals(salmonella.capContagioHumano, 70)
    }

    @Test
    fun seSabeLaCapacidadDeContagioAAnimalesDeUnPatogeno() {
        Assertions.assertEquals(salmonella.capContagioAnimal, 10)
    }

    @Test
    fun seSabeLaCapacidadDeContagioAInsectosDeUnPatogeno() {
        Assertions.assertEquals(salmonella.capContagioInsecto, 15)
    }

    @Test
    fun seSabeLaDefensaDeUnPatogeno() {
        Assertions.assertEquals(salmonella.defensa, 30)
    }

    @Test
    fun seSabeLaCapacidadDeBiomecanizacionDeUnPatogeno() {
        Assertions.assertEquals(salmonella.capDeBiomecanizacion, 66)
    }

    @Test
    fun sePuedeCrearUnaEspecieNuevaDeUnPatogeno() {

        val enterica = salmonella.crearEspecie("Enterica", "Arg")

        Assertions.assertEquals(salmonella.cantidadDeEspecies, 1)
        Assertions.assertEquals(enterica.nombre, "Enterica")

    }

}