package ar.edu.unq.eperdemic.testModelo

import ar.edu.unq.eperdemic.exceptions.ErrorNombre
import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.Patogeno
import ar.edu.unq.eperdemic.modelo.RandomGenerator.NoAleatorioStrategy
import ar.edu.unq.eperdemic.modelo.RandomGenerator.RandomGenerator
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EspecieModeloTest {

    lateinit var especie  : Especie
    lateinit var patogeno : Patogeno
    lateinit var random : RandomGenerator

    @BeforeEach
    fun crearModelo() {
        patogeno = Patogeno("Bacteria", 20, 25, 100, 1, 55)
        especie = Especie("Wachiturro", patogeno, "Argentina")
        random = RandomGenerator.getInstance()
        random.setStrategy(NoAleatorioStrategy())
        random.setNumeroGlobal(0)
    }

    @Test
    fun seSabeElNombreDeLaEspecie() {
        Assertions.assertEquals("Wachiturro", especie.nombre)
    }

    @Test
    fun seSabeElPatogenoDeLaEspecie() {
        Assertions.assertEquals(patogeno, especie.patogeno)
    }

    @Test
    fun testCuandoSeIntentaCrearUnaEspecieSinNombre() {

        Assertions.assertThrows(ErrorNombre::class.java){
            Especie(null, patogeno, "Argentina")
        }

    }

    @Test
    fun seSabeElPaisDeOrigneDeLaEspecie() {
        Assertions.assertEquals("Argentina", especie.paisDeOrigen)
    }

}