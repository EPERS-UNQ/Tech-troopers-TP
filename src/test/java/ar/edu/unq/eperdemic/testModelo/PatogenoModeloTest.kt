package ar.edu.unq.eperdemic.testModelo

import ar.edu.unq.eperdemic.exceptions.LimiteDeCampoErroneo
import ar.edu.unq.eperdemic.exceptions.NoHayVectorException
import ar.edu.unq.eperdemic.modelo.Patogeno
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PatogenoModeloTest {

    lateinit var salmonella: Patogeno
    @BeforeEach
    fun prepare() {

        salmonella = Patogeno("Salmonella", 70, 10, 15, 30, 66)

        salmonella.setId(1)
        salmonella.cantidadDeEspecies = 0

    }

    @Test
    fun seSabeElIdDeUnPatogeno() {
        Assertions.assertEquals(salmonella.getId(), 1)
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

    @Test
    fun errorAlIntentarInicializarUnPatogenoConUnValorNoPermitido(){
        Assertions.assertThrows(LimiteDeCampoErroneo::class.java) {
            Patogeno("Bacteria", 70, 10, 115, 30, 66)
        }

        Assertions.assertThrows(LimiteDeCampoErroneo::class.java) {
            Patogeno("Bacteria", -5, 10, 80, 30, 66)
        }
    }

}