package ar.edu.unq.eperdemic.modelo

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id


@Entity
class Especie() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    var nombre: String? = null

    @Column(nullable = false)
    var patogeno: Patogeno? = null
    var paisDeOrigen: String? = null

    constructor( nombre: String, patogeno: Patogeno, paisDeOrigen: String ) : this() {
        this.nombre = nombre
        this.patogeno = patogeno
        this.paisDeOrigen = paisDeOrigen
    }
}