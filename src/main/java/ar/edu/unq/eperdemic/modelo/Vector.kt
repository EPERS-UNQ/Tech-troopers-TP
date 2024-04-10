package ar.edu.unq.eperdemic.modelo

import net.bytebuddy.implementation.bind.annotation.Empty
import javax.persistence.*
import java.util.Objects

@Entity
class Vector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    var tipo:   String? = null //Cambiar -> Template Method -> Plantilla general y 3 particulares para cada vector de como contagia.
    var nombre: String? = null

    @ManyToOne
    var ubicacion: Ubicacion? = null

}