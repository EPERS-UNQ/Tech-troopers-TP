package ar.edu.unq.eperdemic.testServicios

import ar.edu.unq.eperdemic.exceptions.ErrorValorDePaginacionIvalido
import ar.edu.unq.eperdemic.helper.dao.HibernateDataDAO
import ar.edu.unq.eperdemic.helper.service.DataService
import ar.edu.unq.eperdemic.helper.service.DataServiceImpl
import ar.edu.unq.eperdemic.modelo.*
import ar.edu.unq.eperdemic.modelo.RandomGenerator.NoAleatorioStrategy
import ar.edu.unq.eperdemic.modelo.RandomGenerator.RandomGenerator
import ar.edu.unq.eperdemic.modelo.ubicacion.UbicacionGlobal
import ar.edu.unq.eperdemic.modelo.vector.TipoVector
import ar.edu.unq.eperdemic.modelo.vector.Vector
import ar.edu.unq.eperdemic.modelo.vector.VectorGlobal
import ar.edu.unq.eperdemic.services.*
import org.junit.jupiter.api.Assertions
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
class EstadisticaServiceTest {

    lateinit var dataService: DataService

    @Autowired lateinit var service : EstadisticaService
    @Autowired lateinit var serviceVector : VectorService
    @Autowired lateinit var serviceUbicacion : UbicacionService
    @Autowired lateinit var servicePatogeno : PatogenoService

    lateinit var especie   : Especie
    lateinit var especie2  : Especie
    lateinit var especie3  : Especie
    lateinit var patogeno  : Patogeno

    lateinit var humano     : VectorGlobal
    lateinit var humano2    : VectorGlobal
    lateinit var humano3    : VectorGlobal
    lateinit var golondrina : VectorGlobal
    lateinit var insecto    : VectorGlobal
    lateinit var insecto2   : VectorGlobal

    lateinit var ubicacion: UbicacionGlobal
    lateinit var coordenada: GeoJsonPoint

    lateinit var random : RandomGenerator

    @BeforeEach
    fun crearModelo() {

        dataService = DataServiceImpl(HibernateDataDAO())

        random = RandomGenerator.getInstance()
        random.setStrategy(NoAleatorioStrategy())
        random.setNumeroGlobal(1)

        coordenada = GeoJsonPoint(45.00, 40.00)
        ubicacion = UbicacionGlobal("Argentina", coordenada)
        serviceUbicacion.crear(ubicacion)

        humano     = VectorGlobal("Pedro", ubicacion, TipoVector.HUMANO)
        humano2    = VectorGlobal("Juan", ubicacion,TipoVector.HUMANO)
        golondrina = VectorGlobal("Pepita", ubicacion, TipoVector.ANIMAL)

        humano = serviceVector.crear(humano)

        patogeno  = Patogeno("Wachiturro", 90, 9, 9, 9, 67)
        servicePatogeno.crear(patogeno)

        especie  = servicePatogeno.agregarEspecie(patogeno.getId(), "Bacteria", ubicacion.getId())

    }

    @Test
    fun testEspecieLider() {

        especie2 = servicePatogeno.agregarEspecie(patogeno.getId(), "Virus", ubicacion.getId())

        val humano2Persistido    = serviceVector.crear(humano2)
        val golondrinaPersistida = serviceVector.crear(golondrina)

        serviceVector.infectar(humano2Persistido.getId(), especie.getId()!!)
        serviceVector.infectar(golondrinaPersistida.getId(), especie.getId()!!)

        Assertions.assertEquals(especie.getId(), service.especieLider().getId())
        Assertions.assertFalse(especie2.getId()!! == service.especieLider().getId())
    }

