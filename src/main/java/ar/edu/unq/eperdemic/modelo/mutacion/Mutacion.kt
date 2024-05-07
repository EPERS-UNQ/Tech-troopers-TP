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

    @ManyToMany(mappedBy = "posibles_mutaciones",cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var especies_mutadas: MutableSet<Especie> = HashSet()

    abstract fun atributo() : Any

    abstract fun habilitaContagiarA(vector: TipoVector) : Boolean

    abstract fun potencia() : Int

    abstract fun eliminarEspeciesInferiores(vector : Vector)

    fun getId() : Long? {
        return this.id
    }

    fun mutarLaEspecie(especieMutada : Especie) {
        especies_mutadas.add(especieMutada)
        especieMutada.agregarNuevaMutacionPosible(this)
    }

}
