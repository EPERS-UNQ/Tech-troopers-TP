package ar.edu.unq.eperdemic.testServicios

import ar.edu.unq.eperdemic.exceptions.NoExisteLaEspecie
import ar.edu.unq.eperdemic.helper.dao.HibernateDataDAO
import ar.edu.unq.eperdemic.helper.service.DataService
import ar.edu.unq.eperdemic.helper.service.DataServiceImpl
import ar.edu.unq.eperdemic.modelo.*
import ar.edu.unq.eperdemic.modelo.RandomGenerator.NoAleatorioStrategy
import ar.edu.unq.eperdemic.modelo.RandomGenerator.RandomGenerator
import ar.edu.unq.eperdemic.modelo.mutacion.BioalteracionGenetica
import ar.edu.unq.eperdemic.modelo.mutacion.Mutacion
import ar.edu.unq.eperdemic.modelo.mutacion.SupresionBiomecanica
import ar.edu.unq.eperdemic.modelo.ubicacion.UbicacionGlobal
import ar.edu.unq.eperdemic.modelo.vector.TipoVector
import ar.edu.unq.eperdemic.modelo.vector.Vector
import ar.edu.unq.eperdemic.modelo.vector.VectorGlobal
import ar.edu.unq.eperdemic.services.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MutacionServiceTest {

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
    lateinit var china: UbicacionGlobal
    lateinit var corea: UbicacionGlobal
    lateinit var tailandia: UbicacionGlobal
    lateinit var japon: UbicacionGlobal
    lateinit var indonesia: UbicacionGlobal
    lateinit var coordenada1: GeoJsonPoint
    lateinit var coordenada2: GeoJsonPoint
    lateinit var coordenada3: GeoJsonPoint
    lateinit var coordenada4: GeoJsonPoint
    lateinit var coordenada5: GeoJsonPoint
    lateinit var john: VectorGlobal
    lateinit var viktor: VectorGlobal
    lateinit var monoAndroide: VectorGlobal
    lateinit var cromaColera: Especie
    lateinit var mecaViruela: Especie
    lateinit var roboRabia: Especie
    lateinit var supresionBiomecanica: Mutacion
    lateinit var bioalteracionGenetica: Mutacion

    lateinit var johnPersistido        : Vector
    lateinit var viktorPersistido      : Vector
    lateinit var monoAndroidePersistido: Vector


    @BeforeEach
    fun crearModelo() {

        dataService = DataServiceImpl(HibernateDataDAO())
        colera = Patogeno("Colera", 90, 5, 1, 30, 45)
        viruela = Patogeno("Viruela", 90, 80, 15, 15, 35)
        rabia = Patogeno("Rabia",1,1,1,35,10)
        coordenada1 = GeoJsonPoint(45.10, 40.00)
        coordenada2 = GeoJsonPoint(45.20, 40.00)
        coordenada3 = GeoJsonPoint(45.30, 40.00)
        coordenada4 = GeoJsonPoint(45.40, 40.00)
        coordenada5 = GeoJsonPoint(45.50, 40.00)
        corea = UbicacionGlobal("Corea", coordenada1)
        japon = UbicacionGlobal("Japon", coordenada2)
        tailandia = UbicacionGlobal("Tailandia", coordenada3)
        indonesia = UbicacionGlobal("Indonesia", coordenada4)
        china = UbicacionGlobal("China", coordenada5)

        servicioUbicacion.crear(corea)
        servicioUbicacion.crear(indonesia)
        servicioUbicacion.crear(japon)
        servicioUbicacion.crear(tailandia)
        servicioUbicacion.crear(china)

        john         = VectorGlobal("John", corea, TipoVector.HUMANO)
        viktor       = VectorGlobal("Viktor", japon, TipoVector.HUMANO)
        monoAndroide = VectorGlobal("Mono-17", china, TipoVector.ANIMAL)
        supresionBiomecanica  = SupresionBiomecanica(35)
        bioalteracionGenetica = BioalteracionGenetica(TipoVector.ANIMAL)

        servicioPatogeno.crear(viruela)
        servicioPatogeno.crear(colera)
        servicioPatogeno.crear(rabia)
        johnPersistido         = servicioVector.crear(john)
        viktorPersistido       = servicioVector.crear(viktor)
        monoAndroidePersistido = servicioVector.crear(monoAndroide)

        mecaViruela = servicioPatogeno.agregarEspecie(viruela.getId(), "Meca-Viruela", corea.getId())
        cromaColera = servicioPatogeno.agregarEspecie(colera.getId(), "Croma Colera", japon.getId())
        roboRabia = servicioPatogeno.agregarEspecie(rabia.getId(), "Robo Rabia", china.getId())

        servicioUbicacion.conectar(corea.getNombre(), japon.getNombre(), "Terrestre")
        servicioUbicacion.conectar(japon.getNombre(), china.getNombre(), "Terrestre")
        servicioUbicacion.conectar(china.getNombre(), japon.getNombre(), "Terrestre")
        servicioUbicacion.conectar(japon.getNombre(), tailandia.getNombre(), "Terrestre")
        servicioUbicacion.conectar(indonesia.getNombre(), japon.getNombre(), "Aereo")
        servicioUbicacion.conectar(japon.getNombre(), corea.getNombre(), "Terrestre")
        servicioUbicacion.conectar(corea.getNombre(), china.getNombre(), "Terrestre")

        random = RandomGenerator.getInstance()
        random.setStrategy(NoAleatorioStrategy())
        random.setBooleanoGlobal(true)
        random.setBooleanoAltGlobal(true)
        random.setNumeroGlobal(1)

    }

    @Test
    fun errorAlAgregarUnaNuevaMutacionAUnaEspecieQueNoExiste() {

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

        servicioUbicacion.mover(johnPersistido.getId(),japon.getId())

        val mecaViruelaConMutacion = servicioEspecie.recuperar(mecaViruela.getId()!!)
        val johnNoMutado = servicioVector.recuperar(johnPersistido.getId())
        val viktorNoContagiado = servicioVector.recuperar(viktorPersistido.getId())

        Assertions.assertTrue(mecaViruelaConMutacion.tieneLaMutacion(bioalteracionGenetica))
        Assertions.assertFalse(viktorNoContagiado.estaInfectadoCon(mecaViruelaConMutacion))
        Assertions.assertFalse(johnNoMutado.estaMutado())
    }

    @Test
    fun unVectorNoMutaConExitoLuegoDeContagiarAOtroVector() {
        random.setBooleanoAltGlobal(false)

        servicioVector.infectar(johnPersistido.getId(), mecaViruela.getId()!!)

        servicioMutacion.agregarMutacion(mecaViruela.getId()!!, bioalteracionGenetica)

        val mecaViruelaConMutacion = servicioEspecie.recuperar(mecaViruela.getId()!!)

        servicioUbicacion.mover(johnPersistido.getId(),japon.getId())

        val johnNoMutado = servicioVector.recuperar(johnPersistido.getId())
        val viktorContagiado = servicioVector.recuperar(viktorPersistido.getId())

        Assertions.assertTrue(mecaViruelaConMutacion.tieneLaMutacion(bioalteracionGenetica))
        Assertions.assertTrue(viktorContagiado.estaInfectadoCon(mecaViruela))
        Assertions.assertFalse(johnNoMutado.estaMutado())

    }

    @Test
    fun unVectorMutaConExitoLuegoDeContagiarAOtroVector() {

        servicioMutacion.agregarMutacion(mecaViruela.getId()!!, bioalteracionGenetica)
        servicioUbicacion.mover(johnPersistido.getId(),japon.getId())

        val johnMutado = servicioVector.recuperar(johnPersistido.getId())
        val viktorContagiado = servicioVector.recuperar(viktorPersistido.getId())

        Assertions.assertTrue(viktorContagiado.estaInfectadoCon(mecaViruela))
        Assertions.assertTrue(johnMutado.estaMutado())
        Assertions.assertTrue(johnMutado.estaMutadoCon(bioalteracionGenetica))

    }

    @Test
    fun unVectorHumanoMutadoConBioalteracionGeneticaHabilitaAContagiarAUnTipoDeVectorAnimal() {

        servicioMutacion.agregarMutacion(mecaViruela.getId()!!, bioalteracionGenetica)
        servicioUbicacion.mover(johnPersistido.getId(),japon.getId())

        val johnMutado = servicioVector.recuperar(johnPersistido.getId())
        val viktorContagiado = servicioVector.recuperar(viktorPersistido.getId())

        Assertions.assertTrue(viktorContagiado.estaInfectadoCon(mecaViruela))
        Assertions.assertFalse(monoAndroidePersistido.estaInfectadoCon(mecaViruela))
        Assertions.assertTrue(johnMutado.estaMutado())
        Assertions.assertTrue(johnMutado.estaMutadoCon(bioalteracionGenetica))

        servicioUbicacion.mover(johnPersistido.getId(),china.getId())

        val monoAndroideContagiado = servicioVector.recuperar(monoAndroidePersistido.getId())

        Assertions.assertTrue(monoAndroideContagiado.estaInfectadoCon(mecaViruela))

    }

    @Test
    fun unVectorAnimalMutadoConBioalteracionGeneticaHabilitaAContagiarAUnTipoDeVectorAnimal() {

        val gatoAndroide = servicioVector.crear(VectorGlobal("Stray", tailandia, TipoVector.ANIMAL))

        servicioMutacion.agregarMutacion(roboRabia.getId()!!, bioalteracionGenetica)
        servicioUbicacion.mover(monoAndroidePersistido.getId(), japon.getId())

        val monoMutado = servicioVector.recuperar(monoAndroidePersistido.getId())
        val viktorContagiado = servicioVector.recuperar(viktorPersistido.getId())

        Assertions.assertTrue(viktorContagiado.estaInfectadoCon(roboRabia))
        Assertions.assertFalse(gatoAndroide.estaInfectadoCon(roboRabia))
        Assertions.assertTrue(monoMutado.estaMutado())
        Assertions.assertTrue(monoMutado.estaMutadoCon(bioalteracionGenetica))

        servicioUbicacion.mover(monoAndroidePersistido.getId(), tailandia.getId())

        val gatoAndroideContagiado = servicioVector.recuperar(gatoAndroide.getId())

        Assertions.assertTrue(gatoAndroideContagiado.estaInfectadoCon(roboRabia))

    }

    @Test
    fun unVectorInsectoMutadoConBioalteracionGeneticaHabilitaAContagiarAUnTipoDeVectorInsecto() {

        val turboGrillo = servicioVector.crear(VectorGlobal("Pepe", tailandia, TipoVector.INSECTO))
        val bioMosquito = servicioVector.crear(VectorGlobal("Raul", indonesia, TipoVector.INSECTO))
        val bioalteracionGenetica2 = BioalteracionGenetica(TipoVector.INSECTO)
        servicioVector.infectar(bioMosquito.getId(), roboRabia.getId()!!)

        servicioMutacion.agregarMutacion(roboRabia.getId()!!, bioalteracionGenetica2)
        servicioUbicacion.mover(bioMosquito.getId(), japon.getId())

        val bioMosquitoMutado = servicioVector.recuperar(bioMosquito.getId())
        val viktorContagiado = servicioVector.recuperar(viktorPersistido.getId())

        Assertions.assertTrue(viktorContagiado.estaInfectadoCon(roboRabia))
        Assertions.assertFalse(turboGrillo.estaInfectadoCon(roboRabia))
        Assertions.assertTrue(bioMosquitoMutado.estaInfectadoCon(roboRabia))
        Assertions.assertTrue(bioMosquitoMutado.estaMutadoCon(bioalteracionGenetica2))

        servicioUbicacion.mover(bioMosquito.getId(), tailandia.getId())

        val turboGrilloContagiado = servicioVector.recuperar(turboGrillo.getId())

        Assertions.assertTrue(turboGrilloContagiado.estaInfectadoCon(roboRabia))

    }

    @Test
    fun cuandoUnVectorMutaConSupresionBiomecanicaElimanaLasEspeciesConBajaDefensa() {

        servicioVector.infectar(johnPersistido.getId(), roboRabia.getId()!!)
        servicioVector.infectar(johnPersistido.getId(), cromaColera.getId()!!)

        val johnContagiado = servicioVector.recuperar(johnPersistido.getId())

        Assertions.assertTrue(johnContagiado.estaInfectadoCon(mecaViruela))
        Assertions.assertTrue(johnContagiado.estaInfectadoCon(roboRabia))
        Assertions.assertTrue(johnContagiado.estaInfectadoCon(cromaColera))
        Assertions.assertEquals(3, johnContagiado.enfermedadesDelVector().size)

        servicioMutacion.agregarMutacion(mecaViruela.getId()!!, supresionBiomecanica)
        servicioUbicacion.mover(johnPersistido.getId(),japon.getId())

        val johnMutado = servicioVector.recuperar(johnPersistido.getId())

        Assertions.assertTrue(johnMutado.estaMutado())
        Assertions.assertTrue(johnMutado.estaMutadoCon(supresionBiomecanica))
        Assertions.assertEquals(2, johnMutado.enfermedadesDelVector().size)
        Assertions.assertTrue(johnMutado.estaInfectadoCon(mecaViruela))
        Assertions.assertTrue(johnMutado.estaInfectadoCon(roboRabia))

    }

    @Test
    fun unVectorMutadoConSupresionBiomecanicaEliminaLasEspeciesDeBajaDefensaPeroNoLasMutacionesQueProvienenDeLasEspecies() {

        servicioMutacion.agregarMutacion(cromaColera.getId()!!, bioalteracionGenetica)
        servicioUbicacion.mover(viktorPersistido.getId(),corea.getId())

        val viktorMutado   = servicioVector.recuperar(viktorPersistido.getId())
        val johnContagiado = servicioVector.recuperar(johnPersistido.getId())

        Assertions.assertTrue(johnContagiado.estaInfectadoCon(cromaColera))
        Assertions.assertTrue(viktorMutado.estaMutado())
        Assertions.assertTrue(viktorMutado.estaMutadoCon(bioalteracionGenetica))

        servicioMutacion.agregarMutacion(mecaViruela.getId()!!, supresionBiomecanica)
        servicioVector.infectar(viktorPersistido.getId(), mecaViruela.getId()!!)
        servicioUbicacion.mover(viktorPersistido.getId(), china.getId())

        val viktorContagiado = servicioVector.recuperar(viktorPersistido.getId())
        val monoContagiado = servicioVector.recuperar(monoAndroidePersistido.getId())

        Assertions.assertFalse(viktorContagiado.estaInfectadoCon(cromaColera))
        Assertions.assertFalse(monoContagiado.estaInfectadoCon(cromaColera))
        Assertions.assertTrue(viktorContagiado.estaMutado())
        Assertions.assertTrue(viktorContagiado.estaMutadoCon(bioalteracionGenetica))
        Assertions.assertTrue(viktorContagiado.estaMutadoCon(supresionBiomecanica))

    }

    @Test
    fun unVectorMutadoConBioalteracionGeneticaIntentaContagiarAOtroVectorConUnaEspecieQueNoLoHizoMutar() {

        servicioMutacion.agregarMutacion(cromaColera.getId()!!, bioalteracionGenetica)
        servicioUbicacion.mover(viktorPersistido.getId(),corea.getId())

        val johnContagiado = servicioVector.recuperar(johnPersistido.getId())

        Assertions.assertTrue(johnContagiado.estaInfectadoCon(cromaColera))

        servicioVector.infectar(viktorPersistido.getId(), mecaViruela.getId()!!)
        servicioUbicacion.mover(viktorPersistido.getId(), china.getId())

        val viktorContagiado = servicioVector.recuperar(viktorPersistido.getId())
        val monoContagiado = servicioVector.recuperar(monoAndroidePersistido.getId())

        Assertions.assertTrue(monoContagiado.estaInfectadoCon(cromaColera))
        Assertions.assertFalse(monoContagiado.estaInfectadoCon(mecaViruela))
        Assertions.assertTrue(viktorContagiado.estaMutado())
        Assertions.assertTrue(viktorContagiado.estaMutadoCon(bioalteracionGenetica))

    }

    @Test
    fun cuandoUnVectorMutaConSupresionBiomecanicaNoPuedeSerInfectadoConEspeciesDeBajaDefensa() {

        servicioMutacion.agregarMutacion(mecaViruela.getId()!!, supresionBiomecanica)
        servicioUbicacion.mover(johnPersistido.getId(),japon.getId())

        val johnMutado = servicioVector.recuperar(johnPersistido.getId())

        Assertions.assertTrue(johnMutado.estaMutado())
        Assertions.assertTrue(johnMutado.estaMutadoCon(supresionBiomecanica))

        servicioVector.infectar(johnPersistido.getId(),cromaColera.getId()!!)

        Assertions.assertFalse(johnPersistido.estaInfectadoCon(cromaColera))

    }

    @AfterEach
    fun tearDown() {
       dataService.cleanAll()
       servicioUbicacion.deleteAll()
    }


}