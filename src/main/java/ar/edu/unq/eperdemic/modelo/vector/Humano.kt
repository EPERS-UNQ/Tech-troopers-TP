package ar.edu.unq.eperdemic.modelo.vector

import ar.edu.unq.eperdemic.modelo.Ubicacion
import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
class Humano: Vector {

    constructor(nombre: String, ubicacion: Ubicacion) : super(nombre, ubicacion) {
    }
    override fun infectar(vectorId: Long, especieId: Long) {
        TODO("Not yet implemented")
    }
}