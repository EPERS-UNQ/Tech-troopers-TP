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

    abstract fun atributo() : Any

    abstract fun habilitaContagiarA(vector: TipoVector) : Boolean

    abstract fun potencia() : Int

    abstract fun eliminarEspeciesInferiores(vector : Vector)

    fun getId() : Long? {
        return this.id
    }

    fun mutarLaEspecie(especieMutada : Especie) {
        especieMutada.agregarNuevaMutacionPosible(this)
    }

}
