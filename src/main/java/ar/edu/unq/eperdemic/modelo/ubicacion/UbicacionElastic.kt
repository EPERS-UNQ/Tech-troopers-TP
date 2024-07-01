package ar.edu.unq.eperdemic.modelo.ubicacion

import ar.edu.unq.eperdemic.controller.dto.UbicacionElasticDTO
import ar.edu.unq.eperdemic.modelo.Coordenada
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType
import org.springframework.data.elasticsearch.annotations.GeoPointField

@Document(indexName = "ubicacionelastic")
class UbicacionElastic {

    @Field(type = FieldType.Keyword)
    var nombre: String? = null

    @GeoPointField
    var coordenada: Coordenada? = null

    constructor(nombre: String, coordenada: Coordenada){
        this.nombre = nombre
        this.coordenada = coordenada
    }

    fun aDTO(): UbicacionElasticDTO {
        return UbicacionElasticDTO(this.nombre!!, this.coordenada!!.aDTO())
    }

}