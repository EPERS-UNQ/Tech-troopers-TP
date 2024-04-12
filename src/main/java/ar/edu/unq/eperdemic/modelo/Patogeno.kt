package ar.edu.unq.eperdemic.modelo


import java.io.Serializable
import java.util.*
import javax.persistence.*
import kotlin.collections.HashSet

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

    @OneToMany(mappedBy = "patogeno", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var especies: MutableSet<Especie> = HashSet()

    override fun toString(): String {
        return tipo!!
    }

    fun crearEspecie(nombreEspecie: String, paisDeOrigen: String) : Especie {
        var nuevaEspecie = Especie(nombreEspecie, this, paisDeOrigen)
        especies.add(nuevaEspecie)
        cantidadDeEspecies++
        return nuevaEspecie
    }

}