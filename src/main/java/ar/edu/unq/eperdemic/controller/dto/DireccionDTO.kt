package ar.edu.unq.eperdemic.controller.dto

import ar.edu.unq.eperdemic.modelo.Direccion

class DireccionDTO ( val tipo: String ) {

    companion object {
        fun aModelo( direccion: String ): Direccion {
            var nuevaDireccion: Direccion = Direccion.ASCENDENTE //esta mal, es solo para que no rompa por ahora

            if ( direccion == "ASCENDENTE" ) {
                nuevaDireccion = Direccion.ASCENDENTE
            } else if ( direccion == "DESCENDENTE" ) {
                nuevaDireccion = Direccion.DESCENDENTE
            } // tirar error??

            return nuevaDireccion
        }
    }


}