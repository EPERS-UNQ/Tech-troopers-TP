package ar.edu.unq.eperdemic.modelo

import javax.persistence.*
@Entity
class Vector() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    var tipo:   String? = null //Cambiar -> Template Method -> Plantilla general y 3 particulares para cada vector de como contagia.
    var nombre: String? = null

    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var patogenos: MutableSet<Patogeno> = HashSet()

    @ManyToOne
    var ubicacion: Ubicacion? = null

    constructor(nombre: String):this() {
        this.nombre = nombre
    }

    fun infectar(){
    // COMPLETAR
    }

    fun enfermedad(){
    // COMPLETAR
    }
}