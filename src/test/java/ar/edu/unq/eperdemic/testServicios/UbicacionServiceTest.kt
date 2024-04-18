package ar.edu.unq.eperdemic.testServicios

import ar.edu.unq.eperdemic.helper.dao.HibernateDataDAO
import ar.edu.unq.eperdemic.modelo.Ubicacion
import ar.edu.unq.eperdemic.modelo.vector.Vector

import ar.edu.unq.eperdemic.persistencia.dao.hibernate.HibernateVectorDAO
import ar.edu.unq.eperdemic.persistencia.dao.hibernate.HibernateUbicacionDAO

import ar.edu.unq.eperdemic.helper.service.DataService
import ar.edu.unq.eperdemic.helper.service.DataServiceImpl
import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.Patogeno
import ar.edu.unq.eperdemic.modelo.vector.TipoVector
import ar.edu.unq.eperdemic.persistencia.dao.hibernate.HibernateEspecieDAO
import ar.edu.unq.eperdemic.persistencia.dao.hibernate.HibernatePatogenoDAO
import ar.edu.unq.eperdemic.services.EspecieService
import ar.edu.unq.eperdemic.services.PatogenoService

import ar.edu.unq.eperdemic.services.UbicacionService
import ar.edu.unq.eperdemic.services.VectorService
import ar.edu.unq.eperdemic.services.impl.EspecieServiceImpl
import ar.edu.unq.eperdemic.services.impl.PatogenoServiceImpl
import ar.edu.unq.eperdemic.services.impl.UbicacionServiceImp
import ar.edu.unq.eperdemic.services.impl.VectorServiceImp

