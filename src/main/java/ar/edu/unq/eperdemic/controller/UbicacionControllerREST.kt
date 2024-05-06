package ar.edu.unq.eperdemic.controller

import ar.edu.unq.eperdemic.controller.dto.dtoCreacion.UbicacionCreacionDTO
import ar.edu.unq.eperdemic.controller.dto.UbicacionDTO
import ar.edu.unq.eperdemic.services.UbicacionService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin
@RequestMapping("/ubicacion")
class UbicacionControllerREST( private val ubicacionService: UbicacionService ) {

    @PostMapping("/")
    fun crearUbicacion( @RequestBody ubicacion: UbicacionCreacionDTO) = ubicacionService.crear(ubicacion.aModelo())

    @PutMapping("/actualizarUbicacion")
    fun actualizarUbicacion( @RequestBody ubicacion: UbicacionDTO ) = ubicacionService.updatear(ubicacion.aModelo())

    @GetMapping("/{ubicacionId}")
    fun recuperarUbicacion( @PathVariable ubicacionId: Long ) = ubicacionService.recuperar(ubicacionId).aDTO()

    @GetMapping("/todasLasUbicaciones")
    fun recuperarTodasLasUbicaciones() = ubicacionService.recuperarTodos().map { ubicacion -> ubicacion.aDTO() }

    @PutMapping("/moverUbicacion")
    fun moverUbicacion( @RequestBody vectorId: Long, ubicacionId: Long ) = ubicacionService.mover(vectorId, ubicacionId)

    @PutMapping("/expandirUbicacion/{ubicacionId}")
    fun expandirUbicacion( @PathVariable ubicacionId: Long ) = ubicacionService.expandir(ubicacionId)

}