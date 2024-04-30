package ar.edu.unq.eperdemic.controller

import ar.edu.unq.eperdemic.controller.dto.EspecieDTO
import ar.edu.unq.eperdemic.services.EspecieService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin
@RequestMapping("/especie")
class EspecieControllerREST( private val especieService: EspecieService ) {

    @PostMapping("/")
    fun modificarEspecie( @RequestBody especie: EspecieDTO) = especieService.updatear(especie.aModelo())

    @GetMapping("/{especieId}")
    fun recuperarEspecie( @PathVariable especieId: Long ) = EspecieDTO.desdeModelo(especieService.recuperar(especieId!!))

    @GetMapping("/todasLasEspecies")
    fun recuperarTodasLasEspecies() = especieService.recuperarTodos().map { especie -> EspecieDTO.desdeModelo(especie) }

    @GetMapping("/cantInfectados/{especieId}")
    fun cantidadDeVectoresInfectados( @PathVariable especieId: Long ) = especieService.cantidadDeInfectados(especieId)

}