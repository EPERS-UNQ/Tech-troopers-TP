package ar.edu.unq.eperdemic.testModelo

import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.Patogeno
import ar.edu.unq.eperdemic.modelo.RandomGenerator.NoAleatorioStrategy
import ar.edu.unq.eperdemic.modelo.RandomGenerator.RandomGenerator
import ar.edu.unq.eperdemic.modelo.mutacion.BioalteracionGenetica
import ar.edu.unq.eperdemic.modelo.mutacion.Mutacion
import ar.edu.unq.eperdemic.modelo.mutacion.SupresionBiomecanica
import ar.edu.unq.eperdemic.modelo.vector.TipoVector
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MutacionModeloTest {

    lateinit var especie  : Especie
    lateinit var patogeno : Patogeno
    lateinit var supresionBiomecanica : Mutacion
    lateinit var bioalteracionMecanica : Mutacion
    lateinit var random : RandomGenerator

    @BeforeEach
    fun crearModelo() {
        patogeno = Patogeno("Bacteria", 20, 25, 100, 1, 55)
        especie = Especie("Wachiturro", patogeno, "Argentina")
        bioalteracionMecanica = BioalteracionGenetica(TipoVector.INSECTO)
        supresionBiomecanica = SupresionBiomecanica(30)
        random = RandomGenerator.getInstance()
        random.setStrategy(NoAleatorioStrategy())
        random.setNumeroGlobal(0)
    }

    @Test
    fun supresionBionmecanicaSabeSuPotenciaDeSupresion() {
        Assertions.assertEquals(30, supresionBiomecanica.atributo())
    }

    @Test
    fun bioalteracionMecanicaSabeSuNuevoTipoDeVector() {
        Assertions.assertEquals(TipoVector.INSECTO, bioalteracionMecanica.atributo())
    }















}