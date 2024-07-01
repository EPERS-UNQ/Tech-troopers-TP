package ar.edu.unq.eperdemic.modelo.vector

import ar.edu.unq.eperdemic.controller.dto.VectorElasticDTO
import ar.edu.unq.eperdemic.exceptions.ErrorNombre
import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.mutacion.Mutacion
import ar.edu.unq.eperdemic.modelo.ubicacion.UbicacionElastic
import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType


@Document(indexName = "vectorelastic")
open class VectorElastic() {

    @Id
    var id: String? = null
    @Field(type = FieldType.Text)
    var nombre: String? = null
    @Field(type = FieldType.Text)
    private lateinit var tipo: TipoVector
    var especies: MutableSet<Especie> = HashSet()
    @Field(type = FieldType.Boolean)
    var estaInfectado: Boolean = false
    var mutaciones: MutableSet<Mutacion> = HashSet()
    @Field(type = FieldType.Nested, includeInParent = true)
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
