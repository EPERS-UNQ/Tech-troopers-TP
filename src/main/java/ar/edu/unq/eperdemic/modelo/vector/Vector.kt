package ar.edu.unq.eperdemic.modelo.vector

import javax.persistence.*
import ar.edu.unq.eperdemic.modelo.*
import ar.edu.unq.eperdemic.modelo.RandomGenerator.RandomGenerator

@Entity
open class Vector() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Long? = null

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private lateinit var tipo: TipoVector

    var nombre: String? = null

    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var especies: MutableSet<Especie> = HashSet()

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

    fun getTipo(): TipoVector {
        return this.tipo
    }

    fun setId(idNew: Long){
        this.id = idNew
    }

    fun estaInfectado(): Boolean{
        return this.especies.isNotEmpty()
    }

    open fun infectar(especie: Especie) {
        this.especies.add(especie)
        especie.agregarVector(this)
    }

    fun contargiarA(vector: Vector){
        if (this.tipo.puedeContagiarA(vector.getTipo())){
            this.enfermedadesDelVector().map{ this.intentarInfectar(vector, it) }
        }
    }

    private fun intentarInfectar(vector: Vector, especie: Especie){
        val random = RandomGenerator.getInstance()

        val porcentajeDeContagioExitoso = random.getNumeroRandom() + especie.capacidadDeContagioPara(vector.getTipo())

        if (random.porcentajeExistoso(porcentajeDeContagioExitoso)) {
            vector.infectar(especie)
        }
    }

    fun enfermedadesDelVector(): List<Especie> {
        return this.especies.toList()
    }

    fun estaInfectadoCon(especie: Especie): Boolean {
        return especies.contains(especie)
    }
}