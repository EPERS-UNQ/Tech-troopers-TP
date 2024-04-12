package ar.edu.unq.eperdemic

import ar.edu.unq.eperdemic.helper.dao.HibernateDataDAO
import ar.edu.unq.eperdemic.helper.service.DataService
import ar.edu.unq.eperdemic.helper.service.DataServiceImpl
import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.Patogeno
import ar.edu.unq.eperdemic.persistencia.dao.EspecieDAO
import ar.edu.unq.eperdemic.persistencia.dao.PatogenoDAO
import ar.edu.unq.eperdemic.persistencia.dao.hibernate.HibernateEspecieDAO
import ar.edu.unq.eperdemic.persistencia.dao.hibernate.HibernatePatogenoDAO
import ar.edu.unq.eperdemic.persistencia.dao.hibernate.HibernateVectorDAO
import ar.edu.unq.eperdemic.services.EspecieService
import ar.edu.unq.eperdemic.services.PatogenoService
import ar.edu.unq.eperdemic.services.impl.EspecieServiceImpl
import ar.edu.unq.eperdemic.services.impl.PatogenoServiceImpl
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EspecieServiceImplTest {

    lateinit var dataService         : DataService
    lateinit var service             : EspecieService
    lateinit var servicePatogeno     : PatogenoService

    lateinit var especie   : Especie
    lateinit var especie2  : Especie
    lateinit var especiePersistida   : Especie
    lateinit var especiePersistida2  : Especie
    lateinit var patogeno  : Patogeno
    lateinit var patogeno2 : Patogeno



    @BeforeEach
    fun crearModelo() {
        patogeno = Patogeno("Wachiturro")
        especie  = Especie("Bacteria", patogeno, "Argentina")

        servicePatogeno = PatogenoServiceImpl(HibernatePatogenoDAO(), HibernateEspecieDAO())
        service = EspecieServiceImpl(HibernateEspecieDAO(), HibernateVectorDAO())
        dataService = DataServiceImpl(HibernateDataDAO())

        servicePatogeno.crear(patogeno)
        especiePersistida = servicePatogeno.agregarEspecie(patogeno.id!!, especie.nombre!!, especie.paisDeOrigen!!)
    }

    @Test
    fun alCrearYRecuperarUnaEspecieSeObtienenObjetosSimilares() {
        val otraEspecie = service.recuperar(especiePersistida.id!!)

        Assertions.assertEquals(especiePersistida.id, otraEspecie.id)
        Assertions.assertEquals(especiePersistida.nombre, otraEspecie.nombre)
        Assertions.assertEquals(especiePersistida.paisDeOrigen, otraEspecie.paisDeOrigen)
    }

    @Test
    fun alUpdatearUnPatogenoLaInformacionDelMismoCmabia() {
        var especieRecuperada = service.recuperar(especiePersistida.id!!)
        especieRecuperada.paisDeOrigen = "Chile"

        service.updatear(especieRecuperada)

        Assertions.assertEquals("Chile", service.recuperar(especieRecuperada.id!!).paisDeOrigen)
    }


    @Test
    fun alRecuperarTodasLasEspeciesLasMismasSonSimiliaresALasYaExistentes() {
        patogeno2 = Patogeno("Otaku")
        servicePatogeno.crear(patogeno2)
        especie2  = Especie("Virus", patogeno2, "Brasil")
        especiePersistida2 = servicePatogeno.agregarEspecie(patogeno2.id!!, especie2.nombre!!, especie2.paisDeOrigen!!)

        val listaEspeciesRecuperadas : List<Especie> = service.recuperarTodos()

        Assertions.assertEquals(especiePersistida.id,  listaEspeciesRecuperadas.get(0).id)
        Assertions.assertEquals(especiePersistida.nombre, listaEspeciesRecuperadas.get(0).nombre)
        Assertions.assertEquals(especiePersistida.paisDeOrigen, listaEspeciesRecuperadas.get(0).paisDeOrigen)
        Assertions.assertEquals(especiePersistida2.id, listaEspeciesRecuperadas.get(1).id)
        Assertions.assertEquals(especiePersistida2.nombre, listaEspeciesRecuperadas.get(1).nombre)
        Assertions.assertEquals(especiePersistida2.paisDeOrigen, listaEspeciesRecuperadas.get(1).paisDeOrigen)
    }

    /*@Test
    fun verificacionDeCantidadDeVectoresInfectadosPorUnaEspecieParticular() {

        service.cantidadDeInfectados(especiePersistida.id!!)

    }*/

    @AfterEach
    fun borrarRegistros() {

        dataService.cleanAll()

    }

}