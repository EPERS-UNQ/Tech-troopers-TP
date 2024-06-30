package ar.edu.unq.eperdemic.modelo.vector

import ar.edu.unq.eperdemic.controller.dto.VectorElasticDTO
import ar.edu.unq.eperdemic.exceptions.ErrorNombre
import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.mutacion.Mutacion
import ar.edu.unq.eperdemic.modelo.ubicacion.UbicacionElastic
import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document



@Document(indexName = "#{@indexNameProvider.indexName}")
open class VectorElastic() {

    @Id
    var id: String? = null
    var nombre: String? = null
    private lateinit var tipo: TipoVector
    var especies: MutableSet<Especie> = HashSet()
    var estaInfectado: Boolean = false
    var mutaciones: MutableSet<Mutacion> = HashSet()
    private lateinit var ubicacionActual: UbicacionElastic

    constructor(nombre: String, ubicacion: UbicacionElastic, tipoVector: TipoVector):this() {
        if(nombre.isBlank()){
            throw ErrorNombre("El nombre del vector no puede estar vacio.")
        }
        this.nombre = nombre
        this.ubicacionActual = ubicacion
        this.tipo = tipoVector
    }

    fun aDTO(): VectorElasticDTO {
        return VectorElasticDTO(this.id, this.nombre, this.tipo.toString(), null, this.ubicacionActual.aDTO())
    }

}
