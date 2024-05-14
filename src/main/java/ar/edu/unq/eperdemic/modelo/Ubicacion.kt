package ar.edu.unq.eperdemic.modelo

import ar.edu.unq.eperdemic.controller.dto.UbicacionDTO
import ar.edu.unq.eperdemic.exceptions.ErrorNombre
import javax.persistence.*
import org.springframework.data.neo4j.core.schema.Node
import org.springframework.data.neo4j.core.schema.Relationship

@Node
class Ubicacion() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Long? = null

    @Column(unique = true)
    private var nombre: String? = null

    @Relationship(type = "CAMINO")
    var camino: Camino? = null

    fun getId(): Long? {
        return this.id
    }
    fun setId(nuevoId: Long) {
        this.id = nuevoId
    }

    constructor(nombre: String) : this() {
        if(nombre.isBlank()){
            throw ErrorNombre("El nombre no puede ser vacio.")
        }
        this.nombre = nombre
    }

    fun getNombre(): String? {
        return this.nombre
    }

    fun setNombre(nombre: String) {
        this.nombre = nombre
    }

    fun aDTO(): UbicacionDTO? {
        return UbicacionDTO(this.getId(), this.nombre)
    }


}