package ar.edu.unq.eperdemic.testModelo

import ar.edu.unq.eperdemic.exceptions.ErrorCantidadDeCoordenadasMinimas
import ar.edu.unq.eperdemic.exceptions.ErrorNombre
import ar.edu.unq.eperdemic.modelo.Coordenada
import ar.edu.unq.eperdemic.modelo.Distrito
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DistritoModeloTest {

    @Test
    fun creacionDeUnDistrito() {
        val coordenada1 = Coordenada(45.0, 40.0)
        val coordenada2 = Coordenada(55.0, 50.0)
        val coordenada3 = Coordenada(65.0, 60.0)

        val coordenadas = HashSet<Coordenada>()
        coordenadas.add(coordenada1)
        coordenadas.add(coordenada2)
        coordenadas.add(coordenada3)

        val distrito = Distrito("Quilmes", coordenadas)

        Assertions.assertEquals("Quilmes", distrito.getNombre())
        Assertions.assertEquals(coordenada1.getLatitud(), distrito.getCoordenadas().elementAt(0).getLatitud())
        Assertions.assertEquals(coordenada1.getLongitud(), distrito.getCoordenadas().elementAt(0).getLongitud())
        Assertions.assertEquals(coordenada2.getLatitud(), distrito.getCoordenadas().elementAt(2).getLatitud())
        Assertions.assertEquals(coordenada2.getLongitud(), distrito.getCoordenadas().elementAt(2).getLongitud())
        Assertions.assertEquals(coordenada3.getLatitud(), distrito.getCoordenadas().elementAt(1).getLatitud())
        Assertions.assertEquals(coordenada3.getLongitud(), distrito.getCoordenadas().elementAt(1).getLongitud())
    }

    @Test
    fun errorAlCrearUnDistritoSinNombre() {
        val coordenada1 = Coordenada(45.0, 40.0)
        val coordenada2 = Coordenada(55.0, 50.0)
        val coordenada3 = Coordenada(65.0, 60.0)

        val coordenadas = HashSet<Coordenada>()
        coordenadas.add(coordenada1)
        coordenadas.add(coordenada2)
        coordenadas.add(coordenada3)

        val mensajeError = Assertions.assertThrows(ErrorNombre::class.java){
            Distrito("", coordenadas)
        }

        Assertions.assertEquals("El nombre del Distrito no puede ser vacio.", mensajeError.message)
    }

    @Test
    fun errorAlCrearUnDistritoConMenosDeDosCoordenadas() {
        val coordenada1 = Coordenada(45.0, 40.0)
        val coordenada2 = Coordenada(55.0, 50.0)

        val coordenadas = HashSet<Coordenada>()
        coordenadas.add(coordenada1)
        coordenadas.add(coordenada2)

        Assertions.assertThrows(ErrorCantidadDeCoordenadasMinimas::class.java) {
            Distrito("Quilmes", coordenadas)
        }
    }



}