import org.junit.jupiter.api.*
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS

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

    var serviceEspecie: EspecieService = EspecieServiceImpl(HibernateEspecieDAO())

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


    @BeforeEach
    fun crearModelo() {

        ubi1 = serviceUbicacion.crear(Ubicacion("Argentina"))
        ubi2 = serviceUbicacion.crear(Ubicacion("paraguay"))
        ubi4 = serviceUbicacion.crear(Ubicacion("Chiles"))

        vector1 = serviceVector.crear(Vector("Jose", ubi2, TipoVector.HUMANO))
        vector2 = serviceVector.crear(Vector("araña", ubi2, TipoVector.INSECTO))
        vector3 = serviceVector.crear(Vector("perrito", ubi1, TipoVector.ANIMAL))


        patogeno1 = servicePatogeno.crear(Patogeno())
<<<<<<< HEAD
        especie1 = servicePatogeno.agregarEspecie(patogeno1.id!!, "juanito", ubi4.getNombre()!!)
        especie2 = servicePatogeno.agregarEspecie(patogeno1.id!!, "corona2", ubi4.getNombre()!!)
=======
        especie1 = servicePatogeno.agregarEspecie(patogeno1.id!!, "juanito", ubi4.id!!)
        especie2 = servicePatogeno.agregarEspecie(patogeno1.id!!, "corona2", ubi4.id!!)
>>>>>>> feature/VectorService
    }

    @Test
    fun alGuardarYLuegoRecuperarSeObtieneObjetosSimilares() {
        ubi3 = serviceUbicacion.crear(Ubicacion("Uruguay"))

        ubiPersistida1 = serviceUbicacion.recuperar(4)

        Assertions.assertEquals(ubiPersistida1.getNombre(), "Uruguay")
    }

    @Test
    fun noEsPosibleCrearDosUbicacionesConElMismoNombre() {
        //assertThrows<SQLIntegrityConstraintViolationException> {
        //    serviceUbicacion.crear(Ubicacion("Argentina"))
        //}
    }

    @Test
    fun cuandoSeIntentaRecuperarUnVectorConUnIdQueNoExisteDaNull() {
        Assertions.assertEquals(serviceUbicacion.recuperar(50), null)
    }

    @Test
    fun cuandoSeUpdateaUnaUbicacionValidaSeActualizaLaBaseDeDatos() {
        ubi1.setNombre("Estados Unidos")
        serviceUbicacion.updatear(ubi1)

        // Volvemos a obtener la entidad de la base de datos
        val ubi1Actualizada = serviceUbicacion.recuperar(ubi1.getId()!!)

        // El nombre del objeto ya no es el mismo con el que inicializo
        Assertions.assertFalse(
                ubi1Actualizada.getNombre() == "Argentina"
        )

        // El nombre cambio a Estados Unidos
        Assertions.assertEquals(ubi1Actualizada.getNombre(), "Estados Unidos")
    }

    @Test
    fun alObtenerTodasLasUbicacionesLaListaTieneTresElementos() {

        val todasLasUbicaiones = serviceUbicacion.recuperarTodos()

        // Creamos unalista con todos los nombres de ubicaciones que creamos
        //      en la base de datos
        val nombres = setOf("Argentina", "Paraguay", "Chiles")

        // Comprobamos que el tamaño de la lista sea el correcto
        Assertions.assertEquals(todasLasUbicaiones.size, 3)

        // Comprobamos que los nombres de las ubcaciones sean los de la lista
        for (u in todasLasUbicaiones) {
            nombres.any { it == u.getNombre() }
        }
    }

    @Test
    fun cuandoUnVectorCambiaSeMueveCambiaDeUbicacion() {
        serviceUbicacion.mover(vector1.getId()!!, ubi2.getId()!!)

        val vectorTemporal = serviceVector.recuperar(vector1.getId()!!)
        val ubicacionNueva = vectorTemporal.ubicacion!!

        // Comprobamos que la ubicacion del vector fue actualizada
        Assertions.assertEquals(ubicacionNueva.getNombre(), ubi2.getNombre())
    }

    @Test
    fun cuandoUnVectorCambiaDeUbicacionSiEstaInfectadoInfectaALosVectoresDeLANuevaUbicacion() {

        serviceUbicacion.mover(vector1.getId()!!, ubi2.getId()!!)

        serviceVector.infectar(vector1.getId()!!, especie1.id!!)
        serviceVector.infectar(vector1.getId()!!, especie2.id!!)


        // Obtenemos los vectores que esten en la nueva ubicacion
        val vectoresDeNuevaUbicacion = serviceVector.recuperarTodos().filter { v -> v.ubicacion!!.getId() == ubi2.getId() }

        Assertions.assertTrue(
            vectoresDeNuevaUbicacion.all {
                v -> v.enfermedadesDelVector().containsAll(vector1.enfermedadesDelVector())
            }
        )
    }

    @Test
    fun cuandoSeEnviaElMensajeExpandirSiHayVectorInfectadoLaInfeccionDeEsteVectorSeExpandePorTodaLaUbicacion() {
        val vector4 = serviceVector.crear(Vector("Miguel", ubi2, TipoVector.HUMANO))

        // Restrinjo la prueba a un solo vector infectado
        serviceVector.infectar(vector4.getId()!!, especie1.id!!)
        serviceUbicacion.expandir(ubi2.getId()!!)

        val vectoresUbicacion = serviceVector.recuperarTodos().filter { v -> v.ubicacion!!.getId() == ubi2.getId() }

        Assertions.assertTrue(
            vectoresUbicacion.all {
                   it.enfermedadesDelVector().any { it.id == especie1.id!! }
            }
        )
    }

    @Test
    fun cuandoSeEnviaElMensajeExpandirSiNoHayUnVenctorInfectadoEnLaUbicacionNoHayCambios() {
        val vector4 = serviceVector.crear(Vector("Miguel", ubi2, TipoVector.HUMANO))

        serviceUbicacion.expandir(ubi2.getId()!!)

        val vectoresUbicacion = serviceVector.recuperarTodos().filter { v -> v.ubicacion!!.getId() == ubi2.getId() }

        for (v in vectoresUbicacion) {
            Assertions.assertFalse( v.estaInfectado() )
        }
    }


    @AfterEach
    fun finalizar() {
        dataService.cleanAll()
    }

}
