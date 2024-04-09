package ar.edu.unq.eperdemic.modelo


import java.io.Serializable
import java.util.*
import javax.persistence.*

@Entity
class Patogeno() : Serializable {
    constructor(tipo : String) : this() {
        this.tipo = tipo
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long? = null

    var cantidadDeEspecies: Int = 0
    var tipo : String? = null

    @ManyToMany(mappedBy = "patogenos", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var vectoresInfectados: MutableSet<Vector> = HashSet()

    override fun toString(): String {
        return tipo!!
    }

    fun crearEspecie(nombreEspecie: String, paisDeOrigen: String) : Especie {
        cantidadDeEspecies++
        return Especie(this, nombreEspecie, paisDeOrigen)
    }

}