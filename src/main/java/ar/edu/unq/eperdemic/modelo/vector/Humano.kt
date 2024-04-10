package ar.edu.unq.eperdemic.modelo.vector

import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
class Humano: Vector {

    constructor(nombre: String) : super(nombre) {
    }
    override fun infectar(vectorId: Long, especieId: Long) {
        TODO("Not yet implemented")
    }
}