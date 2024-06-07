package ar.edu.unq.eperdemic.modelo.ubicacion

import ar.edu.unq.eperdemic.exceptions.ErrorNombre
import org.springframework.data.neo4j.core.schema.Node
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

    fun getNombre(): String? {
        return this.nombre
    }
}