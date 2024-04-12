package ar.edu.unq.eperdemic.modelo.vector

import javax.persistence.*
import ar.edu.unq.eperdemic.modelo.*
@Entity
open class Vector() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    var tipo: String? = null //Template Method -> Plantilla general y 3 particulares para cada vector de como contagia.
    var nombre: String? = null

    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var especies: MutableSet<Especie> = HashSet()

    @ManyToOne
    var ubicacion: Ubicacion? = null

    constructor(nombre: String, ubicacion: Ubicacion):this() {
        this.nombre = nombre
        this.ubicacion = ubicacion
    }

    open fun infectar(vectorId: Long, especieId: Long) {}

    fun enfermedad(vectorId: Long): List<Especie>{
        TODO("COMPLETAR")
    }
}