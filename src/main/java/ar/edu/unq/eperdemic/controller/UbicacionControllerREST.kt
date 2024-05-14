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
    fun crearUbicacion( @RequestBody ubicacion: UbicacionCreacionDTO) = ubicacionService.crear(ubicacion.aModelo()).aDTO()

    @PutMapping("/actualizarUbicacion")
    fun actualizarUbicacion( @RequestBody ubicacion: UbicacionDTO ) = ubicacionService.updatear(ubicacion.aModelo())

    @GetMapping("/{ubicacionId}")
    fun recuperarUbicacion( @PathVariable ubicacionId: Long ) = ubicacionService.recuperar(ubicacionId).aDTO()

    @GetMapping("/todasLasUbicaciones")
    fun recuperarTodasLasUbicaciones() = ubicacionService.recuperarTodos().map { ubicacion -> ubicacion.aDTO() }

    @PostMapping("/moverUbicacion/{vectorId}/{ubicacionId}")
    fun moverUbicacion( @PathVariable vectorId: Long, ubicacionId: Long ) = ubicacionService.mover(vectorId, ubicacionId)

    @PutMapping("/expandirUbicacion/{ubicacionId}")
    fun expandirUbicacion( @PathVariable ubicacionId: Long ) = ubicacionService.expandir(ubicacionId)

    @PostMapping("/conectar/{nombreUbicacion1}/{nombreUbicacion2}/{tipoCamino}")
    fun conectarUbicaciones( @PathVariable nombreUbicacion1: String, nombreUbicacion2: String, tipoCamino: String ) = ubicacionService.conectar(nombreUbicacion1, nombreUbicacion2, tipoCamino)

    @PostMapping("/conectados/{nombreUbicacion}")
    fun conectadosDeLaUbicacion( @PathVariable nombreUbicacion: String ) = ubicacionService.conectados(nombreUbicacion).map { ubicacion -> ubicacion.aDTO() }

    @PostMapping("/moverPorCaminoMasCorto/{vectorId}/{nombreUbicacion}")
    fun moverPorCaminoMasCorto( @PathVariable  vectorId: Long, nombreUbicacion: String ) = ubicacionService.moverPorCaminoMasCorto(vectorId, nombreUbicacion)

}