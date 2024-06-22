package ar.edu.unq.eperdemic.testServicios

import ar.edu.unq.eperdemic.exceptions.ErrorDeMovimiento
import ar.edu.unq.eperdemic.exceptions.ErrorUbicacionMuyLejana
import ar.edu.unq.eperdemic.exceptions.ErrorYaExisteLaEntidad
import ar.edu.unq.eperdemic.exceptions.NoExisteLaUbicacion
import ar.edu.unq.eperdemic.helper.dao.HibernateDataDAO
import ar.edu.unq.eperdemic.helper.service.DataService
import ar.edu.unq.eperdemic.helper.service.DataServiceImpl
import ar.edu.unq.eperdemic.modelo.*
import ar.edu.unq.eperdemic.modelo.vector.Vector
import ar.edu.unq.eperdemic.modelo.RandomGenerator.NoAleatorioStrategy
import ar.edu.unq.eperdemic.modelo.RandomGenerator.RandomGenerator
import ar.edu.unq.eperdemic.modelo.ubicacion.UbicacionGlobal
import ar.edu.unq.eperdemic.modelo.vector.TipoVector
import ar.edu.unq.eperdemic.persistencia.dao.UbicacionMongoDAO
import ar.edu.unq.eperdemic.persistencia.dao.UbicacionNeo4jDAO
import ar.edu.unq.eperdemic.services.PatogenoService
import ar.edu.unq.eperdemic.services.UbicacionService
import ar.edu.unq.eperdemic.services.VectorService
import org.junit.jupiter.api.*
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.test.context.junit.jupiter.SpringExtension


@ExtendWith(SpringExtension::class)
@SpringBootTest
@TestInstance(PER_CLASS)
class UbicacionServiceJpaTest {

    @Autowired lateinit var serviceUbicacion: UbicacionService
    @Autowired lateinit var serviceVector: VectorService
    @Autowired lateinit var servicePatogeno: PatogenoService
    @Autowired private lateinit var ubicacionNeo4jDAO: UbicacionNeo4jDAO
    @Autowired private lateinit var ubicacionMongoDBDAO: UbicacionMongoDAO


    lateinit var ubi1: UbicacionGlobal
    lateinit var ubi2: UbicacionGlobal
    lateinit var ubi3: UbicacionGlobal

    lateinit var ubiPersistida1: UbicacionGlobal

    lateinit var vector1: Vector
    lateinit var vector2: Vector
    lateinit var vector3: Vector

    lateinit var patogeno1: Patogeno

    lateinit var especie1: Especie
    lateinit var especie2: Especie

    lateinit var random: RandomGenerator

    lateinit var dataService: DataService

    lateinit var coordenada1: GeoJsonPoint
    lateinit var coordenada2: GeoJsonPoint
    lateinit var coordenada3: GeoJsonPoint

    @BeforeEach
    fun crearModelo() {

        dataService = DataServiceImpl(HibernateDataDAO())

        coordenada1 = GeoJsonPoint(45.00, 40.00)
        coordenada2 = GeoJsonPoint(46.00, 40.00)
        coordenada3 = GeoJsonPoint(47.00, 40.00)

        ubi1 = serviceUbicacion.crear(UbicacionGlobal("Argentina", coordenada1))
        ubi2 = serviceUbicacion.crear(UbicacionGlobal("Paraguay", coordenada2))
        ubi3 = serviceUbicacion.crear(UbicacionGlobal("Chile", coordenada3))

        vector1 = serviceVector.crear(Vector("Jose", ubi2.aJPA(), TipoVector.HUMANO))
        vector2 = serviceVector.crear(Vector("araña", ubi2.aJPA(), TipoVector.INSECTO))
        vector3 = serviceVector.crear(Vector("perrito", ubi1.aJPA(), TipoVector.ANIMAL))

        patogeno1 = servicePatogeno.crear(Patogeno("Bacteria", 100, 100, 100, 30, 66))
        especie1 = servicePatogeno.agregarEspecie(patogeno1.getId(), "juanito", ubi2.getId())
        especie2 = servicePatogeno.agregarEspecie(patogeno1.getId(), "corona2", ubi2.getId())

        random = RandomGenerator.getInstance()
        random.setStrategy(NoAleatorioStrategy())
        random.setNumeroGlobal(1)

    }

    @Test
    fun alGuardarYLuegoRecuperarSeObtieneObjetosSimilares() {

        val coordenada = GeoJsonPoint(35.00, 30.00)
        ubi3 = serviceUbicacion.crear(UbicacionGlobal("Uruguay", coordenada))

        ubiPersistida1 = serviceUbicacion.recuperar(ubi3.getId())

        Assertions.assertEquals(ubiPersistida1.getNombre(), "Uruguay")
    }

