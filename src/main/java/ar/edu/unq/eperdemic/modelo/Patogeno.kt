package ar.edu.unq.eperdemic.modelo


import java.io.Serializable
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

    override fun toString(): String {
        return tipo!!
    }

    fun crearEspecie(nombreEspecie: String, paisDeOrigen: String) : Especie {
        var nuevaEspecie = Especie(nombreEspecie, this, paisDeOrigen)
        cantidadDeEspecies++
        return nuevaEspecie
    }

}