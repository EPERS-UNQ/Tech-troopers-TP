package ar.edu.unq.eperdemic.services

import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.Patogeno
import ar.edu.unq.eperdemic.modelo.Direccion

interface PatogenoService {
    fun crear(patogeno: Patogeno): Patogeno
    fun updatear(patogeno: Patogeno)
    fun recuperar(id: Long): Patogeno
    fun recuperarTodos(): List<Patogeno>
    fun agregarEspecie(idDePatogeno: Long, nombreEspecie: String, ubicacionId: Long) : Especie
    //fun especiesDePatogeno(patogenoId: Long, direccion: Direccion, pagina: Int, cantidadPorPagina: Int): List<Especie>
    fun esPandemia(especieId: Long): Boolean
}