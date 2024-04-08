package ar.edu.unq.eperdemic.modelo

import ar.edu.unq.eperdemic.modelo.Patogeno
import ar.edu.unq.eperdemic.modelo.Ubicacion
import javax.persistence.*
@Entity
class Vector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    var tipo: String? = null //Cambiar -> Template Method -> Plantilla general y 3 particulares para cada vector de como contagia.

    @ManyToMany(mappedBy = "vectoresInfectados", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var patogenos: MutableSet<Patogeno> = HashSet()

    @ManyToOne
    var ubicacion: Ubicacion? = null

    fun infectar(){
    // COMPLETAR
    }

    fun enfermedad(){
    // COMPLETAR
    }
}