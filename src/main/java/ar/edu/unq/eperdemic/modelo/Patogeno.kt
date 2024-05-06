package ar.edu.unq.eperdemic.modelo

import ar.edu.unq.eperdemic.controller.dto.PatogenoDTO
import ar.edu.unq.eperdemic.exceptions.ErrorNombre
import ar.edu.unq.eperdemic.exceptions.LimiteDeCampoErroneo
import javax.persistence.*

@Entity
class Patogeno() {
    constructor(
        tipo: String, contagioHumanos: Int,
        contagioAnimales: Int, contagioInsectos: Int,
        defensa: Int,
        capacidadDeBiomecanizacion: Int,
    ) : this() {
        this.tipo = tipo
        if (!(esCampoValido(contagioHumanos) && esCampoValido(contagioAnimales)
                    && esCampoValido(contagioInsectos) && esCampoValido(defensa)
                    && esCampoValido(capacidadDeBiomecanizacion))
        ) {
            throw LimiteDeCampoErroneo()
        }
        if (tipo.isBlank()) {
            throw ErrorNombre("El nombre del tipo del patogeno no puede ser vacio.")
        }
        this.cap_contagio_humano = contagioHumanos
        this.cap_contagio_animal = contagioAnimales
        this.cap_contagio_insecto = contagioInsectos
        this.defensa = defensa
        this.cap_de_biomecanizacion = capacidadDeBiomecanizacion
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    var id: Long? = null

    var cantidadDeEspecies: Int = 0

    var tipo: String? = null

    @Column(columnDefinition = "INT CHECK (cap_contagio_humano <= 100)")
    var cap_contagio_humano: Int = 0

    @Column(columnDefinition = "INT CHECK (cap_contagio_animal <= 100)")
    var cap_contagio_animal: Int = 0

    @Column(columnDefinition = "INT CHECK (cap_contagio_insecto <= 100)")
    var cap_contagio_insecto: Int = 0

    @Column(columnDefinition = "INT CHECK (defensa <= 100)")
    var defensa: Int = 0

    @Column(columnDefinition = "INT CHECK (cap_de_biomecanizacion <= 100)")
    var cap_de_biomecanizacion: Int = 0

    override fun toString(): String {
        return tipo!!
    }

    fun getId(): Long {
        return this.id!!
    }

    fun setId(idNew: Long) {
        this.id = idNew
    }

    fun crearEspecie(nombreEspecie: String, paisDeOrigen: String): Especie {
        var nuevaEspecie = Especie(nombreEspecie, this, paisDeOrigen)
        cantidadDeEspecies++
        return nuevaEspecie
    }

    private fun esCampoValido(campoAComprobar : Int) : Boolean {
        return (campoAComprobar > 0) && (campoAComprobar < 101)
    }


    fun aDTO(): PatogenoDTO? {
        return PatogenoDTO(this.getId(), this.tipo, this.cap_contagio_humano, this.cap_contagio_animal,
                           this.cap_contagio_insecto, this.defensa, this.cap_de_biomecanizacion)
    }

}