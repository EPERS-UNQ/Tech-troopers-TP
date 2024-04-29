package ar.edu.unq.eperdemic.testServicios

import ar.edu.unq.eperdemic.exceptions.ErrorDeMovimiento
import ar.edu.unq.eperdemic.exceptions.NoExisteElVector
import ar.edu.unq.eperdemic.exceptions.NoExisteLaUbicacion
import ar.edu.unq.eperdemic.exceptions.NoHayVectorException
import ar.edu.unq.eperdemic.helper.dao.HibernateDataDAO
import ar.edu.unq.eperdemic.modelo.Ubicacion
import ar.edu.unq.eperdemic.modelo.vector.Vector

import ar.edu.unq.eperdemic.persistencia.dao.hibernate.HibernateVectorDAO
import ar.edu.unq.eperdemic.persistencia.dao.hibernate.HibernateUbicacionDAO

import ar.edu.unq.eperdemic.helper.service.DataService
import ar.edu.unq.eperdemic.helper.service.DataServiceImpl
import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.Patogeno
import ar.edu.unq.eperdemic.modelo.RandomGenerator.NoAleatorioStrategy
import ar.edu.unq.eperdemic.modelo.RandomGenerator.RandomGenerator
import ar.edu.unq.eperdemic.modelo.vector.TipoVector
import ar.edu.unq.eperdemic.persistencia.dao.hibernate.HibernateEspecieDAO
import ar.edu.unq.eperdemic.persistencia.dao.hibernate.HibernatePatogenoDAO
import ar.edu.unq.eperdemic.services.PatogenoService

import ar.edu.unq.eperdemic.services.UbicacionService
import ar.edu.unq.eperdemic.services.VectorService
import ar.edu.unq.eperdemic.services.impl.PatogenoServiceImpl
import ar.edu.unq.eperdemic.services.impl.UbicacionServiceImp
import ar.edu.unq.eperdemic.services.impl.VectorServiceImp

import org.junit.jupiter.api.*
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import javax.persistence.PersistenceException

@TestInstance(PER_CLASS)
class UbicacionServiceTest {

    var serviceUbicacion: UbicacionService = UbicacionServiceImp(
        HibernateUbicacionDAO(),
        HibernateVectorDAO()
    )
    var serviceVector: VectorService = VectorServiceImp(
            HibernateVectorDAO(),
            HibernateEspecieDAO()
    )

    var servicePatogeno: PatogenoService = PatogenoServiceImpl(
            HibernatePatogenoDAO(),
            HibernateEspecieDAO(),
            HibernateUbicacionDAO(),
            HibernateVectorDAO()
    )

    private var dataService: DataService = DataServiceImpl(HibernateDataDAO())


    lateinit var ubi1: Ubicacion
    lateinit var ubi2: Ubicacion
    lateinit var ubi3: Ubicacion
    lateinit var ubi4: Ubicacion

    lateinit var ubiPersistida1: Ubicacion

    lateinit var vector1: Vector
    lateinit var vector2: Vector
    lateinit var vector3: Vector

    lateinit var patogeno1: Patogeno

    lateinit var especie1: Especie
    lateinit var especie2: Especie

    lateinit var random: RandomGenerator

    @BeforeEach
    fun crearModelo() {

        ubi1 = serviceUbicacion.crear(Ubicacion("Argentina"))
        ubi2 = serviceUbicacion.crear(Ubicacion("paraguay"))
        ubi4 = serviceUbicacion.crear(Ubicacion("Chiles"))

        vector1 = serviceVector.crear(Vector("Jose", ubi2, TipoVector.HUMANO))
        vector2 = serviceVector.crear(Vector("araña", ubi2, TipoVector.INSECTO))
        vector3 = serviceVector.crear(Vector("perrito", ubi1, TipoVector.ANIMAL))


        patogeno1 = servicePatogeno.crear(Patogeno("Bacteria", 100, 100, 100, 30, 66))
        especie1 = servicePatogeno.agregarEspecie(patogeno1.getId()!!, "juanito", ubi2.getId()!!)
        especie2 = servicePatogeno.agregarEspecie(patogeno1.getId()!!, "corona2", ubi2.getId()!!)

        random = RandomGenerator.getInstance()
        random.setStrategy(NoAleatorioStrategy())
        random.setNumeroGlobal(0)

    }

