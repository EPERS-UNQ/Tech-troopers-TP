package ar.edu.unq.eperdemic.services.impl

import ar.edu.unq.eperdemic.exceptions.*
import ar.edu.unq.eperdemic.modelo.Distrito
import ar.edu.unq.eperdemic.persistencia.dao.DistritoDAO
import ar.edu.unq.eperdemic.persistencia.dao.UbicacionJpaDAO
import ar.edu.unq.eperdemic.services.DistritoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class DistritoServiceImpl : DistritoService {

    @Autowired private lateinit var distritoDAO: DistritoDAO
    @Autowired private lateinit var ubicacionDAO: UbicacionJpaDAO

    override fun crear(distrito: Distrito): Distrito {
        val distritoDb = this.distritoDAO.findByNombre(distrito.getNombre()!!)
        if(distritoDb !== null){
            throw NombreDeDistritoExistenteException(distrito.getNombre()!!)
        }
        if(this.distritoDAO.findSeIntersectanCon(distrito.getForma()).isNotEmpty()){
            throw CoordenadaDistritoIntersectionException()
        }
        return distritoDAO.save(distrito)
    }

    override fun distritoMasEnfermo(): Distrito {

        if (!ubicacionDAO.hayUbicaciones()){
            throw NoHayUbicaciones()
        }
        val ubicacionesInfectadas = ubicacionDAO.ubicacionesInfectadas()

        val distritoMasInfectado = distritoDAO.distritoMasInfectado(ubicacionesInfectadas) ?: throw NoHayDistritoInfectado()

        return  distritoDAO.findByNombre(distritoMasInfectado)!!
    }

    override fun recuperarPorNombre(nombreDistrito: String): Distrito {
        return distritoDAO.findByNombre(nombreDistrito) ?:
        throw DistritoNoExistenteException(nombreDistrito)
    }

    override fun actualizarDistrito(distrito: Distrito) {
        if(distrito.getId() == null) {
            throw DistritoNoExistenteException(distrito.getNombre()!!)
        }

        val distritoOriginal: Optional<Distrito> = distritoDAO.findById(distrito.getId()!!)
        val distritoValor: Distrito = distritoOriginal.orElseThrow {
            DistritoNoExistenteException(distrito.getNombre()!!)
        }

        val distritoDb = this.distritoDAO.findByNombre(distrito.getNombre()!!)
        if(distritoDb!!.getNombre() != null &&
            distritoValor.getNombre()!! != distrito.getNombre()!!
            ) {
            throw NombreDeDistritoExistenteException(distrito.getNombre()!!)
        }

        if( distrito.getForma() != distritoValor.getForma()
            &&
            this.distritoDAO.findSeIntersectanCon(distrito.getForma()).isNotEmpty()){
            throw CoordenadaDistritoIntersectionException()
        }

        this.distritoDAO.save(distrito)
    }

    override fun deleteAll() {
        distritoDAO.deleteAll()
    }

}