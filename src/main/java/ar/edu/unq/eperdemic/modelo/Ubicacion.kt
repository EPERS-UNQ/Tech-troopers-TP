package ar.edu.unq.eperdemic.modelo

import javax.persistence.*
import java.util.Objects

@Entity
class Ubicacion {

    @Id
    var nombre: String

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var vectores: MutableSet<Vector> = HashSet()

    constructor(nombre: String) {
        this.nombre = nombre
    }
}