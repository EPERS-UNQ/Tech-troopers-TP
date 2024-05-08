package ar.edu.unq.eperdemic.modelo.mutacion.mutacionStrategy

import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.RandomGenerator.RandomGenerator

class MutacionStandarStrategy : MutacionStrategy() {

    override fun porcentajeDeMutacionExitoso(especie: Especie): Boolean {
        val random = RandomGenerator.getInstance()
        val porcentajeDeMutacionExitoso = random.getNumeroRandom() + especie.capacidadDeBiomecanizacion()
        return random.porcentajeExistoso(porcentajeDeMutacionExitoso)
    }

}