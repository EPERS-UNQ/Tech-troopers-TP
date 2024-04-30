package ar.edu.unq.eperdemic.modelo

enum class Direccion(private var direccion: String) {

    ASCENDENTE("ASC"),
    DESCENDENTE("DESC");

    fun getExp() : String {
        return this.direccion
    }
}