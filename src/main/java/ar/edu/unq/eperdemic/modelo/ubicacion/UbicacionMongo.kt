package ar.edu.unq.eperdemic.modelo.ubicacion

import ar.edu.unq.eperdemic.controller.dto.UbicacionDTO
import ar.edu.unq.eperdemic.exceptions.ErrorNombre
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed
import org.springframework.data.mongodb.core.mapping.Document
import javax.persistence.Id
import kotlin.math.*

@Document("ubicacionMongo")
class UbicacionMongo() {

    @Id
    private var id: String? = null

    private var nombre: String? = null

    @GeoSpatialIndexed
    var coordenada: GeoJsonPoint? = null

    constructor(nombre: String, coordenada: GeoJsonPoint) : this() {
        if(nombre.isBlank()){
            throw ErrorNombre("El nombre no puede ser vacio.")
        }
        this.nombre = nombre
        this.coordenada = coordenada
    }

    fun getCordenada() : GeoJsonPoint {
        return this.coordenada!!
    }
    fun getNombre() : String {
        return this.nombre!!
    }

    fun setId(id: String) {
        this.id = id
    }

    fun setNombre(nombre: String) {
        this.nombre = nombre
    }

    fun aDTO(): UbicacionDTO {
        return UbicacionDTO(this.id!!.toLong(), this.nombre, this.coordenada)
    }

    private fun degreesToRadians(degrees: Double): Double {
        return degrees * PI / 180.0
    }

    fun calcularDistancia(otraUbicacion: UbicacionMongo): Double {

        val radioTierra = 6371 // Radio de la Tierra en kilómetros

        val latitud1 = coordenada!!.y
        val longitud1 = coordenada!!.x
        val latitud2 = otraUbicacion.coordenada!!.y
        val longitud2 = otraUbicacion.coordenada!!.x

        val dLatitud = degreesToRadians(latitud2 - latitud1)
        val dLongitud = degreesToRadians(longitud2 - longitud1)

        val a = sin(dLatitud / 2) * sin(dLatitud / 2) +
                cos(degreesToRadians(latitud1)) * cos(degreesToRadians(latitud2)) *
                sin(dLongitud / 2) * sin(dLongitud / 2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        return radioTierra * c
    }

    fun estaAMasDe100Km(ubicacionDestino: UbicacionMongo): Boolean {
        return calcularDistancia(ubicacionDestino) > 100
    }

}
