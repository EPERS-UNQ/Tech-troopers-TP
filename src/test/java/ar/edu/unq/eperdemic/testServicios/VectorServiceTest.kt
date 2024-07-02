package ar.edu.unq.eperdemic.testServicios

import ar.edu.unq.eperdemic.exceptions.NoExisteElVector
import ar.edu.unq.eperdemic.exceptions.NoExisteLaUbicacion
import ar.edu.unq.eperdemic.modelo.vector.Vector
import ar.edu.unq.eperdemic.services.VectorService
import ar.edu.unq.eperdemic.helper.dao.HibernateDataDAO
import ar.edu.unq.eperdemic.helper.service.DataService
import ar.edu.unq.eperdemic.helper.service.DataServiceImpl
import ar.edu.unq.eperdemic.modelo.*
import ar.edu.unq.eperdemic.modelo.RandomGenerator.NoAleatorioStrategy
import ar.edu.unq.eperdemic.modelo.RandomGenerator.RandomGenerator
import ar.edu.unq.eperdemic.modelo.ubicacion.UbicacionGlobal
import ar.edu.unq.eperdemic.modelo.ubicacion.UbicacionJpa
import ar.edu.unq.eperdemic.modelo.vector.TipoVector
import ar.edu.unq.eperdemic.modelo.vector.VectorGlobal
import ar.edu.unq.eperdemic.services.PatogenoService
import ar.edu.unq.eperdemic.services.UbicacionService
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
class VectorServiceTest {

    @Autowired lateinit var service: VectorService
    @Autowired lateinit var serviceUbicacion: UbicacionService
    @Autowired lateinit var servicePatogeno: PatogenoService
    lateinit var dataService: DataService

    lateinit var especie   : Especie
    lateinit var patogeno  : Patogeno

    lateinit var humano    : VectorGlobal
    lateinit var golondrina: VectorGlobal

    lateinit var humanoPersistido    : Vector
    lateinit var golondrinaPersistida: Vector

    lateinit var ubicacion: UbicacionGlobal
    lateinit var coordenada: GeoJsonPoint

    lateinit var random : RandomGenerator

    @BeforeEach
    fun crearModelo() {
        this.dataService = DataServiceImpl(HibernateDataDAO())

        coordenada = GeoJsonPoint(45.00, 40.00)
        ubicacion = UbicacionGlobal("Argentina", coordenada)

        serviceUbicacion.crear(ubicacion)

        humano     = VectorGlobal("Pedro", ubicacion, TipoVector.HUMANO)
        golondrina = VectorGlobal("Pepita", ubicacion, TipoVector.ANIMAL)

        patogeno  = Patogeno("Wachiturro", 90, 9, 9, 9, 67)

        humanoPersistido = service.crear(humano)

        servicePatogeno.crear(patogeno)

        especie = servicePatogeno.agregarEspecie(patogeno.getId(), "Bacteria", ubicacion.getId())

        random = RandomGenerator.getInstance()
        random.setStrategy(NoAleatorioStrategy())
        random.setNumeroGlobal(1)

    }


    @Test
    fun testDeCreacionDeUnVector(){
        val pepita = service.crear(golondrina)

        Assertions.assertEquals(2, pepita.getId())
    }

    @Test
    fun testDeActualizacionDeUnVector(){
        val pepita = service.crear(golondrina)

        Assertions.assertEquals("Pepita", pepita.nombre)

        golondrina.nombre = "Marta"
        service.updatear(pepita)

        Assertions.assertEquals("Marta", pepita.nombre)
    }

    @Test
    fun testDeRecuperarUnVector(){
        val otroHumano = service.recuperar(humanoPersistido.getId())

        Assertions.assertEquals(otroHumano.getId(), humanoPersistido.getId())
        Assertions.assertEquals(otroHumano.nombre, humano.nombre)
    }

    @Test
    fun testDeRecuperarTodosLosVectores(){
        val pepita = service.crear(golondrina)

        val listaDeVectores = service.recuperarTodos()

        Assertions.assertEquals(humanoPersistido.getId(), listaDeVectores.get(0).getId())
        Assertions.assertEquals(humanoPersistido.nombre, listaDeVectores.get(0).nombre)
        Assertions.assertEquals(pepita.getId(), listaDeVectores.get(1).getId())
        Assertions.assertEquals(pepita.nombre, listaDeVectores.get(1).nombre)
    }

    @Test
    fun testDeInfectarAUnVector(){

        val pepita = service.crear(golondrina)

        Assertions.assertFalse(pepita.estaInfectado())

        service.infectar(pepita.getId(), especie.getId()!!)

        val otraGolondrina = service.recuperar(pepita.getId())

        Assertions.assertTrue(otraGolondrina.estaInfectado())
        Assertions.assertEquals(otraGolondrina.enfermedadesDelVector().first().getId(), especie.getId())

    }

    @Test
    fun testDeEnfermedadesDeUnVector(){

        val pepita = service.crear(golondrina)

        service.infectar(pepita.getId(), especie.getId()!!)

        Assertions.assertFalse(service.enfermedades(pepita.getId()).isEmpty())
        Assertions.assertEquals(service.recuperar(pepita.getId()).enfermedadesDelVector().first().getId(), especie.getId())

        service.infectar(pepita.getId(), especie.getId()!!)

        Assertions.assertTrue(service.recuperar(pepita.getId()).estaInfectado())
    }

    @Test
    fun errorCuandoSeTrataDeRecuperarUnVectorQueNoExiste() {

        Assertions.assertThrows(NoExisteElVector::class.java) {
            service.recuperar(15)
        }

    }

    @Test
    fun errorCuandoSeTrataDeCrearUnVectorEnUnaUbicacionQueNoExiste() {

        Assertions.assertThrows(NoExisteLaUbicacion::class.java) {
            service.crear(VectorGlobal("Pepe", UbicacionGlobal("Belgica", GeoJsonPoint(24.00, 34.00)), TipoVector.HUMANO))
        }

    }

    @AfterEach
    fun cleanup() {
        dataService.cleanAll()
    }

}
