package ar.edu.unq.eperdemic.modelo

import javax.persistence.*
import ar.edu.unq.eperdemic.modelo.vector.Vector

@Entity
class Ubicacion() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Long? = null

    var nombre: String? = null

    constructor(nombre: String): this() {
        this.nombre = nombre
    }

}