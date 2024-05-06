package ar.edu.unq.eperdemic.modelo.mutacion

import ar.edu.unq.eperdemic.modelo.vector.TipoVector
import ar.edu.unq.eperdemic.modelo.vector.Vector

class SupresionBiomecanica(val potencia : Int) : Mutacion(){ //AGREGAR RESTRICCIÓN DEL 1 AL 100.

    override fun eliminarEspeciesInferiores(vector : Vector) {

        val especies = vector.especies

        for (e in especies) {
            if (e.defensaDeEspecie() < potencia)
                vector.eleminarEspecie(e)
        }

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

}