package ar.edu.unq.eperdemic.testModelo

import ar.edu.unq.eperdemic.exceptions.ErrorCantidadDeCoordenadasMinimas
import ar.edu.unq.eperdemic.exceptions.ErrorNombre
import ar.edu.unq.eperdemic.modelo.Distrito
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.data.mongodb.core.geo.GeoJsonPoint
import org.springframework.data.mongodb.core.geo.GeoJsonPolygon

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DistritoModeloTest {

    @Test
    fun creacionDeUnDistrito() {
        val coordenadas = listOf(
            GeoJsonPoint(0.0, 0.0),
            GeoJsonPoint(30.0, 60.0),
            GeoJsonPoint(60.0, 10.0),
            GeoJsonPoint(0.0, 0.0),
        )

        val forma = GeoJsonPolygon(coordenadas)

        val distrito = Distrito("Quilmes", forma)

        Assertions.assertEquals("Quilmes", distrito.getNombre())
        Assertions.assertEquals(coordenadas.elementAt(0).x, distrito.getForma().elementAt(0).x)
        Assertions.assertEquals(coordenadas.elementAt(0).y, distrito.getForma().elementAt(0).y)
        Assertions.assertEquals(coordenadas.elementAt(1).x, distrito.getForma().elementAt(1).x)
        Assertions.assertEquals(coordenadas.elementAt(1).y, distrito.getForma().elementAt(1).y)
        Assertions.assertEquals(coordenadas.elementAt(2).x, distrito.getForma().elementAt(2).x)
        Assertions.assertEquals(coordenadas.elementAt(2).y, distrito.getForma().elementAt(2).y)
    }

    @Test
    fun errorAlCrearUnDistritoSinNombre() {
        val coordenadas = listOf(
            GeoJsonPoint(0.0, 0.0),
            GeoJsonPoint(30.0, 60.0),
            GeoJsonPoint(60.0, 10.0),
            GeoJsonPoint(0.0, 0.0),
        )

        val forma = GeoJsonPolygon(coordenadas)

        val mensajeError = Assertions.assertThrows(ErrorNombre::class.java){
            Distrito("", forma)
        }

        Assertions.assertEquals("El nombre del Distrito no puede ser vacio.", mensajeError.message)
    }

    @Test
    fun errorAlCrearUnDistritoConMenosDeDosCoordenadas() {
        val coordenadas = listOf(
            GeoJsonPoint(0.0, 0.0),
            GeoJsonPoint(30.0, 60.0)
        )

        val forma = GeoJsonPolygon(coordenadas)

        Assertions.assertThrows(ErrorCantidadDeCoordenadasMinimas::class.java) {
            Distrito("Quilmes", forma)
        }
    }



}