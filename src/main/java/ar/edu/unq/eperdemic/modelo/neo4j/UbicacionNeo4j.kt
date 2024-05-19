package ar.edu.unq.eperdemic.modelo.neo4j

import ar.edu.unq.eperdemic.exceptions.ErrorNombre
import ar.edu.unq.eperdemic.modelo.camino.Camino
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Relationship
import org.springframework.data.neo4j.core.schema.*

@Node
class UbicacionNeo4j() {

    @Id
    @GeneratedValue
    private var id: Long? = null

    private var nombre: String? = null

    constructor(nombre: String) : this() {
        if(nombre.isBlank()){
            throw ErrorNombre("El nombre no puede ser vacio.")
        }
        this.nombre = nombre
    }

    @Relationship(type = "CAMINO")
    var camino: Camino? = null

}