package ar.edu.unq.eperdemic

import ar.edu.unq.eperdemic.modelo.Patogeno
import ar.edu.unq.eperdemic.persistencia.dao.PatogenoDAO
import ar.edu.unq.eperdemic.persistencia.dao.jdbc.JDBCPatogenoDAO
import org.junit.jupiter.api.*
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS

@TestInstance(PER_CLASS)
class JDBCPatogenoDAOTest {

    private val dao: PatogenoDAO = JDBCPatogenoDAO()
    lateinit var patogeno: Patogeno

    @BeforeEach
    fun crearModelo() {
        patogeno = Patogeno("Coronavirus")
    }

    @Test
    fun alGuardarYLuegoRecuperarSeObtieneObjetosSimilares() {
        dao.crear(patogeno)

        //Los patogenos son iguales
        val otroPatogeno = dao.recuperar(5)
        Assertions.assertEquals(patogeno.id, otroPatogeno.id)
        Assertions.assertEquals(patogeno.cantidadDeEspecies, otroPatogeno.cantidadDeEspecies)

        //Pero no son el mismo objeto =(
        //A esto nos referimos con "perdida de identidad"
        Assertions.assertTrue(patogeno !== otroPatogeno)
    }

    @AfterEach
    fun recuperarModelo() {
        //dao.recuperarATodos()
    }

}