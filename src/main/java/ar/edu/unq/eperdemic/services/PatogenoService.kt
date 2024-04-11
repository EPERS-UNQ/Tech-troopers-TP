package ar.edu.unq.eperdemic.services

import ar.edu.unq.eperdemic.modelo.Patogeno

interface PatogenoService {
    fun crearPatogeno(patogeno: Patogeno): Patogeno
    fun updatearPatogeno(patogeno: Patogeno)
    fun recuperarPatogeno(id: Long): Patogeno
    fun recuperarATodosLosPatogenos(): List<Patogeno>
    fun agregarEspecie(idDePatogeno: Long, nombreEspecie: String, paisDeOrigen : String) : Especie
    fun especiesDePatogeno(patogenoId: Long): List<Especie>
    fun esPademia(especieId: Long): Boolean
}