package ar.edu.unq.eperdemic.EspecieTest

import ar.edu.unq.eperdemic.helper.dao.HibernateDataDAO
import ar.edu.unq.eperdemic.helper.service.DataService
import ar.edu.unq.eperdemic.helper.service.DataServiceImpl
import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.persistencia.dao.hibernate.HibernateEspecieDAO
import ar.edu.unq.eperdemic.services.EspecieService
import ar.edu.unq.eperdemic.services.impl.EspecieServiceImpl
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EspecieServiceImplTest {

    lateinit var dataService : DataService
    lateinit var service     : EspecieService

    lateinit var especie  : Especie
    lateinit var especie2 : Especie
    lateinit var patogeno : Patogeno


    @BeforeEach
    fun crearModelo() {
        patogeno = Patogeno("Wachiturro")
        especie  = Especie("Bacteria", patogeno, "Argentina")

        service     = EspecieServiceImpl( HibernateEspecieDAO() )
        dataService = DataServiceImpl( HibernateDataDAO() )

        service.crear(especie)
    }

    @Test
    fun alCrearYRecuperarUnaEspecieSeObtienenObjetosSimilares() {
        val otraEspecie = service.recuperar(especie.id!!)

        Assertions.assertEquals(especie.id, otraEspecie.id)
        Assertions.assertEquals(especie.nombre, otraEspecie.nombre)
        Assertions.assertEquals(especie.paisDeOrigen, otraEspecie.paisDeOrigen)
    }

    @Test
    fun alUpdatearUnPatogenoLaInformacionDelMismoCmabia() {
        service.updatear(especie)


    }


    @Test
    fun alRecuperarTodasLasEspeciesLasMismasSonSimiliaresALasYaExistentes() {
        especie2 = Especie("Virus", patogeno, "Brasil")
        service.crear(especie2)

        val listaEspeciesRecuperadas : List<Especie> = service.recuperarTodos()

        Assertions.assertEquals(especie.id,  listaEspeciesRecuperadas.get(0).id)
        Assertions.assertEquals(especie.nombre, listaEspeciesRecuperadas.get(0).nombre)
        Assertions.assertEquals(especie.paisDeOrigen, listaEspeciesRecuperadas.get(0).paisDeOrigen)
        Assertions.assertEquals(especie2.id, listaEspeciesRecuperadas.get(1).id)
        Assertions.assertEquals(especie2.nombre, listaEspeciesRecuperadas.get(1).nombre)
        Assertions.assertEquals(especie2.paisDeOrigen, listaEspeciesRecuperadas.get(1).paisDeOrigen)
    }

    @AfterEach
    fun borrarRegistros() {

        dataService.cleanAll()

    }

}