    @Test
    fun alGuardarYLuegoRecuperarSeObtieneObjetosSimilares() {
        ubi3 = serviceUbicacion.crear(Ubicacion("Uruguay"))

        ubiPersistida1 = serviceUbicacion.recuperar(4)

        Assertions.assertEquals(ubiPersistida1.getNombre(), "Uruguay")
    }

    @Test
    fun cuandoSeIntentaRecuperarUnVectorConUnIdQueNoExisteDaNull() {
        Assertions.assertEquals(serviceUbicacion.recuperar(50), null)
    }

    @Test
    fun cuandoSeUpdateaUnaUbicacionValidaSeActualizaLaBaseDeDatos() {
        ubi1.setNombre("Estados Unidos")
        serviceUbicacion.updatear(ubi1)

        val ubi1Actualizada = serviceUbicacion.recuperar(ubi1.getId()!!)

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
    fun cuandoUnVectorCambiaSeMueveCambiaDeUbicacion() {
        random.setNumeroGlobal(1)
        serviceUbicacion.mover(vector1.getId()!!, ubi2.getId()!!)

        val vectorTemporal = serviceVector.recuperar(vector1.getId()!!)
        val ubicacionNueva = vectorTemporal.ubicacion!!

        Assertions.assertEquals(ubicacionNueva.getNombre(), ubi2.getNombre())
    }

    @Test
    fun cuandoUnVectorCambiaDeUbicacionSiEstaInfectadoInfectaALosVectoresDeLANuevaUbicacion() {
        random.setNumeroGlobal(1)
        serviceUbicacion.mover(vector1.getId()!!, ubi2.getId()!!)

        serviceVector.infectar(vector1.getId()!!, especie1.getId()!!)
        serviceVector.infectar(vector1.getId()!!, especie2.getId()!!)

        val vectoresDeNuevaUbicacion = serviceVector.recuperarTodos().filter { v -> v.ubicacion!!.getId() == ubi2.getId() }

        Assertions.assertTrue(
            vectoresDeNuevaUbicacion.all {
                v -> v.enfermedadesDelVector().containsAll(vector1.enfermedadesDelVector())
            }
        )
    }

    @Test
    fun cuandoSeEnviaElMensajeExpandirSiHayVectorInfectadoLaInfeccionDeEsteVectorSeExpandePorTodaLaUbicacion() {
        random.setNumeroGlobal(1)
        serviceVector.crear(Vector("Miguel", ubi1, TipoVector.HUMANO))
        serviceVector.crear(Vector("Mariano", ubi1, TipoVector.HUMANO))
        val vector4 = serviceVector.crear(Vector("Juan", ubi1, TipoVector.INSECTO))

        serviceVector.infectar(vector3.getId()!!, especie1.getId()!!)
        serviceVector.infectar(vector4.getId()!!, especie2.getId()!!)
        serviceUbicacion.expandir(ubi1.getId()!!)

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
        serviceUbicacion.expandir(ubi1.getId()!!)

        val vectoresUbicacion = serviceVector.recuperarTodos().filter { v -> v.ubicacion!!.getId() == ubi4.getId() }

        for (v in vectoresUbicacion) {
            Assertions.assertFalse( v.estaInfectado() )
        }
    }

    @Test
    fun cuandoSeIntentaMoverUnVectorAUnaUbicacionIdQueNoExiste(){
        Assertions.assertThrows(ErrorDeMovimiento::class.java) {
            serviceUbicacion.mover(vector1.getId()!!, 50)
        }
    }

    @Test
    fun cuandoSeIntentaMoverUnVectorIdQueNoExisteAUnaUbicacion(){
        Assertions.assertThrows(ErrorDeMovimiento::class.java) {
            serviceUbicacion.mover(35, ubi1.getId()!!)
        }
    }

    @Test
    fun testCuandoSeIntentaCrearDosUbicacionesConElMismoNombre(){

        val ubicacion = Ubicacion("Argentina")

        Assertions.assertThrows(PersistenceException::class.java){
            serviceUbicacion.crear(ubicacion)
        }
    }

    @Test
    fun testSeTrataDeRecuperarUnaUbicacionQueNoExiste() {

        Assertions.assertThrows(NoExisteLaUbicacion::class.java) {
            serviceUbicacion.recuperar(15)
        }

    }

    @AfterEach
    fun finalizar() {
        dataService.cleanAll()
    }

}