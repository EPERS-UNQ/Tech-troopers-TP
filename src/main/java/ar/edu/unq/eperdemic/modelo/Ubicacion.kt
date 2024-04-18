package ar.edu.unq.eperdemic.modelo

import javax.persistence.*

@Entity
class Ubicacion() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Long? = null

    @Column(unique = true)
    var nombre: String? = null

    fun getId(): Long?{
        return this.id!!
    }

    constructor(nombre: String) : this() {
        this.nombre = nombre
    }

}