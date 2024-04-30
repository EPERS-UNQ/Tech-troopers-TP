/*package ar.edu.unq.eperdemic.testServicios

import ar.edu.unq.eperdemic.exceptions.ErrorValorDePaginacionIvalido
import ar.edu.unq.eperdemic.helper.dao.HibernateDataDAO
import ar.edu.unq.eperdemic.helper.service.DataService
import ar.edu.unq.eperdemic.helper.service.DataServiceImpl
import ar.edu.unq.eperdemic.modelo.*
import ar.edu.unq.eperdemic.modelo.RandomGenerator.NoAleatorioStrategy
import ar.edu.unq.eperdemic.modelo.RandomGenerator.RandomGenerator
import ar.edu.unq.eperdemic.modelo.vector.TipoVector
import ar.edu.unq.eperdemic.modelo.vector.Vector
import ar.edu.unq.eperdemic.persistencia.dao.hibernate.*
import ar.edu.unq.eperdemic.services.*
import ar.edu.unq.eperdemic.services.impl.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.*
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS

@TestInstance(PER_CLASS)
class EstadisticaServiceTest {

    lateinit var service    : EstadisticaService
    lateinit var dataService: DataService
    lateinit var serviceVector   : VectorService
    lateinit var serviceUbicacion: UbicacionService
    lateinit var servicePatogeno : PatogenoService

    lateinit var especie   : Especie
    lateinit var especie2  : Especie
    lateinit var especie3  : Especie
    lateinit var patogeno  : Patogeno

    lateinit var humano     : Vector
    lateinit var humano2    : Vector
    lateinit var humano3    : Vector
    lateinit var golondrina : Vector
    lateinit var insecto    : Vector
    lateinit var insecto2   : Vector

    lateinit var ubicacion: Ubicacion

    lateinit var random : RandomGenerator

    @BeforeEach
    fun crearModelo() {

        service     = EstadisticaServiceImpl( HibernateEstadisticaDAO() )
        dataService = DataServiceImpl( HibernateDataDAO() )
        servicePatogeno  = PatogenoServiceImpl(HibernatePatogenoDAO(), HibernateEspecieDAO(), HibernateUbicacionDAO(), HibernateVectorDAO())
        serviceVector    = VectorServiceImp( HibernateVectorDAO(), HibernateEspecieDAO() )
        serviceUbicacion = UbicacionServiceImp( HibernateUbicacionDAO(), HibernateVectorDAO() )

        random = RandomGenerator.getInstance()
        random.setStrategy(NoAleatorioStrategy())
        random.setNumeroGlobal(0)

        ubicacion = Ubicacion("Argentina")
        serviceUbicacion.crear(ubicacion)

        humano     = Vector("Pedro", ubicacion, TipoVector.HUMANO)
        humano2    = Vector("Juan", ubicacion, TipoVector.HUMANO)
        golondrina = Vector("Pepita", ubicacion, TipoVector.ANIMAL)

        serviceVector.crear(humano)

        patogeno  = Patogeno("Wachiturro", 90, 9, 9, 9, 67)
        servicePatogeno.crear(patogeno)

        especie  = servicePatogeno.agregarEspecie(patogeno.getId()!!, "Bacteria", ubicacion.getId()!!)

    }

    @Test
    fun testEspecieLider() {

        especie2 = servicePatogeno.agregarEspecie(patogeno.getId()!!, "Virus", ubicacion.getId()!!)

        serviceVector.crear(humano2)
        serviceVector.crear(golondrina)

        serviceVector.infectar(humano2.getId()!!, especie.getId()!!)
        serviceVector.infectar(golondrina.getId()!!, especie.getId()!!)

        Assertions.assertEquals(especie.getId(), service.especieLider().getId())
        Assertions.assertFalse(especie2.getId()!! == service.especieLider().getId())
    }

    @Test
    fun testDeLosLideres() {
        especie2 = servicePatogeno.agregarEspecie(patogeno.getId()!!, "Virus", ubicacion.getId()!!)
        especie3 = servicePatogeno.agregarEspecie(patogeno.getId()!!, "Adenovirus", ubicacion.getId()!!)
        humano3  = Vector("Bautista", ubicacion, TipoVector.HUMANO)
        insecto  = Vector("Chinche", ubicacion, TipoVector.INSECTO)
        insecto2  = Vector("Mosca", ubicacion, TipoVector.INSECTO)
        serviceVector.crear(golondrina)
        serviceVector.crear(humano2)
        serviceVector.crear(humano3)
        serviceVector.crear(insecto)
        serviceVector.crear(insecto2)

        serviceVector.infectar(humano2.getId()!!, especie.getId()!!)
        serviceVector.infectar(insecto.getId()!!, especie.getId()!!)
        serviceVector.infectar(insecto2.getId()!!, especie.getId()!!)

        serviceVector.infectar(humano3.getId()!!, especie2.getId()!!)
        serviceVector.infectar(golondrina.getId()!!, especie2.getId()!!)

        serviceVector.infectar(humano.getId()!!, especie3.getId()!!)

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

        Assertions.assertEquals(especie2.getId(), especiesLideres.first().getId())
        Assertions.assertEquals(especie.getId() , especiesLideres[1].getId())
        Assertions.assertEquals(especie3.getId() , especiesLideres[2].getId())
    }

    @Test
    fun testReporteDeContagios() {

        especie2 = servicePatogeno.agregarEspecie(patogeno.getId()!!, "Virus", ubicacion.getId()!!)
        insecto  = Vector("Chinche", ubicacion, TipoVector.INSECTO)
        insecto2  = Vector("Mosca", ubicacion, TipoVector.INSECTO)
        serviceVector.crear(insecto)
        serviceVector.crear(insecto2)
        serviceVector.crear(humano2)
        serviceVector.crear(golondrina)

        serviceVector.infectar(humano2.getId()!!, especie.getId()!!)
        serviceVector.infectar(insecto.getId()!!, especie.getId()!!)
        serviceVector.infectar(golondrina.getId()!!, especie2.getId()!!)

        val reporte : ReporteDeContagios = service.reporteDeContagios(ubicacion.getNombre()!!)

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
        // Se devuelve la única especie que tiene el patogeno sin importar si es especie lider o no.
        Assertions.assertEquals(especie.getId(), service.especieLider().getId())
    }

    @AfterEach
    fun borrarRegistros() {

        dataService.cleanAll()

    }

}

 */