package ar.edu.unq.eperdemic

import ar.edu.unq.eperdemic.modelo.UbicacionJpa
import ar.edu.unq.eperdemic.modelo.neo4j.UbicacionNeo4j
import ar.edu.unq.eperdemic.persistencia.dao.Neo4jUbicacionDAO
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestDAOneo4 {

    @Autowired
    private lateinit var ubicacionNeoDAO: Neo4jUbicacionDAO

    lateinit var arg: UbicacionNeo4j
    lateinit var chl: UbicacionNeo4j
    lateinit var col: UbicacionNeo4j
    lateinit var vnz: UbicacionNeo4j
    lateinit var par: UbicacionNeo4j
    lateinit var bol: UbicacionNeo4j
    lateinit var urg: UbicacionNeo4j
    lateinit var ecu: UbicacionNeo4j
    lateinit var br: UbicacionNeo4j
    lateinit var per: UbicacionNeo4j

    @BeforeEach
    fun crearModelo() {
        arg = UbicacionNeo4j("Argentina")
        chl = UbicacionNeo4j("Chile")
        col = UbicacionNeo4j("Colombia")
        vnz = UbicacionNeo4j("Venezuela")
        par = UbicacionNeo4j("Paraguay")
        bol = UbicacionNeo4j("Bolivia")
        urg = UbicacionNeo4j("Uruguay")
        ecu = UbicacionNeo4j("Ecuador")
        br = UbicacionNeo4j("Brasil")
        per = UbicacionNeo4j("Peru")

        ubicacionNeoDAO.save(arg)
        ubicacionNeoDAO.save(chl)
        ubicacionNeoDAO.save(col)
        ubicacionNeoDAO.save(vnz)
        ubicacionNeoDAO.save(par)
        ubicacionNeoDAO.save(bol)
        ubicacionNeoDAO.save(urg)
        ubicacionNeoDAO.save(ecu)
        ubicacionNeoDAO.save(br)
        ubicacionNeoDAO.save(per)

        ubicacionNeoDAO.conectarCaminos(arg.getNombre()!!, vnz.getNombre()!!, "Aereo")
        ubicacionNeoDAO.conectarCaminos(arg.getNombre()!!, chl.getNombre()!!, "Terrestre")
        ubicacionNeoDAO.conectarCaminos(arg.getNombre()!!, chl.getNombre()!!, "Aquatico")
        ubicacionNeoDAO.conectarCaminos(arg.getNombre()!!, urg.getNombre()!!, "Aquatico")
        ubicacionNeoDAO.conectarCaminos(chl.getNombre()!!, urg.getNombre()!!, "Aquatico")
        ubicacionNeoDAO.conectarCaminos(chl.getNombre()!!, br.getNombre()!!, "Aquatico")
        ubicacionNeoDAO.conectarCaminos(chl.getNombre()!!, per.getNombre()!!, "Terrestre")
        ubicacionNeoDAO.conectarCaminos(per.getNombre()!!, bol.getNombre()!!, "Aereo")
        ubicacionNeoDAO.conectarCaminos(per.getNombre()!!, vnz.getNombre()!!, "Aereo")
        ubicacionNeoDAO.conectarCaminos(per.getNombre()!!, bol.getNombre()!!, "Terrestre")
        ubicacionNeoDAO.conectarCaminos(vnz.getNombre()!!, col.getNombre()!!, "Terrestre")
        ubicacionNeoDAO.conectarCaminos(col.getNombre()!!, vnz.getNombre()!!, "Aquatico")
        ubicacionNeoDAO.conectarCaminos(br.getNombre()!!, ecu.getNombre()!!, "Aereo")
        ubicacionNeoDAO.conectarCaminos(br.getNombre()!!, bol.getNombre()!!, "Terrestre")
        ubicacionNeoDAO.conectarCaminos(par.getNombre()!!, br.getNombre()!!, "Terrestre")
    }

    @Test
    fun test() {
        Assertions.assertTrue(ubicacionNeoDAO.esUbicacionCercana("Argentina", "Uruguay"))
    }

    @Test
    fun test2() {
        Assertions.assertTrue(ubicacionNeoDAO.esUbicacionAlcanzable("Argentina", "Uruguay", listOf("Terrestre", "Aquatico")))
    }

    @Test
    fun test3() {
        Assertions.assertEquals(listOf("Argentina", "Uruguay"),ubicacionNeoDAO.caminoIdeal("Argentina","Uruguay", listOf("Aquatico")))
    }

    @AfterEach
    fun tearDown() {
        ubicacionNeoDAO.deleteAll()
    }

}