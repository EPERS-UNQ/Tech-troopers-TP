package ar.edu.unq.eperdemic.modelo.mutacion

import ar.edu.unq.eperdemic.modelo.vector.TipoVector
import ar.edu.unq.eperdemic.modelo.vector.Vector

class BioalteracionGenetica(val tipoVector : TipoVector) : Mutacion(){

    override fun atributo(): Any {
        return tipoVector
    }

    override fun habilitaContagiarA(vector: TipoVector) : Boolean {
        return this.tipoVector == vector
    }

    override fun potencia(): Int {
        return 0
    }

    override fun eliminarEspeciesInferiores(vector: Vector) { }

}