package ar.edu.unq.eperdemic.controller.dto

import ar.edu.unq.eperdemic.modelo.vector.Vector
import ar.edu.unq.eperdemic.controller.dto.UbicacionDTO
import ar.edu.unq.eperdemic.controller.dto.EspecieDTO
import ar.edu.unq.eperdemic.modelo.Especie

class VectorDTO ( val id: Long?,
                  val nombre: String?,
                  val ubicacion: UbicacionDTO,
                  val tipo: String,
                  val especies: MutableSet<EspecieDTO>) {

    companion object {
        fun desdeModelo(vector: Vector) =
            VectorDTO(
                id           = vector.getId(),
                nombre       = vector.nombre,
                ubicacion    = UbicacionDTO.desdeModelo(vector.ubicacion!!),
                tipo         = vector.getTipo().name(),
                especies     = vector.especies
                    .map { especie -> EspecieDTO.desdeModelo(especie) }
                    .toCollection(HashSet())
            )
    }

    fun aModelo(): Vector {
        val vector = Vector()
        val ubicacionDTO = UbicacionDTO(this.ubicacion.id, this.ubicacion.nombre)
        // vector.id  = this.id  // se deberia hacer un setter o no se deberia colocar, ya que se genera automaticamente en el back?
        vector.nombre = this.nombre
        vector.ubicacion = UbicacionDTO.aModelo(this.ubicacion)
        // vector.setTipo(this.tipo) //hacer una funcion que pase un string a un TipoDeVector? se debe hacer un setter?
        vector.especies = this.especies
            .map { especie -> EspecieDTO.aModelo(especie) }
            .toCollection(HashSet())
        return vector
    }

    fun aModelo(especie: Especie): Vector {
        val vector = aModelo()
        // vector.id  = this.id  // se deberia hacer un setter o no se deberia colocar, ya que se genera automaticamente en el back?
        vector.nombre = this.nombre
        vector.ubicacion = UbicacionDTO.(this.ubicacion)
        // vector.setTipo(this.tipo) //hacer una funcion que pase un string a un TipoDeVector? se debe hacer un setter?
        vector.especies = this.especies?.
             map { especieDTO -> especieDTO.aModelo(vector) }?.
             toCollection(HashSet()) ?:
             HashSet()
        return vector
    }

}