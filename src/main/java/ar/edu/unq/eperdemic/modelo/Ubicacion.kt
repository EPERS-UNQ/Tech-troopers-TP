package ar.edu.unq.eperdemic.modelo

import ar.edu.unq.eperdemic.modelo.vector.Vector
import javax.persistence.*

@Entity
class Ubicacion {

    @Id
    var nombre: String

    @OneToMany(mappedBy = "ubicacion", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var vectores: MutableSet<Vector> = HashSet()

    constructor(nombre: String) {
        this.nombre = nombre
    }
}