package ar.edu.unq.eperdemic.modelo.vector

import ar.edu.unq.eperdemic.exceptions.ErrorNombre
import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.ubicacion.UbicacionJpa
import org.springframework.data.mongodb.core.mapping.Document
import javax.persistence.Id

@Document("vectorElastic")
class VectorElastic() {

    @Id
    private var id: String? = null

    var nombre: String? = null

    var ubicacion: UbicacionJpa? = null

    lateinit var tipo: TipoVector

    var especies: MutableSet<Especie> = HashSet()

    var registroDeMovimiento: MutableSet<UbicacionJpa> = HashSet()

    constructor(nombre: String, ubicacion: UbicacionJpa, tipoVector: TipoVector):this() {
        if(nombre.isBlank()){
            throw ErrorNombre("El nombre del vector no puede estar vacio.")
        }
        this.nombre = nombre
        this.ubicacion = ubicacion
        this.tipo = tipoVector
    }

    fun agregarUbicacion(ubicacion: UbicacionJpa) {
        this.registroDeMovimiento.add(ubicacion)
    }

}
