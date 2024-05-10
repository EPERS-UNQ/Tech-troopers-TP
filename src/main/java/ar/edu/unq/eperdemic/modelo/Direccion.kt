package ar.edu.unq.eperdemic.modelo

import org.springframework.data.domain.Sort

enum class Direccion(private var direccion: Sort.Direction, private var direccionS: String) {

    ASCENDENTE(Sort.Direction.ASC, "asc"),
    DESCENDENTE(Sort.Direction.DESC, "desc");

    fun getExp() : Sort.Direction {
        return this.direccion
    }

    fun getString() : String {
        return this.direccionS
    }
}