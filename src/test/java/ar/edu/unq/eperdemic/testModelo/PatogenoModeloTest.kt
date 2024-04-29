package ar.edu.unq.eperdemic.testModelo

import ar.edu.unq.eperdemic.exceptions.ErrorNombre
import ar.edu.unq.eperdemic.exceptions.LimiteDeCampoErroneo
import ar.edu.unq.eperdemic.modelo.Patogeno
import ar.edu.unq.eperdemic.modelo.RandomGenerator.NoAleatorioStrategy
import ar.edu.unq.eperdemic.modelo.RandomGenerator.RandomGenerator
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PatogenoModeloTest {

    lateinit var salmonella: Patogeno
    lateinit var random: RandomGenerator
    @BeforeEach
    fun prepare() {

        salmonella = Patogeno("Salmonella", 70, 10, 15, 30, 66)

        salmonella.setId(1)
        salmonella.cantidadDeEspecies = 0
        random = RandomGenerator.getInstance()
        random.setStrategy(NoAleatorioStrategy())
        random.setNumeroGlobal(0)

    }

    @Test
    fun seSabeElIdDeUnPatogeno() {
        Assertions.assertEquals(1, salmonella.getId())
    }

    @Test
    fun seSabeElTipoDeUnPatogeno() {
        Assertions.assertEquals("Salmonella", salmonella.toString())
    }

    @Test
    fun seSabeLaCantidadDeEspeciesDeUnPatogeno() {
        Assertions.assertEquals(0, salmonella.cantidadDeEspecies)
    }

    @Test
    fun seSabeLaCapacidadDeContagioAHumanosDeUnPatogeno() {
        Assertions.assertEquals(70, salmonella.capContagioHumano)
    }

    @Test
    fun seSabeLaCapacidadDeContagioAAnimalesDeUnPatogeno() {
        Assertions.assertEquals(10, salmonella.capContagioAnimal)
    }

    @Test
    fun seSabeLaCapacidadDeContagioAInsectosDeUnPatogeno() {
        Assertions.assertEquals(15, salmonella.capContagioInsecto)
    }

    @Test
    fun seSabeLaDefensaDeUnPatogeno() {
        Assertions.assertEquals(30, salmonella.defensa)
    }

    @Test
    fun seSabeLaCapacidadDeBiomecanizacionDeUnPatogeno() {
        Assertions.assertEquals(66, salmonella.capDeBiomecanizacion)
    }

    @Test
    fun sePuedeCrearUnaEspecieNuevaDeUnPatogeno() {

        val enterica = salmonella.crearEspecie("Enterica", "Arg")

        Assertions.assertEquals(1, salmonella.cantidadDeEspecies)
        Assertions.assertEquals("Enterica", enterica.nombre)

    }

    @Test
    fun errorAlIntentarInicializarUnPatogenoConUnValorDeContagioDeHumanoNoPermitido(){
        Assertions.assertThrows(LimiteDeCampoErroneo::class.java) {
            Patogeno("Bacteria", 115, 10, 10, 30, 66)
        }

        Assertions.assertThrows(LimiteDeCampoErroneo::class.java) {
            Patogeno("Bacteria", -5, 10, 80, 30, 66)
        }
    }

    @Test
    fun errorAlIntentarInicializarUnPatogenoConUnValorDeContagioDeAnimalesNoPermitido(){
        Assertions.assertThrows(LimiteDeCampoErroneo::class.java) {
            Patogeno("Bacteria", 15, 150, 10, 30, 66)
        }

        Assertions.assertThrows(LimiteDeCampoErroneo::class.java) {
            Patogeno("Bacteria", 5, -10, 80, 30, 66)
        }
    }

    @Test
    fun errorAlIntentarInicializarUnPatogenoConUnValorDeContagioDeInsectosNoPermitido(){
        Assertions.assertThrows(LimiteDeCampoErroneo::class.java) {
            Patogeno("Bacteria", 15, 10, 250, 30, 66)
        }

        Assertions.assertThrows(LimiteDeCampoErroneo::class.java) {
            Patogeno("Bacteria", 5, 40, -8, 30, 66)
        }
    }

    @Test
    fun errorAlIntentarInicializarUnPatogenoConUnValorDeDefensaNoPermitido(){
        Assertions.assertThrows(LimiteDeCampoErroneo::class.java) {
            Patogeno("Bacteria", 15, 12, 10, 450, 66)
        }

        Assertions.assertThrows(LimiteDeCampoErroneo::class.java) {
            Patogeno("Bacteria", 5, 10, 80, -30, 66)
        }
    }

    @Test
    fun errorAlIntentarInicializarUnPatogenoConUnValorDeBiomecanizacionNoPermitido(){
        Assertions.assertThrows(LimiteDeCampoErroneo::class.java) {
            Patogeno("Bacteria", 15, 10, 10, 30, 466)
        }

        Assertions.assertThrows(LimiteDeCampoErroneo::class.java) {
            Patogeno("Bacteria", 5, 30, 80, 30, -10)
        }
    }

    @Test
    fun errorAlIntentarInicializarUnPatogenoSinNombre(){
        val mensajeError = Assertions.assertThrows(ErrorNombre::class.java) {
            Patogeno("", 70, 10, 15, 30, 66)
        }

        Assertions.assertEquals("El nombre del tipo del patogeno no puede ser vacio.", mensajeError.message)
    }

}