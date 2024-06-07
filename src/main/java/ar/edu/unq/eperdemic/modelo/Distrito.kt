package ar.edu.unq.eperdemic.modelo

import ar.edu.unq.eperdemic.controller.dto.DistritoDTO
import ar.edu.unq.eperdemic.exceptions.ErrorCantidadDeCoordenadasMinimas
import ar.edu.unq.eperdemic.exceptions.ErrorNombre
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.annotation.Id

@Document
class Distrito(nombre: String, coordenadas: HashSet<Coordenada>) {

    @Id
    private var id: String? = null

    private var nombre: String? = nombre
    private var coordenadas: HashSet<Coordenada> = coordenadas
    var ubicaciones: MutableSet<UbicacionMongo> = HashSet()

    fun aDTO() : DistritoDTO {
        val coordenadasDTO = this.coordenadas.map { coordenada -> coordenada.aDTO() }.toMutableSet()
        return DistritoDTO(this.id, this.nombre!!, coordenadasDTO)
    }

    fun getNombre(): String {
        return nombre!!
    }

    fun getCoordenadas(): HashSet<Coordenada> {
        return coordenadas
    }

    init {
        if(nombre.isBlank()){
            throw ErrorNombre("El nombre del Distrito no puede ser vacio.")
        }
        if(coordenadas.size < 3) {
            throw ErrorCantidadDeCoordenadasMinimas()
        }
    }

}
