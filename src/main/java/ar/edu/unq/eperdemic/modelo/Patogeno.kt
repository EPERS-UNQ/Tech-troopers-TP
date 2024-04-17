package ar.edu.unq.eperdemic.modelo

import java.io.Serializable
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
    var capContagioHumano : Int = 0
    var capContagioAnimal : Int = 0
    var capContagioInsecto : Int = 0
    var defensa : Int = 0
    var capDeBiomecanizacion : Int = 0

    override fun toString(): String {
        return tipo!!
    }

    fun crearEspecie(nombreEspecie: String, paisDeOrigen: String) : Especie {
        var nuevaEspecie = Especie(nombreEspecie, this, paisDeOrigen)
        cantidadDeEspecies++
        return nuevaEspecie
    }

}