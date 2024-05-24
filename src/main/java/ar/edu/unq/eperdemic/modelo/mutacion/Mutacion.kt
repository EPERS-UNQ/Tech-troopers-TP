package ar.edu.unq.eperdemic.modelo.mutacion

import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.vector.TipoVector
import ar.edu.unq.eperdemic.modelo.vector.Vector
import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
abstract class Mutacion() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Long? = null

    open fun atributo() : Any { return 0 }

    open fun habilitaContagiarA(vector: TipoVector) : Boolean { return false }

    open fun potencia() : Int { return 0 }

    open fun eliminarEspeciesInferiores(vector : Vector){}

    fun setId(id: Long) {
        this.id = id
    }

    fun getId() : Long? {
        return this.id
    }

    fun mutarLaEspecie(especieMutada : Especie) {
        especieMutada.agregarNuevaMutacionPosible(this)
    }

}
