package ar.edu.unq.eperdemic.modelo

import ar.edu.unq.eperdemic.exceptions.ErrorNombre
import ar.edu.unq.eperdemic.exceptions.LimiteDeCampoErroneo
import javax.persistence.*

@Entity
class Patogeno() {
    constructor(tipo : String, contagioHumanos : Int,
                contagioAnimales : Int, contagioInsectos : Int,
                defensa : Int,
                capacidadDeBiomecanizacion : Int) : this() {
        this.tipo = tipo
        if( !(esCampoValido(contagioHumanos) && esCampoValido(contagioAnimales)
            && esCampoValido(contagioInsectos) && esCampoValido(defensa)
            && esCampoValido(capacidadDeBiomecanizacion)) ) {
            throw LimiteDeCampoErroneo()
        }
        if(tipo.isBlank()){
            throw ErrorNombre("El nombre del tipo del patogeno no puede ser vacio.")
        }
        this.capContagioHumano = contagioHumanos
        this.capContagioAnimal = contagioAnimales
        this.capContagioInsecto = contagioInsectos
        this.defensa = defensa
        this.capDeBiomecanizacion = capacidadDeBiomecanizacion
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long? = null

    var cantidadDeEspecies: Int = 0
    var tipo : String? = null

    @Column(columnDefinition = "INT CHECK (capContagioHumano <= 100)")
    var capContagioHumano : Int = 0

    @Column(columnDefinition = "INT CHECK (capContagioAnimal <= 100)")
    var capContagioAnimal : Int = 0

    @Column(columnDefinition = "INT CHECK (capContagioInsecto <= 100)")
    var capContagioInsecto : Int = 0

    @Column(columnDefinition = "INT CHECK (defensa <= 100)")
    var defensa : Int = 0

    @Column(columnDefinition = "INT CHECK (capDeBiomecanizacion <= 100)")
    var capDeBiomecanizacion : Int = 0

    override fun toString(): String {
        return tipo!!
    }

    fun getId(): Long {
        return this.id!!
    }

    fun setId(idNew : Long) {
        this.id = idNew
    }

    fun crearEspecie(nombreEspecie: String, paisDeOrigen: String) : Especie {
        var nuevaEspecie = Especie(nombreEspecie, this, paisDeOrigen)
        cantidadDeEspecies++
        return nuevaEspecie
    }

    private fun esCampoValido(campoAComprobar : Int) : Boolean {
        return (campoAComprobar > 0) && (campoAComprobar < 101)
    }

}