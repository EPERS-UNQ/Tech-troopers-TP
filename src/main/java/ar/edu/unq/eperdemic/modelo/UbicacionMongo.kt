package ar.edu.unq.eperdemic.modelo

import ar.edu.unq.eperdemic.exceptions.ErrorNombre
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import javax.persistence.Id

@Document("ubicacionMongo")
class UbicacionMongo() {

    @Id
    private var id: String? = null

    //@Indexed(unique = true)
    private var nombre: String? = null

    private var coordenada: Coordenada? = null

    constructor(nombre: String, coordenada: Coordenada) : this() {
        if(nombre.isBlank()){
            throw ErrorNombre("El nombre no puede ser vacio.")
        }
        this.nombre = nombre
        this.coordenada = coordenada
    }

    fun getCordenada() : Coordenada {
        return this.coordenada!!
    }
    fun getNombre() : String {
        return this.nombre!!
    }
}
