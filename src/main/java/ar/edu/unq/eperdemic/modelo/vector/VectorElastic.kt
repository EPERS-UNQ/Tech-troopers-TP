package ar.edu.unq.eperdemic.modelo.vector

import ar.edu.unq.eperdemic.controller.dto.VectorElasticDTO
import ar.edu.unq.eperdemic.exceptions.ErrorNombre
import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.mutacion.Mutacion
import ar.edu.unq.eperdemic.modelo.ubicacion.UbicacionJpa
import ar.edu.unq.eperdemic.modelo.ubicacion.UbicacionMongo
import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.mongodb.core.geo.GeoJsonPoint


@Document(indexName = "#{@indexNameProvider.indexName}")
open class VectorElastic() {

    @Id
    var id: String? = null
    var nombre: String? = null
    private lateinit var tipo: TipoVector
    var especies: MutableSet<Especie> = HashSet()
    var estaInfectado: Boolean = false
    var ubicaciones: MutableList<UbicacionMongo> = mutableListOf() // REVISAR EL TIPO DE UBICACION KIBANA
    var mutaciones: MutableSet<Mutacion> = HashSet()
    var ubicacionActual: UbicacionMongo = UbicacionMongo("Default", GeoJsonPoint( -58.277798, -34.740635))

    constructor(nombre: String, ubicacion: UbicacionMongo, tipoVector: TipoVector):this() {
        if(nombre.isBlank()){
            throw ErrorNombre("El nombre del vector no puede estar vacio.")
        }
        this.nombre = nombre
        this.ubicaciones.add(ubicacion)
        this.tipo = tipoVector
    }

    fun aDTO(): VectorElasticDTO {
        val ubicacionesDTO = this.ubicaciones.map { ubicacion -> ubicacion.aDTO() }
        val especiesDTO = this.especies.map { especie -> especie.aDTO() } // No se usa ver que hacer
        return VectorElasticDTO(this.id, this.nombre, ubicacionesDTO.toMutableList(), this.tipo.toString(), null) //this.ubicacionActual.aDTO()
    }

}
