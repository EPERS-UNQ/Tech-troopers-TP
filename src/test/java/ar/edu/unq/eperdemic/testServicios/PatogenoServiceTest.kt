package ar.edu.unq.eperdemic.testServicios

import ar.edu.unq.eperdemic.exceptions.ErrorValorDePaginacionIvalido
import ar.edu.unq.eperdemic.exceptions.NoExisteElPatogeno
import ar.edu.unq.eperdemic.exceptions.NoHayVectorException
import ar.edu.unq.eperdemic.helper.dao.HibernateDataDAO
import ar.edu.unq.eperdemic.helper.service.DataService
import ar.edu.unq.eperdemic.helper.service.DataServiceImpl
import ar.edu.unq.eperdemic.modelo.Direccion
import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.Patogeno
import ar.edu.unq.eperdemic.modelo.RandomGenerator.NoAleatorioStrategy
import ar.edu.unq.eperdemic.modelo.RandomGenerator.RandomGenerator
import ar.edu.unq.eperdemic.modelo.Ubicacion
import ar.edu.unq.eperdemic.modelo.vector.TipoVector
import ar.edu.unq.eperdemic.services.PatogenoService
import ar.edu.unq.eperdemic.modelo.vector.Vector
import ar.edu.unq.eperdemic.services.UbicacionService
import ar.edu.unq.eperdemic.services.VectorService
import org.junit.jupiter.api.*
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import javax.persistence.PersistenceException

@ExtendWith(SpringExtension::class)
@SpringBootTest
@TestInstance(PER_CLASS)
class PatogenoServiceTest {

    @Autowired lateinit var servicioPatogeno: PatogenoService
    @Autowired lateinit var servicioVector: VectorService
    @Autowired lateinit var servicioUbicacion: UbicacionService

    lateinit var random : RandomGenerator
    lateinit var dataService: DataService

    lateinit var covid: Patogeno
    lateinit var salmonella: Patogeno
    lateinit var china: Ubicacion
    lateinit var corea: Ubicacion
    lateinit var pepe: Vector
    lateinit var pedro: Vector

    @BeforeEach
    fun crearModelo() {

        dataService = DataServiceImpl(HibernateDataDAO())
        covid = Patogeno("Coronavirus", 90, 5, 1, 60, 95)
        salmonella = Patogeno("Salmonella", 70, 10, 15, 30, 66)
        china = Ubicacion("China")
        corea = Ubicacion("Corea")
        pedro = Vector("Pedro", corea, TipoVector.HUMANO)
        pepe = Vector("Pepe", china, TipoVector.HUMANO)

        servicioUbicacion.crear(corea)
        servicioUbicacion.crear(china)

        random = RandomGenerator.getInstance()
        random.setStrategy(NoAleatorioStrategy())
        random.setNumeroGlobal(0)

    }

    @Test
    fun testCrearYRecuperarPatogeno() {

        servicioPatogeno.crear(covid)
        val patogenoRecuperado = servicioPatogeno.recuperar(covid.getId())!!

        Assertions.assertEquals("Coronavirus", patogenoRecuperado.toString())
        Assertions.assertEquals(1, patogenoRecuperado.getId())

    }

    @Test
    fun testSeTrataDeRecuperarUnPatogenoQueNoExiste() {

        Assertions.assertThrows(NoExisteElPatogeno::class.java) {
            servicioPatogeno.recuperar(15)
        }

    }

    @Test
    fun testActualizarPatogeno() {

        servicioPatogeno.crear(covid)

        val covid = servicioPatogeno.recuperar(covid.getId())!!
        covid.crearEspecie("Virus", "Corea")

        servicioPatogeno.updatear(covid)

        Assertions.assertEquals(1, covid.cantidadDeEspecies)
        Assertions.assertEquals(1, covid.getId())

    }

    @Test
    fun seRecuperanTodosLosPatogenos() {

        servicioPatogeno.crear(covid)
        servicioPatogeno.crear(salmonella)

        val patogenos = servicioPatogeno.recuperarTodos()

        Assertions.assertEquals(2, patogenos.size)
        Assertions.assertEquals("Coronavirus", patogenos[0].toString())

    }

    @Test
    fun seTrataDeRecuperarTodosLosPatogenosPeroNoHay() {

        val patogenos = servicioPatogeno.recuperarTodos()

        Assertions.assertEquals(0, patogenos.size)

    }

    @Test
    fun seAgregaUnaEspecieNueva() {

        servicioPatogeno.crear(covid)
        servicioVector.crear(pedro)

        val especie: Especie = servicioPatogeno.agregarEspecie(covid.getId(), "Virus", corea.getId())
        val cantidadDeEspecies = servicioPatogeno.recuperar(covid.getId())!!.cantidadDeEspecies

        Assertions.assertEquals(1, cantidadDeEspecies)
        Assertions.assertEquals("Coronavirus", especie.nombrePatogeno())

    }

