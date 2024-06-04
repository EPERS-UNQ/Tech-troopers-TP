package ar.edu.unq.eperdemic.modelo

import ar.edu.unq.eperdemic.exceptions.ErrorNombre

class UbicacionGlobal() {

    private var nombre: String? = null
    private var coordenada: Coordenada? = null

    constructor(nombre: String, latitud: Double, longitud: Double) : this() {
        if(nombre.isBlank()){
            throw ErrorNombre("El nombre no puede ser vacio.")
        }
        this.coordenada = Coordenada(latitud, longitud)
        this.nombre = nombre
    }

    fun getNombre(): String {
        return this.nombre!!
    }

    fun getCoordenada(): Coordenada {
        return this.coordenada!!
    }
}