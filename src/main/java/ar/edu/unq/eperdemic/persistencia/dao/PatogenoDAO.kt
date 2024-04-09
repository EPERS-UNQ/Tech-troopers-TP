package ar.edu.unq.eperdemic.persistencia.dao

import ar.edu.unq.eperdemic.modelo.Patogeno

interface PatogenoDAO {
    fun crear(patogeno: Patogeno) : Patogeno
    fun recuperar(id: Long): Patogeno
    fun actualizar(patogeno: Patogeno)
    fun recuperarATodos() : List<Patogeno>
    fun eliminar(patogeno: Patogeno)
}
