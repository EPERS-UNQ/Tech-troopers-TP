package ar.edu.unq.eperdemic.testServicios

import ar.edu.unq.eperdemic.modelo.Ubicacion
import ar.edu.unq.eperdemic.modelo.vector.Vector

import ar.edu.unq.eperdemic.persistencia.dao.hibernate.HibernateVectorDAO
import ar.edu.unq.eperdemic.persistencia.dao.hibernate.HibernateUbicacionDAO

import ar.edu.unq.eperdemic.helper.service.DataService
import ar.edu.unq.eperdemic.services.UbicacionService
import ar.edu.unq.eperdemic.services.impl.UbicacionServiceImp

import org.junit.jupiter.api.*
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS

@TestInstance(PER_CLASS)
class UbicacionServiceTest {

    val serviceUbicacion: UbicacionService = UbicacionServiceImp(
        HibernateUbicacionDAO(),
        HibernateVectorDAO()
    )


    lateinit var dataService: DataService

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
        //ubi2 = Ubicacion("paraguay")
        //ubi3 = Ubicacion("Uruguay")

        serviceUbicacion.crear(ubi1)
        //serviceUbicacion.crear(ubi2)
        //serviceUbicacion.crear(ubi3)

    }

    @Test
    fun alGuardarYLuegoRecuperarSeObtieneObjetosSimilares() {
        /* ubiPersistida1 = serviceUbicacion.recuperar(1)
        Assertions.assertEquals(ubiPersistida1.nombre, "Argentina")

         */
    }

}