package ar.edu.unq.eperdemic

import ar.edu.unq.eperdemic.modelo.vector.Vector
import ar.edu.unq.eperdemic.persistencia.dao.hibernate.HibernateVectorDAO
import ar.edu.unq.eperdemic.services.VectorService
import ar.edu.unq.eperdemic.services.impl.VectorServiceImp
import ar.edu.unq.eperdemic.helper.dao.HibernateDataDAO
import ar.edu.unq.eperdemic.helper.service.DataService
import ar.edu.unq.eperdemic.helper.service.DataServiceImpl
import ar.edu.unq.eperdemic.modelo.Ubicacion
import ar.edu.unq.eperdemic.persistencia.dao.hibernate.HibernateUbicacionDAO
import ar.edu.unq.eperdemic.services.UbicacionService
import ar.edu.unq.eperdemic.services.impl.UbicacionServiceImp
import org.junit.jupiter.api.*
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS

@TestInstance(PER_CLASS)
class VectorServiceTest {

    lateinit var service: VectorService
    lateinit var serviceUbicacion: UbicacionService
    lateinit var dataService: DataService

    lateinit var humano: Vector
    lateinit var golondrina: Vector
    lateinit var ubicacion: Ubicacion

    @BeforeEach
    fun prepare() {

        this.service = VectorServiceImp(HibernateVectorDAO())
        this.serviceUbicacion = UbicacionServiceImp()
        this.dataService = DataServiceImpl(HibernateDataDAO())

        ubicacion = Ubicacion("Argentina")
        humano     = Vector("Pedro", ubicacion)
        golondrina = Vector("Pepita", ubicacion)

        serviceUbicacion.crear(ubicacion)
        service.crear(humano)

    }

    @Test
    fun testDeCreacionDeUnVector(){
        val pepita = service.crear(golondrina)

        Assertions.assertEquals(2, pepita.id)
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
        val otroHumano = service.recuperar(humano.id!!)

        Assertions.assertEquals(otroHumano.id, humano.id)
        Assertions.assertEquals(otroHumano.nombre, humano.nombre)
    }

    @Test
    fun testDeRecuperarTodosLosVectores(){
        val pepita = service.crear(golondrina)

        val listaDeVectores = service.recuperarTodos()

        Assertions.assertEquals(humano.id, listaDeVectores.get(0).id)
        Assertions.assertEquals(humano.nombre, listaDeVectores.get(0).nombre)
        Assertions.assertEquals(pepita.id, listaDeVectores.get(1).id)
        Assertions.assertEquals(pepita.nombre, listaDeVectores.get(1).nombre)
    }


    @AfterEach
    fun cleanup() {
        // Destroy cierra la session factory y fuerza a que, la proxima vez, una nueva tenga que ser creada.
        // Elimina los registros (la base de datos no).
        dataService.cleanAll()
    }

}