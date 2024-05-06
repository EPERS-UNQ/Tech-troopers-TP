package ar.edu.unq.eperdemic.modelo.mutacion

import ar.edu.unq.eperdemic.modelo.vector.TipoVector
import ar.edu.unq.eperdemic.modelo.vector.Vector
import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
abstract class Mutacion() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected var id: Long? = null

    abstract fun atributo() : Any

    abstract fun habilitaContagiarA(vector: TipoVector) : Boolean

    abstract fun potencia() : Int

    abstract fun eleminarEspeciesInferiores(vector : Vector)

}
