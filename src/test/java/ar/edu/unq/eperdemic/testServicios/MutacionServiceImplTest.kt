package ar.edu.unq.eperdemic.testServicios

import ar.edu.unq.eperdemic.exceptions.NoExisteLaEspecie
import ar.edu.unq.eperdemic.helper.dao.HibernateDataDAO
import ar.edu.unq.eperdemic.helper.service.DataService
import ar.edu.unq.eperdemic.helper.service.DataServiceImpl
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
import ar.edu.unq.eperdemic.services.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MutacionServiceImplTest {

    @Autowired lateinit var servicioMutacion: MutacionService
    @Autowired lateinit var servicioPatogeno: PatogenoService
    @Autowired lateinit var servicioVector: VectorService
    @Autowired lateinit var servicioEspecie: EspecieService
    @Autowired lateinit var servicioUbicacion: UbicacionService

    lateinit var random : RandomGenerator
    lateinit var dataService: DataService

    lateinit var colera: Patogeno
    lateinit var viruela: Patogeno
    lateinit var rabia: Patogeno
    lateinit var china: Ubicacion
    lateinit var corea: Ubicacion
    lateinit var japon: Ubicacion
    lateinit var john: Vector
    lateinit var viktor: Vector
    lateinit var monoAndroide: Vector
    lateinit var cromaColera: Especie
    lateinit var mecaViruela: Especie
    lateinit var roboRabia: Especie
    lateinit var supresionBiomecanica: Mutacion
    lateinit var bioalteracionMecanica: Mutacion


    @BeforeEach
    fun crearModelo() {

        dataService = DataServiceImpl(HibernateDataDAO())
        colera = Patogeno("Colera", 90, 5, 1, 60, 95)
        viruela = Patogeno("Viruela", 70, 10, 15, 30, 66)
        rabia = Patogeno("Rabia",1,1,1,35,1)
        corea = Ubicacion("Corea")
        japon = Ubicacion("Japon")
        china = Ubicacion("China")
        john = Vector("John", corea, TipoVector.HUMANO)
        viktor = Vector("Viktor", japon, TipoVector.HUMANO)
        monoAndroide = Vector("Mono-17", china, TipoVector.ANIMAL)
        supresionBiomecanica = SupresionBiomecanica(35)
        bioalteracionMecanica = BioalteracionGenetica(TipoVector.ANIMAL)

        servicioUbicacion.crear(corea)
        servicioUbicacion.crear(japon)
        servicioUbicacion.crear(china)
        servicioPatogeno.crear(colera)
        servicioPatogeno.crear(viruela)
        servicioPatogeno.crear(rabia)
        servicioVector.crear(john)
        servicioVector.crear(viktor)
        servicioVector.crear(monoAndroide)

        cromaColera = servicioPatogeno.agregarEspecie(colera.getId(), "Croma Colera", china.getId()!!)
        mecaViruela = servicioPatogeno.agregarEspecie(viruela.getId(), "Meca-Viruela", japon.getId()!!)
        roboRabia = servicioPatogeno.agregarEspecie(rabia.getId(), "Robo Rabia", corea.getId()!!)

        random = RandomGenerator.getInstance()
        random.setStrategy(NoAleatorioStrategy())
        random.setNumeroGlobal(0)

    }

    @Test
    fun seTrataDeAgregarUnaNuevaMutacionAUnaEspecieQueNoExiste() {

        Assertions.assertThrows(NoExisteLaEspecie::class.java) {
            servicioMutacion.agregarMutacion(17, supresionBiomecanica)
        }
    }

    @Test
    fun inicialmenteUnaEspecieNoTieneMutacionesPosibles() {

        Assertions.assertEquals(0, mecaViruela.cantidadDeMutaciones())

    }

    @Test
    fun seAgregaUnaNuevaMutacionAUnaEspecie() {

        servicioMutacion.agregarMutacion(mecaViruela.getId()!!, supresionBiomecanica)
        val otraMecaViruela = servicioEspecie.recuperar(mecaViruela.getId()!!)

        Assertions.assertEquals(2, supresionBiomecanica.getId()!!)
        Assertions.assertEquals(1, mecaViruela.cantidadDeMutaciones())
        Assertions.assertTrue(mecaViruela.tieneLaMutacion(supresionBiomecanica))

    }

    @Test
    fun unaEspeciePuedeTenerVariasMutacionesPosibles() {

        servicioMutacion.agregarMutacion(mecaViruela.getId()!!, supresionBiomecanica)
        servicioMutacion.agregarMutacion(mecaViruela.getId()!!, bioalteracionMecanica)

        Assertions.assertEquals(2, mecaViruela.cantidadDeMutaciones())

    }
//
//    @Test
//    fun unVectorMutaConExitoLuegoDeContagiarAOtro() {
//        servicioVector.infectar()
//    }


    @AfterEach
    fun tearDown() {
        dataService.cleanAll()
    }


}