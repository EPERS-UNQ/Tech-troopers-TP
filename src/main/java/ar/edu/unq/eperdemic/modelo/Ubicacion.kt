package ar.edu.unq.eperdemic.modelo

import javax.persistence.*
import ar.edu.unq.eperdemic.modelo.vector.Vector

@Entity
class Ubicacion() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(unique = true)
    var nombre: String? = null

    @OneToMany(mappedBy = "ubicacion", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var vectores: MutableSet<Vector> = HashSet()

    constructor(nombre: String) : this() {
        this.nombre = nombre
    }
}