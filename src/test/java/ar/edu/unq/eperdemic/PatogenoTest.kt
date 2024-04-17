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

        salmonella = Patogeno("Salmonella")

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
    fun sePuedeCrearUnaEspecieNuevaDeUnPatogeno() {

        val enterica = salmonella.crearEspecie("Enterica", "Arg")

        Assertions.assertEquals(salmonella.cantidadDeEspecies, 1)
        Assertions.assertEquals(enterica.nombre, "Enterica")

    }

}