package ar.edu.unq.eperdemic.controller

import ar.edu.unq.eperdemic.services.EstadisticaService
import ar.edu.unq.eperdemic.modelo.Direccion
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin
@RequestMapping("/estadistica")
class EstadisticaControllerREST ( private val estadisticaService: EstadisticaService ){

    @GetMapping("/lider")
    fun especieLider() = estadisticaService.especieLider().aDTO()

    @GetMapping("/lideres/{direccion}/{pagina}/{cantPorPagina}")
    fun especiesLideres( @PathVariable direccion: String, pagina: Int, cantPorPagina: Int ) = estadisticaService.lideres(enumValueOf<Direccion>(direccion), pagina, cantPorPagina)

    @GetMapping("/reporteDeContagios/{nombreUbicacion}")
    fun reporteDeContagios( @PathVariable nombreUbicacion: String ) = estadisticaService.reporteDeContagios(nombreUbicacion)

}