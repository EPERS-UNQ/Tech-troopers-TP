package ar.edu.unq.eperdemic.modelo

import javax.persistence.*
import java.util.Objects

@Entity
class Ubicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    var nombre: String? = null

    @OneToMany(mappedBy = "ubicacion", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var vectores: MutableSet<Vector> = HashSet()

    constructor(nombre: String) {
        this.nombre = nombre
    }

    constructor() {
        this.nombre= ""
    }

}