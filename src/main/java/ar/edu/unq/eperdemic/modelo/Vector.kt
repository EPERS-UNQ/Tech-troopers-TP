package ar.edu.unq.eperdemic.modelo

import net.bytebuddy.implementation.bind.annotation.Empty
import javax.persistence.*
import java.util.Objects

@Entity
class Vector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    var tipo: String? = null //Template Method -> Plantilla general y 3 particulares para cada vector de como contagia.
    var nombre: String? = null

    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var especies: MutableSet<Especie> = HashSet()

    @ManyToOne
    var ubicacion: Ubicacion? = null

    constructor(nombre: String) {
        this.nombre = nombre
    }

    constructor()

    fun enfermedad(vectorId: Long): List<Especie>{
        TODO("COMPLETAR")
    }

    fun contagiar(vector: Vector) {

    }

}