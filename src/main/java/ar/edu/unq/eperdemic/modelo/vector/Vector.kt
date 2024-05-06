package ar.edu.unq.eperdemic.modelo.vector

import ar.edu.unq.eperdemic.controller.dto.EspecieDTO
import ar.edu.unq.eperdemic.controller.dto.VectorDTO
import ar.edu.unq.eperdemic.exceptions.ErrorNombre
import javax.persistence.*
import ar.edu.unq.eperdemic.modelo.*
import ar.edu.unq.eperdemic.modelo.RandomGenerator.RandomGenerator
import ar.edu.unq.eperdemic.modelo.mutacion.Mutacion

@Entity
open class Vector() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private lateinit var tipo: TipoVector

    var nombre: String? = null

    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var especies: MutableSet<Especie> = HashSet()

    @ManyToOne
    var ubicacion: Ubicacion? = null

    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var mutaciones: MutableSet<Mutacion> = HashSet()

    constructor(nombre: String, ubicacion: Ubicacion, tipoVector: TipoVector):this() {
        if(nombre.isBlank()){
            throw ErrorNombre("El nombre del vector no puede estar vacio.")
        }
        this.nombre = nombre
        this.ubicacion = ubicacion
        this.tipo = tipoVector
    }

    fun getId(): Long {
        return this.id!!
    }
    fun setId(idNew: Long){
        this.id = idNew
    }

    fun getTipo(): TipoVector {
        return this.tipo
    }

    fun setTipo(nuevoTipo: TipoVector) {
        this.tipo = nuevoTipo
    }

    fun estaInfectado(): Boolean{
        return this.especies.isNotEmpty()
    }

    open fun infectar(especie: Especie) {
        this.especies.add(especie)
        especie.agregarVector(this)
    }

    fun contargiarA(vector: Vector){
        if (this.tipo.puedeContagiarA(vector.getTipo()) ||
            mutaciones.any { it.habilitaContagiarA(vector.getTipo()) }){
            this.enfermedadesDelVector().map{ this.intentarInfectar(vector, it) }
        }
    }

    private fun intentarInfectar(vector: Vector, especie: Especie){
        val random = RandomGenerator.getInstance()

        val porcentajeDeContagioExitoso = random.getNumeroRandom() + especie.capacidadDeContagioPara(vector.getTipo())
        val porcentajeDeMutacionExitoso = random.getNumeroRandom() + especie.capacidadDeBiomecanizacion()


        if (vector.defiendeContra(especie.defensaDeEspecie()) &&
            random.porcentajeExistoso(porcentajeDeContagioExitoso)) {
            vector.infectar(especie)
            if (random.porcentajeExistoso(porcentajeDeMutacionExitoso)) {
                this.mutarConMutacionRandom(especie.posiblesMutaciones.toList()) // Refactor del nombre????
            }
        }
    }

    private fun mutarConMutacionRandom(mutacionesDeEspecie: List<Mutacion>) {
        if(mutacionesDeEspecie.isNotEmpty()) {
            val random = RandomGenerator.getInstance()
            val mutacionElegida = random.getElementoRandomEnLista(mutacionesDeEspecie)
            this.mutaciones.add(mutacionElegida)
            mutacionElegida.eliminarEspeciesInferiores(this)
        }
    }

    private fun defiendeContra(defensaDeLaEspecie : Int): Boolean {
        return this.defensaDeSupresionBiomecanica() < defensaDeLaEspecie
    }

    private fun defensaDeSupresionBiomecanica(): Int {
        return mutaciones.maxOf { it.potencia() }
    }

    fun enfermedadesDelVector(): List<Especie> {
        return this.especies.toList()
    }

    fun estaInfectadoCon(especie: Especie): Boolean {
        return especies.contains(especie)
    }

    fun aDTO(): VectorDTO? {
        var especiesDTO : List<EspecieDTO> = especies.map { especie -> especie!!.aDTO()!! }
        return VectorDTO(this.getId(), this.nombre, this.ubicacion!!.aDTO()!!, this.getTipo().toString(), especiesDTO.toMutableSet())
    }

    fun eleminarEspecie(especie : Especie) {
        especies.remove(especie)
    }

}