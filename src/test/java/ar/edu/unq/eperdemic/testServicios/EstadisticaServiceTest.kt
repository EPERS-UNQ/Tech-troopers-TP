package ar.edu.unq.eperdemic.testServicios

import ar.edu.unq.eperdemic.helper.dao.HibernateDataDAO
import ar.edu.unq.eperdemic.helper.service.DataService
import ar.edu.unq.eperdemic.helper.service.DataServiceImpl
import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.Patogeno
import ar.edu.unq.eperdemic.modelo.Ubicacion
import ar.edu.unq.eperdemic.modelo.vector.TipoVector
import ar.edu.unq.eperdemic.modelo.vector.Vector
import ar.edu.unq.eperdemic.persistencia.dao.hibernate.HibernateEspecieDAO
import ar.edu.unq.eperdemic.persistencia.dao.hibernate.HibernatePatogenoDAO
import ar.edu.unq.eperdemic.persistencia.dao.hibernate.HibernateUbicacionDAO
import ar.edu.unq.eperdemic.persistencia.dao.hibernate.HibernateVectorDAO
import ar.edu.unq.eperdemic.services.*
import ar.edu.unq.eperdemic.services.impl.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EstadisticaServiceTest {

    lateinit var service    : EstadisticaService
    lateinit var dataService: DataService
    lateinit var serviceVector   : VectorService
    lateinit var serviceUbicacion: UbicacionService
    lateinit var serviceEspecie  : EspecieService
    lateinit var servicePatogeno : PatogenoService

    lateinit var especie   : Especie
    lateinit var especie2  : Especie
    lateinit var especie3  : Especie
    lateinit var patogeno  : Patogeno

    lateinit var humano     : Vector
    lateinit var humano2    : Vector
    lateinit var humano3    : Vector
    lateinit var golondrina: Vector
    lateinit var insecto:  Vector
    lateinit var insecto2: Vector

    lateinit var ubicacion: Ubicacion

    @BeforeEach
    fun crearModelo() {

        service     = EstadisticaServiceImpl( HibernateEspecieDAO() )
        dataService = DataServiceImpl( HibernateDataDAO() )
        servicePatogeno  = PatogenoServiceImpl( HibernatePatogenoDAO(), HibernateEspecieDAO() )
        serviceVector    = VectorServiceImp( HibernateVectorDAO(), HibernateEspecieDAO() )
        serviceUbicacion = UbicacionServiceImp( HibernateUbicacionDAO(), HibernateVectorDAO() )

        patogeno  = Patogeno("Wachiturro")
        servicePatogeno.crear(patogeno)
        especie  = servicePatogeno.agregarEspecie(patogeno.id!!, "Bacteria", "Argentina")
        especie2 = servicePatogeno.agregarEspecie(patogeno.id!!, "Virus", "Peru")

        ubicacion = Ubicacion("Argentina")

        humano     = Vector("Pedro", ubicacion, TipoVector.HUMANO)
        humano2    = Vector("Juan", ubicacion, TipoVector.HUMANO)
        golondrina = Vector("Pepita", ubicacion, TipoVector.ANIMAL)

        serviceUbicacion.crear(ubicacion)
        serviceVector.crear(humano)
        serviceVector.crear(humano2)
        serviceVector.crear(golondrina)
    }

    @Test
    fun testEspecieLider() {

        serviceVector.infectar(humano.getId()!!, especie.id!!)
        serviceVector.infectar(humano.getId()!!, especie2.id!!)
        serviceVector.infectar(humano2.getId()!!, especie.id!!)
        serviceVector.infectar(golondrina.getId()!!, especie.id!!)

        Assertions.assertEquals(especie.id, service.especieLider().id)
        Assertions.assertFalse(especie2.id!! == service.especieLider().id)

    }

    @Test
    fun testDeLosLideres() {

        especie3 = servicePatogeno.agregarEspecie(patogeno.id!!, "Adenovirus", "Bolivia")
        humano3  = Vector("Bautista", ubicacion, TipoVector.HUMANO)
        insecto  = Vector("Chinche", ubicacion, TipoVector.INSECTO)
        insecto2  = Vector("Mosca", ubicacion, TipoVector.INSECTO)
        serviceVector.crear(humano3)
        serviceVector.crear(insecto)
        serviceVector.crear(insecto2)

        serviceVector.infectar(humano.getId()!!,  especie.id!!)
        serviceVector.infectar(humano2.getId()!!, especie.id!!)
        serviceVector.infectar(insecto.getId()!!, especie.id!!)
        serviceVector.infectar(insecto2.getId()!!, especie.id!!)

        serviceVector.infectar(humano.getId()!!,  especie2.id!!)
        serviceVector.infectar(humano3.getId()!!, especie2.id!!)
        serviceVector.infectar(golondrina.getId()!!, especie2.id!!)

        serviceVector.infectar(humano.getId()!!, especie3.id!!)

        // especie2 -> Infectó dos humanos y un animal. Es mas lider esta.
        // especie  -> Infectó dos humanos y dos insecto.
        // especie3 -> Infectó un humano.

        Assertions.assertEquals(especie2.id, service.lideres().first().id)
        Assertions.assertEquals(especie.id , service.lideres()[1].id)
        Assertions.assertEquals(especie3.id , service.lideres()[2].id)

    }

    @AfterEach
    fun borrarRegistros() {

        dataService.cleanAll()

    }

}