    @Test
    fun testDeLosLideres() {
        especie2 = servicePatogeno.agregarEspecie(patogeno.getId(), "Virus", ubicacion.getId())
        especie3 = servicePatogeno.agregarEspecie(patogeno.getId(), "Adenovirus", ubicacion.getId())
        humano3   = VectorGlobal("Bautista", ubicacion, TipoVector.HUMANO)
        insecto   = VectorGlobal("Chinche", ubicacion, TipoVector.INSECTO)
        insecto2  = VectorGlobal("Mosca", ubicacion, TipoVector.INSECTO)
        val golondrinaPersistida = serviceVector.crear(golondrina)
        val humano2Persistido    = serviceVector.crear(humano2)
        val humano3Persistido    = serviceVector.crear(humano3)
        val insectoPersistido    = serviceVector.crear(insecto)
        val insecto2Persistido   = serviceVector.crear(insecto2)

        serviceVector.infectar(humano2Persistido.getId(), especie.getId()!!)
        serviceVector.infectar(insectoPersistido.getId(), especie.getId()!!)
        serviceVector.infectar(insecto2Persistido.getId(), especie.getId()!!)


        serviceVector.infectar(humano3Persistido.getId(), especie2.getId()!!)
        serviceVector.infectar(golondrinaPersistida.getId(), especie2.getId()!!)

        serviceVector.infectar(humano.getId(), especie3.getId()!!)

        // especie2 -> Infectó dos humanos y un animal. Es mas lider esta.
        // especie  -> Infectó dos humanos y dos insecto.
        // especie3 -> Infectó un humano.


        val especiesPagina0Descendente = service.lideres(Direccion.DESCENDENTE, 0, 3)

        Assertions.assertTrue(
            especiesPagina0Descendente.elementAt(0).nombre.equals("Virus")
        )
        Assertions.assertTrue(
            especiesPagina0Descendente.elementAt(1).nombre.equals("Bacteria")
        )
        Assertions.assertTrue(
            especiesPagina0Descendente.elementAt(2).nombre.equals("Adenovirus")
        )


        val especiesPagina0Ascendente = service.lideres(Direccion.ASCENDENTE, 0, 3)
        Assertions.assertTrue(
            especiesPagina0Ascendente.elementAt(0).nombre.equals("Adenovirus")
        )
        Assertions.assertTrue(
            especiesPagina0Ascendente.elementAt(1).nombre.equals("Bacteria")
        )
        Assertions.assertTrue(
            especiesPagina0Ascendente.elementAt(2).nombre.equals("Virus")
        )

        val especiesPagina5 = service.lideres(Direccion.ASCENDENTE, 5, 3)
        Assertions.assertTrue(especiesPagina5.isEmpty())

        val especiesLideres = service.lideres(Direccion.DESCENDENTE, 0, 3)

        Assertions.assertEquals(especie2.getId(), especiesLideres[0].getId())
        Assertions.assertEquals(especie.getId() , especiesLideres[1].getId())
        Assertions.assertEquals(especie3.getId() , especiesLideres[2].getId())
    }

    @Test
    fun testReporteDeContagios() {

        especie2 = servicePatogeno.agregarEspecie(patogeno.getId(), "Virus", ubicacion.getId())
        insecto  = VectorGlobal("Chinche", ubicacion, TipoVector.INSECTO)
        insecto2  = VectorGlobal("Mosca", ubicacion, TipoVector.INSECTO)
        val insectoPersistido    = serviceVector.crear(insecto)
        val insecto2Persistido   = serviceVector.crear(insecto2)
        val humano2Persistido    = serviceVector.crear(humano2)
        val golondrinaPersistida = serviceVector.crear(golondrina)

        serviceVector.infectar(humano2Persistido.getId(), especie.getId()!!)
        serviceVector.infectar(insectoPersistido.getId(), especie.getId()!!)
        serviceVector.infectar(golondrinaPersistida.getId(), especie2.getId()!!)

        val reporte : ReporteDeContagios = service.reporteDeContagios(ubicacion.getNombre())

        Assertions.assertEquals(5, reporte.cantidadVectores)
        Assertions.assertEquals(4, reporte.cantidadInfectados)
        Assertions.assertEquals("Bacteria", reporte.especiePrevalente)
    }

    @Test
    fun comprobacionDeErrorAlPedirUnaPaginaNegativaCuandoSeBuscanLosLideres(){
        val mensajeError = Assertions.assertThrows(ErrorValorDePaginacionIvalido::class.java) {
            service.lideres(Direccion.ASCENDENTE, -2, 2)
        }

        Assertions.assertEquals("El número de página es menor a 0 o la cantida de elementos por pagina es menor a 0.", mensajeError.message)
    }

    @Test
    fun comprobacionDeErrorAlPedirUnaCantidadDePaginasNegativaCuandoSeBuscanLosLideres(){
        Assertions.assertThrows(ErrorValorDePaginacionIvalido::class.java) {
            service.lideres(Direccion.ASCENDENTE, 1, -5)
        }
    }

    @Test
    fun cuandoSeIntentaRecuperarUnaEspecieLiderDeUnaUbicacionEnDondeNoHayEspecieLider(){
        Assertions.assertEquals(especie.getId(), service.especieLider().getId())
    }

    @Test
    fun testReporteDeContagiosDeUnaUbicacionVacia() {
        val ubicacionVacia = UbicacionGlobal("Vietnam", GeoJsonPoint(46.00, 40.00))
        val ubicacionRecuperada = serviceUbicacion.crear(ubicacionVacia)

        val reporte : ReporteDeContagios = service.reporteDeContagios(ubicacionRecuperada.getNombre())

        Assertions.assertEquals(0, reporte.cantidadVectores)
        Assertions.assertEquals(0, reporte.cantidadInfectados)
        Assertions.assertEquals("No existe una Especie prevalente.", reporte.especiePrevalente)
    }

    @Test
    fun testReporteDeContagiosDeUnaUbicacionQueNoExiste() {
        val reporte : ReporteDeContagios = service.reporteDeContagios("Noruega")

        Assertions.assertEquals(0, reporte.cantidadVectores)
        Assertions.assertEquals(0, reporte.cantidadInfectados)
        Assertions.assertEquals("No existe una Especie prevalente.", reporte.especiePrevalente)
    }

    @AfterEach
    fun borrarRegistros() {
        serviceVector.deleteAll()
        dataService.cleanAll()
    }

}
