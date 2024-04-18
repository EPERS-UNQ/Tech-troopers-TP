package ar.edu.unq.eperdemic.testServicios

import ar.edu.unq.eperdemic.modelo.vector.Vector
import ar.edu.unq.eperdemic.persistencia.dao.hibernate.HibernateVectorDAO
import ar.edu.unq.eperdemic.services.VectorService
import ar.edu.unq.eperdemic.services.impl.VectorServiceImp
import ar.edu.unq.eperdemic.helper.dao.HibernateDataDAO
import ar.edu.unq.eperdemic.helper.service.DataService
import ar.edu.unq.eperdemic.helper.service.DataServiceImpl
import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.Patogeno
import ar.edu.unq.eperdemic.modelo.Ubicacion
import ar.edu.unq.eperdemic.modelo.vector.TipoVector
import ar.edu.unq.eperdemic.persistencia.dao.hibernate.HibernateEspecieDAO
import ar.edu.unq.eperdemic.persistencia.dao.hibernate.HibernatePatogenoDAO
import ar.edu.unq.eperdemic.persistencia.dao.hibernate.HibernateUbicacionDAO
import ar.edu.unq.eperdemic.services.EspecieService
import ar.edu.unq.eperdemic.services.PatogenoService
import ar.edu.unq.eperdemic.services.UbicacionService
import ar.edu.unq.eperdemic.services.impl.EspecieServiceImpl
import ar.edu.unq.eperdemic.services.impl.PatogenoServiceImpl
import ar.edu.unq.eperdemic.services.impl.UbicacionServiceImp
import org.junit.jupiter.api.*
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS

@TestInstance(PER_CLASS)
class VectorServiceTest {

    lateinit var service: VectorService
    lateinit var serviceUbicacion: UbicacionService
    lateinit var serviceEspecie: EspecieService
    lateinit var servicePatogeno: PatogenoService
    lateinit var dataService: DataService

    lateinit var especie   : Especie
    lateinit var patogeno  : Patogeno

    lateinit var humano: Vector
    lateinit var golondrina: Vector
    lateinit var ubicacion: Ubicacion

    @BeforeEach
    fun prepare() {

        this.service = VectorServiceImp(HibernateVectorDAO(), HibernateEspecieDAO())
        this.serviceUbicacion = UbicacionServiceImp(HibernateUbicacionDAO(), HibernateVectorDAO())
        this.serviceEspecie = EspecieServiceImpl(HibernateEspecieDAO())
        this.servicePatogeno  = PatogenoServiceImpl(HibernatePatogenoDAO(), HibernateEspecieDAO(), HibernateUbicacionDAO(), HibernateVectorDAO())
        this.dataService = DataServiceImpl(HibernateDataDAO())

        ubicacion = Ubicacion("Argentina")
        humano     = Vector("Pedro", ubicacion, TipoVector.HUMANO)
        golondrina = Vector("Pepita", ubicacion, TipoVector.ANIMAL)

        patogeno  = Patogeno("Wachiturro", 90, 9, 9, 9, 67)

        serviceUbicacion.crear(ubicacion)
        service.crear(humano)

        servicePatogeno.crear(patogeno)
        especie = servicePatogeno.agregarEspecie(patogeno.getId()!!, "Bacteria", ubicacion.getId()!!)

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
        val otroHumano = service.recuperar(humano.getId()!!)

        Assertions.assertEquals(otroHumano.getId(), humano.getId())
        Assertions.assertEquals(otroHumano.nombre, humano.nombre)
    }

    @Test
    fun testDeRecuperarTodosLosVectores(){
        val pepita = service.crear(golondrina)

        val listaDeVectores = service.recuperarTodos()

        Assertions.assertEquals(humano.getId(), listaDeVectores.get(0).getId())
        Assertions.assertEquals(humano.nombre, listaDeVectores.get(0).nombre)
        Assertions.assertEquals(pepita.getId(), listaDeVectores.get(1).getId())
        Assertions.assertEquals(pepita.nombre, listaDeVectores.get(1).nombre)
    }

    @Test
    fun testDeInfectarAUnVector(){

        val pepita = service.crear(golondrina)

        Assertions.assertFalse(pepita.estaInfectado())

        service.infectar(pepita.getId()!!, especie.getId()!!)

        val otraGolondrina = service.recuperar(pepita.getId()!!)

        Assertions.assertTrue(otraGolondrina.estaInfectado())
        Assertions.assertEquals(otraGolondrina.enfermedadesDelVector().first().getId(), especie.getId())

    }

    @Test
    fun testDeEnfermedadesDeUnVector(){

        val pepita = service.crear(golondrina)

        service.infectar(pepita.getId()!!, especie.getId()!!)

        Assertions.assertFalse(service.enfermedades(pepita.getId()!!).isEmpty())
        Assertions.assertEquals(service.recuperar(pepita.getId()!!).enfermedadesDelVector().first().getId(), especie.getId())

        service.infectar(pepita.getId()!!, especie.getId()!!)

        Assertions.assertTrue(service.recuperar(pepita.getId()!!).estaInfectado())
    }

    @AfterEach
    fun cleanup() {
        // Destroy cierra la session factory y fuerza a que, la proxima vez, una nueva tenga que ser creada.
        // Elimina los registros (la base de datos no).
        dataService.cleanAll()
    }

}

