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
    var ubicacion: UbicacionJpa? = null

    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var mutaciones: MutableSet<Mutacion> = HashSet()

    constructor(nombre: String, ubicacion: UbicacionJpa, tipoVector: TipoVector):this() {
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
        if (!this.defiendeContra(especie)){
            this.especies.add(especie)
            especie.agregarVector(this)
        }
    }

    fun contargiarA(vector: Vector){
        if (this.puedeContagiarA(vector)){
            this.enfermedadesDelVector().map{ this.intentarInfectar(vector, it) }
        }
    }

    private fun intentarInfectar(vector: Vector, especie: Especie){
        val random = RandomGenerator.getInstance()

        val porcentajeDeContagioExitoso = random.getNumeroRandom() + especie.capacidadDeContagioPara(vector.getTipo())
        val porcentajeDeMutacionExitoso = random.getNumeroRandom() + especie.capacidadDeBiomecanizacion()
        val mutacionQueHabilita = this.mutaciones.find { it.habilitaContagiarA(vector.getTipo()) }

        if ((!vector.defiendeContra(especie) && random.porcentajeExistoso(porcentajeDeContagioExitoso)) &&
            (mutacionQueHabilita == null || especie.posibles_mutaciones.contains(mutacionQueHabilita))) {
            vector.infectar(especie)
            if (random.porcentajeAltExistoso(porcentajeDeMutacionExitoso)) {
                this.mutarConMutacionRandom(especie.posibles_mutaciones.toList())
            }
        }
    }

    fun puedeContagiarA(vector: Vector) : Boolean {
        return this.tipo.puedeContagiarA(vector.getTipo()) ||
                this.mutaciones.any { it.habilitaContagiarA(vector.getTipo()) }
    }

    fun mutarConMutacionRandom(mutacionesDeEspecie: List<Mutacion>) {
        if(mutacionesDeEspecie.isNotEmpty()) {
            val random = RandomGenerator.getInstance()
            val mutacionElegida = random.getElementoRandomEnLista(mutacionesDeEspecie)
            mutacionElegida.eliminarEspeciesInferiores(this)
            this.mutaciones.add(mutacionElegida)
        }
    }

    private fun defiendeContra(especie : Especie): Boolean {
        return this.defensaDeSupresionBiomecanica() >= especie.defensaDeEspecie()
    }

    private fun defensaDeSupresionBiomecanica(): Int {
        if (mutaciones.isEmpty()) {
            return 0
        }
        return mutaciones.maxOf { it.potencia() }
    }

    fun enfermedadesDelVector(): List<Especie> {
        return this.especies.toList()
    }

    fun estaInfectadoCon(especie: Especie): Boolean {
        return especies.any { it.getId() == especie.getId()}
    }

    fun aDTO(): VectorDTO? {
        val especiesDTO : List<EspecieDTO> = especies.map { especie -> especie!!.aDTO()!! }
        return VectorDTO(this.getId(), this.nombre, this.ubicacion!!.aDTO()!!, this.getTipo().toString(), especiesDTO.toMutableSet())
    }

    fun eliminarEspecie(especie : Especie) : Boolean {
        return especies.remove(especie)
    }

    fun estaMutado(): Boolean {
        return mutaciones.isNotEmpty()
    }

    fun estaMutadoCon(unaMutacion : Mutacion): Boolean{
        return mutaciones.any { it.getId() == unaMutacion.getId() }
    }

    fun nombreDeUbicacionActual(): String {
        return ubicacion!!.getNombre()!!
    }

}