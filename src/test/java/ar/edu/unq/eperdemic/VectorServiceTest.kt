package ar.edu.unq.eperdemic

import ar.edu.unq.eperdemic.modelo.Vector
import ar.edu.unq.eperdemic.persistencia.dao.jdbc.HibernateVectorDAO
import ar.edu.unq.eperdemic.services.VectorService
import ar.edu.unq.eperdemic.services.impl.VectorServiceImp
import ar.edu.unq.eperdemic.helper.dao.HibernateDataDAO
import ar.edu.unq.eperdemic.helper.service.DataService
import ar.edu.unq.eperdemic.helper.service.DataServiceImpl
import org.junit.jupiter.api.*
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS

@TestInstance(PER_CLASS)
class VectorServiceTest {

    lateinit var service: VectorService
    lateinit var dataService: DataService
    lateinit var humano: Vector
    lateinit var vaca:   Vector

    @BeforeEach
    fun prepare() {

        this.service = VectorServiceImp(HibernateVectorDAO())
        this.dataService = DataServiceImpl(HibernateDataDAO()) //Va en el After each.

        humano = Vector("Pedro")
        // Por el momento no lo uso...
        vaca = Vector("Marta")

    }

    @Test
    fun testDeCreacionDeUnVector(){
        var pedro = service.crear(humano)

        Assertions.assertEquals(1, pedro.id)
    }

    @AfterEach
    fun cleanup() {
        // Destroy cierra la session factory y fuerza a que, la proxima vez, una nueva tenga que ser creada.
        // Elimina los registros (la base de datos no).
        dataService.cleanAll()
    }

}