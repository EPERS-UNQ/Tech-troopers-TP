package ar.edu.unq.eperdemic.controller.dto.dtoCreacion

import ar.edu.unq.eperdemic.modelo.ubicacion.UbicacionGlobal
import org.springframework.data.mongodb.core.geo.GeoJsonPoint

class UbicacionCreacionDTO( val nombre: String,
                            val coordenada: GeoJsonPoint
) {

    fun aModelo(): UbicacionGlobal {
        return UbicacionGlobal(this.nombre, this.coordenada)
    }

}