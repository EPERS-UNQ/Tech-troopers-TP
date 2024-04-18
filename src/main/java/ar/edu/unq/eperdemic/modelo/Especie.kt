package ar.edu.unq.eperdemic.modelo

import ar.edu.unq.eperdemic.modelo.vector.TipoVector
import ar.edu.unq.eperdemic.modelo.vector.Vector
import javax.persistence.*


@Entity
class Especie() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    var nombre: String? = null

    @Column(nullable = false)
    var paisDeOrigen: String? = null

    @ManyToMany(mappedBy = "especies", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var vectores:  MutableSet<Vector> = HashSet()

    @ManyToOne
    var patogeno: Patogeno? = null

    constructor( nombre: String, patogeno: Patogeno, paisDeOrigen: String ) : this() {
        this.nombre = nombre
        this.patogeno = patogeno
        this.paisDeOrigen = paisDeOrigen
    }

    fun agregarVector(vector: Vector) {
        vectores.add(vector)
    }

    fun capacidadDeContagioPara(tipoDeVector: TipoVector): Int{
        return when (tipoDeVector) {
            TipoVector.HUMANO -> patogeno!!.capContagioHumano
            TipoVector.ANIMAL -> patogeno!!.capContagioAnimal
            TipoVector.INSECTO -> patogeno!!.capContagioInsecto
        }
    }
}