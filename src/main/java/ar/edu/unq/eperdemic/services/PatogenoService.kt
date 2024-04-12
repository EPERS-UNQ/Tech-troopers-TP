package ar.edu.unq.eperdemic.services

import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.Patogeno

interface PatogenoService {
    fun crear(patogeno: Patogeno): Patogeno
    fun updatear(patogeno: Patogeno)
    fun recuperar(id: Long): Patogeno
    fun recuperarTodos(): List<Patogeno>
    fun agregarEspecie(idDePatogeno: Long, nombreEspecie: String, paisDeOrigen : String) : Especie
    fun especiesDePatogeno(patogenoId: Long): List<Especie>
    fun esPademia(especieId: Long): Boolean
}