package ar.edu.unq.eperdemic.controller

import ar.edu.unq.eperdemic.controller.dto.PatogenoDTO
import ar.edu.unq.eperdemic.services.PatogenoService
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
@RequestMapping("/patogeno")
class PatogenoControllerREST( private val patogenoService: PatogenoService ) {

    @PostMapping("/")
    fun crearPatogeno( @RequestBody patogeno: PatogenoDTO) = patogenoService.crear(patogeno.aModelo())

    @PutMapping("/actualizarPatogeno")
    fun actualizarPatogeno( @RequestBody patogenoDTO: PatogenoDTO) = patogenoService.updatear(patogenoDTO.aModelo())

    @GetMapping("/{patogenoId}")
    fun recuperarPatogeno( @PathVariable patogenoId: Long ) = patogenoService.recuperar(patogenoId).aDTO()

    @GetMapping("/todosLosPatogenos")
    fun recuperarTodosLosPatogenos() = patogenoService.recuperarTodos().map { patogeno -> patogeno.aDTO() }

    @PostMapping("/agregarEspecie/{patogenoId}/{nombreEspecie}/{ubicacionId}")
    fun agregarEspecie( @PathVariable patogenoId: Long, nombreEspecie: String, ubicacionId: Long ) = patogenoService.agregarEspecie(patogenoId, nombreEspecie, ubicacionId)

    @GetMapping("/esPandemia/{especieId}")
    fun esPandemia( @PathVariable especieId: Long ) = patogenoService.esPandemia(especieId)

    //@GetMapping("/especiesDePatogeno")
    //fun especiesDePatogeno() = patogenoService.especiesDePatogeno().map { especie -> especie.aDTO() }

}