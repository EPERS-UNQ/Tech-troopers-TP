package ar.edu.unq.eperdemic.testModelo

import ar.edu.unq.eperdemic.exceptions.ErrorNombre
import ar.edu.unq.eperdemic.modelo.Patogeno
import ar.edu.unq.eperdemic.modelo.vector.Vector
import ar.edu.unq.eperdemic.modelo.vector.TipoVector
import ar.edu.unq.eperdemic.modelo.UbicacionJpa
import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.RandomGenerator.NoAleatorioStrategy
import ar.edu.unq.eperdemic.modelo.RandomGenerator.RandomGenerator
import ar.edu.unq.eperdemic.modelo.mutacion.Mutacion
import ar.edu.unq.eperdemic.modelo.mutacion.SupresionBiomecanica
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.Test

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VectorModeloTest {

    lateinit var ubicacion : UbicacionJpa
    lateinit var humano  : Vector
    lateinit var animal  : Vector
    lateinit var insecto : Vector
    lateinit var random  : RandomGenerator
    lateinit var patogeno : Patogeno
    lateinit var viruela  : Especie
    lateinit var neurosinérgico : Mutacion
    lateinit var electrogenoide : Mutacion

    @BeforeEach
    fun prerate(){

        ubicacion = UbicacionJpa("Argentina")
        humano = Vector("Pedro", ubicacion, TipoVector.HUMANO)
        animal = Vector("Pepita", ubicacion, TipoVector.ANIMAL)
        insecto = Vector("Raul", ubicacion, TipoVector.INSECTO)
        neurosinérgico = SupresionBiomecanica(30)
        electrogenoide = SupresionBiomecanica(15)

        random = RandomGenerator.getInstance()
        random.setStrategy(NoAleatorioStrategy())
        random.setBooleanoGlobal(true)
        random.setNumeroGlobal(1)

        patogeno = Patogeno("Bacteria", 100, 100, 100, 1, 55)
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

        humano.infectar(viruela)

        Assertions.assertTrue(humano.estaInfectadoCon(viruela))
    }

    @Test
    fun unHumanoIntentaContagiarAUnAnimalYNoLoLogra(){

        humano.infectar(viruela)

        humano.contargiarA(animal)

        Assertions.assertFalse(animal.estaInfectadoCon(viruela))

    }

    @Test
    fun unAnimalIntentaContagiarAOtroAnimalYNoLoLogra(){

        var animal2 = Vector("Flocky", ubicacion, TipoVector.ANIMAL)

        animal.infectar(viruela)

        animal.contargiarA(animal2)

        Assertions.assertFalse(animal2.estaInfectadoCon(viruela))

    }

    @Test
    fun unInsectoIntentaContagiarAOtroInsectoYNoLoLogra(){

        var insecto2 = Vector("Mosca", ubicacion, TipoVector.INSECTO)

        insecto.infectar(viruela)

        insecto.contargiarA(insecto2)

        Assertions.assertFalse(insecto2.estaInfectadoCon(viruela))

    }

    @Test
    fun unVectorIntentaContagiarAOtroVectorYNoLoLogra(){

        random.setBooleanoGlobal(false)

        humano.infectar(viruela)

        humano.contargiarA(insecto)

        Assertions.assertFalse(insecto.estaInfectadoCon(viruela))

    }

    @Test
    fun errorAlIntentarInicializarUnVectorSinNombre(){

        val errorMensaje = Assertions.assertThrows(ErrorNombre::class.java){
            Vector("", ubicacion, TipoVector.HUMANO)
        }

        Assertions.assertEquals("El nombre del vector no puede estar vacio.", errorMensaje.message)
    }

    @Test
    fun seSabeLasEnfermedadesDeUnVector(){

        humano.infectar(viruela)

        Assertions.assertEquals(viruela.hashCode(), humano.enfermedadesDelVector().first().hashCode())
    }

}