    @Test
    fun errorCuandoSeIntentaRecuperarUnaUbicacionConUnIdQueNoExiste() {

        Assertions.assertThrows(NoExisteLaUbicacion::class.java){
            serviceUbicacion.recuperar(50)
        }

    }

    @Test
    fun cuandoSeUpdateaUnaUbicacionValidaSeActualizaLaBaseDeDatos() {
        ubi1.setNombre("Estados Unidos")
        serviceUbicacion.updatear(ubi1)

        val ubi1Actualizada = serviceUbicacion.recuperar(ubi1.getId())

        Assertions.assertFalse(
                ubi1Actualizada.getNombre() == "Argentina"
        )

        Assertions.assertEquals(ubi1Actualizada.getNombre(), "Estados Unidos")
    }

    @Test
    fun alObtenerTodasLasUbicacionesLaListaTieneTresElementos() {

        val todasLasUbicaiones = serviceUbicacion.recuperarTodos()

        Assertions.assertEquals(todasLasUbicaiones.size, 3)

    }

    @Test
    fun cuandoSeEnviaElMensajeExpandirSiHayVectorInfectadoLaInfeccionDeEsteVectorSeExpandePorTodaLaUbicacion() {
        random.setNumeroGlobal(2)
        serviceVector.crear(Vector("Miguel", ubi1.aJPA(), TipoVector.HUMANO))
        serviceVector.crear(Vector("Mariano", ubi1.aJPA(), TipoVector.HUMANO))
        val vector4 = serviceVector.crear(Vector("Juan", ubi1.aJPA(), TipoVector.INSECTO))

        serviceVector.infectar(vector3.getId(), especie1.getId()!!)
        serviceVector.infectar(vector4.getId(), especie2.getId()!!)
        serviceUbicacion.expandir(ubi1.getId())

        val vectoresUbicacion = serviceVector.recuperarTodos().filter { v -> v.ubicacion!!.getId() == ubi1.getId() }

        Assertions.assertTrue(
            vectoresUbicacion.all {
                   it.enfermedadesDelVector().any { it.getId() == especie2.getId()!! }
            }
        )
    }

    @Test
    fun cuandoSeEnviaElMensajeExpandirSiNoHayUnVenctorInfectadoEnLaUbicacionNoHayCambios() {
        random.setNumeroGlobal(1)
        serviceUbicacion.expandir(ubi1.getId())

        val vectoresUbicacion = serviceVector.recuperarTodos().filter { v -> v.ubicacion!!.getId() == ubi3.getId() }

        for (v in vectoresUbicacion) {
            Assertions.assertFalse( v.estaInfectado() )
        }
    }

    @Test
    fun errorCuandoSeIntentaMoverUnVectorAUnaUbicacionIdQueNoExiste(){
        Assertions.assertThrows(ErrorDeMovimiento::class.java) {
            serviceUbicacion.mover(vector1.getId(), 50)
        }
    }

    @Test
    fun errorCuandoSeIntentaMoverUnVectorIdQueNoExisteAUnaUbicacion(){
        Assertions.assertThrows(ErrorDeMovimiento::class.java) {
            serviceUbicacion.mover(35, ubi1.getId())
        }
    }

    @Test
    fun errorCuandoSeIntentaCrearDosUbicacionesConElMismoNombre(){

        val ubicacion = UbicacionGlobal("Argentina", coordenada1)

        Assertions.assertThrows(ErrorYaExisteLaEntidad::class.java){
            serviceUbicacion.crear(ubicacion)
        }
    }

    @Test
    fun seTrataDeUpdatearUnaUbicacionConNombreYaEnUso() {

        val coordenada = GeoJsonPoint(35.00, 30.00)
        ubi3 = serviceUbicacion.crear(UbicacionGlobal("Uruguay", coordenada))
        ubiPersistida1 = serviceUbicacion.recuperar(ubi3.getId())

        serviceUbicacion.crear(UbicacionGlobal("Peru", GeoJsonPoint(25.00, 30.00)))
        ubiPersistida1.setNombre("Peru")

        val ubicacionRecuperada = serviceUbicacion.recuperar(ubiPersistida1.getId())

        assertThrows<ErrorYaExisteLaEntidad> { serviceUbicacion.updatear(ubicacionRecuperada) }
    }

    @AfterEach
    fun borrarRegistros() {
        serviceUbicacion.deleteAll()
        dataService.cleanAll()
        ubicacionNeo4jDAO.detachDelete()
        ubicacionMongoDBDAO.deleteAll()
    }

}