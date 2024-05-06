package ar.edu.unq.eperdemic.controller

import ar.edu.unq.eperdemic.controller.dto.dtoCreacion.VectorCreacionDTO
import ar.edu.unq.eperdemic.controller.dto.VectorDTO
import ar.edu.unq.eperdemic.services.VectorService
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
@RequestMapping("/vector")
class VectorControllerREST( private val vectorService: VectorService ) {

    @PostMapping("/")
    fun crearVector( @RequestBody vectorDTO: VectorCreacionDTO) = vectorService.crear(vectorDTO.aModelo())

    @PutMapping("/actualizarVector")
    fun actualizarVector( @RequestBody vectorDTO: VectorDTO ) = vectorService.updatear(vectorDTO.aModelo())

    @GetMapping("/{vectorId}")
    fun vectorUbicacion( @PathVariable vectorId: Long ) = vectorService.recuperar(vectorId).aDTO()

    @GetMapping("/todosLasVectores")
    fun recuperarTodasLasVectores() = vectorService.recuperarTodos().map { vector -> vector.aDTO() }

    @PutMapping("/infectar/{vectorId}/{especieId}")
    fun infectarVector( @RequestBody vectorId: Long, especieId: Long) = vectorService.infectar(vectorId, especieId)

    @PutMapping("/enfermedades/{vectorId}")
    fun enfermedades( @PathVariable vectorId: Long ) = vectorService.enfermedades(vectorId)

}