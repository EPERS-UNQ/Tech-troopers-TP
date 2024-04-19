package ar.edu.unq.eperdemic.testModelo

import ar.edu.unq.eperdemic.modelo.Patogeno
import ar.edu.unq.eperdemic.modelo.vector.Vector
import ar.edu.unq.eperdemic.modelo.vector.TipoVector
import ar.edu.unq.eperdemic.modelo.Ubicacion
import ar.edu.unq.eperdemic.modelo.Especie
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.Test

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VectorModeloTest {

    lateinit var ubicacion : Ubicacion
    lateinit var humano : Vector
    lateinit var animal : Vector
    lateinit var insecto : Vector

    @BeforeEach
    fun prerate(){

        ubicacion = Ubicacion("Argentina")
        humano = Vector("Pedro", ubicacion, TipoVector.HUMANO)
        animal = Vector("Pepita", ubicacion, TipoVector.ANIMAL)
        insecto = Vector("Raul", ubicacion, TipoVector.INSECTO)

    }

    @Test
    fun seSabeElIdDeUnVector(){
        humano.setId(1) // Se settea ya que no se persiste.
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
    fun unVectorIntentaContagiarAOtroVectorYLoLogra(){
        val patogeno = Patogeno("Bacteria", 20, 25, 100, 1, 55)
        val viruela  = Especie("Viruela", patogeno, "Francia")
        humano.infectar(viruela)

        humano.contargiarA(insecto)

        Assertions.assertTrue(insecto.estaInfectadoCon(viruela))

    }

    @Test
    fun seSabeLasEnfermedadesDeUnVector(){
        val viruela = Especie()
        humano.infectar(viruela)

        Assertions.assertEquals(viruela.hashCode(), humano.enfermedadesDelVector().first().hashCode())
    }

}