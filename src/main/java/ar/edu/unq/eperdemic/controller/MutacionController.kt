package ar.edu.unq.eperdemic.controller

import ar.edu.unq.eperdemic.controller.dto.MutacionDTO
import ar.edu.unq.eperdemic.services.MutacionService
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
@RequestMapping("/mutacion")
class MutacionController( private val mutacionService: MutacionService ) {

    @PostMapping("/{especieId}")
    fun agregarMutacion(@RequestBody mutacionDTO: MutacionDTO, @PathVariable especieId: Long ) = mutacionService.agregarMutacion(especieId, mutacionDTO.aModelo())

}