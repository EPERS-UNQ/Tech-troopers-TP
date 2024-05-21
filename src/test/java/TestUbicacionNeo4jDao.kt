package ar.edu.unq.eperdemic

import ar.edu.unq.eperdemic.modelo.neo4j.Neo4jUbicacionDAO
import ar.edu.unq.eperdemic.modelo.neo4j.UbicacionNeo4j
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestUbicacionNeo4jDao {
    @Autowired
    private lateinit var ubicacionNeoDAO: Neo4jUbicacionDAO

    lateinit var ubi1: UbicacionNeo4j
    lateinit var ubi2: UbicacionNeo4j

    @BeforeEach
    fun crearModelo() {
        ubi1 = UbicacionNeo4j("Argentina")
        ubi2 = UbicacionNeo4j("Paraguay")
        ubicacionNeoDAO.save(ubi1)
        ubicacionNeoDAO.save(ubi2)
    }

    @Test
    fun a() {

    }
}