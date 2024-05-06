package ar.edu.unq.eperdemic.modelo.mutacion

import ar.edu.unq.eperdemic.modelo.vector.TipoVector
import ar.edu.unq.eperdemic.modelo.vector.Vector
import javax.persistence.Entity

@Entity
class BioalteracionMecanica() : Mutacion(){

    lateinit var tipoNuevo : TipoVector

    constructor(nuevoTipoDeVector : TipoVector) : this() {
       this.tipoNuevo = nuevoTipoDeVector
    }

    override fun atributo(): Any {
        return this.tipoNuevo
    }

    override fun habilitaContagiarA(vector: TipoVector) : Boolean {
        return this.tipoNuevo == vector
    }

    override fun potencia(): Int {
        return 0
    }

    override fun eleminarEspeciesInferiores(vector: Vector) { }

}