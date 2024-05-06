package ar.edu.unq.eperdemic.modelo.mutacion

import ar.edu.unq.eperdemic.modelo.vector.TipoVector
import ar.edu.unq.eperdemic.modelo.vector.Vector
import javax.persistence.Entity

@Entity
class SupresionBiomecanica() : Mutacion(){

    var potencia : Int = 0

    constructor(potenciaDeSupresion : Int) : this() {
        this.potencia = potenciaDeSupresion
    }

    override fun atributo(): Any {
        return potencia
    }

    override fun habilitaContagiarA(vector: TipoVector): Boolean {
        return false
    }

    override fun potencia(): Int {
        return potencia
    }

    override fun eleminarEspeciesInferiores(vector : Vector) {

        val especies = vector.especies

        for (e in especies) {
            if (e.defensaDeEspecie() < potencia)
                vector.eleminarEspecie(e)
        }

    }

}