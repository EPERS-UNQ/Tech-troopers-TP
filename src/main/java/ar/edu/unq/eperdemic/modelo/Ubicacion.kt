package ar.edu.unq.eperdemic.modelo

import javax.persistence.*

@Entity
class Ubicacion() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Long? = null

    @Column(unique = true)

    private var nombre: String? = null

    fun getId(): Long? {
        return this.id!!
    }

    constructor(nombre: String) : this() {
        this.nombre = nombre
    }

    fun getNombre(): String? {
        return this.nombre
    }

    fun setNombre(nombre: String) {
        this.nombre = nombre
    }

}