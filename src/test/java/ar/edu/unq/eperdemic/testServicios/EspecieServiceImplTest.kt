/*package ar.edu.unq.eperdemic.testServicios

import ar.edu.unq.eperdemic.exceptions.NoExisteLaEspecie
import ar.edu.unq.eperdemic.helper.service.DataService
import ar.edu.unq.eperdemic.helper.service.DataServiceImpl
import ar.edu.unq.eperdemic.helper.dao.HibernateDataDAO
import ar.edu.unq.eperdemic.modelo.*
import ar.edu.unq.eperdemic.modelo.RandomGenerator.NoAleatorioStrategy
import ar.edu.unq.eperdemic.modelo.RandomGenerator.RandomGenerator
import ar.edu.unq.eperdemic.modelo.vector.TipoVector
import ar.edu.unq.eperdemic.modelo.vector.Vector
import ar.edu.unq.eperdemic.persistencia.dao.HibernateEspecieDAO
import ar.edu.unq.eperdemic.persistencia.dao.hibernate.HibernatePatogenoDAO
import ar.edu.unq.eperdemic.persistencia.dao.hibernate.HibernateVectorDAO
import ar.edu.unq.eperdemic.services.EspecieService
import ar.edu.unq.eperdemic.services.PatogenoService
import ar.edu.unq.eperdemic.services.VectorService
import ar.edu.unq.eperdemic.services.UbicacionService
import ar.edu.unq.eperdemic.services.impl.EspecieServiceImpl
import ar.edu.unq.eperdemic.services.impl.PatogenoServiceImpl
import ar.edu.unq.eperdemic.services.impl.UbicacionServiceImp
import ar.edu.unq.eperdemic.services.impl.VectorServiceImp
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EspecieServiceImplTest {

    lateinit var dataService         : DataService
    lateinit var service             : EspecieService
    lateinit var servicePatogeno     : PatogenoService
    lateinit var serviceVector       : VectorService
    lateinit var serviceUbicacion    : UbicacionService

    lateinit var especiePersistida   : Especie
    lateinit var especiePersistida2  : Especie

    lateinit var patogeno  : Patogeno
    lateinit var patogeno2 : Patogeno

    lateinit var humano     : Vector
    lateinit var golondrina : Vector
    lateinit var ubicacion : Ubicacion

    lateinit var random : RandomGenerator

    @BeforeEach
    fun crearModelo() {
        patogeno  = Patogeno("Wachiturro", 90, 9, 9, 9, 67)
        ubicacion = Ubicacion("Argentina")
        humano    = Vector("Pedro", ubicacion, TipoVector.HUMANO)

        service = EspecieServiceImpl( HibernateEspecieDAO(), HibernateVectorDAO() )
        servicePatogeno  = PatogenoServiceImpl(HibernatePatogenoDAO(), HibernateEspecieDAO(), HibernateUbicacionDAO(), HibernateVectorDAO())
        serviceVector    = VectorServiceImp( HibernateVectorDAO(), HibernateEspecieDAO() )
        serviceUbicacion = UbicacionServiceImp( HibernateUbicacionDAO(), HibernateVectorDAO() )
        dataService = DataServiceImpl(HibernateDataDAO())

        serviceUbicacion.crear(ubicacion)
        serviceVector.crear(humano)

        servicePatogeno.crear(patogeno)

        especiePersistida = servicePatogeno.agregarEspecie(patogeno.getId()!!, "Bacteria", ubicacion.getId()!!)

        random = RandomGenerator.getInstance()
        random.setStrategy(NoAleatorioStrategy())
        random.setNumeroGlobal(0)
        random.setBooleanoGlobal(true)
    }

    @Test
    fun testAlRecuperarUnaEspecieSeObtienenObjetosSimilares() {
        val otraEspecie = service.recuperar(especiePersistida.getId()!!)

        Assertions.assertEquals(especiePersistida.getId(), otraEspecie.getId())
        Assertions.assertEquals(especiePersistida.nombre, otraEspecie.nombre)
        Assertions.assertEquals(especiePersistida.paisDeOrigen, otraEspecie.paisDeOrigen)
        Assertions.assertEquals(especiePersistida.patogeno!!.getId(), otraEspecie.patogeno!!.getId())
    }

    @Test
    fun testAlUpdatearUnPatogenoLaInformacionDelMismoCmabia() {
        var especieRecuperada = service.recuperar(especiePersistida.getId()!!)
        especieRecuperada.paisDeOrigen = "Chile"

        service.updatear(especieRecuperada)

        Assertions.assertEquals("Chile", service.recuperar(especieRecuperada.getId()!!).paisDeOrigen)
    }


    @Test
    fun testAlRecuperarTodasLasEspeciesLasMismasSonSimiliaresALasYaExistentes() {
        patogeno2 = Patogeno("Otaku", 78, 7, 7, 8, 12)
        servicePatogeno.crear(patogeno2)

        especiePersistida2 = servicePatogeno.agregarEspecie(patogeno2.getId()!!, "Virus", ubicacion.getId()!!)

        val listaEspeciesRecuperadas : List<Especie> = service.recuperarTodos()

        Assertions.assertEquals(especiePersistida.getId(),  listaEspeciesRecuperadas[0].getId())
        Assertions.assertEquals(especiePersistida.nombre, listaEspeciesRecuperadas[0].nombre)
        Assertions.assertEquals(especiePersistida.paisDeOrigen, listaEspeciesRecuperadas[0].paisDeOrigen)
        Assertions.assertEquals(especiePersistida2.getId(), listaEspeciesRecuperadas[1].getId())
        Assertions.assertEquals(especiePersistida2.nombre, listaEspeciesRecuperadas[1].nombre)
        Assertions.assertEquals(especiePersistida2.paisDeOrigen, listaEspeciesRecuperadas[1].paisDeOrigen)
    }

    @Test
    fun testVerificacionDeCantidadDeVectoresInfectadosPorUnaEspecieParticular() {
        golondrina = Vector("Pepita", ubicacion, TipoVector.ANIMAL)
        serviceVector.crear(golondrina)

        serviceVector.infectar(golondrina.getId()!!, especiePersistida.getId()!!)

        Assertions.assertEquals(2, service.cantidadDeInfectados(especiePersistida.getId()!!))

    }

    @Test
    fun testSeTrataDeRecuperarUnaEspecieQueNoExiste() {

        Assertions.assertThrows(NoExisteLaEspecie::class.java) {
            service.recuperar(15)
        }

    }

    @AfterEach
    fun borrarRegistros() {

        dataService.cleanAll()

    }

}

 */