package ar.edu.unq.eperdemic.testServicios

import ar.edu.unq.eperdemic.exceptions.NoExisteLaEspecie
import ar.edu.unq.eperdemic.helper.dao.HibernateDataDAO
import ar.edu.unq.eperdemic.helper.service.DataService
import ar.edu.unq.eperdemic.helper.service.DataServiceImpl
import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.Patogeno
import ar.edu.unq.eperdemic.modelo.RandomGenerator.NoAleatorioStrategy
import ar.edu.unq.eperdemic.modelo.RandomGenerator.RandomGenerator
import ar.edu.unq.eperdemic.modelo.UbicacionJpa
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
    lateinit var china: UbicacionJpa
    lateinit var corea: UbicacionJpa
    lateinit var tailandia: UbicacionJpa
    lateinit var japon: UbicacionJpa
    lateinit var indonesia: UbicacionJpa
    lateinit var john: Vector
    lateinit var viktor: Vector
    lateinit var monoAndroide: Vector
    lateinit var cromaColera: Especie
    lateinit var mecaViruela: Especie
    lateinit var roboRabia: Especie
    lateinit var supresionBiomecanica: Mutacion
    lateinit var bioalteracionGenetica: Mutacion


    @BeforeEach
    fun crearModelo() {

        dataService = DataServiceImpl(HibernateDataDAO())
        colera = Patogeno("Colera", 90, 5, 1, 30, 45)
        viruela = Patogeno("Viruela", 90, 80, 15, 15, 35)
        rabia = Patogeno("Rabia",1,1,1,35,10)
        corea = UbicacionJpa("Corea")
        japon = UbicacionJpa("Japon")
        tailandia = UbicacionJpa("Tailandia")
        indonesia = UbicacionJpa("Indonesia")
        china = UbicacionJpa("China")
        john = Vector("John", corea, TipoVector.HUMANO)
        viktor = Vector("Viktor", japon, TipoVector.HUMANO)
        monoAndroide = Vector("Mono-17", china, TipoVector.ANIMAL)
        supresionBiomecanica = SupresionBiomecanica(35)
        bioalteracionGenetica = BioalteracionGenetica(TipoVector.ANIMAL)

        servicioUbicacion.crear(corea)
        servicioUbicacion.crear(indonesia)
        servicioUbicacion.crear(japon)
        servicioUbicacion.crear(tailandia)
        servicioUbicacion.crear(china)
        servicioPatogeno.crear(viruela)
        servicioPatogeno.crear(colera)
        servicioPatogeno.crear(rabia)
        servicioVector.crear(john)
        servicioVector.crear(viktor)
        servicioVector.crear(monoAndroide)

        mecaViruela = servicioPatogeno.agregarEspecie(viruela.getId(), "Meca-Viruela", corea.getId()!!)
        cromaColera = servicioPatogeno.agregarEspecie(colera.getId(), "Croma Colera", japon.getId()!!)
        roboRabia = servicioPatogeno.agregarEspecie(rabia.getId(), "Robo Rabia", china.getId()!!)

        servicioUbicacion.conectar(corea.getNombre()!!, japon.getNombre()!!, "Terrestre")
        servicioUbicacion.conectar(japon.getNombre()!!, china.getNombre()!!, "Terrestre")
        servicioUbicacion.conectar(china.getNombre()!!, japon.getNombre()!!, "Terrestre")
        servicioUbicacion.conectar(japon.getNombre()!!, tailandia.getNombre()!!, "Terrestre")
        servicioUbicacion.conectar(indonesia.getNombre()!!, japon.getNombre()!!, "Aereo")
        servicioUbicacion.conectar(japon.getNombre()!!, corea.getNombre()!!, "Terrestre")
        servicioUbicacion.conectar(corea.getNombre()!!, china.getNombre()!!, "Terrestre")

        random = RandomGenerator.getInstance()
        random.setStrategy(NoAleatorioStrategy())
        random.setBooleanoGlobal(true)
        random.setBooleanoAltGlobal(true)
        random.setNumeroGlobal(1)

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

        val mecaViruelaConMutacion = servicioEspecie.recuperar(mecaViruela.getId()!!)

        Assertions.assertEquals(1, supresionBiomecanica.getId()!!)
        Assertions.assertEquals(1, mecaViruelaConMutacion.cantidadDeMutaciones())
        Assertions.assertTrue(mecaViruelaConMutacion.tieneLaMutacion(supresionBiomecanica))

    }

    @Test
    fun unaEspeciePuedeTenerVariasMutacionesPosibles() {

        servicioMutacion.agregarMutacion(roboRabia.getId()!!, supresionBiomecanica)
        servicioMutacion.agregarMutacion(roboRabia.getId()!!, bioalteracionGenetica)

        val roboRabiaConMutacion = servicioEspecie.recuperar(roboRabia.getId()!!)

        Assertions.assertEquals(2, roboRabiaConMutacion.cantidadDeMutaciones())
        Assertions.assertTrue(roboRabiaConMutacion.tieneLaMutacion(supresionBiomecanica))
        Assertions.assertTrue(roboRabiaConMutacion.tieneLaMutacion(bioalteracionGenetica))

    }

    @Test
    fun unVectorNoMutaSinoLograContagiarAOtroVector() {

        random.setBooleanoGlobal(false)

        servicioMutacion.agregarMutacion(mecaViruela.getId()!!, bioalteracionGenetica)

        servicioUbicacion.mover(john.getId(),japon.getId()!!)

        val mecaViruelaConMutacion = servicioEspecie.recuperar(mecaViruela.getId()!!)
        val johnNoMutado = servicioVector.recuperar(john.getId())
        val viktorNoContagiado = servicioVector.recuperar(viktor.getId())

        Assertions.assertTrue(mecaViruelaConMutacion.tieneLaMutacion(bioalteracionGenetica))
        Assertions.assertFalse(viktorNoContagiado.estaInfectadoCon(mecaViruelaConMutacion))
        Assertions.assertFalse(johnNoMutado.estaMutado())
    }

    @Test
    fun unVectorNoMutaConExitoLuegoDeContagiarAOtroVector() {
        random.setBooleanoAltGlobal(false)

        servicioVector.infectar(john.getId(), mecaViruela.getId()!!)

        servicioMutacion.agregarMutacion(mecaViruela.getId()!!, bioalteracionGenetica)

        val mecaViruelaConMutacion = servicioEspecie.recuperar(mecaViruela.getId()!!)

        servicioUbicacion.mover(john.getId(),japon.getId()!!)

        val johnNoMutado = servicioVector.recuperar(john.getId())
        val viktorContagiado = servicioVector.recuperar(viktor.getId())

        Assertions.assertTrue(mecaViruelaConMutacion.tieneLaMutacion(bioalteracionGenetica))
        Assertions.assertTrue(viktorContagiado.estaInfectadoCon(mecaViruela))
        Assertions.assertFalse(johnNoMutado.estaMutado())

    }

    @Test
    fun unVectorMutaConExitoLuegoDeContagiarAOtroVector() {

        servicioMutacion.agregarMutacion(mecaViruela.getId()!!, bioalteracionGenetica)
        servicioUbicacion.mover(john.getId(),japon.getId()!!)

        val johnMutado = servicioVector.recuperar(john.getId())
        val viktorContagiado = servicioVector.recuperar(viktor.getId())

        Assertions.assertTrue(viktorContagiado.estaInfectadoCon(mecaViruela))
        Assertions.assertTrue(johnMutado.estaMutado())
        Assertions.assertTrue(johnMutado.estaMutadoCon(bioalteracionGenetica))

    }

    @Test
    fun unVectorHumanoMutadoConBioalteracionGeneticaHabilitaAContagiarAUnTipoDeVectorAnimal() {

        servicioMutacion.agregarMutacion(mecaViruela.getId()!!, bioalteracionGenetica)
        servicioUbicacion.mover(john.getId(),japon.getId()!!)

        val johnMutado = servicioVector.recuperar(john.getId())
        val viktorContagiado = servicioVector.recuperar(viktor.getId())

        Assertions.assertTrue(viktorContagiado.estaInfectadoCon(mecaViruela))
        Assertions.assertFalse(monoAndroide.estaInfectadoCon(mecaViruela))
        Assertions.assertTrue(johnMutado.estaMutado())
        Assertions.assertTrue(johnMutado.estaMutadoCon(bioalteracionGenetica))

        servicioUbicacion.mover(john.getId(),china.getId()!!)

        val monoAndroideContagiado = servicioVector.recuperar(monoAndroide.getId())

        Assertions.assertTrue(monoAndroideContagiado.estaInfectadoCon(mecaViruela))

    }

    @Test
    fun unVectorAnimalMutadoConBioalteracionGeneticaHabilitaAContagiarAUnTipoDeVectorAnimal() {

        val gatoAndroide = servicioVector.crear(Vector("Stray", tailandia, TipoVector.ANIMAL))

        servicioMutacion.agregarMutacion(roboRabia.getId()!!, bioalteracionGenetica)
        servicioUbicacion.mover(monoAndroide.getId(), japon.getId()!!)

        val monoMutado = servicioVector.recuperar(monoAndroide.getId())
        val viktorContagiado = servicioVector.recuperar(viktor.getId())

        Assertions.assertTrue(viktorContagiado.estaInfectadoCon(roboRabia))
        Assertions.assertFalse(gatoAndroide.estaInfectadoCon(roboRabia))
        Assertions.assertTrue(monoMutado.estaMutado())
        Assertions.assertTrue(monoMutado.estaMutadoCon(bioalteracionGenetica))

        servicioUbicacion.mover(monoAndroide.getId(), tailandia.getId()!!)

        val gatoAndroideContagiado = servicioVector.recuperar(gatoAndroide.getId())

        Assertions.assertTrue(gatoAndroideContagiado.estaInfectadoCon(roboRabia))

    }

    @Test
    fun unVectorInsectoMutadoConBioalteracionGeneticaHabilitaAContagiarAUnTipoDeVectorInsecto() {

        val turboGrillo = servicioVector.crear(Vector("Pepe", tailandia, TipoVector.INSECTO))
        val bioMosquito = servicioVector.crear(Vector("Raul", indonesia, TipoVector.INSECTO))
        val bioalteracionGenetica2 = BioalteracionGenetica(TipoVector.INSECTO)
        servicioVector.infectar(bioMosquito.getId(), roboRabia.getId()!!)

        servicioMutacion.agregarMutacion(roboRabia.getId()!!, bioalteracionGenetica2)
        servicioUbicacion.mover(bioMosquito.getId(), japon.getId()!!)

        val bioMosquitoMutado = servicioVector.recuperar(bioMosquito.getId())
        val viktorContagiado = servicioVector.recuperar(viktor.getId())

        Assertions.assertTrue(viktorContagiado.estaInfectadoCon(roboRabia))
        Assertions.assertFalse(turboGrillo.estaInfectadoCon(roboRabia))
        Assertions.assertTrue(bioMosquitoMutado.estaInfectadoCon(roboRabia))
        Assertions.assertTrue(bioMosquitoMutado.estaMutadoCon(bioalteracionGenetica2))

        servicioUbicacion.mover(bioMosquito.getId(), tailandia.getId()!!)

        val turboGrilloContagiado = servicioVector.recuperar(turboGrillo.getId())

        Assertions.assertTrue(turboGrilloContagiado.estaInfectadoCon(roboRabia))

    }

    @Test
    fun cuandoUnVectorMutaConSupresionBiomecanicaElimanaLasEspeciesConBajaDefensa() {

        servicioVector.infectar(john.getId(), roboRabia.getId()!!)
        servicioVector.infectar(john.getId(), cromaColera.getId()!!)

        val johnContagiado = servicioVector.recuperar(john.getId())

        Assertions.assertTrue(johnContagiado.estaInfectadoCon(mecaViruela))
        Assertions.assertTrue(johnContagiado.estaInfectadoCon(roboRabia))
        Assertions.assertTrue(johnContagiado.estaInfectadoCon(cromaColera))
        Assertions.assertEquals(3, johnContagiado.enfermedadesDelVector().size)

        servicioMutacion.agregarMutacion(mecaViruela.getId()!!, supresionBiomecanica)
        servicioUbicacion.mover(john.getId(),japon.getId()!!)

        val johnMutado = servicioVector.recuperar(john.getId())

        Assertions.assertTrue(johnMutado.estaMutado())
        Assertions.assertTrue(johnMutado.estaMutadoCon(supresionBiomecanica))
        Assertions.assertEquals(2, johnMutado.enfermedadesDelVector().size)
        Assertions.assertTrue(johnMutado.estaInfectadoCon(mecaViruela))
        Assertions.assertTrue(johnMutado.estaInfectadoCon(roboRabia))

    }

    @Test
    fun unVectorMutadoConSupresionBiomecanicaEliminaLasEspeciesDeBajaDefensaPeroNoLasMutacionesQueProvienenDeLasEspecies() {

        servicioMutacion.agregarMutacion(cromaColera.getId()!!, bioalteracionGenetica)
        servicioUbicacion.mover(viktor.getId(),corea.getId()!!)

        val viktorMutado = servicioVector.recuperar(viktor.getId())
        val johnContagiado = servicioVector.recuperar(john.getId())

        Assertions.assertTrue(johnContagiado.estaInfectadoCon(cromaColera))
        Assertions.assertTrue(viktorMutado.estaMutado())
        Assertions.assertTrue(viktorMutado.estaMutadoCon(bioalteracionGenetica))

        servicioMutacion.agregarMutacion(mecaViruela.getId()!!, supresionBiomecanica)
        servicioVector.infectar(viktor.getId(), mecaViruela.getId()!!)
        servicioUbicacion.mover(viktor.getId(), china.getId()!!)

        val viktorContagiado = servicioVector.recuperar(viktor.getId())
        val monoContagiado = servicioVector.recuperar(monoAndroide.getId())

        Assertions.assertFalse(viktorContagiado.estaInfectadoCon(cromaColera))
        Assertions.assertFalse(monoContagiado.estaInfectadoCon(cromaColera))
        Assertions.assertTrue(viktorContagiado.estaMutado())
        Assertions.assertTrue(viktorContagiado.estaMutadoCon(bioalteracionGenetica))
        Assertions.assertTrue(viktorContagiado.estaMutadoCon(supresionBiomecanica))

    }

    @Test
    fun unVectorMutadoConBioalteracionGeneticaIntentaContagiarAOtroVectorConUnaEspecieQueNoLoHizoMutar() {

        servicioMutacion.agregarMutacion(cromaColera.getId()!!, bioalteracionGenetica)
        servicioUbicacion.mover(viktor.getId(),corea.getId()!!)

        val johnContagiado = servicioVector.recuperar(john.getId())

        Assertions.assertTrue(johnContagiado.estaInfectadoCon(cromaColera))

        servicioVector.infectar(viktor.getId(), mecaViruela.getId()!!)
        servicioUbicacion.mover(viktor.getId(), china.getId()!!)

        val viktorContagiado = servicioVector.recuperar(viktor.getId())
        val monoContagiado = servicioVector.recuperar(monoAndroide.getId())

        Assertions.assertTrue(monoContagiado.estaInfectadoCon(cromaColera))
        Assertions.assertFalse(monoContagiado.estaInfectadoCon(mecaViruela))
        Assertions.assertTrue(viktorContagiado.estaMutado())
        Assertions.assertTrue(viktorContagiado.estaMutadoCon(bioalteracionGenetica))

    }

    @Test
    fun cuandoUnVectorMutaConSupresionBiomecanicaNoPuedeSerInfectadoConEspeciesDeBajaDefensa() {

        servicioMutacion.agregarMutacion(mecaViruela.getId()!!, supresionBiomecanica)
        servicioUbicacion.mover(john.getId(),japon.getId()!!)

        val johnMutado = servicioVector.recuperar(john.getId())

        Assertions.assertTrue(johnMutado.estaMutado())
        Assertions.assertTrue(johnMutado.estaMutadoCon(supresionBiomecanica))

        servicioVector.infectar(john.getId(),cromaColera.getId()!!)

        Assertions.assertFalse(john.estaInfectadoCon(cromaColera))

    }

    @AfterEach
    fun tearDown() {
       dataService.cleanAll()
       servicioUbicacion.deleteAll()
    }


}