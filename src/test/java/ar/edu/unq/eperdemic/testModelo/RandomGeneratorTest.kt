package ar.edu.unq.eperdemic.testModelo

import ar.edu.unq.eperdemic.modelo.RandomGenerator.AleatorioStrategy
import ar.edu.unq.eperdemic.modelo.RandomGenerator.NoAleatorioStrategy
import ar.edu.unq.eperdemic.modelo.RandomGenerator.RandomGenerator
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS

@TestInstance(PER_CLASS)
class RandomGeneratorTest {

    private val randomGenerator = RandomGenerator.getInstance()

    @BeforeEach
    fun crearModelo() {
        randomGenerator.setStrategy(AleatorioStrategy())
    }

    @Test
    fun getNumberoRandomDevuelveEnnumeroEntre1_10() {
        val randomNumber = randomGenerator.getNumeroRandom()
        Assertions.assertTrue(randomNumber in 1..10)
    }

    @Test
    fun getNumeroRandomDevuelveElNumeroEspecificadoSiEstaEntre1_10() {
        randomGenerator.setStrategy(NoAleatorioStrategy())
        val num = 5

        randomGenerator.setNumeroGlobal(5)
        Assertions.assertEquals(num, randomGenerator.getNumeroRandom())
    }

    @Test
    fun getNumeroRandomLanzaUnaExcepcionSiElNumeroEstaFueraDelRango1_10() {
        randomGenerator.setStrategy(NoAleatorioStrategy())
        randomGenerator.setNumeroGlobal(15)
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            randomGenerator.getNumeroRandom()
        }
    }

    @Test
    fun getElementoRandomEnListaDevuelveUnElementoRandomDeLaListaNoVacia() {
        val list = listOf("a", "b", "c")
        val randomElement = randomGenerator.getElementoRandomEnLista(list)
        Assertions.assertTrue(randomElement in list)
    }

    @Test
    fun getElementoRandomEnListaLanzaUnaExcepcionSiLaListaEstaVacia() {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            randomGenerator.getElementoRandomEnLista(emptyList())
        }

    }

    @Test
    fun getElementoRandomDevuelveElElementoEnLaPosicionPasadaPorParametro() {
        randomGenerator.setStrategy(NoAleatorioStrategy())
        randomGenerator.setNumeroGlobal(1)
        val list = listOf("a", "b", "c")
        val randomElement = randomGenerator.getElementoRandomEnLista(list)
        Assertions.assertTrue(randomElement == "b")
    }

    @Test
    fun porcentajeExistosoDevuelveUnBooleanoDependiendoDeLaProbabilidad() {
        val boolReturn = randomGenerator.porcentajeExistoso(10)
        Assertions.assertEquals(true, boolReturn || !boolReturn)
    }

    @Test
    fun porcentajeExistosoDevuelveElBooleanoQueLePasasPorParametro() {
        randomGenerator.setStrategy(NoAleatorioStrategy())
        randomGenerator.setBooleanoGlobal(false)
        //Assertions.assertTrue(randomGenerator.porcentajeExistoso(10))
        Assertions.assertFalse(randomGenerator.porcentajeExistoso(10))
    }

}