package ar.edu.unq.eperdemic.modelo.vector

import javax.persistence.*
import ar.edu.unq.eperdemic.modelo.*
@Entity
open class Vector() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Long? = null

    @Column(nullable = false)
    @Enumerated(EnumType.STRING) // Se persiste como String.
    lateinit var tipo: TipoVector

    var nombre: String? = null

    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var especies: MutableSet<Especie> = HashSet() // Set para que las especies no sean repetidas.

    @ManyToOne
    var ubicacion: Ubicacion? = null

    constructor(nombre: String, ubicacion: Ubicacion, tipoVector: TipoVector):this() {
        this.nombre = nombre
        this.ubicacion = ubicacion
        this.tipo = tipoVector
    }

    fun getId(): Long? {
        return this.id!!
    }

    fun estaInfectado(): Boolean{
        return this.especies.isNotEmpty()
    }

    open fun infectar(especie: Especie) {
        this.especies.add(especie)
        especie.agregarVector(this)
    }

    //Nota: Si no puede contagiar no hace nada.
    fun contargiarA(vector: Vector){
        if (this.tipo.puedeContagiarA(vector.tipo)){
            this.enfermedadesDelVector().map{ this.intentarInfectar(vector, it) }
        }
    }

    fun intentarInfectar(vector: Vector, especie: Especie){
        val random = RandomGenerator()
        val porcentajeDeContagioExitoso = random.getNumberoRandom() + especie.capacidadDeContagioPara(vector.tipo)
        if (random.porcentajeExistoso(porcentajeDeContagioExitoso)) {
            vector.infectar(especie)
        }
    }

    fun enfermedadesDelVector(): List<Especie>{
        return this.especies.toList()
    }

    fun estaInfectadoCon(especie: Especie): Boolean {
        return especies.contains(especie)
    }
}