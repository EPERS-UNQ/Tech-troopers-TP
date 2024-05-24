package ar.edu.unq.eperdemic.testServicios

import ar.edu.unq.eperdemic.exceptions.*
import ar.edu.unq.eperdemic.helper.dao.HibernateDataDAO
import ar.edu.unq.eperdemic.helper.service.DataService
import ar.edu.unq.eperdemic.helper.service.DataServiceImpl
import ar.edu.unq.eperdemic.modelo.Especie
import ar.edu.unq.eperdemic.modelo.Patogeno
import ar.edu.unq.eperdemic.modelo.RandomGenerator.NoAleatorioStrategy
import ar.edu.unq.eperdemic.modelo.RandomGenerator.RandomGenerator
import ar.edu.unq.eperdemic.modelo.UbicacionJpa
import ar.edu.unq.eperdemic.modelo.mutacion.ElectroBranqueas
import ar.edu.unq.eperdemic.modelo.mutacion.PropulsionMotora
import ar.edu.unq.eperdemic.modelo.vector.TipoVector
import ar.edu.unq.eperdemic.modelo.vector.Vector
import ar.edu.unq.eperdemic.services.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.util.Assert

@ExtendWith(SpringExtension::class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UbicacionServiceNeo4JTest {

    @Autowired lateinit var serviceUbicacion: UbicacionService
    @Autowired lateinit var serviceVector:    VectorService
    @Autowired lateinit var servicePatogeno:  PatogenoService
    @Autowired lateinit var serviceMutacion:  MutacionService
    @Autowired lateinit var serviceEspecie:   EspecieService

    lateinit var arg: UbicacionJpa
    lateinit var chl: UbicacionJpa
    lateinit var col: UbicacionJpa
    lateinit var vnz: UbicacionJpa
    lateinit var par: UbicacionJpa
    lateinit var bol: UbicacionJpa
    lateinit var urg: UbicacionJpa
    lateinit var ecu: UbicacionJpa
    lateinit var br:  UbicacionJpa
    lateinit var per: UbicacionJpa
    lateinit var hn:  UbicacionJpa
    lateinit var hornerito: Vector
    lateinit var mosca: Vector
    lateinit var guanaco: Vector
    lateinit var abeja: Vector
    lateinit var joao: Vector
    lateinit var maria: Vector
    lateinit var virus: Patogeno
    lateinit var bacteria: Patogeno
    lateinit var hongo: Patogeno
    lateinit var granulosis: Especie
    lateinit var poliedrosisCitoplasmica: Especie
    lateinit var beauveriaBassiana: Especie
    lateinit var metarhiziumAnisopliae: Especie
    lateinit var wolbachia: Especie
    lateinit var bacillusThuringiensis: Especie
    lateinit var propulsionMotora: PropulsionMotora
    lateinit var electroBranqueas: ElectroBranqueas

    lateinit var random: RandomGenerator
    lateinit var dataService: DataService

    @BeforeEach
    fun crearModelo() {

        dataService = DataServiceImpl(HibernateDataDAO())

        arg = UbicacionJpa("Argentina")
        chl = UbicacionJpa("Chile")
        col = UbicacionJpa("Colombia")
        vnz = UbicacionJpa("Venezuela")
        par = UbicacionJpa("Paraguay")
        bol = UbicacionJpa("Bolivia")
        urg = UbicacionJpa("Uruguay")
        ecu = UbicacionJpa("Ecuador")
        br  = UbicacionJpa("Brasil")
        per = UbicacionJpa("Peru")
        hn  = UbicacionJpa("Honduras")
        hornerito = Vector("Hornerito", arg, TipoVector.ANIMAL)
        guanaco = Vector("Guanaco", chl, TipoVector.ANIMAL)
        mosca = Vector("Mosca", urg, TipoVector.INSECTO)
        abeja = Vector("Abeja", col, TipoVector.INSECTO)
        joao = Vector("Joao", br, TipoVector.HUMANO)
        maria = Vector("Maria", vnz, TipoVector.HUMANO)
        virus = Patogeno("Virus", 13,14,53,30,10)
        bacteria = Patogeno("Bacteria", 5, 10, 32, 25, 50)
        hongo = Patogeno("Hongo", 65, 20, 30, 45, 15)
        propulsionMotora = PropulsionMotora()
        electroBranqueas = ElectroBranqueas()

        serviceUbicacion.crear(arg)
        serviceUbicacion.crear(chl)
        serviceUbicacion.crear(col)
        serviceUbicacion.crear(vnz)
        serviceUbicacion.crear(par)
        serviceUbicacion.crear(bol)
        serviceUbicacion.crear(urg)
        serviceUbicacion.crear(ecu)
        serviceUbicacion.crear(br)
        serviceUbicacion.crear(per)
        serviceUbicacion.crear(hn)
        serviceVector.crear(hornerito)
        serviceVector.crear(guanaco)
        serviceVector.crear(mosca)
        serviceVector.crear(abeja)
        serviceVector.crear(joao)
        serviceVector.crear(maria)
        servicePatogeno.crear(virus)
        servicePatogeno.crear(bacteria)
        servicePatogeno.crear(hongo)

        granulosis = servicePatogeno.agregarEspecie(virus.getId(), "Granulosis", arg.getId()!!)
        poliedrosisCitoplasmica = servicePatogeno.agregarEspecie(virus.getId(), "Poliedrosis Citoplasmica", chl.getId()!!)
        beauveriaBassiana = servicePatogeno.agregarEspecie(bacteria.getId(), "Beauveria Bassiana", br.getId()!!)
        metarhiziumAnisopliae = servicePatogeno.agregarEspecie(bacteria.getId(), "Metarhizium Anisopliae", vnz.getId()!!)
        wolbachia = servicePatogeno.agregarEspecie(hongo.getId(), "Wolbachia", urg.getId()!!)
        bacillusThuringiensis = servicePatogeno.agregarEspecie(hongo.getId(), "Bacillus Thuringiensis", col.getId()!!)

        serviceUbicacion.conectar(arg.getNombre()!!, vnz.getNombre()!!, "Aereo")
        serviceUbicacion.conectar(arg.getNombre()!!, chl.getNombre()!!, "Terrestre")
        serviceUbicacion.conectar(arg.getNombre()!!, chl.getNombre()!!, "Maritimo")
        serviceUbicacion.conectar(arg.getNombre()!!, urg.getNombre()!!, "Maritimo")
        serviceUbicacion.conectar(urg.getNombre()!!, bol.getNombre()!!, "Aereo")
        serviceUbicacion.conectar(urg.getNombre()!!, arg.getNombre()!!, "Maritimo")
        serviceUbicacion.conectar(chl.getNombre()!!, urg.getNombre()!!, "Maritimo")
        serviceUbicacion.conectar(chl.getNombre()!!, br.getNombre()!!, "Maritimo")
        serviceUbicacion.conectar(chl.getNombre()!!, per.getNombre()!!, "Terrestre")
        serviceUbicacion.conectar(per.getNombre()!!, bol.getNombre()!!, "Aereo")
        serviceUbicacion.conectar(per.getNombre()!!, vnz.getNombre()!!, "Aereo")
        serviceUbicacion.conectar(per.getNombre()!!, bol.getNombre()!!, "Terrestre")
        serviceUbicacion.conectar(vnz.getNombre()!!, col.getNombre()!!, "Terrestre")
        serviceUbicacion.conectar(col.getNombre()!!, vnz.getNombre()!!, "Maritimo")
        serviceUbicacion.conectar(col.getNombre()!!, bol.getNombre()!!, "Aereo")
        serviceUbicacion.conectar(br.getNombre()!!, ecu.getNombre()!!, "Aereo")
        serviceUbicacion.conectar(br.getNombre()!!, bol.getNombre()!!, "Terrestre")
        serviceUbicacion.conectar(par.getNombre()!!, br.getNombre()!!, "Terrestre")
        serviceUbicacion.conectar(bol.getNombre()!!, ecu.getNombre()!!, "Terrestre")
        serviceUbicacion.conectar(bol.getNombre()!!, br.getNombre()!!, "Terrestre")
        serviceUbicacion.conectar(bol.getNombre()!!, urg.getNombre()!!, "Terrestre")

        random = RandomGenerator.getInstance()
        random.setStrategy(NoAleatorioStrategy())
        random.setNumeroGlobal(1)
        random.setBooleanoGlobal(true)
        random.setBooleanoAltGlobal(true)

    }

    @Test
    fun sePuedenConectarDosUbicacionesMedianteUnCamino() {

        val ubicacionesDe = serviceUbicacion.conectados(ecu.getNombre()!!)
        Assertions.assertTrue(ubicacionesDe.isEmpty())

        serviceUbicacion.conectar(ecu.getNombre()!!, col.getNombre()!!, "Maritimo")

        val ubicacionesDeEcu = serviceUbicacion.conectados(ecu.getNombre()!!)
        Assertions.assertTrue(ubicacionesDeEcu.isNotEmpty())
        Assertions.assertEquals(9, ubicacionesDeEcu.size)

    }

    @Test
    fun seSabeCuantosCaminosPuedeElegirUnVectorALaHoraDeMoversePorUnaUbicacion() {

        val ubicacionesDeHonduras = serviceUbicacion.conectados(hn.getNombre()!!)

        Assertions.assertEquals(0, ubicacionesDeHonduras.size)

        serviceUbicacion.conectar(hn.getNombre()!!, chl.getNombre()!!, "Aereo")

        val nuevasUbicacionesDeHonduras = serviceUbicacion.conectados(hn.getNombre()!!)

        Assertions.assertEquals(9, nuevasUbicacionesDeHonduras.size)
    }

    @Test
    fun unVectorSoloSePuedeMoverEntrePaisesSiEstanConectadosPorAlgunTipoDeCamino() {

        serviceUbicacion.mover(hornerito.getId(), urg.getId()!!)

        val ubicacionHornerito = serviceVector.recuperar(hornerito.getId()).ubicacion!!.getId()!!

        Assertions.assertEquals(urg.getId()!!, ubicacionHornerito)

    }

    @Test
    fun unVectorHumanoNoSePuedeMoverPorCaminosAereos() {

        Assertions.assertThrows(ErrorUbicacionNoAlcanzable::class.java){
            serviceUbicacion.mover(joao.getId(), ecu.getId()!!)
        }

    }

    @Test
    fun unVectorInsectoNoSePuedeMoverPorCaminosMaritimo() {

        Assertions.assertThrows(ErrorUbicacionNoAlcanzable::class.java){
            serviceUbicacion.mover(abeja.getId(), vnz.getId()!!)
        }

    }

    @Test
    fun unVectorNoSePuedeMoverEntreUbicacionesQueNoTienenCaminos() {

        Assertions.assertThrows(ErrorUbicacionMuyLejana::class.java){
            serviceUbicacion.mover(mosca.getId(), chl.getId()!!)
        }

    }

    @Test
    fun unVectorSeMuevePorElCaminoMasCortoCuandoSeMuevePorVariasUbicaciones() {

        Assertions.assertEquals(arg.getNombre(), hornerito.nombreDeUbicacionActual())

        serviceUbicacion.moverPorCaminoMasCorto(hornerito.getId(), ecu.getNombre()!!)

        val horneritoLuegoDeMoverse = serviceVector.recuperar(hornerito.getId())

        Assertions.assertEquals(ecu.getNombre(), horneritoLuegoDeMoverse.nombreDeUbicacionActual())

    }

    @Test
    fun cuandoUnVectorContagiadoSeMuevePorVariasUbicacionesTrataDeContagiarEnTodasEllas() {

        val horneritoInfectado = serviceVector.recuperar(hornerito.getId())

        Assertions.assertFalse(maria.estaInfectadoCon(granulosis))
        Assertions.assertFalse(abeja.estaInfectadoCon(granulosis))
        Assertions.assertTrue(horneritoInfectado.estaInfectadoCon(granulosis))

        serviceUbicacion.moverPorCaminoMasCorto(hornerito.getId(),col.getNombre()!!)

        val abejaInfectada = serviceVector.recuperar(abeja.getId())
        val mariaInfectada = serviceVector.recuperar(maria.getId())

        Assertions.assertTrue(abejaInfectada.estaInfectadoCon(granulosis))
        Assertions.assertTrue(mariaInfectada.estaInfectadoCon(granulosis))

    }

    @Test
    fun cuandoUnVectorHumanoConPropulsionMotoraPuedeCruzarUnCaminoDeTipoAereo() {
        val pepe = Vector("Pepe", bol, TipoVector.HUMANO)
        serviceVector.crear(pepe)
        serviceMutacion.agregarMutacion(beauveriaBassiana.getId()!!, propulsionMotora)

        val beauveriaBassianaConMutacion = serviceEspecie.recuperar(beauveriaBassiana.getId()!!)
        val joaoInfectado = serviceVector.recuperar(joao.getId())

        Assertions.assertFalse(pepe.estaInfectado())
        Assertions.assertTrue(joaoInfectado.estaInfectado())
        Assertions.assertFalse(joaoInfectado.estaMutado())

        serviceUbicacion.mover(joao.getId(), bol.getId()!!)

        val pepeInfectado = serviceVector.recuperar(pepe.getId())

        val joaoMutado = serviceVector.recuperar(joao.getId())

        Assertions.assertTrue(pepeInfectado.estaInfectado())
        Assertions.assertTrue(beauveriaBassianaConMutacion.tieneLaMutacion(propulsionMotora))
        Assertions.assertTrue(joaoMutado.estaMutadoCon(propulsionMotora))

        serviceUbicacion.mover(joao.getId(), br.getId()!!)

        serviceUbicacion.mover(joao.getId(), ecu.getId()!!)

        val joaoViajero = serviceVector.recuperar(joao.getId())

        Assertions.assertEquals(ecu.getNombre(), joaoViajero.nombreDeUbicacionActual())
    }

    @Test
    fun cuandoUnVectorInsectoConElectroBranqueasPuedeCruzarUnCaminoDeTipoMaritimo() {
        val pepe = Vector("Pepe", bol, TipoVector.HUMANO)
        serviceVector.crear(pepe)
        serviceMutacion.agregarMutacion(wolbachia.getId()!!, electroBranqueas)

        val wolbachiaConMutacion = serviceEspecie.recuperar(wolbachia.getId()!!)
        val moscaInfectada = serviceVector.recuperar(mosca.getId())

        Assertions.assertFalse(pepe.estaInfectado())
        Assertions.assertTrue(moscaInfectada.estaInfectado())
        Assertions.assertFalse(moscaInfectada.estaMutado())

        serviceUbicacion.mover(moscaInfectada.getId(), bol.getId()!!)

        val pepeInfectado = serviceVector.recuperar(pepe.getId())

        val moscaMutada = serviceVector.recuperar(moscaInfectada.getId())

        Assertions.assertTrue(pepeInfectado.estaInfectado())
        Assertions.assertTrue(wolbachiaConMutacion.tieneLaMutacion(electroBranqueas))
        Assertions.assertTrue(moscaMutada.estaMutadoCon(electroBranqueas))

        serviceUbicacion.mover(moscaMutada.getId(), urg.getId()!!)

        serviceUbicacion.mover(moscaMutada.getId(), arg.getId()!!)

        val moscaViajera = serviceVector.recuperar(mosca.getId())

        Assertions.assertEquals(arg.getNombre(), moscaViajera.nombreDeUbicacionActual())
    }

    @AfterEach
    fun tearDown() {
        serviceUbicacion.deleteAll()
        dataService.cleanAll()
    }

}