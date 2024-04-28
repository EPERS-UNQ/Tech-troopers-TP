package ar.edu.unq.eperdemic.testServicios

/*

import javax.persistence.PersistenceException

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EspecieServiceImplTest {

    lateinit var dataService         : DataService
    lateinit var service             : EspecieService
    lateinit var servicePatogeno     : PatogenoService
    lateinit var serviceVector       : VectorService
    lateinit var serviceUbicacion    : UbicacionService

    lateinit var especiePersistida   : Especie
    lateinit var especiePersistida2  : Especie

    lateinit var patogeno  : Patogeno
    lateinit var patogeno2 : Patogeno

    lateinit var humano     : Vector
    lateinit var golondrina : Vector
    lateinit var ubicacion : Ubicacion

    lateinit var random : RandomGenerator

    @BeforeEach
    fun crearModelo() {
        patogeno  = Patogeno("Wachiturro", 90, 9, 9, 9, 67)
        ubicacion = Ubicacion("Argentina")
        humano    = Vector("Pedro", ubicacion, TipoVector.HUMANO)

        service = EspecieServiceImpl( HibernateEspecieDAO(), HibernateVectorDAO() )
        servicePatogeno  = PatogenoServiceImpl(HibernatePatogenoDAO(), HibernateEspecieDAO(), HibernateUbicacionDAO(), HibernateVectorDAO())
        serviceVector    = VectorServiceImp( HibernateVectorDAO(), HibernateEspecieDAO() )
        serviceUbicacion = UbicacionServiceImp( HibernateUbicacionDAO(), HibernateVectorDAO() )
        dataService = DataServiceImpl(HibernateDataDAO())

        random = RandomGenerator.getInstance()
        random.setStrategy(NoAleatorioStrategy())
        random.setNumeroGlobal(0)
        random.setBooleanoGlobal(true)

        serviceUbicacion.crear(ubicacion)
        serviceVector.crear(humano)

        servicePatogeno.crear(patogeno)

        especiePersistida = servicePatogeno.agregarEspecie(patogeno.getId()!!, "Bacteria", ubicacion.getId()!!)

    }

    @Test
    fun testAlRecuperarUnaEspecieSeObtienenObjetosSimilares() {
        val otraEspecie = service.recuperar(especiePersistida.getId()!!)

        Assertions.assertEquals(especiePersistida.getId(), otraEspecie.getId())
        Assertions.assertEquals(especiePersistida.nombre, otraEspecie.nombre)
        Assertions.assertEquals(especiePersistida.paisDeOrigen, otraEspecie.paisDeOrigen)
        Assertions.assertEquals(especiePersistida.patogeno!!.getId(), otraEspecie.patogeno!!.getId())
    }

    @Test
    fun testAlUpdatearUnPatogenoLaInformacionDelMismoCmabia() {
        var especieRecuperada = service.recuperar(especiePersistida.getId()!!)
        especieRecuperada.paisDeOrigen = "Chile"

        service.updatear(especieRecuperada)

        Assertions.assertEquals("Chile", service.recuperar(especieRecuperada.getId()!!).paisDeOrigen)
    }


    @Test
    fun testAlRecuperarTodasLasEspeciesLasMismasSonSimiliaresALasYaExistentes() {
        patogeno2 = Patogeno("Otaku", 78, 7, 7, 8, 12)
        servicePatogeno.crear(patogeno2)

        especiePersistida2 = servicePatogeno.agregarEspecie(patogeno2.getId()!!, "Virus", ubicacion.getId()!!)

        val listaEspeciesRecuperadas : List<Especie> = service.recuperarTodos()

        Assertions.assertEquals(especiePersistida.getId(),  listaEspeciesRecuperadas[0].getId())
        Assertions.assertEquals(especiePersistida.nombre, listaEspeciesRecuperadas[0].nombre)
        Assertions.assertEquals(especiePersistida.paisDeOrigen, listaEspeciesRecuperadas[0].paisDeOrigen)
        Assertions.assertEquals(especiePersistida2.getId(), listaEspeciesRecuperadas[1].getId())
        Assertions.assertEquals(especiePersistida2.nombre, listaEspeciesRecuperadas[1].nombre)
        Assertions.assertEquals(especiePersistida2.paisDeOrigen, listaEspeciesRecuperadas[1].paisDeOrigen)
    }

    @Test
    fun testVerificacionDeCantidadDeVectoresInfectadosPorUnaEspecieParticular() {
        golondrina = Vector("Pepita", ubicacion, TipoVector.ANIMAL)
        serviceVector.crear(golondrina)

        serviceVector.infectar(golondrina.getId()!!, especiePersistida.getId()!!)

        Assertions.assertEquals(2, service.cantidadDeInfectados(especiePersistida.getId()!!))

    }

    @Test
    fun testSeTrataDeRecuperarUnaEspecieQueNoExiste() {

        Assertions.assertThrows(NoExisteLaEspecie::class.java) {
            service.recuperar(15)
        }

    }

    @Test
    fun testCuandoSeIntentaCrearDosEspeciesConElMismoNombre(){

        Assertions.assertThrows(PersistenceException::class.java){
            servicePatogeno.agregarEspecie(patogeno.getId()!!, "Bacteria", ubicacion.getId()!!)
        }
    }

    @AfterEach
    fun borrarRegistros() {

        dataService.cleanAll()

    }

}

 */