package ar.edu.unq.eperdemic.testServicios

import ar.edu.unq.eperdemic.helper.service.DataService
import ar.edu.unq.eperdemic.helper.service.DataServiceImpl
import ar.edu.unq.eperdemic.helper.dao.HibernateDataDAO
import ar.edu.unq.eperdemic.modelo.*
import ar.edu.unq.eperdemic.modelo.vector.TipoVector
import ar.edu.unq.eperdemic.modelo.vector.Vector
import ar.edu.unq.eperdemic.persistencia.dao.hibernate.HibernateEspecieDAO
import ar.edu.unq.eperdemic.persistencia.dao.hibernate.HibernatePatogenoDAO
import ar.edu.unq.eperdemic.persistencia.dao.hibernate.HibernateUbicacionDAO
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
/*
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


    @BeforeEach
    fun crearModelo() {
        patogeno  = Patogeno("Wachiturro")

        servicePatogeno = PatogenoServiceImpl(HibernatePatogenoDAO(), HibernateEspecieDAO(), HibernateUbicacionDAO(), HibernateVectorDAO())
        service = EspecieServiceImpl(HibernateEspecieDAO())
        service         = EspecieServiceImpl(HibernateEspecieDAO(), HibernateVectorDAO())
        servicePatogeno  = PatogenoServiceImpl(HibernatePatogenoDAO(), HibernateEspecieDAO())
        dataService = DataServiceImpl(HibernateDataDAO())

        servicePatogeno.crear(patogeno)
        especiePersistida = servicePatogeno.agregarEspecie(patogeno.id!!, "Bacteria", "Argentina")
    }

    @Test
    fun testAlRecuperarUnaEspecieSeObtienenObjetosSimilares() {
        val otraEspecie = service.recuperar(especiePersistida.id!!)

        Assertions.assertEquals(especiePersistida.id, otraEspecie.id)
        Assertions.assertEquals(especiePersistida.nombre, otraEspecie.nombre)
        Assertions.assertEquals(especiePersistida.paisDeOrigen, otraEspecie.paisDeOrigen)
    }

    @Test
    fun testAlUpdatearUnPatogenoLaInformacionDelMismoCmabia() {
        var especieRecuperada = service.recuperar(especiePersistida.id!!)
        especieRecuperada.paisDeOrigen = "Chile"

        service.updatear(especieRecuperada)

        Assertions.assertEquals("Chile", service.recuperar(especieRecuperada.id!!).paisDeOrigen)
    }


    @Test
    fun testAlRecuperarTodasLasEspeciesLasMismasSonSimiliaresALasYaExistentes() {
        patogeno2 = Patogeno("Otaku")
        servicePatogeno.crear(patogeno2)
        especiePersistida2 = servicePatogeno.agregarEspecie(patogeno2.id!!, "Virus", "Brasil")

        val listaEspeciesRecuperadas : List<Especie> = service.recuperarTodos()

        Assertions.assertEquals(especiePersistida.id,  listaEspeciesRecuperadas.get(0).id)
        Assertions.assertEquals(especiePersistida.nombre, listaEspeciesRecuperadas.get(0).nombre)
        Assertions.assertEquals(especiePersistida.paisDeOrigen, listaEspeciesRecuperadas.get(0).paisDeOrigen)
        Assertions.assertEquals(especiePersistida2.id, listaEspeciesRecuperadas.get(1).id)
        Assertions.assertEquals(especiePersistida2.nombre, listaEspeciesRecuperadas.get(1).nombre)
        Assertions.assertEquals(especiePersistida2.paisDeOrigen, listaEspeciesRecuperadas.get(1).paisDeOrigen)
    }

    @Test
    fun testVerificacionDeCantidadDeVectoresInfectadosPorUnaEspecieParticular() {
        ubicacion  = Ubicacion("Argentina")
        humano     = Vector("Pedro", ubicacion, TipoVector.HUMANO)
        golondrina = Vector("Pepita", ubicacion, TipoVector.ANIMAL)

        serviceVector    = VectorServiceImp( HibernateVectorDAO(), HibernateEspecieDAO() )
        serviceUbicacion = UbicacionServiceImp(HibernateUbicacionDAO(), HibernateVectorDAO())
        serviceUbicacion.crear(ubicacion)
        serviceVector.crear(humano)
        serviceVector.crear(golondrina)

        serviceVector.infectar(humano.getId()!!, especiePersistida.id!!)
        serviceVector.infectar(golondrina.getId()!!, especiePersistida.id!!)

        Assertions.assertEquals(2, service.cantidadDeInfectados(especiePersistida.id!!))

    }

    @AfterEach
    fun borrarRegistros() {

        dataService.cleanAll()

    }

}

 */
