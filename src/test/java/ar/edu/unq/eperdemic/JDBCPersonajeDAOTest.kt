package ar.edu.unq.eperdemic

import ar.edu.unq.eperdemic.modelo.Patogeno
import ar.edu.unq.eperdemic.persistencia.dao.PatogenoDAO
import ar.edu.unq.eperdemic.persistencia.dao.jdbc.JDBCPatogenoDAO
import org.junit.jupiter.api.*
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS

@TestInstance(PER_CLASS)
class JDBCPersonajeDAOTest {

    private val dao: PatogenoDAO = JDBCPatogenoDAO()
    lateinit var patogeno: Patogeno

    @BeforeEach
    fun crearModelo() {
        patogeno = Patogeno("Coronavirus")
    }

    @Test
    fun alGuardarYLuegoRecuperarSeObtieneObjetosSimilares() {
        dao.crear(patogeno)

        //Los personajes son iguales
        val otroPatogeno = dao.recuperar(1)
        Assertions.assertEquals(patogeno.id, otroPatogeno.id)
        Assertions.assertEquals(patogeno.cantidadDeEspecies, otroPatogeno.cantidadDeEspecies)

        //Pero no son el mismo objeto =(
        //A esto nos referimos con "perdida de identidad"
        Assertions.assertTrue(patogeno !== otroPatogeno)
    }


    /*
    @AfterEach
    fun emilinarModelo() {
        dao
    }
    */
}