package ar.edu.unq.eperdemic.testModelo

import ar.edu.unq.eperdemic.modelo.Patogeno
import ar.edu.unq.eperdemic.modelo.vector.Vector
import ar.edu.unq.eperdemic.modelo.vector.TipoVector
import ar.edu.unq.eperdemic.modelo.Ubicacion
import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.RandomGenerator.AleatorioStrategy
import ar.edu.unq.eperdemic.modelo.RandomGenerator.NoAleatorioStrategy
import ar.edu.unq.eperdemic.modelo.RandomGenerator.RandomGenerator
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.Test

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VectorModeloTest {

    lateinit var ubicacion : Ubicacion
    lateinit var humano  : Vector
    lateinit var animal  : Vector
    lateinit var insecto : Vector
    lateinit var random  : RandomGenerator
    lateinit var patogeno : Patogeno
    lateinit var viruela  : Especie

    @BeforeEach
    fun prerate(){

        ubicacion = Ubicacion("Argentina")
        humano = Vector("Pedro", ubicacion, TipoVector.HUMANO)
        animal = Vector("Pepita", ubicacion, TipoVector.ANIMAL)
        insecto = Vector("Raul", ubicacion, TipoVector.INSECTO)

        random = RandomGenerator.getInstance()
        random.setStrategy(NoAleatorioStrategy())
        random.setBooleanoGlobal(true)

        patogeno = Patogeno("Bacteria", 20, 25, 100, 1, 55)
        viruela  = Especie("Viruela", patogeno, "Francia")
    }

    @Test
    fun seSabeElIdDeUnVector(){
        humano.setId(1)
        Assertions.assertEquals(humano.getId(), 1)
    }

    @Test
    fun seSabeElTipoDeUnVector() {
        Assertions.assertEquals(TipoVector.HUMANO, humano.getTipo())
    }

    @Test
    fun seSabeSiElVectorEstaInfectado() {
        Assertions.assertFalse(humano.estaInfectado())
    }

    @Test
    fun unVectorNoEstaInfectadoConUnaEspecieEnParticular() {
        val viruela = Especie()

        Assertions.assertFalse(humano.estaInfectadoCon(viruela))
    }

    @Test
    fun unVectorEstaInfectadoConUnaEspecieEnParticular() {
        val viruela = Especie()

        humano.infectar(viruela)

        Assertions.assertTrue(humano.estaInfectadoCon(viruela))
    }

    @Test
    fun unHumanoIntentaContagiarAUnInsectoYLoLogra(){

        humano.infectar(viruela)

        humano.contargiarA(insecto)

        Assertions.assertTrue(insecto.estaInfectadoCon(viruela))

    }

    @Test
    fun unHumanoIntentaContagiarAOtroHumanoYLoLogra(){

        val humano2 = Vector("Guido", ubicacion, TipoVector.HUMANO)

        humano.infectar(viruela)

        humano.contargiarA(humano2)

        Assertions.assertTrue(humano2.estaInfectadoCon(viruela))

    }

    @Test
    fun unHumanoIntentaContagiarAUnAnimalYNoLoLogra(){

        humano.infectar(viruela)

        humano.contargiarA(animal)

        Assertions.assertFalse(animal.estaInfectadoCon(viruela))

    }

    @Test
    fun unAnimalIntentaContagiarAUnHumanoYLoLogra(){

        animal.infectar(viruela)

        animal.contargiarA(humano)

        Assertions.assertTrue(humano.estaInfectadoCon(viruela))

    }

    @Test
    fun unAnimalIntentaContagiarAUnInsectoYLoLogra(){

        animal.infectar(viruela)

        animal.contargiarA(insecto)

        Assertions.assertTrue(insecto.estaInfectadoCon(viruela))

    }

    @Test
    fun unAnimalIntentaContagiarAOtroAnimalYNoLoLogra(){

        var animal2 = Vector("Flocky", ubicacion, TipoVector.ANIMAL)

        animal.infectar(viruela)

        animal.contargiarA(animal2)

        Assertions.assertFalse(animal2.estaInfectadoCon(viruela))

    }

    @Test
    fun unInsectoIntentaContagiarAUnHumanoYLoLogra(){

        insecto.infectar(viruela)

        insecto.contargiarA(humano)

        Assertions.assertTrue(humano.estaInfectadoCon(viruela))

    }

    @Test
    fun unInsectoIntentaContagiarAUnAnimalYLoLogra(){

        insecto.infectar(viruela)

        insecto.contargiarA(animal)

        Assertions.assertTrue(animal.estaInfectadoCon(viruela))

    }

    @Test
    fun unInsectoIntentaContagiarAOtroInsectoYNoLoLogra(){

        var insecto2 = Vector("Mosca", ubicacion, TipoVector.INSECTO)

        insecto.infectar(viruela)

        insecto.contargiarA(insecto2)

        Assertions.assertFalse(insecto2.estaInfectadoCon(viruela))

    }

    @Test
    fun unVectorIntentaContagiarAOtroVectorYLoLogra(){

        random.setStrategy(AleatorioStrategy())
        random.setBooleanoGlobal(false)
        humano.infectar(viruela)

        humano.contargiarA(insecto)

        Assertions.assertTrue(insecto.estaInfectadoCon(viruela))

    }

    @Test
    fun unVectorIntentaContagiarAOtroVectorYNoLoLogra(){

        random.setBooleanoGlobal(false)
        humano.infectar(viruela)

        humano.contargiarA(insecto)

        Assertions.assertFalse(insecto.estaInfectadoCon(viruela)) // ESTE

    }

    @Test
    fun seSabeLasEnfermedadesDeUnVector(){
        val viruela = Especie()
        humano.infectar(viruela)

        Assertions.assertEquals(viruela.hashCode(), humano.enfermedadesDelVector().first().hashCode())
    }

}