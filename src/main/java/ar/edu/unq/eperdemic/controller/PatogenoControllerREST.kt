package ar.edu.unq.eperdemic.controller

import ar.edu.unq.eperdemic.controller.dto.dtoCreacion.PatogenoCreacionDTO
import ar.edu.unq.eperdemic.controller.dto.PatogenoDTO
import ar.edu.unq.eperdemic.controller.dto.dtoCreacion.EspecieCreacionDTO
import ar.edu.unq.eperdemic.modelo.Direccion
import ar.edu.unq.eperdemic.services.PatogenoService
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
@RequestMapping("/patogeno")
class PatogenoControllerREST( private val patogenoService: PatogenoService ) {

    @PostMapping("/")
    fun crearPatogeno( @RequestBody patogeno: PatogenoCreacionDTO) = patogenoService.crear(patogeno.aModelo()).aDTO()

    @PutMapping("/actualizarPatogeno")
    fun actualizarPatogeno( @RequestBody patogenoDTO: PatogenoDTO) = patogenoService.updatear(patogenoDTO.aModelo())

    @GetMapping("/{patogenoId}")
    fun recuperarPatogeno( @PathVariable patogenoId: Long ) = patogenoService.recuperar(patogenoId)!!.aDTO()

    @GetMapping("/todosLosPatogenos")
    fun recuperarTodosLosPatogenos() = patogenoService.recuperarTodos().map { patogeno -> patogeno.aDTO() }

    @PostMapping("/agregarEspecie")
    fun agregarEspecie( @RequestBody especieCreacionDTO: EspecieCreacionDTO) = patogenoService.agregarEspecie(especieCreacionDTO.patogenoId, especieCreacionDTO.nombre, especieCreacionDTO.ubicacionId).aDTO()

    @GetMapping("/esPandemia/{especieId}")
    fun esPandemia( @PathVariable especieId: Long ) = patogenoService.esPandemia(especieId)

    @GetMapping("/especiesDePatogeno/{patogenoId}/{direccion}/{pagina}/{cantidadPorPagina}")
    fun especiesDePatogeno( @PathVariable patogenoId: Long, direccion: String, pagina: Int, cantidadPorPagina: Int) = patogenoService.especiesDePatogeno(patogenoId, enumValueOf<Direccion>(direccion), pagina, cantidadPorPagina).map { especie -> especie.aDTO() }

}