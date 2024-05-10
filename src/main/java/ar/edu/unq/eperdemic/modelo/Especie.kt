package ar.edu.unq.eperdemic.modelo

import ar.edu.unq.eperdemic.controller.dto.EspecieDTO
import ar.edu.unq.eperdemic.exceptions.ErrorNombre
import ar.edu.unq.eperdemic.modelo.mutacion.Mutacion
import ar.edu.unq.eperdemic.modelo.vector.TipoVector
import ar.edu.unq.eperdemic.modelo.vector.Vector
import javax.persistence.*
import kotlin.jvm.Transient


@Entity
class Especie() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Long? = null
    @Column(unique = true)
    var nombre: String? = null

    @Column(nullable = false)
    var paisDeOrigen: String? = null

    @ManyToMany(mappedBy = "especies", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var vectores: MutableSet<Vector> = HashSet()

    @ManyToOne
    var patogeno: Patogeno? = null

    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var posibles_mutaciones: MutableSet<Mutacion> = HashSet()


    constructor( nombre: String, patogeno: Patogeno, paisDeOrigen: String ) : this() {
        if (nombre.isBlank()){
            throw ErrorNombre("El nombre de la especie no puede ser vacio.")
        }
        if (paisDeOrigen.isBlank()){
            throw ErrorNombre("El nombre del pais no puede ser vacio.")
        }
        this.nombre = nombre
        this.patogeno = patogeno
        this.paisDeOrigen = paisDeOrigen
    }

    fun getId(): Long? {
        return this.id
    }
    fun setId(nuevoId: Long?) {
        this.id = nuevoId
    }

    fun agregarVector(vector: Vector) {
        vectores.add(vector)
    }

    fun capacidadDeContagioPara(tipoDeVector: TipoVector): Int{
        return when (tipoDeVector) {
            TipoVector.HUMANO -> patogeno!!.cap_contagio_humano
            TipoVector.ANIMAL -> patogeno!!.cap_contagio_animal
            TipoVector.INSECTO -> patogeno!!.cap_contagio_insecto
        }
    }

    fun nombrePatogeno(): String {
        return this.patogeno!!.tipo!!
    }

    fun aDTO(): EspecieDTO? {
        return EspecieDTO(this.getId(), this.nombre, this.paisDeOrigen, this.patogeno!!.aDTO())
    }

    fun defensaDeEspecie(): Int {
        return patogeno!!.defensa
    }

    fun capacidadDeBiomecanizacion(): Int {
        return patogeno!!.cap_de_biomecanizacion
    }

    fun agregarNuevaMutacionPosible(mutacion : Mutacion) {
        this.posibles_mutaciones.add(mutacion)
    }

    fun cantidadDeMutaciones(): Int {
        return this.posibles_mutaciones.size
    }

    fun tieneLaMutacion(mutacion: Mutacion) : Boolean {
        val id = mutacion.getId()
        val resultado = posibles_mutaciones.any { it.getId() == id }
        return resultado
    }

}