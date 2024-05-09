package ar.edu.unq.eperdemic.testModelo

import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.Patogeno
import ar.edu.unq.eperdemic.modelo.RandomGenerator.NoAleatorioStrategy
import ar.edu.unq.eperdemic.modelo.RandomGenerator.RandomGenerator
import ar.edu.unq.eperdemic.modelo.Ubicacion
import ar.edu.unq.eperdemic.modelo.mutacion.BioalteracionGenetica
import ar.edu.unq.eperdemic.modelo.mutacion.Mutacion
import ar.edu.unq.eperdemic.modelo.mutacion.SupresionBiomecanica
import ar.edu.unq.eperdemic.modelo.vector.TipoVector
import ar.edu.unq.eperdemic.modelo.vector.Vector
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MutacionModeloTest {

    lateinit var vector1 : Vector
    lateinit var vector2 : Vector

    lateinit var ubicacion1 : Ubicacion

    lateinit var especie  : Especie
    lateinit var especie2  : Especie
    lateinit var patogeno : Patogeno

    lateinit var supresionBiomecanica : Mutacion
    lateinit var bioalteracionMecanica : Mutacion

    lateinit var random : RandomGenerator

    @BeforeEach
    fun crearModelo() {
        ubicacion1 = Ubicacion("El Paraguay, perro")

        vector1 = Vector("araña", ubicacion1, TipoVector.INSECTO)
        vector2 = Vector("mosquito", ubicacion1, TipoVector.INSECTO)

        patogeno = Patogeno("Bacteria", 20, 25, 100, 1, 55)

        especie = Especie("Wachiturro", patogeno, "Argentina")
        especie.setId(1)
        especie2 = Especie("Decadente", patogeno, "Peru, papa")
        especie2.setId(2)

        bioalteracionMecanica = BioalteracionGenetica(TipoVector.INSECTO)
        bioalteracionMecanica.setId(1)
        supresionBiomecanica = SupresionBiomecanica(30)
        supresionBiomecanica.setId(2)

        random = RandomGenerator.getInstance()
        random.setStrategy(NoAleatorioStrategy())
        random.setNumeroGlobal(1)
    }

    @Test
    fun esPosibleAgregarUnaNuevaMutacionAUnaEspecie() {
        bioalteracionMecanica.mutarLaEspecie(especie)
        Assertions.assertTrue(especie.tieneLaMutacion(bioalteracionMecanica))
    }

    @Test
    fun supresionBionmecanicaSabeSuPotenciaDeSupresion() {
        Assertions.assertEquals(30, supresionBiomecanica.atributo())
    }

    @Test
    fun bioalteracionMecanicaSabeSuNuevoTipoDeVector() {
        Assertions.assertEquals(TipoVector.INSECTO, bioalteracionMecanica.atributo())
    }

    @Test
    fun siAlgunVectorTieneLaMutacionBioalteracionMecanicaDeTipoInsectoPuedeContagiarAUnInsectoSiOSi() {
        Assertions.assertFalse(vector1.puedeContagiarA(vector2))

        vector1.mutarConMutacionRandom(listOf(bioalteracionMecanica))

        Assertions.assertTrue(vector1.puedeContagiarA(vector2))
    }

    @Test
    fun siUnaMutacionEsDeTipoSupresionBiomecanicaPuedeELiminarLasEspeciesInferioresDeUnVector() {
        bioalteracionMecanica.mutarLaEspecie(especie)
        supresionBiomecanica.mutarLaEspecie(especie2)
        vector1.infectar(especie)
        vector1.infectar(especie2)

        supresionBiomecanica.eliminarEspeciesInferiores(vector1)

        Assertions.assertFalse(vector1.estaInfectadoCon(especie))
    }

}