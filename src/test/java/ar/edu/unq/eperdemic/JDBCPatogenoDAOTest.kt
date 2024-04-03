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
    lateinit var patogeno2: Patogeno

    @BeforeEach
    fun crearModelo() {

        patogeno = Patogeno("Coronavirus")
        dao.crear(patogeno)

    }

    @Test
    fun alGuardarYLuegoRecuperarSeObtieneObjetosSimilares() {

        //Los patogenos son iguales
        val otroPatogeno = dao.recuperar(patogeno.id!!)
        Assertions.assertEquals(patogeno.id, otroPatogeno.id)
        Assertions.assertEquals(patogeno.cantidadDeEspecies, otroPatogeno.cantidadDeEspecies)

        //Pero no son el mismo objeto =(
        //A esto nos referimos con "perdida de identidad"
        Assertions.assertTrue(patogeno !== otroPatogeno)

    }

    @Test
    fun alCrearEspecieDeUnPatogenoYLuegoActualizarYRecuperarLaCantidadDeEspeciesDelObjetoAumenta() {

        var cont: Int = patogeno.cantidadDeEspecies

        this.patogeno.crearEspecie("SARS-CoV-2", "China")

        // persistimos el patogeno actualizado y obtenemos un nuevo objeto desde la base de datos
        dao.actualizar(patogeno)
        val otroPatogeno = dao.recuperar(patogeno.id!!)

        Assertions.assertEquals(cont + 1, otroPatogeno.cantidadDeEspecies)
        Assertions.assertEquals(patogeno.cantidadDeEspecies, otroPatogeno.cantidadDeEspecies)

    }

    @Test
    fun a() {

        patogeno2 = Patogeno("Dengue")
        dao.crear(patogeno2)

        val patogenos: List<Patogeno> = dao.recuperarATodos()

        Assertions.assertEquals(patogeno.id, patogenos.get(0).id)
        Assertions.assertEquals(patogeno2.id, patogenos.get(1).id)
        /*
        patogenos.forEach { pat ->
            Assertions.assertTrue(patogenos2.any { it.id == pat.id })
        }

         */
        dao.eliminar(patogeno2)
    }

    @AfterEach
    fun emilinarModelo() {
        dao.eliminar(patogeno)
    }

}