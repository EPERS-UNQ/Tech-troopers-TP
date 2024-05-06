package ar.edu.unq.eperdemic.modelo.mutacion

import ar.edu.unq.eperdemic.modelo.vector.TipoVector
import ar.edu.unq.eperdemic.modelo.vector.Vector

class SupresionBiomecanica(val potencia : Int) : Mutacion(){

    override fun eleminarEspeciesInferiores(vector : Vector) {

        val especies = vector.especies

        for (e in especies) {
            if (e.defensaDeEspecie() < potencia)
                vector.eleminarEspecie(e)
        }

    }

    override fun habilitaContagiarA(vector: TipoVector): Boolean {
        return false
    }

    override fun potencia(): Int {
        return potencia
    }

}