package ar.edu.unq.eperdemic.testModelo

import ar.edu.unq.eperdemic.modelo.RandomGenerator
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS

@TestInstance(PER_CLASS)
class RandomGeneratorTest {

    private val randomGenerator = RandomGenerator()

    @Test
    fun getNumberoRandomDevuelveEnnumeroEntre1_10() {
        val randomNumber = randomGenerator.getNumeroRandom()
        Assertions.assertTrue(randomNumber in 1..10)
    }

    @Test
    fun getNumeroEspecificoDevuelveElNumeroEspecificadoSiEstaEntre1_10() {
        val num = 5
        Assertions.assertEquals(num, randomGenerator.getNumeroEspecifico(num))
    }

    @Test
    fun getNumeroEspecificoLanzaUnaExcepcionSiElNumeroEstaFueraDelRango1_10() {
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            randomGenerator.getNumeroEspecifico(15)
        }
    }

    @Test
    fun getElementoRandomEnListaDevuelveUnElementoDeLaListaNoVacia() {
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


}