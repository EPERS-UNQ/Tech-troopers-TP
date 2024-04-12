package ar.edu.unq.eperdemic

import ar.edu.unq.eperdemic.modelo.Ubicacion
import ar.edu.unq.eperdemic.modelo.vector.Vector

import ar.edu.unq.eperdemic.services.UbicacionService
import ar.edu.unq.eperdemic.services.impl.UbicacionServiceImp

import org.junit.jupiter.api.*
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS

@TestInstance(PER_CLASS)
class HibernateUbicacinDAOTest {

    var service: UbicacionService = UbicacionServiceImp()

    lateinit var ubi1: Ubicacion
    lateinit var ubi2: Ubicacion
    lateinit var ubi3: Ubicacion

    lateinit var ubiPersistida1: Ubicacion
    lateinit var ubiPersistida2: Ubicacion
    lateinit var ubiPersistida3: Ubicacion

    lateinit var vector1: Vector

    @BeforeEach
    fun crearModelo() {

        ubi1 = Ubicacion("Argentina")
        ubi2 = Ubicacion("paraguay")
        ubi3 = Ubicacion("Uruguay")

        service.crear(ubi1)
        service.crear(ubi2)
        service.crear(ubi3)

        service
    }

    @Test
    fun alGuardarYLuegoRecuperarSeObtieneObjetosSimilares() {
        ubiPersistida1 = service.recuperar(1)
        Assertions.assertEquals(ubiPersistida1.nombre, "Argentina")
    }

}