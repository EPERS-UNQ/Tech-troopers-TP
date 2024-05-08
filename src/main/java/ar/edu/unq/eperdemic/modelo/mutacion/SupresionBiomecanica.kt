package ar.edu.unq.eperdemic.modelo.mutacion

import ar.edu.unq.eperdemic.modelo.vector.TipoVector
import ar.edu.unq.eperdemic.modelo.vector.Vector
import javax.persistence.Entity

@Entity
class SupresionBiomecanica() : Mutacion(){

    var potencia : Int = 0

    constructor(potenciaDeSupresion : Int) : this() { // AGREGAR RESTRICCIÓN DEL 1 AL 100.
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

    override fun eliminarEspeciesInferiores(vector : Vector) {

        val especies = vector.enfermedadesDelVector()
        for (e in especies) {
            if (e.defensaDeEspecie() < potencia &&
                !e.tieneLaMutacion(this))
                vector.eliminarEspecie(e)
        }

    }

}