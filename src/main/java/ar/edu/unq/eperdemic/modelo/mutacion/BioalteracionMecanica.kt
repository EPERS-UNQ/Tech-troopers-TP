package ar.edu.unq.eperdemic.modelo.mutacion

import ar.edu.unq.eperdemic.modelo.vector.TipoVector
import ar.edu.unq.eperdemic.modelo.vector.Vector

class BioalteracionMecanica(val vector : TipoVector) : Mutacion(){

    override fun habilitaContagiarA(vector: TipoVector) : Boolean {
        return this.vector == vector
    }

    override fun potencia(): Int {
        return 0
    }

    override fun eleminarEspeciesInferiores(vector: Vector) { }

}