package ar.edu.unq.eperdemic.controller

import ar.edu.unq.eperdemic.controller.dto.dtoCreacion.VectorCreacionDTO
import ar.edu.unq.eperdemic.controller.dto.VectorDTO
import ar.edu.unq.eperdemic.services.UbicacionService
import ar.edu.unq.eperdemic.services.VectorService
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
@RequestMapping("/vector")
class VectorControllerREST( private val vectorService: VectorService, private val ubicacionService: UbicacionService ) {

    @PostMapping("/")
    fun crearVector( @RequestBody vectorDTO: VectorCreacionDTO) : VectorDTO {
        val ubicacion = ubicacionService.recuperar(vectorDTO.ubicacionId!!)

        return vectorService.crear(vectorDTO.aModelo(ubicacion)).aDTO()
    }

    @PutMapping("/actualizarVector")
    fun actualizarVector( @RequestBody vectorDTO: VectorDTO ) {
        val ubicacion = ubicacionService.recuperar(vectorDTO.ubicacionId)

        vectorService.crear(vectorDTO.aModelo(ubicacion)).aDTO()
    }

    @GetMapping("/{vectorId}")
    fun vectorUbicacion( @PathVariable vectorId: Long ) = vectorService.recuperar(vectorId).aDTO()

    @GetMapping("/todosLosVectores")
    fun recuperarTodasLasVectores() = vectorService.recuperarTodos().map { vector -> vector.aDTO() }

    @GetMapping("/todosLosVectoresElastic")
    fun recuperarTodosLosVectoresElastic() = vectorService.recuperarTodosElastic().map { vector -> vector.aDTO() }

    @PutMapping("/infectar/{vectorId}/{especieId}")
    fun infectarVector( @PathVariable vectorId: Long, @PathVariable especieId: Long) = vectorService.infectar(vectorId, especieId)

    @GetMapping("/enfermedades/{vectorId}")
    fun enfermedades( @PathVariable vectorId: Long ) = vectorService.enfermedades(vectorId).map { especie -> especie.aDTO() }

}