package ar.edu.unq.eperdemic

import ar.edu.unq.eperdemic.modelo.Ubicacion
import ar.edu.unq.eperdemic.services.UbicacionService
import ar.edu.unq.eperdemic.services.impl.UbicacionServiceImp
import org.junit.jupiter.api.*
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS

@TestInstance(PER_CLASS)
class HibernateUbicacinDAOTest {

    private val service: UbicacionService = UbicacionServiceImp()
    lateinit var ubi1: Ubicacion
    lateinit var ubi2: Ubicacion

    @BeforeEach
    fun crearModelo() {

        ubi1 = Ubicacion("Argentina")

        service.crear(ubi1)

        ubi2 = service.recuperar(1)

    }

    @Test
    fun alGuardarYLuegoRecuperarSeObtieneObjetosSimilares() {
        Assertions.assertEquals(ubi2.nombre, "Argentina")
        Assertions.assertEquals(ubi2.id, 1)
    }

}