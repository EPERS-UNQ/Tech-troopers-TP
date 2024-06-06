package ar.edu.unq.eperdemic.controller.dto

import ar.edu.unq.eperdemic.modelo.UbicacionGlobal
import ar.edu.unq.eperdemic.modelo.UbicacionMongo
import org.springframework.data.mongodb.core.geo.GeoJsonPoint

class UbicacionDTO ( val id: Long?,
                     val nombre: String?,
                     var coordenada: GeoJsonPoint? ) {

    fun aModelo(): UbicacionGlobal {
        val ubicacion = UbicacionGlobal()
        ubicacion.setId(this.id!!)
        ubicacion.setNombre(this.nombre!!)
        return ubicacion
    }

    fun aModeloMongo() : UbicacionMongo {
        val ubicacion = UbicacionMongo()
        ubicacion.setId(this.id.toString())
        ubicacion.setNombre(this.nombre!!)
        return ubicacion
    }


}