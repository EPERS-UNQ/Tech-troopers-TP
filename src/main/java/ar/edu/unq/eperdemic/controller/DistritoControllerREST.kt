package ar.edu.unq.eperdemic.controller

import ar.edu.unq.eperdemic.controller.dto.DistritoDTO
import ar.edu.unq.eperdemic.services.DistritoService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin
@RequestMapping("/distrito")
class DistritoControllerREST( private val distritoService: DistritoService ) {

    @PostMapping("/")
    fun crearDistrito( @RequestBody distritoDTO: DistritoDTO) = distritoService.crear(distritoDTO.aModelo()).aDTO()

    @GetMapping("/masEnfermo")
    fun distritoMasEnfermo() = distritoService.distritoMasEnfermo().aDTO()

}