    @Test
    fun seRecuperanTodasLasEspeciesDelPatogenoDeManeraAscendente() {

        servicioPatogeno.crear(salmonella)
        servicioVector.crear(pedro)
        servicioVector.crear(pepe)

        servicioPatogeno.agregarEspecie(salmonella.getId(), "Enterica", china.getId())
        servicioPatogeno.agregarEspecie(salmonella.getId(), "Bongori", corea.getId())
        servicioPatogeno.agregarEspecie(salmonella.getId(), "Varicela", corea.getId())
        servicioPatogeno.agregarEspecie(salmonella.getId(), "Quetzal", corea.getId())
        servicioPatogeno.agregarEspecie(salmonella.getId(), "Ahuehuete", corea.getId())

        val especiesPagina1 = servicioPatogeno.especiesDePatogeno(salmonella.getId(), Direccion.ASCENDENTE, 1, 2)
        Assertions.assertTrue(
            especiesPagina1.elementAt(0).nombre.equals("Enterica")
        )
        Assertions.assertTrue(
            especiesPagina1.elementAt(1).nombre.equals("Quetzal")
        )

        val especiesPagina5 = servicioPatogeno.especiesDePatogeno(salmonella.getId(), Direccion.ASCENDENTE, 5, 2)
        Assertions.assertTrue(especiesPagina5.isEmpty())

    }

    @Test
    fun seRecuperanTodasLasEspeciesDelPatogenoDeManeraDescendente() {

        servicioPatogeno.crear(salmonella)
        servicioVector.crear(pedro)
        servicioVector.crear(pepe)

        servicioPatogeno.agregarEspecie(salmonella.getId(), "Enterica", china.getId())
        servicioPatogeno.agregarEspecie(salmonella.getId(), "Bongori", corea.getId())
        servicioPatogeno.agregarEspecie(salmonella.getId(), "Varicela", corea.getId())
        servicioPatogeno.agregarEspecie(salmonella.getId(), "Quetzal", corea.getId())
        servicioPatogeno.agregarEspecie(salmonella.getId(), "Ahuehuete", corea.getId())

        val especiesPagina1 = servicioPatogeno.especiesDePatogeno(salmonella.getId(), Direccion.DESCENDENTE, 1, 2)
        Assertions.assertTrue(
            especiesPagina1.elementAt(0).nombre.equals("Enterica")
        )
        Assertions.assertTrue(
            especiesPagina1.elementAt(1).nombre.equals("Quetzal")
        )

        val especiesPagina5 = servicioPatogeno.especiesDePatogeno(salmonella.getId(), Direccion.DESCENDENTE, 5, 2)
        Assertions.assertTrue(especiesPagina5.isEmpty())

    }

    @Test
    fun comprobacionDeErrorAlPedirUnaPaginaNegativaCuandoSeBuscanLasEspeciesDeUnPatogeno(){
        servicioPatogeno.crear(salmonella)
        Assertions.assertThrows(ErrorValorDePaginacionIvalido::class.java) {
            servicioPatogeno.especiesDePatogeno(salmonella.getId(), Direccion.ASCENDENTE, -2, 2)
        }
    }

    @Test
    fun comprobacionDeErrorAlPedirUnaCantidadDeElementosPorPaginaNegativaCuandoSeBuscanLasEspeciesDeUnPatogeno(){
        servicioPatogeno.crear(salmonella)
        Assertions.assertThrows(ErrorValorDePaginacionIvalido::class.java) {
            servicioPatogeno.especiesDePatogeno(salmonella.getId(), Direccion.ASCENDENTE, 1, -5)
        }
    }

    @Test
    fun seTrataDeRecuperarTodasLasEspeciesDelPatogenoEsteNoTiene() {

        servicioPatogeno.crear(covid)
        val especies = servicioPatogeno.especiesDePatogeno(covid.getId(), Direccion.DESCENDENTE, 1, 2)

        Assertions.assertEquals(especies.size, 0)

    }

    @Test
    fun seSabeSiNoEsPandemia() {

        servicioPatogeno.crear(salmonella)
        servicioVector.crear(pedro)

        val enterica: Especie = servicioPatogeno.agregarEspecie(salmonella.getId(), "Enterica", corea.getId())

        servicioVector.infectar(pedro.getId(),enterica.getId()!!)

        Assertions.assertFalse(servicioPatogeno.esPandemia(enterica.getId()!!))

    }

    @Test
    fun seSabeSiEsPandemia() {

        servicioPatogeno.crear(salmonella)
        servicioVector.crear(pepe)
        servicioVector.crear(pedro)

        val enterica: Especie = servicioPatogeno.agregarEspecie(salmonella.getId(), "Enterica", corea.getId())

        servicioVector.infectar(pepe.getId(),enterica.getId()!!)
        servicioVector.infectar(pedro.getId(),enterica.getId()!!)

        Assertions.assertTrue(servicioPatogeno.esPandemia(enterica.getId()!!))

    }

    @Test
    fun errorNoHayVectoresEnUbicacion() {

        Assertions.assertThrows(NoHayVectorException::class.java) {
            servicioPatogeno.crear(salmonella)
            servicioUbicacion.crear(corea)
            servicioPatogeno.agregarEspecie(salmonella.getId(), "Enterica", corea.getId())
        }

    }

    /*
    @Test
    fun testCuandoSeIntentaCrearDosPatogenosConElMismoNombre(){

        servicioPatogeno.crear(covid)

        Assertions.assertThrows(PersistenceException::class.java){
            servicioPatogeno.crear(Patogeno("Coronavirus", 1, 1, 1, 1, 1))
        }
    }

     */

    @AfterEach
    fun borrarRegistros() {
        dataService.cleanAll()
    }

}